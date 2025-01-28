package filters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.*;

public class XSSFilterTest {

    @InjectMocks
    private XSSFilter xssFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private FilterConfig filterConfig;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Testa se o filtro é inicializado corretamente
    @Test
    public void testInit() throws ServletException {
        xssFilter.init(filterConfig);
        // Nenhuma ação específica a ser verificada
    }

    // Testa se o filtro é destruído corretamente
    @Test
    public void testDestroy() {
        xssFilter.destroy();
        // Nenhuma ação específica a ser verificada
    }

    // Testa se o filtro envolve a requisição com XSSRequestWrapper
    @Test
    public void testRequestIsWrapped() throws IOException, ServletException {
        xssFilter.doFilter(request, response, chain);
        verify(chain).doFilter(any(XSSRequestWrapper.class), eq(response));
    }

    // Testa se o método doFilter não lança exceções
    @Test
    public void testDoFilterDoesNotThrowException() {
         try {
            xssFilter.doFilter(request, response, chain);
        } catch (Exception e) {
            // Caso alguma exceção seja lançada, o teste falha
            fail("O método doGet não deve lançar exceção");
        }
    }
}