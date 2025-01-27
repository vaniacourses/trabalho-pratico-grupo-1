package webservices;

import models.FBS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PriceAndSeatsTest {

    @InjectMocks
    private PriceAndSeats priceAndSeats;

    @Mock
    private FBS fbs;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Testa se o preço é retornado corretamente
    @Test
    void testGetPrice() {
        when(FBS.getPrice("CityA", "CityB")).thenReturn(100);

        int price = priceAndSeats.getPrice("CityA", "CityB");

        assertEquals(100, price);
    }

    // Testa se o número de assentos é retornado corretamente
    @Test
    void testGetSeats() {
        when(FBS.getSeats("Flight101", "12/12/2023")).thenReturn(10);

        int seats = priceAndSeats.getSeats("Flight101", "12/12/2023");

        assertEquals(10, seats);
    }
}