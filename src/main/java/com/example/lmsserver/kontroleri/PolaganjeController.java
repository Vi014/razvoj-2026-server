package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.request.IzlazakRequest;
import com.example.lmsserver.kontroleri.request.UlazakRequest;
import com.example.lmsserver.kontroleri.response.IzlazakResponse;
import com.example.lmsserver.kontroleri.response.UlazakResponse;
import com.example.lmsserver.servisi.PolaganjeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/polaganje")
public class PolaganjeController
{
    final PolaganjeService polaganjeService;

    @PostMapping(path = "/ulazak")
    public UlazakResponse udjiUTermin(@RequestBody @Valid UlazakRequest ulazakRequest)
    {
        return polaganjeService.ulazak(ulazakRequest);
    }

    @PutMapping("/izlazak")
    public IzlazakResponse izadjiIzTermina(@RequestBody @Valid IzlazakRequest request)
    {
        return polaganjeService.izlazak(request);
    }
}
