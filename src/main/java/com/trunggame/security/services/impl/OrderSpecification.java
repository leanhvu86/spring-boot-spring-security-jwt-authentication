package com.trunggame.security.services.impl;

import com.trunggame.models.GameOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

    public static Specification<GameOrder> serverEqual(String server) {
        return StringUtils.isEmpty(server) ? null : (root, query, builder) -> builder.equal(root.get("serverName"), server);
    }

    public static Specification<GameOrder> customerId(Long id) {
        return id>0 ? null : (root, query, builder) -> builder.equal(root.get("customerId"), id);
    }

    public static Specification<GameOrder> loginTypeEqual(String loginType) {
        return StringUtils.isEmpty(loginType) ? null : (root, query, builder) -> builder.equal(root.get("loginType"), loginType);
    }

    public static Specification<GameOrder> characterNameLike(String characterName) {
        return StringUtils.isEmpty(characterName) ? null : (root, query, builder) -> builder.like(root.get("characterName"), "%" + characterName + "%");
    }

    public static Specification<GameOrder> orderCodeEqual(String orderCode) {
        return StringUtils.isEmpty(orderCode) ? null : (root, query, builder) -> builder.equal(root.get("code"), orderCode);
    }
}
