import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
public class DisapproveFeaturesTest {
    private DisapproveFeatures disapproveFeaturesServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletContext servletContext;
    private ArrayList<Features> featuresList;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        disapproveFeaturesServlet = new DisapproveFeatures();
        featuresList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Features feature = new Features();
            feature.setNewSeatPitch(15);
            feature.setNewSeatWidth(18);
            feature.setNewVideoType("OLED");
            feature.setNewPowerType("Solar");
            feature.setNewSeatType("Premium");
            feature.setNewPrice(200);
            feature.setNewWifi("Available");
            feature.setNewSpecialFood("Gluten-Free");
            featuresList.add(feature);
        }
        when(servletContext.getAttribute("features")).thenReturn(featuresList);
        disapproveFeaturesServlet.setServletContext(servletContext);
    }
    @Test
    public void doPostTest() throws IOException {
        disapproveFeaturesServlet.doPost(request, response);
        for (int i = 0; i < 3; i++) {
            Features feature = featuresList.get(i);
            assertEquals(15, feature.getSeatPitch());
            assertEquals(18, feature.getSeatWidth());
            assertEquals("OLED", feature.getVideoType());
            assertEquals("Solar", feature.getPowerType());
            assertEquals("Premium", feature.getSeatType());
            assertEquals(200, feature.getPrice());
            assertEquals("Available", feature.getWifi());
            assertEquals("Gluten-Free", feature.getSpecialFood());
            assertEquals(0, feature.getNewSeatPitch());
            assertEquals(0, feature.getNewSeatWidth());
            assertNull(feature.getNewVideoType());
            assertNull(feature.getNewPowerType());
            assertNull(feature.getNewSeatType());
            assertEquals(0, feature.getNewPrice());
            assertNull(feature.getNewWifi());
            assertNull(feature.getNewSpecialFood());
        }
        assertFalse(Features.isChanged);
        verify(response).sendRedirect("ApproveFeatures.jsp");
    }
}
