package be.vinci.pae.utils;

import be.vinci.pae.business.UserUCC;
import be.vinci.pae.business.UserUCCImpl;
import be.vinci.pae.services.UserDS;
import be.vinci.pae.services.UserDSImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

@Provider
public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(UserDSImpl.class).to(UserDS.class).in(Singleton.class);
  }
}
