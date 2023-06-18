package com.trunggame.controllers;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.GetOrderDTO;
import com.trunggame.dto.OrderDTO;
import com.trunggame.dto.OrderInfoDTO;
import com.trunggame.models.GameOrder;
import com.trunggame.security.services.GameOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private GameOrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public BaseResponseDTO<?>  createOrder(@RequestBody OrderInfoDTO orderInfoDTO) {
        return orderService.createOrder(orderInfoDTO);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GameOrder updateOrder(@PathVariable Long id, @RequestBody OrderInfoDTO orderInfoDTO) {
        return orderService.updateOrder(id, orderInfoDTO);
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public OrderDTO detail(@PathVariable Long id) {
        return orderService.detailById(id);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<GameOrder> getAllOrders(@ModelAttribute GetOrderDTO getOrderDTO) {
        return orderService.getAllOrders(getOrderDTO);
    }


    @PostMapping("/update/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public void updateOrderStatus(@RequestBody GetOrderDTO getOrderDTO) {
         orderService.updateOrderStatus(getOrderDTO);
    }


    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteOrder(@PathVariable Long id) {
         orderService.deleteOrder(id);
    }
}
