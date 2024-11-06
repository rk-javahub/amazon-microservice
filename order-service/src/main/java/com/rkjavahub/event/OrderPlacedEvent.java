package com.rkjavahub.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;


@Getter
@Setter
public class OrderPlacedEvent extends ApplicationEvent {
    private String orderId;

    public OrderPlacedEvent(Object source) {
        super(source);
    }

    public OrderPlacedEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
