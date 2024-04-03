package be.vinci.pae.business.contact;

import be.vinci.pae.business.academicyear.AcademicYear;
import be.vinci.pae.business.company.CompanyDTO;
import be.vinci.pae.business.contact.ContactDTO.State;
import be.vinci.pae.business.user.UserDTO;
import be.vinci.pae.business.user.UserDTO.Role;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.company.CompanyDAO;
import be.vinci.pae.dal.contact.ContactDAO;
import be.vinci.pae.presentation.exceptions.NotFoundException;
import be.vinci.pae.presentation.exceptions.PreconditionFailedException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of ContactUCC.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO contactDAO;
  @Inject
  private DALServices dalServices;
  @Inject
  private CompanyDAO companyDAO;
  @Inject
  private AcademicYear academicYear;

  /**
   * Get the contact.
   *
   * @return the contact
   */
  @Override
  public List<ContactDTO> getContacts(UserDTO user) {
    List<ContactDTO> list;
    try {
      dalServices.open();
      if (user == null) {
        throw new NotFoundException("User not found");
      }
      if (user.getRole().equals(Role.STUDENT)) {
        list = contactDAO.getContactsByStudentId(user.getIdUser());
      } else {
        list = contactDAO.getAllContacts();
      }
    } finally {
      dalServices.close();
    }
    return list;
  }


  /**
   * Refuse a contact.
   *
   * @param idContact     the id of the contact
   * @param refusalReason the refusal reason
   * @param idUser        the id of the user
   * @return the contact
   */
  @Override
  public ContactDTO refuseContact(int idContact, String refusalReason, int idUser) {
    ContactDTO contact;
    try {
      dalServices.open();
      contact = contactDAO.getOneById(idContact);

      if (contact == null) {
        return null;
      }
      if (contact.getIdStudent() != idUser) {
        throw new NotFoundException("You don't have a contact with this id");
      }
      if (!((Contact) contact).updateState(State.TURNED_DOWN)) {
        throw new PreconditionFailedException(
            "Le contact doit être dans l'état 'pris' pour être refusé");
      }
      contact.setRefusalReason(refusalReason);
      contactDAO.updateContact(contact);
    } finally {
      dalServices.close();
    }
    return contact;
  }

  @Override
  public ContactDTO addContact(ContactDTO contact) {
    try {
      dalServices.open();
      CompanyDTO companyDTO = companyDAO.getCompanyById(contact.getIdCompany());
      if (companyDTO == null) {
        throw new NotFoundException("L'entreprise n'existe pas.");
      }
      if (contactDAO.getContactAccepted(contact.getIdStudent()) != null) {
        throw new PreconditionFailedException("Vous avez déjà un contact accepté");
      }
      contact.setAcademicYear(academicYear.getAcademicYear());
      if (contactDAO.getCompanyContact(contact.getIdStudent(), contact.getIdCompany(),
          contact.getAcademicYear()) != null) {
        throw new PreconditionFailedException(
            "Vous avez déjà un contact avec cette entreprise pour cette année académique");
      }
      contact.setState(State.STARTED);
      contact = contactDAO.addContact(contact);
      contact.setCompany(companyDTO);
    } finally {
      dalServices.close();
    }
    return contact;
  }

  @Override
  public ContactDTO meetContact(int id, String meetPlace, int idUser) {
    Contact contact;
    try {
      dalServices.open();
      contact = (Contact) contactDAO.getOneById(id);
      if (contact == null) {
        return null;
      }
      if (contact.getIdStudent() != idUser) {
        throw new NotFoundException("You don't have a contact with this id");
      }
      if (!contact.updateState(State.ADMITTED)) {
        throw new PreconditionFailedException(
            "The contact must be in the state 'started' to be admitted");
      }
      contact.setState(State.ADMITTED);
      contact.setMeetPlace(meetPlace);
      contactDAO.updateContact(contact);
    } finally {
      dalServices.close();
    }
    return contact;
  }

  @Override
  public ContactDTO unfollowContact(int id, int idUser) {
    Contact contact;
    try {
      dalServices.open();
      contact = (Contact) contactDAO.getOneById(id);
      if (contact == null) {
        return null;
      }
      if (contact.getIdStudent() != idUser) {
        throw new NotFoundException("You don't have a contact with this id");
      }
      if (!contact.updateState(State.UNSUPERVISED)) {
        throw new PreconditionFailedException(
            "The contact must be either in the state 'started' or 'admitted' to be unsupervised");
      }
      contact.setState(State.UNSUPERVISED);
      contactDAO.updateContact(contact);
    } finally {
      dalServices.close();
    }
    return contact;
  }

  @Override
  public List<ContactDTO> getContactsByCompany(int idCompany) {
    try {
      dalServices.open();
      CompanyDTO company = companyDAO.getCompanyById(idCompany);
      if (company == null) {
        throw new NotFoundException("Company not found");
      }
      return contactDAO.getContactsByCompany(idCompany);
    } finally {
      dalServices.close();
    }
  }

  @Override
  public List<ContactDTO> blacklistContacts(int idCompany) {
    try {
      dalServices.start();
      List<ContactDTO> contacts = getContactsByCompany(idCompany);
      for (ContactDTO c : contacts) {
        if (((Contact) c).updateState(State.BLACKLISTED)) {
          contactDAO.updateContact(c);
        }
      }
      dalServices.commit();
      return contacts;
    } catch (Exception e) {
      dalServices.rollback();
      throw e;
    } finally {
      dalServices.close();
    }
  }
}
