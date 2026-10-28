package com.example.demo2.repozitorijumi;

import com.example.demo2.klase.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj
}
