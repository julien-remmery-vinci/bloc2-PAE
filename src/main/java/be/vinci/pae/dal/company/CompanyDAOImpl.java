package be.vinci.pae.dal.company;

import be.vinci.pae.business.company.CompanyDTO;
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
 * Implementation of CompanyDAO.
 */
public class CompanyDAOImpl implements CompanyDAO {

  @Inject
  private DALBackServices dalServices;
  @Inject
  private DAOServices daoServices;

  @Override
  public List<CompanyDTO> getAll() {
    List<CompanyDTO> companies = new ArrayList<>();
    try (PreparedStatement ps = dalServices.getPS(
        "SELECT * FROM pae.companies")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          String prefix = "company";
          companies.add((CompanyDTO) daoServices.getDataFromRs(rs, prefix));
        }
        return companies;
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }

  @Override
  public CompanyDTO getCompanyById(int id) {
    try (PreparedStatement ps = dalServices.getPS(
        "SELECT * FROM pae.companies WHERE company_idCompany = ?")) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          String prefix = "company";
          return (CompanyDTO) daoServices.getDataFromRs(rs, prefix);
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return null;
  }
}