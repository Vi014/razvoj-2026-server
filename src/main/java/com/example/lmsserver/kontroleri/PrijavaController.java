package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.request.PrijavaRequest;
import com.example.lmsserver.kontroleri.response.PrijavaResponse;
import com.example.lmsserver.servisi.PrijavaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/prijava")
public class PrijavaController {
    final PrijavaService prijavaService;

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public PrijavaResponse prijaviStudenta(@RequestBody @Valid PrijavaRequest prijavaRequest)
    {
        return prijavaService.prijavi(prijavaRequest);
    }
}
