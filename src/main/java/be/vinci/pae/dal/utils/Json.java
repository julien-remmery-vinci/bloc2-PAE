package be.vinci.pae.dal.utils;

import be.vinci.pae.views.Views;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

/**
 * Class to filter the JSON view.
 *
 * @param <T> the type of the object
 */
public class Json<T> {

  private static final ObjectMapper jsonMapper = new ObjectMapper();

  private Class<T> type;

  /**
   * Constructor.
   *
   * @param type the type of the object
   */
  public Json(Class<T> type) {
    this.type = type;
  }

  /**
   * Filter the JSON view.
   *
   * @param list the list to filter
   * @return the list filtered
   */
  public <T> List<T> filterPublicJsonViewAsList(List<T> list) {
    try {
      JavaType type = jsonMapper.getTypeFactory().constructCollectionType(List.class, this.type);
      // serialize using JSON Views : public view (all fields not required in the
      // views are not serialized)
      String publicItemListAsString = jsonMapper.writerWithView(Views.Public.class)
          .writeValueAsString(list);
      // deserialize using JSON Views : Public View (all fields that are not serialized
      // are set to their default values in the POJOs)
      return jsonMapper.readerWithView(Views.Public.class).forType(type)
          .readValue(publicItemListAsString);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Filter the JSON view.
   *
   * @param item the item to filter
   * @return the item filtered
   */
  public <T> T filterPublicJsonView(T item) {
    try {
      // serialize using JSON Views : public view (all fields not required in the
      // views are not serialized)
      String publicItemAsString = jsonMapper.writerWithView(Views.Public.class)
          .writeValueAsString(item);
      // deserialize using JSON Views : Public View (all fields that are not serialized
      // are set to their default values in the POJO)
      return jsonMapper.readerWithView(Views.Public.class).forType(type)
          .readValue(publicItemAsString);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }
}
