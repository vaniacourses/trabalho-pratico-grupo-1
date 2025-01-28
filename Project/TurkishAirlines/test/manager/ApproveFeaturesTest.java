package manager;

import models.Features;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class ApproveFeaturesTest {

    private ApproveFeatures approveFeatures;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext servletContext;

    
    private List<Features> featuresList;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando uma lista de Features
        featuresList = new ArrayList<>();

        // Adicionando alguns objetos Features à lista
        featuresList.add(new Features(
            100, 120, 1, true,
            30.0, 18.0, 32.0, 19.0,
            "HD", "4K", "Economy", "Business",
            "USB", "USB-C", "Available", "Unavailable",
            "Vegetarian", "Vegan"
        ));

        featuresList.add(new Features(
            200, 150, 2, false,
            28.0, 16.0, 35.0, 20.0,
            "SD", "1080p", "First", "Economy",
            "HDMI", "USB-C", "Available", "Unavailable",
            "Non-Vegetarian", "Vegan"
        ));

        featuresList.add(new Features(
            150, 100, 3, true,
            25.0, 20.0, 30.0, 22.0,
            "4K", "HD", "Business", "Economy",
            "USB", "USB-A", "Available", "Unavailable",
            "Vegetarian", "Non-Vegetarian"
        ));
        // Configurando o ServletContext para retornar a lista de Features
        when(servletContext.getAttribute("features")).thenReturn(featuresList);

        // Configurando o servlet para usar o contexto simulado
        when(request.getServletContext()).thenReturn(servletContext);
    }
    
    @Test
    public void testFirstFeature() {
        // Verifique as propriedades do primeiro Feature na lista
        Features firstFeature = (Features) featuresList.get(0);
        assertEquals(18, firstFeature.getSeatWidth(), 0.01);
        assertEquals("HD", firstFeature.getVideoType());
    }

    @Test
    public void testAllFeatures() {
        // Iterando pela lista de Features e verificando uma propriedade de cada
        for (Features feature : featuresList) {
            assertNotNull(feature.getVideoType());  // Apenas um exemplo de verificação
        }
    }

    @Test
    public void testDoPost() throws IOException {
        try {
            approveFeatures.doPost(request, response);
            // Verifique as interações aqui, caso existam
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            fail("O método doPost deve ser executado sem lançar exceções");
        }

        // Verifica se os métodos de modificação foram chamados
        for (Features feature : featuresList) {
            verify(feature).setNewSeatPitch(0);
            verify(feature).setNewSeatWidth(0);
            verify(feature).setNewVideoType(null);
            verify(feature).setNewPowerType(null);
            verify(feature).setNewSeatType(null);
            verify(feature).setNewPrice(0);
            verify(feature).setNewWifi(null);
            verify(feature).setNewSpecialFood(null);
        }
        // Verificando que Features.isChanged foi atualizado
        assertFalse(Features.isChanged);

        // Verificando o redirecionamento
        verify(response).sendRedirect("ApproveFeatures.jsp");
    }
}
