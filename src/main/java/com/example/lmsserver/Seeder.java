package com.example.lmsserver;

import com.example.lmsserver.klase.*;
import com.example.lmsserver.klase.enumi.TipPredavaca;
import com.example.lmsserver.klase.enumi.VrsteStudija;
import com.example.lmsserver.repozitorijumi.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner
{
    // Seeder služi za generisanje test podataka
    // TODO: iskucati kod

    //region repozitorijumi
    private final SmerRepository smerRepository;
    private final GrupaRepository grupaRepository;
    private final PredmetRepository predmetRepository;
    private final PredavacRepository predavacRepository;
    private final StudentRepository studentRepository;
    private final StudentPredmetRepository studentPredmetRepository;
    private final DrziPredmetRepository drziPredmetRepository;
    private final ProveraZnanjaRepository proveraZnanjaRepository;
    private final TerminRepository terminRepository;
    private final ZadatakRepository zadatakRepository;
    private final StudentTerminRepository terminStudentRepository;
    private final ResenjeRepository resenjeRepository;
    private final KomentarRepository komentarRepository;
    //endregion

    //region pomoćne metode za pravljenje entiteta

    private Smer smer(String oznaka, String naziv, int trajanjeGodina, int trajanjeSemestara, VrsteStudija vrsteStudija)
    {
        Smer s = new Smer();
        s.setOznaka(oznaka);
        s.setNaziv(naziv);
        s.setTrajanjeGodina(trajanjeGodina);
        s.setTrajanjeSemestara(trajanjeSemestara);
        s.setVrstaStudija(vrsteStudija);
        s.setUkupnoEspb(trajanjeSemestara * 60);
        return s;
    }

    private Grupa grupa(String oznaka, Smer smer, int godina)
    {
        Grupa g = new Grupa();
        g.setOznaka(oznaka);
        g.setSmer(smer);
        g.setGodina(godina);
        return g;
    }

    private Predmet predmet(String sifra, String naziv, int espb,  int semestar, Smer smer)
    {
        Predmet p = new Predmet();
        p.setSifra(sifra);
        p.setNaziv(naziv);
        p.setEspb(espb);
        p.setSemestar(semestar);
        p.setSmer(smer);
        return p;
    }

    private Predavac predavac(String ime, String prezime, String email, String jmbg, TipPredavaca titula)
    {
        Predavac p = new Predavac();
        p.setIme(ime);
        p.setPrezime(prezime);
        p.setEmail(email);
        p.setJmbg(jmbg);
        p.setTitula(titula);
        return p;
    }

    private Student student(String ime, String prezime, String jmbg, String brojIndeksa, Grupa grupa, Smer smer)
    {
        Student s = new Student();
        s.setIme(ime);
        s.setPrezime(prezime);
        s.setJmbg(jmbg);
        s.setBrojIndeksa(brojIndeksa);
        s.setGrupa(grupa);
        s.setSmer(smer);
        return s;
    }

    private DrziPredmet drziPredmet(Predavac predavac, Predmet predmet, String uloga)
    {
        DrziPredmet d = new DrziPredmet();
        d.setPredavac(predavac);
        d.setPredmet(predmet);
        d.setUloga(uloga);
        return d;
    }

    private StudentPredmet studentPredmet(Student student, Predmet predmet)
    {
        StudentPredmet s = new StudentPredmet();
        s.setStudent(student);
        s.setPredmet(predmet);
        return s;
    }

    //endregion

    @Override
    public void run(String... args) throws Exception
    {
        if (smerRepository.count() > 0) return; // Ako je baza puna ne radi ništa

        Smer si = smerRepository.save(smer("SI", "Softversko Inženjerstvo", 4,8, VrsteStudija.OSNOVNE_AKADEMSKE));
        Smer rn = smerRepository.save(smer("RN", "Računarske Nauke", 4,8, VrsteStudija.OSNOVNE_AKADEMSKE));
        Smer ri = smerRepository.save(smer("RI", "Racunarsko Inženjerstvo", 4,8, VrsteStudija.OSNOVNE_AKADEMSKE));

        Predmet p1 = predmetRepository.save(predmet("001", "Uvod u programiranje", 8,1, si));
        Predmet p2 = predmetRepository.save(predmet("002", "Uvod u programiranje", 8,1, rn));
        Predmet p3 = predmetRepository.save(predmet("003", "Uvod u programiranje", 8,1, ri));
        Predmet p4 = predmetRepository.save(predmet("004", "Objektno-orijentisano programiranje", 8,2, si));
        Predmet p5 = predmetRepository.save(predmet("005", "Objektno-orijentisano programiranje", 8,2, rn));
        Predmet p6 = predmetRepository.save(predmet("006", "Objektno-orijentisano programiranje", 8,2, ri));

        Predavac pr1 = predavacRepository.save(predavac("Petar", "Petrović", "ppetrovic@raf.rs", "0123456789013", TipPredavaca.REDOVNI_PROFESOR));
        Predavac pr2 = predavacRepository.save(predavac("Jovana", "Jovanović", "jjovanovic@raf.rs", "0123456789014", TipPredavaca.VANDREDNI_PROFESOR));
        Predavac pr3 = predavacRepository.save(predavac("Nikola", "Nikolić", "nnikolic@raf.rs", "0123456789012", TipPredavaca.REDOVNI_PROFESOR));

        Predavac as1 = predavacRepository.save(predavac("Miloš", "Milošević", "mmilosevic@raf.rs", "0123456789067", TipPredavaca.ASISTENT));
        Predavac as2 = predavacRepository.save(predavac("Filip", "Filipović", "ffilipovic@raf.rs", "0123456789041", TipPredavaca.ASISTENT));
        Predavac as3 = predavacRepository.save(predavac("Marko", "Marković", "mmarkovic@raf.rs", "0123456789042", TipPredavaca.DEMONSTRATOR));

        drziPredmetRepository.save(drziPredmet(pr1, p1,"Profesor"));
        drziPredmetRepository.save(drziPredmet(as1, p1, "Asistent"));
        drziPredmetRepository.save(drziPredmet(pr2, p2, "Profesor"));
        drziPredmetRepository.save(drziPredmet(as2, p2, "Asistent"));
        drziPredmetRepository.save(drziPredmet(pr3, p3, "Profesor"));
        drziPredmetRepository.save(drziPredmet(as3, p3, "Asistent"));
        drziPredmetRepository.save(drziPredmet(pr1, p4, "Profesor"));
        drziPredmetRepository.save(drziPredmet(as2, p4, "Asistent"));
        drziPredmetRepository.save(drziPredmet(pr3, p5, "Profesor"));
        drziPredmetRepository.save(drziPredmet(as1, p5, "Asistent"));
        drziPredmetRepository.save(drziPredmet(pr2, p6, "Profesor"));
        drziPredmetRepository.save(drziPredmet(as3, p6,  "Asistent"));

        Grupa si1 = grupaRepository.save(grupa("111", si, 1));
        Grupa si2 = grupaRepository.save(grupa("112", si, 1));
        Grupa rn1 = grupaRepository.save(grupa("101", rn, 1));
        Grupa rn2 = grupaRepository.save(grupa("102", rn, 1));
        Grupa ri1 = grupaRepository.save(grupa("121", ri, 1));
        Grupa ri2 = grupaRepository.save(grupa("122", ri, 1));

        List<Student> studenti = new ArrayList<>();
        studenti.add(studentRepository.save(student("Stefan","Stefanović","0123456789010","1/2026 SI", si1, si)));
        studenti.add(studentRepository.save(student("Stefan","Stefanović","0123456789011","2/2026 SI", si2, si)));
        studenti.add(studentRepository.save(student("Stefan","Stefanović","0123456789012","3/2026 RN", rn1, rn)));
        studenti.add(studentRepository.save(student("Stefan","Stefanović","0123456789013","4/2026 RN", rn2, rn)));
        studenti.add(studentRepository.save(student("Stefan","Stefanović","0123456789014","5/2026 RI", ri1, ri)));
        studenti.add(studentRepository.save(student("Stefan","Stefanović","0123456789015","6/2026 RI", ri2, ri)));
        studenti.add(studentRepository.save(student("Stefan","Ponovac","0123456789016","1/2025 SI", null, si)));

        for (Student s : studenti)
        {
            if(s.getSmer().equals(si))
            {
                studentPredmetRepository.save(studentPredmet(s,p1));
                studentPredmetRepository.save(studentPredmet(s,p4));
            }
            if(s.getSmer().equals(rn))
            {
                studentPredmetRepository.save(studentPredmet(s,p2));
                studentPredmetRepository.save(studentPredmet(s,p5));
            }
            if(s.getSmer().equals(ri))
            {
                studentPredmetRepository.save(studentPredmet(s,p3));
                studentPredmetRepository.save(studentPredmet(s,p6));
            }
        }
    }
}
