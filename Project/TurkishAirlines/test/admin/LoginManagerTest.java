package admin;

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
    private ServletContext servletContext;

    @Mock
    private RequestDispatcher requestDispatcher;

    private ArrayList<Customer> customers;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        customers = new ArrayList<>();
        customers.add(new Customer("Admin", "admin@example.com", new ArrayList<>()));
        customers.add(new Customer("Manager", "manager@example.com", new ArrayList<>()));
        customers.add(new Customer("Customer", "customer@example.com", new ArrayList<>()));

    }

    @Test
    public void testCustomerRoleRedirectsToCurrentBooking() throws Exception {
        
        LoginManager loginManagerMocked = mock(LoginManager.class);
        
        when(servletContext.getAttribute("customers")).thenReturn(customers);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("CurrentBooking.do")).thenReturn(requestDispatcher);
        when(loginManagerMocked.getServletContext()).thenReturn(servletContext);
        
        when(request.isUserInRole("Customer")).thenReturn(true);
        when(request.getRemoteUser()).thenReturn("customer@example.com");
        when(session.getAttribute("customer")).thenReturn(null);

        loginManagerMocked.doGet(request, response);

        verify(request).getRequestDispatcher("CurrentBooking.do");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testAdminRoleRedirectsToChangeFeatures() throws Exception {
        when(request.isUserInRole("Admin")).thenReturn(true);

        loginManager.doGet(request, response);

        verify(response).sendRedirect("ChangeFeatures.jsp");
    }

    @Test
    public void testManagerRoleRedirectsToApproveFeatures() throws Exception {
        when(request.isUserInRole("Manager")).thenReturn(true);

        loginManager.doGet(request, response);

        verify(response).sendRedirect("ApproveFeatures.jsp");
    }

    @Test
    public void testUnauthenticatedUserRedirectsToHome() throws Exception {
        when(request.isUserInRole("Admin")).thenReturn(false);
        when(request.isUserInRole("Manager")).thenReturn(false);
        when(request.isUserInRole("Customer")).thenReturn(false);

        loginManager.doGet(request, response);

        verify(response).sendRedirect("home.jsp");
    }
}