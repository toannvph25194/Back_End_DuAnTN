package com.example.be_duantn.service.quan_ly_hoa_don_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HinhThucThanhToanAdminRequest;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HinhThucThanhToanRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.NhanVienAdminRespon;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.HoaDonChiTiet;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.entity.NhanVien;
import com.example.be_duantn.entity.SanPhamChiTiet;
import com.example.be_duantn.repository.authentication_repository.NhanVienRepository;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.SanPhamRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HinhThucThanhToanAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HoaDonAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.NhanVienAdminQuanLyHoaDonRepository;
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
public class HinhThucThanhToanAdminAdminServiceImpl implements HinhThucThanhToanAdminService {
    @Autowired
    HinhThucThanhToanAdminRepository hinhThucThanhToanAdminRepository;

    @Autowired
    HoaDonAdminRepository hoaDonAdminRepository;

    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    NhanVienAdminQuanLyHoaDonRepository nhanVienRepository;

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
                HTTT.setHinhthucthanhtoan(3); // Mã 3 đại diện cho hình thức thanh toán đã bị hủy
                HTTT.setGhichu("Hoá đơn đã bị huỷ");
                HTTT.setTrangthai(1);
                KhachHang khachHang = new KhachHang();
                khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
                HTTT.setKhachhang(khachHang);
                HoaDon hoaDon1 = new HoaDon();
                hoaDon1.setIdhoadon(hoaDon.getIdhoadon());
                HTTT.setHoadon(hoaDon1);
                HinhThucThanhToan savedHTTT = hinhThucThanhToanAdminRepository.save(HTTT);
                // Log thông tin sau khi lưu
                System.out.println("Hình thức thanh toán đã được lưu thành công, ID: " + savedHTTT.getIdhttt());
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

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            HoaDon hoaDon = hoaDonAdminRepository.findById(idhd)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + idhd));

            // Log trạng thái hiện tại của hoaDon
            System.out.println("Hóa đơn hiện tại: " + hoaDon);

            if (hoaDon.getTienkhachtra() == null) {
                // Lấy danh sách httt
                List<HinhThucThanhToan> HTTT = hoaDon.getHinhthucthanhtoan();

                // Xóa tất cả chi tiết giỏ hàng và cập nhật số lượng tồn
                for (HinhThucThanhToan HTTT01 : HTTT) {
                    if (HTTT01 != null) {
                        // Generate transaction ID and log it
                        String generatedTransactionId = generateRandomTransactionId();
                        HTTT01.setMagiaodich(generatedTransactionId);
                        HTTT01.setGhichu("Khách hàng đã thanh toán");

                        HTTT01.setSotientra(hoaDon.getThanhtien());
                        HTTT01.setNgaycapnhat(timestamp);
                        if (HTTT01.getNgaythanhtoan() == null) {
                            HTTT01.setNgaythanhtoan(timestamp);
                        }
                        // Log trạng thái sau khi cập nhật nhưng trước khi lưu
                        System.out.println("Sau khi cập nhật nhưng trước khi lưu: " + HTTT01);
                        hinhThucThanhToanAdminRepository.save(HTTT01); // Lưu hóa đơn đã cập nhật

                        // Log the updated quantities
                        System.out.println("Updated soluongton for spct ID " + HTTT01.getIdhttt() + ": " + HTTT01);
                    } else {
                        throw new IllegalArgumentException("Không tìm thấy sản phẩm chi tiết trong hóa đơn chi tiết!");
                    }
                }
                // Sau khi xử lý tất cả các phần tử
                return HTTT.isEmpty() ? null : HTTT.get(0);

            } else if (hoaDon.getTienkhachtra() > hoaDon.getThanhtien()) {
                HinhThucThanhToan HTTT = new HinhThucThanhToan();
                HTTT.setMagiaodich(generateRandomTransactionId()); // Sử dụng phương thức tạo mã giao dịch 8 chữ số
                HTTT.setNgaytao(timestamp);
                HTTT.setNgaythanhtoan(timestamp);
                HTTT.setSotientra(hoaDon.getTienkhachtra() - hoaDon.getThanhtien());
                HTTT.setHinhthucthanhtoan(3); // Mã 3 đại diện cho hình thức thanh toán đã bị hủy
                HTTT.setGhichu("Hoàn tiền thừa cho khách");
                HTTT.setTrangthai(1);
                KhachHang khachHang = new KhachHang();
                khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
                HTTT.setKhachhang(khachHang);
                HoaDon hoaDon1 = new HoaDon();
                hoaDon1.setIdhoadon(hoaDon.getIdhoadon());
                HTTT.setHoadon(hoaDon1);
                HinhThucThanhToan savedHTTT = hinhThucThanhToanAdminRepository.save(HTTT);
                // Log thông tin sau khi lưu
                System.out.println("Hình thức thanh toán đã được lưu thành công, ID: " + savedHTTT.getIdhttt());
                return savedHTTT;

            } else if (hoaDon.getTienkhachtra() < hoaDon.getThanhtien()) {
                HinhThucThanhToan HTTT = new HinhThucThanhToan();
                HTTT.setMagiaodich(generateRandomTransactionId()); // Sử dụng phương thức tạo mã giao dịch 8 chữ số
                HTTT.setNgaytao(timestamp);
                HTTT.setNgaythanhtoan(timestamp);
                HTTT.setSotientra(hoaDon.getThanhtien() - hoaDon.getTienkhachtra());
                HTTT.setHinhthucthanhtoan(1); // Mã 1 đại diện cho hình thức thanh toán thêm
                HTTT.setGhichu("Thu thêm tiền thiếu");
                HTTT.setTrangthai(1);
                KhachHang khachHang = new KhachHang();
                khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
                HTTT.setKhachhang(khachHang);
                HoaDon hoaDon1 = new HoaDon();
                hoaDon1.setIdhoadon(hoaDon.getIdhoadon());
                HTTT.setHoadon(hoaDon1);
                HinhThucThanhToan savedHTTT = hinhThucThanhToanAdminRepository.save(HTTT);
                // Log thông tin sau khi lưu
                System.out.println("Hình thức thanh toán đã được lưu thành công, ID: " + savedHTTT.getIdhttt());
                return savedHTTT;

            } else {
                // Trường hợp hoaDon.getTienkhachtra() == hoaDon.getThanhtien()
                System.out.println("Khách hàng đã thanh toán đúng số tiền, không cần xử lý thêm.");
                return null;
            }
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public List<HinhThucThanhToan> updateNguoiXacNhan(UUID IDHD, UUID idnhanvien) {
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
            Integer trangthai = 1 ;
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
