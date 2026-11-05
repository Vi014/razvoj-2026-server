package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.Resenje;
import com.example.lmsserver.klase.Zadatak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResenjeRepository extends JpaRepository<Resenje, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<Resenje> findByZadatak(Zadatak zadatak);

}
