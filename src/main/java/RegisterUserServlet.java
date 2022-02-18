import java.io.IOException;
import java.util.HashMap;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterUserServlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getMethod().equals("GET")) doGet(req, resp);
    if (req.getMethod().equals("POST")) doPost(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    RequestDispatcher dispatcher =
        getServletContext().getRequestDispatcher("/user-registration.jsp");
    dispatcher.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String name = req.getParameter(UserDataFilter.USERNAME_ATTR);
    String pass = req.getParameter("pswd");
    User newUser = new User(name, pass, false);
    RequestDispatcher dispatcher =
        getServletContext().getRequestDispatcher("/user-registration.jsp");
    boolean registered = false; // used to check if user is already registered
    HashMap<String, User> allUsers =
        (HashMap<String, User>) req.getServletContext().getAttribute(UserDataFilter.USER_ATTR);
    if (!allUsers.containsKey(name)) {
      allUsers.put(name, newUser);
      registered = true;
    }
    req.setAttribute("registered", registered);
    req.getServletContext().setAttribute(UserDataFilter.USER_ATTR, allUsers);
    dispatcher.forward(req, resp);
  }
}
