package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FeaturesTest {

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

    // Testa getter de preço
    @Test
    void testGetPrice() {
        assertEquals(100, features.getPrice());
    }

    // Testa setter de preço
    @Test
    void testSetPrice() {
        features.setPrice(150);
        assertEquals(150, features.getPrice());
    }

    // Testa getter de novo preço
    @Test
    void testGetNewPrice() {
        assertEquals(120, features.getNewPrice());
    }

    // Testa setter de novo preço
    @Test
    void testSetNewPrice() {
        features.setNewPrice(130);
        assertEquals(130, features.getNewPrice());
    }

    // Testa getter de inclinação do assento
    @Test
    void testGetSeatPitch() {
        assertEquals(32.5, features.getSeatPitch());
    }

    // Testa setter de inclinação do assento
    @Test
    void testSetSeatPitch() {
        features.setSeatPitch(34.0);
        assertEquals(34.0, features.getSeatPitch());
    }

    // Testa getter de largura do assento
    @Test
    void testGetSeatWidth() {
        assertEquals(18.0, features.getSeatWidth());
    }

    // Testa setter de largura do assento
    @Test
    void testSetSeatWidth() {
        features.setSeatWidth(19.0);
        assertEquals(19.0, features.getSeatWidth());
    }

    // Testa getter de mudança de status
    @Test
    void testGetIsChanged() {
        assertTrue(features.getIsChanged());
    }

    // Testa setter de mudança de status
    @Test
    void testSetIsChanged() {
        features.setIsChanged(false);
        assertFalse(features.getIsChanged());
    }

    // Testa getter de nova inclinação do assento
    @Test
    void testGetNewSeatPitch() {
        assertEquals(33.0, features.getNewSeatPitch());
    }

    // Testa setter de nova inclinação do assento
    @Test
    void testSetNewSeatPitch() {
        features.setNewSeatPitch(35.0);
        assertEquals(35.0, features.getNewSeatPitch());
    }

    // Testa getter de nova largura do assento
    @Test
    void testGetNewSeatWidth() {
        assertEquals(18.5, features.getNewSeatWidth());
    }

    // Testa setter de nova largura do assento
    @Test
    void testSetNewSeatWidth() {
        features.setNewSeatWidth(19.5);
        assertEquals(19.5, features.getNewSeatWidth());
    }

    // Testa getter de tipo de vídeo
    @Test
    void testGetVideoType() {
        assertEquals("LCD", features.getVideoType());
    }

    // Testa setter de tipo de vídeo
    @Test
    void testSetVideoType() {
        features.setVideoType("OLED");
        assertEquals("OLED", features.getVideoType());
    }

    // Testa getter de novo tipo de vídeo
    @Test
    void testGetNewVideoType() {
        assertEquals("LED", features.getNewVideoType());
    }

    // Testa setter de novo tipo de vídeo
    @Test
    void testSetNewVideoType() {
        features.setNewVideoType("QLED");
        assertEquals("QLED", features.getNewVideoType());
    }

    // Testa getter de tipo de assento
    @Test
    void testGetSeatType() {
        assertEquals("Economy", features.getSeatType());
    }

    // Testa setter de tipo de assento
    @Test
    void testSetSeatType() {
        features.setSeatType("Premium Economy");
        assertEquals("Premium Economy", features.getSeatType());
    }

    // Testa getter de novo tipo de assento
    @Test
    void testGetNewSeatType() {
        assertEquals("Business", features.getNewSeatType());
    }

    // Testa setter de novo tipo de assento
    @Test
    void testSetNewSeatType() {
        features.setNewSeatType("First Class");
        assertEquals("First Class", features.getNewSeatType());
    }

    // Testa getter de tipo de energia
    @Test
    void testGetPowerType() {
        assertEquals("AC", features.getPowerType());
    }

    // Testa setter de tipo de energia
    @Test
    void testSetPowerType() {
        features.setPowerType("USB");
        assertEquals("USB", features.getPowerType());
    }

    // Testa getter de novo tipo de energia
    @Test
    void testGetNewPowerType() {
        assertEquals("DC", features.getNewPowerType());
    }

    // Testa setter de novo tipo de energia
    @Test
    void testSetNewPowerType() {
        features.setNewPowerType("Solar");
        assertEquals("Solar", features.getNewPowerType());
    }

    // Testa getter de Wi-Fi
    @Test
    void testGetWifi() {
        assertEquals("Yes", features.getWifi());
    }

    // Testa setter de Wi-Fi
    @Test
    void testSetWifi() {
        features.setWifi("No");
        assertEquals("No", features.getWifi());
    }

    // Testa getter de novo Wi-Fi
    @Test
    void testGetNewWifi() {
        assertEquals("No", features.getNewWifi());
    }

    // Testa setter de novo Wi-Fi
    @Test
    void testSetNewWifi() {
        features.setNewWifi("Yes");
        assertEquals("Yes", features.getNewWifi());
    }

    // Testa getter de comida especial
    @Test
    void testGetSpecialFood() {
        assertEquals("Vegetarian", features.getSpecialFood());
    }

    // Testa setter de comida especial
    @Test
    void testSetSpecialFood() {
        features.setSpecialFood("Gluten-Free");
        assertEquals("Gluten-Free", features.getSpecialFood());
    }

    // Testa getter de nova comida especial
    @Test
    void testGetNewSpecialFood() {
        assertEquals("Vegan", features.getNewSpecialFood());
    }

    // Testa setter de nova comida especial
    @Test
    void testSetNewSpecialFood() {
        features.setNewSpecialFood("Halal");
        assertEquals("Halal", features.getNewSpecialFood());
    }
}