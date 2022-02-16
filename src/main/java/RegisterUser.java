import java.io.IOException;
import java.util.TreeMap;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterUser extends HttpServlet {

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
    boolean registered = false;
    TreeMap<String, String> allUsers =
        (TreeMap<String, String>) req.getServletContext().getAttribute(UserDataFilter.USER_ATTR);
    if (!allUsers.containsKey(name)) {
      allUsers.put(name, pass);
      registered = true;
    }
    req.setAttribute("registered", Boolean.toString(registered));
    req.getServletContext().setAttribute(UserDataFilter.USER_ATTR, allUsers);
    if (registered) {
      resp.getWriter().append("<p>Registered!</p> <a href=\"/\"> Go Back!</a>");
    } else {
      resp.getWriter().append("<p>User already exists</p> <a href=\"/register\"> Go Back!</a>");
    }
  }
}
