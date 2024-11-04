import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.sql.Date;
import java.util.ArrayList;

public class FlightTest {

    private Flight flight;
    private ArrayList<Seat> seats;

    @Before
    public void setUp() {
        seats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            seats.add(new Seat());  // Assumindo que o construtor padrão de Seat existe
        }

        flight = new Flight(
            true, 5, 3, 2, 10, "Flight101", seats, 10, 10,
            "CityA", "CityB", Date.valueOf("2023-12-01"), Date.valueOf("2023-12-01"),
            5, 3, 2
        );
    }

    @Test
    public void testGetFlightName() {
        assertEquals("Flight101", flight.getFlightName());
    }

    @Test
    public void testIsEmptyWhenFull() {
        assertTrue(flight.isEmpty());
    }

    @Test
    public void testIsEmptyWhenNotFull() {
        flight.setCurrentSeats(5);
        assertFalse(flight.isEmpty());
    }

    @Test
    public void testGetAndSetEconomySeats() {
        assertEquals(5, flight.getEconomySeats());
        flight.setEconomySeats(6);
        assertEquals(6, flight.getEconomySeats());
    }

    @Test
    public void testGetAndSetBusinessSeats() {
        assertEquals(3, flight.getBusinessSeats());
        flight.setBusinessSeats(4);
        assertEquals(4, flight.getBusinessSeats());
    }

    @Test
    public void testGetAndSetFirstSeats() {
        assertEquals(2, flight.getFirstSeats());
        flight.setFirstSeats(3);
        assertEquals(3, flight.getFirstSeats());
    }

    @Test
    public void testGetAndSetOldESeats() {
        assertEquals(5, flight.getOldESeats());
        flight.setOldESeats(6);
        assertEquals(6, flight.getOldESeats());
    }

    @Test
    public void testGetAndSetOldBSeats() {
        assertEquals(3, flight.getOldBSeats());
        flight.setOldBSeats(4);
        assertEquals(4, flight.getOldBSeats());
    }

    @Test
    public void testGetAndSetOldFSeats() {
        assertEquals(2, flight.getOldFSeats());
        flight.setOldFSeats(3);
        assertEquals(3, flight.getOldFSeats());
    }

    @Test
    public void testGetAndSetOldTSeats() {
        assertEquals(10, flight.getOldTSeats());
        flight.setOldTSeats(11);
        assertEquals(11, flight.getOldTSeats());
    }

    @Test
    public void testSetAndGetTotalSeats() {
        assertEquals(10, flight.getTotalSeats());
        flight.setTotalSeats(12);
        assertEquals(12, flight.getTotalSeats());
    }

    @Test
    public void testSetAndGetCurrentSeats() {
        assertEquals(10, flight.getCurrentSeats());
        flight.setCurrentSeats(8);
        assertEquals(8, flight.getCurrentSeats());
    }

    @Test
    public void testGetDepartureCity() {
        assertEquals("CityA", flight.getDepartureCity());
    }

    @Test
    public void testGetArrivalCity() {
        assertEquals("CityB", flight.getArrivalCity());
    }

    @Test
    public void testGetDepartureDate() {
        assertEquals(Date.valueOf("2023-12-01"), flight.getDepartureDate());
    }

    @Test
    public void testGetArrivalDate() {
        assertEquals(Date.valueOf("2023-12-01"), flight.getArrivalDate());
    }

    @Test
    public void testSetSeats() {
        ArrayList<Seat> newSeats = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            newSeats.add(new Seat());
        }
        flight.setSeats(newSeats);
        assertEquals(8, flight.seats.size());
    }

    @Test
    public void testSetCustomer() {
        Customer customer = new Customer();  // Assumindo que o construtor padrão de Customer existe
        int initialCurrentSeats = flight.getCurrentSeats();
        
        flight.setCustomer(customer);

        assertEquals(initialCurrentSeats - 1, flight.getCurrentSeats());
        assertEquals(customer, seats.get(initialCurrentSeats - 1).getCustomer());
        assertEquals(seats.get(initialCurrentSeats - 1), customer.getSeat());
    }
}
