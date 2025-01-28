package filters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class XSSRequestWrapperTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private XSSRequestWrapper xssRequestWrapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        xssRequestWrapper = new XSSRequestWrapper(request);
    }

    // Testa se os valores dos parâmetros são filtrados
    @Test
    public void testGetParameterValues() {
        String[] values = {"<script>alert('xss')</script>", "normal"};
        when(request.getParameterValues("param")).thenReturn(values);

        String[] filteredValues = xssRequestWrapper.getParameterValues("param");

        assertEquals(2, filteredValues.length);
        assertEquals("", filteredValues[0]);
        assertEquals("normal", filteredValues[1]);
    }

    // Testa se o valor do parâmetro é filtrado
    @Test
    public void testGetParameter() {
        when(request.getParameter("param")).thenReturn("<script>alert('xss')</script>");

        String filteredValue = xssRequestWrapper.getParameter("param");

        assertEquals("", filteredValue);
    }

    // Testa se o valor do cabeçalho é filtrado
    @Test
    public void testGetHeader() {
        when(request.getHeader("header")).thenReturn("<script>alert('xss')</script>");

        String filteredHeader = xssRequestWrapper.getHeader("header");

        assertEquals("", filteredHeader);
    }

    // Testa se o método stripXSS remove scripts
    @Test
    public void testStripXSSRemovesScripts() {
        String value = "<script>alert('xss')</script>";
        String filteredValue = xssRequestWrapper.stripXSS(value);

        assertEquals("", filteredValue);
    }

    // Testa se o método stripXSS remove atributos src
    @Test
    public void testStripXSSRemovesSrcAttributes() {
        String value = "<img src='xss.jpg'>";
        String filteredValue = xssRequestWrapper.stripXSS(value);

        assertEquals("<img >", filteredValue);
    }

    // Testa se o método stripXSS remove eval
    @Test
    public void testStripXSSRemovesEval() {
        String value = "eval('xss')";
        String filteredValue = xssRequestWrapper.stripXSS(value);

        assertEquals("", filteredValue);
    }

    // Testa se o método stripXSS remove javascript
    @Test
    public void testStripXSSRemovesJavascript() {
        String value = "javascript:alert('xss')";
        String filteredValue = xssRequestWrapper.stripXSS(value);

        assertEquals("", filteredValue);
    }

    // Testa se o método stripXSS remove vbscript
    @Test
    public void testStripXSSRemovesVbscript() {
        String value = "vbscript:msgbox('xss')";
        String filteredValue = xssRequestWrapper.stripXSS(value);

        assertEquals("", filteredValue);
    }

    // Testa se o método stripXSS remove onload
    @Test
    public void testStripXSSRemovesOnload() {
        String value = "onload=alert('xss')";
        String filteredValue = xssRequestWrapper.stripXSS(value);

        assertEquals("", filteredValue);
    }
}