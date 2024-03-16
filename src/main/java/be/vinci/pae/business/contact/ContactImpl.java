package be.vinci.pae.business.contact;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.user.UserDTO;

/**
 * Implementation of Contact which inherits of ContactDTO.
 */
public class ContactImpl implements Contact {

  private int idContact;
  private int idCompany;
  private CompanyDTO company;
  private int idStudent;
  private UserDTO student;
  private String state;
  private String meetPlace;
  private String refusalReason;
  private String academicYear;

  @Override
  public int getIdContact() {
    return idContact;
  }

  @Override
  public void setIdContact(int idContact) {
    this.idContact = idContact;
  }

  @Override
  public int getIdCompany() {
    return idCompany;
  }

  @Override
  public void setIdCompany(int idCompany) {
    this.idCompany = idCompany;
  }

  @Override
  public CompanyDTO getCompany() {
    return company;
  }

  @Override
  public void setCompany(CompanyDTO company) {
    this.company = company;
  }

  @Override
  public int getIdStudent() {
    return idStudent;
  }

  @Override
  public void setIdStudent(int idStudent) {
    this.idStudent = idStudent;
  }

  public UserDTO getStudent() {
    return student;
  }

  public void setStudent(UserDTO student) {
    this.student = student;
  }

  @Override
  public String getState() {
    return state;
  }

  @Override
  public void setState(String state) {
    this.state = state;
  }

  @Override
  public String getMeetPlace() {
    return meetPlace;
  }

  @Override
  public void setMeetPlace(String meetPlace) {
    this.meetPlace = meetPlace;
  }

  @Override
  public String getRefusalReason() {
    return refusalReason;
  }

  @Override
  public void setRefusalReason(String refusalReason) {
    this.refusalReason = refusalReason;
  }

  @Override
  public String getAcademicYear() {
    return academicYear;
  }

  @Override
  public void setAcademicYear(String academicYear) {
    this.academicYear = academicYear;
  }
}
