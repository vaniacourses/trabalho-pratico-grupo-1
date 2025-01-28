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
    
    private LoginManager loginManager;

    private ArrayList<Customer> customers;  // Lista de clientes simulada
    
    @Mock
    private ServletContext servletContext;
    
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Before
    public void setUp() {
        // Inicializa o objeto que será testado
        loginManager = new LoginManager();
        MockitoAnnotations.openMocks(this);

        // Inicializa a lista de clientes (simulada)
        customers = new ArrayList<>();
        // Adicionando um cliente à lista
        customers.add(new Customer("Admin", "haris@admin.com", new ArrayList<>()));
        customers.add(new Customer("Manager", "haris@manager.com", new ArrayList<>()));
        customers.add(new Customer("Customer", "shariq@customer.com", new ArrayList<>()));
        
        // Mockando o comportamento do getServletContext() para retornar a lista de clientes
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("customers")).thenReturn(customers);

        // Mockando o comportamento do request.getSession()
        when(request.getSession()).thenReturn(session);
        when(request.isUserInRole("Customer")).thenReturn(true);
        when(request.getRemoteUser()).thenReturn("shariq@customer.com");

    }
    
    @Test
    public void testCustomerRoleRedirectsToCurrentBooking() throws Exception {

        // Simulando que o cliente ainda não foi definido na sessão
        when(session.getAttribute("customer")).thenReturn(null);

        // Executando o método doGet
        loginManager.doGet(request, response);

        // Verificando se a requisição foi encaminhada para "CurrentBooking.do"
        verify(request).getRequestDispatcher("CurrentBooking.do");

        // Verificando se o cliente foi adicionado à sessão corretamente
        Customer customer = customers.get(2); // O cliente "customer@example.com"
        verify(session).setAttribute("customer", customer);  // Verifica se o cliente foi adicionado à sessão
    }

    
    
    @Test
    public void testAdminRoleRedirectsToChangeFeatures() throws Exception {
        // Configura o mock para o request com a role de Admin
        when(request.isUserInRole("Admin")).thenReturn(true);

        // Chama o método doGet do LoginManager
        loginManager.doGet(request, response);

        // Verifica se a resposta foi redirecionada para a página "ChangeFeatures.jsp"
        verify(response).sendRedirect("ChangeFeatures.jsp");
    }

    @Test
    public void testManagerRoleRedirectsToApproveFeatures() throws Exception {
        // Configura o mock para o request com a role de Manager
        when(request.isUserInRole("Manager")).thenReturn(true);

        // Chama o método doGet do LoginManager
        loginManager.doGet(request, response);

        // Verifica se a resposta foi redirecionada para a página "ApproveFeatures.jsp"
        verify(response).sendRedirect("ApproveFeatures.jsp");
    }

    @Test
    public void testUnauthenticatedUserRedirectsToHome() throws Exception {
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
