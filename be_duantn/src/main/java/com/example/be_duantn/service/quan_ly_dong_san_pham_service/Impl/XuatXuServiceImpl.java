package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.XuatXuRespon;
import com.example.be_duantn.entity.ThuongHieu;
import com.example.be_duantn.entity.XuatXu;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.ChatLieuRepository;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.XuatXuRepository;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.XuatXuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class XuatXuServiceImpl implements XuatXuService {
    @Autowired
    XuatXuRepository xuatXuRepository;

    @Override
    public List<XuatXuRespon> getXuatXu() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return xuatXuRepository.Getallxuatxu();
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public XuatXu addXuatXu(XuatXuRequest xuatxu) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            XuatXu xuatXu = new XuatXu();
            xuatXu.setTenxuatxu(xuatxu.getTenxuatxu());
            xuatXu.setMota(xuatxu.getMota());
            xuatXu.setTrangthai(xuatxu.getTrangthai());

            return xuatXuRepository.save(xuatXu);
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
