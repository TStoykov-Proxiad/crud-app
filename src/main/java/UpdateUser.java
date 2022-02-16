import java.io.IOException;
import java.util.TreeMap;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/update-user")
public class UpdateUser extends HttpServlet {
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getMethod().equals("GET")) doGet(req, resp);
    if (req.getMethod().equals("POST")) doPost(req, resp);
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-update.jsp");
    dispatcher.forward(req, resp);
  }

  // add doDelete()
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String name = req.getParameter("username");
    String pw = req.getParameter("pswd");
    TreeMap<String, String> allUsers =
        (TreeMap<String, String>) req.getServletContext().getAttribute(UserDataFilter.USER_ATTR);
    if (allUsers.containsKey(name) && allUsers.containsValue(pw)) {
      // give buttons for chaning username/pw or to delete acc
      resp.getWriter().append("Login success");
    } else {
      // return to page, give wrong combo msg
      resp.getWriter().append("Wrong details");
    }
  }
}
