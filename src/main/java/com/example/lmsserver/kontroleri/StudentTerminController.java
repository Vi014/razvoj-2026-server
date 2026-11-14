package com.example.lmsserver.kontroleri;

import com.example.lmsserver.servisi.PristupService;
import com.example.lmsserver.servisi.StudentTerminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/termin")
public class StudentTerminController {
    final StudentTerminService studentTerminService;
    final PristupService pristupService;

    @PostMapping(path = "/{terminId}/addStudents")
    @PreAuthorize("hasRole('PREDAVAC') and @pristupService.jePredavacZaTermin(#terminId)")
    public void addStudentsToTermin(@PathVariable Long terminId, @RequestBody List<Long> studentIds) {
        studentTerminService.addStudentsToTermin(terminId, studentIds);
    }
}
