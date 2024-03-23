package be.vinci.pae.business.internship;

import java.sql.Date;

public interface InternshipDTO {


  /**
   * @return the idInternship
   */
  int getIdInternship();

  /**
   * @param idInternship the idInternship to set
   */
  void setIdInternship(int idInternship);

  /**
   * @return the idStudent
   */
  int getIdStudent();

  /**
   * @param idStudent the idStudent to set
   */
  void setIdStudent(int idStudent);

  /**
   * @return the internshipProject
   */
  String getInternshipProject();

  /**
   * @param internshipProject the internshipProject to set
   */
  void setInternshipProject(String internshipProject);

  /**
   * @return the signatureDate
   */
  Date getSignatureDate();

  /**
   * @param signatureDate the signatureDate to set
   */
  void setSignatureDate(Date signatureDate);

  /**
   * @return the idContact
   */
  int getIdContact();

  /**
   * @param idContact the idContact to set
   */
  void setIdContact(int idContact);

  /**
   * @return the internshipSupervisor
   */
  int getInternshipSupervisor();

  /**
   * @param internshipSupervisor the internshipSupervisor to set
   */
  void setInternshipSupervisor(int internshipSupervisor);

  /**
   * @return the idCompany
   */
  int getIdCompany();

  /**
   * @param idCompany the idCompany to set
   */
  void setIdCompany(int idCompany);

  /**
   * @return the version
   */
  int getVersion();

  /**
   * @param version the version to set
   */
  void setVersion(int version);
}
