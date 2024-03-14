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

  @Override
  public List<CompanyDTO> getAll() {
    List<CompanyDTO> companies = new ArrayList<>();
    try (PreparedStatement ps = dalServices.getPS(
        "SELECT * FROM pae.companies")) {
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

  /**
   * Get a company from a ResultSet.
   *
   * @param rs the ResultSet
   * @return the company
   * @throws SQLException if a SQL exception occurs
   */
  private CompanyDTO getCompanyFromRs(ResultSet rs) throws SQLException {
    CompanyDTO companyDTO = factory.getCompany();
    // Get the fields of the UserImpl class
    for (Field f : CompanyImpl.class.getDeclaredFields()) {
      try {
        // Get the setter method of the field
        Method m = CompanyDTO.class.getDeclaredMethod(
            "set" + f.getName().substring(0, 1).toUpperCase()
                + f.getName().substring(1), f.getType());
        // Set the value of the field
        m.invoke(companyDTO, rs.getObject(f.getName()));
      } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    return companyDTO;
  }
}