package be.vinci.pae;

import be.vinci.pae.business.Factory;
import be.vinci.pae.business.FactoryImpl;
import be.vinci.pae.business.contact.ContactUCC;
import be.vinci.pae.business.contact.ContactUCCImpl;
import be.vinci.pae.business.user.UserUCC;
import be.vinci.pae.business.user.UserUCCImpl;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.dal.user.UserDAO;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mockito.Mockito;

/**
 * This class is used to bind the interfaces to their implementation.
 */
@Provider
public class ApplicationBinderTest extends AbstractBinder {

  @Override
  protected void configure() {
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    UserDAO userDAO = Mockito.mock(UserDAO.class);
    bind(userDAO).to(UserDAO.class);
    bind(FactoryImpl.class).to(Factory.class).in(Singleton.class);
    bind(ContactUCCImpl.class).to(ContactUCC.class).in(Singleton.class);
    bind(Mockito.mock(ContactDAO.class)).to(ContactDAO.class);
    bind(Mockito.mock(DALServices.class)).to(DALServices.class);
    bind(Mockito.mock(CompanyDAO.class)).to(CompanyDAO.class);
  }
}
