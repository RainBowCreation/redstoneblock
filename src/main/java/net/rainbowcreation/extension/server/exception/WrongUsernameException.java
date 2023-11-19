package net.rainbowcreation.extension.server.exception;

public class WrongUsernameException extends LoginException {
  private static final long serialVersionUID = 1L;
  
  public WrongUsernameException() {
    super("Your username doesn't match with the server credentials");
  }
}