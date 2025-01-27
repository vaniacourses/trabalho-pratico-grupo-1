import models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.mockito.Mockito.*;

public class LoginManagerTest {

    @InjectMocks
    private LoginManager loginManager;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customers = new ArrayList<>();
        customers.add(new Customer("customer1@example.com"));
        customers.add(new Customer("customer2@example.com"));

        when(context.getAttribute("customers")).thenReturn(customers);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("CurrentBooking.do")).thenReturn(requestDispatcher);
    }

    // Testa redirecionamento para ChangeFeatures para Admin
    @Test
    void testAdminRoleRedirectsToChangeFeatures() throws Exception {
        when(request.isUserInRole("Admin")).thenReturn(true);

        loginManager.doGet(request, response);

        verify(response).sendRedirect("ChangeFeatures.jsp");
    }

    // Testa redirecionamento para ApproveFeatures para Manager
    @Test
    void testManagerRoleRedirectsToApproveFeatures() throws Exception {
        when(request.isUserInRole("Manager")).thenReturn(true);

        loginManager.doGet(request, response);

        verify(response).sendRedirect("ApproveFeatures.jsp");
    }

    // Testa redirecionamento para CurrentBooking para Customer
    @Test
    void testCustomerRoleRedirectsToCurrentBooking() throws Exception {
        when(request.isUserInRole("Customer")).thenReturn(true);
        when(request.getRemoteUser()).thenReturn("customer1@example.com");

        loginManager.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    // Testa se o cliente é definido na sessão
    @Test
    void testCustomerIsSetInSession() throws Exception {
        when(request.isUserInRole("Customer")).thenReturn(true);
        when(request.getRemoteUser()).thenReturn("customer1@example.com");
        when(session.getAttribute("customer")).thenReturn(null);

        loginManager.doGet(request, response);

        verify(session).setAttribute("customer", customers.get(0));
    }

    // Testa se o cliente não é redefinido na sessão
    @Test
    void testCustomerNotResetInSession() throws Exception {
        when(request.isUserInRole("Customer")).thenReturn(true);
        when(session.getAttribute("customer")).thenReturn(customers.get(0));

        loginManager.doGet(request, response);

        verify(session, never()).setAttribute(eq("customer"), any());
    }

    // Testa redirecionamento para home para usuários não autenticados
    @Test
    void testUnauthenticatedUserRedirectsToHome() throws Exception {
        when(request.isUserInRole("Admin")).thenReturn(false);
        when(request.isUserInRole("Manager")).thenReturn(false);
        when(request.isUserInRole("Customer")).thenReturn(false);

        loginManager.doGet(request, response);

        verify(response).sendRedirect("home.jsp");
    }
}