package parsers;

import javax.xml.parsers.ParserConfigurationException;

import model.User;

/**
 * xmlWriter Class writes the dataModel object to the specified file in xml format.
 */
public interface XmlWriter {
  /**
   * This method writes the given User object to the specified file in xml format.
   *
   * @param file - The file in which the data has to be written.
   * @param user - The user object which has to be written in the file.
   * @return -  return a integer to show the satus of the write operation, negative
   *              value signifies failure and positive value show success.
   * @throws ParserConfigurationException - The exception is thrown when the configuration
   *                                      passed to the parser are invalid.
   */
  int writeData(String file, User user) throws ParserConfigurationException;

}
