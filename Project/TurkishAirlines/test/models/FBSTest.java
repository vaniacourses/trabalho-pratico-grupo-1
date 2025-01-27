package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FBSTest {

    @InjectMocks
    private FBS fbs;

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
    }

    // Testa se os clientes são populados corretamente
    @Test
    void testPopulateCustomers() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("email")).thenReturn("john.doe@example.com");

        ArrayList<Customer> customers = fbs.populateCustomers(connection);

        assertEquals(1, customers.size());
        assertEquals("John Doe", customers.get(0).getName());
        assertEquals("john.doe@example.com", customers.get(0).getEmail());
    }

    // Testa se as features são populadas corretamente
    @Test
    void testPopulateFeatures() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getBoolean("isChanged")).thenReturn(true);
        when(resultSet.getInt("type")).thenReturn(1);
        when(resultSet.getDouble("seatPitch")).thenReturn(32.5);
        when(resultSet.getDouble("seatWidth")).thenReturn(18.0);
        when(resultSet.getDouble("NewSeatPitch")).thenReturn(33.0);
        when(resultSet.getDouble("NewSeatWidth")).thenReturn(18.5);
        when(resultSet.getString("VideoType")).thenReturn("LCD");
        when(resultSet.getString("NewVideoType")).thenReturn("LED");
        when(resultSet.getString("SeatType")).thenReturn("Economy");
        when(resultSet.getString("NewSeatType")).thenReturn("Business");
        when(resultSet.getString("PowerType")).thenReturn("AC");
        when(resultSet.getString("NewPowerType")).thenReturn("DC");
        when(resultSet.getString("Wifi")).thenReturn("Yes");
        when(resultSet.getString("NewWifi")).thenReturn("No");
        when(resultSet.getString("SpecialFood")).thenReturn("Vegetarian");
        when(resultSet.getString("NewSpecialFood")).thenReturn("Vegan");
        when(resultSet.getInt("price")).thenReturn(100);
        when(resultSet.getInt("newPrice")).thenReturn(120);

        ArrayList<Features> features = fbs.populateFeatures(connection);

        assertEquals(1, features.size());
        Features feature = features.get(0);
        assertTrue(feature.isChanged);
        assertEquals(1, feature.getType());
        assertEquals(32.5, feature.getSeatPitch());
        assertEquals(18.0, feature.getSeatWidth());
        assertEquals(33.0, feature.getNewSeatPitch());
        assertEquals(18.5, feature.getNewSeatWidth());
        assertEquals("LCD", feature.getVideoType());
        assertEquals("LED", feature.getNewVideoType());
        assertEquals("Economy", feature.getSeatType());
        assertEquals("Business", feature.getNewSeatType());
        assertEquals("AC", feature.getPowerType());
        assertEquals("DC", feature.getNewPowerType());
        assertEquals("Yes", feature.getWifi());
        assertEquals("No", feature.getNewWifi());
        assertEquals("Vegetarian", feature.getSpecialFood());
        assertEquals("Vegan", feature.getNewSpecialFood());
        assertEquals(100, feature.getPrice());
        assertEquals(120, feature.getNewPrice());
    }

    // Testa se todos os voos são retornados corretamente
    @Test
    void testGetAllFlights() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Features> features = new ArrayList<>();

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getBoolean("isChanged")).thenReturn(true);
        when(resultSet.getString("FlightName")).thenReturn("Flight101");
        when(resultSet.getInt("TotalSeats")).thenReturn(10);
        when(resultSet.getInt("CurrentSeats")).thenReturn(10);
        when(resultSet.getString("DepartureCity")).thenReturn("CityA");
        when(resultSet.getString("ArrivalCity")).thenReturn("CityB");
        when(resultSet.getDate("DepartureDate")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(resultSet.getDate("ArrivalDate")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(resultSet.getInt("EconomySeats")).thenReturn(5);
        when(resultSet.getInt("BusinessSeats")).thenReturn(3);
        when(resultSet.getInt("FirstSeats")).thenReturn(2);
        when(resultSet.getInt("OldESeats")).thenReturn(5);
        when(resultSet.getInt("OldBSeats")).thenReturn(3);
        when(resultSet.getInt("OldFSeats")).thenReturn(2);
        when(resultSet.getInt("OldTSeats")).thenReturn(10);

        ArrayList<Flight> flights = fbs.getAllFlights(connection, customers, features);

        assertEquals(1, flights.size());
        Flight flight = flights.get(0);
        assertTrue(flight.isChanged);
        assertEquals("Flight101", flight.getFlightName());
        assertEquals(10, flight.getTotalSeats());
        assertEquals(10, flight.getCurrentSeats());
        assertEquals("CityA", flight.getDepartureCity());
        assertEquals("CityB", flight.getArrivalCity());
        assertNotNull(flight.getDepartureDate());
        assertNotNull(flight.getArrivalDate());
        assertEquals(5, flight.getEconomySeats());
        assertEquals(3, flight.getBusinessSeats());
        assertEquals(2, flight.getFirstSeats());
        assertEquals(5, flight.getOldESeats());
        assertEquals(3, flight.getOldBSeats());
        assertEquals(2, flight.getOldFSeats());
        assertEquals(10, flight.getOldTSeats());
    }

    // Testa se o preço é retornado corretamente
    @Test
    void testGetPrice() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("price")).thenReturn(100);

        int price = FBS.getPrice("CityA", "CityB");

        assertEquals(100, price);
    }

    // Testa se o número de assentos é retornado corretamente
    @Test
    void testGetSeats() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("CURRENTSEATS")).thenReturn(10);

        int seats = FBS.getSeats("Flight101", "12/12/2023");

        assertEquals(10, seats);
    }
}