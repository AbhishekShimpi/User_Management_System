package com.tech.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    public Role() {
		// TODO Auto-generated constructor stub
	}
    

    //custom constructor
    public Role(String name) {
        this.name = name;
    }

    public Role(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	// Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
}