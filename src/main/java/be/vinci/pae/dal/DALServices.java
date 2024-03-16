package be.vinci.pae.dal;

public interface DALServices {
    /**
     * Get the connection to the database.
     *
     * @return the connection to the database
     */
    void start();
    /**
     * Get the connection to the database.
     *
     * @return the connection to the database
     */
    void commit();
    /**
     * Get the connection to the database.
     *
     * @return the connection to the database
     */
    void rollback();
}
