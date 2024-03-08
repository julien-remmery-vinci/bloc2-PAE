package be.vinci.pae.dal.contact;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactImpl;
import be.vinci.pae.dal.DALServices;
import jakarta.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of ContactDAO.
 */
public class ContactDAOImpl implements ContactDAO {

  @Inject
  private Factory factory;
  @Inject
  private DALServices dalServices;

  /**
   * Refuse a contact.
   *
   * @param id the id of the contact
   * @return the contact
   */
  @Override
  public ContactDTO refuseContact(int id, String refusalReason) {
    try (PreparedStatement ps = dalServices.getPS(
        "UPDATE pae.contacts SET status = 'refused' AND refusalReason = ? WHERE id = ? RETURNING *;")) {
      ps.setString(1, refusalReason);
      ps.setInt(2, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return getContactFromRs(rs);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  /**
   * Get a contact from a ResultSet.
   *
   * @param rs the ResultSet
   * @return the contact
   * @throws SQLException if an error occurs
   */
  private ContactDTO getContactFromRs(ResultSet rs) throws SQLException {
    ContactDTO contact = factory.getContact();
    // Get the fields of the UserImpl class
    for (Field f : ContactImpl.class.getDeclaredFields()) {
      try {
        // Get the setter method of the field
        Method m = ContactDTO.class.getDeclaredMethod(
            "set" + f.getName().substring(0, 1).toUpperCase()
                + f.getName().substring(1), f.getType());
        // Set the value of the field
        m.invoke(contact, rs.getObject(f.getName()));
      } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    return contact;
  }
}
