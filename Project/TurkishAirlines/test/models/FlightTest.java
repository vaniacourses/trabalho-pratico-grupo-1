package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {

    private Flight flight;
    private ArrayList<Seat> seats;

    @BeforeEach
    void setUp() {
        seats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            seats.add(new Seat(i + 1, null, null, null));
        }

        flight = new Flight(
            true, 5, 3, 2, 10, "Flight101", seats, 10, 10,
            "CityA", "CityB", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()),
            5, 3, 2
        );
    }

    // Testa getter de nome do voo
    @Test
    void testGetFlightName() {
        assertEquals("Flight101", flight.getFlightName());
    }

    // Testa se o voo está vazio quando cheio
    @Test
    void testIsEmptyWhenFull() {
        flight.setCurrentSeats(10);
        assertTrue(flight.isEmpty());
    }

    // Testa se o voo não está vazio quando não cheio
    @Test
    void testIsEmptyWhenNotFull() {
        flight.setCurrentSeats(5);
        assertFalse(flight.isEmpty());
    }

    // Testa getter e setter de assentos econômicos
    @Test
    void testGetAndSetEconomySeats() {
        assertEquals(5, flight.getEconomySeats());
        flight.setEconomySeats(6);
        assertEquals(6, flight.getEconomySeats());
    }

    // Testa getter e setter de assentos de negócios
    @Test
    void testGetAndSetBusinessSeats() {
        assertEquals(3, flight.getBusinessSeats());
        flight.setBusinessSeats(4);
        assertEquals(4, flight.getBusinessSeats());
    }

    // Testa getter e setter de assentos de primeira classe
    @Test
    void testGetAndSetFirstSeats() {
        assertEquals(2, flight.getFirstSeats());
        flight.setFirstSeats(3);
        assertEquals(3, flight.getFirstSeats());
    }

    // Testa getter e setter de assentos antigos econômicos
    @Test
    void testGetAndSetOldESeats() {
        assertEquals(5, flight.getOldESeats());
        flight.setOldESeats(6);
        assertEquals(6, flight.getOldESeats());
    }

    // Testa getter e setter de assentos antigos de negócios
    @Test
    void testGetAndSetOldBSeats() {
        assertEquals(3, flight.getOldBSeats());
        flight.setOldBSeats(4);
        assertEquals(4, flight.getOldBSeats());
    }

    // Testa getter e setter de assentos antigos de primeira classe
    @Test
    void testGetAndSetOldFSeats() {
        assertEquals(2, flight.getOldFSeats());
        flight.setOldFSeats(3);
        assertEquals(3, flight.getOldFSeats());
    }

    // Testa getter e setter de assentos antigos totais
    @Test
    void testGetAndSetOldTSeats() {
        assertEquals(10, flight.getOldTSeats());
        flight.setOldTSeats(11);
        assertEquals(11, flight.getOldTSeats());
    }

    // Testa getter e setter de assentos totais
    @Test
    void testSetAndGetTotalSeats() {
        assertEquals(10, flight.getTotalSeats());
        flight.setTotalSeats(12);
        assertEquals(12, flight.getTotalSeats());
    }

    // Testa getter e setter de assentos atuais
    @Test
    void testSetAndGetCurrentSeats() {
        assertEquals(10, flight.getCurrentSeats());
        flight.setCurrentSeats(8);
        assertEquals(8, flight.getCurrentSeats());
    }

    // Testa getter de cidade de partida
    @Test
    void testGetDepartureCity() {
        assertEquals("CityA", flight.getDepartureCity());
    }

    // Testa getter de cidade de chegada
    @Test
    void testGetArrivalCity() {
        assertEquals("CityB", flight.getArrivalCity());
    }

    // Testa getter de data de partida
    @Test
    void testGetDepartureDate() {
        assertNotNull(flight.getDepartureDate());
    }

    // Testa getter de data de chegada
    @Test
    void testGetArrivalDate() {
        assertNotNull(flight.getArrivalDate());
    }

    // Testa setter de assentos
    @Test
    void testSetSeats() {
        ArrayList<Seat> newSeats = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            newSeats.add(new Seat(i + 1, null, null, null));
        }
        flight.setSeats(newSeats);
        assertEquals(8, flight.getSeats().size());
    }

    // Testa setter de cliente
    @Test
    void testSetCustomer() {
        Customer customer = new Customer("John Doe", "john.doe@example.com", new ArrayList<>());
        int initialCurrentSeats = flight.getCurrentSeats();
        
        flight.setCustomer(customer);

        assertEquals(initialCurrentSeats - 1, flight.getCurrentSeats());
        assertEquals(customer, seats.get(initialCurrentSeats - 1).getCustomer());
        assertEquals(seats.get(initialCurrentSeats - 1), customer.getCurrentBooking().get(0));
    }
}