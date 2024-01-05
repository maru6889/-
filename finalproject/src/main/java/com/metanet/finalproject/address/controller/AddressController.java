package com.metanet.finalproject.address.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.finalproject.address.model.Address;
import com.metanet.finalproject.address.service.IAddressService;
import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/member/address")
@Tag(name = "Address", description = "주소 관리 API")
public class AddressController {
	
	@Autowired IAddressService addressService;
	
	@Autowired IMemberService memberService;


	@Autowired
    JwtTokenProvider jwtTokenProvider;
    
	//	Header에서 Token으로 사용자 이메일 획득
	private String getTokenUserEmail(HttpServletRequest request) {
        String token = "";

        try {
            Cookie[] cookies = request.getCookies();
        	for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    token = cookie.getValue();
                }
            }
        } catch(Exception e) {
        	e.getMessage();
        }
        
        return jwtTokenProvider.getUserId(token);
	}
	


	@Operation(summary = "사용자 주소 조회")
	@GetMapping("")
	public String getAddress(HttpServletRequest request, Model model) {
		String userEmail = getTokenUserEmail(request);
		Member member = memberService.selectMember(userEmail);
		Address address = addressService.getAddress(member.getMemberId());
		model.addAttribute("address", address);
		return "member/address_view";
	}
	
	@Operation(summary = "사용자 주소 입력 view")
	@GetMapping("/insert")
	public String insetAddress(Model model) {
		return "member/address_insert";
	}
	
	@Operation(summary = "사용자 주소 입력")
	@PostMapping("/insert")
	public String insetAddress(HttpServletRequest request, Model model, Address address) {
		String userEmail = getTokenUserEmail(request);
		Member member = memberService.selectMember(userEmail);
		address.setMemberId(member.getMemberId());
		address.setAddressCategory("3");
		addressService.insertAddress(address);

		return "redirect:/member/address";
	}
	
	@Operation(summary = "사용자 주소 수정 view")
	@GetMapping("/update/{addressId}")
	public String updateAddress(@PathVariable int addressId, Model model) {
		Address address = addressService.getOneAddress(addressId);
		model.addAttribute("address", address);
		return "member/address_update";
	}
	
	@Operation(summary = "사용자 주소 수정")
	@PostMapping("/update")
	public String updateAddress(Model model, Address address) {
		addressService.updateAddress(address);
		return "redirect:/member/address";
	}
	

	@PostMapping("/delete")
	public String deleteAddress(Address address, Model model) {
		addressService.deleteAddress(address.getAddressId());
		return "redirect:/member/address";
	}
}
