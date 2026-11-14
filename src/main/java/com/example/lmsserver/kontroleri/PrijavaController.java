package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.request.PrijavaRequest;
import com.example.lmsserver.kontroleri.response.PrijavaResponse;
import com.example.lmsserver.servisi.PrijavaService;
import com.example.lmsserver.servisi.PristupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/prijava")
public class PrijavaController
{
    final PrijavaService prijavaService;
    final PristupService pristupService;

    @PostMapping(path = "/add")
    @PreAuthorize("hasRole('STUDENT')")
    @ResponseStatus(HttpStatus.CREATED)
    public PrijavaResponse prijaviStudenta(@RequestBody @Valid PrijavaRequest prijavaRequest)
    {
        return prijavaService.prijavi(prijavaRequest);
    }

    @PutMapping("/remove")
    @PreAuthorize("hasRole('STUDENT')")
    public PrijavaResponse odjaviStudenta(@RequestBody @Valid PrijavaRequest request)
    {
        return prijavaService.odjavi(request);
    }
}
