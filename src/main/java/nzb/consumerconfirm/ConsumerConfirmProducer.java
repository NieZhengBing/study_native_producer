package nzb.consumerconfirm;/**
 * Created by M on 2018/1/27.
 */

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
public class ConsumerConfirmProducer {
    private final static String EXCHANGE_NAME = "direct_cc_confirm_1";
    private final static String ROUTE_KEY = "error";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        for (int i = 0; i < 3; i++) {
            String message = "Hello world_" + (i + 1);

            channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, null, message.getBytes());
            System.out.println("Sent " + ROUTE_KEY + ":" + message);
        }

        channel.close();
        connection.close();
    }
}
