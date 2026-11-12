package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.Predmet;
import com.example.lmsserver.klase.ProveraZnanja;
import com.example.lmsserver.klase.enumi.TipProvere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProveraZnanjaRepository extends JpaRepository<ProveraZnanja, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj
    
    List<ProveraZnanja> findByPredmet(Predmet predmet);

    List<ProveraZnanja> findByPredmetAndTip(Predmet predmet, TipProvere tip);

    boolean existsByPredmetIdAndNazivIgnoreCase(Long predmetId, String naziv);

    boolean existsByPredmetIdAndNazivIgnoreCaseAndIdNot(Long predmetId, String naziv, Long id);
}
