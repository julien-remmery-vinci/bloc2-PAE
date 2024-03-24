package be.vinci.pae.business.internship;

import be.vinci.pae.business.user.UserDTO;
import java.util.List;

/**
 * Interface of InternshipUCCImpl.
 */
public interface InternshipUCC {

  /**
   * Get an internship by its id.
   *
   * @param user the user
   * @return the internship
   */
  List<InternshipDTO> getInternshipById(UserDTO user);

}
