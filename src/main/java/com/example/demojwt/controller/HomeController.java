package com.example.demojwt.controller;

import com.example.demojwt.dto.response.MessageResponse;
import com.example.demojwt.entity.bo.Resource;
import com.example.demojwt.service.ResourceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/home")
public class HomeController {
    private static final Logger LOGGER = LogManager.getLogger(HomeController.class);


    @Autowired
    ResourceService resourceService;
    @Autowired
    RedisTemplate redisTemplate;

    private HashOperations hashOperations;

    @GetMapping("/getResource/{page}")
    public ResponseEntity<?> getResource(/*@PageableDefault(sort = "code", direction = Sort.Direction.DESC)Pageable pageable*/@PathVariable("page") int page) {
//    Sort sort = Sort.by("apiPath").descending();
//    Pageable pageable = PageRequest.of(1, 10, sort);
//    Page<Resource> page = resourceService.resource(pageable);
        Page<Resource> resourcePage = resourceService.getResource(page);
        if (resourcePage == null) {
            return ResponseEntity.ok("no page");
        }
        Map<Object, Object> aa = new HashMap<>();
        Map<String, String> map = new HashMap<String, String>();
        for (Map.Entry<Object, Object> entry : aa.entrySet()) {
            String key = (String) entry.getKey();
            map.put(key, aa.get(key).toString());

        }
        LOGGER.info("hello" + map);
        return new ResponseEntity<>(new MessageResponse("total: " + resourcePage.getTotalPages(), 200, resourcePage), HttpStatus.OK);
    }

    @PostMapping("/saveResource/{id}")
    public ResponseEntity<MessageResponse> save(@PathVariable Long id, @RequestBody Resource resource) {
        Optional<Resource> currentResource = resourceService.findById(id);
        if (!currentResource.isPresent()) {
            resourceService.save(resource);
            return new ResponseEntity<>(new MessageResponse("vao day", 200, resource), HttpStatus.OK);

        }
        resource.setId(currentResource.get().getId());
        resourceService.save(resource);
        return new ResponseEntity<>(new MessageResponse("success", 200, resource), HttpStatus.OK);
    }

    @DeleteMapping("/removeResource/{id}")
    public ResponseEntity<?> removeResource(@PathVariable("id") Long id) {
        Optional<Resource> resource = resourceService.findById(id);
        if (resource.isPresent()) {
            resourceService.delete(id);
            return new ResponseEntity<>(new MessageResponse("deleted", 200, resource), HttpStatus.OK);
        }
        return new ResponseEntity<>("no resource", HttpStatus.OK);
    }
}
