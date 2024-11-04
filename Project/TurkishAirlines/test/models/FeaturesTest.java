import models.Features;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FeaturesTest {

    private Features features;

    @BeforeEach
    void setUp() {
        features = new Features(
                100, 120, 1, true, 
                32.5, 18.0, 33.0, 18.5, 
                "LCD", "LED", 
                "Economy", "Business", 
                "AC", "DC", 
                "Yes", "No", 
                "Vegetarian", "Vegan");
    }

    @Test
    void testGetPrice() {
        assertEquals(100, features.getPrice());
    }

    @Test
    void testSetPrice() {
        features.setPrice(150);
        assertEquals(150, features.getPrice());
    }

    @Test
    void testGetNewPrice() {
        assertEquals(120, features.getNewPrice());
    }

    @Test
    void testSetNewPrice() {
        features.setNewPrice(130);
        assertEquals(130, features.getNewPrice());
    }

    @Test
    void testGetSeatPitch() {
        assertEquals(32.5, features.getSeatPitch());
    }

    @Test
    void testSetSeatPitch() {
        features.setSeatPitch(34.0);
        assertEquals(34.0, features.getSeatPitch());
    }

    @Test
    void testGetSeatWidth() {
        assertEquals(18.0, features.getSeatWidth());
    }

    @Test
    void testSetSeatWidth() {
        features.setSeatWidth(19.0);
        assertEquals(19.0, features.getSeatWidth());
    }

    @Test
    void testGetIsChanged() {
        assertTrue(features.getIsChanged());
    }

    @Test
    void testSetIsChanged() {
        features.setIsChanged(false);
        assertFalse(features.getIsChanged());
    }

    @Test
    void testGetNewSeatPitch() {
        assertEquals(33.0, features.getNewSeatPitch());
    }

    @Test
    void testSetNewSeatPitch() {
        features.setNewSeatPitch(35.0);
        assertEquals(35.0, features.getNewSeatPitch());
    }

    @Test
    void testGetNewSeatWidth() {
        assertEquals(18.5, features.getNewSeatWidth());
    }

    @Test
    void testSetNewSeatWidth() {
        features.setNewSeatWidth(19.5);
        assertEquals(19.5, features.getNewSeatWidth());
    }

    @Test
    void testGetVideoType() {
        assertEquals("LCD", features.getVideoType());
    }

    @Test
    void testSetVideoType() {
        features.setVideoType("OLED");
        assertEquals("OLED", features.getVideoType());
    }

    @Test
    void testGetNewVideoType() {
        assertEquals("LED", features.getNewVideoType());
    }

    @Test
    void testSetNewVideoType() {
        features.setNewVideoType("QLED");
        assertEquals("QLED", features.getNewVideoType());
    }

    @Test
    void testGetSeatType() {
        assertEquals("Economy", features.getSeatType());
    }

    @Test
    void testSetSeatType() {
        features.setSeatType("Premium Economy");
        assertEquals("Premium Economy", features.getSeatType());
    }

    @Test
    void testGetNewSeatType() {
        assertEquals("Business", features.getNewSeatType());
    }

    @Test
    void testSetNewSeatType() {
        features.setNewSeatType("First Class");
        assertEquals("First Class", features.getNewSeatType());
    }

    @Test
    void testGetPowerType() {
        assertEquals("AC", features.getPowerType());
    }

    @Test
    void testSetPowerType() {
        features.setPowerType("USB");
        assertEquals("USB", features.getPowerType());
    }

    @Test
    void testGetNewPowerType() {
        assertEquals("DC", features.getNewPowerType());
    }

    @Test
    void testSetNewPowerType() {
        features.setNewPowerType("Solar");
        assertEquals("Solar", features.getNewPowerType());
    }

    @Test
    void testGetWifi() {
        assertEquals("Yes", features.getWifi());
    }

    @Test
    void testSetWifi() {
        features.setWifi("No");
        assertEquals("No", features.getWifi());
    }

    @Test
    void testGetNewWifi() {
        assertEquals("No", features.getNewWifi());
    }

    @Test
    void testSetNewWifi() {
        features.setNewWifi("Yes");
        assertEquals("Yes", features.getNewWifi());
    }

    @Test
    void testGetSpecialFood() {
        assertEquals("Vegetarian", features.getSpecialFood());
    }

    @Test
    void testSetSpecialFood() {
        features.setSpecialFood("Gluten-Free");
        assertEquals("Gluten-Free", features.getSpecialFood());
    }

    @Test
    void testGetNewSpecialFood() {
        assertEquals("Vegan", features.getNewSpecialFood());
    }

    @Test
    void testSetNewSpecialFood() {
        features.setNewSpecialFood("Halal");
        assertEquals("Halal", features.getNewSpecialFood());
    }
}
