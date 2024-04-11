package be.vinci.pae.dal.internship;

import be.vinci.pae.business.internship.InternshipDTO;
import be.vinci.pae.dal.DALBackServices;
import be.vinci.pae.dal.utils.DAOServices;
import be.vinci.pae.exceptions.FatalException;
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
        "SELECT * FROM pae.contacts, pae.users, pae.companies, pae.internships, "
            + "pae.internshipSupervisors WHERE\n"
            + "contact_idCompany = company_idCompany AND\n"
            + "contact_idStudent = user_idUser AND\n"
            + "internshipSupervisor_idCompany = company_idCompany AND\n"
            + "internship_idStudent = user_idUser AND\n"
            + "internship_idContact = contact_idContact AND\n"
            + "internship_idInternshipSupervisor = internshipSupervisor_idInternshipSupervisor\n"
            + " AND internship_idCompany = company_idCompany AND\n"
            + "contact_idStudent = ?")) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          String prefix = "internship";
          internships.add((InternshipDTO) daoServices.getDataFromRs(rs, prefix));
        }
        return internships;
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }
}
