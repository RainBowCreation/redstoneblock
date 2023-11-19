package net.rainbowcreation.extension.server.guard.datasource.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionFactory {
  Connection getConnection() throws SQLException;
  
  String getURL();
}