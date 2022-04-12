package com.example.paymentservice.api;

import com.example.paymentservice.model.Rent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("payment-service")
public interface PaymentServiceApi {
    @PostMapping("/payment/pay")
    public Redirection payment(@RequestBody Rent rent);
}
