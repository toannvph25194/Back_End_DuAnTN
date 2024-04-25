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
@RequestMapping("/api/ol/san-pham")
public class SanPhamHomeController {

    @Autowired
    SanPhamHomeServiceImpl sanPhamHomeService;

    // ToDo getAll SPHome phân trang
    @GetMapping("/show")
    public ResponseEntity<?> getAllSPHome(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(sanPhamHomeService.getAllSPHome(page));
    }

    // ToDo getAll SPNamHome phân trang
    @GetMapping("/show-nam")
    public ResponseEntity<?> getAllSPNamNuHome(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(sanPhamHomeService.getAllSPNamHome(page));
    }

    // ToDo getAll SPNuHome phân trang
    @GetMapping("/show-nu")
    public ResponseEntity<?> getAllSPNuHome(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(sanPhamHomeService.getAllSPNuHome(page));
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
