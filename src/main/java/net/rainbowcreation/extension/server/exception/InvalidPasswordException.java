package net.rainbowcreation.extension.server.exception;

public class InvalidPasswordException extends RegistrationException {
  private static final long serialVersionUID = 1L;
  
  public InvalidPasswordException() {
    super("Password too short !");
  }
}