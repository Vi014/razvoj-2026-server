package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.BadRequestException;
import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.Predavac;
import com.example.lmsserver.klase.ProveraZnanja;
import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.kontroleri.request.TerminRequest;
import com.example.lmsserver.kontroleri.response.TerminResponse;
import com.example.lmsserver.repozitorijumi.DrziPredmetRepository;
import com.example.lmsserver.repozitorijumi.PredavacRepository;
import com.example.lmsserver.repozitorijumi.ProveraZnanjaRepository;
import com.example.lmsserver.repozitorijumi.TerminRepository;
import com.example.lmsserver.util.Konverteri;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TerminService
{
    private final TerminRepository terminRepository;
    private final ProveraZnanjaRepository proveraZnanjaRepository;
    private final PredavacRepository predavacRepository;
    private final DrziPredmetRepository drziPredmetRepository;

    @Transactional
    public TerminResponse save(TerminRequest request)
    {
        ProveraZnanja provera = proveraZnanjaRepository.findById(request.getProveraId())
                .orElseThrow(() -> new ResourceNotFoundException("Provera znanja sa id " + request.getProveraId() + " ne postoji."));

        if (!request.getKraj().isAfter(request.getPocetak()))
        {
            throw new BadRequestException("Kraj termina mora biti posle njegovog pocetka.");
        }
        if (!request.getPocetak().isAfter(LocalDateTime.now()))
        {
            throw new BadRequestException("Termin mora poceti u buducnosti.");
        }

        Predavac predavac = null;
        if (request.getPredavacId() != null)
        {
            predavac = predavacRepository.findById(request.getPredavacId())
                    .orElseThrow(() -> new ResourceNotFoundException("Predavac sa id " + request.getPredavacId() + " ne postoji."));
            if (!predavac.isAktivan())
            {
                throw new BadRequestException("Nalog predavaca nije aktivan.");
            }
            if (!drziPredmetRepository.drziPredmet(predavac.getId(), provera.getPredmet().getId()))
            {
                throw new BadRequestException("Predavac ne drzi predmet na kome se odrzava ova provera.");
            }
        }

        // TODO (kada se uvede autentifikacija): prijavljeni predavac mora da drzi predmet provere

        Termin termin = Konverteri.TerminRequestToTermin(request);
        termin.setProvera(provera);
        termin.setPredavac(predavac);
        return Konverteri.TerminToTerminResponse(terminRepository.save(termin));
    }
}
