package org.example;

import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.*;

import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for simple App.
 */
public class AppTest {

    Service service;
    StudentXMLRepo studentXMLRepo;
    StudentValidator studentValidator;
    TemaXMLRepo temaXMLRepo;
    TemaValidator temaValidator;
    NotaXMLRepo notaXMLRepo;
    NotaValidator notaValidator;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

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

    @After
    public void destroy(){
        service.deleteStudent("id");
        service.deleteTema("1");

    }

// =========================================================================================================
//                                              STUDENT
// =========================================================================================================

    @Test
    public void addStudentSuccess() {
        assertNull(service.addStudent(new Student("id", "name", 934, "abc@mail.com")));
        service.deleteStudent("id");
    }

    @Test
    public void addStudent_NullId() {
        exceptionRule.expect(NullPointerException.class);

        service.addStudent(new Student(null, "name", 934, "abc@mail.com"));
    }

    @Test
    public void addStudent_EmptyId() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Id incorect!");

        service.addStudent(new Student("", "name", 934, "abc@mail.com"));
    }

    @Test
    public void addStudent_NullName() {
        exceptionRule.expect(NullPointerException.class);

        service.addStudent(new Student("id", null, 934, "abc@mail.com"));
    }

    @Test
    public void addStudent_EmptyName() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Nume incorect!");

        service.addStudent(new Student("id", "", 934, "abc@mail.com"));
    }

    @Test
    public void addStudent_NameContainsNumbers() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Nume incorect!");

        service.addStudent(new Student("id", "nume1", 934, "abc@mail.com"));
    }

    @Test
    public void addStudent_NullGroup() {
        exceptionRule.expect(NumberFormatException.class);

        service.addStudent(new Student("id", "nume1", Integer.parseInt(null), "abc@mail.com"));
    }

    @Test
    public void addStudent_GroupNotNaturalNumber() {
        exceptionRule.expect(NumberFormatException.class);

        service.addStudent(new Student("id", "nume", Integer.parseInt("934.5"), "abc@mail.com"));
    }

    @Test
    public void addStudent_GroupOutOfBoundary() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Grupa incorecta!");

        service.addStudent(new Student("id", "nume", 110, "abc@mail.com"));
    }

    @Test
    public void addStudent_NullEmail() {
        exceptionRule.expect(NullPointerException.class);

        service.addStudent(new Student("id", "nume", 934, null));
    }

    @Test
    public void addStudent_EmptyEmail() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Email incorect!");

        service.addStudent(new Student("id", "nume", 934, ""));
    }

    @Test
    public void addStudent_EmailWrongFormat() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Email incorect!");

        service.addStudent(new Student("id", "nume", 934, "email"));
    }

// =========================================================================================================
//                                              ASSIGNMENT
// =========================================================================================================

    @Test
    public void addAssignmentSuccess() {

        TemaXMLRepo repo1 = new TemaXMLRepo("fisiere/Teme.xml");
        repo1.save(new Tema("10", "descriere tema 10", 10, 9));
        assertEquals("descriere tema 10", repo1.findOne("10").getDescriere());
    }

    @Test
    public void addAssignment_NullAssignmentNumber() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Numar tema invalid!");

        service.addTema(new Tema(null, "descriere", 4, 3));
    }

    @Test
    public void addAssignment_EmptyAssignmentNumber() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Numar tema invalid!");

        service.addTema(new Tema("", "descriere", 4, 3));
    }

    @Test
    public void addAssignment_NullDescription() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Descriere invalida!");

        service.addTema(new Tema("1", null, 4, 3));
    }

    @Test
    public void addAssignment_EmptyDescription() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Descriere invalida!");

        service.addTema(new Tema("1", "", 4, 3));
    }

    @Test
    public void addAssignment_DeadlineOutOfBoundary() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Deadlineul trebuie sa fie intre 1-14.");

        service.addTema(new Tema("1", "descriere", 15, 3));
    }

    @Test
    public void addAssignment_DeadlineNotNaturalNumber() {
        exceptionRule.expect(NumberFormatException.class);

        service.addTema(new Tema("1", "descriere", Integer.parseInt("12.5"), 3));
    }

    @Test
    public void addAssignment_DueOutOfBoundary() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Saptamana primirii trebuie sa fie intre 1-14.");

        service.addTema(new Tema("1", "descriere", 4, 15));
    }

    @Test
    public void addAssignment_DueNotNaturalNumber() {
        exceptionRule.expect(NumberFormatException.class);

        service.addTema(new Tema("1", "descriere", 4, Integer.parseInt("2.5")));
    }

    @Test
    public void addAssignment_DeadlineBeforeDue() {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Saptamana primirii trebuie sa fie inaintea deadlineului.");

        service.addTema(new Tema("1", "descriere", 4, 5));
    }

//
//    @Test
//    public void addAssignmentFailure() {
//        Validator<Tema> temaValidator = new TemaValidator();
//        Tema tema = new Tema("11", "", 10, 9);
//
//        assertTrue(true);
////        Exception exception = assertThrows(ValidationException.class, () -> {
////            temaValidator.validate(tema);
////        });
//
////        String expectedMessage = "Descriere invalida!";
////        String actualMessage = exception.getMessage();
////
////        assertTrue(actualMessage.contains(expectedMessage));
//    }
}
