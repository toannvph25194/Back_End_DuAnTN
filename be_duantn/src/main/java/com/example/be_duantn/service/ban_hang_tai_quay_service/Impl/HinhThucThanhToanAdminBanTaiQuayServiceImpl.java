package com.example.be_duantn.service.ban_hang_tai_quay_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HinhThucThanhToanAdminRequest;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.HinhThucThanhToanBanTaiQuayRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HinhThucThanhToanRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.NhanVienAdminRespon;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.entity.NhanVien;
import com.example.be_duantn.repository.ban_hang_tai_quay_repository.HinhThucThanhToanAdminBanTaiQuayRepository;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.SanPhamRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HinhThucThanhToanAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HoaDonAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.NhanVienAdminQuanLyHoaDonRepository;
import com.example.be_duantn.service.ban_hang_tai_quay_service.HinhThucThanhToanAdminBanTaiQuayService;
import com.example.be_duantn.service.quan_ly_hoa_don_service.HinhThucThanhToanAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class HinhThucThanhToanAdminBanTaiQuayServiceImpl implements HinhThucThanhToanAdminBanTaiQuayService {
    @Autowired
    HinhThucThanhToanAdminBanTaiQuayRepository hinhThucThanhToanAdminRepository;

    @Autowired
    HoaDonAdminRepository hoaDonAdminRepository;

    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    NhanVienAdminQuanLyHoaDonRepository nhanVienRepository;

    @Override
    public List<HinhThucThanhToanBanTaiQuayRespon> finByIdHTTT(UUID IdHD) {
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
    public HinhThucThanhToan AddHTTTMoiKhiThanhToanTienMat(UUID idhd, Double TienCuoiCung, Double TienKhachDua, String taikhoan) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            HoaDon hoaDon = hoaDonAdminRepository.findById(idhd)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + idhd));

            // Log trạng thái hiện tại của hoaDon
            System.out.println("Hóa đơn hiện tại: " + hoaDon);

            // tạo mới hình thức thanh toán
            HinhThucThanhToan HTTT = new HinhThucThanhToan();
            HTTT.setMagiaodich(generateRandomTransactionId());
            HTTT.setNgaytao(timestamp);
            HTTT.setNgaythanhtoan(timestamp);

            Double tienthua = 0.0;
            Double tienkhachtra = hoaDon.getTienkhachtra() != null ? hoaDon.getTienkhachtra() : 0.0;
            Double tiencantra = TienCuoiCung - tienkhachtra;

            if (TienKhachDua > tiencantra) {
                HTTT.setSotientra(tiencantra);
                tienthua = TienKhachDua - TienCuoiCung;
            } else {
                HTTT.setSotientra(TienKhachDua);
            }

            HTTT.setHinhthucthanhtoan(1); // Mã 1 đại diện cho hình thức thanh toán thêm
            HTTT.setGhichu("Thanh toán thành công");
            HTTT.setTrangthai(1);

            KhachHang khachHang = hoaDon.getKhachhang();
            if (khachHang != null) {
                HTTT.setKhachhang(khachHang);
            } else {
                throw new IllegalArgumentException("Không tìm thấy khách hàng cho hóa đơn với ID: " + idhd);
            }

            HTTT.setHoadon(hoaDon);

            Optional<NhanVien> taiKhoanOptional = nhanVienRepository.findByTaikhoan(taikhoan);
            if (taiKhoanOptional.isPresent()) {
                NhanVien taiKhoan = taiKhoanOptional.get();
                HTTT.setNhanvien(taiKhoan);
            } else {
                throw new IllegalArgumentException("Không tìm thấy nhân viên với tài khoản: " + taikhoan);
            }

            HinhThucThanhToan savedHTTT = hinhThucThanhToanAdminRepository.save(HTTT);

            // Log thông tin sau khi lưu
            System.out.println("Hình thức thanh toán đã được lưu thành công, ID: " + savedHTTT.getIdhttt());

            // Update hoaDon with tienthua
            hoaDon.setTienthua(tienthua);

            if (TienKhachDua > tiencantra) {
                hoaDon.setTienkhachtra(tienkhachtra + tiencantra);
            } else {
                hoaDon.setTienkhachtra(tienkhachtra + TienKhachDua);
            }

            hoaDonAdminRepository.save(hoaDon);
            return savedHTTT;
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HinhThucThanhToan AddHTTTMoiKhiThanhToanChuyenKhoan(UUID idhd, Double TienCuoiCung, String magiaodich, String taikhoan) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            HoaDon hoaDon = hoaDonAdminRepository.findById(idhd)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + idhd));

            // Log trạng thái hiện tại của hoaDon
            System.out.println("Hóa đơn hiện tại: " + hoaDon);

            // tạo mới hình thức thanh toán
            HinhThucThanhToan HTTT = new HinhThucThanhToan();
            HTTT.setMagiaodich(magiaodich);
            HTTT.setNgaytao(timestamp);
            HTTT.setNgaythanhtoan(timestamp);

            Double tienkhachtra = hoaDon.getTienkhachtra() != null ? hoaDon.getTienkhachtra() : 0.0;
            Double tiencantra = TienCuoiCung - tienkhachtra;

            HTTT.setSotientra(tiencantra);


            HTTT.setHinhthucthanhtoan(2); // Mã 1 đại diện cho hình thức thanh toán thêm
            HTTT.setGhichu("Thanh toán thành công");
            HTTT.setTrangthai(1);

            KhachHang khachHang = hoaDon.getKhachhang();
            if (khachHang != null) {
                HTTT.setKhachhang(khachHang);
            } else {
                throw new IllegalArgumentException("Không tìm thấy khách hàng cho hóa đơn với ID: " + idhd);
            }

            HTTT.setHoadon(hoaDon);

            Optional<NhanVien> taiKhoanOptional = nhanVienRepository.findByTaikhoan(taikhoan);
            if (taiKhoanOptional.isPresent()) {
                NhanVien taiKhoan = taiKhoanOptional.get();
                HTTT.setNhanvien(taiKhoan);
            } else {
                throw new IllegalArgumentException("Không tìm thấy nhân viên với tài khoản: " + taikhoan);
            }

            HinhThucThanhToan savedHTTT = hinhThucThanhToanAdminRepository.save(HTTT);

            // Log thông tin sau khi lưu
            System.out.println("Hình thức thanh toán đã được lưu thành công, ID: " + savedHTTT.getIdhttt());

            // Update hoaDon with tienthua
            Double tienthua = 0.0;
            hoaDon.setTienthua(tienthua);


            hoaDon.setTienkhachtra(tienkhachtra + tiencantra);


            hoaDonAdminRepository.save(hoaDon);
            return savedHTTT;
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
