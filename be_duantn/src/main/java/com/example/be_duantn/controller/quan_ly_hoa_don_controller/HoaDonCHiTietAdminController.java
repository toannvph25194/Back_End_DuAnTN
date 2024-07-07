package com.example.be_duantn.controller.quan_ly_hoa_don_controller;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.MessageGioHangCTRespon;
import com.example.be_duantn.service.quan_ly_hoa_don_service.Impl.HoaDonAdminServiceImpl;
import com.example.be_duantn.service.quan_ly_hoa_don_service.Impl.HoaDonChiTietAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/hoadonchitiet")
public class HoaDonCHiTietAdminController {
    @Autowired
    HoaDonChiTietAdminServiceImpl hoaDonChiTietAdminService;

    //api Load Table
    @GetMapping("/hienthihoadonchitiettheoid")
    public ResponseEntity<?> hoadonchitiet(@RequestParam("IdHD") UUID IdHD) {
        return ResponseEntity.ok(hoaDonChiTietAdminService.finByIdHDCT(IdHD));
    }

    // ToDo Load sản phẩm phân trang lên bán hàng tại quầy
    @GetMapping("/load")
    public ResponseEntity<?> LoadSPBanTaiQuay(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        try {
            return ResponseEntity.ok(hoaDonChiTietAdminService.LoadSPSuaHoaDon(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi load sản phẩm bán tại quầy !");
        }
    }

    // ToDo Loc sản phẩm phân trang lên bán hàng tại quầy
    @GetMapping("/loc-ten-sp")
    public ResponseEntity<?> LocTenSPBanTaiQuay(@RequestParam(defaultValue = "0", value = "page") Integer page, @RequestParam("tensp") String tensp) {
        try {
            return ResponseEntity.ok(hoaDonChiTietAdminService.LocTenSPBanTaiQuay(page, tensp));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi lọc sản phẩm bán tại quầy !");
        }
    }

    // ToDo Lọc sản phẩm phân trang theo nhiều tiêu chí lên bán hàng tại quầy
    @GetMapping("/loc-tieu-chi-sp")
    public ResponseEntity<?> LocSPNhieuTieuChiBanTaiQuay(@RequestParam(defaultValue = "0", value = "page") Integer page, @RequestParam("tenmausac") String tenmausac,
                                                         @RequestParam("tensize") String tensize, @RequestParam("tenchatlieu") String tenchatlieu, @RequestParam("tendanhmuc") String tendanhmuc,
                                                         @RequestParam("tenthuonghieu") String tenthuonghieu, @RequestParam("tenxuatxu") String tenxuatxu) {
        try {
            return ResponseEntity.ok(hoaDonChiTietAdminService.LocSPNhieuTieuChiBanTaiQuay(page, tenmausac, tensize, tenchatlieu, tendanhmuc, tenthuonghieu, tenxuatxu));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi lọc sản phẩm theo nhiều tiêu chí bán tại quầy !");
        }
    }

    // ToDo Add sản phẩm vào hdct
    @PostMapping("/add-san-pham")
    public ResponseEntity<?> addSPHDCT(@RequestParam("idhd") UUID idgh, @RequestParam("idspct") UUID idspct, @RequestParam("soluong") Integer soluong,
                                       @RequestParam(value = "dongiakhigiam", required = false) Double dongiakhigiam
            , Principal principal) {
        try {
            if (dongiakhigiam == null) {
                dongiakhigiam = null;
            }
            return ResponseEntity.ok(hoaDonChiTietAdminService.addSPHDCT(idgh, idspct, soluong, dongiakhigiam, principal));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageGioHangCTRespon("Lỗi không thêm được sp vào ghct !"));
        }
    }


    @PutMapping("/update-so-luong")
    public ResponseEntity<MessageGioHangCTRespon> updateSPGioHangCT(
            @RequestParam("idhdct") UUID idghct,
            @RequestParam("soluong") Integer soluong,
            @RequestParam("idhd") UUID idhd
            , Principal principal
    ) {
        try {
            MessageGioHangCTRespon response = hoaDonChiTietAdminService.updateHDCT(idghct, soluong, idhd, principal);
            return ResponseEntity.ok(response);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageGioHangCTRespon("Bạn không có quyền"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageGioHangCTRespon("Số lượng thêm vào lớn hơn số lượng tồn kho!"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageGioHangCTRespon("Không tìm thấy hoá đơn chi tiết!"));
        }
    }

    // ToDo Delete giỏ hàng chi tiết
    @DeleteMapping("/delete-san-pham")
    public ResponseEntity<?> deleteSPGioHangCT(@RequestParam("idhdct") UUID idhdct, @RequestParam("idhd") UUID idhd, Principal principal) {
        try {
            return ResponseEntity.ok(hoaDonChiTietAdminService.deleteHoaDonCT(idhdct,idhd, principal));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageGioHangCTRespon("Lỗi không thế xóa 1 sp trong ghct !"));
        }
    }
}
