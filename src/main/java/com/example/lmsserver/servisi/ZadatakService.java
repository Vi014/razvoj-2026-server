package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.ConflictException;
import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.klase.Zadatak;
import com.example.lmsserver.kontroleri.request.ZadatakRequest;
import com.example.lmsserver.kontroleri.response.ZadatakResponse;
import com.example.lmsserver.repozitorijumi.TerminRepository;
import com.example.lmsserver.repozitorijumi.ZadatakRepository;
import com.example.lmsserver.util.Konverteri;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ZadatakService
{
    private final ZadatakRepository zadatakRepository;
    private final TerminRepository terminRepository;

    @Transactional
    public ZadatakResponse save(ZadatakRequest request)
    {
        Termin termin = terminRepository.findById(request.getTerminId())
                .orElseThrow(() -> new ResourceNotFoundException("Termin sa id " + request.getTerminId() + " ne postoji."));

        // zadaci se ne smeju menjati kada su studenti vec poceli da rade
        if (!termin.getPocetak().isAfter(LocalDateTime.now()))
        {
            throw new ConflictException("Termin je vec poceo, zadaci se vise ne mogu dodavati.");
        }

        if (zadatakRepository.existsByTerminIdAndRedniBroj(termin.getId(), request.getRedniBroj().shortValue()))
        {
            throw new ConflictException("Zadatak sa rednim brojem " + request.getRedniBroj() + " vec postoji u ovom terminu.");
        }

        // TODO (kada se uvede autentifikacija): prijavljeni predavac mora da drzi predmet provere (ADMIN sme sve)

        Zadatak zadatak = Konverteri.ZadatakRequestToZadatak(request);
        zadatak.setTermin(termin);
        return Konverteri.ZadatakToZadatakResponse(zadatakRepository.save(zadatak));
    }
}
