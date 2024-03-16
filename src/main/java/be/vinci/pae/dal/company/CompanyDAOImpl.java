package be.vinci.pae.dal.company;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.company.CompanyImpl;
import be.vinci.pae.dal.DALServices;
import jakarta.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
  private Factory factory;
  @Inject
  private DALServices dalServices;

  public CompanyDTO getCompanyFromRs(ResultSet rs) {
    CompanyDTO company = factory.getCompany();
    // Get the fields of the UserImpl class
    for (Field f : CompanyImpl.class.getDeclaredFields()) {
      try {
        // Get the setter method of the field
        Method m = CompanyDTO.class.getDeclaredMethod(
            "set" + f.getName().substring(0, 1).toUpperCase()
                + f.getName().substring(1), f.getType());
        // Set the value of the field
        m.invoke(company, rs.getObject("company." + f.getName()));
      } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
               SQLException e) {
        throw new RuntimeException(e);
      }
    }
    return company;
  }

  @Override
  public List<CompanyDTO> getAll() {
    List<CompanyDTO> companies = new ArrayList<>();
    try (PreparedStatement ps = dalServices.getPS(
        "SELECT idCompany,tradeName,designation,address,phoneNumber,email,blacklisted,blacklistMotivation FROM pae.companies")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          companies.add(getCompanyFromRs(rs));
        }
        return companies;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}