package be.vinci.pae.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserImpl.class)
public interface UserDTO {
  
}
