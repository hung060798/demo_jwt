package com.example.demojwt.repository;

import com.example.demojwt.entity.bo.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    @Query("select r from Resource r")
    Page<Resource> getResource (Pageable pageable);

}
