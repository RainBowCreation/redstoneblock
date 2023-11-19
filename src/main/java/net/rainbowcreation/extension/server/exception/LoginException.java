package net.rainbowcreation.extension.server.exception;

public class LoginException extends AuthmodException {
  private static final long serialVersionUID = 1L;
  
  LoginException(String message) {
    super(message);
  }
}