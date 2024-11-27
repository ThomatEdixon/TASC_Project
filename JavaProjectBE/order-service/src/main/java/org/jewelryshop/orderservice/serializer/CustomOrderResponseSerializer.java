package org.jewelryshop.orderservice.serializer;

import org.apache.kafka.common.serialization.Serializer;
import org.jewelryshop.orderservice.dto.response.OrderResponse;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomOrderResponseSerializer implements Serializer<OrderResponse> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Nếu cần cấu hình gì thì làm ở đây, không bắt buộc
    }

    @Override
    public byte[] serialize(String topic, OrderResponse data) {
        if (data == null) return null;
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try(DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream)){

                dataOutputStream.writeUTF(data.getOrderId());
                dataOutputStream.writeUTF(data.getUserId());
                dataOutputStream.writeUTF(data.getStatus());
                dataOutputStream.writeUTF(String.valueOf(data.getOrderDate()));
                dataOutputStream.writeDouble(data.getTotalAmount());
                dataOutputStream.write(String.valueOf(data.getOrderDetails()).getBytes());

                return byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error serializing OrderResponse", e);
        }
    }

    @Override
    public void close() {
        // Nếu cần đóng resource, xử lý tại đây
    }
}

