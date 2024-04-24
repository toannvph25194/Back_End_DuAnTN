package com.example.be_duantn.controller.authentication_controller;

import com.example.be_duantn.dto.request.authentication_request.khachhang.KhachHangLoginRequest;
import com.example.be_duantn.dto.respon.authentication_respon.khachhang.KhachHangMessageResponse;
import com.example.be_duantn.dto.request.authentication_request.khachhang.KhachHangRegisterRequest;
import com.example.be_duantn.dto.respon.authentication_respon.khachhang.KhachHangLoginResponse;
import com.example.be_duantn.service.authentication_service.khach_hang_service_impl.KhachHangServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/auth/khachhang/")
public class KhachHangControler {

    @Autowired
    private KhachHangServiceImpl userService;


    @PostMapping("dangnhapkhachhang")
    public ResponseEntity<KhachHangLoginResponse> login(@Valid @RequestBody KhachHangLoginRequest khachHnagLoginRequest) {
        return new ResponseEntity<>(userService.login(khachHnagLoginRequest), HttpStatus.OK);
    }

    @PostMapping("dangkykhachhang")
    public ResponseEntity<KhachHangMessageResponse> register(@Valid @RequestBody KhachHangRegisterRequest khachHnagLoginRequest) {
        return new ResponseEntity<>(userService.register(khachHnagLoginRequest), HttpStatus.CREATED);
    }

    @PostMapping("quenmatkhaukhachhang")
    public ResponseEntity<KhachHangMessageResponse> forgotPassword(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
    }
}
