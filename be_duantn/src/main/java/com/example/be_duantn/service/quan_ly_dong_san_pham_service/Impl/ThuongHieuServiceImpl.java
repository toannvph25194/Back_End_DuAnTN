package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ThuongHieuRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ThuongHieuRespon;
import com.example.be_duantn.entity.ThuongHieu;
import com.example.be_duantn.entity.XuatXu;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.ThuongHieuRepository;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.ThuongHieuService;
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
public class ThuongHieuServiceImpl implements ThuongHieuService {
    @Autowired
    ThuongHieuRepository thuongHieuRepository;

    @Override
    public Page<ThuongHieuRespon> getThuongHIeu(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(page, 9);

            return thuongHieuRepository.Getallthuonghieu(pageable);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public ThuongHieu addThuongHieu(ThuongHieuRequest thuonghieu) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            ThuongHieu thuongHieu = new ThuongHieu();
            thuongHieu.setTenthuonghieu(thuonghieu.getTenthuonghieu());
            thuongHieu.setMota(thuonghieu.getMota());
            thuongHieu.setTrangthai(thuonghieu.getTrangthai());

            return thuongHieuRepository.save(thuongHieu);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Optional<ThuongHieuRespon> getthuonghieuById(UUID id) {
        return thuongHieuRepository.Getallthuonghieutheoid(id);
    }

    @Override
    public ThuongHieu updateThuonghieu(UUID id, ThuongHieuRequest thuongHieuRequest) {
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Size không tìm thấy với id :: " + id));

        thuongHieu.setTenthuonghieu(thuongHieuRequest.getTenthuonghieu());
        thuongHieu.setMota(thuongHieuRequest.getMota());
        thuongHieu.setTrangthai(thuongHieuRequest.getTrangthai());
        return thuongHieuRepository.save(thuongHieu);
    }

    @Override
    public void deleteThuonghieu(UUID id) {

    }

    @Override
    public ThuongHieu chuyenTrangThai(UUID id, Integer trangThaiMoi) {

        Optional<ThuongHieu> thuongHieu = thuongHieuRepository.findById(id);
        if (thuongHieu.isPresent()) {
            ThuongHieu thuongHieu1 = thuongHieu.get();
            thuongHieu1.setTrangthai(trangThaiMoi);
            return thuongHieuRepository.save(thuongHieu1);
        } else {
            throw new IllegalArgumentException("Không tìm thấy thương hiệu với ID: " + id);
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
