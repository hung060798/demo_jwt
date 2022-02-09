package com.example.demojwt.service;

import com.example.demojwt.entity.bo.Resource;
import com.example.demojwt.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ResourceService {
    @Autowired
    ResourceRepository resourceRepository;
    public Page<Resource> resource(Pageable pageable){
        return resourceRepository.getResource(pageable);
    }

    public Page<Resource> getResource(int page){
        Page<Resource> pageResource = resourceRepository.findAll(PageRequest.of(page, 5, Sort.by("apiPath").descending()));
        return pageResource;
    }

    public Resource save(Resource resource){
        resourceRepository.save(resource);
        return resource;
    }

    public String delete(Long id){
        resourceRepository.deleteById(id);
        return "deleted";
    }

    public Optional<Resource> findById(Long id){
        Optional<Resource> resource = resourceRepository.findById(id);
        return  resource;
    }
}
