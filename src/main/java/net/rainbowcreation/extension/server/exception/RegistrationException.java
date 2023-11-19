package net.rainbowcreation.extension.server.exception;

public class RegistrationException extends AuthmodException {
  private static final long serialVersionUID = 1L;
  
  RegistrationException(String message) {
    super(message);
  }
}