package com.example.lmsserver.servisi;

import com.example.lmsserver.exceptions.ResourceNotFoundException;
import com.example.lmsserver.klase.*;
import com.example.lmsserver.kontroleri.request.StudentRequest;
import com.example.lmsserver.kontroleri.request.StudentUpdateRequest;
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
import java.util.Optional;

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

    @Transactional
    public StudentResponse findById(Long id) {
        Optional<Student> out = studentRepository.findById(id);
        return out.map(student -> Konverteri.getInstance().StudentToStudentResponse(student)).orElse(null);
    }

    @Transactional
    public StudentResponse findByIndeks(String brojIndeksa) {
        Optional<Student> out = studentRepository.findByBrojIndeksa(brojIndeksa);
        return out.map(student -> Konverteri.getInstance().StudentToStudentResponse(student)).orElse(null);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    public void deleteByIndeks(String brojIndeksa) {
        studentRepository.deleteByBrojIndeksa(brojIndeksa);
    }

    @Transactional
    public StudentResponse updateById(Long id, StudentUpdateRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student sa datim id-jem ne postoji (kako je ovaj request prošao kroz endpoint?)"));

        boolean regenerateEmail = false;
        if (request.getIme() != null) {
            student.setIme(request.getIme().trim());
            regenerateEmail = true;
        }
        if (request.getPrezime() != null) {
            student.setPrezime(request.getPrezime().trim());
            regenerateEmail = true;
        }
        if (request.getJmbg() != null) {
            student.setJmbg(request.getJmbg());
        }
        if (request.getGodina() != null) {
            student.setGodina(request.getGodina());
        }
        if (request.getBrojIndeksa() != null) {
            student.setBrojIndeksa(request.getBrojIndeksa().trim());
            regenerateEmail = true;
        }
        if (request.getGrupaId() != null) {
            student.setGrupa(grupaRepository.findById(request.getGrupaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Odabrana grupa ne postoji.")));
        }
        if (request.getSmerId() != null)
            student.setSmer(smerRepository.findById(request.getSmerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Odabrana grupa ne postoji.")));

        if (regenerateEmail)
            student.setEmail(EmailGenerator.GenerateStudentEmail(student));

        return Konverteri.getInstance().StudentToStudentResponse(studentRepository.save(student));
    }

    @Transactional
    public StudentResponse updateByIndex(String brojIndeksa, StudentUpdateRequest request) {
        Student student = studentRepository.findByBrojIndeksa(brojIndeksa)
                .orElseThrow(() -> new ResourceNotFoundException("Student sa datim indeksom ne postoji (kako je ovaj request prošao kroz endpoint?)"));

        boolean regenerateEmail = false;
        if (request.getIme() != null) {
            student.setIme(request.getIme().trim());
            regenerateEmail = true;
        }
        if (request.getPrezime() != null) {
            student.setPrezime(request.getPrezime().trim());
            regenerateEmail = true;
        }
        if (request.getJmbg() != null) {
            student.setJmbg(request.getJmbg());
        }
        if (request.getGodina() != null) {
            student.setGodina(request.getGodina());
        }
        if (request.getBrojIndeksa() != null) {
            student.setBrojIndeksa(request.getBrojIndeksa().trim());
            regenerateEmail = true;
        }
        if (request.getGrupaId() != null) {
            student.setGrupa(grupaRepository.findById(request.getGrupaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Odabrana grupa ne postoji.")));
        }
        if (request.getSmerId() != null)
            student.setSmer(smerRepository.findById(request.getSmerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Odabrana grupa ne postoji.")));

        if (regenerateEmail)
            student.setEmail(EmailGenerator.GenerateStudentEmail(student));

        return Konverteri.getInstance().StudentToStudentResponse(studentRepository.save(student));
    }
}
