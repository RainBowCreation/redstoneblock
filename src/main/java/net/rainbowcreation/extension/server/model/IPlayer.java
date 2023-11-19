package net.rainbowcreation.extension.server.model;

public interface IPlayer {
  boolean isBanned();
  
  IPlayer setBanned(boolean paramBoolean);
  
  IPlayer setPassword(String paramString);
  
  String getPassword();
  
  IPlayer setEmail(String paramString);
  
  String getEmail();
  
  IPlayer setUsername(String paramString);
  
  String getUsername();
  
  boolean isPremium();
  
  IPlayer setUuid(String paramString);
  
  String getUuid();
}