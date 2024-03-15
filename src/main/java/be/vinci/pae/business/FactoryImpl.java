package be.vinci.pae.business;

import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.company.CompanyImpl;
import be.vinci.pae.business.contact.ContactDTO;
import be.vinci.pae.business.contact.ContactImpl;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserImpl;

/**
 * Implementation of Factory used to create the implementation of the interfaces.
 */
public class FactoryImpl implements Factory {

  @Override
  public UserDTO getUser() {
    return new UserImpl();
  }

  @Override
  public ContactDTO getContact() {
    return new ContactImpl();
  }

  @Override
  public CompanyDTO getCompany() {
    return new CompanyImpl();
  }
}
