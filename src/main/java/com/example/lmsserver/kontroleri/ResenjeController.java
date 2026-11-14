package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.request.PoeniRequest;
import com.example.lmsserver.kontroleri.response.PoeniResponse;
import com.example.lmsserver.kontroleri.response.ResenjeResponse;
import com.example.lmsserver.servisi.ResenjeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/resenje")
public class ResenjeController
{
    final ResenjeService resenjeService;

    @PostMapping(path = "/predaj")
    @PreAuthorize("hasRole('STUDENT') and @pristupService.studentPolazeZadatak(#zadatakId)")
    public ResenjeResponse predajResenje(@RequestParam Long zadatakId, @RequestParam String resenjeUrl)
    {
        return resenjeService.predajResenje(zadatakId, resenjeUrl);
    }

    @PostMapping(path = "/azuriraj")
    @PreAuthorize("hasRole('STUDENT') and @pristupService.studentPolazeResenje(#resenjeId)")
    public ResenjeResponse azurirajResenje(@RequestParam Long resenjeId, @RequestParam String resenjeUrl)
    {
        return resenjeService.azurirajResenje(resenjeId, resenjeUrl);
    }

    @PutMapping(path = "/{resenjeId}/poeni")
    @PreAuthorize("hasRole('PREDAVAC') and @pristupService.jePredavacZaResenje(#resenjeId)")
    public ResenjeResponse upisiPoene(@PathVariable Long resenjeId, @RequestBody @Valid PoeniRequest poeniRequest)
    {
        return resenjeService.upisiPoene(resenjeId, poeniRequest);
    }

    @PutMapping(path = "/{resenjeId}/getPoeni")
    @PreAuthorize("@pristupService.jePredavacZaResenje(#resenjeId) or @pristupService.studentuPripadaResenje(#resenjeId)")
    public PoeniResponse getPoeni(@PathVariable Long resenjeId)
    {
        return resenjeService.getPoeni(resenjeId);
    }
}
