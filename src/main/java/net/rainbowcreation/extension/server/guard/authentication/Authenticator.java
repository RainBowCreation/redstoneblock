package net.rainbowcreation.extension.server.guard.authentication;

import net.rainbowcreation.extension.server.exception.BannedPlayerException;
import net.rainbowcreation.extension.server.exception.InvalidEmailException;
import net.rainbowcreation.extension.server.exception.LoginException;
import net.rainbowcreation.extension.server.exception.PlayerNotFoundException;
import net.rainbowcreation.extension.server.guard.datasource.IDataSourceStrategy;
import net.rainbowcreation.extension.server.model.IPlayer;
import net.rainbowcreation.extension.server.exception.WrongPasswordException;
import net.rainbowcreation.extension.server.exception.WrongUsernameException;
import net.rainbowcreation.extension.server.guard.payload.IPayload;
import net.rainbowcreation.extension.server.guard.payload.LoginPayload;

import javax.validation.ConstraintViolation;

import org.mindrot.jbcrypt.BCrypt;

public class Authenticator {
  private final IDataSourceStrategy dataSource;
  
  public Authenticator(IDataSourceStrategy dataSourceStrategy) {
    this.dataSource = dataSourceStrategy;
  }
  
  public boolean login(LoginPayload payload) throws LoginException, InvalidEmailException {
    if (payload != null) {
      if (payload.isValid()) {
        IPlayer player = this.dataSource.find(payload.getEmail(), payload.getUsername());
        if (player == null)
          throw new PlayerNotFoundException();
        if (!player.getUsername().equals(payload.getUsername()))
          throw new WrongUsernameException();
        if (player.isBanned())
          throw new BannedPlayerException();
        boolean correct = BCrypt.checkpw(payload.getPassword(), player.getPassword());
        if (!correct)
          throw new WrongPasswordException(); 
        return true;
      } 
      for (ConstraintViolation<IPayload> c : (Iterable<ConstraintViolation<IPayload>>)payload.getErrors()) {
        if (c.getPropertyPath().toString().equals("email"))
          throw new InvalidEmailException(); 
        if (c.getPropertyPath().toString().equals("password"))
          throw new WrongPasswordException(); 
      } 
    } 
    return false;
  }
  
  IDataSourceStrategy getDataSourceStrategy() {
    return this.dataSource;
  }
}