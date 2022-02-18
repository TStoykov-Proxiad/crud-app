import java.io.IOException;
import java.util.HashMap;
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
    req.getSession().setAttribute("userExists", false);
    req.getSession().setAttribute("alreadyLoggedIn", false); // could probably be done better
    dispatcher.forward(req, resp);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HashMap<String, User> allUsers =
        (HashMap<String, User>) req.getServletContext().getAttribute(UserDataFilter.USER_ATTR);
    allUsers.remove(req.getSession().getAttribute(UserDataFilter.USERNAME_ATTR));
    req.getServletContext().setAttribute(UserDataFilter.USER_ATTR, allUsers);
    req.getSession().setAttribute(UserDataFilter.USERNAME_ATTR, null);
    req.getSession().setAttribute(UserDataFilter.LOG_ATTR, false);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HashMap<String, User> allUsers =
        (HashMap<String, User>) req.getServletContext().getAttribute(UserDataFilter.USER_ATTR);
    if (req.getParameter("logout") != null) {
      // logout option
      req.getSession().setAttribute(UserDataFilter.LOG_ATTR, false);
      allUsers.get((String) req.getSession().getAttribute(UserDataFilter.USERNAME_ATTR)).logOut();
      req.getSession().removeAttribute(UserDataFilter.USERNAME_ATTR);
    } else if (req.getParameter("delete") != null) {
      // delete option
      doDelete(req, resp);
    } else {
      // login and update options
      String name = req.getParameter(UserDataFilter.USERNAME_ATTR);
      String pass = req.getParameter("pswd");
      String oldUsername = (String) req.getSession().getAttribute(UserDataFilter.USERNAME_ATTR);

      if (req.getSession().getAttribute(UserDataFilter.LOG_ATTR).toString().equals("false")) {
        // login option
        // check for if the user is already logged in on a different session
        if (allUsers.get(name).getIsLoggedIn()) {
          req.getSession().setAttribute("alreadyLoggedIn", true);
        } else {
          if (allUsers.containsKey(name) && allUsers.get(name).getPassword().equals(pass)) {
            // success
            req.getSession().setAttribute(UserDataFilter.LOG_ATTR, true);
            req.getSession().setAttribute(UserDataFilter.USERNAME_ATTR, name);
            req.setAttribute("logAttempt", false);
            allUsers.get(name).logIn();
          } else {
            // wrong credentials
            req.setAttribute("logAttempt", true);
          }
        }
      } else {
        User newUser = allUsers.get(oldUsername);
        // update option
        // used to check if the username is already in the system
        boolean userExists = false;
        // update name
        // check if empty or same as already logged in
        if (!name.equals("") && !name.equals(oldUsername)) {
          if (!allUsers.containsKey(name)) {
            // create new entry with updated username
            newUser.setUsername(name);
            allUsers.put(name, newUser);
            // remove old entry
            allUsers.remove(oldUsername);
            // update username
            req.getSession().setAttribute(UserDataFilter.USERNAME_ATTR, name);
          } else {
            userExists = true;
            req.getSession().setAttribute(UserDataFilter.LOG_ATTR, true);
          }
        }
        req.getSession().setAttribute("userExists", userExists);
        // update password
        if (!userExists) {
          // check for empty or same password
          if (!pass.equals("")
              && !pass.equals(
                  allUsers
                      .get(req.getSession().getAttribute(UserDataFilter.USERNAME_ATTR))
                      .getPassword())) {
            // set new password
            newUser.setPassword(pass);
            allUsers.replace(name, newUser);
          }
          // update users list and log user out
          newUser.logOut();
          req.getServletContext().setAttribute(UserDataFilter.USER_ATTR, allUsers);
          req.getSession().setAttribute(UserDataFilter.LOG_ATTR, false);
        }
      }
    }
    dispatcher.forward(req, resp);
  }
}
