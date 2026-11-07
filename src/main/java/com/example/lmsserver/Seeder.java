package com.example.lmsserver;

import com.example.lmsserver.klase.*;
import com.example.lmsserver.klase.enumi.TipPredavaca;
import com.example.lmsserver.klase.enumi.UlogaPredavaca;
import com.example.lmsserver.klase.enumi.VrsteStudija;
import com.example.lmsserver.repozitorijumi.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

    private Predmet predmet(String sifra, String naziv, int espb)
    {
        Predmet p = new Predmet();
        p.setSifra(sifra);
        p.setNaziv(naziv);
        p.setEspb(espb);
        return p;
    }

    private Predavac predavac(String ime, String prezime, String email, String jmbg, LocalDate datumRodjenja, TipPredavaca titula)
    {
        Predavac p = new Predavac();
        p.setIme(ime);
        p.setPrezime(prezime);
        p.setEmail(email);
        p.setJmbg(jmbg);
        p.setDatumRodjenja(datumRodjenja);
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

    private DrziPredmet drziPredmet(Predavac predavac, Predmet predmet, UlogaPredavaca uloga)
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

        //region smerovi
        Smer rn = smerRepository.save(smer("RN", "Računarske nauke",            4, 8, VrsteStudija.OSNOVNE_AKADEMSKE));
        Smer ri = smerRepository.save(smer("RI", "Racunarsko inženjerstvo",     4, 8, VrsteStudija.OSNOVNE_AKADEMSKE));
        Smer si = smerRepository.save(smer("SI", "Softversko inženjerstvo",     4, 8, VrsteStudija.OSNOVNE_AKADEMSKE));
        Smer is = smerRepository.save(smer("IS", "Informacioni sistemi",        4, 8, VrsteStudija.OSNOVNE_AKADEMSKE));
        Smer it = smerRepository.save(smer("IT", "Informacione tehnologije",    3, 6, VrsteStudija.OSNOVNE_STRUKOVNE));
        Smer m0 = smerRepository.save(smer("M0", "Računarske nauke MAS",        1, 2, VrsteStudija.MASTER_AKADEMSKE));
        Smer m1 = smerRepository.save(smer("M1", "Računarsko inženjerstvo MAS", 1, 2, VrsteStudija.MASTER_AKADEMSKE));
        Smer m2 = smerRepository.save(smer("M2", "Softversko inženjerstvo MAS", 1, 2, VrsteStudija.MASTER_AKADEMSKE));
        Smer m3 = smerRepository.save(smer("M3", "Informacioni sistemi MAS",    1, 2, VrsteStudija.MASTER_AKADEMSKE));
        Smer sm = smerRepository.save(smer("M5", "Informacione tehnologije SM", 2, 4, VrsteStudija.MASTER_STRUKOVNE));
        Smer d0 = smerRepository.save(smer("D0", "Računarske nauke D",          3, 6, VrsteStudija.DOKTORSKE));
        Smer d1 = smerRepository.save(smer("D1", "Računarsko inženjerstvo D",   3, 6, VrsteStudija.DOKTORSKE));
        //endregion

        //region predmeti
        Predmet uup   = predmetRepository.save(predmet("0003",  "Uvod u programiranje",                8)); // RN, RI, SI, IS
        Predmet op    = predmetRepository.save(predmet("2012",  "Osnovi programiranja",                8)); // IS, IT
        Predmet oop   = predmetRepository.save(predmet("1003",  "Objektno-orijentisano programiranje", 8)); // RN, RI, SI, IS
        Predmet asprn = predmetRepository.save(predmet("2025A", "Algoritmi i strukture podataka RN",   8)); // RN
        Predmet aspri = predmetRepository.save(predmet("2025B", "Algoritmi i strukture podataka RI",   8)); // RI
        Predmet aspsi = predmetRepository.save(predmet("2025C", "Algoritmi i strukture podataka SI",   8)); // SI
        Predmet aspsm = predmetRepository.save(predmet("8205",  "Algoritmi",                           8)); // SM
        Predmet dsw   = predmetRepository.save(predmet("2002",  "Dizajn softvera",                     8)); // RN, SI
        Predmet osrn  = predmetRepository.save(predmet("2008A", "Operativni sistemi RN",               8)); // RN
        Predmet osri  = predmetRepository.save(predmet("2008B", "Operativni sistemi RI",               8)); // RI
        Predmet ossi  = predmetRepository.save(predmet("2008C", "Operativni sistemi SI",               8)); // SI
        Predmet ossm  = predmetRepository.save(predmet("2008D", "Operativni sistemi SM",               5)); // SM
        Predmet daa   = predmetRepository.save(predmet("2005",  "Dizajn i analiza algoritama",         6)); // RN
        Predmet rm    = predmetRepository.save(predmet("2001",  "Računarske mreže",                    8)); // RN, RI, SI, IS
        Predmet bp    = predmetRepository.save(predmet("4014",  "Baze podataka",                       8)); // RN, SI, IS
        Predmet nbp   = predmetRepository.save(predmet("4300",  "Napredne baze podataka",              6)); // SI, SM
        Predmet rs    = predmetRepository.save(predmet("3004",  "Razvoj softvera",                     6)); // SI
        Predmet ts    = predmetRepository.save(predmet("5022",  "Testiranje softvera",                 6)); // RN, RI, SI
        Predmet ps    = predmetRepository.save(predmet("3002",  "Programiranje sistema",               6)); // SI
        Predmet rwa   = predmetRepository.save(predmet("5974",  "Razvoj veb aplikacija",               6)); // SI, IS
        Predmet rma   = predmetRepository.save(predmet("5026",  "Razvoj mobilnih aplikacija",          6)); // RN, RI, SI
        Predmet pp    = predmetRepository.save(predmet("5972",  "Paradigme programiranja",             6)); // SI
        Predmet rmp   = predmetRepository.save(predmet("5024",  "Razvoj multiplatformskih aplikacija", 6)); // SI
        Predmet rrg   = predmetRepository.save(predmet("5016",  "Razvoj računarskih igara",            6)); // SI
        Predmet csp   = predmetRepository.save(predmet("5973",  "C# programiranje",                    8)); // IT
        Predmet rbs   = predmetRepository.save(predmet("RBS",   "Razvoj bezbednog softvera",           6)); // SI master
        //endregion

        //region predavači
        // https://konvertor.rs/jmbg-validator
        Predavac pr0 = predavacRepository.save(predavac("Petar",    "Petrović",  "ppetrovic@raf.rs",  "2104972720167", LocalDate.of(1972,  4, 21), TipPredavaca.REDOVNI_PROFESOR));
        Predavac pr1 = predavacRepository.save(predavac("Jovana",   "Jovanović", "jjovanovic@raf.rs", "2308971927498", LocalDate.of(1971,  8, 23), TipPredavaca.VANREDNI_PROFESOR));
        Predavac pr2 = predavacRepository.save(predavac("Nikola",   "Nikolić",   "nnikolic@raf.rs",   "2503967132798", LocalDate.of(1967,  3, 25), TipPredavaca.REDOVNI_PROFESOR));
        Predavac pr3 = predavacRepository.save(predavac("Tibor",    "Rácz",      "tracz@raf.rs",      "1208971820075", LocalDate.of(1971,  8, 12), TipPredavaca.REDOVNI_PROFESOR));
        Predavac pr4 = predavacRepository.save(predavac("Lucija",   "Radić",     "lradic@raf.rs",     "1206992355612", LocalDate.of(1992,  6, 12), TipPredavaca.VANREDNI_PROFESOR));
        Predavac pr5 = predavacRepository.save(predavac("Luka",     "Jurić",     "ljuric@raf.rs",     "1208977311768", LocalDate.of(1977,  8, 12), TipPredavaca.REDOVNI_PROFESOR));
        Predavac pr6 = predavacRepository.save(predavac("Etaoin",   "Shrdlu",    "eshrdlu@raf.rs",    "2907973850396", LocalDate.of(1973,  7, 29), TipPredavaca.VANREDNI_PROFESOR));
        Predavac pr7 = predavacRepository.save(predavac("Jasmina",  "Marić",     "jmaric@raf.rs",     "0408978199908", LocalDate.of(1978,  8,  4), TipPredavaca.REDOVNI_PROFESOR));
        Predavac pr8 = predavacRepository.save(predavac("Milica",   "Milić",     "mmilic@raf.rs",     "1809961227140", LocalDate.of(1961,  9, 18), TipPredavaca.VANREDNI_PROFESOR));
        Predavac pr9 = predavacRepository.save(predavac("John",     "Doe",       "jdoe@raf.rs",       "1705978861463", LocalDate.of(1978,  5, 17), TipPredavaca.REDOVNI_PROFESOR));

        Predavac as0 = predavacRepository.save(predavac("Miloš",    "Milošević", "mmilosevic@raf.rs", "2008995123833", LocalDate.of(1995,  8, 20), TipPredavaca.ASISTENT));
        Predavac as1 = predavacRepository.save(predavac("Filip",    "Filipović", "ffilipovic@raf.rs", "2007000101607", LocalDate.of(2000,  7, 20), TipPredavaca.ASISTENT));
        Predavac as2 = predavacRepository.save(predavac("Nikolina", "Marković",  "nmarkovic@raf.rs",  "2809000966720", LocalDate.of(2000,  9, 28), TipPredavaca.ASISTENT));
        Predavac as3 = predavacRepository.save(predavac("Amon",     "Gus",       "agus@raf.rs",       "2507000172078", LocalDate.of(2000,  7, 25), TipPredavaca.ASISTENT));
        Predavac as4 = predavacRepository.save(predavac("Jana",     "Janošević", "jjanosevic@raf.rs", "1511001527116", LocalDate.of(2001, 11, 15), TipPredavaca.ASISTENT));
        Predavac as5 = predavacRepository.save(predavac("Milena",   "Marinkov",  "mmarinkov@raf.rs",  "3007003957603", LocalDate.of(2003,  7, 30), TipPredavaca.ASISTENT));
        Predavac as6 = predavacRepository.save(predavac("Tanja",    "Tasić",     "ttasic@raf.rs",     "2809003919766", LocalDate.of(2003,  9, 28), TipPredavaca.ASISTENT));
        Predavac as7 = predavacRepository.save(predavac("Bogdan",   "Bogdanov",  "bbogdanov@raf.rs",  "1501007750741", LocalDate.of(2007,  1, 15), TipPredavaca.ASISTENT));
        Predavac as8 = predavacRepository.save(predavac("Mila",     "Petković",  "mpetkovic@raf.rs",  "2506001465706", LocalDate.of(2001,  6, 25), TipPredavaca.ASISTENT));
        Predavac as9 = predavacRepository.save(predavac("Jelena",   "Mitrović",  "jmitrovic@raf.rs",  "3007005937758", LocalDate.of(2005,  7, 30), TipPredavaca.ASISTENT));

        Predavac dm0 = predavacRepository.save(predavac("Marko",    "Marković",  "mmarkovic@raf.rs",  "2905992921668", LocalDate.of(1992,  5, 29), TipPredavaca.DEMONSTRATOR));
        Predavac dm1 = predavacRepository.save(predavac("Mirka",    "Mirković",  "mmirkovic@raf.rs",  "2910975769929", LocalDate.of(1975, 10, 29), TipPredavaca.DEMONSTRATOR));
        Predavac dm2 = predavacRepository.save(predavac("Gustavo",  "Fring",     "gfring@raf.rs",     "1801991593442", LocalDate.of(1991,  1, 18), TipPredavaca.DEMONSTRATOR));
        Predavac dm3 = predavacRepository.save(predavac("Branko",   "Branković", "bbrankovic@raf.rs", "0612001121060", LocalDate.of(2001, 12,  6), TipPredavaca.DEMONSTRATOR));
        //endregion

        //region drziPredmet
        drziPredmetRepository.save(drziPredmet(pr1, uup, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(pr2, uup, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(dm0, uup, UlogaPredavaca.DEMONSTRACIJE));
        drziPredmetRepository.save(drziPredmet(as2, uup, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr5, op, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(dm2, op, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr1, oop, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(pr3, oop, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(dm1, oop, UlogaPredavaca.DEMONSTRACIJE));
        drziPredmetRepository.save(drziPredmet(pr2, asprn, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as0, asprn, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr3, aspri, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as1, aspri, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr4, aspsi, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as2, aspsi, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr5, aspsm, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(dm3, aspsm, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr1, dsw, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(pr8, dsw, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as0, dsw, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(as1, dsw, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr2, osrn, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as0, osrn, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr3, osri, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as1, osri, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr4, ossi, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as2, ossi, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr5, ossm, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as3, ossm, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr2, daa, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as0, daa, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr0, rm, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as5, rm, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr7, bp, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as5, bp, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr7, nbp, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as5, nbp, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr8, rs, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as8, rs, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr9, ts, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as6, ts, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr8, ps, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as8, ps, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr6, rwa, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as3, rwa, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr6, rma, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as3, rma, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr9, pp, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as9, pp, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr6, rmp, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as3, rmp, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr5, csp, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as7, csp, UlogaPredavaca.VEZBE));
        drziPredmetRepository.save(drziPredmet(pr5, rbs, UlogaPredavaca.PREDAVANJA));
        drziPredmetRepository.save(drziPredmet(as7, rbs, UlogaPredavaca.VEZBE));
        //endregion

        Grupa si1 = grupaRepository.save(grupa("111", si, 1));
        Grupa si2 = grupaRepository.save(grupa("112", si, 1));
        Grupa rn1 = grupaRepository.save(grupa("101", rn, 1));
        Grupa rn2 = grupaRepository.save(grupa("102", rn, 1));
        Grupa ri1 = grupaRepository.save(grupa("121", ri, 1));
        Grupa ri2 = grupaRepository.save(grupa("122", ri, 1));

        List<Student> studenti = new ArrayList<>();
        /*studenti.add(studentRepository.save(student("Stefan","Stefanović","0123456789010","1/2026 SI", si1, si)));
        studenti.add(studentRepository.save(student("Stefan","Stefanović","0123456789011","2/2026 SI", si2, si)));
        studenti.add(studentRepository.save(student("Stefan","Stefanović","0123456789012","3/2026 RN", rn1, rn)));
        studenti.add(studentRepository.save(student("Stefan","Stefanović","0123456789013","4/2026 RN", rn2, rn)));
        studenti.add(studentRepository.save(student("Stefan","Stefanović","0123456789014","5/2026 RI", ri1, ri)));
        studenti.add(studentRepository.save(student("Stefan","Stefanović","0123456789015","6/2026 RI", ri2, ri)));
        studenti.add(studentRepository.save(student("Stefan","Ponovac","0123456789016","1/2025 SI", null, si)));*/
        studenti.add(studentRepository.save(student("Marko","Petrović","0101998710015","1/2021 RN", si1, rn)));
        studenti.add(studentRepository.save(student("Ana","Jovanović","0202999710125","2/2022 RI", si1, ri)));
        studenti.add(studentRepository.save(student("Nikola","Ilić","0303000710230","3/2023 SI", si1, si)));
        studenti.add(studentRepository.save(student("Milica","Stanković","0404001710340","4/2024 IS", si1, is)));
        studenti.add(studentRepository.save(student("Lazar","Pavlović","0505002800416","5/2025 IT", si1, it)));
        studenti.add(studentRepository.save(student("Jelena","Nikolić","0606996710500","6/2026 M5", si1, sm)));
        studenti.add(studentRepository.save(student("Đorđe","Đorđević","0707997710629","7/2021 SI", si1, si)));
        studenti.add(studentRepository.save(student("Teodora","Milosavljević","0808003710715","8/2022 IT", si1, it)));
        studenti.add(studentRepository.save(student("Ivan","Kostić","0909004710809","9/2023 RN", si1, rn)));
        studenti.add(studentRepository.save(student("Sara","Tomić","1010005710919","10/2024 RI", si1, ri)));
        studenti.add(studentRepository.save(student("Balázs","Kovács","1111999800122","11/2025 IS", si1, is)));
        studenti.add(studentRepository.save(student("Eszter","Nagy","1212000800237","12/2026 SI", si1, si)));
        studenti.add(studentRepository.save(student("Zoltán","Tóth","1301001800349","13/2021 IT", si1, it)));
        studenti.add(studentRepository.save(student("Katalin","Szabó","1402998800450","14/2022 M5", si1, sm)));
        studenti.add(studentRepository.save(student("Peter","Horváth","1503999800560","15/2023 RN", si1, rn)));
        studenti.add(studentRepository.save(student("Zuzana","Kováčová","1604000800674","16/2024 RI", si1, ri)));
        studenti.add(studentRepository.save(student("Martin","Novák","1705001800784","17/2025 SI", si1, si)));
        studenti.add(studentRepository.save(student("Jakub","Varga","1806002800894","18/2026 IS", si1, is)));
        studenti.add(studentRepository.save(student("Ivana","Babić","1907003200905","19/2021 IT", si1, it)));
        studenti.add(studentRepository.save(student("Luka","Marić","2008004200013","20/2022 RN", si1, rn)));
        studenti.add(studentRepository.save(student("Mario","Horvat","2109005200127","21/2023 RI", si1, ri)));
        studenti.add(studentRepository.save(student("Petra","Kovačević","2210996200231","22/2024 SI", si1, si)));
        studenti.add(studentRepository.save(student("Tomislav","Jurić","2311997200341","23/2025 M5", si1, sm)));
        studenti.add(studentRepository.save(student("Ante","Perić","2412998200451","24/2026 IT", si1, it)));
        studenti.add(studentRepository.save(student("Emir","Hadžić","2501999100566","25/2021 IS", si1, is)));
        studenti.add(studentRepository.save(student("Amina","Begović","2602000100678","26/2022 SI", si1, si)));
        studenti.add(studentRepository.save(student("Adnan","Mehmedović","2703001100781","27/2023 RN", si1, rn)));
        studenti.add(studentRepository.save(student("Lejla","Džaferović","2804002100895","28/2024 IT", si1, it)));
        studenti.add(studentRepository.save(student("Vuk","Simić","2905003710900","29/2025 RI", si1, ri)));
        studenti.add(studentRepository.save(student("Sofija","Radović","3006004710019","30/2026 SI", si1, si)));
        studenti.add(studentRepository.save(student("Aleksa","Rakić","3107005710122","31/2021 M5", si1, sm)));
        studenti.add(studentRepository.save(student("Kristina","Vuković","0108996710233","32/2022 IS", si1, is)));
        studenti.add(studentRepository.save(student("Uroš","Obradović","0209997710340","33/2023 IT", si1, it)));
        studenti.add(studentRepository.save(student("Iva","Lukić","0310998710456","34/2024 RN", si1, rn)));
        studenti.add(studentRepository.save(student("Dušan","Filipović","0411999710560","35/2025 SI", si1, si)));
        studenti.add(studentRepository.save(student("Mila","Gajić","0512000710678","36/2026 RI", si1, ri)));
        studenti.add(studentRepository.save(student("Stefan","Jovičić","0601001710783","37/2021 IT", si1, it)));
        studenti.add(studentRepository.save(student("Nevena","Todorović","0702002710897","38/2022 SI", si1, si)));
        studenti.add(studentRepository.save(student("Bojan","Đukić","0803003710909","39/2023 M5", si1, sm)));
        studenti.add(studentRepository.save(student("Andrea","Molnár","0904004800019","40/2024 IS", si1, is)));
        studenti.add(studentRepository.save(student("Dávid","Farkas","1005005800120","41/2025 RN", si1, rn)));
        studenti.add(studentRepository.save(student("Réka","Balogh","1106996800233","42/2026 IT", si1, it)));
        studenti.add(studentRepository.save(student("Miroslav","Konečný","1207997800347","43/2021 SI", si1, si)));
        studenti.add(studentRepository.save(student("Lucia","Hudáková","1308998800450","44/2022 RI", si1, ri)));
        studenti.add(studentRepository.save(student("Filip","Mitrović","1409999710565","45/2023 IS", si1, is)));
        studenti.add(studentRepository.save(student("Jovana","Ristić","1510000710675","46/2024 SI", si1, si)));
        studenti.add(studentRepository.save(student("Velibor","Savić","1611001710789","47/2025 IT", si1, it)));
        studenti.add(studentRepository.save(student("Dragana","Petrović","1712002710892","48/2026 M5", si1, sm)));
        studenti.add(studentRepository.save(student("Nemanja","Kostić","1801003710906","49/2021 RN", si1, rn)));
        studenti.add(studentRepository.save(student("Tijana","Vasić","1902004710017","50/2022 SI", si1, si)));

        /*
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
        }*/
    }
}
