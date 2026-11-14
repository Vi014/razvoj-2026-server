package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.response.IzlazakResponse;
import com.example.lmsserver.kontroleri.response.UlazakResponse;
import com.example.lmsserver.servisi.PolaganjeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/polaganje")
public class PolaganjeController
{
    final PolaganjeService polaganjeService;

    @PostMapping(path = "/ulazak")
    //@PreAuthorize("hasRole('STUDENT')")
    @PreAuthorize("@pristupService.studentPolazeTermin(#id)")
    public UlazakResponse udjiUTermin(@RequestBody @Valid Long id)
    {
        return polaganjeService.ulazak(id);
    }

    @PutMapping("/izlazak")
    //@PreAuthorize("hasRole('STUDENT')")
    @PreAuthorize("@pristupService.studentPolazeTermin(#id)")
    public IzlazakResponse izadjiIzTermina(@RequestBody @Valid Long id)
    {
        return polaganjeService.izlazak(id);
    }
}
