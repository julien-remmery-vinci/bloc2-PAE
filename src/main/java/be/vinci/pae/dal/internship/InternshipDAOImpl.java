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
        "SELECT"
            + "    con.idContact AS \"contact.idContact\",\n"
            + "    con.idCompany AS \"contact.idCompany\",\n"
            + "    con.idStudent AS \"contact.idStudent\",\n"
            + "    con.state AS \"contact.state\",\n"
            + "    con.meetPlace AS \"contact.meetPlace\",\n"
            + "    con.refusalReason AS \"contact.refusalReason\",\n"
            + "    con.academicYear AS \"contact.academicYear\",\n"
            + "    con.version AS \"contact.version\",\n"
            + "    u.idUser AS \"user.idUser\",\n"
            + "    u.lastname AS \"user.lastname\",\n"
            + "    u.firstname AS \"user.firstname\",\n"
            + "    u.email AS \"user.email\",\n"
            + "    u.password AS \"user.password\",\n"
            + "    u.phoneNumber AS \"user.phoneNumber\",\n"
            + "    u.registerDate AS \"user.registerDate\",\n"
            + "    u.role AS \"user.role\",\n"
            + "    u.academicYear AS \"user.academicYear\",\n"
            + "    u.version AS \"user.version\",\n"
            + "    com.idCompany AS \"company.idCompany\",\n"
            + "    com.tradeName AS \"company.tradeName\",\n"
            + "    com.designation AS \"company.designation\",\n"
            + "    com.address AS \"company.address\",\n"
            + "    com.phoneNumber AS \"company.phoneNumber\",\n"
            + "    com.email AS \"company.email\",\n"
            + "    com.blacklisted AS \"company.blacklisted\",\n"
            + "    com.blacklistMotivation AS \"company.blacklistMotivation\",\n"
            + "    com.version AS \"company.version\",\n"
            + "    isn.idInternshipSupervisor AS \"internshipsupervisor.idInternshipSupervisor\",\n"
            + "    isn.lastname AS \"internshipsupervisor.lastname\",\n"
            + "    isn.firstname AS \"internshipsupervisor.firstname\",\n"
            + "    isn.phoneNumber AS \"internshipsupervisor.phoneNumber\",\n"
            + "    isn.email AS \"internshipsupervisor.email\",\n"
            + "    isn.idCompany AS \"internshipsupervisor.idCompany\",\n"
            + "    isn.version AS \"internshipsupervisor.version\",\n"
            + "    i.idInternship AS \"internship.idInternship\",\n"
            + "    i.idStudent AS \"internship.idStudent\",\n"
            + "    i.internshipProject AS \"internship.internshipProject\",\n"
            + "    i.signatureDate AS \"internship.signatureDate\",\n"
            + "    i.idContact AS \"internship.idContact\",\n"
            + "    i.idInternshipSupervisor AS \"internship.idInternshipSupervisor\",\n"
            + "    i.idCompany AS \"internship.idCompany\",\n"
            + "    i.version AS \"internship.version\"\n"
            + "FROM\n"
            + "    pae.contacts con\n"
            + "    INNER JOIN pae.users u ON con.idStudent = u.idUser\n"
            + "    INNER JOIN pae.companies com ON con.idCompany = com.idCompany\n"
            + "    INNER JOIN pae.internships i ON con.idContact = i.idContact AND"
            + " com.idCompany = i.idCompany\n"
            + "    INNER JOIN pae.internshipsupervisors isn ON i.idInternshipSupervisor ="
            + " isn.idInternshipSupervisor AND com.idCompany = isn.idCompany\n"
            + "WHERE\n"
            + "    u.idUser = i.idStudent AND\n"
            + "    con.idStudent = ?")) {
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
