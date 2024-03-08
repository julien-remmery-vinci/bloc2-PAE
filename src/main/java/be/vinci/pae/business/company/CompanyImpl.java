package be.vinci.pae.business.company;

/**
 * Implementation of Company which inherits of CompanyDTO.
 */
public class CompanyImpl implements Company {

  private int idCompany;
  private String tradeName;
  private String designation;
  private String address;
  private String phoneNumber;
  private String email;
  private boolean blacklisted;
  private String blacklistedMotivation;

  public int getIdCompany() {
    return idCompany;
  }

  public void setIdCompany(int idCompany) {
    this.idCompany = idCompany;
  }

  public String getTradeName() {
    return tradeName;
  }

  public void setTradeName(String tradeName) {
    this.tradeName = tradeName;
  }

  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isBlacklisted() {
    return blacklisted;
  }

  public void setBlacklisted(boolean blacklisted) {
    this.blacklisted = blacklisted;
  }

  public String getBlacklistedMotivation() {
    return blacklistedMotivation;
  }

  public void setBlacklistedMotivation(String blacklistedMotivation) {
    this.blacklistedMotivation = blacklistedMotivation;
  }
}
