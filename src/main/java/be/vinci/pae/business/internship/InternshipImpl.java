package be.vinci.pae.business.internship;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.internshipsupervisor.InternshipSupervisorDTO;
import be.vinci.pae.business.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

/**
 * Implementation of InternshipDTO.
 */
public class InternshipImpl implements Internship {

  private int idInternship;

  private int idStudent;

  private UserDTO user;

  private String internshipProject;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy hh:mm:ss")
  private Date signatureDate;

  private int idContact;

  private ContactDTO contact;

  private int internshipSupervisor;

  private InternshipSupervisorDTO supervisor;

  private int idCompany;

  private CompanyDTO company;

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
  public UserDTO getUser() {
    return user;
  }

  @Override
  public void setUser(UserDTO user) {
    this.user = user;
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
  public ContactDTO getContact() {
    return contact;
  }

  @Override
  public void setContact(ContactDTO contact) {
    this.contact = contact;
  }

  @Override
  public int getIdInternshipSupervisor() {
    return internshipSupervisor;
  }

  @Override
  public void setIdInternshipSupervisor(int internshipSupervisor) {
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
  public CompanyDTO getCompany() {
    return company;
  }

  @Override
  public void setCompany(CompanyDTO company) {
    this.company = company;
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
