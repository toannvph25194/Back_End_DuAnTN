package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SizeRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SizeRespon;
import com.example.be_duantn.entity.Size;
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
    public Page<SizeRespon> GetAllSize(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(page, 10);
            return sizeRepository.GetAllSize(pageable);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }


    @Override
    public Optional<SizeRespon> FindBySizeID(UUID id) {
        return sizeRepository.FindBySizeID(id);
    }

    @Override
    public Size AddSize(SizeRequest size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {

            Size size1 = new Size();
            size1.setTensize(size.getTensize());
            size1.setMota(size.getMota());
            size1.setTrangthai(size.getTrangthai());

            return sizeRepository.save(size1);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Size UpdateSize(SizeRequest sizeRequest) {
        Size size = sizeRepository.findById(sizeRequest.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy size với Id : " + sizeRequest.getId()));

        size.setTensize(sizeRequest.getTensize());
        size.setMota(sizeRequest.getMota());
        size.setTrangthai(sizeRequest.getTrangthai());
        return sizeRepository.save(size);
    }

    @Override
    public Size ChuyenTrangThai(UUID id, Integer trangThaiMoi) {
        Optional<Size> size = sizeRepository.findById(id);
        if (size.isPresent()) {
            Size size1 = size.get();
            size1.setTrangthai(trangThaiMoi);
            return sizeRepository.save(size1);
        } else {
            throw new IllegalArgumentException("Không tìm thấy size với Id : " + id);
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
    public List<SizeRespon> GetAllSizeLoadComboBox() {
        return sizeRepository.GetAllSizeLoadComboBox();

    }
}
