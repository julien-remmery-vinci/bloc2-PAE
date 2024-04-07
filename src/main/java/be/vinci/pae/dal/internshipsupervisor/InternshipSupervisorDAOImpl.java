package be.vinci.pae.dal.internshipsupervisor;

import be.vinci.pae.business.internshipsupervisor.InternshipSupervisorDTO;
import be.vinci.pae.dal.DALBackServices;
import be.vinci.pae.dal.utils.DAOServices;
import be.vinci.pae.presentation.exceptions.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of InternshipSupervisorDAOImpl.
 */
public class InternshipSupervisorDAOImpl implements InternshipSupervisorDAO {

  @Inject
  private DALBackServices dalServices;

  @Inject
  private DAOServices daoServices;

  @Override
  public List<InternshipSupervisorDTO> getAll() {
    List<InternshipSupervisorDTO> list = new ArrayList<>();
    try (PreparedStatement ps = dalServices.getPS(
        "SELECT * FROM pae.internshipSupervisors, pae.companies WHERE "
            + "internshipsupervisor_idCompany = company_idCompany;")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          String prefix = "internshipSupervisor";
          list.add((InternshipSupervisorDTO) daoServices.getDataFromRs(rs, prefix));
        }
        return list;
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }
}
