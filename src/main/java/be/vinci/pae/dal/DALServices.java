package be.vinci.pae.dal;

import java.sql.PreparedStatement;

/**
 * Interface of DALServicesImpl.
 */
public interface DALServices {

  /**
   * Get a prepared statement from a request.
   *
   * @param request String containing the SQL request
   * @return the prepared statement
   */
  PreparedStatement getPrepareStatement(String request);
}
