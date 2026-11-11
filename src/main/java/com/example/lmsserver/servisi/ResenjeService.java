package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.BadRequestException;
import com.example.lmsserver.exceptions.ConflictException;
import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.Resenje;
import com.example.lmsserver.kontroleri.request.PoeniRequest;
import com.example.lmsserver.kontroleri.response.ResenjeResponse;
import com.example.lmsserver.repozitorijumi.ResenjeRepository;
import com.example.lmsserver.util.Konverteri;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResenjeService
{
    private final ResenjeRepository resenjeRepository;

    @Transactional
    public ResenjeResponse upisiPoene(Long resenjeId, PoeniRequest request)
    {
        Resenje resenje = resenjeRepository.findById(resenjeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resenje sa id " + resenjeId + " ne postoji."));

        // TODO (kada se uvede autentifikacija): prijavljeni predavac mora da drzi predmet provere (ADMIN sme sve)

        if (resenje.getPredao() == null)
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
        return Konverteri.ResenjeToResenjeResponse(resenjeRepository.save(resenje));
    }
}
