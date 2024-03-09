package be.vinci.pae.business.contact;

/**
 * Implementation of Contact which inherits of ContactDTO.
 */
public class ContactImpl implements Contact {

  private int idContact;
  private int company;
  private int student;
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
  public int getCompany() {
    return company;
  }

  @Override
  public void setCompany(int company) {
    this.company = company;
  }

  @Override
  public int getStudent() {
    return student;
  }

  @Override
  public void setStudent(int student) {
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
