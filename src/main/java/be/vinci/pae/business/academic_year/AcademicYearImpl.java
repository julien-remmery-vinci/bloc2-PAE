package be.vinci.pae.business.academic_year;

import java.time.LocalDate;

public class AcademicYearImpl implements AcademicYear {
  
  @Override
  public String getAcademicYear() {
    LocalDate date = LocalDate.now();
    if (date.getMonthValue() >= 9) {
      return date.getYear() + "-" + (date.getYear() + 1);
    }
    return (date.getYear() - 1) + "-" + date.getYear();
  }

}
