package be.vinci.pae.dal.utils;

import be.vinci.pae.business.Factory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class with utility methods.
 */
public class Utils {

  /**
   * Get data from a ResultSet.
   *
   * @param rs      the ResultSet to get the data from
   * @param prefix  the prefix of the class in lowercase
   * @param factory the factory to get the implementation of the classes
   * @return the object with the data from the ResultSet
   */
  public static Object getDataFromRs(ResultSet rs, String prefix, Factory factory) {
    try {
      // Build the class prefix (first letter in uppercase and the rest of the string)
      String classPrefix = prefix.substring(0, 1).toUpperCase() + prefix.substring(1);
      // Get the implementation class
      Class<?> cImpl = Class.forName(
          "be.vinci.pae.business." + prefix + "." + classPrefix + "Impl");
      // Get the method to get the implementation of the class
      Method getImpl = factory.getClass().getDeclaredMethod("get" + classPrefix);
      // Get the object from the factory using the method
      Object object = getImpl.invoke(factory);
      // Set the fields of the object
      for (Field f : cImpl.getDeclaredFields()) {
        // Get the method to set the field of the object
        Method m = cImpl.getDeclaredMethod(
            "set" + f.getName().substring(0, 1).toUpperCase()
                + f.getName().substring(1), f.getType());
        // If field is an enum, use Enum.valueOf to get the enum value
        if (f.getType().isEnum()) {
          m.invoke(object, Enum.valueOf((Class<Enum>) Class.forName(f.getType().getName()),
              rs.getString(prefix + "." + f.getName())));
        }
        // If field is not an interface, set the field of the object
        else if (!f.getType().isInterface()) {
          m.invoke(object, rs.getObject(prefix + "." + f.getName()));
        }
        // If field is an interface, get the data from the ResultSet and set the field of the object
        else {
          m.invoke(object, getDataFromRs(rs, f.getName(), factory));
        }
      }
      return object;
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
             IllegalAccessException |
             SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
