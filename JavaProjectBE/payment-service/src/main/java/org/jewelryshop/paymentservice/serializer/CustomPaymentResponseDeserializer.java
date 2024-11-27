package org.jewelryshop.paymentservice.serializer;

import org.apache.kafka.common.serialization.Deserializer;
import org.jewelryshop.paymentservice.dto.response.OrderResponse;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

public class CustomPaymentResponseDeserializer implements Deserializer<OrderResponse> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Nếu cần cấu hình, thực hiện tại đây
    }

    @Override
    public OrderResponse deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            // Chuyển byte array thành chuỗi JSON
            String json = new String(data, StandardCharsets.UTF_8);

            String[] fields = json.replace("{", "").replace("}", "").split(",");
            OrderResponse orderResponse = new OrderResponse();

            for (String field : fields) {
                String[] keyValue = field.split(":");
                String key = keyValue[0].replace("\"", "").trim();
                String value = keyValue[1].replace("\"", "").trim();

                switch (key) {
                    case "orderId":
                        orderResponse.setOrderId(value);
                        break;
                    case "userId":
                        orderResponse.setOrderId(value);
                        break;
                    case "status":
                        orderResponse.setStatus(value);
                        break;
                    case "createdDate":
                        orderResponse.setOrderDate(LocalDateTime.parse(value));
                        break;
                    case "totalAmount":
                        orderResponse.setTotalAmount(Integer.parseInt(value));
                        break;
                }
            }

            return orderResponse;
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing OrderResponse", e);
        }
    }

    @Override
    public void close() {
        // Dọn dẹp resource nếu cần
    }
}

