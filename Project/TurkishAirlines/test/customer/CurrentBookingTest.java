package customer;

import models.Customer;
import models.Seat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class CurrentBookingTest {

    @InjectMocks
    private CurrentBooking currentBooking;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private Customer customer;

    private ArrayList<Seat> seats;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        seats = new ArrayList<>();
        seats.add(new Seat(1, null, null, customer));
        seats.add(new Seat(2, null, null, customer));

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("customer")).thenReturn(customer);
        when(customer.getCurrentBooking()).thenReturn(seats);
        when(request.getRequestDispatcher("CurrentBooking.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetSetsSeatsAndForwards() throws ServletException, IOException {
        currentBooking.doGet(request, response);

        verify(request).setAttribute("seats", seats);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostSetsSeatsAndForwards() throws ServletException, IOException {
        currentBooking.doPost(request, response);

        verify(request).setAttribute("seats", seats);

        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = NullPointerException.class)
    public void testDoGetThrowsExceptionForNullCustomer() throws ServletException, IOException {
        when(session.getAttribute("customer")).thenReturn(null);

        currentBooking.doGet(request, response);
    }

    @Test(expected = NullPointerException.class)
    public void testDoPostThrowsExceptionForNullCustomer() throws ServletException, IOException {
        when(session.getAttribute("customer")).thenReturn(null);

        currentBooking.doPost(request, response);
    }
}