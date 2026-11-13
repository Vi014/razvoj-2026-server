package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.Resenje;
import com.example.lmsserver.klase.Zadatak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResenjeRepository extends JpaRepository<Resenje, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<Resenje> findByZadatak(Zadatak zadatak);

    List<Resenje> findByStudentTerminId(Long studentTerminId);

    @Query("select r from Resenje r join fetch r.zadatak z where r.studentTermin.id = :studentTerminId")
    List<Resenje> findAllByStudentTerminId(@Param("studentTerminId") Long studentTerminId);

}
