package com.example.productmicroservice.service.Impl;

import com.example.productmicroservice.dto.CreateProductDto;
import com.example.productmicroservice.service.ProductService;
import com.psvmchannel.core.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    /// Продюсер, который будет отправлять наше сообщение
    private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductDto createProductDto) {
        //TODO save to database
        String productId = UUID.randomUUID().toString();

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
                productId,
                createProductDto.getTitle(),
                createProductDto.getPrice(),
                createProductDto.getQuantity()
        );

        ProducerRecord<String, ProductCreatedEvent> record = new ProducerRecord<>(
              "product-created-events-topic",
              productId,
              productCreatedEvent
        );

        record.headers().add("messageId", UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
                kafkaTemplate.send(record);

        /// Асинхронный вариант, при синхронном вызвать метод get()
        /// kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent).get();
//        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
//                kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent);

        future.whenComplete((result, error) -> {
            if (error != null) {
                log.error("failed to send message {}", error.getMessage());
            }else{
                log.info("successfully sent message {}", result.getRecordMetadata());
            }
        });

        log.info("return {}", productId);



        return productId;
    }
}
