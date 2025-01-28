package models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SeatTest {

    private Seat seat;
    private Flight flight;
    private Customer customer;

    @Before
    public void setUp() {
        flight = new Flight(
            false, 10, 5, 3, 18,
            "Flight123", null, 18, 18,
            "New York", "London", null, null,
            10, 5, 3
        );

        customer = new Customer("John Doe", "john.doe@example.com", null);
        
        seat = new Seat(12, flight, null, customer);
    }


    @Test
    public void testGetFlight() {
        assertEquals(flight, seat.getFlight());
    }


    @Test
    public void testSetCustomer() {
        Customer newCustomer = new Customer("Jane Doe", "jane.doe@example.com", null);

        seat.setCustomer(newCustomer);

        assertEquals(newCustomer, seat.c);
    }


    @Test
    public void testGetSeatNumber() {
        assertEquals(12, seat.getSeatNumber());
    }
}
