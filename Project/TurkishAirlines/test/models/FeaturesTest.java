package models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FeaturesTest {

    private Features features;

    @Before
    public void setUp() {
        
        features = new Features(
            100, 120, 1, true,
            30.0, 18.0, 32.0, 19.0,
            "HD", "4K", "Economy", "Business",
            "USB", "USB-C", "Available", "Unavailable",
            "Vegetarian", "Vegan"
        );
    }

    @Test
    public void testGetPrice() {
        assertEquals(100, features.getPrice());
    }

    @Test
    public void testSetPrice() {
        features.setPrice(150);
        assertEquals(150, features.getPrice());
    }

    @Test
    public void testGetNewPrice() {
        assertEquals(120, features.getNewPrice());
    }

    @Test
    public void testSetNewPrice() {
        features.setNewPrice(130);
        assertEquals(130, features.getNewPrice());
    }

    @Test
    public void testSeatPitch() {
        assertEquals(30.0, features.getSeatPitch(), 0.0);
        features.setSeatPitch(31.5);
        assertEquals(31.5, features.getSeatPitch(), 0.0);
    }

    @Test
    public void testNewSeatPitch() {
        assertEquals(32.0, features.getNewSeatPitch(), 0.0);
        features.setNewSeatPitch(33.0);
        assertEquals(33.0, features.getNewSeatPitch(), 0.0);
    }

    @Test
    public void testVideoType() {
        assertEquals("HD", features.getVideoType());
        features.setVideoType("Full HD");
        assertEquals("Full HD", features.getVideoType());
    }

    @Test
    public void testNewVideoType() {
        assertEquals("4K", features.getNewVideoType());
        features.setNewVideoType("8K");
        assertEquals("8K", features.getNewVideoType());
    }

    @Test
    public void testIsChanged() {
        assertTrue(features.getIsChanged());
        features.setIsChanged(false);
        assertFalse(features.getIsChanged());
    }

    @Test
    public void testWifi() {
        assertEquals("Available", features.getWifi());
        features.setWifi("Unavailable");
        assertEquals("Unavailable", features.getWifi());
    }

    @Test
    public void testSpecialFood() {
        assertEquals("Vegetarian", features.getSpecialFood());
        features.setSpecialFood("Gluten-Free");
        assertEquals("Gluten-Free", features.getSpecialFood());
    }

    @Test
    public void testNewSpecialFood() {
        assertEquals("Vegan", features.getNewSpecialFood());
        features.setNewSpecialFood("Low Carb");
        assertEquals("Low Carb", features.getNewSpecialFood());
    }
}
