package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.Grupa;
import com.example.lmsserver.klase.Smer;
import com.example.lmsserver.klase.Student;
import com.example.lmsserver.kontroleri.request.StudentRequest;
import com.example.lmsserver.kontroleri.response.StudentResponse;
import com.example.lmsserver.repozitorijumi.*;
import com.example.lmsserver.util.EmailGenerator;
import com.example.lmsserver.util.Konverteri;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    //region repozitorijumi
    final DrziPredmetRepository drziPredmetRepository;
    final GrupaRepository grupaRepository;
    final KomentarRepository komentarRepository;
    final PredavacRepository predavacRepository;
    final PredmetRepository predmetRepository;
    final ProveraZnanjaRepository proveraZnanjaRepository;
    final ResenjeRepository resenjeRepository;
    final SmerRepository smerRepository;
    final StudentRepository studentRepository;
    final StudentPredmetRepository studentPredmetRepository;
    final StudentTerminRepository studentTerminRepository;
    final TerminRepository terminRepository;
    final ZadatakRepository zadatakRepository;
    //endregion

    @Transactional
    public Student save(StudentRequest studentRequest) {
        Smer smer = smerRepository.findById(studentRequest.getSmerId())
                .orElseThrow(() -> new ResourceNotFoundException("Neispravan unos smera."));

        Grupa grupa = null;
        if (studentRequest.getGrupaId() != null) {
            grupa = grupaRepository.findById(studentRequest.getGrupaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Neispravan unos grupe."));
        }

        Student student = Konverteri.getInstance().StudentRequestToStudent(studentRequest);

        student.setSmer(smer);
        student.setGrupa(grupa);
        student.setEmail(EmailGenerator.GenerateStudentEmail(student.getIme(), student.getPrezime(), student.getBrojIndeksa()));

        return studentRepository.save(student);

        // TODO: validacija?
    }

    @Transactional
    public List<StudentResponse> findAll() {
        List<Student> studentiUnedited = studentRepository.findAll();
        List<StudentResponse> studentiEdited = new ArrayList<>();
        for(Student student : studentiUnedited) {
            studentiEdited.add(Konverteri.getInstance().StudentToStudentResponse(student));
        }
        return studentiEdited;
    }

    @Transactional
    public Page<Student> findAll(int page, int size) {
        List<StudentResponse> tmp = findAll();
        return studentRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }
}
