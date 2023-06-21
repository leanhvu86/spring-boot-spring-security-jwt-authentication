package com.trunggame.security.services;

import com.trunggame.dto.*;
import com.trunggame.models.GameOrder;

import javax.mail.MessagingException;
import java.util.List;

public interface GameOrderService {
    BaseResponseDTO<?> createOrder(OrderInfoDTO orderInfoDTO) throws MessagingException;
    GameOrder  updateOrder(Long id, OrderInfoDTO orderInfoDTO);
    OrderDTO detailById(Long id);
    void deleteOrder(Long id);
    List<GameOrder> getAllOrders(GetOrderDTO getOrderDTO);
    List<GameOrder> getCheckOrder();
    void updateOrderStatus(GetOrderDTO getOrderDTO);
    OrderInfoDetailDTO updateOrderDetailStatus(OrderInfoDetailDTO getOrderDTO);
}
