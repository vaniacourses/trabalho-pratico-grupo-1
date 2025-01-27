package admin;

import models.Features;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ChangeFeaturesTest {

    @InjectMocks
    private ChangeFeatures changeFeatures;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext context;

    private ArrayList<Features> featuresList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        featuresList = new ArrayList<>();
        featuresList.add(new Features(100, 120, 1, true, 32.5, 18.0, 33.0, 18.5, "LCD", "LED", "Economy", "Business", "AC", "DC", "Yes", "No", "Vegetarian", "Vegan"));
        featuresList.add(new Features(200, 220, 2, true, 34.5, 19.0, 35.0, 19.5, "LED", "OLED", "Business", "First Class", "DC", "USB", "No", "Yes", "Vegan", "Gluten-Free"));
        featuresList.add(new Features(300, 320, 3, true, 36.5, 20.0, 37.0, 20.5, "OLED", "QLED", "First Class", "Premium", "USB", "Solar", "Yes", "No", "Gluten-Free", "Halal"));

        when(context.getAttribute("features")).thenReturn(featuresList);
        when(request.getServletContext()).thenReturn(context);
    }

    // Testa se os valores antigos são salvos
    @Test
    void testOldValuesAreSaved() throws IOException {
        when(request.getParameter("seat_pitch_e")).thenReturn("32.5");
        when(request.getParameter("seat_width_e")).thenReturn("18.0");
        when(request.getParameter("video_e")).thenReturn("LCD");
        when(request.getParameter("power_e")).thenReturn("AC");
        when(request.getParameter("seat_type_e")).thenReturn("Economy");
        when(request.getParameter("price_e")).thenReturn("100");

        changeFeatures.doPost(request, response);

        assertEquals(32.5, featuresList.get(0).getNewSeatPitch());
        assertEquals(18.0, featuresList.get(0).getNewSeatWidth());
        assertEquals("LCD", featuresList.get(0).getNewVideoType());
        assertEquals("AC", featuresList.get(0).getNewPowerType());
        assertEquals("Economy", featuresList.get(0).getNewSeatType());
        assertEquals(100, featuresList.get(0).getNewPrice());
    }

    // Testa se os novos valores são definidos
    @Test
    void testNewValuesAreSet() throws IOException {
        when(request.getParameter("seat_pitch_e")).thenReturn("35.0");
        when(request.getParameter("seat_width_e")).thenReturn("19.0");
        when(request.getParameter("video_e")).thenReturn("OLED");
        when(request.getParameter("power_e")).thenReturn("USB");
        when(request.getParameter("seat_type_e")).thenReturn("Premium Economy");
        when(request.getParameter("price_e")).thenReturn("150");

        changeFeatures.doPost(request, response);

        assertEquals(35.0, featuresList.get(0).getSeatPitch());
        assertEquals(19.0, featuresList.get(0).getSeatWidth());
        assertEquals("OLED", featuresList.get(0).getVideoType());
        assertEquals("USB", featuresList.get(0).getPowerType());
        assertEquals("Premium Economy", featuresList.get(0).getSeatType());
        assertEquals(150, featuresList.get(0).getPrice());
    }

    // Testa se o Wi-Fi é atualizado
    @Test
    void testWifiIsUpdated() throws IOException {
        when(request.getParameter("wifi_b")).thenReturn("Yes");
        when(request.getParameter("wifi_f")).thenReturn("No");

        changeFeatures.doPost(request, response);

        assertEquals("Yes", featuresList.get(1).getWifi());
        assertEquals("No", featuresList.get(2).getWifi());
    }

    // Testa se a comida especial é atualizada
    @Test
    void testSpecialFoodIsUpdated() throws IOException {
        when(request.getParameter("special_food_f")).thenReturn("Kosher");

        changeFeatures.doPost(request, response);

        assertEquals("Kosher", featuresList.get(2).getSpecialFood());
    }

    // Testa se a flag isChanged é definida como verdadeira
    @Test
    void testIsChangedFlagIsSet() throws IOException {
        changeFeatures.doPost(request, response);

        assertTrue(Features.isChanged);
    }

    // Testa se o redirecionamento é feito corretamente
    @Test
    void testRedirectIsDone() throws IOException {
        changeFeatures.doPost(request, response);

        verify(response).sendRedirect("ChangeFeatures.jsp");
    }
}