package com.example.lmsserver.kontroleri;

import com.example.lmsserver.klase.ProveraZnanja;
import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.kontroleri.request.ProveraZnanjaRequest;
import com.example.lmsserver.kontroleri.response.PageResponse;
import com.example.lmsserver.kontroleri.response.ProveraZnanjaResponse;
import com.example.lmsserver.servisi.ProveraZnanjaService;
import com.example.lmsserver.servisi.TerminService;
import com.example.lmsserver.util.Konverteri;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/provera")
public class ProveraZnanjaController
{
    final ProveraZnanjaService proveraZnanjaService;
    final TerminService terminService;

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

    @GetMapping(path = "/{id}")
    public ProveraZnanja getProveraZnanjaById(@PathVariable Long id)
    {
        return proveraZnanjaService.findById(id);
    }

    @GetMapping(path = "/{id}/termini")
    public List<Termin> getTerminiForProveraZnanja(@PathVariable Long id)
    {
        return terminService.terminiZaProveru(id);
    }

    @PutMapping("/{id}")
    public ProveraZnanjaResponse updateProveraZnanja(@PathVariable Long id, @RequestBody @Valid ProveraZnanjaRequest request)
    {
        return Konverteri.ProveraZnanjaToProveraZnanjaResponse(proveraZnanjaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProveraZnanja(@PathVariable Long id)
    {
        proveraZnanjaService.delete(id);
    }
}
