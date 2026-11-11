package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.request.UlazakRequest;
import com.example.lmsserver.kontroleri.response.UlazakResponse;
import com.example.lmsserver.servisi.PolaganjeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/polaganje")
public class PolaganjeController {
    final PolaganjeService polaganjeService;

    @PostMapping(path = "/ulazak")
    public UlazakResponse udjiUTermin(@RequestBody @Valid UlazakRequest ulazakRequest)
    {
        return polaganjeService.ulazak(ulazakRequest);
    }
}
