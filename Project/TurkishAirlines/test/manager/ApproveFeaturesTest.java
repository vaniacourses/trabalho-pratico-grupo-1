package manager;

import models.Features;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ApproveFeaturesTest {

    @InjectMocks
    private ApproveFeatures approveFeatures;

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

    // Testa se os valores novos são resetados
    @Test
    void testNewValuesAreReset() throws IOException {
        approveFeatures.doPost(request, response);

        for (Features feature : featuresList) {
            assertEquals(0, feature.getNewSeatPitch());
            assertEquals(0, feature.getNewSeatWidth());
            assertNull(feature.getNewVideoType());
            assertNull(feature.getNewPowerType());
            assertNull(feature.getNewSeatType());
            assertEquals(0, feature.getNewPrice());
            assertNull(feature.getNewWifi());
            assertNull(feature.getNewSpecialFood());
        }
    }

    // Testa se a flag isChanged é definida como falsa
    @Test
    void testIsChangedFlagIsReset() throws IOException {
        approveFeatures.doPost(request, response);

        assertFalse(Features.isChanged);
    }

    // Testa se o redirecionamento é feito corretamente
    @Test
    void testRedirectIsDone() throws IOException {
        approveFeatures.doPost(request, response);

        verify(response).sendRedirect("ApproveFeatures.jsp");
    }

    // Testa se o método doPost não lança exceções
    @Test
    void testDoPostDoesNotThrowException() {
        assertDoesNotThrow(() -> approveFeatures.doPost(request, response));
    }
}