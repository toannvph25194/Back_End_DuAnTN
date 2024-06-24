package com.example.be_duantn.controller.ban_hang_tai_quay_controller;

import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.HoaDonXacNhanRequest;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.service.ban_hang_tai_quay_service.Impl.HoaDonBanTaiQuayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/hoa-don/ban-tai-quay")
public class HoaDonBanTaiQuayConTroller {
    @Autowired
    HoaDonBanTaiQuayServiceImpl hoaDonBanTaiQuayService;

    // ToDo load hóa đơn bán tại quầy
    @GetMapping("/load")
    public ResponseEntity<?> LoadHoaDonBanTaiQuay() {
        try {
            return ResponseEntity.ok(hoaDonBanTaiQuayService.LoadHoaDonTaiQuay());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load hóa đơn bán tại quầy !");
        }
    }

    // ToDo tìm kiếm hóa đơn bán tại quầy
    @GetMapping("/tim-kiem-hoa-don")
    public ResponseEntity<?> TimKiemHoaDonBanTaiQuay(@RequestParam("mahoadon") String mahoadon) {
        try {
            return ResponseEntity.ok(hoaDonBanTaiQuayService.TimKiemHoaDonTaiQuay(mahoadon));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi tìm kiếm hóa đơn bán tại quầy !");
        }
    }

    // ToDo tạo hóa đơn bán tại quầy
    @PostMapping("/tao-hoa-don")
    public ResponseEntity<?> TaoHoaDonTaiQuay(Principal principal) {
        try {
            if (principal != null) {
                return ResponseEntity.ok(hoaDonBanTaiQuayService.TaoHoaDonTaiQuay(principal));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy thông tin nhân viên tạo hóa đơn bán tại quầy !");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi tạo hóa đơn bán tại quầy !");
        }
    }

    // ToDo hủy hóa đơn bán tại quầy
    @PutMapping("/huy-hoa-don")
    public ResponseEntity<?> HuyHoaDonTaiQuay(@RequestParam("idhoadon") UUID idhoadon, Principal principal) {
        try {
            if (principal != null) {
                return ResponseEntity.ok(hoaDonBanTaiQuayService.HuyHoaDonTaiQuay(idhoadon, principal));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy thông tin nhân viên hủy hóa đơn bán tại quầy !");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi hủy hóa đơn bán tại quầy !");
        }
    }

    // ToDo lấy tổng tiền khách đã trả
    @GetMapping("/laytienkhachtra")
    public ResponseEntity<?> LoadLayTienKhachTra(
            @RequestParam("id") UUID id
    ) {
        try {
            return ResponseEntity.ok(hoaDonBanTaiQuayService.laytienkhachtra(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load hóa đơn bán tại quầy !");
        }
    }

    @PutMapping("/updatehoanthanh")
    public ResponseEntity<?> updatenguoihoanthanh(
            @RequestParam("IDHD") UUID IDHD,
            @RequestParam(value = "Idgg", required = false) UUID Idgg,
            @RequestParam(value = "TienCuoiCung") Double TienCuoiCung,
            @RequestParam(value = "TienDuocGiam", required = false) Double TienDuocGiam
    ) {
        try {
            HoaDon updatedHoaDon = hoaDonBanTaiQuayService.updatehoanthanh(IDHD, Idgg, TienCuoiCung, TienDuocGiam);
            return ResponseEntity.ok(updatedHoaDon);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    @PutMapping("/updatexacnhan")
    public ResponseEntity<?> updateXacNhan(
            @RequestParam("IDHD") UUID IDHD,
            @RequestParam(value = "Idgg", required = false) UUID Idgg,
            @RequestParam("TienCuoiCung") Double TienCuoiCung,
            @RequestParam(value = "TienDuocGiam", required = false) Double TienDuocGiam,
            @RequestBody HoaDonXacNhanRequest hoaDonXacNhanRequest
    ) {
        try {
            // Validate input parameters if necessary
            // For example, check if IDHD is valid UUID, check if TienCuoiCung is non-negative, etc.

            HoaDon updatedHoaDon = hoaDonBanTaiQuayService.updateXacNhan(IDHD, Idgg, TienCuoiCung, TienDuocGiam, hoaDonXacNhanRequest);
            return ResponseEntity.ok(updatedHoaDon);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
        }
    }


}
