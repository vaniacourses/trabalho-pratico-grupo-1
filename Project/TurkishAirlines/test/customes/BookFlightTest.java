package customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getRequestDispatcher("BookFlight.jsp")).thenReturn(requestDispatcher);
    }

    // Testa se o método doGet redireciona corretamente
    @Test
    void testDoGetRedirectsCorrectly() throws ServletException, IOException {
        bookFlight.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    // Testa se o método doGet chama o dispatcher
    @Test
    void testDoGetCallsDispatcher() throws ServletException, IOException {
        bookFlight.doGet(request, response);
        verify(request).getRequestDispatcher("BookFlight.jsp");
    }

    // Testa se o método doGet não chama sendRedirect
    @Test
    void testDoGetDoesNotCallSendRedirect() throws ServletException, IOException {
        bookFlight.doGet(request, response);
        verify(response, never()).sendRedirect(anyString());
    }

    // Testa se o método doGet não lança exceções
    @Test
    void testDoGetDoesNotThrowException() {
        assertDoesNotThrow(() -> bookFlight.doGet(request, response));
    }
}