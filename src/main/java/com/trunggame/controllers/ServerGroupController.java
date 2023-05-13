package com.trunggame.controllers;
import com.trunggame.dto.ServerGroupDTO;
import com.trunggame.models.ServerGroup;
import com.trunggame.security.services.ServerGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/server-groups")
public class ServerGroupController {

    @Autowired
    private ServerGroupService service;

    @PostMapping
    public ResponseEntity<ServerGroup> create(@RequestBody ServerGroupDTO input) {
        ServerGroup sG = new ServerGroup();
        sG.setName(input.getName());
        sG.setCreatedAt(new Date());
        if(input.getParentId() != null) {
            sG.setParentId(input.getParentId());
         }else {
            sG.setParentId(0L);

        }
        ServerGroup savedServerGroup = service.save(sG);
        return new ResponseEntity<>(savedServerGroup, HttpStatus.CREATED);
    }

    @GetMapping("/group-by-parent")
    public ResponseEntity<Map<String, List<ServerGroupDTO>>> groupByParentId() {
        List<ServerGroupDTO> serverGroups = service.getAllServerGroupByParentId();
        Map<String, List<ServerGroupDTO>> groupedServerGroups = serverGroups.stream()
                .collect(Collectors.groupingBy(ServerGroupDTO::getParentName));
        return new ResponseEntity<>(groupedServerGroups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServerGroup> findById(@PathVariable Long id) {
        Optional<ServerGroup> optionalServerGroup = service.findById(id);
        return optionalServerGroup
                .map(serverGroup -> new ResponseEntity<>(serverGroup, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServerGroup> update(@PathVariable Long id, @RequestBody ServerGroup serverGroup) {
        Optional<ServerGroup> optionalServerGroup = service.findById(id);
        return optionalServerGroup
                .map(existingServerGroup -> {
                    existingServerGroup.setName(serverGroup.getName());
                    ServerGroup updatedServerGroup = service.save(existingServerGroup);
                    return new ResponseEntity<>(updatedServerGroup, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<ServerGroup> optionalServerGroup = service.findById(id);
        return optionalServerGroup
                .map(serverGroup -> {
                    service.deleteById(id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
