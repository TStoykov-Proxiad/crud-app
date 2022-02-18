public class User {
  private String username;
  private String password;
  private boolean isLoggedIn;

  User(String user, String pass, boolean logged) {
    this.username = user;
    this.password = pass;
    this.isLoggedIn = logged;
  }

  User(User toCopy) {
    this.username = toCopy.getUsername();
    this.password = toCopy.getPassword();
    this.isLoggedIn = toCopy.getIsLoggedIn();
  }

  @Override
  public String toString() {
    return "Username: "
        + this.username
        + "<br />Password: "
        + this.password
        + "<br />The user is logged in: "
        + this.isLoggedIn;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public boolean getIsLoggedIn() {
    return this.isLoggedIn;
  }

  public void setUsername(String newUsername) {
    this.username = newUsername;
  }

  public void setPassword(String newPassword) {
    this.password = newPassword;
  }

  public void logIn() {
    this.isLoggedIn = true;
  }

  public void logOut() {
    this.isLoggedIn = false;
  }
}
