package be.vinci.pae.dal.internship;

import be.vinci.pae.business.internship.InternshipDTO;
import java.util.List;

/**
 * Interface of InternshipDAOImpl.
 */
public interface InternshipDAO {

  /**
   * Get an internship by its id.
   *
   * @param id the id
   * @return the internship
   */
  List<InternshipDTO> getInternshipById(int id);

}
