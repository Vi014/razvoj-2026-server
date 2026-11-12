package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.ConflictException;
import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.klase.Zadatak;
import com.example.lmsserver.kontroleri.request.ZadatakRequest;
import com.example.lmsserver.kontroleri.response.ZadatakResponse;
import com.example.lmsserver.repozitorijumi.StudentTerminRepository;
import com.example.lmsserver.repozitorijumi.TerminRepository;
import com.example.lmsserver.repozitorijumi.ZadatakRepository;
import com.example.lmsserver.util.Konverteri;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZadatakService
{
    private final ZadatakRepository zadatakRepository;
    private final TerminRepository terminRepository;
    private final StudentTerminRepository studentTerminRepository;

    @Transactional
    public ZadatakResponse save(ZadatakRequest request)
    {
        Termin termin = terminRepository.findById(request.getTerminId())
                .orElseThrow(() -> new ResourceNotFoundException("Termin sa id " + request.getTerminId() + " ne postoji."));

        // zadaci se ne smeju menjati kada su studenti vec poceli da rade
        if (!termin.getPocetak().isAfter(LocalDateTime.now()))
        {
            throw new ConflictException("Termin je vec poceo, zadaci se vise ne mogu dodavati.");
        }

        if (zadatakRepository.existsByTerminIdAndRedniBroj(termin.getId(), request.getRedniBroj().shortValue()))
        {
            throw new ConflictException("Zadatak sa rednim brojem " + request.getRedniBroj() + " vec postoji u ovom terminu.");
        }

        // TODO (kada se uvede autentifikacija): prijavljeni predavac mora da drzi predmet provere

        Zadatak zadatak = Konverteri.getInstance().ZadatakRequestToZadatak(request);
        zadatak.setTermin(termin);
        return Konverteri.getInstance().ZadatakToZadatakResponse(zadatakRepository.save(zadatak));
    }

    @Transactional
    public ZadatakResponse update(Long id, ZadatakRequest request)
    {

        Zadatak zadatak = getZadatak(id);
        Termin stariTermin = zadatak.getTermin();

        // Provera starog termina
        if (!stariTermin.getPocetak().isAfter(LocalDateTime.now()))
        {
            throw new ConflictException("Nije moguće izmeniti zadatak jer je termin već počeo.");
        }

        proveriDaStudentiNisuPoceli(stariTermin.getId());

        // Pronalaženje novog termina
        Termin noviTermin = terminRepository.findById(request.getTerminId())
                .orElseThrow(() -> new ResourceNotFoundException("Termin sa id " + request.getTerminId() + " ne postoji."));

        // Provera novog termina
        if (!noviTermin.getPocetak().isAfter(LocalDateTime.now()))
        {
            throw new ConflictException("Nije moguće dodati zadatak u termin koji je već počeo.");
        }

        proveriDaStudentiNisuPoceli(noviTermin.getId());

        // Provera jedinstvenosti rednog broja
        Short redniBroj = request.getRedniBroj().shortValue();

        if (zadatakRepository.existsByTerminIdAndRedniBrojAndIdNot(noviTermin.getId(), redniBroj, id))
        {
            throw new ConflictException("Zadatak sa rednim brojem " + redniBroj + " već postoji u ovom terminu.");
        }

        // Izmena postojećeg zadatka
        zadatak.setTermin(noviTermin);
        zadatak.setRedniBroj(redniBroj);
        zadatak.setOpis(request.getOpis());
        zadatak.setPocetniKodUrl(request.getPocetniKodUrl());
        zadatak.setMaxPoena(request.getMaxPoena());

        return Konverteri.getInstance().ZadatakToZadatakResponse(zadatakRepository.save(zadatak));
    }

    @Transactional
    public void delete(Long id)
    {
        Zadatak zadatak = getZadatak(id);
        Termin termin = zadatak.getTermin();

        if (!termin.getPocetak().isAfter(LocalDateTime.now()))
        {
            throw new ConflictException("Nije moguće obrisati zadatak jer je termin već počeo.");
        }

        proveriDaStudentiNisuPoceli(termin.getId());
        zadatakRepository.delete(zadatak);
    }

    private void proveriDaStudentiNisuPoceli(Long terminId)
    {
        if (studentTerminRepository.existsByTerminIdAndUlazakIsNotNull(terminId))
        {
            throw new ConflictException("Nije moguće izmeniti ili obrisati zadatke jer je neki student već započeo polaganje.");
        }
    }

    private ZadatakResponse mapToResponse(Zadatak zadatak)
    {
        ZadatakResponse response = new ZadatakResponse();
        response.setId(zadatak.getId());
        response.setTerminId(zadatak.getTermin().getId());
        response.setRedniBroj(Integer.valueOf(zadatak.getRedniBroj()));
        response.setOpis(zadatak.getOpis());
        response.setPocetniKodUrl(zadatak.getPocetniKodUrl());
        response.setMaxPoena(zadatak.getMaxPoena());
        return response;
    }

    @Transactional
    public List<ZadatakResponse> getZadaciForTermin(long terminId)
    {
        return zadatakRepository.findByTerminIdOrderByRedniBrojAsc(terminId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public ZadatakResponse getZadatakId(long id)
    {
        Zadatak zadatak = zadatakRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Zadatak sa id-jem '" + id + "' ne postoji."));
        return mapToResponse(zadatak);
    }

    public Zadatak getZadatak(long id)
    {
        return zadatakRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Zadatak sa id-jem '" + id + "' ne postoji."));
    }
}
