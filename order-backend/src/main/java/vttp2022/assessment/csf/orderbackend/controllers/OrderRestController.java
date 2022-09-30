package vttp2022.assessment.csf.orderbackend.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.models.Response;
import vttp2022.assessment.csf.orderbackend.services.OrderService;

@RestController
@RequestMapping("/api")
public class OrderRestController {

    @Autowired
    OrderService oSvc;

    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody String payload) {
        // System.out.println("parload: " + payload);
        Response resp = new Response();
        try {
            Order o = Order.create(payload);
            oSvc.createOrder(o);
        } catch (Exception e) {
            resp.setCode(400);
            resp.setMessage("Unable to create order.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resp.toJson().toString());
        }
        resp.setCode(201);
        resp.setMessage("Order created successfully");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resp.toJson().toString());
    }

    @GetMapping("/order/{email}/all")
    public ResponseEntity<String> getOrdersByEmail(@PathVariable String email) {
        Response resp = new Response();
        List<OrderSummary> orderSum = new LinkedList<>();
        JsonArrayBuilder ab = Json.createArrayBuilder();
        try {
            orderSum = oSvc.getOrdersByEmail(email);
        } catch (Exception e) {
            resp.setCode(400);
            resp.setMessage("Unable to get orders.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resp.toJson().toString());
        }
        for (OrderSummary os : orderSum) {
            ab.add(os.toJson());
        }
        resp.setCode(200);
        resp.setMessage("Order retrieve successful");
        resp.setData(ab.build().toString());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resp.toJson().toString());
    }
}
