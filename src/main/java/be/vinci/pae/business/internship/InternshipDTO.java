package be.vinci.pae.business.internship;

import java.sql.Date;

/**
 * The Interface InternshipDTO.
 */
public interface InternshipDTO {


  /**
   * getter for idInternship.
   *
   * @return the idInternship
   */
  int getIdInternship();

  /**
   * setter for idInternship.
   *
   * @param idInternship the idInternship to set
   */
  void setIdInternship(int idInternship);

  /**
   * getter for idStudent.
   *
   * @return the idStudent
   */
  int getIdStudent();

  /**
   * setter for idStudent.
   *
   * @param idStudent the idStudent to set
   */
  void setIdStudent(int idStudent);

  /**
   * getter for internshipProject.
   *
   * @return the internshipProject
   */
  String getInternshipProject();

  /**
   * setter for internshipProject.
   *
   * @param internshipProject the internshipProject to set
   */
  void setInternshipProject(String internshipProject);

  /**
   * getter signature date.
   *
   * @return the signatureDate
   */
  Date getSignatureDate();

  /**
   * setter for signature date.
   *
   * @param signatureDate the signatureDate to set
   */
  void setSignatureDate(Date signatureDate);

  /**
   * getter for idContact.
   *
   * @return the idContact
   */
  int getIdContact();

  /**
   * setter for idContact.
   *
   * @param idContact the idContact to set
   */
  void setIdContact(int idContact);

  /**
   * getter for internshipSupervisor.
   *
   * @return the internshipSupervisor
   */
  int getInternshipSupervisor();

  /**
   * setter for internshipSupervisor.
   *
   * @param internshipSupervisor the internshipSupervisor to set
   */
  void setInternshipSupervisor(int internshipSupervisor);

  /**
   * getter for idCompany.
   *
   * @return the idCompany
   */
  int getIdCompany();

  /**
   * setter for idCompany.
   *
   * @param idCompany the idCompany to set
   */
  void setIdCompany(int idCompany);

  /**
   * getter for version.
   *
   * @return the version
   */
  int getVersion();

  /**
   * setter for version.
   *
   * @param version the version to set
   */
  void setVersion(int version);
}
