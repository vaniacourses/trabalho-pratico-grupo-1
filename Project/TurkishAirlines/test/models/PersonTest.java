package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person("John Doe", "john.doe@example.com") {};
    }

    // Testa se o nome é inicializado corretamente
    @Test
    void testNameInitialization() {
        assertEquals("John Doe", person.name);
    }

    // Testa se o email é inicializado corretamente
    @Test
    void testEmailInitialization() {
        assertEquals("john.doe@example.com", person.email);
    }
}