package com.my.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.my.stream.DemoChannel.INPUT;

/**
 * Created by zhul-a on 2017/7/21.
 */
@Service
@EnableBinding(DemoChannel.class)
public class DemoService {
    @Autowired
    private DemoChannel channel;

    @StreamListener(INPUT)
    public void print(String message) {
        System.out.println(message);
    }

    @Scheduled(fixedDelay = 1000 * 3)
    public void send() {
        boolean result = channel.output().send(MessageBuilder.withPayload("i am stream demo")
                .setHeader(MessageHeaders.CONTENT_TYPE, "application/json")
                .build());
        if (result)
            System.out.println("send message!!!");
    }

}
