package com.example.demojwt.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resources")
@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String apiPath;
    private Integer sysResourceId;
    private Integer sysOperationId;
    private Integer active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getSysResourceId() {
        return sysResourceId;
    }

    public void setSysResourceId(Integer sysResourceId) {
        this.sysResourceId = sysResourceId;
    }

    public Integer getSysOperationId() {
        return sysOperationId;
    }

    public void setSysOperationId(Integer sysOperationId) {
        this.sysOperationId = sysOperationId;
    }
}
