package com.example.be_duantn.service.quan_ly_hoa_don_service.Impl;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadSPTaiQuayRespon;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.MessageGioHangCTRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HoaDonChiTietRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.LoadSPHoaDonChiTietRespon;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.HoaDonChiTiet;
import com.example.be_duantn.entity.KhachHang;
import com.example.be_duantn.entity.LichSuHoaDon;
import com.example.be_duantn.entity.SanPhamChiTiet;
import com.example.be_duantn.repository.mua_hang_oneline_repository.SanPhamChiTietRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HoaDonAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HoaDonChiTietAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.LichSuHoaDonRepository;
import com.example.be_duantn.service.quan_ly_hoa_don_service.HoaDonChiTietAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class HoaDonChiTietAdminServiceImpl implements HoaDonChiTietAdminService {
    @Autowired
    HoaDonChiTietAdminRepository hoaDonChiTietAdminRepository;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;
    @Autowired
    HoaDonAdminRepository hoaDonAdminRepository;

    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;

    @Override
    public List<HoaDonChiTietRespon> finByIdHDCT(UUID IdHD) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return hoaDonChiTietAdminRepository.finByidHDCT(IdHD);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Page<LoadSPHoaDonChiTietRespon> LoadSPSuaHoaDon(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Pageable pageable = PageRequest.of(page, 10);
            return hoaDonChiTietAdminRepository.LoadSPBanTaiQuay(pageable);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Page<LoadSPHoaDonChiTietRespon> LocTenSPBanTaiQuay(Integer page, String tensp) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Pageable pageable = PageRequest.of(page, 10);
            return hoaDonChiTietAdminRepository.LocTenSPSuaHoaDon(pageable, tensp);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }

    }

    @Override
    public Page<LoadSPHoaDonChiTietRespon> LocSPNhieuTieuChiBanTaiQuay(Integer page, String tenmausac, String tensize, String tenchatlieu, String tendanhmuc, String tenthuonghieu, String tenxuatxu) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Pageable pageable = PageRequest.of(page, 10);
            return hoaDonChiTietAdminRepository.LocSPNhieuTieuChiSuaHoaDon(pageable, tenmausac, tensize, tenchatlieu, tendanhmuc, tenthuonghieu, tenxuatxu);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }

    }

    @Override
    public MessageGioHangCTRespon addSPHDCT(UUID idhd, UUID idspct, Integer soluong, Double dongiakhigiam, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            HoaDon hoaDon = hoaDonAdminRepository.findById(idhd)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + idhd));
            HoaDonChiTiet ghct = hoaDonChiTietAdminRepository.findByHoadonIdhoadonAndSanphamchitietIdspct(idhd, idspct);
            SanPhamChiTiet spct = sanPhamChiTietRepository.findById(idspct).orElse(null);
            if (ghct != null) {
                // Sản phẩm đã có trong ghct, cập nhật số lượng
                ghct.setSoluong(ghct.getSoluong() + soluong);
                ghct.setDongiakhigiam(dongiakhigiam);

                hoaDonChiTietAdminRepository.save(ghct);
                // Giảm số lượng tồn của spct
                if (spct != null) {
                    spct.setSoluongton(spct.getSoluongton() - soluong);
                    sanPhamChiTietRepository.save(spct);
                } else {
                    return MessageGioHangCTRespon.builder().message("K tìm thấy idspct !").build();
                }
                return MessageGioHangCTRespon.builder().message("Cập nhật thành công hdct !").build();

            } else {
                // Sản phẩm chưa có trong ghct thì thêm mới
                HoaDon ghMoi = hoaDonAdminRepository.findById(idhd).orElse(null);
                if (ghMoi == null) {
                    ghMoi = new HoaDon();
                    ghMoi.setIdhoadon(idhd);
                    hoaDonAdminRepository.save(ghMoi);
                }

                HoaDonChiTiet ghctMoi = new HoaDonChiTiet();
                ghctMoi.setIdhdct(UUID.randomUUID());
                ghctMoi.setHoadon(ghMoi);

                // Tạo mới spct để lấy idspct
                SanPhamChiTiet spctMoi = new SanPhamChiTiet();
                spctMoi.setIdspct(idspct);

                ghctMoi.setSanphamchitiet(spctMoi);
                ghctMoi.setSoluong(soluong);
                ghctMoi.setDongia(spct.getSanpham().getGiaban());
                ghctMoi.setDongiakhigiam(dongiakhigiam);
                ghctMoi.setNgaytao(ghctMoi.getNgaytao());
                ghctMoi.setTrangthai(1);

                // add hdct mới
                hoaDonChiTietAdminRepository.save(ghctMoi);
                String taikhoan = principal.getName();
                // update số lượng tồn trong spct
                spct.setSoluongton(spct.getSoluongton() - soluong);
                sanPhamChiTietRepository.save(spct);
                KhachHang khachHang = new KhachHang();
                khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
                LichSuHoaDon lichsuhoadon = new LichSuHoaDon();
                lichsuhoadon.setIdhd(hoaDon.getIdhoadon());
                lichsuhoadon.setNguoithaotac(taikhoan);
                lichsuhoadon.setGhichu("Thêm mới sản phẩm vào hoá đơn");
                lichsuhoadon.setTrangthai(1);
                lichSuHoaDonRepository.save(lichsuhoadon);
                return MessageGioHangCTRespon.builder().message("Thêm mới thành công hdct !").build();
            }
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public MessageGioHangCTRespon updateHDCT(UUID idHDCT, Integer soluong,UUID idhd, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            HoaDon hoaDon = hoaDonAdminRepository.findById(idhd)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + idhd));

            HoaDonChiTiet HDCT = hoaDonChiTietAdminRepository.findById(idHDCT).orElse(null);
            if (HDCT != null) {
                Integer soluongton01 = HDCT.getSanphamchitiet().getSoluongton();
                Integer soluongcu1 = HDCT.getSoluong();
                // xử lý cập nhật số lượng trong giỏ. Lấy số lượng mới - số lượng cũ
                Integer soluongthaydoi1 = soluong - soluongcu1;
                // Kiểm tra nếu số lượng thêm vào lớn hơn số lượng tồn kho
                if (soluongthaydoi1 > soluongton01) {
                    throw new IllegalArgumentException("Số lượng thêm vào lớn hơn số lượng tồn kho!");
                }
                // Số lượng hiện tại trong giỏ hàng
                Integer soluongcu = HDCT.getSoluong();
                // Số lượng tồn hiện tại của spct
                Integer soluongton = HDCT.getSanphamchitiet().getSoluongton();

                // xử lý cập nhật số lượng trong giỏ. Lấy số lượng mới - số lượng cũ
                Integer soluongthaydoi = soluong - soluongcu;
                HDCT.setSoluong(soluong);

                // xử lý cập nhật lại số lượng tồn trong spct. Lấy số lượng tồn - số lượng thay đổi
                Integer soluongtonmoi = soluongton - soluongthaydoi;
                HDCT.getSanphamchitiet().setSoluongton(soluongtonmoi);
                hoaDonChiTietAdminRepository.save(HDCT);
                String taikhoan = principal.getName();
                KhachHang khachHang = new KhachHang();
                khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
                LichSuHoaDon lichsuhoadon = new LichSuHoaDon();
                lichsuhoadon.setIdhd(hoaDon.getIdhoadon());
                lichsuhoadon.setNguoithaotac(taikhoan);
                lichsuhoadon.setGhichu("Sửa số lượng sản phẩm");
                lichsuhoadon.setTrangthai(1);
                lichSuHoaDonRepository.save(lichsuhoadon);
                return new MessageGioHangCTRespon("Cập nhật số lượng hdct thành công!");
            } else {
                throw new AccessDeniedException("Không tìm thấy idhdct!");
            }
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public MessageGioHangCTRespon deleteHoaDonCT(UUID idghct,UUID idhd, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            HoaDon hoaDon = hoaDonAdminRepository.findById(idhd)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + idhd));
            HoaDonChiTiet hdct = hoaDonChiTietAdminRepository.findById(idghct).orElse(null);
            if (hdct != null) {
                hoaDonChiTietAdminRepository.delete(hdct);

                // Cập nhật lại soluongton trong spct
                SanPhamChiTiet spct = hdct.getSanphamchitiet();
                if (spct != null) {
                    spct.setSoluongton(hdct.getSoluong() + spct.getSoluongton());
                    sanPhamChiTietRepository.save(spct);
                } else {
                    return MessageGioHangCTRespon.builder().message("K tìm thấy idspct trong hdct !").build();
                }
                String taikhoan = principal.getName();
                KhachHang khachHang = new KhachHang();
                khachHang.setIdkh(hoaDon.getKhachhang().getIdkh());
                LichSuHoaDon lichsuhoadon = new LichSuHoaDon();
                lichsuhoadon.setIdhd(hoaDon.getIdhoadon());
                lichsuhoadon.setNguoithaotac(taikhoan);
                lichsuhoadon.setGhichu("Xoá sản phẩm khỏi hoá đơn");
                lichsuhoadon.setTrangthai(1);
                lichSuHoaDonRepository.save(lichsuhoadon);

                return MessageGioHangCTRespon.builder().message("Xóa hdct thành công !").build();
            } else {
                return MessageGioHangCTRespon.builder().message("K tìm thấy idghct !").build();
            }
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
