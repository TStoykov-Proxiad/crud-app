import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/list-users")
public class ListUsers extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Set<Map.Entry<Integer, String>> entries =
        ((TreeMap) req.getServletContext().getAttribute(UserDataFilter.USER_ATTR)).entrySet();

    entries.forEach(
        entry -> {
          try {
            resp.getWriter()
                .write("Username:" + entry.getKey() + " Password:" + entry.getValue() + "\n");
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        });
  }
}
