package filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class XSSRequestWrapperTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private XSSRequestWrapper xssRequestWrapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        xssRequestWrapper = new XSSRequestWrapper(request);
    }

    // Testa se os valores dos parâmetros são filtrados
    @Test
    void testGetParameterValues() {
        String[] values = {"<script>alert('xss')</script>", "normal"};
        when(request.getParameterValues("param")).thenReturn(values);

        String[] filteredValues = xssRequestWrapper.getParameterValues("param");

        assertEquals(2, filteredValues.length);
        assertEquals("", filteredValues[0]);
        assertEquals("normal", filteredValues[1]);
    }

    // Testa se o valor do parâmetro é filtrado
    @Test
    void testGetParameter() {
        when(request.getParameter("param")).thenReturn("<script>alert('xss')</script>");

        String filteredValue = xssRequestWrapper.getParameter("param");

        assertEquals("", filteredValue);
    }

    // Testa se o valor do cabeçalho é filtrado
    @Test
    void testGetHeader() {
        when(request.getHeader("header")).thenReturn("<script>alert('xss')</script>");

        String filteredHeader = xssRequestWrapper.getHeader("header");

        assertEquals("", filteredHeader);
    }

    // Testa se o método stripXSS remove scripts
    @Test
    void testStripXSSRemovesScripts() {
        String value = "<script>alert('xss')</script>";
        String filteredValue = xssRequestWrapper.stripXSS(value);

        assertEquals("", filteredValue);
    }

    // Testa se o método stripXSS remove atributos src
    @Test
    void testStripXSSRemovesSrcAttributes() {
        String value = "<img src='xss.jpg'>";
        String filteredValue = xssRequestWrapper.stripXSS(value);

        assertEquals("<img >", filteredValue);
    }

    // Testa se o método stripXSS remove eval
    @Test
    void testStripXSSRemovesEval() {
        String value = "eval('xss')";
        String filteredValue = xssRequestWrapper.stripXSS(value);

        assertEquals("", filteredValue);
    }

    // Testa se o método stripXSS remove javascript
    @Test
    void testStripXSSRemovesJavascript() {
        String value = "javascript:alert('xss')";
        String filteredValue = xssRequestWrapper.stripXSS(value);

        assertEquals("", filteredValue);
    }

    // Testa se o método stripXSS remove vbscript
    @Test
    void testStripXSSRemovesVbscript() {
        String value = "vbscript:msgbox('xss')";
        String filteredValue = xssRequestWrapper.stripXSS(value);

        assertEquals("", filteredValue);
    }

    // Testa se o método stripXSS remove onload
    @Test
    void testStripXSSRemovesOnload() {
        String value = "onload=alert('xss')";
        String filteredValue = xssRequestWrapper.stripXSS(value);

        assertEquals("", filteredValue);
    }
}