package com.example.lmsserver.servisi;

import com.example.lmsserver.klase.*;
import com.example.lmsserver.klase.enumi.StatusPolaganja;
import com.example.lmsserver.repozitorijumi.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Transactional
public class PristupService {
    //region pomocne metode
    private Jwt ulogovaniJwt() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getPrincipal() instanceof Jwt jwt ? jwt : null;
    }

    public Long ulogovaniId() {
        Jwt jwt = ulogovaniJwt();
        if (jwt == null) return null;
        Object id = jwt.getClaim("id");
        return id instanceof Number n ? n.longValue() : null;
    }

    public   String ulogovaniUloga() {
        Jwt jwt = ulogovaniJwt();
        if (jwt == null) return null;
        Object uloga = jwt.getClaim("uloga");
        return uloga instanceof String s ? s : null;
    }
    //endregion

    //region repozitorijumi
    final DrziPredmetRepository drziPredmetRepository;
    final GrupaRepository grupaRepository;
    final KomentarRepository komentarRepository;
    final PredavacRepository predavacRepository;
    final PredmetRepository predmetRepository;
    final ProveraZnanjaRepository proveraZnanjaRepository;
    final ResenjeRepository resenjeRepository;
    final SmerRepository smerRepository;
    final StudentPredmetRepository studentPredmetRepository;
    final StudentRepository studentRepository;
    final StudentTerminRepository studentTerminRepository;
    final TerminRepository terminRepository;
    final ZadatakRepository zadatakRepository;
    //endregion

    public boolean jePredavacZaTermin(Long terminId) {
        Termin termin = terminRepository.findById(terminId)
                        .orElseThrow(() -> new RuntimeException("Termin ne postoji"));
        return termin.getPredavac().getId().equals(ulogovaniId());
    }

    public boolean jePredavacZaZadatak(Long zadatakId) {
        Zadatak zadatak = zadatakRepository.findById(zadatakId)
                        .orElseThrow(() -> new RuntimeException("Zadatak ne postoji"));
        return zadatak.getTermin().getPredavac().getId().equals(ulogovaniId());
    }

    public boolean jePredavacZaProveru(Long proveraId) {
        ProveraZnanja provera = proveraZnanjaRepository.findById(proveraId)
                        .orElseThrow(() -> new RuntimeException("Provera ne postoji"));
        Long predmetIt = provera.getPredmet().getId();
        return drziPredmetRepository.existsByPredmetIdAndPredavacId(predmetIt, ulogovaniId());
    }

    public boolean jePredavacZaResenje(Long resenjeId) {
        if(!(ulogovaniUloga().equals("PREDAVAC"))) return false;

        Resenje resenje = resenjeRepository.findById(resenjeId)
                        .orElseThrow(() -> new RuntimeException("Resenje ne postoji"));

        LocalDateTime kraj = resenje.getZadatak().getTermin().getKraj();
        if(LocalDateTime.now().isBefore(kraj)) return false; // ne moze ocenjivati resenje dok provera znanja i dalje traje

        return resenje.getZadatak().getTermin().getPredavac().getId().equals(ulogovaniId());
    }

    public boolean studentPolazeZadatak(Long zadatakId) {
        if(!(ulogovaniUloga().equals("STUDENT"))) return false;

        Zadatak zadatak = zadatakRepository.findById(zadatakId)
                        .orElseThrow(() -> new RuntimeException("Zadatak ne postoji"));

        Long studentId = ulogovaniId();
        Long terminId = zadatak.getTermin().getId();
        LocalDateTime pocetak = zadatak.getTermin().getPocetak();
        LocalDateTime kraj = zadatak.getTermin().getKraj();

        if(LocalDateTime.now().isBefore(pocetak) || LocalDateTime.now().isAfter(kraj)) return false;
        return studentTerminRepository.existsByTerminIdAndStudentId(terminId, studentId);
    }

    public boolean studentPolazeTermin(Long terminId) {
        if(!(ulogovaniUloga().equals("STUDENT"))) return false;

        Termin termin = terminRepository.findById(terminId)
                .orElseThrow(() -> new RuntimeException("Termin ne postoji"));

        Long studentId = ulogovaniId();
        LocalDateTime pocetak = termin.getPocetak();
        LocalDateTime kraj = termin.getKraj();

        StudentTermin studentTermin = studentTerminRepository.findByTerminIdAndStudentId(terminId, studentId);

        if(LocalDateTime.now().isBefore(pocetak) || LocalDateTime.now().isAfter(kraj) || studentTermin.getStatus() == StatusPolaganja.NIJE_PRIJAVLJEN) return false;
        return studentTerminRepository.existsByTerminIdAndStudentId(terminId, studentId);
    }

    public boolean studentPolazeResenje(Long resenjeId) {
        Resenje resenje = resenjeRepository.findById(resenjeId)
                .orElseThrow(() -> new RuntimeException("Resenje ne postoji"));
        return studentPolazeZadatak(resenje.getZadatak().getId());
    }

    public boolean studentuPripadaResenje(Long resenjeId) {
        Resenje resenje = resenjeRepository.findById(resenjeId)
                .orElseThrow(() -> new RuntimeException("Resenje ne postoji"));
        return resenje.getStudentTermin().getStudent().getId().equals(ulogovaniId());
    }
}
