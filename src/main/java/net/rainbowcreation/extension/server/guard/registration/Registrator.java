package net.rainbowcreation.extension.server.guard.registration;

import net.rainbowcreation.extension.server.exception.InvalidEmailException;
import net.rainbowcreation.extension.server.exception.InvalidPasswordException;
import net.rainbowcreation.extension.server.exception.PlayerAlreadyExistException;
import net.rainbowcreation.extension.server.exception.RegistrationException;
import net.rainbowcreation.extension.server.guard.PlayerFactory;
import net.rainbowcreation.extension.server.guard.datasource.FileDataSourceStrategy;
import net.rainbowcreation.extension.server.guard.datasource.IDataSourceStrategy;
import net.rainbowcreation.extension.server.model.IPlayer;
import net.rainbowcreation.extension.server.exception.WrongPasswordConfirmationException;
import net.rainbowcreation.extension.server.guard.payload.IPayload;
import net.rainbowcreation.extension.server.guard.payload.RegistrationPayload;

import java.nio.file.Paths;
import javax.validation.ConstraintViolation;

import org.mindrot.jbcrypt.BCrypt;

public class Registrator {
  private final IDataSourceStrategy dataSource;
  
  public Registrator() {
    this((IDataSourceStrategy)new FileDataSourceStrategy(Paths.get(System.getProperty("java.io.tmpdir"), new String[] { "authmod.csv" }).toFile()));
  }
  
  public Registrator(IDataSourceStrategy dataSourceStrategy) {
    this.dataSource = dataSourceStrategy;
  }
  
  public boolean register(RegistrationPayload payload) throws RegistrationException {
    if (payload != null) {
      if (payload.isValid()) {
        IPlayer player = PlayerFactory.createFromRegistrationPayload(payload);
        if (this.dataSource.exist(player))
          throw new PlayerAlreadyExistException();
        player.setPassword(BCrypt.hashpw(player.getPassword(), BCrypt.gensalt()));
        return this.dataSource.add(player);
      } 
      for (ConstraintViolation<IPayload> c : (Iterable<ConstraintViolation<IPayload>>)payload.getErrors()) {
        if (c.getPropertyPath().toString().equals("email"))
          throw new InvalidEmailException();
        if (c.getPropertyPath().toString().equals("passwordConfirmationMatches"))
          throw new WrongPasswordConfirmationException(); 
        if (c.getPropertyPath().toString().equals("password"))
          throw new InvalidPasswordException();
      } 
    } 
    return false;
  }
  
  public IDataSourceStrategy getDataSourceStrategy() {
    return this.dataSource;
  }
}


/* Location:              D:\jd-gui\authmod-3.2.jar!\io\chocorean\authmod\guard\registration\Registrator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */