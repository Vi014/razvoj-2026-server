package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.StudentPredmet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentPredmetRepository extends JpaRepository<StudentPredmet, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<StudentPredmet> findByStudentId(Long studentId);

    List<StudentPredmet> findByPredmetId(Long predmetId);
}
