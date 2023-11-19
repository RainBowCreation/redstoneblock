package net.rainbowcreation.extension.server.exception;

public class BannedPlayerException extends LoginException {
  private static final long serialVersionUID = 1L;
  
  public BannedPlayerException() {
    super("you've banned from this server");
  }
}