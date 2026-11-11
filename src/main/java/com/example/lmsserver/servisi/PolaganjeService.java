package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.ConflictException;
import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.StudentTermin;
import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.klase.enumi.StatusPolaganja;
import com.example.lmsserver.kontroleri.request.UlazakRequest;
import com.example.lmsserver.kontroleri.response.UlazakResponse;
import com.example.lmsserver.kontroleri.response.ZadatakResponse;
import com.example.lmsserver.repozitorijumi.StudentTerminRepository;
import com.example.lmsserver.repozitorijumi.ZadatakRepository;
import com.example.lmsserver.util.Konverteri;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PolaganjeService
{
    private final StudentTerminRepository studentTerminRepository;
    private final ZadatakRepository zadatakRepository;

    @Transactional
    public UlazakResponse ulazak(UlazakRequest request)
    {
        StudentTermin prijava = studentTerminRepository.findByTerminIdAndStudentId(request.getTerminId(), request.getStudentId());
        Termin termin = prijava.getTermin();

        // na sekundu tacno: toliko je dovoljno za monitoring, a vrednost iz odgovora je ista kao ona u bazi
        LocalDateTime sada = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        if (sada.isBefore(termin.getPocetak()))
        {
            throw new ConflictException("Termin jos nije poceo.");
        }
        if (sada.isAfter(termin.getKraj()))
        {
            throw new ConflictException("Termin je zavrsen.");
        }

        switch (prijava.getStatus())
        {
            case PRIJAVLJEN ->
            {
                prijava.setStatus(StatusPolaganja.IZASAO);
                prijava.setUlazak(sada);
            }
            case IZASAO -> { /* ponovni ulazak: stanje ostaje isto */ }
            case PREDAO -> throw new ConflictException("Student je vec predao polaganje.");
            case NIJE_IZASAO -> throw new ConflictException("Student je oznacen kao da nije izasao na termin.");
        }

        List<ZadatakResponse> zadaci = zadatakRepository.findByTerminIdOrderByRedniBrojAsc(termin.getId()).stream()
                .map(Konverteri::ZadatakToZadatakResponse)
                .toList();

        UlazakResponse response = new UlazakResponse();
        response.setTerminId(termin.getId());
        response.setProveraNaziv(termin.getProvera().getNaziv());
        response.setPredmetNaziv(termin.getProvera().getPredmet().getNaziv());
        response.setStatus(prijava.getStatus());
        response.setUlazak(prijava.getUlazak());
        response.setKrajTermina(termin.getKraj());
        response.setPreostaloSekundi(Duration.between(sada, termin.getKraj()).getSeconds());
        response.setZadaci(zadaci);
        return response;
    }
}
