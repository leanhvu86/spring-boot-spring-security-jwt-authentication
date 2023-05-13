package com.trunggame.security.services;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.GetOrderDTO;
import com.trunggame.dto.OrderDTO;
import com.trunggame.dto.OrderInfoDTO;
import com.trunggame.models.GameOrder;

import java.util.List;

public interface GameOrderService {
    BaseResponseDTO<?> createOrder(OrderInfoDTO orderInfoDTO);
    GameOrder  updateOrder(Long id, OrderInfoDTO orderInfoDTO);
    OrderDTO detailById(Long id);
    void deleteOrder(Long id);
    List<GameOrder> getAllOrders(GetOrderDTO getOrderDTO);
    void updateOrderStatus(GetOrderDTO getOrderDTO);
}
