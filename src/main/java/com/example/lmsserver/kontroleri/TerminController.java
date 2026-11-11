package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.request.TerminRequest;
import com.example.lmsserver.kontroleri.response.TerminResponse;
import com.example.lmsserver.servisi.TerminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/termin")
public class TerminController
{
    final TerminService terminService;

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public TerminResponse addNewTermin(@RequestBody @Valid TerminRequest terminRequest)
    {
        return terminService.save(terminRequest);
    }
}
