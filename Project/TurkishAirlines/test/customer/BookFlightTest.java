package customer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.junit.Assert.fail;



import static org.mockito.Mockito.*;

public class BookFlightTest {

    @InjectMocks
    private BookFlight bookFlight;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher("BookFlight.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetRedirectsCorrectly() throws ServletException, IOException {
        bookFlight.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetCallsDispatcher() throws ServletException, IOException {
        bookFlight.doGet(request, response);
        verify(request).getRequestDispatcher("BookFlight.jsp");
    }

    @Test
    public void testDoGetDoesNotCallSendRedirect() throws ServletException, IOException {
        bookFlight.doGet(request, response);
        verify(response, never()).sendRedirect(anyString());
    }

    @Test
    public void testDoGetDoesNotThrowException() {
        try {
            bookFlight.doGet(request, response);
        } catch (Exception e) {
            fail("O método doGet não deve lançar exceção");
        }
    }
}