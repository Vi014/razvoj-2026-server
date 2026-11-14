package com.example.lmsserver.servisi;

import com.example.lmsserver.klase.Student;
import com.example.lmsserver.klase.StudentTermin;
import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.klase.enumi.StatusPolaganja;
import com.example.lmsserver.repozitorijumi.StudentRepository;
import com.example.lmsserver.repozitorijumi.StudentTerminRepository;
import com.example.lmsserver.repozitorijumi.TerminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentTerminService {
    private final StudentTerminRepository studentTerminRepository;
    private final StudentRepository studentRepository;
    private final TerminRepository terminRepository;

    public void addStudentsToTermin(Long terminId, List<Long> studentIds) {
        Termin termin = terminRepository.findById(terminId)
                .orElseThrow(() -> new RuntimeException("Termin with id " + terminId + " not found"));

        for(Long studentId : studentIds) {
            StudentTermin studentTermin = new StudentTermin();

            studentTermin.setTermin(termin);
            studentTermin.setStatus(StatusPolaganja.NIJE_PRIJAVLJEN);

            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student with id " + studentId + " not found"));
            studentTermin.setStudent(student);

            studentTerminRepository.save(studentTermin);
        }
    }
}
