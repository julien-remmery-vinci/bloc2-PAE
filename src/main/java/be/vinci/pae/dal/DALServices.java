package be.vinci.pae.dal;

/**
 * Interface of DALServicesImpl.
 */
public interface DALServices {

  /**
   * Start the transaction.
   */
  void start();

  /**
   * Commit the transaction.
   */
  void commit();

  /**
   * Rollback the transaction.
   */
  void rollback();
}
