package com.example.be_duantn.service.quan_ly_hoa_don_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HoaDonTrangThaiAdminRequest;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.Hoadonrespon;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.HoaDonChiTiet;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.entity.LichSuHoaDon;
import com.example.be_duantn.entity.NhanVien;
import com.example.be_duantn.entity.SanPhamChiTiet;
import com.example.be_duantn.entity.VouCher;
import com.example.be_duantn.repository.authentication_repository.NhanVienRepository;
import com.example.be_duantn.repository.mua_hang_oneline_repository.SanPhamChiTietRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HinhThucThanhToanAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HoaDonAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HoaDonChiTietAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.LichSuHoaDonRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.VouCherAdminQuanLyHoaDonRepository;
import com.example.be_duantn.service.quan_ly_hoa_don_service.HoaDonAdminService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HoaDonAdminServiceImpl implements HoaDonAdminService {
    @Autowired
    HoaDonAdminRepository hoaDonAdminRepository;

    @Autowired
    HoaDonChiTietAdminRepository hoaDonChiTietAdminRepository;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    VouCherAdminQuanLyHoaDonRepository vouCherRepository;
    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;

    @Autowired

    HinhThucThanhToanAdminRepository hinhThucThanhToanAdminRepository;

    private final JavaMailSender javaMailSender;


    @Override
    public Integer TongSoHoaDonChoXacNhan() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return hoaDonAdminRepository.TongSoHoaDonChoXacNhan();
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }

    }

    @Override
    public Integer TongSoHoaDonXacNhan() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return hoaDonAdminRepository.TongSoHoaDonXacNhan();
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Integer TongSoHoaDonChoGiao() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return hoaDonAdminRepository.TongSoHoaDonChoGiao();
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Integer TongSoHoaDonDangGiao() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return hoaDonAdminRepository.TongSoHoaDonDangGiao();
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Integer TongSoHoaDonHoanThanh() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return hoaDonAdminRepository.TongSoHoaDonHoanThanh();
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Integer TongSoHoaDonHuy() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return hoaDonAdminRepository.TongSoHoaDonHuy();
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Page<Hoadonrespon> HienThiHoaDonPhanTrang(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(page, 9);
            return hoaDonAdminRepository.ShowAllHoaDon(pageable);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Page<Hoadonrespon> LocHoaDonTheoTrangThai(Integer pageNumber, Integer pageSize, Integer trangthai) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return hoaDonAdminRepository.LocHoaDonTheoTrangThai(pageable, trangthai);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Page<Hoadonrespon> TimKiemTheoMa(Integer pageNumber, Integer pageSize, String mahoadon) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return hoaDonAdminRepository.TimKiemTheoMa(pageable, mahoadon);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Page<Hoadonrespon> LocHoaDonTheoLoaiHoaDon(Integer pageNumber, Integer pageSize, Integer loaihoadon) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return hoaDonAdminRepository.LocHoaDonTheoLoaiHoaDon(pageable, loaihoadon);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Hoadonrespon finByIdHD(UUID IdHD) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return hoaDonAdminRepository.finByidHD(IdHD);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HoaDon updateThongTinHoaDon(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest, String taikhoan, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Kiểm tra và gán các giá trị từ Optional vào đối tượng SanPham
            HoaDon hoaDon = hoaDonAdminRepository.findById(IDHD)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + IDHD));

            // Log trạng thái hiện tại của hoaDon
            System.out.println("Trước khi cập nhật: " + hoaDon);

            // Cập nhật các thông tin của hóa đơn
//          hoaDon.setTrangthai(hoaDonTrangThaiAdminRequest.getTrangthai());
            hoaDon.setEmailnguoinhan(hoaDonTrangThaiAdminRequest.getEmailnguoinhan());
            hoaDon.setSdtnguoinhan(hoaDonTrangThaiAdminRequest.getSdtnguoinhan());
            hoaDon.setTiengiaohang(hoaDonTrangThaiAdminRequest.getTiengiaohang());
            hoaDon.setDiachinhanhang(hoaDonTrangThaiAdminRequest.getDiachinhanhang());
//          hoaDon.setGhichu(hoaDonTrangThaiAdminRequest.getGhichu());
            hoaDon.setNgaycapnhat(timestamp);

            Optional<NhanVien> taiKhoanOptional = nhanVienRepository.findByTaikhoan(taikhoan);
            if (taiKhoanOptional.isPresent()) {
                NhanVien taiKhoan = taiKhoanOptional.get();
                hoaDon.setNhanvien(taiKhoan);
            }

            // Log trạng thái sau khi cập nhật nhưng trước khi lưu
            System.out.println("Sau khi cập nhật nhưng trước khi lưu: " + hoaDon);

            HoaDon savedHoaDon = hoaDonAdminRepository.save(hoaDon); // Lưu hóa đơn đã cập nhật và trả về nó

            // Log trạng thái sau khi lưu
            System.out.println("Sau khi lưu: " + savedHoaDon);
            String taikhoan01 = principal.getName();
            KhachHang khachHang = new KhachHang();
            khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
            LichSuHoaDon lichsuhoadon = new LichSuHoaDon();
            lichsuhoadon.setIdhd(hoaDon.getIdhoadon());
            lichsuhoadon.setNguoithaotac(taikhoan01);
            lichsuhoadon.setGhichu("Chỉnh sửa thông tin hoá đơn");
            lichsuhoadon.setTrangthai(1);
            lichSuHoaDonRepository.save(lichsuhoadon);

            return savedHoaDon;

        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HoaDon updateTrangThaiHoaDon(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest, String taikhoan, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Kiểm tra và gán các giá trị từ Optional vào đối tượng SanPham
            HoaDon hoaDon = hoaDonAdminRepository.findById(IDHD)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + IDHD));

            // Log trạng thái hiện tại của hoaDon
            System.out.println("Trước khi cập nhật: " + hoaDon);

            // Cập nhật các thông tin của hóa đơn
            hoaDon.setTrangthai(hoaDonTrangThaiAdminRequest.getTrangthai());
            hoaDon.setGhichu(hoaDonTrangThaiAdminRequest.getGhichu());
            if (hoaDonTrangThaiAdminRequest.getTrangthai() == 4) {
                hoaDon.setNgaygiaohang(timestamp);
            } else if (hoaDonTrangThaiAdminRequest.getTrangthai() == 2) {
                hoaDon.setNgayxacnhan(timestamp);

            } else if (hoaDonTrangThaiAdminRequest.getTrangthai() == 3) {
                hoaDon.setNgaychogiaohang(timestamp);
            }
            hoaDon.setNgaycapnhat(timestamp);

            Optional<NhanVien> taiKhoanOptional = nhanVienRepository.findByTaikhoan(taikhoan);
            if (taiKhoanOptional.isPresent()) {
                NhanVien taiKhoan = taiKhoanOptional.get();
                hoaDon.setNhanvien(taiKhoan);
            }

            // Log trạng thái sau khi cập nhật nhưng trước khi lưu
            System.out.println("Sau khi cập nhật nhưng trước khi lưu: " + hoaDon);

            HoaDon savedHoaDon = hoaDonAdminRepository.save(hoaDon); // Lưu hóa đơn đã cập nhật và trả về nó

            // Log trạng thái sau khi lưu
            System.out.println("Sau khi lưu: " + savedHoaDon);
            // Send invoice email to customer
            List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietAdminRepository.findByHoadon_Idhoadon(IDHD);
            String productList = generateProductList(hoaDonChiTiets);

            List<HinhThucThanhToan> hinhThucThanhToans = hinhThucThanhToanAdminRepository.findHinhThucThanhToanByHoadonIdhoadon(IDHD);
            String hinhthucthanhtoan = HinhThucThanhToan(hinhThucThanhToans);

            // Kiểm tra và gán giá trị 0 nếu hoaDon.getGiatrigiam() là null
            Double giatrigiam = hoaDon.getGiatrigiam() != null ? hoaDon.getGiatrigiam() : 0.0;

            Double tongtienhang = hoaDon.getThanhtien() + giatrigiam;
            Double sotienphaitra = tongtienhang - giatrigiam;

            sendInvoiceEmailTheotrangthai(hoaDon, hinhthucthanhtoan, productList, tongtienhang, sotienphaitra);
            String taikhoan01 = principal.getName();
            KhachHang khachHang = new KhachHang();
            khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
            LichSuHoaDon lichsuhoadon = new LichSuHoaDon();
            lichsuhoadon.setIdhd(hoaDon.getIdhoadon());
            lichsuhoadon.setNguoithaotac(taikhoan01);
            lichsuhoadon.setGhichu(hoaDonTrangThaiAdminRequest.getGhichu());
            lichsuhoadon.setTrangthai(1);
            lichSuHoaDonRepository.save(lichsuhoadon);
            return savedHoaDon;

        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HoaDon updateThongTinNguoiGiao(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest, String taikhoan, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Kiểm tra và gán các giá trị từ Optional vào đối tượng SanPham
            HoaDon hoaDon = hoaDonAdminRepository.findById(IDHD)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + IDHD));

            // Log trạng thái hiện tại của hoaDon
            System.out.println("Trước khi cập nhật: " + hoaDon);

            // Cập nhật các thông tin của hóa đơn
            hoaDon.setSdtnguoigiao(hoaDonTrangThaiAdminRequest.getSdtnguoigiao());
            hoaDon.setTennguoigiao(hoaDonTrangThaiAdminRequest.getTennguoigiao());
            hoaDon.setDonvigiaohang(hoaDonTrangThaiAdminRequest.getDonvigiaohang());
            hoaDon.setNgaycapnhat(timestamp);

            Optional<NhanVien> taiKhoanOptional = nhanVienRepository.findByTaikhoan(taikhoan);
            if (taiKhoanOptional.isPresent()) {
                NhanVien taiKhoan = taiKhoanOptional.get();
                hoaDon.setNhanvien(taiKhoan);
            }

            // Log trạng thái sau khi cập nhật nhưng trước khi lưu
            System.out.println("Sau khi cập nhật nhưng trước khi lưu: " + hoaDon);

            HoaDon savedHoaDon = hoaDonAdminRepository.save(hoaDon); // Lưu hóa đơn đã cập nhật và trả về nó

            // Log trạng thái sau khi lưu
            System.out.println("Sau khi lưu: " + savedHoaDon);
            String taikhoan01 = principal.getName();
            KhachHang khachHang = new KhachHang();
            khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
            LichSuHoaDon lichsuhoadon = new LichSuHoaDon();
            lichsuhoadon.setIdhd(hoaDon.getIdhoadon());
            lichsuhoadon.setNguoithaotac(taikhoan01);
            lichsuhoadon.setGhichu("Chỉnh sửa thông tin người giao");
            lichsuhoadon.setTrangthai(1);
            lichSuHoaDonRepository.save(lichsuhoadon);
            return savedHoaDon;

        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HoaDon updatehoanthanh(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest, String taikhoan, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Kiểm tra và gán các giá trị từ Optional vào đối tượng SanPham
            HoaDon hoaDon = hoaDonAdminRepository.findById(IDHD)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + IDHD));

            // Log trạng thái hiện tại của hoaDon
            System.out.println("Trước khi cập nhật: " + hoaDon);

            // Cập nhật các thông tin của hóa đơn
            hoaDon.setTrangthai(hoaDonTrangThaiAdminRequest.getTrangthai());
            hoaDon.setGhichu(hoaDonTrangThaiAdminRequest.getGhichu());
            hoaDon.setNgaynhanhang(timestamp);
            // Cập nhật giá trị Thanhtien bằng giá trị của Tienkhachtra
            hoaDon.setTienkhachtra(hoaDon.getThanhtien());
            hoaDon.setNgaycapnhat(timestamp);
            // Chỉ cập nhật ngaythanhtoan nếu nó đang null
            if (hoaDon.getNgaythanhtoan() == null) {
                hoaDon.setNgaythanhtoan(timestamp);
            }
            Optional<NhanVien> taiKhoanOptional = nhanVienRepository.findByTaikhoan(taikhoan);
            if (taiKhoanOptional.isPresent()) {
                NhanVien taiKhoan = taiKhoanOptional.get();
                hoaDon.setNhanvien(taiKhoan);
            }
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"

            // Log trạng thái sau khi cập nhật nhưng trước khi lưu
            System.out.println("Sau khi cập nhật nhưng trước khi lưu: " + hoaDon);

            HoaDon savedHoaDon = hoaDonAdminRepository.save(hoaDon); // Lưu hóa đơn đã cập nhật và trả về nó

            // Log trạng thái sau khi lưu
            System.out.println("Sau khi lưu: " + savedHoaDon);
            String taikhoan01 = principal.getName();
            KhachHang khachHang = new KhachHang();
            khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
            LichSuHoaDon lichsuhoadon = new LichSuHoaDon();
            lichsuhoadon.setIdhd(hoaDon.getIdhoadon());
            lichsuhoadon.setNguoithaotac(taikhoan01);
            lichsuhoadon.setGhichu(hoaDonTrangThaiAdminRequest.getGhichu());
            lichsuhoadon.setTrangthai(1);
            lichSuHoaDonRepository.save(lichsuhoadon);

            return savedHoaDon;

        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HoaDon updatehuyhoadon(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest, String taikhoan, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Kiểm tra và gán các giá trị từ Optional vào đối tượng HoaDon
            HoaDon hoaDon = hoaDonAdminRepository.findById(IDHD)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + IDHD));

            // Log trạng thái hiện tại của hoaDon
            System.out.println("Trước khi cập nhật: " + hoaDon);

            // Cập nhật các thông tin của hóa đơn
            hoaDon.setTrangthai(hoaDonTrangThaiAdminRequest.getTrangthai());
            hoaDon.setGhichu(hoaDonTrangThaiAdminRequest.getGhichu());
            hoaDon.setNgaycapnhat(timestamp);
            hoaDon.setNgayhuy(timestamp);

            // Lấy danh sách chi tiết hoá đơn
            List<HoaDonChiTiet> hdctList = hoaDon.getHoadonchitiet();

            // Xóa tất cả chi tiết giỏ hàng và cập nhật số lượng tồn
            for (HoaDonChiTiet hdct : hdctList) {
                SanPhamChiTiet spct = hdct.getSanphamchitiet();
                if (spct != null) {
                    int updatedSoluongton = spct.getSoluongton() + hdct.getSoluong();
                    spct.setSoluongton(updatedSoluongton);
                    sanPhamChiTietRepository.save(spct);
                    //update lại thành 2
                    hdct.setTrangthai(2); // Assuming there's a setStatus method in HoaDonChiTiet
                    hoaDonChiTietAdminRepository.save(hdct);
                    // Log the updated quantities
                    System.out.println("Updated soluongton for spct ID " + spct.getIdspct() + ": " + updatedSoluongton);
                } else {
                    throw new IllegalArgumentException("Không tìm thấy sản phẩm chi tiết trong hóa đơn chi tiết!");
                }
            }

            // Tìm nhân viên từ tài khoản
            Optional<NhanVien> taiKhoanOptional = nhanVienRepository.findByTaikhoan(taikhoan);
            if (taiKhoanOptional.isPresent()) {
                NhanVien taiKhoan = taiKhoanOptional.get();
                hoaDon.setNhanvien(taiKhoan);
            }

            // Log trạng thái sau khi cập nhật nhưng trước khi lưu
            System.out.println("Sau khi cập nhật nhưng trước khi lưu: " + hoaDon);

            HoaDon savedHoaDon = hoaDonAdminRepository.save(hoaDon); // Lưu hóa đơn đã cập nhật và trả về nó

            // Log trạng thái sau khi lưu
            System.out.println("Sau khi lưu: " + savedHoaDon);

            String taikhoan01 = principal.getName();
            KhachHang khachHang = new KhachHang();
            khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
            LichSuHoaDon lichsuhoadon = new LichSuHoaDon();
            lichsuhoadon.setIdhd(hoaDon.getIdhoadon());
            lichsuhoadon.setNguoithaotac(taikhoan01);
            lichsuhoadon.setGhichu(hoaDonTrangThaiAdminRequest.getGhichu());
            lichsuhoadon.setTrangthai(1);
            lichSuHoaDonRepository.save(lichsuhoadon);
            return savedHoaDon;

        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HoaDon updateThanhTien(UUID IDHD) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Kiểm tra và gán các giá trị từ Optional vào đối tượng HoaDon
            HoaDon hoaDon = hoaDonAdminRepository.findById(IDHD)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + IDHD));
            List<HoaDonChiTiet> hdct = hoaDon.getHoadonchitiet();
            double thanhtien01 = 0.0;

            for (HoaDonChiTiet chiTiet : hdct) {
                if (chiTiet.getDongiakhigiam() == null) {
                    thanhtien01 += chiTiet.getDongia() * chiTiet.getSoluong();
                } else {
                    thanhtien01 += chiTiet.getDongiakhigiam() * chiTiet.getSoluong();
                }
            }

            // Giảm soluongdung của voucher hiện tại nếu có
            if (hoaDon.getVoucher() != null) {
                VouCher currentVoucher = hoaDon.getVoucher();
                int soluongdung = Optional.ofNullable(currentVoucher.getSoluongdung()).orElse(0);
                currentVoucher.setSoluongdung(soluongdung - 1);
                vouCherRepository.save(currentVoucher);
            }

            // Lấy danh sách các voucher có trạng thái là 1 từ cơ sở dữ liệu
            List<VouCher> activeVouchers = vouCherRepository.findByTrangthai(1);

            // Khởi tạo biến lưu trữ voucher phù hợp
            VouCher suitableVoucher = null;
            Double maxDiscountAmount = 0.0;
            Date now = new Date();

            // Tìm voucher phù hợp với mức thanh toán
            for (VouCher voucher : activeVouchers) {
                int soluongdung = Optional.ofNullable(voucher.getSoluongdung()).orElse(0);
                if (thanhtien01 >= voucher.getDieukientoithieuhoadon() &&
                        soluongdung < voucher.getSoluongma() &&
                        !now.before(voucher.getNgaybatdau()) && !now.after(voucher.getNgayketthuc())) {

                    Double potentialDiscount;
                    if (voucher.getHinhthucgiam() == 1) {
                        potentialDiscount = thanhtien01 * voucher.getGiatrigiam() / 100.0;
                    } else {
                        potentialDiscount = voucher.getGiatrigiam();
                    }

                    if (potentialDiscount > maxDiscountAmount) {
                        maxDiscountAmount = potentialDiscount;
                        suitableVoucher = voucher;
                    }
                }
            }

            // Nếu tồn tại voucher phù hợp
            if (suitableVoucher != null) {
                // Cập nhật thành tiền của hóa đơn
                Double newThanhtien;
                Double newTienGiam;
                if (suitableVoucher.getHinhthucgiam() == 1) {
                    // Trừ giá trị giảm theo phần trăm
                    newTienGiam = thanhtien01 * suitableVoucher.getGiatrigiam() / 100.0;
                } else {
                    // Trừ giá trị giảm cố định
                    newTienGiam = suitableVoucher.getGiatrigiam();
                }
                newThanhtien = thanhtien01 - newTienGiam;

                hoaDon.setThanhtien(newThanhtien);
                // Gán voucher phù hợp cho hóa đơn
                hoaDon.setVoucher(suitableVoucher);
                // Cập nhật giá trị giảm của hóa đơn
                hoaDon.setGiatrigiam(newTienGiam);

                // Tăng soluongdung của voucher phù hợp
                int soluongdung = Optional.ofNullable(suitableVoucher.getSoluongdung()).orElse(0);
                suitableVoucher.setSoluongdung(soluongdung + 1);
                vouCherRepository.save(suitableVoucher);
            } else {
                // Nếu không có voucher nào thỏa mãn điều kiện, gán giá trị null cho voucher của hóa đơn
                // Cập nhật thành tiền của hóa đơn
                hoaDon.setThanhtien(thanhtien01);
                // Cập nhật giá trị giảm của hóa đơn
                hoaDon.setGiatrigiam(null);
                hoaDon.setVoucher(null);
            }

            // Lưu và trả về hóa đơn đã cập nhật
            return hoaDonAdminRepository.save(hoaDon);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
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

    private void sendInvoiceEmailTheotrangthai(HoaDon hoaDon, String hinhthucthanhtoan, String productList, Double tongtienhang, Double sotienphaitra) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(hoaDon.getEmailnguoinhan());
            helper.setSubject("Thông Tin Đơn Hàng Của Quý Khách Hàng");

            String trangThai;
            switch (hoaDon.getTrangthai()) {
                case 1:
                    trangThai = "Chờ xác nhận";
                    break;
                case 2:
                    trangThai = "Đã xác nhận";
                    break;
                case 3:
                    trangThai = "Chờ giao";
                    break;
                case 4:
                    trangThai = "Đang giao";
                    break;
                default:
                    trangThai = "Không xác định";
            }

            String htmlMsg = "<body style=\"font-family: Arial, sans-serif;\">\n" +
                    "    <h1 style=\"color: rgb(255, 125, 26);\">Kính Gửi Quý Khách Hàng : " + hoaDon.getTennguoinhan() + "</h1>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Chúng tôi xin chân thành cảm ơn Quý khách đã đặt hàng tại : <strong style=\"color: rgb(255, 125, 26);\">2TH-SHOP</strong></h6>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Thông tin chi tiết về đơn hàng của Quý khách như sau:</h6>\n" +
                    "    <table style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">\n" +
                    "        <thead>\n" +
                    "            <tr>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Mã Đơn Hàng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Khách Hàng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Số Điện Thoại</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Email</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Ngày Đặt Hàng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Địa chỉ giao hàng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Trạng Thái</th>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>\n" +
                    "            <tr>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getMahoadon() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getTennguoinhan() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getSdtnguoinhan() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getEmailnguoinhan() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getNgaytao() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + hoaDon.getDiachinhanhang() + "</td>\n" +
                    "                <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + trangThai + "</td>\n" +
                    "            </tr>\n" +
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
