package com.example.demo2.repozitorijumi;

import com.example.demo2.klase.Grupa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupaRepository extends JpaRepository<Grupa, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj
}
