package com.example.lmsserver.kontroleri;

import com.example.lmsserver.klase.ProveraZnanja;
import com.example.lmsserver.klase.Termin;
import com.example.lmsserver.kontroleri.request.StudentRequest;
import com.example.lmsserver.kontroleri.request.StudentUpdateRequest;
import com.example.lmsserver.kontroleri.response.PageResponse;
import com.example.lmsserver.kontroleri.response.StudentResponse;
import com.example.lmsserver.servisi.StudentService;
import com.example.lmsserver.util.Konverteri;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/student")
public class StudentController {
    final StudentService studentService;

    @PostMapping(path = "/add")
    public Long addNewStudent(@RequestBody @Valid StudentRequest studentRequest) {
        return studentService.save(studentRequest).getId();
    }

    @GetMapping(path = "/all")
    public List<StudentResponse> getAllStudenti() {
        return studentService.findAll();
    }

    @GetMapping(path = "/all/paginated")
    public PageResponse<StudentResponse> getAllStudentPodaciPaginated(@RequestParam(defaultValue="0") Integer page, @RequestParam(defaultValue="10") Integer size) {
        return PageResponse.of(studentService.findAll(page, size), Konverteri.getInstance()::StudentToStudentResponse);
    }

    @GetMapping(path="/podaci/id")
    public StudentResponse getStudentById(@RequestParam Long id){ // ovde koristimo RequestParam samo zbog konzistentnosti sa getByIndeks
        return studentService.findById(id);
    }

    @GetMapping(path="/podaci/index")
    public StudentResponse getStudentByIndeks(@RequestParam String brojIndeksa){ // RequestParam pouzdaniji od PathVariable jer br indeksa ima kosu crtu i razmak u sebi
        return studentService.findByIndeks(brojIndeksa);
    }

    // ResponseEntity je spring klasa za slanje http responseova
    // https://www.geeksforgeeks.org/advance-java/how-to-use-spring-responseentity-to-manipulate-the-http-response/
    @DeleteMapping(path="/delete/id")
    public ResponseEntity<String> deleteStudentById(@RequestParam Long id) {
        // gugl tj. brave search kaze da je najbolja praksa slati 404 Not Found ako id kog zelimo obrisati ne postoji a 204 No Content ako smo ga uspesno obrisali
        if(studentService.findById(id) == null)
            return new ResponseEntity<>("U bazi već ne postoji student sa datim id-jem", HttpStatus.NOT_FOUND);
        studentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path="/delete/index")
    public ResponseEntity<String> deleteStudentByIndex(@RequestParam String brojIndeksa) {
        if(studentService.findByIndeks(brojIndeksa) == null)
            return new ResponseEntity<>("U bazi već ne postoji student sa datim brojem indeksa", HttpStatus.NOT_FOUND);
        studentService.deleteByIndeks(brojIndeksa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path="/update/id")
    public ResponseEntity<StudentResponse> updateStudentById(@RequestParam Long id, @RequestBody @Valid StudentUpdateRequest newData) {
        if(studentService.findById(id) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        StudentResponse sr = studentService.updateById(id, newData);

        return new ResponseEntity<>(sr, HttpStatus.OK);
    }

    @PutMapping(path="/update/index")
    public ResponseEntity<StudentResponse> updateStudentByIndex(@RequestParam String brojIndeksa, @RequestBody @Valid StudentUpdateRequest newData) {
        if(studentService.findByIndeks(brojIndeksa) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        StudentResponse sr = studentService.updateByIndex(brojIndeksa, newData);

        return new ResponseEntity<>(sr, HttpStatus.OK);
    }
}
