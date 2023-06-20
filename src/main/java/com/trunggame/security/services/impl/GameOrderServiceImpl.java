package com.trunggame.security.services.impl;

import com.trunggame.dto.*;
import com.trunggame.enums.EUserStatus;
import com.trunggame.models.GameOrder;
import com.trunggame.models.GameOrderDetail;
import com.trunggame.repository.*;
import com.trunggame.repository.impl.OrderRepositoryCustom;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    @Autowired
    OrderRepositoryCustom orderRepositoryCustom;

    @Override
    @Transactional
    public BaseResponseDTO<?> createOrder(OrderInfoDTO orderInfoDTO) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        var currentUser = userRepository.findByUsername(userDetails.getUsername());

        if (currentUser.isEmpty()) {
            return new BaseResponseDTO<>("User does not exist", 400, 400, null);
        }

        String uuID = UUID.randomUUID().toString();
        var gameOrder = GameOrder.builder()
                .customerId(currentUser.get().getId())
                .createdAt(LocalDateTime.now())
                .customerName(currentUser.get().getUsername())
                .code("TRUNGGAME - " + uuID)
                .status("1") // 1 - cho xu ly, 2 - dang xu ly, 3 - thanh cong , 4 - Huỷ
                .build();
        var gameOrderEntity = gameOrderRepository.save(gameOrder);

        List<GameOrderDetail> gameOrderDetails = new ArrayList<>();
        BigDecimal sum = new BigDecimal("0");
        for (var item : orderInfoDTO.getItems()) {
            sum = sum.add(item.getAmount());
            var gameOrderDetail = GameOrderDetail.builder()
                    .account(item.getAccount())
                    .gameOrderId(gameOrder.getId())
                    .characterName(item.getCharacterName())
                    .serverName(item.getServer())
                    .loginCode(item.getLoginCode())
                    .loginType(item.getLoginType())
                    .password(item.getPassword())
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
        gameOrderEntity.setTotalAmount(sum);
        gameOrderRepository.save(gameOrderEntity);
        return new BaseResponseDTO<>("Success", 200, 200, gameOrderEntity);
    }

    @Override
    @Transactional
    public GameOrder updateOrder(Long id, OrderInfoDTO orderInfoDTO) {
        var gameOrder = gameOrderRepository.findById(id);
        if (gameOrder.isPresent()) {

            // delete all old order detail
            gameOrderDetailRepository.deleteAllByGameOrderId(gameOrder.get().getId());

            // create new game order detail
            List<GameOrderDetail> gameOrderDetails = new ArrayList<>();
            for (var item : orderInfoDTO.getItems()) {
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
        var orderDetails = new ArrayList<OrderInfoDetailDTO>();
        var gameOrder = gameOrderRepository.findById(id);
        if (gameOrder.isPresent()) {
            var gameOrderDetails = gameOrderDetailRepository.findAllByGameOrderId(gameOrder.get().getId());
            if (gameOrderDetails.isEmpty()) {
                return null;
            }

            for (var orderDetailEntity : gameOrderDetails) {
                var orderDetail = new OrderInfoDetailDTO();
                var game = gameRepository.findById(Long.parseLong(orderDetailEntity.getGameId().toString()));
                var serverGameGroup = gameServerGroupRepository.findAllByGameId(game.get().getId());
                var category = categoryRepository.findById(game.get().getCategoryId());
                var pack = packageRepository.findById(orderDetailEntity.getPackageId());
                orderDetail.setId(orderDetailEntity.getId());
                orderDetail.setGameId(game.get().getId());
                orderDetail.setGamePackage(pack.get());
                orderDetail.setLoginCode(orderDetailEntity.getLoginCode());
                orderDetail.setLoginType(orderDetailEntity.getLoginType());
                orderDetail.setQuantity(orderDetailEntity.getQuantity());
                orderDetail.setAmount(orderDetailEntity.getAmount());
                orderDetail.setPrice(orderDetailEntity.getPrice());
                orderDetail.setCharacterName(orderDetailEntity.getCharacterName());
                orderDetail.setPassword(orderDetailEntity.getPassword());
                orderDetail.setGame(game.get());
                orderDetail.setAccount(orderDetailEntity.getAccount());
                orderDetail.setServer(orderDetailEntity.getServerName());
                orderDetail.setStatus(orderDetailEntity.getStatus());
                orderDetail.setServer(orderDetailEntity.getServerName());
                orderDetail.setGameCategories(category.get());
                orderDetail.setDescription(orderDetailEntity.getDescription());
                orderDetails.add(orderDetail);

            }
            var user = userRepository.findById(gameOrder.get().getCustomerId());
            String phone = "";
            String email = "";
            String name = "";
            EUserStatus userStatus = null;
            if (user.isPresent()) {
                phone = user.get().getPhoneNumber();
                email = user.get().getEmail();
                name = user.get().getFullName();
                userStatus = user.get().getStatus();
            }
            List<OrderDTO> userTradeInfo = orderRepositoryCustom.getCustomerTradeInfo(gameOrder.get().getCustomerId());

            return OrderDTO.builder()
                    .id(gameOrder.get().getId())
                    .code(gameOrder.get().getCode())
                    .orderDetailList(orderDetails)
                    .customerName(name)
                    .createdAt(gameOrder.get().getCreatedAt())
                    .phoneNumber(phone)
                    .email(email)
                    .status(gameOrder.get().getStatus())
                    .tradeCount(userTradeInfo.get(0).getTradeCount())
                    .totalAmount(gameOrder.get().getTotalAmount())
                    .totalAmountTrade(userTradeInfo.get(0).getTotalAmountTrade())
                    .userStatus(userStatus)
                    .build();
        }
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        var gameOrder = gameOrderRepository.findById(id);
        if (gameOrder.isPresent()) {
            gameOrder.get().setStatus("4");
            gameOrderRepository.save(gameOrder.get());
        }
    }

    @Override
    public List<GameOrder> getCheckOrder() {

        var orderList = gameOrderRepository.findByStatus("1");
        if (!orderList.isEmpty())
            return orderList;
        return null;
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
    @Transactional
    public void updateOrderStatus(GetOrderDTO getOrderDTO) {
        var order = gameOrderRepository.findById(getOrderDTO.getOrderId());
        if (order.isPresent()) {
            if (Objects.equals(getOrderDTO.getStatus(), "4")) {
                List<GameOrderDetail> listDetail = gameOrderDetailRepository.findAllByGameOrderId(order.get().getId());
                if (listDetail.size() > 0) {
                    listDetail.forEach(detail -> {
                        var orderDetail = gameOrderDetailRepository.findById(detail.getId());

                        if (orderDetail.isPresent()) {
                            orderDetail.get().setStatus("2");
                            gameOrderDetailRepository.save(orderDetail.get());
                        }
                    });
                }
            }
            order.get().setStatus(getOrderDTO.getStatus());
            order.get().setTotalAmount(getOrderDTO.getTotalAmount());
            gameOrderRepository.save(order.get());
        }
    }

    @Override
    public OrderInfoDetailDTO updateOrderDetailStatus(OrderInfoDetailDTO orderInfoDetailDTO) {
        var orderDetail = gameOrderDetailRepository.findById(orderInfoDetailDTO.getId());

        if (orderDetail.isPresent()) {
            var detail = orderDetail.get();
            detail.setAccount(orderInfoDetailDTO.getAccount());
            detail.setStatus(orderInfoDetailDTO.getStatus());
            detail.setLoginCode(orderInfoDetailDTO.getLoginCode());
            detail.setLoginType(orderInfoDetailDTO.getLoginType());
            detail.setCharacterName(orderInfoDetailDTO.getCharacterName());
            detail.setPrice(orderInfoDetailDTO.getPrice());
            detail.setQuantity(orderInfoDetailDTO.getQuantity());
            detail.setAmount(orderInfoDetailDTO.getAmount());
            detail.setPassword(orderInfoDetailDTO.getPassword());
            detail.setServerName(orderInfoDetailDTO.getServer());
            detail.setDescription(orderInfoDetailDTO.getDescription());
            gameOrderDetailRepository.save(detail);
            return orderInfoDetailDTO;
        }
        return null;
    }
}
