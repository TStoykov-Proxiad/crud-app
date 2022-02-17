import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/list-users")
public class ListUsersServlet extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Set<Map.Entry<Integer, String>> entries =
        ((TreeMap) req.getServletContext().getAttribute(UserDataFilter.USER_ATTR)).entrySet();
    PrintWriter writer = resp.getWriter();
    writer.append("<html><body>");
    entries.forEach(
        entry -> {
          writer.append(
              "<p>" + "Username:" + entry.getKey() + " Password:" + entry.getValue() + "</p>");
        });
    writer.append(
        "<form action =\"/\">\r\n"
            + "        <button type = \"submit\">Go back to start</button>\r\n"
            + "      </form>");
    writer.append("</body></html");
  }
}
