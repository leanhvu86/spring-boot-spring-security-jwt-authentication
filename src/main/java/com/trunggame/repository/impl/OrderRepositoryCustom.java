package com.trunggame.repository.impl;

import com.trunggame.dto.GameInformationDTO;
import com.trunggame.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryCustom {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<OrderDTO> getCustomerTradeInfo(Long customerId) {

        String sql =
                "select coalesce(sum(total_amount),0) total_amount_trade ," +
                        "count(id)  success_count, (select count(od.id) from game_order od where od.customer_id = "+customerId+") trade_count " +
                    "from game_order where  status = '3' and customer_id = "+customerId;

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> OrderDTO.
                builder()
                .totalAmountTrade(rs.getBigDecimal("total_amount_trade"))
                .successCount(rs.getInt("success_count"))
                .tradeCount(rs.getInt("trade_count"))
                .build());
    }
}
