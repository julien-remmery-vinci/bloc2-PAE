package be.vinci.pae.business.contact;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.user.UserDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Interface of ContactImpl.
 */
@JsonDeserialize(as = ContactImpl.class)
public interface ContactDTO {

  /**
   * Get the id of the contact.
   *
   * @return the id of the contact
   */
  int getIdContact();

  /**
   * Set the id of the contact.
   *
   * @param idContact the id of the contact
   */
  void setIdContact(int idContact);

  /**
   * Get the company of the contact.
   *
   * @return the company of the contact
   */
  int getIdCompany();

  /**
   * Set the company of the contact.
   *
   * @param idCompany the company of the contact
   */
  void setIdCompany(int idCompany);

  /**
   * Get the company of the contact.
   *
   * @return the company of the contact
   */
  CompanyDTO getCompany();

  /**
   * Set the company of the contact.
   *
   * @param company the company of the contact
   */
  void setCompany(CompanyDTO company);

  /**
   * Get the student of the contact.
   *
   * @return the student of the contact
   */
  int getIdStudent();

  /**
   * Set the student of the contact.
   *
   * @param idStudent the student of the contact
   */
  void setIdStudent(int idStudent);

  /**
   * Get the student of the contact.
   *
   * @return the student of the contact
   */
  UserDTO getUser();

  /**
   * Set the student of the contact.
   *
   * @param user the student of the contact
   */
  void setUser(UserDTO user);

  /**
   * Get the state of the contact.
   *
   * @return the state of the contact
   */
  State getState();

  /**
   * Set the state of the contact.
   *
   * @param state the state of the contact
   */
  void setState(State state);

  /**
   * Get the meet place of the contact.
   *
   * @return the meet place of the contact
   */
  String getMeetPlace();

  /**
   * Set the meet place of the contact.
   *
   * @param meetPlace the meet place of the contact
   */
  void setMeetPlace(String meetPlace);

  /**
   * Get the refusal reason of the contact.
   *
   * @return the refusal reason of the contact
   */
  String getRefusalReason();

  /**
   * Set the refusal reason of the contact.
   *
   * @param refusalReason the refusal reason of the contact
   */
  void setRefusalReason(String refusalReason);

  /**
   * Get the academic year of the contact.
   *
   * @return the academic year of the contact
   */
  String getAcademicYear();

  /**
   * Set the academic year of the contact.
   *
   * @param academicYear the academic year of the contact
   */
  void setAcademicYear(String academicYear);

  enum State {
    STARTED("initié"),
    ADMITTED("pris"),
    TURNED_DOWN("refusé"),
    ACCEPTED("accepté"),
    ON_HOLD("suspendu"),
    UNSUPERVISED("non suivi");

    private String state;

    State(String state) {
      this.state = state;
    }
  }
}
