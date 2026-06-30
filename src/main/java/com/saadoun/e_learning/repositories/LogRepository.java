package com.saadoun.e_learning.repositories;

import com.saadoun.e_learning.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}