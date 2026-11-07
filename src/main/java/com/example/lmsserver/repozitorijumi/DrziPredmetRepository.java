package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.DrziPredmet;
import com.example.lmsserver.klase.enumi.UlogaPredavaca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrziPredmetRepository extends JpaRepository<DrziPredmet, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<DrziPredmet> findByPredavacId(long predavacId);

    List<DrziPredmet> findByPredmetId(long predmetId);

    UlogaPredavaca Uloga(UlogaPredavaca uloga);
}
