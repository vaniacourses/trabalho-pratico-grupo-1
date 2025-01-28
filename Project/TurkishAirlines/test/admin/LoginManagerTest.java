package admin;
import models.Customer;
import models.Seat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.mockito.Mockito.*;
import javax.servlet.RequestDispatcher;

import java.util.ArrayList;
import models.Customer;

public class LoginManagerTest {
    
    public LoginManager loginManager;

    public ArrayList<Customer> customers;  // Lista de clientes simulada

    @Before
    public void setUp() {
        // Inicializa o objeto que será testado
        loginManager = new LoginManager();
        MockitoAnnotations.openMocks(this);

        // Inicializa a lista de clientes (simulada)
        customers = new ArrayList<>();
        // Adicionando um cliente à lista
        customers.add(new Customer("Admin", "admin@example.com", new ArrayList<>()));
        customers.add(new Customer("Manager", "manager@example.com", new ArrayList<>()));
        customers.add(new Customer("Customer", "customer@example.com", new ArrayList<>()));

    }

    @Test
    public void testAdminRoleRedirectsToChangeFeatures() throws Exception {
        // Simulando o HttpServletRequest e HttpServletResponse
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Configura o mock para o request com a role de Admin
        when(request.isUserInRole("Admin")).thenReturn(true);

        // Chama o método doGet do LoginManager
        loginManager.doGet(request, response);

        // Verifica se a resposta foi redirecionada para a página "ChangeFeatures.jsp"
        verify(response).sendRedirect("ChangeFeatures.jsp");
    }

    @Test
    public void testManagerRoleRedirectsToApproveFeatures() throws Exception {
        // Simulando o HttpServletRequest e HttpServletResponse
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Configura o mock para o request com a role de Manager
        when(request.isUserInRole("Manager")).thenReturn(true);

        // Chama o método doGet do LoginManager
        loginManager.doGet(request, response);

        // Verifica se a resposta foi redirecionada para a página "ApproveFeatures.jsp"
        verify(response).sendRedirect("ApproveFeatures.jsp");
    }

    @Test
    public void testUnauthenticatedUserRedirectsToHome() throws Exception {
        // Simulando o HttpServletRequest e HttpServletResponse
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Configura o mock para o request sem nenhuma role
        when(request.isUserInRole("Admin")).thenReturn(false);
        when(request.isUserInRole("Manager")).thenReturn(false);
        when(request.isUserInRole("Customer")).thenReturn(false);

        // Chama o método doGet do LoginManager
        loginManager.doGet(request, response);

        // Verifica se a resposta foi redirecionada para "home.jsp"
        verify(response).sendRedirect("home.jsp");
    }
}
