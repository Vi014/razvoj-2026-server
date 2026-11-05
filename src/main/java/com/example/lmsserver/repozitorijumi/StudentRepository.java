package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<Student> findByEmail(String email);

    List<Student> findByBrojIndeksa(String brojIndeksa);

    List<Student> findByGrupaId(Long grupaId);

    // ovo bi trebalo raditi za ponovce
    List<Student> findByGrupaIsNull();
}
