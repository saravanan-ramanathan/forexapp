package com.project.forex.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.forex.model.Order;
import com.project.forex.service.OrderService;

@RestController
@RequestMapping("/api/v1")
public class ForexController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private Environment env;

	@PostMapping(path="/registerorder")
	@ResponseStatus(HttpStatus.OK)
	public Order orderRegistration(@RequestBody Order order) {
		System.out.println("In Reg : " + order);
		String priceFromConf = env.getProperty("forex.rate.gbp.usd");
		System.out.println("Price from app.prop : " + priceFromConf);
		if(priceFromConf != null) {
			order.setPrice(new BigDecimal(priceFromConf));
		}
		return orderService.register(order);
	}
	
	@PostMapping(path="/cancelorder")
	@ResponseStatus(HttpStatus.OK)
	public Order orderCancellation(@RequestBody Order order) {
		System.out.println("In Canc : " + order);
		return orderService.cancel(order);
	}
	
	@GetMapping("/unmatchedorders")
	public List<Order> getUnmatchedOrders() {
		return orderService.getUnmatchedOrders();
	}
	
	@GetMapping("/matchedtrades")
	public List<Order> getMatchedTrades() {
		return orderService.getMatchedTrades();
	}

	@GetMapping("/cancelledorders")
	public List<Order> getCancelledOrders() {
		return orderService.getCancelledOrders();
	}

	@PostMapping("/matchorders")
	@ResponseStatus(HttpStatus.OK)
	public Integer matchOrders() {
		return orderService.matchOrders();
	}
}
