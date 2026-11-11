package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.DrziPredmet;
import com.example.lmsserver.klase.enumi.UlogaPredavaca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrziPredmetRepository extends JpaRepository<DrziPredmet, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<DrziPredmet> findByPredavacId(long predavacId);

    List<DrziPredmet> findByPredmetId(long predmetId);

    UlogaPredavaca Uloga(UlogaPredavaca uloga);

    @Query("select count(dp) > 0 from DrziPredmet dp where dp.predavac.id = :predavacId and dp.predmet.id = :predmetId")
    boolean drziPredmet(@Param("predavacId") Long predavacId, @Param("predmetId") Long predmetId);
}
