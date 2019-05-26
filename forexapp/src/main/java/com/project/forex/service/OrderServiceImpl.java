package com.project.forex.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.project.forex.model.Order;
import com.project.forex.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public Order register(Order order) {
		order.setStatus("NEW");
		return orderRepository.save(order);
	}

	public Order cancel(Order order) {
		Order cancOrder = null;
		try {
			cancOrder = orderRepository.getOne(order.getId());
			if(cancOrder == null) {
				return null;
			}
			cancOrder.setStatus("CANCEL");
			cancOrder = orderRepository.save(cancOrder);
		} catch(Exception exc) {}
		return cancOrder;
	}

	/**
	 * Method to get Unmatched orders
	 * @return list of orders
	 */
	public List<Order> getUnmatchedOrders() {
		Order order = new Order();
		//order.setStatus("UNMATCHED");
		order.setStatus("NEW");
		Example<Order> example = Example.of(order);
		return orderRepository.findAll(example);
	}

	/**
	 * Method to get Matched trades
	 * @return list of trades
	 */
	public List<Order> getMatchedTrades() {
		Order order = new Order();
		order.setStatus("MATCHED");
		Example<Order> example = Example.of(order);
		return orderRepository.findAll(example);
	}

	/**
	 * Method to get cancelled orders
	 * @return
	 */
	public List<Order> getCancelledOrders() {
		Order order = new Order();
		order.setStatus("CANCEL");
		Example<Order> example = Example.of(order);
		return orderRepository.findAll(example);
	}

	/**
	 * Method to get the new orders which
	 * will be used to match(BID / ASK)
	 * @return list of orders
	 */
	public List<Order> getNewOrders() {
		Order order = new Order();
		order.setStatus("NEW");
		Example<Order> example = Example.of(order);
		return orderRepository.findAll(example);
	}

	/**
	 * Method to match orders BID / ASK
	 */
	public Integer matchOrders() {
		int matchCount = 0;
		List<Order> newOrders = getNewOrders();
		System.out.println(">>> In Match Orders newOrders SIZE : " + newOrders.size());
		System.out.println(">>> In Match Orders newOrders : " + newOrders);
		Object []bidOrders = newOrders.stream().filter(order -> order.getOrderType().equals("BID")).toArray();
		Object []askOrders = newOrders.stream().filter(order -> order.getOrderType().equals("ASK")).toArray();
		System.out.println(">>> In Match Orders bidOrders SIZE : " + bidOrders.length);
		System.out.println(">>> In Match Orders askOrders SIZE : " + askOrders.length);
		
		List<Object> bidOrdersList = Arrays.asList(bidOrders);
		List<Object> askOrdersList = Arrays.asList(askOrders);
		System.out.println("In Matching bidOrdersList : " + bidOrdersList);
		System.out.println("In Matching askOrdersList : " + askOrdersList);
		
		// Update Matching status of Bid orders
		int bidMatch = updateMatchingStatus(bidOrdersList, askOrdersList);
		
		// Update Matching status of Ask orders
		int askMatch = updateMatchingStatus(askOrdersList, bidOrdersList);
		
		System.out.println("Order Matching BID count : " + bidMatch + " ASK count : " + askMatch);
		
		if(bidMatch == askMatch) {
			matchCount = bidMatch + askMatch;
		}
		return matchCount;
	}
	
	/**
	 * Method to update the status of order to MATCH
	 * based on the matching logic
	 * @param order
	 */
	private void match(Order order) {
		order.setStatus("MATCHED");
		orderRepository.save(order);
	}

	private int updateMatchingStatus(List<Object> firstList, List<Object> secondList) {
		int matchCount = 0;
		List<Object> matchedOrders = firstList.stream()
				.filter(secondList::contains)
				.collect(Collectors.toList());

		// Iterate the matched orders and update the status to 'MATCHED'.
		for(int index=0; index < matchedOrders.size(); index++) {
			Order order = (Order)matchedOrders.get(index);
			System.out.println("In Update Matching STATUS ORDER : " + order);
			match(order);
			matchCount++;
		}
		return matchCount;
	}
}
