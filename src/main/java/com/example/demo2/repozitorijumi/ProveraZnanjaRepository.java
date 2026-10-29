package com.example.demo2.repozitorijumi;

import com.example.demo2.klase.Predmet;
import com.example.demo2.klase.ProveraZnanja;
import com.example.demo2.klase.enumi.TipProvere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProveraZnanjaRepository extends JpaRepository<ProveraZnanja, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj
    
    List<ProveraZnanja> findByPredmet(Predmet predmet);

    List<ProveraZnanja> findByPredmetAndTip(Predmet predmet, TipProvere tip);
}
