package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.ConflictException;
import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.ProveraZnanja;
import com.example.lmsserver.klase.Student;
import com.example.lmsserver.klase.StudentTermin;
import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.kontroleri.request.PrijavaRequest;
import com.example.lmsserver.kontroleri.response.PrijavaResponse;
import com.example.lmsserver.repozitorijumi.StudentPredmetRepository;
import com.example.lmsserver.repozitorijumi.StudentRepository;
import com.example.lmsserver.repozitorijumi.StudentTerminRepository;
import com.example.lmsserver.repozitorijumi.TerminRepository;
import com.example.lmsserver.util.Konverteri;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PrijavaService
{
    private final StudentTerminRepository studentTerminRepository;
    private final TerminRepository terminRepository;
    private final StudentRepository studentRepository;
    private final StudentPredmetRepository studentPredmetRepository;

    @Transactional
    public PrijavaResponse prijavi(PrijavaRequest request)
    {
        Termin termin = terminRepository.findById(request.getTerminId())
                .orElseThrow(() -> new ResourceNotFoundException("Termin sa id " + request.getTerminId() + " ne postoji."));
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student sa id " + request.getStudentId() + " ne postoji."));


        if (!termin.getPocetak().isAfter(LocalDateTime.now()))
        {
            throw new ConflictException("Prijava nije moguca jer je termin vec poceo.");
        }

        ProveraZnanja provera = termin.getProvera();

        // student mora da slusa predmet provere
        if (!studentPredmetRepository.slusaPredmet(student.getId(), provera.getPredmet().getId()))
        {
            throw new ConflictException("Student ne slusa predmet " + provera.getPredmet().getSifra() + ".");
        }

        if (studentTerminRepository.existsByTerminIdAndStudentId(termin.getId(), student.getId()))
        {
            throw new ConflictException("Student je vec prijavljen na ovaj termin.");
        }


        if (studentTerminRepository.existsPrijavaZaProveru(student.getId(), provera.getId()))
        {
            throw new ConflictException("Student je vec prijavljen na drugi termin iste provere.");
        }

        StudentTermin prijava = new StudentTermin();
        prijava.setTermin(termin);
        prijava.setStudent(student);  // status je PRIJAVLJEN po podrazumevanoj vrednosti
        return Konverteri.StudentTerminToPrijavaResponse(studentTerminRepository.save(prijava));
    }
}
