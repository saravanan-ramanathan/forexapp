package com.project.forex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.forex.model.Order;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ForexappApplication.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ForexappApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int localPort;
	
/*	@Test
	public void when1ContextLoads() {
		System.out.println(">>>>> Test Case 1");
	}*/
	
	private String getBaseURL() {
		return "http://localhost:" + localPort + "/api/v1";
	}
	
	@Test
	@Ignore
	public void when2RegisteringNewOrderReturnResponseObjectShouldNotBeNull() {
		System.out.println(">>>>> Test Case 2");
		// Given
		Order order = new Order();
		//order.setId(new Long("1"));
		order.setUserId("TestJoe");
		order.setOrderType("BID");
		order.setCurrency("GBP/USD");
		order.setPrice(new BigDecimal("1.2323"));
		order.setAmount(new BigDecimal("4646"));

		// When
		ResponseEntity<Order> postResponse = restTemplate.postForEntity(getBaseURL() + "/registerorder", order, Order.class);
		
		// Then
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void when3RegisteringNewOrderInitialOrderStatusShouldBeNew() {
		System.out.println(">>>>> Test Case 3");
		// Given
		Order order = new Order();
		//order.setId(new Long("1"));
		order.setUserId("TestMark");
		order.setOrderType("ASK");
		order.setCurrency("GBP/USD");
		order.setPrice(new BigDecimal("1.2323"));
		order.setAmount(new BigDecimal("4646"));

		// When
		ResponseEntity<Order> postResponse = restTemplate.postForEntity(getBaseURL() + "/registerorder", order, Order.class);
		
		// Then
		assertEquals("NEW", postResponse.getBody().getStatus());
	}

	@Test
	public void when4CancellingAnOrderStatusShouldBeUpdatedToCancel() {
		System.out.println(">>>>> Test Case 4");
		// Given
		Order order = new Order();
		order.setId(new Long("1"));

		// When
		ResponseEntity<Order> postResponse = restTemplate.postForEntity(getBaseURL() + "/cancelorder", order, Order.class);
		
		// Then
		assertEquals("CANCEL", postResponse.getBody().getStatus());
	}

	@Test
	public void when5InvokingCancelOrderItShouldReturnAllCancelledOrders() {
		System.out.println(">>>>> Test Case 5");
		// Given
		// Do nothing
		
		// When
		ResponseEntity<List<Order>> response = restTemplate.exchange(getBaseURL() + "/cancelledorders", 
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>() {});
		List<Order> cancelOrders = response.getBody();
		
		// Then
		assertEquals(1, cancelOrders.size());
	}

	@Test
	public void when6BidAndAskOrdersAreMatchedThenMatchingOrdersShouldBeUpdatedToMatchedStatusAndReturnMatchCount() {
		System.out.println(">>>>> Test Case 6");
		// Given

		// Order 1(BID)
		Order order = new Order();
		order.setUserId("TestJoeBid");
		order.setOrderType("BID");
		order.setCurrency("GBP/USD");
		order.setPrice(new BigDecimal("1.2323"));
		order.setAmount(new BigDecimal("5000"));
		ResponseEntity<Order> postResponse = restTemplate.postForEntity(getBaseURL() + "/registerorder", order, Order.class);

		// Order 2(ASK)
		order = new Order();
		order.setUserId("TestMarkAsk");
		order.setOrderType("ASK");
		order.setCurrency("GBP/USD");
		order.setPrice(new BigDecimal("1.2323"));
		order.setAmount(new BigDecimal("5001"));
		postResponse = restTemplate.postForEntity(getBaseURL() + "/registerorder", order, Order.class);

		// Order 3(BID)
		order = new Order();
		order.setUserId("TestJoeBid");
		order.setOrderType("BID");
		order.setCurrency("GBP/USD");
		order.setPrice(new BigDecimal("1.2323"));
		order.setAmount(new BigDecimal("10000"));
		postResponse = restTemplate.postForEntity(getBaseURL() + "/registerorder", order, Order.class);

		// Order 4(ASK)
		order = new Order();
		order.setUserId("TestMarkAsk");
		order.setOrderType("ASK");
		order.setCurrency("GBP/USD");
		order.setPrice(new BigDecimal("1.2323"));
		order.setAmount(new BigDecimal("10000"));
		postResponse = restTemplate.postForEntity(getBaseURL() + "/registerorder", order, Order.class);

		// When
		ResponseEntity<Integer> postResponseMatch = restTemplate.postForEntity(getBaseURL() + "/matchorders", null, Integer.class);
		
		// Then
		assertEquals(2, postResponseMatch.getBody().intValue());
	}

	@Test
	public void when7InvokingMatchedTradesItShouldReturnAllMatchedTrades() {
		System.out.println(">>>>> Test Case 7");
		// Given
		// Do nothing
		
		// When
		ResponseEntity<List<Order>> response = restTemplate.exchange(getBaseURL() + "/matchedtrades", 
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>() {});
		List<Order> matchedTrades = response.getBody();
		
		// Then
		assertEquals(2, matchedTrades.size());
	}

	@Test
	public void when8InvokingUnmatchedOrdersItShouldReturnAllUnmatchedOrders() {
		System.out.println(">>>>> Test Case 8");
		// Given
		// Do nothing
		
		// When
		ResponseEntity<List<Order>> response = restTemplate.exchange(getBaseURL() + "/unmatchedorders", 
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>() {});
		List<Order> unmatchedOrders = response.getBody();
		
		// Then
		assertEquals(2, unmatchedOrders.size());
	}
}
