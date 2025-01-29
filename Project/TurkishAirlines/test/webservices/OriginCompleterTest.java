package webservices;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import webservices.OriginCompleter;
import models.FBS;
import java.sql.Connection;

public class OriginCompleterTest {

    @Mock
    private OriginCompleter originCompleter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext servletContext;

    @Mock
    private FBS fbs;

    @Mock
    private Connection connection;

    @Before
    public void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);

        // Configura o comportamento esperado
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("fbs")).thenReturn(fbs);
        when(servletContext.getAttribute("con")).thenReturn(connection);

        originCompleter = new OriginCompleter();  // Inicializa a classe sob teste
    }

    @Test
    public void testDoGet_withException() throws Exception {
        // Simula a chamada do método doGet() e verifica o comportamento
        originCompleter.doGet(request, response);

        // Verifica se o setContentType foi chamado corretamente
        verify(response).setContentType("application/json");
    }

    @Test
    public void testDoGet() throws Exception {
        // Simula o comportamento normal da requisição
        originCompleter.doGet(request, response);

        // Verifica se o setContentType foi chamado
        verify(response).setContentType("application/json");
    }
}
