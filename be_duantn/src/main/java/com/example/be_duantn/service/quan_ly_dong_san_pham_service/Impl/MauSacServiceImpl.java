package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.MauSacRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.MauSacRespon;
import com.example.be_duantn.entity.MauSac;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.DanhMucRepository;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.MauSacRepository;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.DanhMucService;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.MauSacService;
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

public class MauSacServiceImpl implements MauSacService {
    @Autowired
    MauSacRepository mauSacRepository;

    @Override
    public Page<MauSacRespon> getMauSac(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Pageable pageable = PageRequest.of(page, 9);
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return mauSacRepository.GetAllMauSac(pageable);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public List<MauSac> getAllMauSac() {
        return mauSacRepository.findAll();
    }

    @Override
    public Optional<MauSac> getMauSacById(UUID id) {
        return mauSacRepository.findById(id);
    }

    @Override
    public MauSac createMauSac(MauSacRequest mausacRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
        MauSac mausac = MauSac.builder()
                .tenmausac(mausacRequest.getTenmausac())
                .mota(mausacRequest.getMota())
                .trangthai(mausacRequest.getTrangthai())
                .build();
        return mauSacRepository.save(mausac);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public MauSac updateMauSac(UUID id, MauSacRequest mausacRequest) {
        MauSac existingMauSac = mauSacRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MauSac not found for this id :: " + id));

        existingMauSac.setTenmausac(mausacRequest.getTenmausac());
        existingMauSac.setMota(mausacRequest.getMota());
        existingMauSac.setTrangthai(mausacRequest.getTrangthai());

        return mauSacRepository.save(existingMauSac);
    }


    @Override
    public MauSac chuyenTrangThai(UUID idmausac, Integer trangThaiMoi) {


        Optional<MauSac> mauSacOpt = mauSacRepository.findById(idmausac);
        if (mauSacOpt.isPresent()) {
            MauSac mauSac = mauSacOpt.get();
            mauSac.setTrangthai(trangThaiMoi);
            return mauSacRepository.save(mauSac);
        } else {
            throw new IllegalArgumentException("Không tìm thấy màu sắc với ID: " + idmausac);
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
