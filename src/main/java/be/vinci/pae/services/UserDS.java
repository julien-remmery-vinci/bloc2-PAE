package be.vinci.pae.services;

import be.vinci.pae.business.UserDTO;

public interface UserDS {

  UserDTO getOneByEmail(String email);
}
