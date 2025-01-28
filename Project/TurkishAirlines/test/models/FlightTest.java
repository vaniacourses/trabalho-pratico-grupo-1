package models;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FlightTest {

    private Flight flight;

    @Before
    public void setUp() {
        flight = new Flight(
            false, 10, 5, 3, 18,
            "Flight123", new ArrayList<>(), 18, 18,
            "New York", "London",
            Date.valueOf("2025-01-01"), Date.valueOf("2025-01-02"),
            10, 5, 3
        );
    }


    @Test
    public void testGetFlightName() {
        assertEquals("Flight123", flight.getFlightName());
    }


    @Test
    public void testGetOldESeats() {
        assertEquals(10, flight.getOldESeats());
    }


    @Test
    public void testSetOldESeats() {
        flight.setOldESeats(15);
        assertEquals(15, flight.getOldESeats());
    }


    @Test
    public void testIsEmpty() {
        assertTrue(flight.isEmpty());

        flight.setCurrentSeats(10);
        assertFalse(flight.isEmpty());
    }


    @Test
    public void testGetCities() {
        assertEquals("New York", flight.getDepartureCity());
        assertEquals("London", flight.getArrivalCity());
    }


    @Test
    public void testGetDates() {
        assertEquals(Date.valueOf("2025-01-01"), flight.getDepartureDate());
        assertEquals(Date.valueOf("2025-01-02"), flight.getArrivalDate());
    }

    @Test
    public void testSetSeats() {
        ArrayList<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1, flight, null, null));
        seats.add(new Seat(2, flight, null, null));

        flight.setSeats(seats);

        assertNotNull(flight.seats);
        assertEquals(2, flight.seats.size());
        assertEquals(1, flight.seats.get(0).getSeatNumber());
        assertEquals(2, flight.seats.get(1).getSeatNumber());
    }


    @Test
    public void testSetCustomer() {
        ArrayList<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1, flight, null, null));
        seats.add(new Seat(2, flight, null, null));
        flight.setSeats(seats);

        Customer customer = new Customer("John", "john@example.com", null);

        flight.setCustomer(customer);

        assertEquals(customer, seats.get(0).c);
        assertEquals(17, flight.getCurrentSeats());
    }
}
