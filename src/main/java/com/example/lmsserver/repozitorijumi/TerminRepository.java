package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.Predavac;
import com.example.lmsserver.klase.ProveraZnanja;
import com.example.lmsserver.klase.Termin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TerminRepository extends JpaRepository<Termin, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj
    
    List<Termin> findByDatum(LocalDate datum);
    
    List<Termin> findByDatumBetween(LocalDate begin, LocalDate end);

    List<Termin> findByProvera(ProveraZnanja provera);

    List<Termin> findByPredavac(Predavac predavac);

    // termini koji su u toku u zadatom trenutku
    @Query("select t from Termin t where t.pocetak <= :trenutak and t.kraj >= :trenutak")
    List<Termin> getTerminiUToku(@Param("trenutak") LocalDateTime trenutak);

    @Query("select t from Termin t where t.provera.predmet.id = :predmetId order by t.pocetak")
    List<Termin> getTerminiPredmeta(@Param("predmetId") Long predmetId);
}
