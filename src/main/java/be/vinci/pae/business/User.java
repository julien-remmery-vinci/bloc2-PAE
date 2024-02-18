package be.vinci.pae.business;

// Interface of UserImpl inherits of UserDTO
public interface User extends UserDTO {

  boolean checkPassword(String password);

  String hashPassword(String password);
}
