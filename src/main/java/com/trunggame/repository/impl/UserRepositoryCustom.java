package com.trunggame.repository.impl;


import com.trunggame.models.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class UserRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    public Page<User> getListUser(String search, Pageable pageable) {

        StringBuilder strQuery  = new StringBuilder();
        strQuery.append(" select * from users as s where 1=1 ");
        if(StringUtils.isNoneBlank(search)) {
            strQuery.append(" and ( s.email like CONCAT('%',?1,'%') ");
            strQuery.append(" or s.username like CONCAT('%' ,?2,'%') ");
            strQuery.append(" or s.phone_number like CONCAT('%',?3 , '%') )");
        }

        System.out.println(strQuery.toString());


        Query query = entityManager.createNativeQuery(
                strQuery.toString(),
                User.class);
        Query countQuery = entityManager.createNativeQuery(
                "select count(*) from ( " + strQuery + ") as tmp");

        if(StringUtils.isNoneBlank(search)) {
            query.setParameter(1, search);
            query.setParameter(2, search);
            query.setParameter(3, search);

            countQuery.setParameter(1, search);
            countQuery.setParameter(2, search);
            countQuery.setParameter(3, search);
        }
        long total = ((Number) countQuery.getSingleResult()).longValue();

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return new PageImpl<User>(query.getResultList(), pageable, total);
    }

}
