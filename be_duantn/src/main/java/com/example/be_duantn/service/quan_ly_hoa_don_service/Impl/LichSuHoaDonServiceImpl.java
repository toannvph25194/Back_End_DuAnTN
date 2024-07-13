package com.example.be_duantn.service.quan_ly_hoa_don_service.Impl;

import com.example.be_duantn.entity.LichSuHoaDon;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.LichSuHoaDonRepository;
import com.example.be_duantn.service.quan_ly_hoa_don_service.LichSuHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class LichSuHoaDonServiceImpl implements LichSuHoaDonService {

    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;



    @Override
    public List<LichSuHoaDon> finByIdLSTT(UUID IdHD) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return lichSuHoaDonRepository.finByidLstt(IdHD);
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
