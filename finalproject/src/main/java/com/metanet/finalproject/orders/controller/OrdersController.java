package com.metanet.finalproject.orders.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.service.IOrdersService;

import io.swagger.v3.oas.annotations.Operation;

@Controller
@RequestMapping("/orders")
public class OrdersController {
	
	@Autowired
	IOrdersService ordersService;
	
	@Operation(summary = "회원별 주문 조회")
	@GetMapping("")
	public String getOrder(){
//	List<Orders> searchOrder(HttpServletRequest request){
//	String token = tokenProvider.resolveToken(request);
//  log.info("token {}",token); //권장
//  
//  Authentication auth = tokenProvider.getAuthentication(token);
//  log.info("principal {}, name {}, authorities{}", auth.getPrincipal(), auth.getName(), auth.getAuthorities());
//		ordersService.searchOrder(1);
		return "member/orders_view";
	}
	
	@Operation(summary = "회원 회차별 주문 조회")
	@GetMapping("/{memberId}/{washId}")
	public List<Orders> searchOrder(@PathVariable int memberId, @PathVariable int washId){
		return ordersService.searchOrder(memberId, washId);
	}

	@Operation(summary = "주문 입력 view")
	@GetMapping("/insert")
	public String insertOrder() {
		return "member/orders_insert";
	}
	
	@Operation(summary = "주문 입력")
	@PostMapping("/insert")
	public String insertOrder(Principal principal) {
		return "redirect:/orders/insertOk";
	}
	
	@Operation(summary = "주문 입력 view")
	@GetMapping("/insertOk")
	public String insertOkOrder() {
		return "member/orders_insert_ok";
	}
	
	@Operation(summary = "주문 수정 view")
	@GetMapping("/update/{ordersId}")
	public String updateOrder() {
		return "dd";
	}
	
	@Operation(summary = "주문 수정")
	@GetMapping("/update")
	public String updateOrder(Principal principal) {
		return "ok";
	}
	
	@Operation(summary = "주문 삭제")
	@PostMapping("/delete")
	public String deleteOrder() {
		return "ok";
	}
}
