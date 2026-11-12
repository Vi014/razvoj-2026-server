package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.BadRequestException;
import com.example.lmsserver.exceptions.ConflictException;
import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.Resenje;
import com.example.lmsserver.klase.StudentTermin;
import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.klase.enumi.StatusPolaganja;
import com.example.lmsserver.kontroleri.request.IzlazakRequest;
import com.example.lmsserver.kontroleri.request.UlazakRequest;
import com.example.lmsserver.kontroleri.response.IzlazakResponse;
import com.example.lmsserver.kontroleri.response.UlazakResponse;
import com.example.lmsserver.kontroleri.response.ZadatakResponse;
import com.example.lmsserver.repozitorijumi.ResenjeRepository;
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
    private final ResenjeRepository resenjeRepository;

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
                .map(Konverteri.getInstance()::ZadatakToZadatakResponse)
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

    @Transactional
    public IzlazakResponse izlazak(IzlazakRequest request)
    {
        StudentTermin prijava = studentTerminRepository.findByTerminIdAndStudentId(request.getTerminId(), request.getStudentId());
        if (prijava == null)
        {
            throw new ResourceNotFoundException("Student nije prijavljen na ovaj termin.");
        }

        // Student mora prethodno da je ušao u termin
        if (prijava.getUlazak() == null)
        {
            throw new ConflictException("Student nije započeo polaganje, pa ne može da preda rad.");
        }

        if (prijava.getStatus() == StatusPolaganja.PREDAO)
        {
            throw new ConflictException("Student je već predao polaganje.");
        }

        if (prijava.getStatus() != StatusPolaganja.IZASAO)
        {
            throw new ConflictException("Student nema status koji dozvoljava predaju polaganja.");
        }

        LocalDateTime sada = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        // Pronalazimo sva rešenja studenta za ovaj termin
        List<Resenje> resenja = resenjeRepository.findByStudentTerminId(prijava.getId());

        // Predajemo sva postojeća rešenja
        for (Resenje resenje : resenja)
        {
            if (resenje.getPredao() == null)
            {
                resenje.setPredao(sada);
                try
                {
                    resenje.validiraj();
                }
                catch (IllegalStateException e)
                {
                    throw new BadRequestException(e.getMessage());
                }
            }
        }

        // Evidentiramo izlazak i predaju polaganja
        prijava.setPredaja(sada);
        prijava.setStatus(StatusPolaganja.PREDAO);
        resenjeRepository.saveAll(resenja);
        studentTerminRepository.save(prijava);

        // Formiranje odgovora
        IzlazakResponse response = new IzlazakResponse();
        response.setTerminId(prijava.getTermin().getId());
        response.setStudentId(prijava.getStudent().getId());
        response.setStatus(prijava.getStatus());
        response.setUlazak(prijava.getUlazak());
        response.setPredaja(prijava.getPredaja());
        response.setResenja(resenja.stream().map(Konverteri.getInstance()::ResenjeToResenjeResponse).toList());
        return response;
    }
}
