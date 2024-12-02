package org.jewelryshop.productservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public <T> T getValues(String key, TypeReference<T> typeReference) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                // Deserialize từ JSON string thay vì đối tượng trực tiếp
                return mapper.readValue(value.toString(), typeReference);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setValue(String key, Object value) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            // Serialize đối tượng thành chuỗi JSON
            String jsonValue = mapper.writeValueAsString(value);

            // Lưu chuỗi JSON vào Redis
            redisTemplate.opsForValue().set(key, jsonValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setTimout(String key, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key,timeout, timeUnit);
    }

    public void setValueWithTimeout(String key, Object value, long ttl, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key,ttl, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public boolean checkExistsKey(String key){
        boolean check = false;
        try {
            check = redisTemplate.hasKey(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return check;
    }
    public void lPushAll(String key, List<String> value) {
        redisTemplate.opsForList().leftPushAll(key, value);
    }

    public Object rPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }
}

