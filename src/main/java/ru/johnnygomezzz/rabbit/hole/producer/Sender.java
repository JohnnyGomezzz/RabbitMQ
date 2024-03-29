package ru.johnnygomezzz.rabbit.hole.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class Sender {
    private static final String EXCHANGE_NAME = "Blog";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            while(true) {
                String text = writeText();
                String[] token = text.split("\\s");
                String topic = token[0];
                String message = token[1];
                for (int i = 2; i < token.length; i++) {
                    message = message+token[i]+" ";
                }
                channel.basicPublish(EXCHANGE_NAME, topic, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent message '" + message + "'");
            }
        }
    }
    public static String writeText (){
        Scanner sc = new Scanner(System.in);
        String readCode=sc.nextLine();
        return readCode;
    }
}
