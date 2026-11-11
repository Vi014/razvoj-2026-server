package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.request.PoeniRequest;
import com.example.lmsserver.kontroleri.response.ResenjeResponse;
import com.example.lmsserver.servisi.ResenjeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/resenje")
public class ResenjeController
{
    final ResenjeService resenjeService;

    @PatchMapping(path = "/{resenjeId}/poeni")
    public ResenjeResponse upisiPoene(@PathVariable Long resenjeId, @RequestBody @Valid PoeniRequest poeniRequest)
    {
        return resenjeService.upisiPoene(resenjeId, poeniRequest);
    }
}
