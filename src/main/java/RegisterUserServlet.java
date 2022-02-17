import java.io.IOException;
import java.util.TreeMap;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterUserServlet extends HttpServlet {

  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getMethod().equals("GET")) doGet(req, resp);
    if (req.getMethod().equals("POST")) doPost(req, resp);
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    RequestDispatcher dispatcher =
        getServletContext().getRequestDispatcher("/user-registration.jsp");
    dispatcher.forward(req, resp);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String name = req.getParameter("username");
    String pass = req.getParameter("pswd");
    RequestDispatcher dispatcher =
        getServletContext().getRequestDispatcher("/user-registration.jsp");
    boolean registered = false; // used to check if user is already registered
    TreeMap<String, String> allUsers =
        (TreeMap<String, String>) req.getServletContext().getAttribute(UserDataFilter.USER_ATTR);
    if (!allUsers.containsKey(name)) {
      allUsers.put(name, pass);
      registered = true;
    }
    req.setAttribute("registered", registered);
    req.getServletContext().setAttribute(UserDataFilter.USER_ATTR, allUsers);
    dispatcher.forward(req, resp);
  }
}
