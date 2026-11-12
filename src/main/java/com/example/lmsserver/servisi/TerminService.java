package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.BadRequestException;
import com.example.lmsserver.exceptions.ConflictException;
import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.Predavac;
import com.example.lmsserver.klase.ProveraZnanja;
import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.klase.Zadatak;
import com.example.lmsserver.kontroleri.request.TerminRequest;
import com.example.lmsserver.kontroleri.response.TerminResponse;
import com.example.lmsserver.repozitorijumi.*;
import com.example.lmsserver.util.Konverteri;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TerminService
{
    private final TerminRepository terminRepository;
    private final ProveraZnanjaRepository proveraZnanjaRepository;
    private final PredavacRepository predavacRepository;
    private final DrziPredmetRepository drziPredmetRepository;
    private final StudentTerminRepository studentTerminRepository;

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
    @Transactional
    public TerminResponse update(Long id, TerminRequest request) {

        Termin termin = findById(id);

        terminNijePoceo(termin);

        if (!request.getKraj().isAfter(request.getPocetak()))
        {
            throw new BadRequestException("Kraj termina mora biti posle njegovog početka.");
        }

        if (!request.getPocetak().isAfter(LocalDateTime.now()))
        {
            throw new BadRequestException("Termin mora početi u budućnosti.");
        }

        ProveraZnanja provera = proveraZnanjaRepository.findById(request.getProveraId())
                .orElseThrow(() -> new ResourceNotFoundException("Provera znanja sa id " + request.getProveraId() + " ne postoji."));

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

        termin.setProvera(provera);
        termin.setPredavac(predavac);
        termin.setDatum(request.getDatum());
        termin.setPocetak(request.getPocetak());
        termin.setKraj(request.getKraj());
        return Konverteri.TerminToTerminResponse(terminRepository.save(termin));
    }

    @Transactional
    public void delete(Long id)
    {
        Termin termin = findById(id);
        terminNijePoceo(termin);
        // Brisanje prijava studenata
        studentTerminRepository.deleteAllByTerminId(id);
        // Brisanje termina
        terminRepository.delete(termin);
    }


    public boolean proveraJePocela(Long proveraId)
    {
        return terminRepository.existsByProveraIdAndPocetakLessThanEqual(proveraId, LocalDateTime.now());
    }

    private void terminNijePoceo(Termin termin)
    {
        if (!termin.getPocetak().isAfter(LocalDateTime.now()))
        {
            throw new ConflictException("Nije moguće izmeniti ili obrisati termin koji je već počeo.");
        }
    }

    public Termin findById(long id)
    {
        return terminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Termin sa id-jem '" + id + "' ne postoji."));
    }

    public List<Termin> terminiUToku(LocalDateTime time)
    {
        return terminRepository.getTerminiUToku(time);
    }

    public List<Termin> terminiZaPredmet(long predmetId)
    {
        return terminRepository.getTerminiPredmeta(predmetId);
    }

    public List<Termin> terminiZaProveru(long predmetId)
    {
        return terminRepository.getTerminiProvere(predmetId);
    }
}
