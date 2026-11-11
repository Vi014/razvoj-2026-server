package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.request.ProveraZnanjaRequest;
import com.example.lmsserver.kontroleri.response.PageResponse;
import com.example.lmsserver.kontroleri.response.ProveraZnanjaResponse;
import com.example.lmsserver.servisi.ProveraZnanjaService;
import com.example.lmsserver.util.Konverteri;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/provera")
public class ProveraZnanjaController {
    final ProveraZnanjaService proveraZnanjaService;

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addNewProvera(@RequestBody @Valid ProveraZnanjaRequest proveraZnanjaRequest)
    {
        return proveraZnanjaService.save(proveraZnanjaRequest).getId();
    }

    @GetMapping(path = "/all/paginated")
    public PageResponse<ProveraZnanjaResponse> getAllProveraZnanjaPodaciPaginated(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size)
    {
        return PageResponse.of(proveraZnanjaService.findAll(page, size), Konverteri::ProveraZnanjaToProveraZnanjaResponse);
    }
}
