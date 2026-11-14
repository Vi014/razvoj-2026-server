package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.BadRequestException;
import com.example.lmsserver.exceptions.ConflictException;
import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.Resenje;
import com.example.lmsserver.klase.StudentTermin;
import com.example.lmsserver.klase.Zadatak;
import com.example.lmsserver.kontroleri.request.PoeniRequest;
import com.example.lmsserver.kontroleri.response.PoeniResponse;
import com.example.lmsserver.kontroleri.response.ResenjeResponse;
import com.example.lmsserver.repozitorijumi.ResenjeRepository;
import com.example.lmsserver.repozitorijumi.StudentTerminRepository;
import com.example.lmsserver.repozitorijumi.ZadatakRepository;
import com.example.lmsserver.util.Konverteri;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResenjeService
{
    private final ResenjeRepository resenjeRepository;
    private final ZadatakRepository zadatakRepository;
    private final StudentTerminRepository studentTerminRepository;
    private final PristupService pristupService;

    @Transactional
    public ResenjeResponse predajResenje(Long zadatakId, String resenjeUrl) {
        Resenje resenje = new Resenje();

        resenje.setResenjeUrl(resenjeUrl);

        Zadatak zadatak = zadatakRepository.findById(zadatakId)
                .orElseThrow(() -> new ResourceNotFoundException("Zadatak sa id " + zadatakId + " ne postoji."));
        resenje.setZadatak(zadatak);

        Long terminId = zadatak.getTermin().getId();
        StudentTermin studentTermin = studentTerminRepository.findByTerminIdAndStudentId(terminId, pristupService.ulogovaniId());
        resenje.setStudentTermin(studentTermin);

        return Konverteri.getInstance().ResenjeToResenjeResponse(resenjeRepository.save(resenje));
    }

    @Transactional
    public ResenjeResponse azurirajResenje(Long resenjeId, String resenjeUrl) {
        Resenje resenje = resenjeRepository.findById(resenjeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resenje sa id " + resenjeId + " ne postoji."));
        resenje.setResenjeUrl(resenjeUrl);
        return Konverteri.getInstance().ResenjeToResenjeResponse(resenjeRepository.save(resenje));
    }

    @Transactional
    public ResenjeResponse upisiPoene(Long resenjeId, PoeniRequest request)
    {
        Resenje resenje = resenjeRepository.findById(resenjeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resenje sa id " + resenjeId + " ne postoji."));

        // TODO (kada se uvede autentifikacija): prijavljeni predavac mora da drzi predmet provere (ADMIN sme sve)

        if (resenje.getStudentTermin().getPredaja() == null)
        {
            throw new ConflictException("Resenje jos nije predato, pa se ne moze ocenjivati.");
        }

        resenje.setPoeni(request.getPoeni());
        try
        {
            resenje.validiraj();  // poeni <= max poena zadatka, zadatak pripada terminu prijave...
        }
        catch (IllegalStateException e)
        {
            // izuzetak ponistava transakciju, pa se izmenjeni poeni nikada ne upisuju u bazu
            throw new BadRequestException(e.getMessage());
        }
        return Konverteri.getInstance().ResenjeToResenjeResponse(resenjeRepository.save(resenje));
    }

    public PoeniResponse getPoeni(Long resenjeId)
    {
        Resenje resenje = resenjeRepository.findById(resenjeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resenje sa id " + resenjeId + " ne postoji."));
        return new PoeniResponse(resenje.getPoeni());
    }
}
