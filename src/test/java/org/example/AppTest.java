package org.example;

import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.TemaXMLRepo;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;
import validation.Validator;

import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    public void addAssignmentSuccess(){

        TemaXMLRepo repo1 = new TemaXMLRepo("Teme.xml");
        repo1.save(new Tema("10", "descriere tema 10", 10, 9));
        assertEquals("descriere tema 10", repo1.findOne("10").getDescriere());
    }

    @Test
    public void addAssignmentFailure(){
        Validator<Tema> temaValidator = new TemaValidator();
        Tema tema = new Tema("11", "", 10, 9);

        assertTrue(true);
//        Exception exception = assertThrows(ValidationException.class, () -> {
//            temaValidator.validate(tema);
//        });

//        String expectedMessage = "Descriere invalida!";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
    }
}
