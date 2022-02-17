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

  @Override
  public void init() throws ServletException {
    dispatcher = getServletContext().getRequestDispatcher("/user-update.jsp");
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getMethod().equals("GET")) doGet(req, resp);
    if (req.getMethod().equals("POST")) doPost(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    dispatcher.forward(req, resp);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    TreeMap<String, String> allUsers =
        (TreeMap<String, String>) req.getServletContext().getAttribute(UserDataFilter.USER_ATTR);
    allUsers.remove(req.getSession().getAttribute(UserDataFilter.USERNAME_ATTR));
    req.getServletContext().setAttribute(UserDataFilter.USER_ATTR, allUsers);
    req.getSession().setAttribute(UserDataFilter.USERNAME_ATTR, null);
    req.getSession().setAttribute(UserDataFilter.LOG_ATTR, false);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getParameter("logout") != null) {
      // logout option
      req.getSession().setAttribute(UserDataFilter.LOG_ATTR, false);
    } else if (req.getParameter("delete") != null) {
      // delete option
      doDelete(req, resp);
    } else {
      // login and update options
      String name = req.getParameter(UserDataFilter.USERNAME_ATTR);
      String pass = req.getParameter("pswd");
      TreeMap<String, String> allUsers =
          (TreeMap<String, String>) req.getServletContext().getAttribute(UserDataFilter.USER_ATTR);
      if (req.getSession().getAttribute(UserDataFilter.LOG_ATTR).toString().equals("false")) {
        // login option
        if (allUsers.containsKey(name) && allUsers.containsValue(pass)) {
          // success
          req.getSession().setAttribute(UserDataFilter.LOG_ATTR, true);
          req.getSession().setAttribute(UserDataFilter.USERNAME_ATTR, name);
          req.setAttribute("logAttempt", false);
        } else {
          // wrong credentials
          req.setAttribute("logAttempt", true);
        }
      } else {
        // update option
        // update name
        if (!name.equals("")
            && !name.equals(req.getSession().getAttribute(UserDataFilter.USERNAME_ATTR))) {
          allUsers.put(
              name, allUsers.get(req.getSession().getAttribute(UserDataFilter.USERNAME_ATTR)));
          allUsers.remove(req.getSession().getAttribute(UserDataFilter.USERNAME_ATTR));
          req.getSession().setAttribute(UserDataFilter.USERNAME_ATTR, name);
        }
        // update password
        if (!pass.equals("")
            && !pass.equals(
                allUsers.get(req.getSession().getAttribute(UserDataFilter.USERNAME_ATTR)))) {
          allUsers.replace(name, pass);
        }
        req.getServletContext().setAttribute(UserDataFilter.USER_ATTR, allUsers);
        req.getSession().setAttribute(UserDataFilter.LOG_ATTR, false);
      }
    }
    dispatcher.forward(req, resp);
  }
}
