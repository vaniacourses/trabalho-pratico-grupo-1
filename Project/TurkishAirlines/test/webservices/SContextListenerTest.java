package webservices;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import models.Customer;
import models.FBS;
import models.Features;
import models.Flight;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class SContextListenerTest {

    // Mocks para as dependências
    @Mock private ServletContextEvent servletContextEvent;
    @Mock private ServletContext servletContext;
    @Mock private FBS fbs;
    @Mock private Connection connection;
    
    // Instância do listener a ser testado
    private SContextListener contextListener;

    // Inicializa os mocks antes de cada teste
    @Before
    public void setUp() {
        // Inicializa os mocks do Mockito
        MockitoAnnotations.openMocks(this);
        
        // Configura o mock do ServletContextEvent para retornar o ServletContext mockado
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        
        // Cria uma instância do listener
        contextListener = new SContextListener();
    }

    // Teste do método contextInitialized
    @Test
    public void testContextInitialized() throws SQLException {
        // Simula a conexão com o banco de dados
        when(servletContext.getAttribute("con")).thenReturn(connection);

        // Simula a interação com o FBS
        ArrayList<Features> features = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Flight> flights = new ArrayList<>();
        
        when(fbs.populateFeatures(connection)).thenReturn(features);
        when(fbs.populateCustomers(connection)).thenReturn(customers);
        when(fbs.getAllFlights(connection, customers, features)).thenReturn(flights);

        // Chamamos o método que estamos testando
        contextListener.contextInitialized(servletContextEvent);

        // Verifica se a conexão foi setada no ServletContext
        verify(servletContext).setAttribute(eq("con"), any(Connection.class));

        // Verifica se o método populateFeatures foi chamado
        verify(fbs).populateFeatures(connection);
        
        // Verifica se o método populateCustomers foi chamado
        verify(fbs).populateCustomers(connection);

        // Verifica se o método getAllFlights foi chamado
        verify(fbs).getAllFlights(connection, customers, features);
        
        // Verifica se as features, fbs, flights e customers foram setados no ServletContext
        verify(servletContext).setAttribute(eq("features"), eq(features));
        verify(servletContext).setAttribute(eq("fbs"), eq(fbs));
        verify(servletContext).setAttribute(eq("flights"), eq(flights));
        verify(servletContext).setAttribute(eq("customers"), eq(customers));
    }

    // Teste do método contextInitialized quando ocorre uma exceção
    @Test
    public void testContextInitializedThrowsSQLException() throws SQLException {
        // Simula uma exceção no momento da conexão com o banco
        when(fbs.populateFeatures(connection)).thenThrow(new SQLException("Database error"));

        // Executa o método, esperando que ele não cause um erro fatal
        contextListener.contextInitialized(servletContextEvent);

        // Verifica se o log de erro ou o comportamento esperado foi executado
        // O que pode ser adicionado aqui depende de como o seu código gerencia falhas
    }

    // Teste do método contextDestroyed
    @Test
    public void testContextDestroyed() {
        // Chama o método contextDestroyed, sem comportamentos a testar diretamente
        contextListener.contextDestroyed(servletContextEvent);
        
       // Teste básico de invocação
        verify(servletContextEvent, times(1)).getServletContext();  // Verifica se o método foi invocado no mock
    }
}
