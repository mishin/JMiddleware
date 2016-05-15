package subscriber.com.github.julenpardo.jmiddleware.examples.subscriber;


import com.github.julenpardo.jmiddleware.properties.InvalidPropertiesException;
import com.github.julenpardo.jmiddleware.subscriber.Subscriber;

import java.io.IOException;

public class ExampleSubscriber extends Subscriber {
    public ExampleSubscriber() throws IOException, InvalidPropertiesException {

    }

    public static void main(String[] args) {
        try {
            ExampleSubscriber subscriber = new ExampleSubscriber();
            System.out.println("Subscriber initializated and running.");

            while (true) {
                byte[] topic100 = subscriber.getLastSample(100);
                System.out.println("Received message of topic 100: " + new String(topic100));

                byte[] topic200 = subscriber.getLastSample(200);
                System.out.println("Received message of topic 200: " + new String(topic200));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidPropertiesException e) {
            e.printStackTrace();
        }
    }
}
