package be.vinci.pae.dal.internshipsupervisor;

import be.vinci.pae.business.internshipsupervisor.InternshipSupervisorDTO;
import java.util.List;

/**
 * Interface of InternshipSupervisorDAOImpl.
 */
public interface InternshipSupervisorDAO {

  /**
   * Get all the internship supervisors.
   *
   * @param idCompany the id of the company
   * @return all a list of internship supervisors
   */
  List<InternshipSupervisorDTO> getSupervisorsByCompanyID(int idCompany);

  /**
   * Add an internship supervisor.
   *
   * @param internshipSupervisor the internship supervisor to add
   * @return the internship supervisor added
   */
  InternshipSupervisorDTO addInternshipSupervisor(InternshipSupervisorDTO internshipSupervisor);

  /**
   * Get an internship supervisor by its id.
   */
  InternshipSupervisorDTO getInternshipSupervisorById(int id);
}
