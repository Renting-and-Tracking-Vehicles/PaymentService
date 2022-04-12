package com.example.paymentservice.controller;

import com.example.paymentservice.api.PaymentServiceApi;
import com.example.paymentservice.api.Redirection;
import com.example.paymentservice.model.Rent;
import com.example.paymentservice.service.PaypalService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaypalController implements PaymentServiceApi{

    private final PaypalService paypalService;
    Boolean isSuccess = false;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/pay")
    public Redirection payment(@RequestBody Rent rent) {
        System.out.println(rent.getIntent());
        try {
            Payment payment = paypalService.createPayment(rent.getPrice(), rent.getIntent(),
                    "http://localhost:9090/payment/" + CANCEL_URL,
                    "http://localhost:9090/payment/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return new Redirection(0, link.getHref());
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return new Redirection();
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                isSuccess = true;
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "homepage2";
    }

    @GetMapping(value = "/isSuccess")
    public Boolean isPaymentSuccess(){
        return isSuccess;
    }

}