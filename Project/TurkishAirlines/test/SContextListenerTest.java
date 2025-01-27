import models.Customer;
import models.FBS;
import models.Features;
import models.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SContextListenerTest {

    @InjectMocks
    private SContextListener sContextListener;

    @Mock
    private ServletContextEvent servletContextEvent;

    @Mock
    private ServletContext servletContext;

    @Mock
    private Connection connection;

    @Mock
    private FBS fbs;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(DriverManager.getConnection(anyString())).thenReturn(connection);
    }

    // Testa se o contexto é inicializado corretamente
    @Test
    void testContextInitialized() throws SQLException {
        ArrayList<Features> features = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Flight> flights = new ArrayList<>();

        when(fbs.populateFeatures(connection)).thenReturn(features);
        when(fbs.populateCustomers(connection)).thenReturn(customers);
        when(fbs.getAllFlights(connection, customers, features)).thenReturn(flights);

        sContextListener.contextInitialized(servletContextEvent);

        verify(servletContext).setAttribute("con", connection);
        verify(servletContext).setAttribute("features", features);
        verify(servletContext).setAttribute("fbs", fbs);
        verify(servletContext).setAttribute("flights", flights);
        verify(servletContext).setAttribute("customers", customers);
    }

    // Testa se o contexto é destruído corretamente
    @Test
    void testContextDestroyed() {
        sContextListener.contextDestroyed(servletContextEvent);
        // Nenhuma ação específica a ser verificada
    }

    // Testa se a conexão é fechada corretamente
    @Test
    void testConnectionClosed() throws SQLException {
        sContextListener.contextDestroyed(servletContextEvent);
        verify(connection, times(1)).close();
    }

    // Testa se o método contextInitialized não lança exceções
    @Test
    void testContextInitializedDoesNotThrowException() {
        assertDoesNotThrow(() -> sContextListener.contextInitialized(servletContextEvent));
    }

    // Testa se o método contextDestroyed não lança exceções
    @Test
    void testContextDestroyedDoesNotThrowException() {
        assertDoesNotThrow(() -> sContextListener.contextDestroyed(servletContextEvent));
    }
}