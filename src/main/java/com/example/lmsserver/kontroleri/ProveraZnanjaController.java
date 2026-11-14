package com.example.lmsserver.kontroleri;


import com.example.lmsserver.kontroleri.request.ProveraZnanjaRequest;
import com.example.lmsserver.kontroleri.response.PageResponse;
import com.example.lmsserver.kontroleri.response.ProveraZnanjaResponse;
import com.example.lmsserver.kontroleri.response.TerminResponse;
import com.example.lmsserver.servisi.ProveraZnanjaService;
import com.example.lmsserver.servisi.TerminService;
import com.example.lmsserver.util.Konverteri;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addNewProvera(@RequestBody @Valid ProveraZnanjaRequest proveraZnanjaRequest)
    {
        return proveraZnanjaService.save(proveraZnanjaRequest).getId();
    }

    @GetMapping(path = "/all/paginated")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PREDAVAC')")
    public PageResponse<ProveraZnanjaResponse> getAllProveraZnanjaPodaciPaginated(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size)
    {
        return PageResponse.of(proveraZnanjaService.findAll(page, size), Konverteri.getInstance()::ProveraZnanjaToProveraZnanjaResponse);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PREDAVAC')")
    public ProveraZnanjaResponse getProveraZnanjaById(@PathVariable Long id)
    {
        return proveraZnanjaService.findProveraById(id);
    }

    @GetMapping(path = "/{id}/termini")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PREDAVAC')")
    public List<TerminResponse> getTerminiForProveraZnanja(@PathVariable Long id)
    {
        return terminService.terminiZaProveru(id);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProveraZnanjaResponse updateProveraZnanja(@PathVariable Long id, @RequestBody @Valid ProveraZnanjaRequest request)
    {
        return Konverteri.getInstance().ProveraZnanjaToProveraZnanjaResponse(proveraZnanjaService.update(id, request));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProveraZnanja(@PathVariable Long id)
    {
        proveraZnanjaService.delete(id);
    }
}
