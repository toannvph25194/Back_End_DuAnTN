package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SizeRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SizeRespon;
import com.example.be_duantn.entity.MauSac;
import com.example.be_duantn.entity.Size;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.DanhMucRepository;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.SizeRepository;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.SizeService;
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

public class SizeServiceImpl implements SizeService {
    @Autowired
    SizeRepository sizeRepository;

    @Override
    public Page<SizeRespon> getSize(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Pageable pageable = PageRequest.of(page, 9);
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return sizeRepository.GetAllSIze(pageable);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Optional<Size> getSizeById(UUID id) {
        return sizeRepository.findById(id);
    }

    @Override
    public Size createSize(SizeRequest sizeRequest) {
        Size size = Size.builder()
                .tensize(sizeRequest.getTensize())
                .mota(sizeRequest.getMota())
                .trangthai(sizeRequest.getTrangthai())
                .build();
        return sizeRepository.save(size);
    }

    @Override
    public Size updateSize(UUID id, SizeRequest sizeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Size không tìm thấy với id :: " + id));

        size.setTensize(sizeRequest.getTensize());
        size.setMota(sizeRequest.getMota());
        size.setTrangthai(sizeRequest.getTrangthai());
        return sizeRepository.save(size);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public void deleteSize(UUID id) {

    }
    @Override
    public Size chuyenTrangThai(UUID idsize, Integer trangThaiMoi) {


        Optional<Size> sizeOpt = sizeRepository.findById(idsize);
        if (sizeOpt.isPresent()) {
            Size size = sizeOpt.get();
            size.setTrangthai(trangThaiMoi);
            return sizeRepository.save(size);
        } else {
            throw new IllegalArgumentException("Không tìm thấy size  với ID: " + idsize);
        }
    }

    @Override
    public List<SizeRespon> getSizeLoadComboBox() {
        return sizeRepository.GetAllSizeLoadComboBox();

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
