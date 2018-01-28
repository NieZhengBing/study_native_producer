package nzb.normal;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author M
 * @create 2018/1/27
 */
public class FanoutProducer {
    private final static String EXCHANGE_NAME = "fnaout_logs_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String[] serverties = {"error", "info", "warning"};

        for (int i = 0; i < 3; i++) {
            String server = serverties[i];
            String message = "Hello world_" + (i + 1);

            channel.basicPublish(EXCHANGE_NAME, server, null, message.getBytes());
            System.out.println("Sent " + server + ":" + message);
        }

        channel.close();
        connection.close();
    }
}
