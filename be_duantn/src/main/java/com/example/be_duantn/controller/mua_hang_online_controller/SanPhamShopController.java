package com.example.be_duantn.controller.mua_hang_online_controller;

import com.example.be_duantn.service.mua_hang_online_service.Impl.SanPhamShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/san-pham-shop")
public class SanPhamShopController {
    @Autowired
    SanPhamShopServiceImpl sanPhamShopService;

    // TODO Load danh mục sp shop theo trạng thái
    @GetMapping("/load-danh-muc")
    public ResponseEntity<?> loadDMSPShop(){
        return ResponseEntity.ok(sanPhamShopService.loadDanhMucSPShop());
    }

    // TODO Load màu sắc sp shop theo trạng thái
    @GetMapping("/load-mau-sac")
    public ResponseEntity<?> loadMSSPShop(){
        return ResponseEntity.ok(sanPhamShopService.loadMauSacSPShop());
    }

    // TODO Load size sp shop theo trạng thái
    @GetMapping("/load-size")
    public ResponseEntity<?> loadSizeSPShop(){
        return ResponseEntity.ok(sanPhamShopService.loadSizeSPShop());
    }

    // TODO Load sp shop phân trang
    @GetMapping("/load")
    public ResponseEntity<?> loadSPShop(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(sanPhamShopService.loadSPShop(page));
    }

    // TODO Lọc sp shop theo khoanggia
    @GetMapping("/loc/khoang-gia")
    public ResponseEntity<?> locKhoangGiaSPShop(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                @RequestParam(name = "pageSize", defaultValue = "9") Integer pageSize,
                                                @RequestParam("key1") Double key1,
                                                @RequestParam("key2") Double key2){
        return ResponseEntity.ok(sanPhamShopService.locKhongGiaSPShop(pageNumber, pageSize, key1, key2));
    }

    // TODO Lọc theo nhiều tiêu chí . tensp, tendanhmuc, tenmausac, tensize
    @GetMapping("/loc/san-pham")
    public ResponseEntity<?> locSPShop(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                       @RequestParam(name = "pageSize", defaultValue = "9") Integer pageSize, @RequestParam("tensp") String tensp,
                                       @RequestParam("tendanhmuc") String tendanhmuc, @RequestParam("tenmausac") String tenmausac,
                                       @RequestParam("tensize") String tensize){
        return ResponseEntity.ok(sanPhamShopService.locSPShop(pageNumber, pageSize, tensp, tendanhmuc, tenmausac, tensize));
    }
}
