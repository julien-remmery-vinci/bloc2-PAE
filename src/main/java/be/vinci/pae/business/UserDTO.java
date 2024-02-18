package be.vinci.pae.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.sql.Date;

// Interface of UserImpl
@JsonDeserialize(as = UserImpl.class)
public interface UserDTO {

  int getIdUser();

  void setIdUser(int idUser);

  String getFirstname();

  void setFirstname(String firstname);

  String getLastname();

  void setLastname(String lastname);

  String getEmail();

  void setEmail(String email);

  String getPassword();

  void setPassword(String password);

  String getPhoneNumber();

  void setPhoneNumber(String phoneNumber);

  Date getRegisterDate();

  void setRegisterDate(Date registerDate);

  Role getRole();

  void setRole(Role role);

  public enum Role {
    ETUDIANT("E"), PROFESSEUR("P"), ADMINISTRATIF("A");

    private String role;

    Role(String role) {
      this.role = role;
    }
  }
}
