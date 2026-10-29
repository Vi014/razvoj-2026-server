package com.example.demo2.repozitorijumi;

import com.example.demo2.klase.StudentTermin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentTerminRepository extends JpaRepository<StudentTermin, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<StudentTermin> findByTerminId(Long terminId);

    List<StudentTermin> findByStudentId(Long studentId);

    List<StudentTermin> findByTerminIdAndStudentId(Long terminId, Long studentId);
}
