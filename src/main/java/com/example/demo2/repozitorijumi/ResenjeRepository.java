package com.example.demo2.repozitorijumi;

import com.example.demo2.klase.Resenje;
import com.example.demo2.klase.Zadatak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResenjeRepository extends JpaRepository<Resenje, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<Resenje> findByZadatak(Zadatak zadatak);

}
