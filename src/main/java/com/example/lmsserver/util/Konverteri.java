package com.example.lmsserver.util;

import com.example.lmsserver.klase.Student;
import com.example.lmsserver.kontroleri.request.StudentRequest;
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
}
