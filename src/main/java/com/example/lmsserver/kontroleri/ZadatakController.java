package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.request.ZadatakRequest;
import com.example.lmsserver.kontroleri.response.ZadatakResponse;
import com.example.lmsserver.servisi.ZadatakService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/zadatak")
public class ZadatakController {
    final ZadatakService zadatakService;

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ZadatakResponse addNewZadatak(@RequestBody @Valid ZadatakRequest zadatakRequest)
    {
        return zadatakService.save(zadatakRequest);
    }
}
