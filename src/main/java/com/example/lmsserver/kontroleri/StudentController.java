package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.request.StudentRequest;
import com.example.lmsserver.kontroleri.response.PageResponse;
import com.example.lmsserver.kontroleri.response.StudentResponse;
import com.example.lmsserver.servisi.StudentService;
import com.example.lmsserver.util.Konverteri;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/student")
public class StudentController {
    //TODO - utvrditi koji sve api endpointi treba da se nalaze u projektu i napraviti sve objekte vezane za njih
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
        return PageResponse.of(studentService.findAll(page, size), Konverteri::StudentToStudentResponse);
    }
}
