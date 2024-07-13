package com.example.be_duantn.service.quan_ly_hoa_don_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HinhThucThanhToanAdminRequest;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HinhThucThanhToanRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.NhanVienAdminRespon;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.HoaDonChiTiet;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.entity.LichSuHoaDon;
import com.example.be_duantn.entity.NhanVien;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.SanPhamRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HinhThucThanhToanAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HoaDonAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HoaDonChiTietAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.LichSuHoaDonRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.NhanVienAdminQuanLyHoaDonRepository;
import com.example.be_duantn.service.quan_ly_hoa_don_service.HinhThucThanhToanAdminService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class HinhThucThanhToanAdminAdminServiceImpl implements HinhThucThanhToanAdminService {
    @Autowired
    HinhThucThanhToanAdminRepository hinhThucThanhToanAdminRepository;

    @Autowired
    HoaDonAdminRepository hoaDonAdminRepository;
    @Autowired
    HoaDonChiTietAdminRepository hoaDonChiTietAdminRepository;

    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    LichSuHoaDonRepository lichHoaDonRepository;

    @Autowired
    NhanVienAdminQuanLyHoaDonRepository nhanVienRepository;
    private final JavaMailSender javaMailSender;


    @Override
    public List<HinhThucThanhToanRespon> finByIdHTTT(UUID IdHD) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return hinhThucThanhToanAdminRepository.finByidHTTT(IdHD);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HinhThucThanhToan updateHTTT(UUID IDHTT, HinhThucThanhToanAdminRequest hinhThucThanhToanAdminRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Kiểm tra và gán các giá trị từ Optional vào đối tượng SanPham
            HinhThucThanhToan hinhThucThanhToan = hinhThucThanhToanAdminRepository.findById(IDHTT)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + IDHTT));

            // Log trạng thái hiện tại của hoaDon
            System.out.println("Trước khi cập nhật: " + hinhThucThanhToan);

            // Cập nhật các thông tin của hóa đơn
            hinhThucThanhToan.setGhichu("Khách hàng đã thanh toán");
            hinhThucThanhToan.setNgaycapnhat(timestamp);
            if (hinhThucThanhToan.getNgaythanhtoan() == null) {
                hinhThucThanhToan.setNgaythanhtoan(timestamp);
            }

            // Log trạng thái sau khi cập nhật nhưng trước khi lưu
            System.out.println("Sau khi cập nhật nhưng trước khi lưu: " + hinhThucThanhToan);

            HinhThucThanhToan thucThanhToan = hinhThucThanhToanAdminRepository.save(hinhThucThanhToan); // Lưu hóa đơn đã cập nhật và trả về nó

            // Log trạng thái sau khi lưu
            System.out.println("Sau khi lưu: " + thucThanhToan);

            return thucThanhToan;

        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HinhThucThanhToan AddHTTTMoi(UUID idhd, HinhThucThanhToanAdminRequest hinhThucThanhToanAdminRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            HoaDon hoaDon = hoaDonAdminRepository.findById(idhd)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + idhd));

            if (hoaDon.getTienkhachtra() == null) {
                // Log thông tin lỗi
                System.out.println("Hóa đơn không có thông tin tiền khách trả, ID hóa đơn: " + idhd);
                return null;
            } else {
                HinhThucThanhToan HTTT = new HinhThucThanhToan();
                HTTT.setMagiaodich(generateRandomTransactionId()); // Sử dụng phương thức tạo mã giao dịch 8 chữ số
                HTTT.setNgaytao(timestamp);
                HTTT.setNgaythanhtoan(timestamp);
                HTTT.setSotientra(hoaDon.getTienkhachtra());
                HTTT.setHinhthucthanhtoan(2); // Mã 3 đại diện cho hình thức thanh toán đã bị hủy
                HTTT.setGhichu("Hoàn tiền thừa cho khách");
                HTTT.setTrangthai(3);
                KhachHang khachHang = new KhachHang();
                khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
                HTTT.setKhachhang(khachHang);
                HoaDon hoaDon1 = new HoaDon();
                hoaDon1.setIdhoadon(hoaDon.getIdhoadon());
                HTTT.setHoadon(hoaDon1);
                HinhThucThanhToan savedHTTT = hinhThucThanhToanAdminRepository.save(HTTT);
                // Log thông tin sau khi lưu
                System.out.println("Hình thức thanh toán đã được lưu thành công, ID: " + savedHTTT.getIdhttt());
                // Send invoice email to customer
                List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietAdminRepository.findByHoadon_Idhoadon(idhd);
                String productList = generateProductList(hoaDonChiTiets);

                List<HinhThucThanhToan> hinhThucThanhToans = hinhThucThanhToanAdminRepository.findHinhThucThanhToanByHoadonIdhoadon(idhd);
                String hinhthucthanhtoan = HinhThucThanhToan(hinhThucThanhToans);
                Double tongtienhang = hoaDon.getThanhtien() + hoaDon.getGiatrigiam();

                Double sotienphaitra = tongtienhang - hoaDon.getGiatrigiam();

                sendInvoiceEmail(hoaDon, hinhthucthanhtoan, productList, tongtienhang, sotienphaitra);
                return savedHTTT;
            }
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HinhThucThanhToan AddHTTTMoiKhiHoanThanh(UUID idhd, HinhThucThanhToanAdminRequest hinhThucThanhToanAdminRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            HoaDon hoaDon = hoaDonAdminRepository.findById(idhd)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + idhd));

            System.out.println("Hóa đơn hiện tại: " + hoaDon);

            HinhThucThanhToan result = null;
            if (hoaDon.getTienkhachtra() == null) {
                result = handleNoPayment(hoaDon, timestamp);
            } else if (hoaDon.getTienkhachtra() > hoaDon.getThanhtien()) {
                result = handleExcessPayment(hoaDon, timestamp);
            } else if (hoaDon.getTienkhachtra() < hoaDon.getThanhtien()) {
                result = handleInsufficientPayment(hoaDon, timestamp);
            } else {
                System.out.println("Khách hàng đã thanh toán đúng số tiền, không cần xử lý thêm.");
                return null;
            }

            // Gửi email hóa đơn cho khách hàng
            sendInvoiceEmailhoanthanh(hoaDon);

            return result;
        } else {
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    private HinhThucThanhToan handleNoPayment(HoaDon hoaDon, Timestamp timestamp) {
        List<HinhThucThanhToan> HTTTList = hoaDon.getHinhthucthanhtoan();

        for (HinhThucThanhToan HTTT : HTTTList) {
            if (HTTT != null) {
                updatePaymentDetails(HTTT, hoaDon.getThanhtien(), timestamp);
                hinhThucThanhToanAdminRepository.save(HTTT);
                System.out.println("Updated soluongton for spct ID " + HTTT.getIdhttt() + ": " + HTTT);
            } else {
                throw new IllegalArgumentException("Không tìm thấy sản phẩm chi tiết trong hóa đơn chi tiết!");
            }
        }
        return HTTTList.isEmpty() ? null : HTTTList.get(0);
    }

    private HinhThucThanhToan handleExcessPayment(HoaDon hoaDon, Timestamp timestamp) {
        HinhThucThanhToan HTTT = createPaymentDetails(hoaDon.getTienkhachtra() - hoaDon.getThanhtien(), timestamp, 2, "Hoàn tiền thừa cho khách", hoaDon, 3);
        HinhThucThanhToan savedHTTT = hinhThucThanhToanAdminRepository.save(HTTT);
        System.out.println("Hình thức thanh toán đã được lưu thành công, ID: " + savedHTTT.getIdhttt());
        return savedHTTT;
    }

    private HinhThucThanhToan handleInsufficientPayment(HoaDon hoaDon, Timestamp timestamp) {
        HinhThucThanhToan HTTT = createPaymentDetails(hoaDon.getThanhtien() - hoaDon.getTienkhachtra(), timestamp, 1, "Thu thêm tiền thiếu", hoaDon, 1);
        HinhThucThanhToan savedHTTT = hinhThucThanhToanAdminRepository.save(HTTT);
        System.out.println("Hình thức thanh toán đã được lưu thành công, ID: " + savedHTTT.getIdhttt());
        return savedHTTT;
    }

    private void updatePaymentDetails(HinhThucThanhToan HTTT, Double amount, Timestamp timestamp) {
        String generatedTransactionId = generateRandomTransactionId();
        HTTT.setMagiaodich(generatedTransactionId);
        HTTT.setGhichu("Khách hàng đã thanh toán");
        HTTT.setSotientra(amount);
        HTTT.setTrangthai(1);
        HTTT.setNgaycapnhat(timestamp);
        if (HTTT.getNgaythanhtoan() == null) {
            HTTT.setNgaythanhtoan(timestamp);
        }
        System.out.println("Sau khi cập nhật nhưng trước khi lưu: " + HTTT);
    }

    private HinhThucThanhToan createPaymentDetails(Double amount, Timestamp timestamp, int paymentMethod, String note, HoaDon hoaDon, int trangthai) {
        HinhThucThanhToan HTTT = new HinhThucThanhToan();
        HTTT.setMagiaodich(generateRandomTransactionId());
        HTTT.setNgaytao(timestamp);
        HTTT.setNgaythanhtoan(timestamp);
        HTTT.setSotientra(amount);
        HTTT.setHinhthucthanhtoan(paymentMethod);
        HTTT.setGhichu(note);
        HTTT.setTrangthai(trangthai);
        KhachHang khachHang = new KhachHang();
        khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
        HTTT.setKhachhang(khachHang);
        HoaDon hoaDon1 = new HoaDon();
        hoaDon1.setIdhoadon(hoaDon.getIdhoadon());
        HTTT.setHoadon(hoaDon1);
        return HTTT;
    }

    private void sendInvoiceEmailhoanthanh(HoaDon hoaDon) {
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietAdminRepository.findByHoadon_Idhoadon(hoaDon.getIdhoadon());
        String productList = generateProductList(hoaDonChiTiets);

        List<HinhThucThanhToan> hinhThucThanhToans = hinhThucThanhToanAdminRepository.findHinhThucThanhToanByHoadonIdhoadon(hoaDon.getIdhoadon());
        String hinhthucthanhtoan = HinhThucThanhToan(hinhThucThanhToans);
        Double giatrigiam = (hoaDon.getGiatrigiam() != null) ? hoaDon.getGiatrigiam() : 0;

        Double tongtienhang = hoaDon.getThanhtien() + giatrigiam;

        Double sotienphaitra = tongtienhang - giatrigiam;


        sendInvoiceEmailhoanthanh(hoaDon, hinhthucthanhtoan, productList, tongtienhang, sotienphaitra);
    }

    @Override
    public List<HinhThucThanhToan> updateNguoiXacNhan(UUID IDHD, UUID idnhanvien, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            HoaDon hoaDon = hoaDonAdminRepository.findById(IDHD)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + IDHD));

            List<HinhThucThanhToan> htttList = hoaDon.getHinhthucthanhtoan();
            for (HinhThucThanhToan hdct : htttList) {
                if (hdct != null) {
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setIdnv(idnhanvien);
                    hdct.setNhanvien(nhanVien);
                } else {
                    throw new IllegalArgumentException("Không tìm thấy hình thức thanh toán trong hóa đơn!");
                }
            }
            String taikhoan = principal.getName();
            KhachHang khachHang = new KhachHang();
            khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());

            LichSuHoaDon lichsuhoadon = new LichSuHoaDon();
            lichsuhoadon.setIdhd(hoaDon.getIdhoadon());
            lichsuhoadon.setNguoithaotac(taikhoan);
            lichsuhoadon.setGhichu("Chọn nhân viên xác nhận giao dịch");
            lichsuhoadon.setTrangthai(1);
            lichHoaDonRepository.save(lichsuhoadon);

            // Lưu danh sách các hình thức thanh toán đã cập nhật và trả về
            return hinhThucThanhToanAdminRepository.saveAll(htttList);

        } else {
            // Người dùng không có quyền, ném ra ngoại lệ AccessDeniedException
            throw new AccessDeniedException("Bạn không có quyền truy cập");
        }
    }

    @Override
    public List<NhanVienAdminRespon> loadtatcanhanvienTT1() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Integer trangthai = 1;
            return nhanVienRepository.findByTrangthai(trangthai);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    // Phương thức để tạo mã giao dịch 8 chữ số
    private String generateRandomTransactionId() {
        Random random = new Random();
        int transactionId = 10000000 + random.nextInt(90000000); // Tạo số ngẫu nhiên trong khoảng từ 10000000 đến 99999999
        return String.valueOf(transactionId);
    }

    private String HinhThucThanhToan(List<HinhThucThanhToan> hoaDonChiTiets) {
        StringBuilder hinhthucthanhtoan = new StringBuilder();
        int STT = 1;
        for (HinhThucThanhToan chiTiet : hoaDonChiTiets) {
            String hinhThucThanhToanText;
            if (chiTiet.getHinhthucthanhtoan() == 1) {
                hinhThucThanhToanText = "tiền mặt";
            } else if (chiTiet.getHinhthucthanhtoan() == 2) {
                hinhThucThanhToanText = "chuyển khoản";
            } else if (chiTiet.getHinhthucthanhtoan() == 3) {
                hinhThucThanhToanText = "hoàn tiền cho khách";
            } else {
                hinhThucThanhToanText = "khác"; // For any other values
            }

            hinhthucthanhtoan.append(
                    "<tr>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + STT + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + chiTiet.getMagiaodich() + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + chiTiet.getSotientra() + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + chiTiet.getNgaythanhtoan() + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hinhThucThanhToanText + "</td>\n" +
                            "</tr>\n"
            );
            STT++;
        }
        return hinhthucthanhtoan.toString();
    }

    private String generateProductList(List<HoaDonChiTiet> hoaDonChiTiets) {
        StringBuilder productList = new StringBuilder();
        int STT = 1;
        for (HoaDonChiTiet chiTiet : hoaDonChiTiets) {
            productList.append(
                    "<tr>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + STT + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + chiTiet.getSanphamchitiet().getSanpham().getTensp() + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + chiTiet.getSoluong() + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + chiTiet.getDongia() + " đ" + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + ((chiTiet.getDongiakhigiam() != null) ? chiTiet.getDongiakhigiam() : 0) + " đ" + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + ((chiTiet.getDongiakhigiam() != null) ? chiTiet.getDongiakhigiam() * chiTiet.getSoluong() : (chiTiet.getDongia() * chiTiet.getSoluong())) + " đ" + "</td>\n" +
                            "</tr>\n"
            );
            STT++;
        }
        return productList.toString();
    }

    private void sendInvoiceEmail(HoaDon hoaDon, String hinhthucthanhtoan, String productList, Double tongtienhang, Double sotienphaitra) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(hoaDon.getEmailnguoinhan());
            helper.setSubject("Thông Tin Đơn Hàng Của Quý Khách Hàng");

            String htmlMsg = "<body style=\"font-family: Arial, sans-serif;\">\n" +
                    "    <h1 style=\"color: rgb(255, 125, 26);\">Kính Gửi Quý Khách Hàng : " + hoaDon.getTennguoinhan() + "</h1>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Chúng tôi xin chân thành cảm ơn Quý khách đã đặt hàng tại : <strong  style=\"color: rgb(255, 125, 26);\">2TH-SHOP</strong></h6>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Thông tin chi tiết về đơn hàng của Quý khách như sau:</h6>\n" +
                    "    <table style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">\n" +
                    "        <thead>\n" +
                    "           <tr>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Mã Đơn Hàng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Khách Hàng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Số Điện Thoại</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Email</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Ngày Đặt Hàng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Địa chỉ giao hàng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Trạng Thái</th>\n" +
                    "           </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>\n" +
                    "           <tr>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getMahoadon() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getTennguoinhan() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getSdtnguoinhan() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getEmailnguoinhan() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getNgaytao() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getDiachinhanhang() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + "Đã huỷ" + "</td>\n" +
                    "           </tr>\n" +
                    "        </tbody>\n" +
                    "    </table>\n" +
                    "    <table style=\"width: 100%; border-collapse: collapse;\">\n" +
                    "        <thead>\n" +
                    "            <tr>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">STT</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Tên Sản Phẩm</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Số Lượng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Đơn Giá</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Giảm Giá</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Thành Tiền</th>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>\n" +
                    productList +
                    "        </tbody>\n" +
                    "    </table>\n" +
                    "    <br>\n" +
                    "    <table style=\"width: 100%; border-collapse: collapse;\">\n" +
                    "        <thead>\n" +
                    "            <tr>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">STT</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Mã giao dịch</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Số tiền trả</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Ngày thanh toán</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Hình thức</th>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>\n" +
                    hinhthucthanhtoan +
                    "        </tbody>\n" +
                    "    </table>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Tổng Tiền Hàng: " + tongtienhang + " đ</h6>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Giảm Giá: " + hoaDon.getGiatrigiam() + " đ</h6>\n" +
                    "<h6 style=\"color: black; font-size: 15px;\">Tiền ship: " + (hoaDon.getTiengiaohang() != null ? hoaDon.getTiengiaohang() + " đ" : "0 đ") + "</h6>\n"+
            "    <h6 style=\"color: black; font-size: 15px;\">Số Tiền Phải Trả: " + sotienphaitra + " đ</h6>\n" +
                    "</body>";

            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendInvoiceEmailhoanthanh(HoaDon hoaDon, String hinhthucthanhtoan, String productList, Double tongtienhang, Double sotienphaitra) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(hoaDon.getEmailnguoinhan());
            helper.setSubject("Thông Tin Đơn Hàng Của Quý Khách Hàng");

            String htmlMsg = "<body style=\"font-family: Arial, sans-serif;\">\n" +
                    "    <h1 style=\"color: rgb(255, 125, 26);\">Kính Gửi Quý Khách Hàng : " + hoaDon.getTennguoinhan() + "</h1>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Chúng tôi xin chân thành cảm ơn Quý khách đã đặt hàng tại : <strong  style=\"color: rgb(255, 125, 26);\">2TH-SHOP</strong></h6>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Thông tin chi tiết về đơn hàng của Quý khách như sau:</h6>\n" +
                    "    <table style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">\n" +
                    "        <thead>\n" +
                    "           <tr>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Mã Đơn Hàng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Khách Hàng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Số Điện Thoại</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Email</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Ngày Đặt Hàng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Địa chỉ giao hàng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Trạng Thái</th>\n" +
                    "           </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>\n" +
                    "           <tr>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getMahoadon() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getTennguoinhan() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getSdtnguoinhan() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getEmailnguoinhan() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getNgaytao() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getDiachinhanhang() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + "Hoàn thành" + "</td>\n" +
                    "           </tr>\n" +
                    "        </tbody>\n" +
                    "    </table>\n" +
                    "    <table style=\"width: 100%; border-collapse: collapse;\">\n" +
                    "        <thead>\n" +
                    "            <tr>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">STT</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Tên Sản Phẩm</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Số Lượng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Đơn Giá</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Giảm Giá</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Thành Tiền</th>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>\n" +
                    productList +
                    "        </tbody>\n" +
                    "    </table>\n" +
                    "    <br>\n" +
                    "    <table style=\"width: 100%; border-collapse: collapse;\">\n" +
                    "        <thead>\n" +
                    "            <tr>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">STT</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Mã giao dịch</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Số tiền trả</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Ngày thanh toán</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Hình thức</th>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>\n" +
                    hinhthucthanhtoan +
                    "        </tbody>\n" +
                    "    </table>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Tổng Tiền Hàng: " + tongtienhang + " đ</h6>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Giảm Giá: " + hoaDon.getGiatrigiam() + " đ</h6>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Tiền ship: " + hoaDon.getTiengiaohang() + " đ</h6>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Số Tiền Phải Trả: " + sotienphaitra + " đ</h6>\n" +
                    "</body>";

            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    private boolean hasPermission(Collection<? extends GrantedAuthority> authorities, String... requiredRoles) {
        // Kiểm tra xem người dùng có ít nhất một trong các quyền cần thiết hay không
        for (String requiredRole : requiredRoles) {
            if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals(requiredRole))) {
                return true;
            }
        }
        return false;
    }
}
