package models;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;
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

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
    }

    @Test
    public void testPopulateCustomersReturnsCorrectData() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("email")).thenReturn("john.doe@example.com");

        ArrayList<Customer> customers = fbs.populateCustomers(connection);

        assertNotNull(customers);
        assertEquals(1, customers.size());
        Customer customer = customers.get(0);
        assertEquals("john.doe@example.com", customer.getEmail());
    }

    @Test
    public void testPopulateFeaturesWithValidData() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("price")).thenReturn(100);
        when(resultSet.getInt("newPrice")).thenReturn(120);
        when(resultSet.getBoolean("isChanged")).thenReturn(true);

        ArrayList<Features> features = fbs.populateFeatures(connection);

        assertNotNull(features);
        assertEquals(1, features.size());
        Features feature = features.get(0);
        assertEquals(100, feature.getPrice());
        assertEquals(120, feature.getNewPrice());
        assertTrue(feature.getIsChanged());
    }

    @Test
    public void testGetAllFlightsReturnsValidData() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("FlightName")).thenReturn("Flight101");
        when(resultSet.getInt("TotalSeats")).thenReturn(10);
        when(resultSet.getInt("CurrentSeats")).thenReturn(10);

        ArrayList<Flight> flights = fbs.getAllFlights(connection, new ArrayList<>(), new ArrayList<>());

        assertNotNull(flights);
        assertEquals(1, flights.size());
        Flight flight = flights.get(0);
        assertEquals("Flight101", flight.getFlightName());
        assertEquals(10, flight.getTotalSeats());
        assertEquals(10, flight.getCurrentSeats());
    }

    @Test
    public void testGetPriceReturnsCorrectValue() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("price")).thenReturn(100);

        int price = FBS.getPrice("CityA", "CityB");

        assertEquals(100, price);
        verify(statement).executeQuery(anyString());
    }

    @Test
    public void testGetSeatsReturnsCorrectValue() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("CURRENTSEATS")).thenReturn(10);

        int seats = FBS.getSeats("Flight101", "12/12/2023");

        assertEquals(10, seats);
        verify(statement).executeQuery(anyString());
    }
}
