package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.MauSacRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.MauSacRespon;
import com.example.be_duantn.entity.MauSac;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.MauSacRepository;
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
    public Page<MauSacRespon> GetAllMauSac(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(page, 10);
            return mauSacRepository.GetAllMauSac(pageable);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Optional<MauSacRespon> FindByMauSacID(UUID id) {
        return mauSacRepository.FindByMauSacID(id);
    }

    @Override
    public MauSac AddMauSac(MauSacRequest mauSacRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {

            MauSac msm = new MauSac();
            msm.setTenmausac(mauSacRequest.getTenmausac());
            msm.setMota(mauSacRequest.getMota());
            msm.setTrangthai(mauSacRequest.getTrangthai());

            return mauSacRepository.save(msm);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public MauSac UpdateMauSac(MauSacRequest mauSacRequest) {
        MauSac mauSac = mauSacRepository.findById(mauSacRequest.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc với Id : " + mauSacRequest.getId()));

        mauSac.setTenmausac(mauSacRequest.getTenmausac());
        mauSac.setMota(mauSacRequest.getMota());
        mauSac.setTrangthai(mauSacRequest.getTrangthai());
        return mauSacRepository.save(mauSac);
    }

    @Override
    public MauSac ChuyenTrangThai(UUID id, Integer trangThaiMoi) {
        Optional<MauSac> mauSac = mauSacRepository.findById(id);
        if (mauSac.isPresent()) {
            MauSac mauSac1 = mauSac.get();
            mauSac1.setTrangthai(trangThaiMoi);
            return mauSacRepository.save(mauSac1);
        } else {
            throw new IllegalArgumentException("Không tìm thấy màu sắc với Id : " + id);
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

    @Override
    public List<MauSacRespon> GetAllMauSacLoadComboBox() {
        return mauSacRepository.GetAllMauSacLoadComboBox();
    }
}
