package com.example.lmsserver.servisi;

import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.klase.Zadatak;
import com.example.lmsserver.repozitorijumi.TerminRepository;
import com.example.lmsserver.repozitorijumi.ZadatakRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Transactional
public class PristupService {
    //region pomocne metode
    private Jwt ulogovaniJwt() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getPrincipal() instanceof Jwt jwt ? jwt : null;
    }

    private Long ulogovaniId() {
        Jwt jwt = ulogovaniJwt();
        if (jwt == null) return null;
        Object id = jwt.getClaim("id");
        return id instanceof Number n ? n.longValue() : null;
    }
    //endregion

    //region repozitorijumi
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

    /* public boolean jePredavacZaPredmet(Long predavacId, Long predmetId) {

    } */
}
