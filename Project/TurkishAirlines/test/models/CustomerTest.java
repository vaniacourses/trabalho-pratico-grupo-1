package models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerTest {

    private Customer customer;
    private Seat seat;

    @Before
    public void setUp() {
        customer = new Customer("John Doe", "john.doe@example.com", null);

        seat = new Seat(12, null, null, null);
    }

    @Test
    public void testGetCurrentBooking() {
        assertNull(customer.getCurrentBooking());

        customer.setSeat(seat);

        assertNotNull(customer.getCurrentBooking());
        assertEquals(1, customer.getCurrentBooking().size());
        assertEquals(seat, customer.getCurrentBooking().get(0));
    }

    @Test
    public void testSetSeat() {
        customer.setSeat(seat);

        assertNotNull(customer.getCurrentBooking());
        assertTrue(customer.getCurrentBooking().contains(seat));
    }


    @Test
    public void testGetEmail() {
        assertEquals("john.doe@example.com", customer.getEmail());
    }

    @Test
    public void testSetMultipleSeats() {
        Seat seat1 = new Seat(1, null, null, null);
        Seat seat2 = new Seat(2, null, null, null);

        customer.setSeat(seat);
        customer.setSeat(seat1);
        customer.setSeat(seat2);

        assertEquals(3, customer.getCurrentBooking().size());
        assertTrue(customer.getCurrentBooking().contains(seat));
        assertTrue(customer.getCurrentBooking().contains(seat1));
        assertTrue(customer.getCurrentBooking().contains(seat2));
    }
}
