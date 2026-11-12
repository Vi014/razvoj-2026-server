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
    private final TerminService terminService;

    @Transactional
    public ProveraZnanja save(ProveraZnanjaRequest request)
    {
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

    @Transactional
    public ProveraZnanja update(Long id, ProveraZnanjaRequest request) {

        ProveraZnanja provera = findById(id);

        if (terminService.proveraJePocela(id))
        {
            throw new ConflictException("Nije moguće izmeniti proveru znanja jer je neki od njenih termina već počeo.");
        }

        Predmet predmet = predmetRepository.findById(request.getPredmetId())
                .orElseThrow(() -> new ResourceNotFoundException("Predmet sa id " + request.getPredmetId() + " ne postoji."));

        String naziv = request.getNaziv().trim();

        if (proveraZnanjaRepository.existsByPredmetIdAndNazivIgnoreCaseAndIdNot(predmet.getId(), naziv, id))
        {
            throw new ConflictException("Provera '" + naziv + "' već postoji za predmet " + predmet.getSifra() + ".");
        }

        provera.setPredmet(predmet);
        provera.setNaziv(naziv);
        provera.setTip(request.getTip());
        provera.setPragPoena(request.getPragPoena());
        return proveraZnanjaRepository.save(provera);
    }

    @Transactional
    public void delete(Long id)
    {

        ProveraZnanja provera = findById(id);

        if (terminService.proveraJePocela(id))
        {
            throw new ConflictException("Nije moguće obrisati proveru znanja jer je neki od njenih termina već počeo.");
        }

        proveraZnanjaRepository.delete(provera);
    }


    public Page<ProveraZnanja> findAll(int page, int size)
    {
        return proveraZnanjaRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    public ProveraZnanja findById(long id)
    {
        return proveraZnanjaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Provera znanja sa id-jem '" + id + "' ne postoji."));
    }


}
