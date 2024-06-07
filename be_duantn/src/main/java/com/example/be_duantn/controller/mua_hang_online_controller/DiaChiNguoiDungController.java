package com.example.be_duantn.controller.mua_hang_online_controller;

import com.example.be_duantn.dto.request.mua_hang_online_request.DiaChiTaiKhoanRequest;
import com.example.be_duantn.dto.request.mua_hang_online_request.TTTaiKhoanRequest;
import com.example.be_duantn.entity.DiaChi;
import com.example.be_duantn.service.mua_hang_online_service.Impl.DiaChiNguoiDungServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ol/check-out/dia-chi-nguoi-dung")
public class DiaChiNguoiDungController {

    @Autowired
    DiaChiNguoiDungServiceImpl ttNguoiDungService;

    // ToDo finby địa chỉ người dùng lên trang checkout
    @GetMapping("/load/dia-chi")
    public ResponseEntity<?> finByDiaChiNguoiDung(@RequestParam("idkh") UUID idkh){
        try {
            if (idkh != null) {
                return ResponseEntity.ok(ttNguoiDungService.finByDiaChiKhachHang(idkh));
            } else {
                return ResponseEntity.badRequest().body("K tìm thấy địa chỉ người dùng !");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi load thông tin người dùng !");
        }
    }

    // ToDo finby địa chỉ người dùng lên trang tài khoản
    @GetMapping("/fin-dia-chi")
    public ResponseEntity<?> finByDiaChiTaiKhoan(@RequestParam("idkh") UUID idkh){
        try {
            if (idkh != null) {
                return ResponseEntity.ok(ttNguoiDungService.finByDiaChiTaiKhoan(idkh));
            } else {
                return ResponseEntity.badRequest().body("K tìm thấy địa chỉ người dùng !");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi finby thông tin người dùng !");
        }
    }

    // ToDo add địa chỉ người dùng trang tài khoản
    @PostMapping ("/them-dia-chi")
    public ResponseEntity<?> ThemDiaChiTaiKhoan(@RequestBody DiaChiTaiKhoanRequest dcrequest){
        try {
            if (dcrequest.getIdkh() != null) {
                return ResponseEntity.ok(ttNguoiDungService.ThemDiaChiTaiKhoan(dcrequest));
            } else {
                return ResponseEntity.badRequest().body("K tìm thấy tài khoản người dùng !");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi add địa chỉ người dùng !");
        }
    }

    // ToDo update địa chỉ người dùng trang tài khoản
    @PutMapping ("/update-dia-chi")
    public ResponseEntity<?> UpdateDiaChiTaiKhoan(@RequestBody DiaChiTaiKhoanRequest dcrequest){
        try {
            if (dcrequest.getIddiachi() != null) {
                return ResponseEntity.ok(ttNguoiDungService.UpdateDiaChiTaiKhoan(dcrequest));
            } else {
                return ResponseEntity.badRequest().body("K tìm thấy địa chỉ người dùng !");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi update địa chỉ người dùng !");
        }
    }

    // ToDo update trạng thái địa chỉ người dùng trang tài khoản
    @PutMapping ("/update-trang-thai")
    public ResponseEntity<?> UpdateTrangThaiDC(@RequestParam("iddiachi") UUID iddc, @RequestParam("taikhoan") String taikhoan){
        try {
            if (iddc != null) {
                return ResponseEntity.ok(ttNguoiDungService.UpdateTrangThaiDC(iddc, taikhoan));
            } else {
                return ResponseEntity.badRequest().body("K tìm thấy địa chỉ người dùng !");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Lỗi update trạng thái địa chỉ người dùng !");
        }
    }
}
