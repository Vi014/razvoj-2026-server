package com.example.demo2.repozitorijumi;

import com.example.demo2.klase.Zadatak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZadatakRepository extends JpaRepository<Zadatak, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<Zadatak> findByTerminIdOrderByRedniBroj(Long terminId);
}
