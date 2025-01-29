package manager;

import customer.CustomerManager;
import models.Customer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class CustomerManagerTest {

    @InjectMocks
    private CustomerManager customerManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private ServletContext context;

    @Mock
    private RequestDispatcher requestDispatcher;

    private ArrayList<Customer> customers;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        customers = new ArrayList<>();
        customers.add(new Customer("John Doe", "john.doe@example.com", null));
        customers.add(new Customer("Jane Doe", "jane.doe@example.com", null));

        when(context.getAttribute("customers")).thenReturn(customers);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    // Testa se o cliente é definido na sessão
    @Test
    public void testCustomerIsSetInSession() throws ServletException, IOException {
        when(request.getRemoteUser()).thenReturn("john.doe@example.com");

        customerManager.doGet(request, response);

        verify(session).setAttribute(eq("customer"), any(Customer.class));
    }

    // Testa se o redirecionamento é feito corretamente
    @Test
    public void testRedirectIsDone() throws ServletException, IOException {
        when(request.getRemoteUser()).thenReturn("john.doe@example.com");
        when(request.getRequestURI()).thenReturn("/somepath/SomePage.jsp");

        customerManager.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    // Testa se o método doGet não lança exceções
    @Test
    public void testDoGetDoesNotThrowException() {
        when(request.getRemoteUser()).thenReturn("john.doe@example.com");

        assertDoesNotThrow(() -> {
            try {
                customerManager.doGet(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(CustomerManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Testa se o cliente não é definido na sessão se já estiver presente
    @Test
    public void testCustomerNotSetIfAlreadyInSession() throws ServletException, IOException {
        when(session.getAttribute("customer")).thenReturn(customers.get(0));

        customerManager.doGet(request, response);

        verify(session, never()).setAttribute(eq("customer"), any(Customer.class));
    }

    // Testa se o cliente é encontrado na lista de clientes
    @Test
    public void testCustomerIsFoundInList() throws ServletException, IOException {
        when(request.getRemoteUser()).thenReturn("jane.doe@example.com");

        customerManager.doGet(request, response);

        verify(session).setAttribute("customer", customers.get(1));
    }
}