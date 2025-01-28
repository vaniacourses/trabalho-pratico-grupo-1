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

public class SecurityFilterTest {

    @InjectMocks
    private SecurityFilter securityFilter;

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
        securityFilter.init(filterConfig);
        // Nenhuma ação específica a ser verificada
    }

    // Testa se o filtro é destruído corretamente
    @Test
    public void testDestroy() {
        securityFilter.destroy();
        // Nenhuma ação específica a ser verificada
    }

    // Testa se os cabeçalhos de cache são definidos corretamente
    @Test
    public void testCacheHeadersAreSet() throws IOException, ServletException {
        securityFilter.doFilter(request, response, chain);

        verify(response).setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        verify(response).setHeader("Pragma", "no-cache");
        verify(response).setDateHeader("Expires", 0);
    }

    // Testa se o filtro chama o próximo filtro na cadeia
    @Test
    public void testFilterChainIsCalled() throws IOException, ServletException {
        securityFilter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    // Testa se o método doFilter não lança exceções
    @Test
    public void testDoFilterDoesNotThrowException() {
        try {
            securityFilter.doFilter(request, response, chain);
        } catch (Exception e) {
            // Caso alguma exceção seja lançada, o teste falha
            fail("O método doGet não deve lançar exceção");
        }
    }
}