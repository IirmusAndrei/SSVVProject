package validation;

import domain.Student;

public class StudentValidator implements Validator<Student> {

    /**
     * Valideaza un student
     *
     * @param entity - studentul pe care il valideaza
     * @throws ValidationException - daca studentul nu e valid
     */
    @Override
    public void validate(Student entity) throws ValidationException {
        if (entity.getID().equals("") || entity.getID() == null) {
            throw new ValidationException("Id incorect!");
        }

        if (entity.getNume().equals("") || entity.getNume() == null || entity.getNume().matches(".*\\d.*")) {
            throw new ValidationException("Nume incorect!");
        }

        if (entity.getGrupa() < 111 || entity.getGrupa() > 937) {
            throw new ValidationException("Grupa incorecta!");
        }

        if (entity.getEmail().equals("") || entity.getEmail() == null || (!entity.getEmail().contains("@") && !entity.getEmail().contains("."))) {
            throw new ValidationException("Email incorect!");
        }
    }
}
