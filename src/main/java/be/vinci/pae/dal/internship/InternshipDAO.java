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

  /**
   * Get all internships.
   *
   * @param internship the internship to add
   * @return the internship added
   */
  InternshipDTO addInternship(InternshipDTO internship);

  /**
   * Get internship by student id.
   *
   * @param id the id of the student
   * @return the internship or null if no internship was found
   */
  InternshipDTO getInternshipByStudentId(int id);

  /**
   * Update the subject of an internship.
   *
   * @param internship the internship to update
   * @return the internship updated
   */
  InternshipDAO updateInternshipSubject(InternshipDTO internship);
}
