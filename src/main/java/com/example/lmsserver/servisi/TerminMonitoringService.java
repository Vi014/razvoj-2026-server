package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.BadRequestException;
import com.example.lmsserver.klase.Resenje;
import com.example.lmsserver.klase.Student;
import com.example.lmsserver.klase.StudentTermin;
import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.kontroleri.response.monitoring.ResenjeMonitoringResponse;
import com.example.lmsserver.kontroleri.response.monitoring.StudentMonitoringResponse;
import com.example.lmsserver.kontroleri.response.monitoring.TerminMonitoringResponse;
import com.example.lmsserver.kontroleri.response.monitoring.TerminStatistikaResponse;
import com.example.lmsserver.repozitorijumi.ResenjeRepository;
import com.example.lmsserver.repozitorijumi.StudentTerminRepository;
import com.example.lmsserver.repozitorijumi.TerminRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TerminMonitoringService
{
    private final TerminRepository terminRepository;
    private final StudentTerminRepository studentTerminRepository;
    private final ResenjeRepository resenjeRepository;

    @Transactional
    public TerminMonitoringResponse getMonitoring(Long terminId)
    {

        Termin termin = terminRepository.findById(terminId)
                .orElseThrow(() -> new BadRequestException("Termin nije pronađen."));

        LocalDateTime sada = LocalDateTime.now();

        boolean terminUToku = !sada.isBefore(termin.getPocetak()) && !sada.isAfter(termin.getKraj());
        boolean terminZavrsen = sada.isAfter(termin.getKraj());
        List<StudentTermin> studenti = studentTerminRepository.findAllForMonitoring(terminId);
        long brojPredalo = studenti.stream().filter(st -> st.getPredaja() != null).count();
        boolean sviPredali = !studenti.isEmpty() && brojPredalo == studenti.size();
        boolean prikaziResenja = terminZavrsen && sviPredali;

        List<StudentMonitoringResponse> studentResponses = studenti.stream()
                        .map(st -> mapirajStudenta(st, sada, prikaziResenja))
                        .toList();

        TerminMonitoringResponse response = new TerminMonitoringResponse();
        response.setTerminId(terminId);
        response.setPocetak(termin.getPocetak());
        response.setKraj(termin.getKraj());
        response.setTerminUToku(terminUToku);
        response.setTerminZavrsen(terminZavrsen);
        response.setSviPredali(sviPredali);
        response.setStudenti(studentResponses);
        return response;
    }

    @Transactional
    public StudentMonitoringResponse mapirajStudenta(StudentTermin st, LocalDateTime sada, boolean prikaziResenja)
    {

        Student student = st.getStudent();

        List<ResenjeMonitoringResponse> resenja = prikaziResenja ? resenjeRepository
                        .findAllByStudentTerminId(st.getId())
                        .stream()
                        .map(this::mapirajResenje)
                        .toList() : Collections.emptyList();

        StudentMonitoringResponse response = new StudentMonitoringResponse();
        response.setStudentId(student.getId());
        response.setIme(student.getIme());
        response.setPrezime(student.getPrezime());
        response.setBrojIndeksa(student.getBrojIndeksa());
        response.setStatus(st.getStatus());
        response.setUlazak(st.getUlazak());
        response.setPredaja(st.getPredaja());
        response.setTrajanjeSekunde(izracunajTrajanje(st,sada));
        response.setResenja(resenja);
        return response;
    }

    private Long izracunajTrajanje(StudentTermin st, LocalDateTime sada)
    {
        if (st.getUlazak() == null)
        {
            return null;
        }
        LocalDateTime kraj = st.getPredaja() != null ? st.getPredaja() : sada;
        return Math.max(0, Duration.between(st.getUlazak(), kraj).getSeconds());
    }

    @Transactional
    public ResenjeMonitoringResponse mapirajResenje(Resenje r)
    {
        ResenjeMonitoringResponse response = new ResenjeMonitoringResponse();
        response.setResenjeId(r.getId());
        response.setZadatakId(r.getZadatak().getId());
        response.setResenjeUrl(r.getResenjeUrl());
        response.setPoeni(r.getPoeni());
        return response;
    }

    @Transactional
    public TerminStatistikaResponse getStatistika(Long terminId) {

        if (!terminRepository.existsById(terminId))
        {
            throw new BadRequestException("Termin nije pronađen.");
        }

        long ukupno = studentTerminRepository.countByTerminId(terminId);

        long brojUslo = studentTerminRepository.countByTerminIdAndUlazakIsNotNull(terminId);

        long brojPredalo = studentTerminRepository.countByTerminIdAndPredajaIsNotNull(terminId);

        long brojNisuUsli = ukupno - brojUslo;

        TerminStatistikaResponse response = new TerminStatistikaResponse();
        response.setTerminId(terminId);
        response.setBrojPrijavljenih(ukupno);
        response.setBrojUslo(brojUslo);
        response.setBrojPredalo(brojPredalo);
        response.setBrojNisuUsli(brojNisuUsli);
        response.setProcenatUslo(procenat(brojUslo,ukupno));
        response.setProcenatPredalo(procenat(brojPredalo,ukupno));
        response.setProcenatNisuUsli(procenat(brojNisuUsli,ukupno));
        response.setUsliFormat(brojUslo + "/" + ukupno);
        response.setPredaliFormat(brojPredalo + "/" + ukupno);
        return response;
    }

    private double procenat(long broj, long ukupno)
    {

        if (ukupno == 0)
        {
            return 0.0;
        }

        return Math.round((broj * 10000.0) / ukupno) / 100.0;
    }
}
