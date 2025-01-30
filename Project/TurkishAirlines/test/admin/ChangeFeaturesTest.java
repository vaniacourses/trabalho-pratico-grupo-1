package admin;
import admin.ChangeFeatures;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import models.Features;

public class ChangeFeaturesTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletContext context;
    private ChangeFeatures changeFeatures;
    private ArrayList<Features> featuresList;

    // Método de setup para inicializar mocks
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        changeFeatures = new ChangeFeatures();
        featuresList = new ArrayList<>();
        featuresList.add(new Features(100, 120, 1, true, 32.5, 18.0, 33.0, 18.5, "LCD", "LED", "Economy", "Business", "AC", "DC", "Yes", "No", "Vegetarian", "Vegan"));
        featuresList.add(new Features(200, 220, 2, true, 34.5, 19.0, 35.0, 19.5, "LED", "OLED", "Business", "First Class", "DC", "USB", "No", "Yes", "Vegan", "Gluten-Free"));
        featuresList.add(new Features(300, 320, 3, true, 36.5, 20.0, 37.0, 20.5, "OLED", "QLED", "First Class", "Premium", "USB", "Solar", "Yes", "No", "Gluten-Free", "Halal"));
        
        when(context.getAttribute("features")).thenReturn(featuresList);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        // Mockar os parâmetros de entrada do request
        when(request.getParameter("seat_pitch_e")).thenReturn("34.0");
        when(request.getParameter("seat_width_e")).thenReturn("18.5");
        when(request.getParameter("video_e")).thenReturn("OLED");
        when(request.getParameter("power_e")).thenReturn("USB");
        when(request.getParameter("seat_type_e")).thenReturn("Economy");
        when(request.getParameter("price_e")).thenReturn("180");

        // Chamar o método doPost
        changeFeatures.doPost(request, response);

        // Verificar se o parâmetro foi atualizado corretamente
        Features updatedFeature = featuresList.get(0);  // Primeira feature, que corresponde ao tipo 'e'
        assertEquals(34.0, updatedFeature.getSeatPitch(), 0.01);
        assertEquals(18.5, updatedFeature.getSeatWidth(), 0.01);
        assertEquals("OLED", updatedFeature.getVideoType());
        assertEquals("USB", updatedFeature.getPowerType());
        assertEquals("Economy", updatedFeature.getSeatType());
        assertEquals(180, updatedFeature.getPrice());

        // Verificar o redirecionamento
        verify(response).sendRedirect("ChangeFeatures.jsp");
    }

    @Test
    public void testUpdateFeatureFromRequest() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Criar uma feature mockada
        Features feature = new Features(100, 120, 1, true, 32.5, 18.0, 33.0, 18.5, "LCD", "LED", "Economy", "Business", "AC", "DC", "Yes", "No", "Vegetarian", "Vegan");

        // Mockar os parâmetros
        when(request.getParameter("seat_pitch_e")).thenReturn("35.0");
        when(request.getParameter("seat_width_e")).thenReturn("19.0");
        when(request.getParameter("video_e")).thenReturn("QLED");
        when(request.getParameter("power_e")).thenReturn("Solar");
        when(request.getParameter("seat_type_e")).thenReturn("Business");
        when(request.getParameter("price_e")).thenReturn("200");

        // Usando reflexão para chamar o método privado
        Method method = ChangeFeatures.class.getDeclaredMethod("updateFeatureFromRequest", Features.class, HttpServletRequest.class, char.class);
        method.setAccessible(true);
        method.invoke(changeFeatures, feature, request, 'e');


        // Verificar se os valores foram atualizados
        assertEquals(35.0, feature.getSeatPitch(), 0.01);
        assertEquals(19.0, feature.getSeatWidth(), 0.01);
        assertEquals("QLED", feature.getVideoType());
        assertEquals("Solar", feature.getPowerType());
        assertEquals("Business", feature.getSeatType());
        assertEquals(200, feature.getPrice());
    }

    @Test
    public void testSaveOldValues() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Criar uma feature mockada
        Features feature = new Features(100, 120, 1, true, 32.5, 18.0, 33.0, 18.5, "LCD", "LED", "Economy", "Business", "AC", "DC", "Yes", "No", "Vegetarian", "Vegan");

        /// Usar reflexão para chamar o método privado saveOldValues
        Method method = ChangeFeatures.class.getDeclaredMethod("saveOldValues", Features.class);
        method.setAccessible(true);
        method.invoke(changeFeatures, feature);

        // Verificar se os valores antigos foram salvos corretamente
        assertEquals(32.5, feature.getNewSeatPitch(), 0.01);
        assertEquals(18.0, feature.getNewSeatWidth(), 0.01);
        assertEquals("LCD", feature.getNewVideoType());
        assertEquals("LED", feature.getNewPowerType());
        assertEquals("Economy", feature.getNewSeatType());
        assertEquals(120, feature.getNewPrice());
    }

    @Test
    public void testUpdateFeatureParameter() throws NoSuchMethodException {
        // Criar uma feature mockada
        Features feature = new Features(100, 120, 1, true, 32.5, 18.0, 33.0, 18.5, "LCD", "LED", "Economy", "Business", "AC", "DC", "Yes", "No", "Vegetarian", "Vegan");

        // Mockar o valor do parâmetro
        when(request.getParameter("seat_pitch_e")).thenReturn("36.0");

        // Chamar o método
        Method method = ChangeFeatures.class.getDeclaredMethod("updateFeatureParameter", Features.class, HttpServletRequest.class, char.class);

        // Verificar se o valor foi atualizado
        assertEquals(36.0, feature.getSeatPitch(), 0.01);
    }

    @Test
    public void testConfigureWifi() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Mockar o parâmetro wifi
        when(request.getParameter("wifi_b")).thenReturn("Enabled");
        
        // Usar reflexão para chamar o método privado configureWifi
        Method method = ChangeFeatures.class.getDeclaredMethod("configureWifi", HttpServletRequest.class, ArrayList.class, int.class, String.class);
        method.setAccessible(true);
        method.invoke(changeFeatures, request, featuresList, 1, "wifi_b");

        // Verificar se o Wi-Fi foi configurado corretamente
        assertEquals("Enabled", featuresList.get(1).getWifi());
    }

    @Test
    public void testConfigureSpecialFood() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Mockar o parâmetro de comida especial
        when(request.getParameter("special_food_f")).thenReturn("Halal");

        // Usar reflexão para chamar o método privado configureSpecialFood
        Method method = ChangeFeatures.class.getDeclaredMethod("configureSpecialFood", HttpServletRequest.class, ArrayList.class, int.class, String.class);
        method.setAccessible(true);
        method.invoke(changeFeatures, request, featuresList, 2, "special_food_f");
        // Verificar se a comida especial foi configurada corretamente
        assertEquals("Halal", featuresList.get(2).getSpecialFood());
    }
}
