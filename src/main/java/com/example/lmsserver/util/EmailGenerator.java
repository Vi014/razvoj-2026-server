package com.example.lmsserver.util;

public class EmailGenerator
{
    public String GenerateStudentEmail(String ime, String prezime, String brojIndeksa)
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
        return email;
    }

    public String GeneratePredavacEmail(String ime, String prezime)
    {
        String email;
        Character prvoSlovoImena = ime.charAt(0);
        email = prvoSlovoImena + prezime.toLowerCase() + "@raf.rs";
        return email;
    }
}
