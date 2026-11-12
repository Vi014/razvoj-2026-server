package com.example.lmsserver.util;

import com.example.lmsserver.klase.*;
import com.example.lmsserver.kontroleri.request.ProveraZnanjaRequest;
import com.example.lmsserver.kontroleri.request.StudentRequest;
import com.example.lmsserver.kontroleri.request.TerminRequest;
import com.example.lmsserver.kontroleri.request.ZadatakRequest;
import com.example.lmsserver.kontroleri.response.*;

public class Konverteri {
    public static Student StudentRequestToStudent(StudentRequest studentRequest) {
        Student student = new Student();
        student.setIme(studentRequest.getIme());
        student.setPrezime(studentRequest.getPrezime());
        student.setJmbg(studentRequest.getJmbg());
        student.setGodina(studentRequest.getGodina());
        student.setBrojIndeksa(studentRequest.getBrojIndeksa());
        return student;
    }

    public static StudentResponse StudentToStudentResponse(Student student) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setIme(student.getIme());
        studentResponse.setPrezime(student.getPrezime());
        studentResponse.setEmail(student.getEmail());
        studentResponse.setJmbg(student.getJmbg());
        studentResponse.setGodina(student.getGodina());
        studentResponse.setBrojIndeksa(student.getBrojIndeksa());
        if(student.getGrupa() != null) {
            studentResponse.setGrupaOznaka(student.getGrupa().getOznaka());
        }
        studentResponse.setSmerNaziv(student.getSmer().getNaziv());
        return studentResponse;
    }

    public static ProveraZnanja ProveraZnanjaRequestToProveraZnanja(ProveraZnanjaRequest request)
    {
        ProveraZnanja provera = new ProveraZnanja();
        provera.setNaziv(request.getNaziv().trim());
        provera.setTip(request.getTip());
        provera.setPragPoena(request.getPragPoena());
        return provera;
    }

    public static ProveraZnanjaResponse ProveraZnanjaToProveraZnanjaResponse(ProveraZnanja provera)
    {
        ProveraZnanjaResponse response = new ProveraZnanjaResponse();
        response.setId(provera.getId());
        response.setPredmetId(provera.getPredmet().getId());
        response.setPredmetSifra(provera.getPredmet().getSifra());
        response.setPredmetNaziv(provera.getPredmet().getNaziv());
        response.setNaziv(provera.getNaziv());
        response.setTip(provera.getTip());
        response.setPragPoena(provera.getPragPoena());
        return response;
    }

    public static Zadatak ZadatakRequestToZadatak(ZadatakRequest request)
    {
        Zadatak zadatak = new Zadatak();
        zadatak.setRedniBroj(request.getRedniBroj().shortValue());
        zadatak.setOpis(request.getOpis());
        zadatak.setPocetniKodUrl(request.getPocetniKodUrl());
        zadatak.setMaxPoena(request.getMaxPoena());
        return zadatak;
    }

    public static ZadatakResponse ZadatakToZadatakResponse(Zadatak zadatak)
    {
        ZadatakResponse response = new ZadatakResponse();
        response.setId(zadatak.getId());
        response.setTerminId(zadatak.getTermin().getId());
        response.setRedniBroj(zadatak.getRedniBroj().intValue());
        response.setOpis(zadatak.getOpis());
        response.setPocetniKodUrl(zadatak.getPocetniKodUrl());
        response.setMaxPoena(zadatak.getMaxPoena());
        return response;
    }

    public static Termin TerminRequestToTermin(TerminRequest request)
    {
        Termin termin = new Termin();
        termin.setDatum(request.getPocetak().toLocalDate());  // datum je izveden iz pocetka
        termin.setPocetak(request.getPocetak());
        termin.setKraj(request.getKraj());
        return termin;
    }

    public static TerminResponse TerminToTerminResponse(Termin termin)
    {
        TerminResponse response = new TerminResponse();
        response.setId(termin.getId());
        response.setProveraId(termin.getProvera().getId());
        response.setProveraNaziv(termin.getProvera().getNaziv());
        response.setPredmetNaziv(termin.getProvera().getPredmet().getNaziv());
        if (termin.getPredavac() != null) {
            response.setPredavacId(termin.getPredavac().getId());
            response.setPredavacImePrezime(termin.getPredavac().getIme() + " " + termin.getPredavac().getPrezime());
        }
        response.setDatum(termin.getDatum());
        response.setPocetak(termin.getPocetak());
        response.setKraj(termin.getKraj());
        return response;
    }

    public static PrijavaResponse StudentTerminToPrijavaResponse(StudentTermin prijava)
    {
        PrijavaResponse response = new PrijavaResponse();
        response.setId(prijava.getId());
        response.setTerminId(prijava.getTermin().getId());
        response.setStudentId(prijava.getStudent().getId());
        response.setBrojIndeksa(prijava.getStudent().getBrojIndeksa());
        response.setStudentIme(prijava.getStudent().getIme());
        response.setStudentPrezime(prijava.getStudent().getPrezime());
        response.setStatus(prijava.getStatus());
        response.setUlazak(prijava.getUlazak());
        response.setPredaja(prijava.getPredaja());
        return response;
    }

    public static ResenjeResponse ResenjeToResenjeResponse(Resenje resenje)
    {
        ResenjeResponse response = new ResenjeResponse();
        response.setId(resenje.getId());
        response.setTerminId(resenje.getStudentTermin().getId());
        response.setZadatakId(resenje.getZadatak().getId());
        response.setZadatakRedniBroj(resenje.getZadatak().getRedniBroj().intValue());
        response.setMaxPoena(resenje.getZadatak().getMaxPoena());
        response.setStudentId(resenje.getStudentTermin().getStudent().getId());
        response.setBrojIndeksa(resenje.getStudentTermin().getStudent().getBrojIndeksa());
        response.setResenjeUrl(resenje.getResenjeUrl());
        response.setKrenuo(resenje.getKrenuo());
        response.setPredao(resenje.getPredao());
        response.setPoeni(resenje.getPoeni());
        return response;
    }

    public static KomentarResponse KomentarToKomentarResponse(Komentar komentar)
    {
        KomentarResponse response = new KomentarResponse();
        response.setId(komentar.getId());
        response.setResenjeId(komentar.getResenje().getId());
        response.setPredavacId(komentar.getPredavac().getId());
        response.setPredavacImePrezime(komentar.getPredavac().getIme() + " " + komentar.getPredavac().getPrezime());
        response.setTekst(komentar.getTekst());
        return response;
    }
}
