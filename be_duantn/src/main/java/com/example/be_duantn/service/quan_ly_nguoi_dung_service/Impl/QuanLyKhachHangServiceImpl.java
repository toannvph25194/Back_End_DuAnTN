package com.example.be_duantn.service.quan_ly_nguoi_dung_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request.KhachHangRequest;
import com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon.KhachHangRespon;
import com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon.MessageKhachHangRespon;
import com.example.be_duantn.entity.DiaChi;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.repository.quan_ly_nguoi_dung_repository.QuanLyDiaChiKhachHangRepository;
import com.example.be_duantn.repository.quan_ly_nguoi_dung_repository.QuanLyKhachHangRepository;
import com.example.be_duantn.service.quan_ly_nguoi_dung_service.QuanLyKhachHangService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuanLyKhachHangServiceImpl implements QuanLyKhachHangService {
    @Autowired
    QuanLyKhachHangRepository quanLyKhachHangRepository;

    @Autowired
    private QuanLyDiaChiKhachHangRepository diaChiRepository;

    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private Map<String, String> codeMap = new HashMap<>();

    @Override
    public Page<KhachHangRespon> LoadAllKhachHang(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return quanLyKhachHangRepository.LoadAllKhachHang(pageable);
    }

    @Override
    public MessageKhachHangRespon ThemMoiKhachHang(KhachHangRequest request) {
        // Ren mã khách hàng
        Random rand = new Random();
        int randomNumber = rand.nextInt(100000);
        String makhachhang = String.format("KH%03d", randomNumber);
        String matkhau = String.format("MK%05d", randomNumber);

        // Xử lý họ và tên khách hàng để tạo tài khoản
        String[] words = request.getHovatenkh().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String combinedName = firstWord + lastWord;
        // Loại bỏ dấu tiếng Việt và chuyển về chữ thường, không khoảng cách
        String taikhoan = Normalizer.normalize(combinedName, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        // Thêm 5 ký tự ngẫu nhiên vào sau tên tài khoản
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomSuffix = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            int index = rand.nextInt(characters.length());
            randomSuffix.append(characters.charAt(index));
        }
        taikhoan += randomSuffix.toString();

        KhachHang khachHang = new KhachHang();
        khachHang.setIdkh(UUID.randomUUID());
        khachHang.setMakh(makhachhang);
        khachHang.setHovatenkh(request.getHovatenkh());
        khachHang.setGioitinh(request.getGioitinh());
        khachHang.setNgaysinh(request.getNgaysinh());
        khachHang.setTaikhoan(taikhoan);
        khachHang.setMatkhau(passwordEncoder.encode(matkhau));
        khachHang.setSodienthoai(request.getSodienthoai());
        khachHang.setEmail(request.getEmail());
        khachHang.setImage(request.getImage());
        khachHang.setMota(request.getMota());
        khachHang.setTrangthai(request.getTrangthai());
        khachHang = quanLyKhachHangRepository.save(khachHang);
        sendConfirmationEmail(request.getEmail(), taikhoan, matkhau);

        Optional<DiaChi> diachikh = diaChiRepository.findByKhachhangAndTrangthai(khachHang, 1);
        if(diachikh.isPresent()){
            DiaChi diaChi = new DiaChi();
            diaChi.setDiachichitiet(request.getDiachichitiet());
            diaChi.setPhuongxa(request.getPhuongxa());
            diaChi.setQuanhuyen(request.getQuanhuyen());
            diaChi.setTinhthanh(request.getTinhthanh());
            diaChi.setQuocgia("Việt Nam");
            diaChi.setGhichu("Tạo thêm địa chỉ cho khách hàng");
            diaChi.setTrangthai(2);
            diaChi.setKhachhang(khachHang);
            diaChiRepository.save(diaChi);
            return MessageKhachHangRespon.builder().message("Thêm khách hàng, tạo thêm địa chỉ cho khách hàng thành công !").build();
        }else{
            // Tạo và lưu địa chỉ với trạng thái là 1
            DiaChi diaChi = new DiaChi();
            diaChi.setDiachichitiet(request.getDiachichitiet());
            diaChi.setPhuongxa(request.getPhuongxa());
            diaChi.setQuanhuyen(request.getQuanhuyen());
            diaChi.setTinhthanh(request.getTinhthanh());
            diaChi.setQuocgia("Việt Nam");
            diaChi.setGhichu("Tạo mới địa chỉ cho khách hàng");
            diaChi.setTrangthai(1);
            diaChi.setKhachhang(khachHang);
            diaChiRepository.save(diaChi);
            return MessageKhachHangRespon.builder().message("Thêm khách hàng, tạo mới địa chỉ cho khách hàng thành công !").build();
        }
    }

    @Override
    public MessageKhachHangRespon UpdateKhachHang(KhachHangRequest updatedRequest) {
        Optional<KhachHang> khfin = quanLyKhachHangRepository.findById(updatedRequest.getId());
        if (khfin.isPresent()) {
            KhachHang khachHang = khfin.get();

            // Cập nhật thông tin khách hàng
            khachHang.setImage(updatedRequest.getImage());
            khachHang.setHovatenkh(updatedRequest.getHovatenkh());
            khachHang.setGioitinh(updatedRequest.getGioitinh());
            khachHang.setEmail(updatedRequest.getEmail());
            khachHang.setSodienthoai(updatedRequest.getSodienthoai());
            khachHang.setNgaysinh(updatedRequest.getNgaysinh());
            khachHang.setMota(updatedRequest.getMota());
            khachHang.setTrangthai(updatedRequest.getTrangthai());
            quanLyKhachHangRepository.save(khachHang);

            // Tìm địa chỉ có trạng thái bằng 1
            Optional<DiaChi> diaChiOptional = diaChiRepository.findByKhachhangAndTrangthai(khachHang, 1);

            if (diaChiOptional.isPresent()) {
                DiaChi diaChi = diaChiOptional.get();

                // Cập nhật thông tin địa chỉ
                diaChi.setDiachichitiet(updatedRequest.getDiachichitiet());
                diaChi.setPhuongxa(updatedRequest.getPhuongxa());
                diaChi.setQuanhuyen(updatedRequest.getQuanhuyen());
                diaChi.setTinhthanh(updatedRequest.getTinhthanh());
                diaChi.setQuocgia("Việt Nam");
                diaChi.setGhichu("Cập nhật ghi chú khách hàng");
                diaChi.setNgaycapnhat(new Date(System.currentTimeMillis()));
                // Lưu lại thông tin đã cập nhật
                diaChiRepository.save(diaChi);
                return MessageKhachHangRespon.builder().message("Cập nhập địa chỉ cho khách hàng thành công !").build();
            } else {
                // Tạo và lưu địa chỉ với trạng thái là 1
                DiaChi diaChi = new DiaChi();
                diaChi.setKhachhang(khachHang);
                diaChi.setDiachichitiet(updatedRequest.getDiachichitiet());
                diaChi.setPhuongxa(updatedRequest.getPhuongxa());
                diaChi.setQuanhuyen(updatedRequest.getQuanhuyen());
                diaChi.setTinhthanh(updatedRequest.getTinhthanh());
                diaChi.setQuocgia("Việt Nam");
                diaChi.setGhichu("Cập nhật tạo mới địa chỉ cho khách hàng");
                diaChi.setTrangthai(1);
                diaChiRepository.save(diaChi);
                return MessageKhachHangRespon.builder().message("Cập nhật Tạo mới địa chỉ cho khách hàng thành công !").build();
            }
        } else {
            return MessageKhachHangRespon.builder().message("Không tìm thấy thông tin khách hàng !").build();
        }
    }

    @Override
    public MessageKhachHangRespon UpdateTrangThaiKhachHang(UUID id, Integer trangThai) {
        Optional<KhachHang> khfin = quanLyKhachHangRepository.findById(id);

        if (khfin.isPresent()) {
            KhachHang khachHang = khfin.get();
            khachHang.setTrangthai(trangThai);
            quanLyKhachHangRepository.save(khachHang);
            return MessageKhachHangRespon.builder().message("Cập nhật trạng thái khách hàng thành công !").build();
        } else {
            return MessageKhachHangRespon.builder().message("Không tìm thấy thông tin khách hàng !").build();
        }
    }

    @Override
    public Page<KhachHangRespon> LocKhachHangTheoNhieuTieuChi(Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        return quanLyKhachHangRepository.LocKhachHangTheoNhieuTieuChi(pageable, keyword);
    }

    @Override
    public Page<KhachHangRespon> LocKhachHangTheoTrangThai(Integer page, Integer trangthai) {
        Pageable pageable = PageRequest.of(page, 10);
        return quanLyKhachHangRepository.LocKhachHangTheoTrangThai(pageable, trangthai);
    }

    /**
     * Send mail khi đăng ký
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

}
