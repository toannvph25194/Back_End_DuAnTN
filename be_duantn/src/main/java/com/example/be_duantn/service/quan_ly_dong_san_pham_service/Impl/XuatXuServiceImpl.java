package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.XuatXuRespon;
import com.example.be_duantn.entity.ThuongHieu;
import com.example.be_duantn.entity.XuatXu;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.ChatLieuRepository;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.XuatXuRepository;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.XuatXuService;
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
public class XuatXuServiceImpl implements XuatXuService {
    @Autowired
    XuatXuRepository xuatXuRepository;

    @Override
    public Page<XuatXuRespon> getXuatXu(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(page, 9);

            return xuatXuRepository.Getallxuatxu(pageable);
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
    @Override
    public Optional<XuatXuRespon> getSizeById(UUID id) {
        return xuatXuRepository.Getallxuatxutheoid(id);
    }
    @Override
    public XuatXu updateXuatxu(UUID id, XuatXuRequest sizeRequest) {
        XuatXu xuatXu = xuatXuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("không tìm thấy Xuất xứ với id :: " + id));

        xuatXu.setTenxuatxu(sizeRequest.getTenxuatxu());
        xuatXu.setMota(sizeRequest.getMota());
        xuatXu.setTrangthai(sizeRequest.getTrangthai());
        return xuatXuRepository.save(xuatXu);
    }

    @Override
    public void deleteXuatxu(UUID id) {

    }

    @Override
    public XuatXu chuyenTrangThai(UUID id, Integer trangThaiMoi) {

            Optional<XuatXu> xuatXu = xuatXuRepository.findById(id);
            if (xuatXu.isPresent()) {
                XuatXu xx = xuatXu.get();
                xx.setTrangthai(trangThaiMoi);
                return xuatXuRepository.save(xx);
            } else {
                throw new IllegalArgumentException("Không tìm thấy xuấtt xứ với ID: " + id);
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
