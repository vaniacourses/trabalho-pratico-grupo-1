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
    MockitoAnnotations.initMocks(this);

    approveFeatures = new ApproveFeatures();

    featuresList = new ArrayList<>();
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

    when(servletContext.getAttribute("features")).thenReturn(featuresList);

    when(request.getServletContext()).thenReturn(servletContext);
}

  @Test
    public void testFeaturesListIsNotNull() {
        assertNotNull("A lista de Features não deve ser nula", featuresList);
    }
    
    @Test
    public void testFirstFeature() {
        Features firstFeature = (Features) featuresList.get(0);
        assertEquals(18, firstFeature.getSeatWidth(), 0.01);
        assertEquals("HD", firstFeature.getVideoType());
    }

    @Test
    public void testAllFeatures() {
        for (Features feature : featuresList) {
            assertNotNull(feature.getVideoType());  
        }
    }

}
