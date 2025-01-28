package admin;

import models.Features;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import java.io.StringWriter;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.mockito.Mockito;


public class ChangeFeaturesTest {

   
    private ChangeFeatures changeFeatures;  // A classe que estamos testando

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext context;

    ArrayList<Features> featuresList;
    
    

    @Before
    public void setUp() {
        changeFeatures = new ChangeFeatures();

        MockitoAnnotations.openMocks(this);

        featuresList = new ArrayList<>();
        featuresList.add(new Features(100, 120, 1, true, 32.5, 18.0, 33.0, 18.5, "LCD", "LED", "Economy", "Business", "AC", "DC", "Yes", "No", "Vegetarian", "Vegan"));
        featuresList.add(new Features(200, 220, 2, true, 34.5, 19.0, 35.0, 19.5, "LED", "OLED", "Business", "First Class", "DC", "USB", "No", "Yes", "Vegan", "Gluten-Free"));
        featuresList.add(new Features(300, 320, 3, true, 36.5, 20.0, 37.0, 20.5, "OLED", "QLED", "First Class", "Premium", "USB", "Solar", "Yes", "No", "Gluten-Free", "Halal"));
        
        Mockito.when(context.getAttribute("features")).thenReturn(featuresList);
        Mockito.when(request.getServletContext()).thenReturn(context);
    }

    @Test
    public void testDoPost() throws Exception {
        
        System.out.println(featuresList);

        when(request.getParameter("seat_pitch_e")).thenReturn("35");
        when(request.getParameter("seat_pitch_f")).thenReturn("35");
        System.out.println(request.getParameter("seat_pitch_f"));

        
        ChangeFeatures servlet = new ChangeFeatures();
        
        servlet.doPost(request, response);
        
        Mockito.verify(response).sendRedirect("ChangeFeatures.jsp");
    }
    // Testa se os valores antigos são salvos
    @Test
    public void testOldValuesAreSaved() throws IOException, ServletException {
        
        System.out.println(featuresList);

        // Adicionando os mocks para as outras features, se necessário
        when(request.getParameter("seat_pitch_e")).thenReturn("35.0");   // Para a segunda Feature
        when(request.getParameter("seat_width_e")).thenReturn("19.0");   // Para a segunda Feature
        when(request.getParameter("video_e")).thenReturn("OLED");        // Para a segunda Feature
        when(request.getParameter("power_e")).thenReturn("USB");         // Para a segunda Feature
        when(request.getParameter("seat_type_e")).thenReturn("Premium Economy"); // Para a segunda Feature
        when(request.getParameter("price_e")).thenReturn("150");         // Para a segunda Feature

      
        System.out.println(request.getParameter("seat_width_b"));

        
        // Preparando a resposta para capturar a saída
        StringWriter responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);
        
        // Executando o doPost
        changeFeatures.doPost(request, response);
        
        
        // Verificar se os valores foram modificados corretamente
        Features feature = featuresList.get(0);
        assertEquals(35.0, feature.getSeatPitch(), 0.0);
        assertEquals(19.0, feature.getSeatWidth(), 0.0);
        assertEquals("OLED", feature.getVideoType());
        assertEquals("USB", feature.getPowerType());
        assertEquals("Premium Economy", feature.getSeatType());
        assertEquals(150, feature.getPrice());

        // Verificando se a resposta foi escrita corretamente
        assertTrue(responseWriter.toString().contains("Dados processados."));
    }

    // Testa se os novos valores são definidos
    @Test
    public void testNewValuesAreSet() throws IOException, ServletException {
        when(request.getParameter("seat_pitch_e")).thenReturn("35.0");
        when(request.getParameter("seat_width_e")).thenReturn("19.0");
        when(request.getParameter("video_e")).thenReturn("OLED");
        when(request.getParameter("power_e")).thenReturn("USB");
        when(request.getParameter("seat_type_e")).thenReturn("Premium Economy");
        when(request.getParameter("price_e")).thenReturn("150");

        changeFeatures.doPost(request, response);

        assertEquals(35.0, featuresList.get(0).getSeatPitch(),0.0);
        assertEquals(19.0, featuresList.get(0).getSeatWidth());
        assertEquals("OLED", featuresList.get(0).getVideoType());
        assertEquals("USB", featuresList.get(0).getPowerType());
        assertEquals("Premium Economy", featuresList.get(0).getSeatType());
        assertEquals(150, featuresList.get(0).getPrice());
    }

    // Testa se o Wi-Fi é atualizado
    @Test
    public void testWifiIsUpdated() throws IOException, ServletException {
        when(request.getParameter("wifi_b")).thenReturn("Yes");
        when(request.getParameter("wifi_f")).thenReturn("No");

        changeFeatures.doPost(request, response);

        assertEquals("Yes", featuresList.get(1).getWifi());
        assertEquals("No", featuresList.get(2).getWifi());
    }

    // Testa se a comida especial é atualizada
    @Test
    public void testSpecialFoodIsUpdated() throws IOException, ServletException {
        when(request.getParameter("special_food_f")).thenReturn("Kosher");

        changeFeatures.doPost(request, response);

        assertEquals("Kosher", featuresList.get(2).getSpecialFood());
    }

    // Testa se a flag isChanged é definida como verdadeira
    @Test
    public void testIsChangedFlagIsSet() throws IOException, ServletException {
        changeFeatures.doPost(request, response);

        assertTrue(Features.isChanged);
    }

    // Testa se o redirecionamento é feito corretamente
    @Test
    public void testRedirectIsDone() throws IOException, ServletException {
        changeFeatures.doPost(request, response);

        verify(response).sendRedirect("ChangeFeatures.jsp");
    }
}