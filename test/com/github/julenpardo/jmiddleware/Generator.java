package com.github.julenpardo.jmiddleware;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Data generator class for tests.
 */
public class Generator {

  /**
   * Removes the given file.
   *
   * @param filename The file to remove.
   */
  public void deletePropertiesFile(String filename) {
    File file = new File(filename);

    file.delete();
  }

  /**
   * Creates the properties file, and add the received properties to the file, if received.
   *
   * @param properties The properties to add to the file.
   */
  public void createPropertiesFile(String filename, HashMap properties) {
    Properties propertiesFile = new Properties();
    OutputStream output;
    Iterator iterator;
    Map.Entry pair;

    try {
      output = new FileOutputStream(filename);

      if (properties != null) {
        iterator = properties.entrySet().iterator();

        while (iterator.hasNext()) {
          pair = (Map.Entry)iterator.next();
          propertiesFile.setProperty((String)pair.getKey(), (String)pair.getValue());
        }

        propertiesFile.store(output, null);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
