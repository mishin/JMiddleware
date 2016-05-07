package com.github.julenpardo.jmiddleware.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Properties;

public class PropertiesReader {

  public static final String PROPERTIES_FILENAME = "config.properties";
  public static final String PROPERTIES_USER_MODE = "userMode";
  public static final String PROPERTIES_SOCKET_MODE = "socketMode";
  public static final String PROPERTIES_TOPICS = "topics";
  public static final String PROPERTIES_PORT = "port";
  public static final String PROPERTIES_MULTICAST_IP = "multicastIp";
  public static final String TOPICS_DELIMITER = ",";

  private Properties properties;
  private InputStream input;

  /**
   * PropertiesReader constructor.
   * @throws InvalidPropertiesException If an exception occurs reading the properties file.
   */
  public PropertiesReader() throws InvalidPropertiesException {
    try {
      this.properties = new Properties();
      this.input = new FileInputStream(this.PROPERTIES_FILENAME);
    } catch (FileNotFoundException exception) {
      throw new InvalidPropertiesException("Properties file missing!");
    }
  }

  /**
   * Reads the properties file, looking for the following properties:
   *  - User mode (subscriber/publisher).
   *  - Socket mode (default only for the moment).
   *  - Topic list.
   *  - Port.
   *  - Multicast IP.
   * Then, every property is checked to ensure that they are correctly defined. And, if yes,
   * a Configuration object that holds the read information is created and returned.
   *
   * @return Configuration The configuration object with the read properties.
   * @throws IOException If an exception occurs reading the properties file.
   * @throws InvalidPropertiesException If any of the properties is not correctly defined.
   */
  public Configuration readProperties() throws IOException, InvalidPropertiesException {
    Configuration configuration;
    byte userMode;
    byte socketMode;
    String topics;
    ArrayList<Integer> topicList;
    int port;
    InetAddress multicastIp;

    this.properties.load(this.input);

    userMode = Byte.parseByte(this.properties.getProperty(this.PROPERTIES_USER_MODE));
    socketMode = Byte.parseByte(this.properties.getProperty(this.PROPERTIES_SOCKET_MODE));
    topics = this.properties.getProperty(this.PROPERTIES_TOPICS);
    topicList = this.parseTopics(topics);
    port = Integer.parseInt(this.properties.getProperty(this.PROPERTIES_PORT));
    multicastIp = InetAddress.getByName(this.properties.getProperty(this.PROPERTIES_MULTICAST_IP));

    PropertiesChecker.checkUserMode(userMode);
    PropertiesChecker.checkSocketMode(socketMode);
    PropertiesChecker.checkPort(port);
    PropertiesChecker.checkMulticastIp(this.properties.getProperty(this.PROPERTIES_MULTICAST_IP));

    configuration = new Configuration(userMode, socketMode, topicList, port, multicastIp);

    return configuration;
  }

  /**
   * Creates a list of topics, from the string with the topics separated by commas read from
   * the properties file.
   * Before splitting the string, the whitespaces are removed, just in case.
   *
   * @param readTopics The topics string separated by comma read from properties file.
   * @return ArrayList\<Integer\> A list with all the topics.
   */
  private ArrayList<Integer> parseTopics(String readTopics) {
    ArrayList<Integer> topics = new ArrayList<Integer>();
    String[] splitedTopics;
    int parsedTopic;

    readTopics = readTopics.replaceAll("\\s", "");
    splitedTopics = readTopics.split(this.TOPICS_DELIMITER);

    for (String topic : splitedTopics) {
      parsedTopic = Integer.parseInt(topic);
      topics.add(parsedTopic);
    }

    return topics;
  }

}
