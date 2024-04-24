package com.example.be_duantn.service.authentication_service.khach_hang_service_impl;

import com.example.be_duantn.dto.request.authentication_request.khachhang.KhachHangLoginRequest;
import com.example.be_duantn.dto.respon.authentication_respon.khachhang.KhachHangMessageResponse;
import com.example.be_duantn.dto.request.authentication_request.khachhang.KhachHangRegisterRequest;
import com.example.be_duantn.dto.respon.authentication_respon.khachhang.KhachHangLoginResponse;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.repository.authentication_repository.KhachHangRepository;
import com.example.be_duantn.service.authentication_service.khach_hang_service.KhachHangService;
import com.example.be_duantn.util.CodeGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangRepository khachhangRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private Map<String, String> codeMap = new HashMap<>();


    @Override
    public KhachHangLoginResponse login(KhachHangLoginRequest khachhangLoginRequest) {
        Optional<KhachHang> otkh = khachhangRepository.findByTaikhoan(khachhangLoginRequest.getTaikhoan());
        // Kiểm tra xem khách hàng có tồn tại không
        // So sánh mật khẩu đã giải mã từ cơ sở dữ liệu với mật khẩu nhập vào từ người dùng
        if (otkh.isPresent() && passwordEncoder.matches(khachhangLoginRequest.getMatkhau(), otkh.get().getMatkhau())) {
            KhachHang khachHang = otkh.get();
            return KhachHangLoginResponse.builder()
                    .idtk(khachHang.getIdkh())
                    .username(khachHang.getTaikhoan())
                    .message("Login thành công !")
                    .build();
        } else {
            return KhachHangLoginResponse.builder()
                    .message("Sai username hoặc password !")
                    .build();
        }
    }

    @Override
    public KhachHangMessageResponse register(KhachHangRegisterRequest khachhangRegisterRequest) {
        Optional<KhachHang> otkh = khachhangRepository.findByTaikhoan(khachhangRegisterRequest.getTaikhoan());

        if (otkh.isPresent()) {
            return KhachHangMessageResponse.builder().message("Tài khoản đã tồn tại !").build();
        }
        KhachHang kh = KhachHang.builder()
                .idkh(UUID.randomUUID())
                .taikhoan(khachhangRegisterRequest.getTaikhoan())
                .matkhau(passwordEncoder.encode(khachhangRegisterRequest.getMatkhau()))
                .email(khachhangRegisterRequest.getEmail())
                .build();

        try {
            KhachHang saveKH = khachhangRepository.save(kh);
            sendConfirmationEmail(khachhangRegisterRequest.getEmail(), khachhangRegisterRequest.getTaikhoan(), khachhangRegisterRequest.getMatkhau());
            return KhachHangMessageResponse.builder().message("Registered thành công !").build();
        } catch (Exception e) {
            return KhachHangMessageResponse.builder().message("Lỗi trong quá trình đăng ký !").build();
        }
    }

    @Override
    public KhachHangMessageResponse forgotPassword(String email) {
        Optional<KhachHang> optionalAccount = khachhangRepository.findByEmail(email);
        if (optionalAccount.isEmpty()) {
            return KhachHangMessageResponse.builder().message("Email không tồn tại 1").build();
        }
        sendConfirmEmailForgotPassWord(email, optionalAccount.get().getIdkh());
        return KhachHangMessageResponse.builder().message("Thay đổi mật khẩu thành công 1").build();
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

    /**
     * Send mail khi quên mật khẩu
     * @param email
     * @return
     */
    @Override
    public KhachHangMessageResponse sendConfirmEmailForgotPassWord(String email, UUID id) {
        String confirmationCode = confirmationCodeForgotPassWord();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Vui lòng click vào link dưới đây để đặt lại mật khẩu:");
        simpleMailMessage.setText("http://127.0.0.1:5505/src/customer/index-customer.html#/dat-lai-password/" + id);
        javaMailSender.send(simpleMailMessage);
        codeMap.put(confirmationCode, email);
        return KhachHangMessageResponse.builder().message("Send mã thành công !").build();
    }

    @Override
    public String confirmationCodeForgotPassWord() {
        return CodeGenerator.generateRandomCode(15);
    }


}
