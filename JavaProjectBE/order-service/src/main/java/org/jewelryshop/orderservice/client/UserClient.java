package org.jewelryshop.orderservice.client;

import org.jewelryshop.orderservice.dto.response.ApiResponse;
import org.jewelryshop.orderservice.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "USER-SERVICE",url ="http://localhost:9000/user")
public interface UserClient {
    @RequestMapping(method = RequestMethod.GET,value = "/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId);
}
