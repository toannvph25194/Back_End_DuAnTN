package com.example.be_duantn.service.quan_ly_nguoi_dung_service.Impl;

import com.example.be_duantn.dto.request.authentication_request.nhanvien.NhanVienRegisterRequest;
import com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request.NhanVienUpdateRequest;
import com.example.be_duantn.dto.respon.authentication_respon.nhanvien.NhanVienMessageResponse;
import com.example.be_duantn.entity.ChucVu;
import com.example.be_duantn.entity.NhanVien;
import com.example.be_duantn.jwt.nhan_vien_jwt.NhanVienCustomDetails;
import com.example.be_duantn.jwt.nhan_vien_jwt.NhanVienJwtService;
import com.example.be_duantn.repository.authentication_repository.ChucVuRepository;
import com.example.be_duantn.repository.quan_ly_nguoi_dung_repository.QuanLyNhanVienRepository;
import com.example.be_duantn.service.quan_ly_nguoi_dung_service.QuanLyNhanVienService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuanLyNhanVienServiceImpl implements QuanLyNhanVienService {


    private final QuanLyNhanVienRepository taiKhoanRepository;

    private final JavaMailSender javaMailSender;

    private final ChucVuRepository chucVuRepository;

    private final PasswordEncoder passwordEncoder;

    private final NhanVienJwtService nhanVienJwtService;


    @Override
    public Page<NhanVien> getAllNhanVien(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // Page index bắt đầu từ 0
        return taiKhoanRepository.findAll(pageable);
    }

    @Override
    public NhanVien updateStatus(UUID id, Integer newStatus) {
        return taiKhoanRepository.findById(id).map(nhanVien -> {
            nhanVien.setTrangthai(newStatus);
            return taiKhoanRepository.save(nhanVien);
        }).orElse(null);
    }

    @Override
    public boolean emailExists(String email) {
        return taiKhoanRepository.existsByEmail(email);
    }

    @Override
    public boolean phoneExists(String phone) {
        return taiKhoanRepository.existsBySodienthoai(phone);
    }

    @Override
    public NhanVien updateNhanVien(NhanVienUpdateRequest nhanVienUpdate, UUID id) {
        NhanVien existingNhanVien = taiKhoanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NhanVien not found with id " + id));
        existingNhanVien.setHovatennv(nhanVienUpdate.getHovatennv());
        existingNhanVien.setGioitinh(nhanVienUpdate.getGioitinh());
        existingNhanVien.setNgaysinh(nhanVienUpdate.getNgaysinh());
        existingNhanVien.setSodienthoai(nhanVienUpdate.getSodienthoai());
        existingNhanVien.setEmail(nhanVienUpdate.getEmail());
        existingNhanVien.setImage(nhanVienUpdate.getImage());
        existingNhanVien.setMota(nhanVienUpdate.getMota());
        existingNhanVien.setDiachi(nhanVienUpdate.getDiachi());
        existingNhanVien.setTrangthai(nhanVienUpdate.getTrangthai());

        return taiKhoanRepository.save(existingNhanVien);
    }

    @Override
    public NhanVien getNhanVienById(UUID id) {
        return taiKhoanRepository.findById(id).orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
    }

    @Override
    public Page<NhanVien> searchNhanVien(String search, Integer trangthai, Pageable pageable) {
        return taiKhoanRepository.searchNhanVien(search, trangthai, pageable);
    }

    @Override
    public NhanVienMessageResponse themNhanVien(NhanVienRegisterRequest nhanVienRegisterRequest) {
        Optional<NhanVien> optionalPhatTu = taiKhoanRepository.findByTaikhoan(nhanVienRegisterRequest.getTaikhoan());

        if (optionalPhatTu.isPresent()) {
            return NhanVienMessageResponse.builder().message("Tài khoản đã tồn tại").build();
        }

        Optional<ChucVu> quyenHan = chucVuRepository.findByTenchucvu(nhanVienRegisterRequest.getChucvu());

        if (quyenHan.isEmpty()) {
            return NhanVienMessageResponse.builder().message("Quyền hạn không hợp lệ").build();
        }

        NhanVien user = NhanVien.builder()
                .idnv(UUID.randomUUID())
                .taikhoan(nhanVienRegisterRequest.getTaikhoan())
                .matkhau(passwordEncoder.encode(nhanVienRegisterRequest.getMatkhau()))
                .email(nhanVienRegisterRequest.getEmail())
                .trangthai(nhanVienRegisterRequest.getTrangthai())
                .sodienthoai(nhanVienRegisterRequest.getSodienthoai())
                .manv(nhanVienRegisterRequest.getManv())
                .ngaysinh(nhanVienRegisterRequest.getNgaysinh())
                .gioitinh(nhanVienRegisterRequest.getGioitinh())
                .hovatennv(nhanVienRegisterRequest.getHovatennv())
                .diachi(nhanVienRegisterRequest.getDiachi())
                .image(nhanVienRegisterRequest.getImage())
                .mota(nhanVienRegisterRequest.getMota())
                .chucvu(quyenHan.get())
                .build();

        try {
            NhanVien savedUser = taiKhoanRepository.save(user);
            String jwtToken = nhanVienJwtService.generateToken(new NhanVienCustomDetails(savedUser));
            // Send confirmation email
            sendConfirmationEmail(savedUser.getEmail(), savedUser.getTaikhoan(), nhanVienRegisterRequest.getMatkhau());
            return NhanVienMessageResponse.builder().message("Registered Successfully").build();
        } catch (Exception e) {
            return NhanVienMessageResponse.builder().message("Lỗi trong quá trình đăng ký").build();
        }
    }

    private void sendConfirmationEmail(String email, String username, String password) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(email);
            helper.setSubject("Chào mừng bạn đến với 2TH Shop");

            String htmlMsg = "<h1>Chào mừng bạn trở thành nhân viên của <span style='color: rgb(255, 125, 26);'>2TH-Shop</span> chúng tôi !</h1>\n" +
                    "<p>Xin chân thành cảm ơn bạn đã đăng ký để trở thành<span style='color: rgb(255, 125, 26);'>2TH-Shop</span> nhân viên của chúng tôi. Chúng tôi sẽ cung cấp cho bạn thông tin cập\n" +
                    "    nhật về tin tức và quy định mới nhất.</p>\n" +
                    "<ul>\n" +
                    "    Đăng nhập để bắt đầu làm việc :</p>\n" +
                    "<a href='http://127.0.0.1:5000/src/admin/index_admin.html#/login'\n" +
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
