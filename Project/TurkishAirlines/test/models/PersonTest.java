package models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PersonTest {

    private Person person;

    @Before
    public void setUp() {
        person = new Person("John Doe", "john.doe@example.com") {};
    }

    // Testa se o nome é inicializado corretamente
    @Test
    public void testNameInitialization() {
        assertEquals("John Doe", person.name);
    }

    // Testa se o email é inicializado corretamente
    @Test
    public void testEmailInitialization() {
        assertEquals("john.doe@example.com", person.email);
    }
}