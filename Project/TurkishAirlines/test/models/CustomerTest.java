package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class CustomerTest {

    private Customer customer;
    private Seat seat;

    @BeforeEach
    void setUp() {
        seat = new Seat(1, null, null, null);
        ArrayList<Seat> seats = new ArrayList<>();
        seats.add(seat);
        customer = new Customer("John Doe", "john.doe@example.com", seats);
    }

    // Testa getter de email
    @Test
    void testGetEmail() {
        assertEquals("john.doe@example.com", customer.getEmail());
    }

    // Testa getter de nome
    @Test
    void testGetName() {
        assertEquals("John Doe", customer.getName());
    }

    // Testa getter de assentos atuais
    @Test
    void testGetCurrentBooking() {
        assertEquals(1, customer.getCurrentBooking().size());
        assertEquals(seat, customer.getCurrentBooking().get(0));
    }

    // Testa setter de assento
    @Test
    void testSetSeat() {
        Seat newSeat = new Seat(2, null, null, null);
        customer.setSeat(newSeat);
        assertEquals(2, customer.getCurrentBooking().size());
        assertEquals(newSeat, customer.getCurrentBooking().get(1));
    }

    // Testa se a lista de assentos é inicializada corretamente
    @Test
    void testSetSeatInitializesList() {
        Customer newCustomer = new Customer("Jane Doe", "jane.doe@example.com", null);
        Seat newSeat = new Seat(2, null, null, null);
        newCustomer.setSeat(newSeat);
        assertEquals(1, newCustomer.getCurrentBooking().size());
        assertEquals(newSeat, newCustomer.getCurrentBooking().get(0));
    }
}