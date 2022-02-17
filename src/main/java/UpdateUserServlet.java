import java.io.IOException;
import java.util.TreeMap;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/update")
public class UpdateUserServlet extends HttpServlet {
  private RequestDispatcher dispatcher;

  public void init() throws ServletException {
    dispatcher = getServletContext().getRequestDispatcher("/user-update.jsp");
  }

  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getMethod().equals("GET")) doGet(req, resp);
    if (req.getMethod().equals("POST")) doPost(req, resp);
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    dispatcher.forward(req, resp);
  }

  // add doDelete()
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    TreeMap<String, String> allUsers =
        (TreeMap<String, String>) req.getServletContext().getAttribute(UserDataFilter.USER_ATTR);
    allUsers.remove(req.getServletContext().getAttribute("username"));
    req.getServletContext().setAttribute(UserDataFilter.USER_ATTR, allUsers);
    req.getServletContext().setAttribute("username", null);
    req.getServletContext().setAttribute(UserDataFilter.LOG_ATTR, false);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getParameter("logout") != null) {
      // logout option
      req.getServletContext().setAttribute(UserDataFilter.LOG_ATTR, false);
    } else if (req.getParameter("delete") != null) {
      // delete option
      doDelete(req, resp);
    } else {
      // login and update options
      String name = req.getParameter("username");
      String pass = req.getParameter("pswd");
      TreeMap<String, String> allUsers =
          (TreeMap<String, String>) req.getServletContext().getAttribute(UserDataFilter.USER_ATTR);
      if (req.getServletContext()
          .getAttribute(UserDataFilter.LOG_ATTR)
          .toString()
          .equals("false")) {
        // login option
        if (allUsers.containsKey(name) && allUsers.containsValue(pass)) {
          // success
          req.getServletContext().setAttribute(UserDataFilter.LOG_ATTR, true);
          req.getServletContext().setAttribute("username", name);
          req.setAttribute("logAttempt", false);
        } else {
          // wrong credentials
          req.setAttribute("logAttempt", true);
        }
      } else {
        // update option
        // update name
        if (name != null && !name.equals(req.getServletContext().getAttribute("username"))) {
          allUsers.put(name, allUsers.get(req.getServletContext().getAttribute("username")));
          allUsers.remove(req.getServletContext().getAttribute("username"));
          req.getServletContext().setAttribute("username", name);
        }
        // update password
        if (pass != null
            && !pass.equals(allUsers.get(req.getServletContext().getAttribute("username")))) {
          allUsers.replace(name, pass);
        }
        req.getServletContext().setAttribute(UserDataFilter.USER_ATTR, allUsers);
      }
    }
    dispatcher.forward(req, resp);
  }
}
