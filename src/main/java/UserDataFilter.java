import java.io.IOException;
import java.util.TreeMap;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class UserDataFilter implements Filter {
  public static final String USER_ATTR = "users";
  public static final String LOG_ATTR = "loggedIn";
  public static final String USERNAME_ATTR = "username";
  private boolean loggedIn = false;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    filterConfig.getServletContext().setAttribute(USER_ATTR, new TreeMap<String, String>());
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpSession session = ((HttpServletRequest) request).getSession();
    if (session.getAttribute(LOG_ATTR) != null) {
      loggedIn = (boolean) session.getAttribute(LOG_ATTR);
    }
    session.setAttribute(LOG_ATTR, loggedIn);
    chain.doFilter(request, response);
  }
}
