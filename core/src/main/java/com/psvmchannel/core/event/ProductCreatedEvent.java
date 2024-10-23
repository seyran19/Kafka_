package com.psvmchannel.core.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductCreatedEvent {

    private String productId;
    private String title;
    private BigDecimal price;
    private int quantity;
}
