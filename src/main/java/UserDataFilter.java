import java.io.IOException;
import java.util.TreeMap;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*")
public class UserDataFilter implements Filter {
  public static final String USER_ATTR = "users";
  public static final String LOG_ATTR = "loggedIn";

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    filterConfig.getServletContext().setAttribute(USER_ATTR, new TreeMap<String, String>());
    filterConfig.getServletContext().setAttribute(LOG_ATTR, false);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    System.out.println("Oh no, your filter... is broken!");
  }
}
