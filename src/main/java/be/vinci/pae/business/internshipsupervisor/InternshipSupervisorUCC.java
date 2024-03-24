package be.vinci.pae.business.internshipsupervisor;

/**
 * Interface of InternshipSupervisorUCCImpl.
 */
public interface InternshipSupervisorUCC {

  /**
   * Get an internship supervisor by its id.
   *
   * @param id the id
   * @return the internship supervisor
   */
  InternshipSupervisorDTO getInternshipSupervisorById(int id);
}
