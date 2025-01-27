package admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class LogoutManagerTest {

    @InjectMocks
    private LogoutManager logoutManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession(false)).thenReturn(session);
        when(request.getRequestDispatcher("home.jsp")).thenReturn(requestDispatcher);
    }

    // Testa se a sessão é invalidada
    @Test
    void testSessionIsInvalidated() throws ServletException, IOException {
        logoutManager.doGet(request, response);
        verify(session).invalidate();
    }

    // Testa se o redirecionamento é feito corretamente
    @Test
    void testRedirectIsDone() throws ServletException, IOException {
        logoutManager.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    // Testa se o método doGet não lança exceções
    @Test
    void testDoGetDoesNotThrowException() {
        assertDoesNotThrow(() -> logoutManager.doGet(request, response));
    }

    // Testa se a sessão não é invalidada se não existir
    @Test
    void testSessionNotInvalidatedIfNotExists() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(null);
        logoutManager.doGet(request, response);
        verify(session, never()).invalidate();
    }
}