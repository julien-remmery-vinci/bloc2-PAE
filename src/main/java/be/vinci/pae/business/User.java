package be.vinci.pae.business;

import java.sql.Date;

public interface User {

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

  boolean checkPassword(String password);

  String hashPassword(String password);

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
