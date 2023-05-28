package com.trunggame.security.services.impl;

import com.trunggame.dto.*;
import com.trunggame.models.GameOrder;
import com.trunggame.models.GameOrderDetail;
import com.trunggame.repository.*;
import com.trunggame.security.services.GameOrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameOrderServiceImpl implements GameOrderService {
    @Autowired
    GameOrderRepository gameOrderRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    GameOrderDetailRepository gameOrderDetailRepository;

    @Autowired
    GameServerGroupRepository gameServerGroupRepository;

    @Autowired
    ServerGroupRepository serverGroupRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public BaseResponseDTO<?>  createOrder(OrderInfoDTO orderInfoDTO) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        var currentUser = userRepository.findByUsername(userDetails.getUsername());

        if(currentUser.isEmpty()) {
            return new BaseResponseDTO<>("User does not exist", 400, 400,null);
        }

        String uuID = UUID.randomUUID().toString();
        var gameOrder = GameOrder.builder()
                .customerId(currentUser.get().getId())
                .createdAt(LocalDateTime.now())
                .code("TRUNGGAME - " +  uuID)
                .status("1")// 1 - cho xu ly, 2 - dang xu ly, 3 - thanh cong , 4 - that bai, 5  - deleted
                .build();
        var gameOrderEntity  =  gameOrderRepository.save(gameOrder);

        List<GameOrderDetail> gameOrderDetails = new ArrayList<>();
        for(var item : orderInfoDTO.getItems()) {
            var gameOrderDetail = GameOrderDetail.builder()
                    .account(item.getAccount())
                    .gameOrderId(gameOrder.getId())
                    .characterName(item.getCharacterName())
                    .serverName(item.getServer())
                    .loginCode(item.getLoginCode())
                    .loginType(item.getLoginType())
                    .password(item.getPassword())
                    .account(item.getAccount())
                    .gameId(item.getGameId())
                    .amount(item.getAmount())
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
                    .packageId(item.getPackageId())
                    .createdAt(LocalDateTime.now())
                    .build();
            gameOrderDetails.add(gameOrderDetail);
        }
        gameOrderDetailRepository.saveAll(gameOrderDetails);
        return new BaseResponseDTO<>("Success", 200, 200,gameOrderEntity);
    }

    @Override
    public GameOrder updateOrder(Long id, OrderInfoDTO orderInfoDTO) {
        var gameOrder = gameOrderRepository.findById(id);
        if(gameOrder.isPresent()) {

            // delete all old order detail
            gameOrderDetailRepository.deleteAllByGameOrderId(gameOrder.get().getId());

            // create new game order detail
            List<GameOrderDetail> gameOrderDetails = new ArrayList<>();
            for(var item : orderInfoDTO.getItems()) {
                var gameOrderDetail = GameOrderDetail.builder()
                        .account(item.getAccount())
                        .gameOrderId(gameOrder.get().getId())
                        .characterName(item.getCharacterName())
                        .serverName(item.getServer())
                        .loginCode(item.getLoginCode())
                        .loginType(item.getLoginType())
                        .password(item.getPassword())
                        .account(item.getAccount())
                        .gameId(item.getGameId())
                        .amount(item.getAmount())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .packageId(item.getPackageId())
                        .createdAt(LocalDateTime.now())
                        .build();
                gameOrderDetails.add(gameOrderDetail);
            }
            gameOrderDetailRepository.saveAll(gameOrderDetails);

        }
        return null;
    }

    @Override
    public OrderDTO detailById(Long id) {
        var orderDTO = new OrderDTO();
        var orderDetails = new ArrayList<OrderInfoDetailDTO>();
        var gameOrder = gameOrderRepository.findById(id);
        if(gameOrder.isPresent()) {
            var gameOrderDetails = gameOrderDetailRepository.findAllByGameOrderId(gameOrder.get().getId());
            if(gameOrderDetails.isEmpty()) {
                return null;
            }

            for(var orderDetailEntity : gameOrderDetails) {
                var orderDetail = new OrderInfoDetailDTO();
                var game = gameRepository.findById(Long.parseLong(orderDetailEntity.getGameId().toString()));
                var serverGameGroup = gameServerGroupRepository.findAllByGameId(game.get().getId());
                var category = categoryRepository.findById(game.get().getCategoryId());
                var pack  = packageRepository.findById(orderDetailEntity.getPackageId());
                            orderDetail.setId(gameOrder.get().getId());
                orderDetail.setGameId(game.get().getId());
                orderDetail.setGamePackage(pack.get());
                orderDetail.setLoginCode(orderDetailEntity.getLoginCode());
                orderDetail.setLoginType(orderDetailEntity.getLoginType());
                orderDetail.setQuantity(orderDetailEntity.getQuantity());
                orderDetail.setAmount(orderDetail.getAmount());
                orderDetail.setCharacterName(orderDetailEntity.getCharacterName());
                orderDetail.setPassword(orderDetailEntity.getPassword());
                orderDetail.setGame(game.get());
                orderDetail.setAccount(orderDetailEntity.getAccount());
                orderDetail.setServer(orderDetailEntity.getServerName());
                orderDetail.setServer(orderDetailEntity.getServerName());
                orderDetail.setGameCategories(category.get());
                orderDetails.add(orderDetail);

            }

            orderDTO.setId(gameOrder.get().getId());
            orderDTO.setCode(gameOrder.get().getCode());
            orderDTO.setOrderDetailList(orderDetails);
            return orderDTO;
        }
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        var gameOrder = gameOrderRepository.findById(id);
        if(gameOrder.isPresent()) {
            gameOrder.get().setStatus("5");
            gameOrderRepository.save(gameOrder.get());
        }
    }

    @Override
    public List<GameOrder> getAllOrders(GetOrderDTO getOrderDTO) {
        String orderCode = getOrderDTO.getOrderCode();
        String orderBy = getOrderDTO.getOrderBy();
        String orderType = getOrderDTO.getOrderType();
        int pageSize = Integer.parseInt(getOrderDTO.getPageSize());
        int pageNumber = Integer.parseInt(getOrderDTO.getPageNumber());

        // Use the filters to retrieve all orders from the database
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        Specification<GameOrder> spec = Specification.where(OrderSpecification.orderCodeEqual(orderCode));

        if (orderType.equalsIgnoreCase("desc")) {
            pageable = ((PageRequest) pageable).withSort(Sort.by(orderBy).descending());
        }
        return gameOrderRepository.findAll(spec, pageable).getContent();
    }

    @Override
    public void updateOrderStatus(GetOrderDTO getOrderDTO) {
        var order = gameOrderRepository.findById(getOrderDTO.getOrderId());
        if(order.isPresent()) {
            order.get().setStatus(getOrderDTO.getStatus());
            gameOrderRepository.save(order.get());
        }
    }
}
