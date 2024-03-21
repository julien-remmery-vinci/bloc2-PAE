package be.vinci.pae.dal.contact;

import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.dal.DALBackServices;
import be.vinci.pae.dal.utils.DAOServices;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of ContactDAO.
 */
public class ContactDAOImpl implements ContactDAO {

  @Inject
  private DALBackServices dalServices;
  @Inject
  private DAOServices daoServices;

  @Override
  public ContactDTO getOneById(int id) {
    try (PreparedStatement ps = dalServices.getPS(
        "SELECT con.idContact as \"contact.idContact\",con.idCompany as \"contact.idCompany\","
            + "con.idStudent as \"contact.idStudent\",\n"
            + "       con.state as \"contact.state\",con.meetPlace as \"contact.meetPlace\","
            + "con.refusalReason as \"contact.refusalReason\",con.academicYear "
            + "as \"contact.academicYear\",\n"
            + "       u.iduser as \"user.idUser\",u.lastname as \"user.lastname\",u.firstname "
            + "as \"user.firstname\",u.email as \"user.email\",\n"
            + "       u.password as \"user.password\",u.phoneNumber as \"user.phoneNumber\","
            + "u.registerDate as \"user.registerDate\",u.role as \"user.role\",\n"
            + "       com.idcompany as \"company.idCompany\", com.tradeName "
            + "as \"company.tradeName\",com.designation as \"company.designation\",\n"
            + "       com.address as \"company.address\", com.phoneNumber "
            + "as \"company.phoneNumber\", com.email as \"company.email\",\n"
            + "       com.blacklisted as \"company.blacklisted\", com.blacklistMotivation "
            + "as \"company.blacklistMotivation\"\n"
            + "FROM pae.contacts con, pae.users u, pae.companies com WHERE con.idCompany = "
            + "com.idCompany AND con.idStudent = u.idUser AND idContact = ?;")) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          String prefix = "contact";
          return (ContactDTO) daoServices.getDataFromRs(rs, prefix);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  /**
   * Update a contact.
   *
   * @param contact the contact to refuse
   */
  @Override
  public void updateContact(ContactDTO contact) {
    try (PreparedStatement ps = dalServices.getPS(
        "UPDATE pae.contacts "
            + "SET idCompany = ?, idStudent = ?, state = ?, "
            + "meetPlace = ?, refusalReason = ?, academicYear = ?"
            + "WHERE idContact = ?;")) {
      ps.setInt(1, contact.getIdCompany());
      ps.setInt(2, contact.getIdStudent());
      ps.setString(3, contact.getState());
      ps.setString(4, contact.getMeetPlace());
      ps.setString(5, contact.getRefusalReason());
      ps.setString(6, contact.getAcademicYear());
      ps.setInt(7, contact.getIdContact());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
