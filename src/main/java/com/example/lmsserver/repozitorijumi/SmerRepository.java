package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.Smer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmerRepository extends JpaRepository<Smer, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<Smer> findByOznaka(String oznaka);
}
