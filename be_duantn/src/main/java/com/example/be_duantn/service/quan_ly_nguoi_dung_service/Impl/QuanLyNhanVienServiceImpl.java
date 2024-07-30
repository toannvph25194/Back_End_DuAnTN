package com.example.be_duantn.service.quan_ly_nguoi_dung_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_nguoi_dung_request.NhanVienRequest;
import com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon.MessageNhanVienRespon;
import com.example.be_duantn.dto.respon.quan_ly_nguoi_dung_respon.NhanVienRespon;
import com.example.be_duantn.entity.ChucVu;
import com.example.be_duantn.entity.NhanVien;
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

    @Override
    public boolean emailExists(String email) {
        return taiKhoanRepository.existsByEmail(email);
    }

    @Override
    public boolean phoneExists(String phone) {
        return taiKhoanRepository.existsBySodienthoai(phone);
    }

    @Override
    public Page<NhanVienRespon> LoadAllNhanVien(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return taiKhoanRepository.LoadAllNhanVien(pageable);
    }

    @Override
    public NhanVienRespon FindNhanVienById(UUID id) {
        return taiKhoanRepository.FindByNhanVien(id);
    }

    @Override
    public Page<NhanVienRespon> LocNhanVienTieuChi(Integer page, String search) {
        Pageable pageable = PageRequest.of(page, 10);
        return taiKhoanRepository.LocNhanVienTieuChi(pageable, search);
    }

    @Override
    public Page<NhanVienRespon> LocNhanVienTrangThai(Integer page, Integer trangthai) {
        Pageable pageable = PageRequest.of(page, 10);
        return taiKhoanRepository.LocNhanVienTrangThai(pageable, trangthai);
    }

    @Override
    public MessageNhanVienRespon UpdateNhanVien(NhanVienRequest nhanVienRequest) {
        Optional<NhanVien> finnv = taiKhoanRepository.findById(nhanVienRequest.getId());
        if(finnv.isPresent()){
            NhanVien nv = finnv.get();
            nv.setEmail(nhanVienRequest.getEmail());
            nv.setHovatennv(nhanVienRequest.getHovatennv());
            nv.setGioitinh(nhanVienRequest.getGioitinh());
            nv.setNgaysinh(nhanVienRequest.getNgaysinh());
            nv.setSodienthoai(nhanVienRequest.getSodienthoai());
            nv.setImage(nhanVienRequest.getImage());
            nv.setMota(nhanVienRequest.getMota());
            nv.setDiachi(nhanVienRequest.getDiachi());
            taiKhoanRepository.save(nv);
            return MessageNhanVienRespon.builder().message("Cập nhật nhân viên thành công !").build();
        }

        return MessageNhanVienRespon.builder().message("Không tìm thấy nhân viên !").build();
    }

    @Override
    public MessageNhanVienRespon UpdateTrangThai(UUID id, Integer trangThai) {
        Optional<NhanVien> nvfin = taiKhoanRepository.findById(id);
        if (nvfin.isPresent()) {
            NhanVien nv = nvfin.get();
            nv.setTrangthai(trangThai);
            taiKhoanRepository.save(nv);
            return MessageNhanVienRespon.builder().message("Cập nhật trạng thái nhân viên thành công !").build();
        } else {
            return MessageNhanVienRespon.builder().message("Không tìm thấy thông tin nhân viên !").build();
        }
    }

    @Override
    public MessageNhanVienRespon ThemNhanVien(NhanVienRequest nhanVienRequest) {
        Optional<NhanVien> optionalPhatTu = taiKhoanRepository.findByTaikhoan(nhanVienRequest.getTaikhoan());

        if (optionalPhatTu.isPresent()) {
            return MessageNhanVienRespon.builder().message("Tài khoản nhân viên đã tồn tại !").build();
        }

        Optional<ChucVu> quyenHan = chucVuRepository.findByTenchucvu(nhanVienRequest.getChucvu());

        if (quyenHan.isEmpty()) {
            return MessageNhanVienRespon.builder().message("Quyền hạn không hợp lệ").build();
        }

        NhanVien nv = new NhanVien();
        nv.setIdnv(UUID.randomUUID());
        nv.setChucvu(quyenHan.get());
        nv.setManv(nhanVienRequest.getManv());
        nv.setEmail(nhanVienRequest.getEmail());
        nv.setHovatennv(nhanVienRequest.getHovatennv());
        nv.setGioitinh(nhanVienRequest.getGioitinh());
        nv.setTaikhoan(nhanVienRequest.getTaikhoan());
        nv.setMatkhau(passwordEncoder.encode(nhanVienRequest.getMatkhau()));
        nv.setNgaysinh(nhanVienRequest.getNgaysinh());
        nv.setSodienthoai(nhanVienRequest.getSodienthoai());
        nv.setImage(nhanVienRequest.getImage());
        nv.setMota(nhanVienRequest.getMota());
        nv.setDiachi(nhanVienRequest.getDiachi());
        nv.setTrangthai(nhanVienRequest.getTrangthai());


        try {
            taiKhoanRepository.save(nv);
            // Send confirmation email
            sendConfirmationEmail(nv.getEmail(), nhanVienRequest.getTaikhoan(), nhanVienRequest.getMatkhau());
            return MessageNhanVienRespon.builder().message("Thêm mới nhân viên thành công !").build();
        } catch (Exception e) {
            return MessageNhanVienRespon.builder().message("Lỗi thêm nhân viên !").build();
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
