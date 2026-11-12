package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.Zadatak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZadatakRepository extends JpaRepository<Zadatak, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<Zadatak> findByTerminIdOrderByRedniBrojAsc(Long terminId);

    boolean existsByTerminIdAndRedniBroj(Long terminId, Short redniBroj);

    boolean existsByTerminIdAndRedniBrojAndIdNot(Long terminId, Short redniBroj, Long id);
}
