package org.jewelryshop.paymentservice.services.impl;

import org.jewelryshop.paymentservice.contants.PaymentStatus;
import org.jewelryshop.paymentservice.dto.request.PayOSRequest;
import org.jewelryshop.paymentservice.dto.response.StatusResponse;
import org.jewelryshop.paymentservice.services.PayOSService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Formatter;
import java.util.Map;
import java.util.TreeMap;

@Service
public class PayOSServiceImpl implements PayOSService {
    private final WebClient webClient;

    @Value("${payos.client-id}")
    private String clientId;

    @Value("${payos.api-key}")
    private String apiKey;

    @Value("${payos.checksum-key}")
    private String checksumKey;
    public PayOSServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api-merchant.payos.vn").build();
    }
    @Override
    public String createPaymentRequest(PayOSRequest payOSRequest) {

        payOSRequest.setExpiredAt(Instant.now().plusSeconds(1800).getEpochSecond());

        payOSRequest.setSignature(generateSignature(payOSRequest));

        return webClient.post()
                .uri("/v2/payment-requests")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("x-client-id", clientId)
                .header("x-api-key", apiKey)
                .body(BodyInserters.fromValue(payOSRequest))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // Tạo liên kết thanh toán với PayOS
    private String generateSignature(PayOSRequest payOSRequest) {
        Map<String, String> signatureMap = new TreeMap<>();
        signatureMap.put("amount", String.valueOf(payOSRequest.getAmount()));
        signatureMap.put("cancelUrl", payOSRequest.getCancelUrl());
        signatureMap.put("description", payOSRequest.getDescription());
        signatureMap.put("orderCode", String.valueOf(payOSRequest.getOrderCode()));
        signatureMap.put("returnUrl", payOSRequest.getReturnUrl());

        String signatureData = signatureMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((s1, s2) -> s1 + "&" + s2)
                .orElse("");

        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(checksumKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            return toHexString(sha256_HMAC.doFinal(signatureData.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }



    private String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    // Xử lý webhook từ PayOS
    public StatusResponse handleWebhook(Webhook webhook) throws Exception {
        PayOS payOS = new PayOS(clientId,apiKey,checksumKey);
        WebhookData webhookData = payOS.verifyPaymentWebhookData(webhook);
        String newStatus;
        switch (webhookData.getPaymentLinkId()) {
            case "SUCCESS":
                newStatus = PaymentStatus.SUCCESS;
                break;
            case "CANCELLED":
                newStatus = PaymentStatus.CANCEL;
                break;
            case "FAILED":
                newStatus = PaymentStatus.ERROR;
                break;
            case "PENDING":
                newStatus = PaymentStatus.PENDING;
                break;
            case "REFUNDED":
                newStatus = PaymentStatus.REFUND;
                break;
            case "DECLINED":
                newStatus = PaymentStatus.DECLINED;
                break;
            default:
                throw new IllegalArgumentException("Unknown payment status");
        };

        return StatusResponse.builder().status(newStatus).build();
    }
}
