package admin;

import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
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

    public LogoutManager logoutManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;
    
    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Before
    public void setUp() {
        logoutManager = new LogoutManager();
        MockitoAnnotations.openMocks(this);
                
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("home.jsp")).thenReturn(requestDispatcher);
    }

    // Testa se a sessão é invalidada
    @Test
    public void testSessionIsInvalidated() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(session);  // Configura o mock para retornar a sessão mockada
        logoutManager.doGet(request, response);
        verify(session).invalidate();
    }

    // Testa se o redirecionamento é feito corretamente
    @Test
    public void testRedirectIsDone() throws ServletException, IOException {
        logoutManager.doGet(request, response);
        verify(response).sendRedirect("home.jsp"); // Verifica se o forward foi chamado
    }

    // Testa se o método doGet não lança exceções
    @Test
    public void testDoGetDoesNotThrowException() {
        try {
            logoutManager.doGet(request, response);
        } catch (Exception e) {
            fail("Esperava-se que o método não lançasse exceções, mas uma exceção foi lançada: " + e.getMessage());
        }
    }

    // Testa se a sessão não é invalidada se não existir
    @Test
    public void testSessionNotInvalidatedIfNotExists() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(null); // Simula que não existe sessão
        logoutManager.doGet(request, response);
        verify(session, never()).invalidate(); // Verifica que invalidate não foi chamado
    }
}