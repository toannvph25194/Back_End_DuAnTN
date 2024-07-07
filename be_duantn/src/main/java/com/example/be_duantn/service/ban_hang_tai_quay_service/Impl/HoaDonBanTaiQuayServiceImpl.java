package com.example.be_duantn.service.ban_hang_tai_quay_service.Impl;

import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.HoaDonTaiQuayRequest;

import com.example.be_duantn.dto.request.ban_hang_tai_quay_request.HoaDonXacNhanRequest;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LayTienKhachTraRespon;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadHoaDonRespon;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.MessageHuyHoaDon;
import com.example.be_duantn.entity.*;
import com.example.be_duantn.enums.TrangThaiDonHangEnums;
import com.example.be_duantn.repository.authentication_repository.NhanVienRepository;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.*;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.LichSuThaoTacRepository;
import com.example.be_duantn.service.ban_hang_tai_quay_service.HoaDonBanTaiQuayService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class HoaDonBanTaiQuayServiceImpl implements HoaDonBanTaiQuayService {

    @Autowired
    HoaDonBanTaiQuayRepository hoaDonBanTaiQuayRepository;

    @Autowired
    HoaDonCTBanTaiQuayRepository hoaDonCTBanTaiQuayRepository;

    @Autowired
    NhanVienBanTaiQuayRepository nhanVienBanTaiQuayRepository;

    @Autowired
    KhachHangBanTaiQuayRepository khachHangBanTaiQuayRepository;

    @Autowired
    GioHangBanTaiQuayRepository gioHangBanTaiQuayRepository;

    @Autowired
    GiohangChiTietBanTaiQuayRepository giohangChiTietBanTaiQuayRepository;

    @Autowired
    SanPhamCTBanTaiQuayRepository sanPhamCTBanTaiQuayRepository;

    @Autowired
    LichSuThaoTacRepository lichSuThaoTacRepository;
    @Autowired
    NhanVienRepository nhanVienRepository;


    @Autowired
    HinhThucThanhToanAdminBanTaiQuayRepository hinhThucThanhToanAdminBanTaiQuayRepository;
    private final JavaMailSender javaMailSender;


    @Override
    public List<LoadHoaDonRespon> LoadHoaDonTaiQuay() {
        return hoaDonBanTaiQuayRepository.LoadHoaDonTaiQuay();
    }

    @Override
    public HoaDonTaiQuayRequest TaoHoaDonTaiQuay(Principal principal) {
        // Lấy idnv từ principal
        String taikhoan = principal.getName();
        KhachHang khachle;
        if (taikhoan != null) {
            NhanVien finnv = nhanVienBanTaiQuayRepository.findByTaikhoan(taikhoan);
            // Kiểm tra xem đã có khách lẻ chưa
            KhachHang finkl = khachHangBanTaiQuayRepository.findByTaikhoan("khachle");
            if (finkl != null) {
                // Nếu có dùng luôn khách lẻ đó
                khachle = finkl;
            } else {
                // Nếu chưa có thì tạo khách lẻ
                khachle = new KhachHang();
                khachle.setIdkh(UUID.randomUUID());
                khachle.setHovatenkh("Khách Lẻ");
                khachle.setTaikhoan("khachle");
                khachle.setTrangthai(2);
                khachHangBanTaiQuayRepository.save(khachle);
            }
            // Tạo hóa đơn
            Random random = new Random();
            int randomNumber = random.nextInt(100000);
            String maHĐ = String.format("HDTQ%03d", randomNumber);
            HoaDon hd = new HoaDon();
            hd.setIdhoadon(UUID.randomUUID());
            hd.setNhanvien(finnv);
            hd.setKhachhang(khachle);
            hd.setMahoadon(maHĐ);
            hd.setNgaytao(new Date(System.currentTimeMillis()));
            hd.setTennguoinhan("Khách Lẻ");
            hd.setGhichu("Nhân viên tạo hóa đơn cho khách");
            hd.setLoaihoadon(2);
            hd.setTrangthai(TrangThaiDonHangEnums.CHO_XAC_NHAN.getValue());
            hoaDonBanTaiQuayRepository.save(hd);

            LichSuTaoTac lichSuTaoTac = new LichSuTaoTac();
            lichSuTaoTac.setIdhd(hd.getIdhoadon());
            lichSuTaoTac.setNguoithaotac(taikhoan);
            lichSuTaoTac.setGhichu("Tạo hoá đơn");
            lichSuTaoTac.setTrangthai(1);
            lichSuThaoTacRepository.save(lichSuTaoTac);

            return HoaDonTaiQuayRequest.builder().idhoadon(hd.getIdhoadon()).idkh(khachle.getIdkh()).mahoadon(hd.getMahoadon()).message("Tạo hóa đơn bán tại quầy thành công !").build();
        } else {
            return HoaDonTaiQuayRequest.builder().message("Tạo hóa đơn bán tại quầy thất bại. K tìm thấy nhân viên !").build();
        }
    }

    @Override
    public MessageHuyHoaDon HuyHoaDonTaiQuay(UUID idhoadon, Principal principal) {
        // Lấy thông tin hóa đơn
        String taikhoan = principal.getName();
        HoaDon finhoadon = hoaDonBanTaiQuayRepository.findById(idhoadon).orElse(null);
        if (finhoadon != null) {
            // Nếu ghct k null thì sẽ cập nhật
            List<HoaDonChiTiet> listhdct = hoaDonCTBanTaiQuayRepository.findByHoadon_Idhoadon(finhoadon.getIdhoadon());
            for (HoaDonChiTiet hdct : listhdct) {
                if (hdct != null) {
                    // Cộng lại số lượng tồn spct
                    SanPhamChiTiet spct = sanPhamCTBanTaiQuayRepository.findById(hdct.getSanphamchitiet().getIdspct()).get();
                    spct.setSoluongton(spct.getSoluongton() + hdct.getSoluong());
                    sanPhamCTBanTaiQuayRepository.save(spct);

                    // Cập nhật lại ghct
                    hdct.setSoluong(0);
                    hdct.setTrangthai(2);
                    hdct.setNgaycapnhat(new Date(System.currentTimeMillis()));
                    hdct.setGhichu("Hóa đơn chi tiết tại quầy đã bị hủy");
                    hoaDonCTBanTaiQuayRepository.save(hdct);
                }
            }
        } else {
            return MessageHuyHoaDon.builder().message("Không tìm thấy hóa đơn của khách !").build();
        }
        // Cập nhật lại trạng thái hóa đơn thành hủy hóa đơn
        finhoadon.setNgaycapnhat(new Date(System.currentTimeMillis()));
        finhoadon.setGhichu("Hóa đơn tại quầy đã bị hủy");
        finhoadon.setTrangthai(TrangThaiDonHangEnums.DA_HUY.getValue());
        hoaDonBanTaiQuayRepository.save(finhoadon);
        if (finhoadon.getTienkhachtra() != null && finhoadon.getTienkhachtra() > 0) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            HinhThucThanhToan HTTT = new HinhThucThanhToan();
            HTTT.setMagiaodich(generateRandomTransactionId()); // Generate a random transaction ID
            HTTT.setNgaytao(timestamp);
            HTTT.setNgaythanhtoan(timestamp);
            HTTT.setSotientra(finhoadon.getTienkhachtra());
            HTTT.setHinhthucthanhtoan(3); // Payment method code for cancelled payment
            HTTT.setGhichu("Hoàn tiền thừa cho khách");
            HTTT.setTrangthai(1);

            KhachHang khachHang = new KhachHang();
            khachHang.setIdkh(finhoadon.getKhachhang().getIdkh());
            HTTT.setKhachhang(khachHang);
            HTTT.setHoadon(finhoadon);
            Optional<NhanVien> taiKhoanOptional = nhanVienRepository.findByTaikhoan(taikhoan);
            if (taiKhoanOptional.isPresent()) {
                NhanVien taiKhoan = taiKhoanOptional.get();
                HTTT.setNhanvien(taiKhoan);
            } else {
                throw new IllegalArgumentException("Không tìm thấy nhân viên với tài khoản: " + taikhoan);
            }
            LichSuTaoTac lichSuTaoTac = new LichSuTaoTac();
            lichSuTaoTac.setIdhd(finhoadon.getIdhoadon());
            lichSuTaoTac.setNguoithaotac(taikhoan);
            lichSuTaoTac.setGhichu("Huỷ hoá đơn");
            lichSuTaoTac.setTrangthai(1);
            lichSuThaoTacRepository.save(lichSuTaoTac);

            HinhThucThanhToan savedHTTT = hinhThucThanhToanAdminBanTaiQuayRepository.save(HTTT);
        }

        return MessageHuyHoaDon.builder().message("Hủy hóa đơn thành công").build();
    }

    @Override
    public List<LoadHoaDonRespon> TimKiemHoaDonTaiQuay(String mahoadon) {
        return hoaDonBanTaiQuayRepository.TimKiemHoaDonTaiQuay(mahoadon);
    }


    ////Hùng làm update khách hàng ở hóa đơn
    @Transactional
    public int updateKhachHang(UUID idhoadon, UUID idkh, String hovatenkh) {
        return hoaDonBanTaiQuayRepository.updateKhachHangByIdHoaDon(idkh, idhoadon, hovatenkh);
    }


    @Override
    public LayTienKhachTraRespon laytienkhachtra(UUID id) {
        return hoaDonBanTaiQuayRepository.LayTienKhachTra(id);
    }

    @Override
    public HoaDon updatehoanthanh(UUID IDHD, UUID Idgg, Double TienCuoiCung, Double TienDuocGiam, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            String taikhoan = principal.getName();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Kiểm tra và gán các giá trị từ Optional vào đối tượng SanPham
            HoaDon hoaDon = hoaDonBanTaiQuayRepository.findById(IDHD)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + IDHD));

            // Log trạng thái hiện tại của hoaDon
            System.out.println("Trước khi cập nhật: " + hoaDon);

            if (hoaDon.getTienkhachtra() > TienCuoiCung) {
                HinhThucThanhToan HTTT = new HinhThucThanhToan();
                HTTT.setMagiaodich(generateRandomTransactionId()); // Sử dụng phương thức tạo mã giao dịch 8 chữ số
                HTTT.setNgaytao(timestamp);
                HTTT.setNgaythanhtoan(timestamp);
                HTTT.setSotientra(hoaDon.getTienkhachtra() - TienCuoiCung);
                HTTT.setHinhthucthanhtoan(3); // Mã 3 đại diện cho hình thức thanh toán đã bị hủy
                HTTT.setGhichu("Hoàn tiền thừa cho khách");
                HTTT.setTrangthai(1);
                KhachHang khachHang = new KhachHang();
                khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
                HTTT.setKhachhang(khachHang);
                HTTT.setHoadon(hoaDon);
                Optional<NhanVien> taiKhoanOptional = nhanVienRepository.findByTaikhoan(taikhoan);
                if (taiKhoanOptional.isPresent()) {
                    NhanVien taiKhoan = taiKhoanOptional.get();
                    HTTT.setNhanvien(taiKhoan);
                } else {
                    throw new IllegalArgumentException("Không tìm thấy nhân viên với tài khoản: " + taikhoan);
                }// Gán trực tiếp đối tượng hoaDon vào HTTT

                HinhThucThanhToan savedHTTT = hinhThucThanhToanAdminBanTaiQuayRepository.save(HTTT);
                // Log thông tin sau khi lưu
                System.out.println("Hình thức thanh toán đã được lưu thành công, ID: " + savedHTTT.getIdhttt());
                hoaDon.setTienkhachtra(TienCuoiCung);
            }

            // Cập nhật các thông tin của hóa đơn
            hoaDon.setTrangthai(5);
            hoaDon.setGhichu("Mua Hàng Thành công");
            hoaDon.setThanhtien(TienCuoiCung);

            // Kiểm tra và gán giá trị nếu không null
            if (TienDuocGiam != null) {
                hoaDon.setGiatrigiam(TienDuocGiam);
            } else {
                hoaDon.setGiatrigiam(0.0); // Hoặc giá trị mặc định khác nếu cần thiết
            }

            hoaDon.setNgaycapnhat(timestamp);
            hoaDon.setNgaythanhtoan(timestamp);

            // Kiểm tra và gán voucher nếu không null
            if (Idgg != null) {
                VouCher vouCher = new VouCher();
                vouCher.setIdvoucher(Idgg);
                hoaDon.setVoucher(vouCher);
            } else {
                hoaDon.setVoucher(null); // Hoặc giá trị mặc định nếu cần thiết
            }

            System.out.println("Sau khi cập nhật nhưng trước khi lưu: " + hoaDon);

            HoaDon savedHoaDon = hoaDonBanTaiQuayRepository.save(hoaDon);

            // Log trạng thái sau khi lưu
            System.out.println("Sau khi lưu: " + savedHoaDon);

            LichSuTaoTac lichSuTaoTac = new LichSuTaoTac();
            lichSuTaoTac.setIdhd(IDHD);
            lichSuTaoTac.setNguoithaotac(taikhoan);
            lichSuTaoTac.setGhichu("Xác nhận hoàn thành hoá đơn");
            lichSuTaoTac.setTrangthai(1);
            lichSuThaoTacRepository.save(lichSuTaoTac);

            return savedHoaDon;

        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HoaDon updateXacNhan(UUID IDHD, UUID Idgg, Double TienCuoiCung, Double TienDuocGiam, HoaDonXacNhanRequest hoaDonXacNhanRequest, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check user permissions
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            String taikhoan = principal.getName();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Fetch and validate the HoaDon entity
            HoaDon hoaDon = hoaDonBanTaiQuayRepository.findById(IDHD)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + IDHD));

            // Log the current state of hoaDon
            System.out.println("Trước khi cập nhật: " + hoaDon);

            if (hoaDon.getTienkhachtra() > TienCuoiCung) {
                // Create and save HinhThucThanhToan (HTTT) if there is excess payment
                HinhThucThanhToan HTTT = new HinhThucThanhToan();
                HTTT.setMagiaodich(generateRandomTransactionId()); // Generate a random transaction ID
                HTTT.setNgaytao(timestamp);
                HTTT.setNgaythanhtoan(timestamp);
                HTTT.setSotientra(hoaDon.getTienkhachtra() - TienCuoiCung);
                HTTT.setHinhthucthanhtoan(3); // Payment method code for cancelled payment
                HTTT.setGhichu("Hoàn tiền thừa cho khách");
                HTTT.setTrangthai(1);
                Optional<NhanVien> taiKhoanOptional = nhanVienRepository.findByTaikhoan(taikhoan);
                if (taiKhoanOptional.isPresent()) {
                    NhanVien taiKhoan = taiKhoanOptional.get();
                    HTTT.setNhanvien(taiKhoan);
                } else {
                    throw new IllegalArgumentException("Không tìm thấy nhân viên với tài khoản: " + taikhoan);
                }

                KhachHang khachHang = new KhachHang();
                khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
                HTTT.setKhachhang(khachHang);
                HTTT.setHoadon(hoaDon);

                HinhThucThanhToan savedHTTT = hinhThucThanhToanAdminBanTaiQuayRepository.save(HTTT);

                // Log the saved HTTT
                System.out.println("Hình thức thanh toán đã được lưu thành công, ID: " + savedHTTT.getIdhttt());

                hoaDon.setTienkhachtra(TienCuoiCung);
            }

            // Update HoaDon details
            hoaDon.setTrangthai(2);
            hoaDon.setGhichu("Mua Hàng Thành công");
            hoaDon.setThanhtien(TienCuoiCung);
            hoaDon.setTiengiaohang(hoaDonXacNhanRequest.getTiengiaohang());
            hoaDon.setTennguoinhan(hoaDonXacNhanRequest.getTennguoinhan());
            hoaDon.setSdtnguoinhan(hoaDonXacNhanRequest.getSdtnguoinhan());
            hoaDon.setEmailnguoinhan(hoaDonXacNhanRequest.getEmailnguoinhan());
            hoaDon.setDiachinhanhang(hoaDonXacNhanRequest.getDiachinhanhang());
            hoaDon.setGiatrigiam(TienDuocGiam != null ? TienDuocGiam : 0.0);
            hoaDon.setNgaycapnhat(timestamp);
            hoaDon.setNgaythanhtoan(timestamp);

            if (Idgg != null) {
                VouCher vouCher = new VouCher();
                vouCher.setIdvoucher(Idgg);
                hoaDon.setVoucher(vouCher);
            } else {
                hoaDon.setVoucher(null);
            }

            // Log the updated state before saving
            System.out.println("Sau khi cập nhật nhưng trước khi lưu: " + hoaDon);

            HoaDon savedHoaDon = hoaDonBanTaiQuayRepository.save(hoaDon);


            // Log the state after saving
            System.out.println("Sau khi lưu: " + savedHoaDon);

            // Send invoice email to customer
            List<HoaDonChiTiet> hoaDonChiTiets = hoaDonCTBanTaiQuayRepository.findByHoadon_Idhoadon(IDHD);
            String productList = generateProductList(hoaDonChiTiets);

            List<HinhThucThanhToan> hinhThucThanhToans = hinhThucThanhToanAdminBanTaiQuayRepository.findHinhThucThanhToanByHoadonIdhoadon(IDHD);
            String hinhthucthanhtoan = HinhThucThanhToan(hinhThucThanhToans);

            Double tongtienhang = hoaDon.getThanhtien() + hoaDon.getGiatrigiam();
            Double sotienphaitra = tongtienhang - hoaDon.getGiatrigiam();

            sendInvoiceEmail(hoaDon, hinhthucthanhtoan, productList, tongtienhang, sotienphaitra);
            // Log trạng thái sau khi lưu
            System.out.println("Sau khi lưu: " + savedHoaDon);
            LichSuTaoTac lichSuTaoTac = new LichSuTaoTac();
            lichSuTaoTac.setIdhd(IDHD);
            lichSuTaoTac.setNguoithaotac(taikhoan);
            lichSuTaoTac.setGhichu("Xác nhận hoá đơn");
            lichSuTaoTac.setTrangthai(1);
            lichSuThaoTacRepository.save(lichSuTaoTac);

            return savedHoaDon;

        } else {
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HoaDon layhoadontheoid(UUID id) {
        return hoaDonBanTaiQuayRepository.findHoaDonByIdhoadon(id);
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
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + "Đã xác nhận" + "</td>\n" +
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

    // Phương thức để tạo mã giao dịch 8 chữ số
    private String generateRandomTransactionId() {
        Random random = new Random();
        int transactionId = 10000000 + random.nextInt(90000000); // Tạo số ngẫu nhiên trong khoảng từ 10000000 đến 99999999
        return String.valueOf(transactionId);
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
