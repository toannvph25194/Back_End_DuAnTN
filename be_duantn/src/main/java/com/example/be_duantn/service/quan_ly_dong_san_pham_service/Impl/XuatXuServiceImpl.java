package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.XuatXuRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.XuatXuRespon;
import com.example.be_duantn.entity.XuatXu;
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
    public Page<XuatXuRespon> GetAllXuatXu(Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Pageable pageable = PageRequest.of(page, 10);
            return xuatXuRepository.GetAllXuatXu(pageable);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public Optional<XuatXuRespon> FindByXuatXuID(UUID id) {
        return xuatXuRepository.FindByXuatXuID(id);
    }

    @Override
    public XuatXu AddXuatXu(XuatXuRequest xuatXu) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {

            XuatXu xuatXu1 = new XuatXu();
            xuatXu1.setTenxuatxu(xuatXu.getTenxuatxu());
            xuatXu1.setMota(xuatXu.getMota());
            xuatXu.setTrangthai(xuatXu.getTrangthai());

            return xuatXuRepository.save(xuatXu1);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public XuatXu UpdateXuatXu(XuatXuRequest xuatXuRequest) {
        XuatXu xuatXu = xuatXuRepository.findById(xuatXuRequest.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xuất xứ với Id : " + xuatXuRequest.getId()));

        xuatXu.setTenxuatxu(xuatXuRequest.getTenxuatxu());
        xuatXu.setMota(xuatXuRequest.getMota());
        xuatXu.setTrangthai(xuatXuRequest.getTrangthai());
        return xuatXuRepository.save(xuatXu);
    }

    @Override
    public XuatXu ChuyenTrangThai(UUID id, Integer trangThaiMoi) {
        Optional<XuatXu> xuatXu = xuatXuRepository.findById(id);
        if (xuatXu.isPresent()) {
            XuatXu xuatXu1 = xuatXu.get();
            xuatXu1.setTrangthai(trangThaiMoi);
            return xuatXuRepository.save(xuatXu1);
        } else {
            throw new IllegalArgumentException("Không tìm thấy xuất xứ với Id : " + id);
        }
    }
<<<<<<< Updated upstream

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

    @Override
    public List<XuatXuRespon> getXuatXuLoadComboBox() {
        return xuatXuRepository.GetallxuatxuLoadCombobox();

    }

=======
>>>>>>> Stashed changes
    private boolean hasPermission(Collection<? extends GrantedAuthority> authorities, String... requiredRoles) {
        for (String requiredRole : requiredRoles) {
            if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals(requiredRole))) {
                return true;
            }
        }
        return false;
    }
}
