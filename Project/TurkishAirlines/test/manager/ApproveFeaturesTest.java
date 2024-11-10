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
public class ApproveFeaturesTest {
    private ApproveFeatures approveFeaturesServlet;
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
        approveFeaturesServlet = new ApproveFeatures();
        featuresList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Features feature = new Features();
            feature.setNewSeatPitch(10);
            feature.setNewSeatWidth(10);
            feature.setNewVideoType("LCD");
            feature.setNewPowerType("Battery");
            feature.setNewSeatType("Economy");
            feature.setNewPrice(100);
            feature.setNewWifi("Available");
            feature.setNewSpecialFood("Vegetarian");
            featuresList.add(feature);
        }
        when(servletContext.getAttribute("features")).thenReturn(featuresList);
        approveFeaturesServlet.setServletContext(servletContext);
    }
    @Test
    public void doPostTest() throws IOException {
        approveFeaturesServlet.doPost(request, response);
        for (int i = 0; i < 3; i++) {
            Features feature = featuresList.get(i);
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
