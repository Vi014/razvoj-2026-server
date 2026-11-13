package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.Termin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TerminRepository extends JpaRepository<Termin, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    // termini koji su u toku u zadatom trenutku
    @Query("select t from Termin t where t.pocetak <= :trenutak and t.kraj >= :trenutak")
    List<Termin> getTerminiUToku(@Param("trenutak") LocalDateTime trenutak);

    @Query("select t from Termin t where t.provera.predmet.id = :predmetId order by t.pocetak")
    List<Termin> getTerminiPredmeta(@Param("predmetId") Long predmetId);

    @Query("select t from Termin t where t.provera= :proveraId order by t.pocetak")
    List<Termin> getTerminiProvere(@Param("proveraId") Long proveraId);

    boolean existsByProveraIdAndPocetakLessThanEqual(Long proveraId, LocalDateTime vreme);
}
