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

    @GetMapping(path = "{id}")
    public ZadatakResponse getZadatakById(@PathVariable long id)
    {
        return zadatakService.getZadatakId(id);
    }

    @PutMapping("update/{id}")
    public ZadatakResponse updateZadatak(@PathVariable Long id, @RequestBody @Valid ZadatakRequest request)
    {
        return zadatakService.update(id, request);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZadatak(@PathVariable Long id)
    {
        zadatakService.delete(id);
    }

}
