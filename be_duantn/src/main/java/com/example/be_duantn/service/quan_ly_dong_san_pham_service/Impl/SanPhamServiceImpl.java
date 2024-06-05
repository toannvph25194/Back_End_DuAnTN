package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.MessageRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamRequest;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.SanPhamShopRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SanPhamAdminRespon;
import com.example.be_duantn.entity.ChatLieu;
import com.example.be_duantn.entity.DanhMuc;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.entity.SanPhamChiTiet;
import com.example.be_duantn.entity.ThuongHieu;
import com.example.be_duantn.entity.XuatXu;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.ChatLieuRepository;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.DanhMucRepository;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.SanPhamRepository;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.ThuongHieuRepository;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.XuatXuRepository;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.SanPhamService;
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

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    SanPhamRepository sanPhamRepository;
    @Autowired
    ChatLieuRepository chatLieuRepository;
    @Autowired
    XuatXuRepository xuatXuRepository;
    @Autowired
    DanhMucRepository danhMucRepository;
    @Autowired
    ThuongHieuRepository thuongHieuRepository;


    @Override
    public Page<SanPhamAdminRespon> HienThiSanPhamPhanTrang(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Pageable pageable = PageRequest.of(page, 9);
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return sanPhamRepository.ShowSanPham(pageable);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public SanPham addSanPham(SanPhamRequest sanpham) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<SanPham> sanPhamCheck = sanPhamRepository.findByMasp(sanpham.getMasp());

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"

            // Kiểm tra xem sản phẩm đã tồn tại hay chưa
            if (sanPhamCheck.isPresent()) {
                System.err.println("Mã sản phẩm đã tồn tại. Vui lòng chọn mã khác.");
                throw new RuntimeException("Mã sản phẩm đã tồn tại. Vui lòng chọn mã khác.");
            }
            SanPham sp = new SanPham();
            sp.setMasp(sanpham.getMasp());
            sp.setTensp(sanpham.getTensp());
            sp.setTheloai(sanpham.getTheloai());
            sp.setMota(sanpham.getMotasp());
            sp.setTrangthai(sanpham.getTrangthai());
            sp.setGiaban(sanpham.getGiaban());
            sp.setGianhap(sanpham.getGianhap());
            sp.setImagedefaul(sanpham.getImagedefaul());

            DanhMuc danhMuc = new DanhMuc();
            danhMuc.setIddanhmuc(sanpham.getDanhmuc());
            sp.setDanhmuc(danhMuc);
            XuatXu xuatXu = new XuatXu();
            xuatXu.setIdxuatxu(sanpham.getXuatxu());
            sp.setXuatxu(xuatXu);
            ThuongHieu thuongHieu = new ThuongHieu();
            thuongHieu.setIdthuonghieu(sanpham.getThuonghieu());
            sp.setThuonghieu(thuongHieu);
            ChatLieu chatLieu = new ChatLieu();
            chatLieu.setIdchatlieu(sanpham.getChatlieu());
            sp.setChatlieu(chatLieu);
            return sanPhamRepository.save(sp);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public List<SanPhamAdminRespon> getSanPhamTheoID(UUID IdSP) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return sanPhamRepository.ShowSanPhamTheoId(IdSP);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Page<SanPhamAdminRespon> locTenSPShop(Integer pageNumber, Integer pageSize, String tensp) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return sanPhamRepository.locTenSP(pageable, tensp);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Page<SanPhamAdminRespon> locSPShopNTC(Integer pageNumber, Integer pageSize, String tendanhmuc, String tenmausac, String tensize, String tenchatlieu, String tenxuatxu, String tenthuonghieu) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return sanPhamRepository.locSPShopNTC(pageable, tendanhmuc, tenmausac, tensize, tenchatlieu,tenxuatxu, tenthuonghieu );
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public MessageRequest udatesanpham(SanPhamRequest sanpham, UUID IdSp) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Optional<ChatLieu> chatLieuOptional = chatLieuRepository.findById(sanpham.getChatlieu());
            Optional<XuatXu> xuatXuOptional = xuatXuRepository.findById(sanpham.getXuatxu());
            Optional<DanhMuc> danhMucOptional = danhMucRepository.findById(sanpham.getDanhmuc());
            Optional<ThuongHieu> thuongHieuOptional = thuongHieuRepository.findById(sanpham.getThuonghieu());

            // Kiểm tra và gán các giá trị từ Optional vào đối tượng SanPham
            SanPham sanPham = sanPhamRepository.findById(IdSp)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + IdSp));

            sanPham.setMasp(sanpham.getMasp());
            sanPham.setTensp(sanpham.getTensp());
            sanPham.setMota(sanpham.getMotasp());
            sanPham.setGiaban(sanpham.getGiaban());
            sanPham.setGianhap(sanpham.getGianhap());
            sanPham.setImagedefaul(sanpham.getImagedefaul());
            sanPham.setTheloai(sanpham.getTheloai());
            sanPham.setTrangthai(1);

            // Kiểm tra và gán các đối tượng liên quan vào sản phẩm
            chatLieuOptional.ifPresent(sanPham::setChatlieu);
            xuatXuOptional.ifPresent(sanPham::setXuatxu);
            danhMucOptional.ifPresent(sanPham::setDanhmuc);
            thuongHieuOptional.ifPresent(sanPham::setThuonghieu);

            sanPhamRepository.save(sanPham);
            return MessageRequest.builder().message("Update thành công").build();
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public SanPham updatesp(UUID idsp, Integer trangthai) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            SanPham ctSP = sanPhamRepository.findById(idsp)
                    .orElseThrow(() -> new EntityNotFoundException("Sản phẩm chi tiết không tồn tại!"));
            ctSP.setTrangthai(trangthai);
            return sanPhamRepository.save(ctSP);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Page<SanPhamAdminRespon> HienThiSanPhamPhanTrangThemHD(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Pageable pageable = PageRequest.of(page, 8);
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return sanPhamRepository.ShowSanPhamThemHoaDon(pageable);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Page<SanPhamAdminRespon> locTenSPShopThemHD(Integer pageNumber, Integer pageSize, String tensp) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return sanPhamRepository.locTenSPThemHD(pageable, tensp);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Page<SanPhamAdminRespon> locSPShopNTCThemHD(Integer pageNumber, Integer pageSize, String tendanhmuc, String tenmausac, String tensize, String tenchatlieu, String tenxuatxu, String tenthuonghieu) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return sanPhamRepository.locSPShopNTCThemHoaDon(pageable, tendanhmuc, tenmausac, tensize, tenchatlieu,tenxuatxu, tenthuonghieu );
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
