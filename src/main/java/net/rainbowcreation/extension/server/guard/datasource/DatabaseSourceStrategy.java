package net.rainbowcreation.extension.server.guard.datasource;

import net.rainbowcreation.extension.server.Main;
import net.rainbowcreation.extension.server.exception.PlayerAlreadyExistException;
import net.rainbowcreation.extension.server.exception.RegistrationException;
import net.rainbowcreation.extension.server.guard.datasource.db.IConnectionFactory;
import net.rainbowcreation.extension.server.guard.datasource.db.IPlayersDAO;
import net.rainbowcreation.extension.server.guard.datasource.db.PlayersDAO;
import net.rainbowcreation.extension.server.model.IPlayer;
import net.rainbowcreation.extension.server.model.Player;

import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class DatabaseSourceStrategy implements IDataSourceStrategy {
  private final IPlayersDAO<IPlayer> playersDAO;
  
  private static final Logger LOGGER = Main.LOGGER;
  
  public DatabaseSourceStrategy(String table, IConnectionFactory connectionFactory) throws SQLException {
    this.playersDAO = (IPlayersDAO<IPlayer>)new PlayersDAO(table, connectionFactory);
  }
  
  public DatabaseSourceStrategy(IConnectionFactory connectionFactory) throws SQLException {
    this.playersDAO = (IPlayersDAO<IPlayer>)new PlayersDAO("players", connectionFactory);
  }
  
  public IPlayer find(String email, String username) {
    try {
      IPlayer p = this.playersDAO.find((new Player()).setEmail(email).setUsername(username));
      return p;
    } catch (SQLException e) {
      LOGGER.catching(Level.ERROR, e);
      return null;
    } 
  }
  
  public boolean add(IPlayer player) throws RegistrationException {
    try {
      boolean alreadyExist = exist(player);
      if (alreadyExist)
        throw new PlayerAlreadyExistException();
      this.playersDAO.create(player);
      return true;
    } catch (SQLException e) {
      LOGGER.catching(Level.ERROR, e);
      return false;
    } 
  }
  
  public boolean exist(IPlayer player) {
    try {
      return (this.playersDAO.find(player) != null);
    } catch (SQLException e) {
      LOGGER.catching(Level.ERROR, e);
      return false;
    } 
  }
}