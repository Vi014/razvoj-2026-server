package com.example.lmsserver.servisi;

import com.example.lmsserver.klase.Grupa;
import com.example.lmsserver.klase.Smer;
import com.example.lmsserver.klase.Student;
import com.example.lmsserver.kontroleri.request.StudentRequest;
import com.example.lmsserver.kontroleri.response.StudentResponse;
import com.example.lmsserver.repozitorijumi.*;
import com.example.lmsserver.util.Konverteri;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Neispravan unos smera.")); // TODO: proveriti jel ovo ispravan tip izuzetka, eventualno napraviti nas (primer ima svoj custom ResourceNotFoundException koji nasledjuje RuntimeException)

        Grupa grupa = null;
        if (studentRequest.getGrupaId() != null) {
            grupa = grupaRepository.findById(studentRequest.getGrupaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Neispravan unos grupe."));
        }

        Student student = Konverteri.StudentRequestToStudent(studentRequest);
        student.setSmer(smer);
        student.setGrupa(grupa);

        return studentRepository.save(student);

        // TODO: validacija? automatsko generisanje mejla?
    }

    @Transactional
    public List<StudentResponse> findAll() {
        List<Student> studentiUnedited = studentRepository.findAll();
        List<StudentResponse> studentiEdited = new ArrayList<>();
        for(Student student : studentiUnedited) {
            studentiEdited.add(Konverteri.StudentToStudentResponse(student));
        }
        return studentiEdited;
    }
}
