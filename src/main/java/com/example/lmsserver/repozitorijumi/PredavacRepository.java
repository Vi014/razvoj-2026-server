package com.example.lmsserver.repozitorijumi;


import com.example.lmsserver.klase.Predavac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PredavacRepository extends JpaRepository<Predavac, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<Predavac> findByEmail(String email);

    @Query("select sp from Predavac sp where "
            + "(:ime is null or lower(sp.ime) like lower(concat('%', :ime, '%'))) and "
            + "(:prezime is null or lower(sp.prezime) like lower(concat('%', :prezime, '%')))")
    List<Predavac> findByImeAndPrezime(String ime, String prezime);
}
