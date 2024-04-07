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
   * @return all a list of internship supervisors
   */
  List<InternshipSupervisorDTO> getSupervisorsByCompanyID(int idCompany);

}
