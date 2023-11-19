package net.rainbowcreation.extension.server.guard.payload;

import java.util.Set;
import javax.validation.ConstraintViolation;

public interface IPayload {
  IPayload setPassword(String paramString);
  
  boolean isValid();
  
  IPayload setUsername(String paramString);
  
  IPayload setUuid(String paramString);
  
  String getUuid();
  
  IPayload setEmail(String paramString);
  
  boolean isEmailRequired();
  
  IPayload setEmailRequired(boolean paramBoolean);
  
  String getEmail();
  
  String getUsername();
  
  String getPassword();
  
  Set<ConstraintViolation<IPayload>> getErrors();
}