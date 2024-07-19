package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.DanhMucRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.DanhMucRespon;
import com.example.be_duantn.entity.ChatLieu;
import com.example.be_duantn.entity.DanhMuc;
import com.example.be_duantn.entity.ThuongHieu;
import com.example.be_duantn.entity.XuatXu;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.ChatLieuRepository;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.DanhMucRepository;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.DanhMucService;
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
public class DanhMucServiceImpl implements DanhMucService {
    @Autowired
    DanhMucRepository danhMucRepository;
    @Override
    public Page<DanhMucRespon> getDanhMuc(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(page, 9);

            return danhMucRepository.Getalldanhmuc(pageable);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public DanhMuc addDanhMuc(DanhMucRequest danhmuc) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            DanhMuc danhMuc = new DanhMuc();
            danhMuc.setTendanhmuc(danhmuc.getTendanhmuc());
            danhMuc.setMota(danhmuc.getMota());
            danhMuc.setTrangthai(danhmuc.getTrangthai());

            return danhMucRepository.save(danhMuc);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Optional<DanhMucRespon> getdanhmucById(UUID id) {
        return danhMucRepository.Getalldanhmuctheoid(id);
    }

    @Override
    public DanhMuc updateDanhmuc(UUID id, DanhMucRequest danhMucRequest) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" không tìm thấy danh mục với id :: " + id));

        danhMuc.setTendanhmuc(danhMucRequest.getTendanhmuc());
        danhMuc.setMota(danhMucRequest.getMota());
        danhMuc.setTrangthai(danhMucRequest.getTrangthai());
        return danhMucRepository.save(danhMuc);
    }

    @Override
    public void deleteDanhmuc(UUID id) {

    }

    @Override
    public DanhMuc chuyenTrangThai(UUID id, Integer trangThaiMoi) {
        Optional<DanhMuc> danhMuc = danhMucRepository.findById(id);
        if (danhMuc.isPresent()) {
            DanhMuc danhMuc1 = danhMuc.get();
            danhMuc1.setTrangthai(trangThaiMoi);
            return danhMucRepository.save(danhMuc1);
        } else {
            throw new IllegalArgumentException("Không tìm thấy danh mục với ID: " + id);
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
