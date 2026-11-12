package com.example.lmsserver.kontroleri;

import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.klase.Zadatak;
import com.example.lmsserver.kontroleri.request.TerminRequest;
import com.example.lmsserver.kontroleri.response.TerminResponse;
import com.example.lmsserver.servisi.TerminService;
import com.example.lmsserver.servisi.ZadatakService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public TerminResponse addNewTermin(@RequestBody @Valid TerminRequest terminRequest)
    {
        return terminService.save(terminRequest);
    }

    @GetMapping(path = "/{id}")
    public Termin getTerminById(@PathVariable int id)
    {
        return terminService.findById(id);
    }

    @GetMapping(path = "/{id}/zadaci")
    public List<Zadatak> getZadaciByTerminId(@PathVariable int id)
    {
        return zadatakService.getZadaciForTermin(id);
    }

    @GetMapping(path = "/u-toku")
    public List<Termin> getTerminiUToku()
    {
        return terminService.terminiUToku(LocalDateTime.now());
    }

    @GetMapping(path = "/predmet/{id}")
    public List<Termin> getTerminiZaPredmet(@PathVariable int id)
    {
        return terminService.terminiZaPredmet(id);
    }

    @PutMapping("/{id}")
    public TerminResponse updateTermin(@PathVariable Long id, @RequestBody @Valid TerminRequest request)
    {
        return terminService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTermin(@PathVariable Long id)
    {
        terminService.delete(id);
    }
}
