package org.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class BigBangTest {

    Service service;
    StudentXMLRepo studentXMLRepo;
    StudentValidator studentValidator;
    TemaXMLRepo temaXMLRepo;
    TemaValidator temaValidator;
    NotaXMLRepo notaXMLRepo;
    NotaValidator notaValidator;

    @Before
    public void init() {
        studentXMLRepo = new StudentXMLRepo("fisiere/Studenti.xml");
        studentValidator = new StudentValidator();
        temaXMLRepo = new TemaXMLRepo("fisiere/Teme.xml");
        temaValidator = new TemaValidator();
        notaXMLRepo = new NotaXMLRepo("fisiere/Note.xml");
        notaValidator = new NotaValidator(studentXMLRepo, temaXMLRepo);
        service = new Service(studentXMLRepo, studentValidator, temaXMLRepo, temaValidator, notaXMLRepo, notaValidator);
    }

    @Test
    public void addStudentSuccess() {
        service.addStudent(new Student("67", "name", 934, "abc@mail.com"));
        assertEquals("name", studentXMLRepo.findOne("67").getNume());
    }

    @Test
    public void addAssignmentSuccess() {
        service.addTema(new Tema("88", "descriere tema 88", 5, 4));
        assertEquals("descriere tema 88", temaXMLRepo.findOne("88").getDescriere());
    }

    @Test
    public void addGradeSuccess() {
        service.addNota(new Nota("48", "67", "88", 7.5, LocalDate.now()), "feedback");

        assertEquals(5, notaXMLRepo.findOne("48").getNota(), 0.01);
    }

    @Test
    public void addGradeSuccess_Integration() {
        addStudentSuccess();
        addAssignmentSuccess();
        addGradeSuccess();
    }
}
