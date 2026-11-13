package com.example.lmsserver.kontroleri;


import com.example.lmsserver.kontroleri.request.TerminRequest;
import com.example.lmsserver.kontroleri.response.TerminResponse;
import com.example.lmsserver.kontroleri.response.ZadatakResponse;
import com.example.lmsserver.servisi.TerminService;
import com.example.lmsserver.servisi.ZadatakService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/termin")
public class TerminController
{
    final TerminService terminService;
    final ZadatakService zadatakService;

    //region read
    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('PREDAVAC')")
    public TerminResponse getTerminById(@PathVariable int id)
    {
        return terminService.findTerminById(id);
    }

    @GetMapping(path = "/u-toku")
    @PreAuthorize("hasRole('PREDAVAC')")
    public List<TerminResponse> getTerminiUToku()
    {
        return terminService.terminiUToku(LocalDateTime.now());
    }

    @GetMapping(path = "/predmet/{id}")
    @PreAuthorize("hasRole('PREDAVAC')")
    public List<TerminResponse> getTerminiZaPredmet(@PathVariable int id)
    {
        return terminService.terminiZaPredmet(id);
    }
    //endregion

    @GetMapping(path = "/{id}/zadaci")
    @PreAuthorize("hasRole('PREDAVAC') or hasRole('STUDENT')")
    public List<ZadatakResponse> getZadaciByTerminId(@PathVariable int id)
    {
        return zadatakService.getZadaciForTermin(id);
    }

    @PostMapping(path = "/add")
    @PreAuthorize("hasRole('PREDAVAC')")
    @ResponseStatus(HttpStatus.CREATED)
    public TerminResponse addNewTermin(@RequestBody @Valid TerminRequest terminRequest)
    {
        return terminService.save(terminRequest);
    }

    @PutMapping("update/{id}")
    public TerminResponse updateTermin(@PathVariable Long id, @RequestBody @Valid TerminRequest request)
    {
        return terminService.update(id, request);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTermin(@PathVariable Long id)
    {
        terminService.delete(id);
    }
}
