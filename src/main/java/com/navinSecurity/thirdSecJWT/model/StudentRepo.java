package com.navinSecurity.thirdSecJWT.model;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.data.jpa.mapping.JpaPersistentProperty;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface  StudentRepo extends JpaRepository<Student,Integer>
{
}
