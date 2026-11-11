package com.example.lmsserver.util;

import com.example.lmsserver.klase.ProveraZnanja;
import com.example.lmsserver.klase.Student;
import com.example.lmsserver.kontroleri.request.ProveraZnanjaRequest;
import com.example.lmsserver.kontroleri.request.StudentRequest;
import com.example.lmsserver.kontroleri.response.ProveraZnanjaResponse;
import com.example.lmsserver.kontroleri.response.StudentResponse;

public class Konverteri {
    public static Student StudentRequestToStudent(StudentRequest studentRequest) {
        Student student = new Student();
        student.setIme(studentRequest.getIme());
        student.setPrezime(studentRequest.getPrezime());
        student.setEmail(studentRequest.getEmail());
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

    public static ProveraZnanja ProveraZnanjaRequestToProveraZnanja(ProveraZnanjaRequest request) {
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
}
