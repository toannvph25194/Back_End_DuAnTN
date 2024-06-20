package com.example.be_duantn.controller.ban_hang_tai_quay_controller;

import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.HoaDonTaiQuayRequest;
import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.KhachHangDiaChiRequest;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.KhachHangTaiQuayRespon;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadDiaChiTaiQuayRespon;
import com.example.be_duantn.entity.DiaChi;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.service.ban_hang_tai_quay_service.HoaDonBanTaiQuayService;
import com.example.be_duantn.service.ban_hang_tai_quay_service.KhachHangBanTaiQuayService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth/khachhangbantaiquay/")
@RequiredArgsConstructor
public class KhachHangBanTaiQuayController {

    @Autowired
    KhachHangBanTaiQuayService service;

    @Autowired
    HoaDonBanTaiQuayService hoaDonBanTaiQuayService;

    private final JavaMailSender javaMailSender;

    @GetMapping("khachhang")
    public Page<LoadDiaChiTaiQuayRespon> getKhachHangByTrangThai(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return service.getKhachHangByTrangThai(page, size);
    }

    @PutMapping("updateKhachHang")
    public ResponseEntity<Map<String, String>> updateKhachHang(@RequestBody HoaDonTaiQuayRequest request) {
        int updatedRows = hoaDonBanTaiQuayService.updateKhachHang(request.getIdhoadon(), request.getIdkh(), request.getHovatenkh());
        if (updatedRows == 0) {
            return ResponseEntity.notFound().build();
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Cập nhật thành công");
        return ResponseEntity.ok(response);
    }


    //api Load Table
    @GetMapping("hienthikhtheoid")
    public ResponseEntity<?> hoadonchitiet(@RequestParam("Idkh") UUID idkh) {
        return ResponseEntity.ok(service.finByIdKh(idkh));
    }

    @GetMapping("khachhangtimkiem")
    public Page<LoadDiaChiTaiQuayRespon> getKhachHangBySDT(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return service.LocTenKHBanTaiQuay(keyword, page, size);
    }



    @PostMapping("addkhachhang")
    public ResponseEntity<KhachHang> addKhachHang(@RequestBody KhachHangDiaChiRequest khachHangRequest) {
        DiaChi diaChi = new DiaChi();
        diaChi.setDiachichitiet(khachHangRequest.getDiachichitiet());
        diaChi.setPhuongxa(khachHangRequest.getPhuongxa());
        diaChi.setQuanhuyen(khachHangRequest.getQuanhuyen());
        diaChi.setTinhthanh(khachHangRequest.getTinhthanh());
        diaChi.setQuocgia(khachHangRequest.getQuocgia());
        diaChi.setTrangthai(khachHangRequest.getTrangthaiDiaChi());


        KhachHang khachHang = new KhachHang();
        khachHang.setHovatenkh(khachHangRequest.getHovatenkh());
        khachHang.setTaikhoan(khachHangRequest.getTaikhoan());
        khachHang.setMatkhau(khachHangRequest.getMatkhau());
        khachHang.setTrangthai(khachHangRequest.getTrangthai());
        khachHang.setSodienthoai(khachHangRequest.getSodienthoai());
        khachHang.setEmail(khachHangRequest.getEmail());
        KhachHang savedKhachHang = service.addKhachHang(khachHang, diaChi);

        sendConfirmationEmail(khachHangRequest.getEmail(), khachHangRequest.getTaikhoan(), khachHangRequest.getMatkhau());

        return ResponseEntity.ok(savedKhachHang);


    }

    /**
     * Send mail khi đăng ký
     *
     * @param email
     * @param username
     * @param password
     */
    private void sendConfirmationEmail(String email, String username, String password) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(email);
            helper.setSubject("Chào mừng bạn đến với 2TH Shop");

            String htmlMsg = "<h1>Chào mừng bạn đến với <span style='color: rgb(255, 125, 26);'>2TH-Shop</span> của chúng tôi !</h1>\n" +
                    "<p>Xin chân thành cảm ơn bạn đã đăng ký <span style='color: rgb(255, 125, 26);'>2TH-Shop</span> của chúng tôi. Chúng tôi sẽ cung cấp cho bạn thông tin cập\n" +
                    "    nhật về tin tức và ưu đãi mới nhất.</p>\n" +
                    "<h3>Ưu điểm của <span style='color: rgb(255, 125, 26);'>2TH-Shop</span> :</h3>\n" +
                    "<ul>\n" +
                    "    <li>Thông tin mới nhất về sản phẩm và dịch vụ của chúng tôi</li>\n" +
                    "    <li>Ưu đãi đặc biệt và khuyến mãi hấp dẫn</li>\n" +
                    "</ul>\n" +
                    "<p><strong>Đừng bỏ lỡ!</strong> Để nhận các thông tin và ưu đãi đặc biệt từ chúng tôi, hãy nhấp vào nút bên dưới để\n" +
                    "    mua ngay sản phẩm :</p>\n" +
                    "<a href='http://127.0.0.1:5000/src/user/pages/Login.html'\n" +
                    "    style='padding: 10px 20px; background-color: rgb(255, 125, 26); color: #ffffff; text-decoration: none; border-radius: 5px;'>Trang web</a>" +
                    "<p><strong>Thông tin đăng nhập :</strong></p>" +
                    "<p>Username: <strong>" + username + "</strong></p>" +
                    "<p>Password: <strong>" + password + "</strong></p>";

            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Xử lý ngoại lệ nếu gửi email thất bại
            e.printStackTrace();
        }
    }




    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = service.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-phone")
    public ResponseEntity<Boolean> checkPhoneExists(@RequestParam String phone) {
        boolean exists = service.phoneExists(phone);
        return ResponseEntity.ok(exists);
    }

}
