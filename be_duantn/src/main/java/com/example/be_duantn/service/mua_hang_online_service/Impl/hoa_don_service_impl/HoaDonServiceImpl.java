package com.example.be_duantn.service.mua_hang_online_service.Impl.hoa_don_service_impl;

import com.example.be_duantn.dto.request.mua_hang_online_request.hoa_don_request.ThongTinHTTTRequest;
import com.example.be_duantn.dto.request.mua_hang_online_request.hoa_don_request.ThongTinThanhToanRequest;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.hoa_don_respon.MessageTTHoaDonLoginRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.hoa_don_respon.MessageTTHoaDonNotLoginRespon;
import com.example.be_duantn.entity.*;
import com.example.be_duantn.enums.TrangThaiDonHangEnums;
import com.example.be_duantn.repository.mua_hang_oneline_repository.VouCherRepository;
import com.example.be_duantn.repository.mua_hang_oneline_repository.hoa_don_repository.*;
import com.example.be_duantn.service.mua_hang_online_service.hoa_don_service.HoaDonService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    GioHangThanhToanRepository gioHangThanhToanRepository;
    @Autowired
    GioHangCTThanhToanRepository gioHangCTThanhToanRepository;
    @Autowired
    HoaDonThanhToanRepository hoaDonThanhToanRepository;
    @Autowired
    HoaDonCTThanhToanRepository hoaDonCTThanhToanRepository;
    @Autowired
    VouCherRepository vouCherRepository;
    @Autowired
    KhachHangThanhToanRepository khachHangThanhToanRepository;
    @Autowired
    HinhThucThanhToanRepository hinhThucThanhToanRepository;
    @Autowired
    DiaChiThanhToanRepository diaChiThanhToanRepository;
    private final JavaMailSender javaMailSender;

    @Override
    public MessageTTHoaDonLoginRespon ThanhToanLogin(ThongTinThanhToanRequest ttttrequest, UUID idkh) {

        // lấy thông tin voucher
        Optional<VouCher> voucher = vouCherRepository.findById(ttttrequest.getIdvoucher());

        // B1. kiểm tra khách hàng đã đăng nhập hay chưa
        KhachHang khachhang = khachHangThanhToanRepository.findById(idkh).orElse(null);
        if (khachhang != null) {

            // B2. Xử lý hóa đơn
            Random rand = new Random();
            int randomNumber = rand.nextInt(100000);
            String mahd = String.format("HD%03d", randomNumber);

            HoaDon hoadon = new HoaDon();
            hoadon.setIdhoadon(UUID.randomUUID());
            hoadon.setMahoadon(mahd);
            hoadon.setKhachhang(khachhang);
            // Nếu idvoucher là null thì k lưu
            if (voucher.isPresent()) {
                hoadon.setVoucher(voucher.get());
                voucher.get().setSoluongdung(voucher.get().getSoluongdung() + 1);
                vouCherRepository.save(voucher.get());
            }
            hoadon.setNgaytao(new Date(System.currentTimeMillis()));
            if (ttttrequest.getPhuongthucthanhtoan() == 2){
                hoadon.setNgaythanhtoan(new Date(System.currentTimeMillis()));
            }
            hoadon.setDiachinhanhang(ttttrequest.getDiachichitiet() + " " + ttttrequest.getPhuongxa() + " " + ttttrequest.getQuanhuyen() + " " + ttttrequest.getTinhthanh());
            hoadon.setTennguoinhan(ttttrequest.getHovatenkh());
            hoadon.setSdtnguoinhan(ttttrequest.getSodienthoai());
            hoadon.setEmailnguoinhan(ttttrequest.getEmail());
            hoadon.setGiatrigiam(ttttrequest.getGiatrigiam());
            if(ttttrequest.getPhuongthucthanhtoan() == 2){
                hoadon.setTienkhachtra(ttttrequest.getTienkhachtra());
            }
            hoadon.setThanhtien(ttttrequest.getThanhtien());
            hoadon.setLoaihoadon(1);
            hoadon.setTrangthai(TrangThaiDonHangEnums.CHO_XAC_NHAN.getValue());
            hoaDonThanhToanRepository.save(hoadon);

            // Xử lý hình thức thanh toán
            if (ttttrequest.getPhuongthucthanhtoan() == 1) {
                HinhThucThanhToan httt = new HinhThucThanhToan();
                httt.setIdhttt(UUID.randomUUID());
                httt.setHoadon(hoadon);
                httt.setKhachhang(khachhang);
                httt.setSotientra(ttttrequest.getThanhtien());
                httt.setNgaytao(new Date(System.currentTimeMillis()));
                httt.setHinhthucthanhtoan(ttttrequest.getPhuongthucthanhtoan());
                httt.setGhichu("Khách hàng chưa thanh toán");
                httt.setTrangthai(1);
                hinhThucThanhToanRepository.save(httt);
            }

            // B3. Xử lý hóa đơn chi tiết
            String productList = "";
            Integer STT = 1;
            for (UUID idghct : ttttrequest.getGiohangchitietlist()) {
                // tạo hóa đơn chi tiết cho từng sp trong ghct
                Optional<GioHangChiTiet> ghctlist = gioHangCTThanhToanRepository.findById(idghct);
                // Lấy thông tin sp người dùng đặt
                productList +=
                                "<tr>\n" +
                                "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + STT + "</td>\n" +
                                "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + ghctlist.get().getSanphamchitiet().getSanpham().getTensp() + "</td>\n" +
                                "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + ghctlist.get().getSoluong() + "</td>\n" +
                                "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + ghctlist.get().getDongia() + " đ" + "</td>\n" +
                                "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + ((ghctlist.get().getDongiakhigiam() != null) ? ghctlist.get().getDongiakhigiam() : 0) + " đ" + "</td>\n" +
                                "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + ((ghctlist.get().getDongiakhigiam() != null) ? ghctlist.get().getDongiakhigiam() * ghctlist.get().getSoluong() : (ghctlist.get().getDongia() * ghctlist.get().getSoluong())) + " đ" + "</td>\n" +
                                "</tr>\n";
                STT++;
                if (ghctlist.isPresent()) {
                    HoaDonChiTiet hdct = new HoaDonChiTiet();
                    hdct.setIdhdct(UUID.randomUUID());
                    hdct.setHoadon(hoadon);
                    hdct.setSanphamchitiet(ghctlist.get().getSanphamchitiet());
                    hdct.setSoluong(ghctlist.get().getSoluong());
                    hdct.setDongia(ghctlist.get().getDongia());
                    hdct.setDongiakhigiam(ghctlist.get().getDongiakhigiam());
                    hdct.setNgaytao(new Date(System.currentTimeMillis()));
                    hdct.setTrangthai(1);
                    hoaDonCTThanhToanRepository.save(hdct);
                } else {
                    return MessageTTHoaDonLoginRespon.builder().message("GHCT k tồn tại. Thanh toán thất bại !").build();
                }
            }

            // B4. Cập nhật trạng thái giỏ hàng thành 2 sau khi thanh toán
            for (UUID idghct : ttttrequest.getGiohangchitietlist()) {
                Optional<GioHangChiTiet> ghct = gioHangCTThanhToanRepository.findById(idghct);
                if (ghct.isPresent()) {
                    ghct.get().getGiohang().setTrangthai(2);
                    gioHangThanhToanRepository.save(ghct.get().getGiohang());
                }
            }

            // B5. Cập nhật số lượng sp trong ghct về 0 sau khi thanh toán
            for (UUID idghct : ttttrequest.getGiohangchitietlist()) {
                Optional<GioHangChiTiet> ghct = gioHangCTThanhToanRepository.findById(idghct);
                if (ghct.isPresent()) {
                    ghct.get().setSoluong(0);
                    gioHangCTThanhToanRepository.save(ghct.get());
                }
            }

            // B6. Sen mail hóa đơn cho khách hàng đã đặt hàng
            Double tongtienhang = hoadon.getThanhtien() + hoadon.getGiatrigiam();
            Double sotienphaitra = tongtienhang - hoadon.getGiatrigiam();
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            try {
                helper.setTo(hoadon.getEmailnguoinhan());
                helper.setSubject("Thông Tin Đơn Hàng Của Quý Khách Hàng");

                String htmlMsg = "<body style=\"font-family: Arial, sans-serif;\">\n" +
                        "    <h1 style=\"color: rgb(255, 125, 26);\">Kính Gửi Quý Khách Hàng : " + hoadon.getTennguoinhan() + "</h1>\n" +
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
                        "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Trạng Thái</th>\n" +
                        "           </tr>\n" +
                        "        </thead>\n" +
                        "        <tbody>\n" +
                        "           <tr>\n" +
                        "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoadon.getMahoadon() + "</td>\n" +
                        "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoadon.getTennguoinhan() + "</td>\n" +
                        "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoadon.getSdtnguoinhan() + "</td>\n" +
                        "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoadon.getEmailnguoinhan() + "</td>\n" +
                        "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoadon.getNgaytao() + "</td>\n" +
                        "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + "Chờ Xác Nhận" + "</td>\n" +
                        "           </tr>\n" +
                        "        </tbody>\n" +
                        "    </table>\n" +
                        "    <h6 style=\"color: black; font-size: 15px;\">Thông tin chi tiết về sản phẩm của Quý khách như sau:</h6>\n" +
                        "    <table style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">\n" +
                        "        <thead>\n" +
                        "            <tr>\n" +
                        "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">STT</th>\n" +
                        "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Tên Sản Phẩm</th>\n" +
                        "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Số Lượng</th>\n" +
                        "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Đơn Giá</th>\n" +
                        "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Đơn Giá Khi Giảm</th>\n" +
                        "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Thành Tiền</th>\n" +
                        "            </tr>\n" +
                        "        </thead>\n" +
                        "        <tbody>\n" +
                                          productList +
                        "        </tbody>\n" +
                        "    </table>\n" +
                        "\n" +
                        "    <ul>\n" +
                        "       <li><p>Tiền Ship : Sẽ được nhân viên gọi điện xác nhận</p></li>\n" +
                        "       <li><p>Tiền Giảm Giá Voucher : " + hoadon.getGiatrigiam() + " đ" + "</p></li>\n" +
                        "       <li><p>Tổng Tiền Hàng : " + tongtienhang + " đ" + "</p></li>\n" +
                        "       <li><p>Số Tiền Phải Trả : " + sotienphaitra + " đ" + "</p></li>\n" +
                        "       <li><p>Ngày Thanh Toán: " + ((hoadon.getNgaythanhtoan()) != null ? hoadon.getNgaythanhtoan() : "Chưa Thanh Toán") + "</p></li>\n" +
                        "       <li><p>Phương Thức Thanh Toán : " + ((ttttrequest.getPhuongthucthanhtoan()) == 1 ? "Thanh Toán Khi Nhận Hàng" : "Thanh Toán Chuyển Khoản") + "</p></li>\n" +
                        "    </ul>\n" +
                        "\n" +
                        "\n" +
                        "    <p><span style='color: rgb(255, 125, 26);'>2TH-SHOP</span> sẽ xác nhận đơn hàng của Quý Khách Hàng sớm nhất có thể và thông báo cho Quý khách khi đơn hàng đã được giao\n" +
                        "        cho dịch vụ vận chuyển.</p>\n" +
                        "    <p>Nếu có bất kỳ thắc mắc hoặc yêu cầu nào, vui lòng liên hệ với chúng tôi qua thông tin sau :</p>\n" +
                        "    <ul>\n" +
                        "       <li><p>Số Điện thoại : 0982544290</p></li>\n" +
                        "       <li><p>Email : 2thshoppoly@gmail.com</p></li>\n" +
                        "    </ul>\n" +
                        "    <p><span style='color: rgb(255, 125, 26);'>2TH-SHOP</span> Xin Chân Thành Cảm Ơn Quý khách Hàng Đã Tin Tưởng Và Lựa Chọn Sản Phẩm Của Chúng Tôi.</p>\n" +
                        "    <footer style=\"margin-top: 20px;\">Trân Trọng</footer>\n" +
                        "</body>";

                helper.setText(htmlMsg, true);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                // Xử lý ngoại lệ nếu gửi email thất bại
                e.printStackTrace();
            }

            return MessageTTHoaDonLoginRespon.builder().message("Thanh toán thành công !").idhoadon(hoadon.getIdhoadon()).build();
        }
        return MessageTTHoaDonLoginRespon.builder().message("Người dùng chưa đăng nhập !").build();
    }

    @Override
    public MessageTTHoaDonNotLoginRespon ThanhToanNotLogin(ThongTinThanhToanRequest ttttrequest) {

        // Lấy thông tin email hoặc sdt của khách hàng nếu có
        List<KhachHang> khList = khachHangThanhToanRepository.getKhachHangByEmailAndSdt(ttttrequest.getEmail(), ttttrequest.getSodienthoai());
        KhachHang khachhang;
        // lấy thông tin voucher
        Optional<VouCher> voucher = vouCherRepository.findById(ttttrequest.getIdvoucher());

        //Step1 : Xử lí khách hàng và địa chỉ
        if (!khList.isEmpty()) {
            // Nếu tài khoản khách hàng đã tồn tại, sử dụng tài khoản đó.
            khachhang = khList.get(0);
        } else {
            // Nếu không tìm thấy tài khoản, tạo tài khoản mới cho khách hàng.
            khachhang = new KhachHang();

            khachhang.setIdkh(UUID.randomUUID());
            khachhang.setEmail(ttttrequest.getEmail());
            khachhang.setHovatenkh(ttttrequest.getHovatenkh());
            khachhang.setSodienthoai(ttttrequest.getSodienthoai());
            khachhang.setTrangthai(0);
            khachHangThanhToanRepository.save(khachhang);

            // Tạo Địa Chỉ Cho Khách Hàng
            DiaChi diaChi = new DiaChi();

            diaChi.setIddiachi(UUID.randomUUID());
            diaChi.setDiachichitiet(ttttrequest.getDiachichitiet());
            diaChi.setQuocgia("Việt Nam");
            diaChi.setTinhthanh(ttttrequest.getTinhthanh());
            diaChi.setQuanhuyen(ttttrequest.getQuanhuyen());
            diaChi.setPhuongxa(ttttrequest.getPhuongxa());
            diaChi.setTrangthai(1);
            diaChi.setKhachhang(khachhang);
            diaChiThanhToanRepository.save(diaChi);
        }

        // B2. Xử lý hóa đơn
        Random rand = new Random();
        int randomNumber = rand.nextInt(100000);
        String mahd = String.format("HD%03d", randomNumber);

        HoaDon hoadon = new HoaDon();
        hoadon.setIdhoadon(UUID.randomUUID());
        hoadon.setMahoadon(mahd);
        hoadon.setKhachhang(khachhang);
        // Nếu idvoucher là null thì k lưu
        if (voucher.isPresent()) {
            hoadon.setVoucher(voucher.get());
            voucher.get().setSoluongdung(voucher.get().getSoluongdung() + 1);
            vouCherRepository.save(voucher.get());
        }
        hoadon.setNgaytao(new Date(System.currentTimeMillis()));
        if (ttttrequest.getPhuongthucthanhtoan() == 2){
            hoadon.setNgaythanhtoan(new Date(System.currentTimeMillis()));
        }
        hoadon.setDiachinhanhang(ttttrequest.getDiachichitiet() + " " + ttttrequest.getPhuongxa() + " " + ttttrequest.getQuanhuyen() + " " + ttttrequest.getTinhthanh());
        hoadon.setTennguoinhan(ttttrequest.getHovatenkh());
        hoadon.setSdtnguoinhan(ttttrequest.getSodienthoai());
        hoadon.setEmailnguoinhan(ttttrequest.getEmail());
        hoadon.setGiatrigiam(ttttrequest.getGiatrigiam());
        if(ttttrequest.getPhuongthucthanhtoan() == 2){
            hoadon.setTienkhachtra(ttttrequest.getTienkhachtra());
        }
        hoadon.setThanhtien(ttttrequest.getThanhtien());
        hoadon.setLoaihoadon(1);
        hoadon.setTrangthai(TrangThaiDonHangEnums.CHO_XAC_NHAN.getValue());
        hoaDonThanhToanRepository.save(hoadon);

        // Xử lý hình thức thanh toán
        if (ttttrequest.getPhuongthucthanhtoan() == 1) {
            HinhThucThanhToan httt = new HinhThucThanhToan();
            httt.setIdhttt(UUID.randomUUID());
            httt.setHoadon(hoadon);
            httt.setKhachhang(khachhang);
            httt.setSotientra(ttttrequest.getThanhtien());
            httt.setNgaytao(new Date(System.currentTimeMillis()));
            httt.setHinhthucthanhtoan(ttttrequest.getPhuongthucthanhtoan());
            httt.setGhichu("Khách hàng chưa thanh toán");
            httt.setTrangthai(1);
            hinhThucThanhToanRepository.save(httt);
        }

        // B3. Xử lý hóa đơn chi tiết
        String productList = "";
        Integer STT = 1;
        for (UUID idghct : ttttrequest.getGiohangchitietlist()) {
            // tạo hóa đơn chi tiết cho từng sp trong ghct
            Optional<GioHangChiTiet> ghctlist = gioHangCTThanhToanRepository.findById(idghct);
            // Lấy thông tin sp người dùng đặt
            productList +=
                    "<tr>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + STT + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + ghctlist.get().getSanphamchitiet().getSanpham().getTensp() + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + ghctlist.get().getSoluong() + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + ghctlist.get().getDongia() + " đ" + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + ((ghctlist.get().getDongiakhigiam() != null) ? ghctlist.get().getDongiakhigiam() : 0) + " đ" + "</td>\n" +
                            "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + ((ghctlist.get().getDongiakhigiam() != null) ? ghctlist.get().getDongiakhigiam() * ghctlist.get().getSoluong() : (ghctlist.get().getDongia() * ghctlist.get().getSoluong())) + " đ" + "</td>\n" +
                            "</tr>\n";
            STT++;
            if (ghctlist.isPresent()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setIdhdct(UUID.randomUUID());
                hdct.setHoadon(hoadon);
                hdct.setSanphamchitiet(ghctlist.get().getSanphamchitiet());
                hdct.setSoluong(ghctlist.get().getSoluong());
                hdct.setDongia(ghctlist.get().getDongia());
                hdct.setDongiakhigiam(ghctlist.get().getDongiakhigiam());
                hdct.setNgaytao(new Date(System.currentTimeMillis()));
                hdct.setTrangthai(1);
                hoaDonCTThanhToanRepository.save(hdct);
            } else {
                return MessageTTHoaDonNotLoginRespon.builder().message("GHCT k tồn tại. Thanh toán thất bại !").build();
            }
        }

        // B4. Cập nhật trạng thái giỏ hàng thành 2 sau khi thanh toán
        for (UUID idghct : ttttrequest.getGiohangchitietlist()) {
            Optional<GioHangChiTiet> ghct = gioHangCTThanhToanRepository.findById(idghct);
            if (ghct.isPresent()) {
                ghct.get().getGiohang().setTrangthai(2);
                gioHangThanhToanRepository.save(ghct.get().getGiohang());
            }
        }

        // B5. Cập nhật số lượng sp trong ghct về 0 sau khi thanh toán
        for (UUID idghct : ttttrequest.getGiohangchitietlist()) {
            Optional<GioHangChiTiet> ghct = gioHangCTThanhToanRepository.findById(idghct);
            if (ghct.isPresent()) {
                ghct.get().setSoluong(0);
                gioHangCTThanhToanRepository.save(ghct.get());
            }
        }

        // B6. Sen mail hóa đơn cho khách hàng đã đặt hàng

        Double tongtienhang = hoadon.getThanhtien() + hoadon.getGiatrigiam();
        Double sotienphaitra = tongtienhang - hoadon.getGiatrigiam();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(hoadon.getEmailnguoinhan());
            helper.setSubject("Thông Tin Đơn Hàng Của Quý Khách Hàng");

            String htmlMsg = "<body style=\"font-family: Arial, sans-serif;\">\n" +
                    "    <h1 style=\"color: rgb(255, 125, 26);\">Kính Gửi Quý Khách Hàng : " + hoadon.getTennguoinhan() + "</h1>\n" +
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
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Trạng Thái</th>\n" +
                    "           </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>\n" +
                    "           <tr>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoadon.getMahoadon() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoadon.getTennguoinhan() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoadon.getSdtnguoinhan() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoadon.getEmailnguoinhan() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoadon.getNgaytao() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + "Chờ Xác Nhận" + "</td>\n" +
                    "           </tr>\n" +
                    "        </tbody>\n" +
                    "    </table>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Thông tin chi tiết về sản phẩm của Quý khách như sau:</h6>\n" +
                    "    <table style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">\n" +
                    "        <thead>\n" +
                    "            <tr>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">STT</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Tên Sản Phẩm</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Số Lượng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Đơn Giá</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Đơn Giá Khi Giảm</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Thành Tiền</th>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>\n" +
                    productList +
                    "        </tbody>\n" +
                    "    </table>\n" +
                    "\n" +
                    "    <ul>\n" +
                    "       <li><p>Tiền Ship : Sẽ được nhân viên gọi điện xác nhận</p></li>\n" +
                    "       <li><p>Tiền Giảm Giá Voucher : " + hoadon.getGiatrigiam() + " đ" + "</p></li>\n" +
                    "       <li><p>Tổng Tiền Hàng : " + tongtienhang + " đ" + "</p></li>\n" +
                    "       <li><p>Số Tiền Phải Trả : " + sotienphaitra + " đ" + "</p></li>\n" +
                    "       <li><p>Ngày Thanh Toán: " + ((hoadon.getNgaythanhtoan()) != null ? hoadon.getNgaythanhtoan() : "Chưa Thanh Toán") + "</p></li>\n" +
                    "       <li><p>Phương Thức Thanh Toán : " + ((ttttrequest.getPhuongthucthanhtoan()) == 1 ? "Thanh Toán Khi Nhận Hàng" : "Thanh Toán Chuyển Khoản") + "</p></li>\n" +
                    "    </ul>\n" +
                    "\n" +
                    "\n" +
                    "    <p><span style='color: rgb(255, 125, 26);'>2TH-SHOP</span> sẽ xác nhận đơn hàng của Quý Khách Hàng sớm nhất có thể và thông báo cho Quý khách khi đơn hàng đã được giao\n" +
                    "        cho dịch vụ vận chuyển.</p>\n" +
                    "    <p>Nếu có bất kỳ thắc mắc hoặc yêu cầu nào, vui lòng liên hệ với chúng tôi qua thông tin sau :</p>\n" +
                    "    <ul>\n" +
                    "       <li><p>Số Điện thoại : 0982544290</p></li>\n" +
                    "       <li><p>Email : 2thshoppoly@gmail.com</p></li>\n" +
                    "    </ul>\n" +
                    "    <p><span style='color: rgb(255, 125, 26);'>2TH-SHOP</span> Xin Chân Thành Cảm Ơn Quý khách Hàng Đã Tin Tưởng Và Lựa Chọn Sản Phẩm Của Chúng Tôi.</p>\n" +
                    "    <footer style=\"margin-top: 20px;\">Trân Trọng</footer>\n" +
                    "</body>";

            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Xử lý ngoại lệ nếu gửi email thất bại
            e.printStackTrace();
        }
        return MessageTTHoaDonNotLoginRespon.builder().message("Thanh toán thành công !").idhoadon(hoadon.getIdhoadon()).idkhdataoandkhmoi(khachhang.getIdkh()).build();
    }

    @Override
    public HinhThucThanhToan hinhThucTT(UUID idhoadon, Double sotientra, String magiaodinh, UUID idkh) {
        HoaDon hoaDon = hoaDonThanhToanRepository.findById(idhoadon).get();
        KhachHang kh = khachHangThanhToanRepository.findById(idkh).orElse(null);

        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setIdhttt(UUID.randomUUID());
        hinhThucThanhToan.setNgaythanhtoan(new Date(System.currentTimeMillis()));
        hinhThucThanhToan.setSotientra(sotientra);
        hinhThucThanhToan.setHinhthucthanhtoan(2);
        hinhThucThanhToan.setMagiaodich(magiaodinh);
        hinhThucThanhToan.setHoadon(hoaDon);
        hinhThucThanhToan.setKhachhang(kh);
        hinhThucThanhToan.setGhichu("Khách hàng đã thanh toán");
        hinhThucThanhToan.setTrangthai(2);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);
        return hinhThucThanhToan;
    }
}
