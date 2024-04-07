package be.vinci.pae.dal.company;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.dal.DALBackServices;
import be.vinci.pae.dal.utils.DAOServices;
import be.vinci.pae.presentation.exceptions.ConflictException;
import be.vinci.pae.presentation.exceptions.FatalException;
import be.vinci.pae.presentation.exceptions.NotFoundException;
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

  @Override
  public CompanyDTO addCompany(CompanyDTO company) {
    try (PreparedStatement ps = dalServices.getPS(
        "INSERT INTO pae.companies (company_tradeName, company_designation,"
            + " company_address, company_city, company_phoneNumber, company_email, "
            + "company_blacklisted, company_blacklistmotivation, company_version) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, 1) RETURNING company_idCompany;")) {
      ps.setString(2, company.getTradeName());
      ps.setString(4, company.getAddress());
      ps.setBoolean(8, company.isBlacklisted());
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          company.setIdCompany(rs.getInt(1));

          return company;
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return null;
  }

  @Override
  public CompanyDTO updateCompany(CompanyDTO company) {
    try (PreparedStatement ps = dalServices.getPS(
        "UPDATE pae.companies SET company_tradename = ?, company_designation = ?,"
            + " company_address = ?, company_city = ?, company_phonenumber = ?, company_email = ?,"
            + " company_blacklisted = ?, company_blacklistmotivation = ?, "
            + "company_version = company_version + 1 WHERE company_idCompany = ?"
            + " AND company_version = ? RETURNING *")) {
      ps.setString(1, company.getTradeName());
      ps.setString(2, company.getDesignation());
      ps.setString(3, company.getAddress());
      ps.setString(4, company.getCity());
      ps.setString(5, company.getPhoneNumber());
      ps.setString(6, company.getEmail());
      ps.setBoolean(7, company.isBlacklisted());
      ps.setString(8, company.getBlacklistMotivation());
      ps.setInt(9, company.getIdCompany());
      ps.setInt(10, company.getVersion());
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) {
          if (getCompanyById(company.getIdCompany()) == null) {
            throw new NotFoundException("The company does not exist.");
          } else {
            throw new ConflictException(
                "Version mismatch, the company has been updated by someone else.");
          }
        }
        return company;
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }
}