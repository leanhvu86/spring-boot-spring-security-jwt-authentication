package com.trunggame.controllers;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.GetOrderDTO;
import com.trunggame.dto.OrderDTO;
import com.trunggame.dto.OrderInfoDTO;
import com.trunggame.models.GameOrder;
import com.trunggame.security.services.GameOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private GameOrderService orderService;

    @PostMapping("/create")
    public BaseResponseDTO<?>  createOrder(@RequestBody OrderInfoDTO orderInfoDTO) {
        return orderService.createOrder(orderInfoDTO);
    }

    @PostMapping("/update/{id}")
    public GameOrder updateOrder(@PathVariable Long id, @RequestBody OrderInfoDTO orderInfoDTO) {
        return orderService.updateOrder(id, orderInfoDTO);
    }

    @GetMapping("/detail/{id}")
    public OrderDTO detail(@PathVariable Long id) {
        return orderService.detailById(id);
    }

    @GetMapping("")
    public List<GameOrder> getAllOrders(@ModelAttribute GetOrderDTO getOrderDTO) {
        return orderService.getAllOrders(getOrderDTO);
    }


    @PostMapping("/update/status")
    public void updateOrderStatus(@RequestBody GetOrderDTO getOrderDTO) {
         orderService.updateOrderStatus(getOrderDTO);
    }


    @PostMapping("/delete/{id}")
    public void deleteOrder(@PathVariable Long id) {
         orderService.deleteOrder(id);
    }
}
