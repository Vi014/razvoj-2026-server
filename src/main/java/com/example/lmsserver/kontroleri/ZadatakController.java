package com.example.lmsserver.kontroleri;


import com.example.lmsserver.kontroleri.request.ZadatakRequest;
import com.example.lmsserver.kontroleri.response.ZadatakResponse;
import com.example.lmsserver.servisi.PristupService;
import com.example.lmsserver.servisi.ZadatakService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/zadatak")
public class ZadatakController {
    final ZadatakService zadatakService;
    final PristupService pristupService;

    @PostMapping(path = "/add")
    @PreAuthorize("hasRole('PREDAVAC') and @pristupService.jePredavacZaTermin(#zadatakRequest.terminId)")
    @ResponseStatus(HttpStatus.CREATED)
    public ZadatakResponse addNewZadatak(@RequestBody @Valid ZadatakRequest zadatakRequest)
    {
        return zadatakService.save(zadatakRequest);
    }

    @GetMapping(path = "{id}")
    public ZadatakResponse getZadatakById(@PathVariable long id)
    {
        return zadatakService.getZadatakId(id);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('PREDAVAC') and @pristupService.jePredavacZaZadatak(#id)")
    public ZadatakResponse updateZadatak(@PathVariable Long id, @RequestBody @Valid ZadatakRequest request)
    {
        return zadatakService.update(id, request);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('PREDAVAC') and @pristupService.jePredavacZaZadatak(#id)")
    // @PreAuthorize("hasRole('PREDAVAC')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZadatak(@PathVariable Long id)
    {
        zadatakService.delete(id);
    }

}
