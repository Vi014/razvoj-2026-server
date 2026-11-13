package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<Student> findByEmail(String email);

    Optional<Student> findByBrojIndeksa(String brojIndeksa);

    List<Student> findByGrupaId(Long grupaId);

    // ovo bi trebalo raditi za ponovce
    List<Student> findByGrupaIsNull();

    void deleteByBrojIndeksa(String brojIndeksa);
}
