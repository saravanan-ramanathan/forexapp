package com.project.forex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.project.forex.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, QueryByExampleExecutor<Order> {

}
