package com.example.demo2.repozitorijumi;

import com.example.demo2.klase.Komentar;
import com.example.demo2.klase.Resenje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KomentarRepository extends JpaRepository<Komentar, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<Komentar> findByResenjeOrderByCreatedAt(Resenje resenje);
}
