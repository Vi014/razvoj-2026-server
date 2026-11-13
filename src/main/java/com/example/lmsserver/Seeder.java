package com.example.lmsserver;

import com.example.lmsserver.klase.*;
import com.example.lmsserver.klase.enumi.TipPredavaca;
import com.example.lmsserver.klase.enumi.TipProvere;
import com.example.lmsserver.klase.enumi.UlogaPredavaca;
import com.example.lmsserver.klase.enumi.VrsteStudija;
import com.example.lmsserver.repozitorijumi.*;
import com.example.lmsserver.util.EmailGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner
{
    // Seeder služi za generisanje test podataka

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
    private final StudentTerminRepository studentTerminRepository;
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

    private Predavac predavac(String ime, String prezime, String jmbg, LocalDate datumRodjenja, TipPredavaca titula)
    {
        Predavac p = new Predavac();
        p.setIme(ime);
        p.setPrezime(prezime);
        p.setEmail(EmailGenerator.GeneratePredavacEmail(ime, prezime, predavacRepository));
        p.setJmbg(jmbg);
        p.setDatumRodjenja(datumRodjenja);
        p.setTitula(titula);
        return p;
    }

    private Student student(String ime, String prezime, String jmbg, int godina, String brojIndeksa, Grupa grupa, Smer smer)
    {
        Student s = new Student();
        s.setIme(ime);
        s.setPrezime(prezime);
        s.setJmbg(jmbg);
        s.setGodina(godina);
        s.setBrojIndeksa(brojIndeksa);
        s.setGrupa(grupa);
        s.setSmer(smer);
        s.setEmail(EmailGenerator.GenerateStudentEmail(ime,prezime,brojIndeksa));
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

    private void upisi(Student s, Predmet... predmeti)
    {
        for (Predmet p : predmeti)
        {
            studentPredmetRepository.save(studentPredmet(s, p));
        }
    }

    private ProveraZnanja proveraZnanja(Predmet predmet, String naziv, TipProvere tipProvere, BigDecimal prag)
    {
        ProveraZnanja p = new ProveraZnanja();
        p.setPredmet(predmet);
        p.setNaziv(naziv);
        p.setTip(tipProvere);
        p.setPragPoena(prag);
        return p;
    }

    private Termin termin(ProveraZnanja proveraZnanja, Predavac predavac, LocalDate datum, LocalDateTime pocetak, LocalDateTime kraj)
    {
        Termin t = new Termin();
        t.setProvera(proveraZnanja);
        t.setPredavac(predavac);
        t.setDatum(datum);
        t.setPocetak(pocetak);
        t.setKraj(kraj);
        return t;
    }

    private StudentTermin studentTermin(Student student, Termin termin, LocalDateTime ulazak, LocalDateTime predaja)
    {
        StudentTermin t = new StudentTermin();
        t.setStudent(student);
        t.setTermin(termin);
        t.setUlazak(ulazak);
        t.setPredaja(predaja);
        return t;
    }

    private Zadatak zadatak(Termin termin, Short redniBroj, String opis, String pocetniKodURL, BigDecimal maxPoena)
    {
        Zadatak z = new Zadatak();
        z.setTermin(termin);
        z.setRedniBroj(redniBroj);
        z.setOpis(opis);
        z.setPocetniKodUrl(pocetniKodURL);
        z.setMaxPoena(maxPoena);
        return z;
    }

    private Resenje resenje(Zadatak zadatak, StudentTermin studentTermin, String resenjeURL, LocalDateTime krenuo, LocalDateTime predao, BigDecimal poeni)
    {
        Resenje r = new Resenje();
        r.setZadatak(zadatak);
        r.setStudentTermin(studentTermin);
        r.setResenjeUrl(resenjeURL);
        r.setKrenuo(krenuo);
        r.setPredao(predao);
        r.setPoeni(poeni);
        return r;
    }

    private Komentar komentar(Resenje resenje, Predavac predavac, String tekst)
    {
        Komentar k = new Komentar();
        k.setResenje(resenje);
        k.setPredavac(predavac);
        k.setTekst(tekst);
        return k;
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
        Predmet uup   = predmetRepository.save(predmet("0003",  "Uvod u programiranje",                8)); // RN, RI, SI, IS (1. godina)
        Predmet op    = predmetRepository.save(predmet("2012",  "Osnovi programiranja",                8)); // IS, IT (1. godina)
        Predmet oop   = predmetRepository.save(predmet("1003",  "Objektno-orijentisano programiranje", 8)); // RN, RI, SI, IS (1. godina)
        Predmet asprn = predmetRepository.save(predmet("2025A", "Algoritmi i strukture podataka RN",   8)); // RN (2. godina)
        Predmet aspri = predmetRepository.save(predmet("2025B", "Algoritmi i strukture podataka RI",   8)); // RI (2. godina)
        Predmet aspsi = predmetRepository.save(predmet("2025C", "Algoritmi i strukture podataka SI",   8)); // SI (2. godina)
        Predmet aspsm = predmetRepository.save(predmet("8205",  "Algoritmi",                           8)); // SM (1. godina)
        Predmet dsw   = predmetRepository.save(predmet("2002",  "Dizajn softvera",                     8)); // RN, SI (2. godina)
        Predmet osrn  = predmetRepository.save(predmet("2008A", "Operativni sistemi RN",               8)); // RN (2. godina)
        Predmet osri  = predmetRepository.save(predmet("2008B", "Operativni sistemi RI",               8)); // RI (2. godina)
        Predmet ossi  = predmetRepository.save(predmet("2008C", "Operativni sistemi SI",               8)); // SI (2. godina)
        Predmet ossm  = predmetRepository.save(predmet("2008D", "Operativni sistemi SM",               5)); // SM (1. godina)
        Predmet daa   = predmetRepository.save(predmet("2005",  "Dizajn i analiza algoritama",         6)); // RN (2. godina)
        Predmet rm    = predmetRepository.save(predmet("2001",  "Računarske mreže",                    8)); // RN, RI, SI, IS (2. godina)
        Predmet bp    = predmetRepository.save(predmet("4014",  "Baze podataka",                       8)); // RN, SI, IS (2. godina)
        Predmet nbp   = predmetRepository.save(predmet("4300",  "Napredne baze podataka",              6)); // SI, SM (3. godina)
        Predmet rs    = predmetRepository.save(predmet("3004",  "Razvoj softvera",                     6)); // SI (3. godina)
        Predmet ts    = predmetRepository.save(predmet("5022",  "Testiranje softvera",                 6)); // RN, RI, SI (3. godina)
        Predmet ps    = predmetRepository.save(predmet("3002",  "Programiranje sistema",               6)); // SI (3. godina)
        Predmet rwa   = predmetRepository.save(predmet("5974",  "Razvoj veb aplikacija",               6)); // SI, IS (3. godina)
        Predmet rma   = predmetRepository.save(predmet("5026",  "Razvoj mobilnih aplikacija",          6)); // RN, RI, SI (3. godina)
        Predmet pp    = predmetRepository.save(predmet("5972",  "Paradigme programiranja",             6)); // SI (3. godina)
        Predmet rmp   = predmetRepository.save(predmet("5024",  "Razvoj multiplatformskih aplikacija", 6)); // SI (4. godina)
        Predmet rrg   = predmetRepository.save(predmet("5016",  "Razvoj računarskih igara",            6)); // SI (4. godina)
        Predmet csp   = predmetRepository.save(predmet("5973",  "C# programiranje",                    8)); // IT (3. godina)
        Predmet rbs   = predmetRepository.save(predmet("RBS",   "Razvoj bezbednog softvera",           6)); // SI master (2. godina)
        //endregion

        //region predavači
        // https://konvertor.rs/jmbg-validator
        Predavac pr0 = predavacRepository.save(predavac("Petar",    "Petrović",    "2104972720167", LocalDate.of(1972,  4, 21), TipPredavaca.REDOVNI_PROFESOR));
        Predavac pr1 = predavacRepository.save(predavac("Jovana",   "Jovanović",  "2308971927498", LocalDate.of(1971,  8, 23), TipPredavaca.VANREDNI_PROFESOR));
        Predavac pr2 = predavacRepository.save(predavac("Nikola",   "Nikolić",      "2503967132798", LocalDate.of(1967,  3, 25), TipPredavaca.REDOVNI_PROFESOR));
        Predavac pr3 = predavacRepository.save(predavac("Tibor",    "Rácz",            "1208971820075", LocalDate.of(1971,  8, 12), TipPredavaca.REDOVNI_PROFESOR));
        Predavac pr4 = predavacRepository.save(predavac("Lucija",   "Radić",          "1206992355612", LocalDate.of(1992,  6, 12), TipPredavaca.VANREDNI_PROFESOR));
        Predavac pr5 = predavacRepository.save(predavac("Luka",     "Jurić",          "1208977311768", LocalDate.of(1977,  8, 12), TipPredavaca.REDOVNI_PROFESOR));
        Predavac pr6 = predavacRepository.save(predavac("Etaoin",   "Shrdlu",    "2907973850396", LocalDate.of(1973,  7, 29), TipPredavaca.VANREDNI_PROFESOR));
        Predavac pr7 = predavacRepository.save(predavac("Jasmina",  "Marić",          "0408978199908", LocalDate.of(1978,  8,  4), TipPredavaca.REDOVNI_PROFESOR));
        Predavac pr8 = predavacRepository.save(predavac("Milica",   "Milić",          "1809961227140", LocalDate.of(1961,  9, 18), TipPredavaca.VANREDNI_PROFESOR));
        Predavac pr9 = predavacRepository.save(predavac("John",     "Doe",              "1705978861463", LocalDate.of(1978,  5, 17), TipPredavaca.REDOVNI_PROFESOR));

        Predavac as0 = predavacRepository.save(predavac("Miloš",    "Milošević",  "2008995123833", LocalDate.of(1995,  8, 20), TipPredavaca.ASISTENT));
        Predavac as1 = predavacRepository.save(predavac("Filip",    "Filipović",  "2007000101607", LocalDate.of(2000,  7, 20), TipPredavaca.ASISTENT));
        Predavac as2 = predavacRepository.save(predavac("Nikolina", "Marković",    "2809000966720", LocalDate.of(2000,  9, 28), TipPredavaca.ASISTENT));
        Predavac as3 = predavacRepository.save(predavac("Amon",     "Gus",             "2507000172078", LocalDate.of(2000,  7, 25), TipPredavaca.ASISTENT));
        Predavac as4 = predavacRepository.save(predavac("Jana",     "Janošević",  "1511001527116", LocalDate.of(2001, 11, 15), TipPredavaca.ASISTENT));
        Predavac as5 = predavacRepository.save(predavac("Milena",   "Marinkov",    "3007003957603", LocalDate.of(2003,  7, 30), TipPredavaca.ASISTENT));
        Predavac as6 = predavacRepository.save(predavac("Tanja",    "Tasić",         "2809003919766", LocalDate.of(2003,  9, 28), TipPredavaca.ASISTENT));
        Predavac as7 = predavacRepository.save(predavac("Bogdan",   "Bogdanov",    "1501007750741", LocalDate.of(2007,  1, 15), TipPredavaca.ASISTENT));
        Predavac as8 = predavacRepository.save(predavac("Mila",     "Petković",    "2506001465706", LocalDate.of(2001,  6, 25), TipPredavaca.ASISTENT));
        Predavac as9 = predavacRepository.save(predavac("Jelena",   "Mitrović",    "3007005937758", LocalDate.of(2005,  7, 30), TipPredavaca.ASISTENT));

        Predavac dm0 = predavacRepository.save(predavac("Marko",    "Marković",    "2905992921668", LocalDate.of(1992,  5, 29), TipPredavaca.DEMONSTRATOR));
        Predavac dm1 = predavacRepository.save(predavac("Mirka",    "Mirković",    "2910975769929", LocalDate.of(1975, 10, 29), TipPredavaca.DEMONSTRATOR));
        Predavac dm2 = predavacRepository.save(predavac("Gustavo",  "Fring",          "1801991593442", LocalDate.of(1991,  1, 18), TipPredavaca.DEMONSTRATOR));
        Predavac dm3 = predavacRepository.save(predavac("Branko",   "Branković",  "0612001121060", LocalDate.of(2001, 12,  6), TipPredavaca.DEMONSTRATOR));

        /*Predavac ts1 = predavacRepository.save(predavac("Borko",   "Branković",  "0712001121060", LocalDate.of(2001, 12,  7), TipPredavaca.DEMONSTRATOR));
        Predavac ts2 = predavacRepository.save(predavac("Branka",   "Branković",  "0812001121060", LocalDate.of(2001, 12,  8), TipPredavaca.DEMONSTRATOR));
        Predavac ts3 = predavacRepository.save(predavac("Biserka",   "Branković",  "0912001121060", LocalDate.of(2001, 12,  9), TipPredavaca.DEMONSTRATOR));*/
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

        //region Grupa
        Grupa g101 = grupaRepository.save(grupa("101", rn, 1));
        Grupa g102 = grupaRepository.save(grupa("102", rn, 1));
        Grupa g201 = grupaRepository.save(grupa("201", rn, 2));
        Grupa g301 = grupaRepository.save(grupa("301", rn, 3));
        Grupa g401 = grupaRepository.save(grupa("401", rn, 4));
        Grupa g111 = grupaRepository.save(grupa("111", si, 1));
        Grupa g112 = grupaRepository.save(grupa("112", si, 1));
        Grupa g211 = grupaRepository.save(grupa("211", si, 2));
        Grupa g311 = grupaRepository.save(grupa("311", si, 3));
        Grupa g411 = grupaRepository.save(grupa("411", si, 4));
        Grupa g121 = grupaRepository.save(grupa("121", ri, 1));
        Grupa g122 = grupaRepository.save(grupa("122", ri, 1));
        Grupa g221 = grupaRepository.save(grupa("221", ri, 2));
        Grupa g321 = grupaRepository.save(grupa("321", ri, 3));
        Grupa g421 = grupaRepository.save(grupa("421", ri, 4));
        Grupa g131 = grupaRepository.save(grupa("131", is, 1));
        Grupa g1s1 = grupaRepository.save(grupa("1s1", it, 1));
        Grupa g2s1 = grupaRepository.save(grupa("2s1", it, 2));
        Grupa g3s1 = grupaRepository.save(grupa("3s1", it, 3));
        Grupa g1sm1 = grupaRepository.save(grupa("1sm1", sm, 1));
        Grupa g2sm1 = grupaRepository.save(grupa("2sm1", sm, 2));
        //endregion

        //region Student
        List<Student> studenti = new ArrayList<>();
        studenti.add(studentRepository.save(student("Marko","Petrović","0101998710015",1,"1/2021 RN", g101, rn)));
        studenti.add(studentRepository.save(student("Ana","Jovanović","0202999710125",1,"2/2022 RI", g121, ri)));
        studenti.add(studentRepository.save(student("Nikola","Ilić","0303000710230",1,"3/2023 SI", g111, si)));
        studenti.add(studentRepository.save(student("Milica","Stanković","0404001710340",1,"4/2024 IS", g131, is)));
        studenti.add(studentRepository.save(student("Lazar","Pavlović","0505002800416",1,"5/2025 IT", g1s1, it)));
        studenti.add(studentRepository.save(student("Jelena","Nikolić","0606996710500",1,"6/2026 M5", g1sm1, sm)));
        studenti.add(studentRepository.save(student("Đorđe","Đorđević","0707997710629",1,"7/2021 SI", g112, si)));
        studenti.add(studentRepository.save(student("Teodora","Milosavljević","0808003710715",1,"8/2022 IT", g1s1, it)));
        studenti.add(studentRepository.save(student("Ivan","Kostić","0909004710809",1,"9/2023 RN", g102, rn)));
        studenti.add(studentRepository.save(student("Sara","Tomić","1010005710919",1,"10/2024 RI", g122, ri)));
        studenti.add(studentRepository.save(student("Balázs","Kovács","1111999800122",1,"11/2025 IS", g131, is)));
        studenti.add(studentRepository.save(student("Eszter","Nagy","1212000800237",1,"12/2026 SI", g111, si)));
        studenti.add(studentRepository.save(student("Zoltán","Tóth","1301001800349",1,"13/2021 IT", g1s1, it)));
        studenti.add(studentRepository.save(student("Katalin","Szabó","1402998800450",1,"14/2022 M5", g1sm1, sm)));
        studenti.add(studentRepository.save(student("Peter","Horváth","1503999800560",1,"15/2023 RN", g101, rn)));
        studenti.add(studentRepository.save(student("Zuzana","Kováčová","1604000800674",1,"16/2024 RI", g121, ri)));
        studenti.add(studentRepository.save(student("Martin","Novák","1705001800784",1,"17/2025 SI", g112, si)));
        studenti.add(studentRepository.save(student("Jakub","Varga","1806002800894",1,"18/2026 IS", g131, is)));
        studenti.add(studentRepository.save(student("Ivana","Babić","1907003200905",1,"19/2021 IT", g1s1, it)));
        studenti.add(studentRepository.save(student("Luka","Marić","2008004200013",1,"20/2022 RN", g102, rn)));
        studenti.add(studentRepository.save(student("Mario","Horvat","2109005200127",1,"21/2023 RI", g122, ri)));
        studenti.add(studentRepository.save(student("Petra","Kovačević","2210996200231",1,"22/2024 SI", g111, si)));
        studenti.add(studentRepository.save(student("Tomislav","Jurić","2311997200341",1,"23/2025 M5", g1sm1, sm)));
        studenti.add(studentRepository.save(student("Ante","Perić","2412998200451",1,"24/2026 IT", g1s1, it)));
        studenti.add(studentRepository.save(student("Emir","Hadžić","2501999100566",1,"25/2021 IS", g131, is)));
        studenti.add(studentRepository.save(student("Amina","Begović","2602000100678",1,"26/2022 SI", g112, si)));
        studenti.add(studentRepository.save(student("Adnan","Mehmedović","2703001100781",1,"27/2023 RN", g101, rn)));
        studenti.add(studentRepository.save(student("Lejla","Džaferović","2804002100895",1,"28/2024 IT", g1s1, it)));
        studenti.add(studentRepository.save(student("Vuk","Simić","2905003710900",1,"29/2025 RI", g121, ri)));
        studenti.add(studentRepository.save(student("Sofija","Radović","3006004710019",1,"30/2026 SI", g111, si)));
        studenti.add(studentRepository.save(student("Aleksa","Rakić","3107005710122",1,"31/2021 M5", g1sm1, sm)));
        studenti.add(studentRepository.save(student("Kristina","Vuković","0108996710233",1,"32/2022 IS", g131, is)));
        studenti.add(studentRepository.save(student("Uroš","Obradović","0209997710340",1,"33/2023 IT", g1s1, it)));
        studenti.add(studentRepository.save(student("Iva","Lukić","0310998710456",1,"34/2024 RN", g102, rn)));
        studenti.add(studentRepository.save(student("Dušan","Filipović","0411999710560",1,"35/2025 SI", g112, si)));
        studenti.add(studentRepository.save(student("Mila","Gajić","0512000710678",1,"36/2026 RI", g122, ri)));
        studenti.add(studentRepository.save(student("Stefan","Jovičić","0601001710783",1,"37/2021 IT", g1s1, it)));
        studenti.add(studentRepository.save(student("Nevena","Todorović","0702002710897",1,"38/2022 SI", g111, si)));
        studenti.add(studentRepository.save(student("Bojan","Đukić","0803003710909",1,"39/2023 M5", g1sm1, sm)));
        studenti.add(studentRepository.save(student("Andrea","Molnár","0904004800019",1,"40/2024 IS", g131, is)));
        studenti.add(studentRepository.save(student("Dávid","Farkas","1005005800120",1,"41/2025 RN", g101, rn)));
        studenti.add(studentRepository.save(student("Réka","Balogh","1106996800233",1,"42/2026 IT", g1s1, it)));
        studenti.add(studentRepository.save(student("Miroslav","Konečný","1207997800347",1,"43/2021 SI", g112, si)));
        studenti.add(studentRepository.save(student("Lucia","Hudáková","1308998800450",1,"44/2022 RI", g121, ri)));
        studenti.add(studentRepository.save(student("Filip","Mitrović","1409999710565",1,"45/2023 IS", g131, is)));
        studenti.add(studentRepository.save(student("Jovana","Ristić","1510000710675",1,"46/2024 SI", g111, si)));
        studenti.add(studentRepository.save(student("Velibor","Savić","1611001710789",1,"47/2025 IT", g1s1, it)));
        studenti.add(studentRepository.save(student("Dragana","Petrović","1712002710892",1,"48/2026 M5", g1sm1, sm)));
        studenti.add(studentRepository.save(student("Nemanja","Kostić","1801003710906",1,"49/2021 RN", g102, rn)));
        studenti.add(studentRepository.save(student("Tijana","Vasić","1902004710017",1,"50/2022 SI", g112, si)));
        studenti.add(studentRepository.save(student("Stevan","Nešić","1604006711066",2,"66/2025 RN", g201, rn)));
        studenti.add(studentRepository.save(student("Dunja","Popović","1705006711067",2,"67/2025 RN", g201, rn)));
        studenti.add(studentRepository.save(student("Kosta","Milić","1806006711068",2,"68/2025 RN", g201, rn)));
        studenti.add(studentRepository.save(student("Miloš","Stevanović","1907005711075",3,"75/2024 RN", g301, rn)));
        studenti.add(studentRepository.save(student("Teodora","Krstić","2008005711076",3,"76/2024 RN", g301, rn)));
        studenti.add(studentRepository.save(student("Aleksandar","Bogdanović","2109005711077",3,"77/2024 RN", g301, rn)));
        studenti.add(studentRepository.save(student("Luka","Nedeljković","2210004711084",4,"84/2023 RN", g401, rn)));
        studenti.add(studentRepository.save(student("Dragica","Sekulić","2311004711085",4,"85/2023 RN", g401, rn)));
        studenti.add(studentRepository.save(student("Pavle","Tadić","2412004711086",4,"86/2023 RN", g401, rn)));
        studenti.add(studentRepository.save(student("Andrija","Blagojević","2501006711069",2,"69/2025 SI", g211, si)));
        studenti.add(studentRepository.save(student("Ema","Vlahović","2602006711070",2,"70/2025 SI", g211, si)));
        studenti.add(studentRepository.save(student("Nikolina","Stojković","2703006711071",2,"71/2025 SI", g211, si)));
        studenti.add(studentRepository.save(student("Jelisaveta","Mitić","2804005711078",3,"78/2024 SI", g311, si)));
        studenti.add(studentRepository.save(student("Strahinja","Ćirić","2905005711079",3,"79/2024 SI", g311, si)));
        studenti.add(studentRepository.save(student("Višnja","Pešić","3006005711080",3,"80/2024 SI", g311, si)));
        studenti.add(studentRepository.save(student("Mina","Ostojić","0107004711087",4,"87/2023 SI", g411, si)));
        studenti.add(studentRepository.save(student("Rade","Janjić","0208004711088",4,"88/2023 SI", g411, si)));
        studenti.add(studentRepository.save(student("Nina","Kojić","0309004711089",4,"89/2023 SI", g411, si)));
        studenti.add(studentRepository.save(student("Rastko","Lukić","0410006711072",2,"72/2025 RI", g221, ri)));
        studenti.add(studentRepository.save(student("Sanja","Đokić","0511006711073",2,"73/2025 RI", g221, ri)));
        studenti.add(studentRepository.save(student("Marija","Antić","0612006711074",2,"74/2025 RI", g221, ri)));
        studenti.add(studentRepository.save(student("Bogdan","Aćimović","0701005711081",3,"81/2024 RI", g321, ri)));
        studenti.add(studentRepository.save(student("Olivera","Radulović","0802005711082",3,"82/2024 RI", g321, ri)));
        studenti.add(studentRepository.save(student("Danilo","Veselinović","0903005711083",3,"83/2024 RI", g321, ri)));
        studenti.add(studentRepository.save(student("Marko","Kralj","1004004711090",4,"90/2023 RI", g421, ri)));
        studenti.add(studentRepository.save(student("Anđela","Perišić","1105004711091",4,"91/2023 RI", g421, ri)));
        studenti.add(studentRepository.save(student("Igor","Vidić","1206004711092",4,"92/2023 RI", g421, ri)));
        studenti.add(studentRepository.save(student("Mirjana","Lalić","1901006711099",2,"99/2025 IT", g2s1, it)));
        studenti.add(studentRepository.save(student("Dejan","Bjelić","2002006711100",2,"100/2025 IT", g2s1, it)));
        studenti.add(studentRepository.save(student("Vera","Cvijanović","2103006711101",2,"101/2025 IT", g2s1, it)));
        studenti.add(studentRepository.save(student("Gavrilo","Mandić","2204005711102",3,"102/2024 IT", g3s1, it)));
        studenti.add(studentRepository.save(student("Sofija","Knežević","2305005711103",3,"103/2024 IT", g3s1, it)));
        studenti.add(studentRepository.save(student("Ratko","Vujić","2406005711104",3,"104/2024 IT", g3s1, it)));
        studenti.add(studentRepository.save(student("Radmila","Pejić","2810006711108",2,"108/2025 M5", g2sm1, sm)));
        studenti.add(studentRepository.save(student("Svetozar","Maletić","2911006711109",2,"109/2025 M5", g2sm1, sm)));
        studenti.add(studentRepository.save(student("Zlata","Rakonjac","3012006711110",2,"110/2025 M5", g2sm1, sm)));
        //endregion

        //region StudentPredmet
        for (Student s : studenti)
        {
            int g = s.getGodina();

            boolean isRn = s.getSmer().equals(rn);
            boolean isSi = s.getSmer().equals(si);
            boolean isRi = s.getSmer().equals(ri);
            boolean isIs = s.getSmer().equals(is);
            boolean isIt = s.getSmer().equals(it);
            boolean isSm = s.getSmer().equals(sm);

            switch (g)
            {
                case 1:
                    if (isRn || isRi || isSi || isIs)
                    {
                        upisi(s, uup, oop);
                    }
                    if (isIs || isIt)
                    {
                        upisi(s, op);
                    }
                    if (isSm)
                    {
                        upisi(s, aspsm, ossm);
                    }
                    break;
                case 2:
                    if (isRn)
                    {
                        upisi(s, asprn, osrn, daa);
                    }
                    if (isRi)
                    {
                        upisi(s, aspri, osri);
                    }
                    if (isSi)
                    {
                        upisi(s, aspsi, ossi);
                    }
                    if (isRn || isSi)
                    {
                        upisi(s, dsw);
                    }
                    if (isRn || isRi || isSi || isIs)
                    {
                        upisi(s, rm);
                    }
                    if (isRn || isSi || isIs)
                    {
                        upisi(s, bp);
                    }
                    break;
                case 3:
                    if (isSi || isSm)
                    {
                        upisi(s, nbp);
                    }
                    if (isSi)
                    {
                        upisi(s, rs, ps, pp);
                    }
                    if (isRn || isRi || isSi)
                    {
                        upisi(s, ts, rma);
                    }
                    if (isSi || isIs)
                    {
                        upisi(s, rwa);
                    }
                    if (isIt)
                    {
                        upisi(s, csp);
                    }
                    break;
                case 4:
                    if (isSi)
                    {
                        upisi(s, rmp, rrg);
                    }
                    break;
            }
        }
        //endregion

        //region Provera znanja
        ProveraZnanja uupKol = proveraZnanjaRepository.save(proveraZnanja(uup, "Kolokvijum Uvod u Programiranje", TipProvere.KOLOKVIJUM, new BigDecimal("15.00")));
        ProveraZnanja uupIspit = proveraZnanjaRepository.save(proveraZnanja(uup, "Ispit Uvod u Programiranje", TipProvere.ISPIT, new BigDecimal("30.00")));
        ProveraZnanja oopKol = proveraZnanjaRepository.save(proveraZnanja(oop, "Kolokvijum Objektno Orijentisano Programiranje", TipProvere.KOLOKVIJUM, new BigDecimal("20.00")));
        ProveraZnanja bpKol = proveraZnanjaRepository.save(proveraZnanja(bp, "Kolokvijum Baze Podataka",TipProvere.KOLOKVIJUM, new BigDecimal("20.00")));
        //endregion

        //region Termin
        LocalDate datum1 = LocalDate.of(2025, 11, 20);
        LocalDate datum2 = LocalDate.of(2025, 11, 27);
        LocalDate datum3 = LocalDate.of(2025, 12, 4);
        LocalDate datum4 = LocalDate.of(2026, 1, 20);

        Termin uupKolT = terminRepository.save(termin(uupKol, pr1, datum1, LocalDateTime.of(2026, 11, 20, 10, 0), LocalDateTime.of(2026, 11, 20, 11, 30)));
        Termin oopKolT = terminRepository.save(termin(oopKol, pr2, datum2, LocalDateTime.of(2026, 11, 27, 12, 0), LocalDateTime.of(2026, 11, 27, 13, 30)));
        Termin bpKolT = terminRepository.save(termin(bpKol, pr1, datum3, LocalDateTime.of(2026, 12, 4, 9, 0), LocalDateTime.of(2026, 12, 4, 10, 30)));
        Termin uupIspitT = terminRepository.save(termin(uupIspit, pr2, datum4, LocalDateTime.of(2027, 1, 20, 9, 0), LocalDateTime.of(2027, 1, 20, 12, 0)));
        //endregion

        //region Zadatak
        List<Zadatak> uupKolZadaci = new ArrayList<>();
        uupKolZadaci.add(zadatakRepository.save(zadatak(uupKolT, (short) 1, "Ispisati sve parne brojeve u nizu.", "https://git.example.com/uup/kol/z1", new BigDecimal("5.00"))));
        uupKolZadaci.add(zadatakRepository.save(zadatak(uupKolT, (short) 2, "Napisati funkciju koja okreće string.", "https://git.example.com/uup/kol/z2", new BigDecimal("5.00"))));
        uupKolZadaci.add(zadatakRepository.save(zadatak(uupKolT, (short) 3, "Izračunati faktorijel rekurzivno.", "https://git.example.com/uup/kol/z3", new BigDecimal("10.00"))));
        List<Zadatak> oopKolZadaci = new ArrayList<>();
        oopKolZadaci.add(zadatakRepository.save(zadatak(oopKolT, (short) 1, "Implementirati klasu Krug sa metodama za obim i površinu.", "https://git.example.com/oop/kol/z1", new BigDecimal("10.00"))));
        oopKolZadaci.add(zadatakRepository.save(zadatak(oopKolT, (short) 2, "Napraviti hijerarhiju Vozilo -> Automobil, Bicikl.", "https://git.example.com/oop/kol/z2", new BigDecimal("15.00"))));
        List<Zadatak> bpKolZadaci = new ArrayList<>();
        bpKolZadaci.add(zadatakRepository.save(zadatak(bpKolT, (short) 1, "Napisati SELECT upit sa JOIN-om nad tabelama student i ispit.", "https://git.example.com/bp/kol/z1", new BigDecimal("10.00"))));
        bpKolZadaci.add(zadatakRepository.save(zadatak(bpKolT, (short) 2, "Napisati upit sa GROUP BY i HAVING.", "https://git.example.com/bp/kol/z2", new BigDecimal("10.00"))));
        //endregion

        //region TerminStudent, Resenje, Komentar
        List<Student> uupStudenti = studenti.stream()
                .filter(s -> s.getGodina() == 1)
                .filter(s -> s.getSmer().equals(rn) || s.getSmer().equals(ri) || s.getSmer().equals(si))
                .limit(3)
                .toList();
        for(Student s : uupStudenti)
        {
            StudentTermin st = studentTerminRepository.save(studentTermin(s, uupKolT, uupKolT.getPocetak().plusMinutes(2), uupKolT.getKraj().minusMinutes(5)));
            for(Zadatak z : uupKolZadaci)
            {
                Resenje r = resenjeRepository.save(resenje(z, st,"https://git.example.com/resenja/" + s.getId() + "/" + z.getId(), uupKolT.getPocetak().plusMinutes(2), uupKolT.getKraj().minusMinutes(5), BigDecimal.valueOf(3.0)));
                komentarRepository.save(komentar(r, pr1, "Rešenje je delimično tačno, obrati pažnju na granične slučajeve."));
            }
        }
        List<Student> oopStudenti = studenti.stream()
                .filter(s -> s.getGodina() == 1)
                .filter(s -> s.getSmer().equals(rn) || s.getSmer().equals(ri) || s.getSmer().equals(si))
                .skip(3).limit(3)
                .toList();
        for(Student s : oopStudenti)
        {
            StudentTermin st = studentTerminRepository.save(studentTermin(s, oopKolT, oopKolT.getPocetak().plusMinutes(2), oopKolT.getKraj().minusMinutes(5)));
            for(Zadatak z : oopKolZadaci)
            {
                Resenje r = resenjeRepository.save(resenje(z, st,"https://git.example.com/resenja/" + s.getId() + "/" + z.getId(), oopKolT.getPocetak().plusMinutes(2), oopKolT.getKraj().minusMinutes(5), BigDecimal.valueOf(3.0)));
                komentarRepository.save(komentar(r, pr2, "Rešenje je delimično tačno, obrati pažnju na granične slučajeve."));
            }
        }
        List<Student> bpStudenti = studenti.stream()
                .filter(s -> s.getGodina() == 2)
                .filter(s -> s.getSmer().equals(rn) || s.getSmer().equals(si))
                .limit(3)
                .toList();
        for(Student s : bpStudenti)
        {
            StudentTermin st = studentTerminRepository.save(studentTermin(s, bpKolT, bpKolT.getPocetak().plusMinutes(2), bpKolT.getKraj().minusMinutes(5)));
            for(Zadatak z : bpKolZadaci)
            {
                Resenje r = resenjeRepository.save(resenje(z, st,"https://git.example.com/resenja/" + s.getId() + "/" + z.getId(), bpKolT.getPocetak().plusMinutes(2), bpKolT.getKraj().minusMinutes(5), BigDecimal.valueOf(3.0)));
                komentarRepository.save(komentar(r, pr3, "Rešenje je delimično tačno, obrati pažnju na granične slučajeve."));
            }
        }
        //endregion
    }
}
