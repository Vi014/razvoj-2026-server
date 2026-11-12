package com.example.lmsserver.util;

import com.example.lmsserver.klase.Predavac;
import com.example.lmsserver.klase.Student;

import java.text.Normalizer;

public class EmailGenerator
{
    public static String GenerateStudentEmail(String ime, String prezime, String brojIndeksa)
    {
        String email;
        Character prvoSlovoImena = ime.charAt(0);
        String[] delovi = brojIndeksa.split(" ");
        String[] brojGodina = delovi[0].split("/");
        String broj = brojGodina[0];
        String godina = brojGodina[1];
        String poslednjeDveCifreGodine = godina.substring(godina.length() - 2);
        String smer = delovi[1].toLowerCase();
        email = prvoSlovoImena + prezime.toLowerCase() + broj + poslednjeDveCifreGodine + smer + "@raf.rs";
        return normalize(email);
    }

    public static String GenerateStudentEmail(Student student)
    {
        return GenerateStudentEmail(student.getIme(), student.getPrezime(), student.getBrojIndeksa());
    }

    public static String GeneratePredavacEmail(String ime, String prezime)
    {
        // TODO: provera da li mejl već postoji (dva predavača sa istim prvim slovom imena i istim prezimenom) te generisanje novog mejla
        // IDEJA: rekurzivna pomoćna funkcija. dodaš donju crtu između prvog i drugog karaktera. ako i taj mejl i dalje postoji, ponavljaš dok ne napraviš originalni mejl
        String email;
        Character prvoSlovoImena = ime.charAt(0);
        email = prvoSlovoImena + prezime.toLowerCase() + "@raf.rs";
        return normalize(email);
    }

    public static String GeneratePredavacEmail(Predavac predavac)
    {
        return GeneratePredavacEmail(predavac.getIme(), predavac.getPrezime());
    }

    private static String normalize(String input) {
        String output = input.toLowerCase();
        output = output.replace("đ", "dj"); // Đ je jedino slovo koje menjamo digrafom umesto da ga šišamo

        /*
            "šišanje" slova
            normalizer razdvaja slovo u dva karaktera, "ošišano" slovo i dijakritiku, replaceAll brise tu odvojenu dijakritiku
            ovaj pristup je bolji od suvog regexa jer ovo funkcioniše za sve jezike, dok bi za regex morali za svaki jezik ručno dodavati slova
            npr. ok implementiramo ovo za srpska i mađarska prezimena, al onda nam se upiše neko iz španije/latinoamerike sa ñ u imenu
        */
        output = Normalizer.normalize(output, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return output;
    }
}
