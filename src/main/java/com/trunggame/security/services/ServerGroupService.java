package com.trunggame.security.services;

import com.trunggame.dto.ServerGroupDTO;
import com.trunggame.models.ServerGroup;
import com.trunggame.repository.ServerGroupRepository;
import com.trunggame.repository.impl.ServerGroupCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class ServerGroupService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private ServerGroupRepository repository;

    @Autowired
    private ServerGroupCustomRepository serverGroupCustomRepository;

    public ServerGroup save(ServerGroup serverGroup) {
        return repository.save(serverGroup);
    }

    public Optional<ServerGroup> findById(Long id) {
        return repository.findById(id);
    }

    public List<ServerGroup> findAll() {
        Query query = entityManager.createNativeQuery(
                "select sg.*, parentSG.name as parentName from server_group as sg  " +
                        "            join server_group as parentSG on sg.parent_id = parentSG.id;", "ServerGroupMapping");

        List<ServerGroup> results = query.getResultList();
        return results;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public  List<ServerGroupDTO>  getAllServerGroupByParentId() {
        return serverGroupCustomRepository.getAllServerGroup();
    }
}
