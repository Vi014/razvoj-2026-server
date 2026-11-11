package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.ConflictException;
import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.Predmet;
import com.example.lmsserver.klase.ProveraZnanja;
import com.example.lmsserver.klase.Student;
import com.example.lmsserver.kontroleri.request.ProveraZnanjaRequest;
import com.example.lmsserver.repozitorijumi.PredmetRepository;
import com.example.lmsserver.repozitorijumi.ProveraZnanjaRepository;
import com.example.lmsserver.util.Konverteri;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProveraZnanjaService
{
    private final ProveraZnanjaRepository proveraZnanjaRepository;
    private final PredmetRepository predmetRepository;

    @Transactional
    public ProveraZnanja save(ProveraZnanjaRequest request) {
        Predmet predmet = predmetRepository.findById(request.getPredmetId())
                .orElseThrow(() -> new ResourceNotFoundException("Predmet sa id " + request.getPredmetId() + " ne postoji."));

        String naziv = request.getNaziv().trim();
        if (proveraZnanjaRepository.existsByPredmetIdAndNazivIgnoreCase(predmet.getId(), naziv))
        {
            throw new ConflictException("Provera '" + naziv + "' vec postoji za predmet " + predmet.getSifra() + ".");
        }

        ProveraZnanja provera = Konverteri.ProveraZnanjaRequestToProveraZnanja(request);
        provera.setPredmet(predmet);
        return proveraZnanjaRepository.save(provera);
    }


    public Page<ProveraZnanja> findAll(int page, int size) {
        return proveraZnanjaRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }
}
