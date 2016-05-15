package publisher.com.github.julenpardo.jmiddleware.examples.publisher;

import com.github.julenpardo.jmiddleware.properties.InvalidPropertiesException;
import com.github.julenpardo.jmiddleware.publisher.Publisher;
import com.github.julenpardo.jmiddleware.socket.NotSubscribedToTopicException;

import java.io.IOException;

public class ExamplePublisher extends Publisher {

    public ExamplePublisher() throws IOException, InvalidPropertiesException {
        super("config.properties");
    }

    public static void main(String args[]) {
        try {
            ExamplePublisher publisher = new ExamplePublisher();
            System.out.println("Publisher initializated and running.");

            for (int i = 0; i < 10; i++) {
                String message = "Sending message for topic 100, iteration: " + i;
                publisher.publish(100, message.getBytes());
                Thread.sleep(1000);

                message = "Sending message for topic 200, iteration: " + i;
                publisher.publish(200, message.getBytes());
                Thread.sleep(1000);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidPropertiesException e) {
            e.printStackTrace();
        } catch (NotSubscribedToTopicException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
