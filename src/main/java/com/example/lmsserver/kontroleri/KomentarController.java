package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.request.KomentarRequest;
import com.example.lmsserver.kontroleri.request.KomentarUpdateRequest;
import com.example.lmsserver.kontroleri.response.KomentarResponse;
import com.example.lmsserver.servisi.KomentarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/komentar")
public class KomentarController
{
    private final KomentarService komentarService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public KomentarResponse addKomentar(@RequestBody @Valid KomentarRequest request)
    {
        return komentarService.save(request);
    }

    @PutMapping("update/{id}")
    public KomentarResponse updateKomentar(@PathVariable Long id, @RequestBody @Valid KomentarUpdateRequest request)
    {
        return komentarService.update(id, request);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKomentar(@PathVariable Long id, @RequestParam Long predavacId)
    {
        komentarService.delete(id, predavacId);
    }

    @GetMapping("/resenje/{resenjeId}")
    public List<KomentarResponse> getKomentariZaResenje(@PathVariable Long resenjeId)
    {
        return komentarService.findByResenje(resenjeId);
    }
}
