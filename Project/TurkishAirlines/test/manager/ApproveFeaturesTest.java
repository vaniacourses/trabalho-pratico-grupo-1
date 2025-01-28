package manager;

import models.Features;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ApproveFeaturesTest {

    private ApproveFeatures approveFeatures;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext servletContext;

    private ArrayList<Features> featuresList;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializando o servlet
        approveFeatures = new ApproveFeatures();

        featuresList = new ArrayList<>();
        featuresList.add(new Features(100, 150, 1, true, 32.0, 18.0, 34.0, 19.0, "HD", "4K", "Leather", "Premium",
                "USB", "USB-C", "Available", "Unavailable", "Vegetarian", "Vegan"));
        featuresList.add(new Features(120, 160, 2, true, 31.0, 17.0, 33.0, 18.0, "SD", "HD", "Cloth", "Standard",
                "None", "USB", "Available", "Available", "None", "Vegetarian"));
        featuresList.add(new Features(130, 170, 3, true, 30.0, 16.0, 32.0, 17.0, "HD", "4K", "Economy", "Business",
                "USB", "USB-C", "Yes", "No", "None", "None"));

        // Configurando o ServletContext para retornar a lista de Features
        when(servletContext.getAttribute("features")).thenReturn(featuresList);

        // Configurando o servlet para usar o contexto simulado
        when(request.getServletContext()).thenReturn(servletContext);
    }

    @Test
    public void testDoPost() throws IOException {
        // Invocando o método doPost
        approveFeatures.doPost(request, response);

        // Verificando alterações nos objetos Features
        for (int i = 0; i < 3; i++) {
            Features feature = featuresList.get(i);
            assertEquals(0, feature.getNewSeatPitch(), 0.0);
            assertEquals(0, feature.getNewSeatWidth(), 0.0);
            assertNull(feature.getNewVideoType());
            assertNull(feature.getNewPowerType());
            assertNull(feature.getNewSeatType());
            assertEquals(0, feature.getNewPrice());
            assertNull(feature.getNewWifi());
            assertNull(feature.getNewSpecialFood());
        }

        // Verificando que Features.isChanged foi atualizado
        assertFalse(Features.isChanged);

        // Verificando o redirecionamento
        verify(response).sendRedirect("ApproveFeatures.jsp");
    }
}
