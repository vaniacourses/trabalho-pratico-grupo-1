package filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Testa se o filtro é inicializado corretamente
    @Test
    void testInit() throws ServletException {
        xssFilter.init(filterConfig);
        // Nenhuma ação específica a ser verificada
    }

    // Testa se o filtro é destruído corretamente
    @Test
    void testDestroy() {
        xssFilter.destroy();
        // Nenhuma ação específica a ser verificada
    }

    // Testa se o filtro envolve a requisição com XSSRequestWrapper
    @Test
    void testRequestIsWrapped() throws IOException, ServletException {
        xssFilter.doFilter(request, response, chain);
        verify(chain).doFilter(any(XSSRequestWrapper.class), eq(response));
    }

    // Testa se o método doFilter não lança exceções
    @Test
    void testDoFilterDoesNotThrowException() {
        assertDoesNotThrow(() -> xssFilter.doFilter(request, response, chain));
    }
}