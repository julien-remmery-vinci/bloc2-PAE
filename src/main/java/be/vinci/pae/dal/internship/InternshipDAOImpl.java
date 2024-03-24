package be.vinci.pae.dal.internship;

import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.dal.DALBackServices;
import be.vinci.pae.dal.utils.DAOServices;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of InternshipDAO.
 */
public class InternshipDAOImpl implements InternshipDAO {

  @Inject
  private DALBackServices dalServices;
  @Inject
  private DAOServices daoServices;

  /**
   * Get an internship by its id.
   *
   * @param id the id
   * @return the internship
   */
  @Override
  public List<InternshipDTO> getInternshipById(int id) {
    List<InternshipDTO> internships = new ArrayList<>();
    try (PreparedStatement ps = dalServices.getPS(
        "SELECT con.idContact as \"contact.idContact\",con.idCompany as \"contact.idCompany\","
            + "con.idStudent as \"contact.idStudent\",\n"
            + "       con.state as \"contact.state\",con.meetPlace as \"contact.meetPlace\","
            + "con.refusalReason as \"contact.refusalReason\",con.academicYear "
            + "as \"contact.academicYear\",\n"
            + "con.version as \"contact.version\",\n"
            + "       u.iduser as \"user.idUser\",u.lastname as \"user.lastname\",u.firstname "
            + "as \"user.firstname\",u.email as \"user.email\",\n"
            + "       u.password as \"user.password\",u.phoneNumber as \"user.phoneNumber\","
            + "u.registerDate as \"user.registerDate\",u.role as \"user.role\",\n"
            + "u.version as \"user.version\",\n"
            + "       com.idcompany as \"company.idCompany\", com.tradeName "
            + "as \"company.tradeName\",com.designation as \"company.designation\",\n"
            + "       com.address as \"company.address\", com.phoneNumber "
            + "as \"company.phoneNumber\", com.email as \"company.email\",\n"
            + "       com.blacklisted as \"company.blacklisted\", com.blacklistMotivation "
            + "as \"company.blacklistMotivation\",\n"
            + "com.version as \"company.version\"\n"
            + ", is.idInternshipSupervisor as \"internshipsupervisor.idInternshipSupervisor\",is.lastname as \"internshipsupervisor.lastname\",\n"
            + "is.firstname as \"internshipsupervisor.firstname\",\n"
            + "       is.phoneNumber as \"internshipsupervisor.phoneNumber\",is.email as \"internshipsupervisor.email\",\n"
            + "is.idCompany as \"internshipsupervisor.idCompany\",\n"
            + "is.version as \"internshipsupervisor.version\",\n"
            + "i.idInternship as \"internship.idInternship\",i.idStudent as \"internship.idStudent\",\n"
            + "i.internshipProject as \"internship.internshipProject\",\n"
            + "       i.signatureDate as \"internship.signatureDate\",i.idContact as \"internship.idContact\",\n"
            + "i.internshipSupervisor as \"internship.internshipSupervisor\",\n"
            + "i.idCompany as \"internship.idCompany\",\n"
            + "is.version as \"internshipsupervisor.version\"\n"
            + "FROM pae.contacts con, pae.users u, pae.companies com, pae.internships i"
            + ",pae.internshipsupervisors is WHERE con.idCompany = "
            + "com.idCompany AND con.idStudent = u.idUser AND "
            + "is.idInternshipSupervisor = i.internshipSupervisor AND"
            + " com.idCompany = is.idCompany"
            + " AND com.idCompany = i.idCompany AND i.idInternship = ?"
            + "AND u.idUser = i.idStudent AND con.idContact = i.idContact"
            + " AND idStudent = ?;")) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          String prefix = "internship";
          internships.add((InternshipDTO) daoServices.getDataFromRs(rs, prefix));
        }
        return internships;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
