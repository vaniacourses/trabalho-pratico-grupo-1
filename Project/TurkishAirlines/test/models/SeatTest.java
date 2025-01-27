package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SeatTest {

    private Seat seat;
    private Flight flight;
    private Features features;
    private Customer customer;

    @BeforeEach
    void setUp() {
        flight = mock(Flight.class);
        features = mock(Features.class);
        customer = mock(Customer.class);
        seat = new Seat(1, flight, features, customer);
    }

    // Testa getter de número do assento
    @Test
    void testGetSeatNumber() {
        assertEquals(1, seat.getSeatNumber());
    }

    // Testa getter de voo
    @Test
    void testGetFlight() {
        assertEquals(flight, seat.getFlight());
    }

    // Testa setter de cliente
    @Test
    void testSetCustomer() {
        Customer newCustomer = new Customer("Jane Doe", "jane.doe@example.com", null);
        seat.setCustomer(newCustomer);
        assertEquals(newCustomer, seat.getCustomer());
    }

    // Testa getter de cliente
    @Test
    void testGetCustomer() {
        assertEquals(customer, seat.getCustomer());
    }

    // Testa se o assento está disponível
    @Test
    void testIsAvailable() {
        seat.setCustomer(null);
        assertTrue(seat.isAvailable());
    }

    // Testa se o assento não está disponível
    @Test
    void testIsNotAvailable() {
        assertFalse(seat.isAvailable());
    }
}