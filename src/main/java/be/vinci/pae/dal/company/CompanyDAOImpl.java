package be.vinci.pae.dal.company;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.dal.DALBackServices;
import be.vinci.pae.dal.utils.DAOServices;
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

    @Inject


  @Override
  public List<CompanyDTO> getAll() {
    List<CompanyDTO> companies = new ArrayList<>();
    try (PreparedStatement ps = dalServices.getPS(
        "SELECT idCompany as \"company.idCompany\",tradeName as \"company.tradeName\","
            + "designation as \"company.designation\",address as \"company.address\","
            + "phoneNumber as \"company.phoneNumber\",email as \"company.email\","
            + "blacklisted as \"company.blacklisted\","
            + "blacklistMotivation as \"company.blacklistMotivation\""
            + " FROM pae.companies")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          String prefix = "company";
          companies.add((CompanyDTO) daoServices.getDataFromRs(rs, prefix));
        }
        return companies;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public CompanyDTO getCompanyById(int id) {
    try (PreparedStatement ps = dalServices.getPS(
        "SELECT idCompany as \"company.idCompany\",tradeName as \"company.tradeName\","
            + "designation as \"company.designation\",address as \"company.address\","
            + "phoneNumber as \"company.phoneNumber\",email as \"company.email\","
            + "blacklisted as \"company.blacklisted\","
            + "blacklistMotivation as \"company.blacklistMotivation\""
            + " FROM pae.companies WHERE idCompany = ?")) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          String prefix = "company";
          return (CompanyDTO) daoServices.getDataFromRs(rs, prefix);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}