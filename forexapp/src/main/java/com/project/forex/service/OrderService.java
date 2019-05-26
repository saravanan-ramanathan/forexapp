package com.project.forex.service;

import java.util.List;

import com.project.forex.model.Order;

public interface OrderService {

	public Order register(Order order);

	public Order cancel(Order order);

	public List<Order> getUnmatchedOrders();

	public List<Order> getMatchedTrades();

	public List<Order> getCancelledOrders();

	public List<Order> getNewOrders();

	public Integer matchOrders();

}
