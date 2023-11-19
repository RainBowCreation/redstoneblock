package net.rainbowcreation.extension.server.guard.datasource.db;

import net.rainbowcreation.extension.server.model.IPlayer;

import java.sql.SQLException;

public interface IPlayersDAO<P extends IPlayer> {
  void create(P paramP) throws SQLException;
  
  P find(P paramP) throws SQLException;
}