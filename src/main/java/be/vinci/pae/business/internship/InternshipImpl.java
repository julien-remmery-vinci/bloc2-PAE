package be.vinci.pae.business.internship;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

/**
 * Implementation of InternshipDTO.
 */
public class InternshipImpl implements Internship {

  private int idInternship;

  private int idStudent;

  private String internshipProject;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy hh:mm:ss")
  private Date signatureDate;

  private int idContact;

  private int internshipSupervisor;

  private int idCompany;

  private int version;


  @Override
  public int getIdInternship() {
    return idInternship;
  }

  @Override
  public void setIdInternship(int idInternship) {
    this.idInternship = idInternship;
  }

  @Override
  public int getIdStudent() {
    return idStudent;
  }

  @Override
  public void setIdStudent(int idStudent) {
    this.idStudent = idStudent;
  }

  @Override
  public String getInternshipProject() {
    return internshipProject;
  }

  @Override
  public void setInternshipProject(String internshipProject) {
    this.internshipProject = internshipProject;
  }

  @Override
  public Date getSignatureDate() {
    return signatureDate;
  }

  @Override
  public void setSignatureDate(Date signatureDate) {
    this.signatureDate = signatureDate;
  }

  @Override
  public int getIdContact() {
    return idContact;
  }

  @Override
  public void setIdContact(int idContact) {
    this.idContact = idContact;
  }

  @Override
  public int getInternshipSupervisor() {
    return internshipSupervisor;
  }

  @Override
  public void setInternshipSupervisor(int internshipSupervisor) {
    this.internshipSupervisor = internshipSupervisor;
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
  public int getVersion() {
    return version;
  }

  @Override
  public void setVersion(int version) {
    this.version = version;
  }
}
