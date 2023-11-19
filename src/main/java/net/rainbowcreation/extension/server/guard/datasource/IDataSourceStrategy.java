package net.rainbowcreation.extension.server.guard.datasource;

import net.rainbowcreation.extension.server.exception.RegistrationException;
import net.rainbowcreation.extension.server.model.IPlayer;

public interface IDataSourceStrategy {
  IPlayer find(String paramString1, String paramString2);
  
  boolean add(IPlayer paramIPlayer) throws RegistrationException;
  
  boolean exist(IPlayer paramIPlayer);
}