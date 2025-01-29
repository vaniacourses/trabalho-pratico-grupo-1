import admin.ChangeFeatures;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import models.Features;
import java.util.ArrayList;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class) 
public class ChangeFeaturesTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext context;
    private ArrayList<Features> featuresList;

    @Before
    public void setUp() {
        // Criando os mocks
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        context = mock(ServletContext.class);

        // Criando lista de Features mockada
        featuresList = new ArrayList<>();
        featuresList.add(new Features(100, 120, 1, true, 32.5, 18.0, 33.0, 18.5, "LCD", "LED", "Economy", "Business", "AC", "DC", "Yes", "No", "Vegetarian", "Vegan"));
        featuresList.add(new Features(200, 220, 2, true, 34.5, 19.0, 35.0, 19.5, "LED", "OLED", "Business", "First Class", "DC", "USB", "No", "Yes", "Vegan", "Gluten-Free"));
        featuresList.add(new Features(300, 320, 3, true, 36.5, 20.0, 37.0, 20.5, "OLED", "QLED", "First Class", "Premium", "USB", "Solar", "Yes", "No", "Gluten-Free", "Halal"));

        // Mockando o contexto para retornar a lista de features
        when(context.getAttribute("features")).thenReturn(featuresList);
    }

    @Test
    public void testDoPost() throws Exception {
        // Mockando a classe ChangeFeatures
        ChangeFeatures changeFeatures = mock(ChangeFeatures.class);

        // Simulando os parâmetros que o método doPost irá pegar do request
        when(request.getParameter("seat_pitch_e")).thenReturn("33.5");
        when(request.getParameter("seat_width_e")).thenReturn("19.5");
        when(request.getParameter("video_e")).thenReturn("OLED");
        when(request.getParameter("power_e")).thenReturn("DC");
        when(request.getParameter("seat_type_e")).thenReturn("Economy");
        when(request.getParameter("price_e")).thenReturn("150");

        // Mockando o comportamento do método getServletContext() para retornar o contexto mockado
        when(changeFeatures.getServletContext()).thenReturn(context);

        // Simulando a chamada do método doPost no mock
        changeFeatures.doPost(request, response);  // Isso irá executar a lógica do método

        // Verificando se a lista de Features foi atualizada
        Features updatedFeature = featuresList.get(0);  // Esperando que a primeira feature tenha sido alterada

        assertEquals(33.5, updatedFeature.getSeatPitch(), 0.01);
        assertEquals(19.5, updatedFeature.getSeatWidth(), 0.01);
        assertEquals("OLED", updatedFeature.getVideoType());
        assertEquals("DC", updatedFeature.getPowerType());
        assertEquals("Economy", updatedFeature.getSeatType());
        assertEquals(150, updatedFeature.getPrice());

        // Verificando se o redirect foi chamado
        verify(response).sendRedirect("ChangeFeatures.jsp");
    }
}
