package com.example.be_duantn.controller.mua_hang_online_controller;

import com.example.be_duantn.service.mua_hang_online_service.Impl.SanPhamChiTietServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/ol/san-pham-chi-tiet")
public class SanPhamChiTietController {

    @Autowired
    SanPhamChiTietServiceImpl sanPhamChiTietService;

    // ToDo load top 4 sp mới nhất
    @GetMapping("/load/san-pham-new")
    public ResponseEntity<?> loadSPCTNew(){
        try {
            return ResponseEntity.ok(sanPhamChiTietService.loadSPCTNew());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(("Lỗi load top 4 spct new !"));
        }
    }

    // ToDo finBySP theo idsp
    @GetMapping("/findbyid/san-pham")
    public ResponseEntity<?> finByIdSP(@RequestParam("idsp") UUID idsp){
        try {
            return ResponseEntity.ok(sanPhamChiTietService.finByIdSP(idsp));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(("Lỗi finById SP !"));
        }
    }

    // ToDo load listImage theo idsp
    @GetMapping("/load-list/image-san-pham")
    public ResponseEntity<?> loadListImageSP(@RequestParam("idsp") UUID idsp){
        try {
            return ResponseEntity.ok(sanPhamChiTietService.loadListImageSP(idsp));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(("Lỗi load list image SP !"));
        }
    }

    // ToDo load mausac theo idsp
    @GetMapping("/load-mau-sac")
    public ResponseEntity<?> loadMauSacByIdSP(@RequestParam("idsp") UUID idsp){
        try {
            return ResponseEntity.ok(sanPhamChiTietService.loadMauSacByIdSP(idsp));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(("Lỗi load màu sắc theo idsp !"));
        }
    }

    // ToDo load size theo idsp
    @GetMapping("/load-size")
    public ResponseEntity<?> loadSizeByIdSP(@RequestParam("idsp") UUID idsp){
        try {
            return ResponseEntity.ok(sanPhamChiTietService.loadSizeByIdSP(idsp));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(("Lỗi load size theo idsp !"));
        }
    }

    // ToDo finByMauSac theo idsp và idsize
    @GetMapping("/finby-mau-sac")
    public ResponseEntity<?> finMauSacByIdSPAndIdSize(@RequestParam("idsp") UUID idsp, @RequestParam("idsize") UUID idsize){
        try {
            return ResponseEntity.ok(sanPhamChiTietService.finMauSacByIdSPAndIdSize(idsp, idsize));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(("Lỗi finBy mausac theo idsp, idsize !"));
        }
    }

    //ToDo finBySize theo idsp và idmausac
    @GetMapping("/finby-size")
    public ResponseEntity<?> finSizeByIdSPAndIdMauSac(@RequestParam("idsp") UUID idsp, @RequestParam("idmausac") UUID idmausac){
        try {
            return ResponseEntity.ok(sanPhamChiTietService.finSizeByIdSPAndIdMauSac(idsp, idmausac));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(("Lỗi finBy size theo idsp, idmausac !"));
        }
    }

    // ToDo tính tổng tất cả SoLuongTon spct theo idsp
    @GetMapping("/tong-so-luong-ton/san-pham")
    public ResponseEntity<?> tinhTongSoLuongTonSPCT(@RequestParam("idsp") UUID idsp){
        try {
            return ResponseEntity.ok(sanPhamChiTietService.tinhTongSoLuongTonSPCT(idsp));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(("Lỗi tính tổng SoLuongTon theo idsp !"));
        }
    }

    // ToDo fin idspct and soluonton theo idsp, idmausac và idsize
    @GetMapping("/finby-idspct-soluongton")
    public ResponseEntity<?> finByIdSPCTAndSoLuongTon(@RequestParam("idsp") UUID idsp, @RequestParam("idmausac") UUID idmausac, @RequestParam("idsize") UUID idsize){
        try {
            return ResponseEntity.ok(sanPhamChiTietService.finByIdSPCTAndSoLuongTon(idsp, idmausac, idsize));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(("Lỗi finBy idsp and soluongton theo idsp, idmausac và idsize !"));
        }
    }
}
