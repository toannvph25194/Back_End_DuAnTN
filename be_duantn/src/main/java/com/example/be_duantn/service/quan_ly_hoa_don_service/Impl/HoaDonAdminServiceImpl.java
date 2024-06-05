package com.example.be_duantn.service.quan_ly_hoa_don_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.MessageRequest;
import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HoaDonTrangThaiAdminRequest;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.Hoadonrespon;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.NhanVien;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.repository.authentication_repository.NhanVienRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HoaDonAdminRepository;
import com.example.be_duantn.service.quan_ly_hoa_don_service.HoaDonAdminService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class HoaDonAdminServiceImpl implements HoaDonAdminService {
    @Autowired
    HoaDonAdminRepository hoaDonAdminRepository;

    @Autowired
    NhanVienRepository nhanVienRepository;

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
    public HoaDon updateThongTinHoaDon(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest, String taikhoan) {
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
//                hoaDon.setTrangthai(hoaDonTrangThaiAdminRequest.getTrangthai());
            hoaDon.setEmailnguoinhan(hoaDonTrangThaiAdminRequest.getEmailnguoinhan());
            hoaDon.setSdtnguoinhan(hoaDonTrangThaiAdminRequest.getSdtnguoinhan());
            hoaDon.setTiengiaohang(hoaDonTrangThaiAdminRequest.getTiengiaohang());
            hoaDon.setDiachinhanhang(hoaDonTrangThaiAdminRequest.getDiachinhanhang());
//                hoaDon.setGhichu(hoaDonTrangThaiAdminRequest.getGhichu());
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

            return savedHoaDon;

        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HoaDon updateTrangThaiHoaDon(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest, String taikhoan) {
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

            return savedHoaDon;

        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HoaDon updateThongTinNguoiGiao(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest, String taikhoan) {
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

            return savedHoaDon;

        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HoaDon updatehoanthanh(UUID IDHD, HoaDonTrangThaiAdminRequest hoaDonTrangThaiAdminRequest, String taikhoan) {
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

            // Log trạng thái sau khi cập nhật nhưng trước khi lưu
            System.out.println("Sau khi cập nhật nhưng trước khi lưu: " + hoaDon);

            HoaDon savedHoaDon = hoaDonAdminRepository.save(hoaDon); // Lưu hóa đơn đã cập nhật và trả về nó

            // Log trạng thái sau khi lưu
            System.out.println("Sau khi lưu: " + savedHoaDon);

            return savedHoaDon;

        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
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
