package com.example.be_duantn.controller.mua_hang_online_controller;

import com.example.be_duantn.service.mua_hang_online_service.Impl.SanPhamHomeServiceImpl;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/san-pham")
public class SanPhamHomeController {

    @Autowired
    SanPhamHomeServiceImpl sanPhamHomeService;

    // ToDo getAll SPHome phân trang
    @GetMapping("/show")
    public ResponseEntity<?> getAllSPHome(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(sanPhamHomeService.getAllSPHome(page));
    }

    // ToDo getAll SPNamNuHome phân trang
    @GetMapping("/show-nam-nu")
    public ResponseEntity<?> getAllSPNamNuHome(@RequestParam(defaultValue = "0", value = "page") Integer page , @RequestParam("theloai") Integer theloai){
        return ResponseEntity.ok(sanPhamHomeService.getAllSPNamNuHome(page,theloai));
    }

    // ToDo getAll SPNewHome
    @GetMapping("/show-new")
    public ResponseEntity<?> getAllSPNewHome(){
        return ResponseEntity.ok(sanPhamHomeService.getAllSPNewHome());
    }

    // ToDo getAll 6 SPGGHome
    @GetMapping("/show-giam-gia")
    public ResponseEntity<?> getAllSPGGHome(){
        return ResponseEntity.ok(sanPhamHomeService.getAllSPGGHome());
    }
}
