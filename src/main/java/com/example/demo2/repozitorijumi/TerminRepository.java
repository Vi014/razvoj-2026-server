package com.example.demo2.repozitorijumi;

import com.example.demo2.klase.Predavac;
import com.example.demo2.klase.ProveraZnanja;
import com.example.demo2.klase.Termin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TerminRepository extends JpaRepository<Termin, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj
    
    List<Termin> findByDatum(LocalDate datum);
    
    List<Termin> findByDatumBetween(LocalDate begin, LocalDate end);

    List<Termin> findByProvera(ProveraZnanja provera);

    List<Termin> findByPredavac(Predavac predavac);
}
