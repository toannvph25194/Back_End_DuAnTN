package com.example.be_duantn.controller.ban_hang_tai_quay_controller;

import com.example.be_duantn.service.ban_hang_tai_quay_service.Impl.SanPhamCTBanTaiQuayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/san-pham-chi-tiet-tai-quay")
public class SanPhamCTBanTaiQuayController {

    @Autowired
    SanPhamCTBanTaiQuayServiceImpl sanPhamCTBanTaiQuayService;

    // ToDo Load sản phẩm phân trang lên bán hàng tại quầy
    @GetMapping("/load")
    public ResponseEntity<?> LoadSPBanTaiQuay(@RequestParam(defaultValue = "0", value = "page") Integer page){
        try {
            return ResponseEntity.ok(sanPhamCTBanTaiQuayService.LoadSPBanTaiQuay(page));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load sản phẩm bán tại quầy !");
        }
    }

    // ToDo Load màu sắc lên bán hàng tại quầy
    @GetMapping("/load-mau-sac")
    public ResponseEntity<?> LoadMSBanTaiQuay(){
        try {
            return ResponseEntity.ok(sanPhamCTBanTaiQuayService.LoadMauSacBanTaiQuay());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load màu sắc bán tại quầy !");
        }
    }

    // ToDo Load size lên bán hàng tại quầy
    @GetMapping("/load-size")
    public ResponseEntity<?> LoadSizeBanTaiQuay(){
        try {
            return ResponseEntity.ok(sanPhamCTBanTaiQuayService.LoadSizeBanTaiQuay());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load size bán tại quầy !");
        }
    }

    // ToDo Load chất liệu lên bán hàng tại quầy
    @GetMapping("/load-chat-lieu")
    public ResponseEntity<?> LoadCLBanTaiQuay(){
        try {
            return ResponseEntity.ok(sanPhamCTBanTaiQuayService.LoadChatLieuBanTaiQuay());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load chất liệu bán tại quầy !");
        }
    }

    // ToDo Load danh mục lên bán hàng tại quầy
    @GetMapping("/load-danh-muc")
    public ResponseEntity<?> LoadDMBanTaiQuay(){
        try {
            return ResponseEntity.ok(sanPhamCTBanTaiQuayService.LoadDanhMucBanTaiQuay());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load danh mục bán tại quầy !");
        }
    }

    // ToDo Load thương hiệu lên bán hàng tại quầy
    @GetMapping("/load-thuong-hieu")
    public ResponseEntity<?> LoadTHBanTaiQuay(){
        try {
            return ResponseEntity.ok(sanPhamCTBanTaiQuayService.LoadThuongHieuBanTaiQuay());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load thương hiệu bán tại quầy !");
        }
    }

    // ToDo Load xuất xứ lên bán hàng tại quầy
    @GetMapping("/load-xuat-xu")
    public ResponseEntity<?> LoadXXBanTaiQuay(){
        try {
            return ResponseEntity.ok(sanPhamCTBanTaiQuayService.LoadXuatXuBanTaiQuay());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load xuất xứ bán tại quầy !");
        }
    }

    // ToDo Loc sản phẩm phân trang lên bán hàng tại quầy
    @GetMapping("/loc-ten-sp")
    public ResponseEntity<?> LocTenSPBanTaiQuay(@RequestParam(defaultValue = "0", value = "page") Integer page, @RequestParam("tensp") String tensp){
        try {
            return ResponseEntity.ok(sanPhamCTBanTaiQuayService.LocTenSPBanTaiQuay(page, tensp));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi lọc sản phẩm bán tại quầy !");
        }
    }

    // ToDo Lọc sản phẩm phân trang theo nhiều tiêu chí lên bán hàng tại quầy
    @GetMapping("/loc-tieu-chi-sp")
    public ResponseEntity<?> LocSPNhieuTieuChiBanTaiQuay(@RequestParam(defaultValue = "0", value = "page") Integer page, @RequestParam("tenmausac") String tenmausac,
                                                         @RequestParam("tensize") String tensize,  @RequestParam("tenchatlieu") String tenchatlieu,  @RequestParam("tendanhmuc") String tendanhmuc,
                                                         @RequestParam("tenthuonghieu") String tenthuonghieu, @RequestParam("tenxuatxu") String tenxuatxu){
        try {
            return ResponseEntity.ok(sanPhamCTBanTaiQuayService.LocSPNhieuTieuChiBanTaiQuay(page, tenmausac, tensize, tenchatlieu, tendanhmuc, tenthuonghieu, tenxuatxu));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi lọc sản phẩm theo nhiều tiêu chí bán tại quầy !");
        }
    }
}
