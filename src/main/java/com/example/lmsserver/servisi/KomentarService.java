package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.BadRequestException;
import com.example.lmsserver.exceptions.ConflictException;
import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.Komentar;
import com.example.lmsserver.klase.Predavac;
import com.example.lmsserver.klase.Resenje;
import com.example.lmsserver.kontroleri.request.KomentarRequest;
import com.example.lmsserver.kontroleri.request.KomentarUpdateRequest;
import com.example.lmsserver.kontroleri.response.KomentarResponse;
import com.example.lmsserver.repozitorijumi.KomentarRepository;
import com.example.lmsserver.repozitorijumi.PredavacRepository;
import com.example.lmsserver.repozitorijumi.ResenjeRepository;
import com.example.lmsserver.util.Konverteri;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KomentarService
{
    private final KomentarRepository komentarRepository;
    private final ResenjeRepository resenjeRepository;
    private final PredavacRepository predavacRepository;

    @Transactional
    public KomentarResponse save(KomentarRequest request)
    {
        Resenje resenje = resenjeRepository.findById(request.getResenjeId())
                .orElseThrow(() -> new ResourceNotFoundException("Resenje sa id " + request.getResenjeId() + " ne postoji."));

        Predavac predavac = predavacRepository.findById(request.getPredavacId())
                .orElseThrow(() -> new ResourceNotFoundException("Predavac sa id " + request.getPredavacId() + " ne postoji."));

        if (!predavac.isAktivan())
        {
            throw new BadRequestException("Nalog predavaca nije aktivan.");
        }

        if (resenje.getPredao() == null)
        {
            throw new ConflictException("Nije moguće komentarisati rešenje koje još nije predato.");
        }

        Komentar komentar = new Komentar();
        komentar.setResenje(resenje);
        komentar.setPredavac(predavac);
        komentar.setTekst(request.getTekst().trim());
        return Konverteri.getInstance().KomentarToKomentarResponse(komentarRepository.save(komentar));
    }

    @Transactional
    public KomentarResponse update(Long komentarId, KomentarUpdateRequest request)
    {
        Komentar komentar = komentarRepository.findById(komentarId)
                .orElseThrow(() -> new ResourceNotFoundException("Komentar sa id " + komentarId + " ne postoji."));

        if (!komentar.getPredavac().getId().equals(request.getPredavacId()))
        {
            throw new ConflictException("Nemate dozvolu za izmenu ovog komentara.");
        }

        komentar.setTekst(request.getTekst().trim());
        return Konverteri.getInstance().KomentarToKomentarResponse(komentarRepository.save(komentar));
    }

    @Transactional
    public void delete(Long komentarId, Long predavacId)
    {
        Komentar komentar = komentarRepository.findById(komentarId)
                .orElseThrow(() -> new ResourceNotFoundException("Komentar sa id " + komentarId + " ne postoji."));

        if (!komentar.getPredavac().getId().equals(predavacId))
        {
            throw new ConflictException("Nemate dozvolu za brisanje ovog komentara.");
        }

        komentarRepository.delete(komentar);
    }

    @Transactional
    public List<KomentarResponse> findByResenje(Long resenjeId)
    {
        if (!resenjeRepository.existsById(resenjeId))
        {
            throw new ResourceNotFoundException("Resenje sa id " + resenjeId + " ne postoji.");
        }
        return komentarRepository.findByResenjeIdOrderByIdAsc(resenjeId).stream().map(Konverteri.getInstance()::KomentarToKomentarResponse).toList();
    }
}
