package parsers;

import model.User;

/**
 * The xmlReader class parses and reads data in xml format from files.
 */
public interface XmlReader {
  /**
   * This method reads  the data from a given file and checks
   * if the password entered by the user matches to fetch all the user
   * related data from the file.
   *
   * @param fileName -  Name of the file from which the data is to be read for the user.
   * @param password -  password of the given user whose file is to be read.
   * @return - Loads all the data from the file to the User object and returns the user object.
   */
  User readData(String fileName, String password);
}
