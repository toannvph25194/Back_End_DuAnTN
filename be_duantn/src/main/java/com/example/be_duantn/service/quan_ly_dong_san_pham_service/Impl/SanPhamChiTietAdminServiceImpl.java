package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamChiTietAdminRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SanPhamChiTietAdminRespon;
import com.example.be_duantn.entity.MauSac;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.entity.SanPhamChiTiet;
import com.example.be_duantn.entity.Size;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.SanPhamChiTietAdminRepository;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.SanPhamChiTietAdminService;
import jakarta.persistence.EntityNotFoundException;
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

public class SanPhamChiTietAdminServiceImpl implements SanPhamChiTietAdminService {
    @Autowired
    SanPhamChiTietAdminRepository sanPhamChiTietAdminRepository;

    @Override
    public List<SanPhamChiTietAdminRespon> getSanPhamCT(UUID IdSP) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return sanPhamChiTietAdminRepository.GetAllSanPhamChiTiet(IdSP);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public SanPhamChiTiet addSanPhamCT(SanPhamChiTietAdminRequest sanPhamChiTietAdminRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            SanPhamChiTiet sp = new SanPhamChiTiet();
            sp.setSoluongton(sanPhamChiTietAdminRequest.getSoluongton());
            sp.setMota(sanPhamChiTietAdminRequest.getMota());
            sp.setQrcode(sanPhamChiTietAdminRequest.getQrcode());
            sp.setTrangthai(sanPhamChiTietAdminRequest.getTrangthai());
            MauSac mauSac = new MauSac();
            mauSac.setIdmausac(sanPhamChiTietAdminRequest.getMausac());
            sp.setMausac(mauSac);
            Size size = new Size();
            size.setIdsize(sanPhamChiTietAdminRequest.getSize());
            sp.setSize(size);
            SanPham sanPham = new SanPham();
            sanPham.setIdsp(sanPhamChiTietAdminRequest.getSanpham());
            sp.setSanpham(sanPham);
            return sanPhamChiTietAdminRepository.save(sp);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public SanPhamChiTiet updateCTSP(UUID idspct, Integer trangthai) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            SanPhamChiTiet ctSP = sanPhamChiTietAdminRepository.findById(idspct)
                    .orElseThrow(() -> new EntityNotFoundException("Sản phẩm chi tiết không tồn tại!"));
            ctSP.setTrangthai(trangthai);
            return sanPhamChiTietAdminRepository.save(ctSP);
        } else {
            // Người dùng không có quyền, xử lý tùy ý (ví dụ: ném lỗi hoặc trả về null)
            throw new AccessDeniedException("Truy cập bị từ chối");
        }
    }

    @Override
    public SanPhamChiTiet updateCTSPsl(UUID idspct, Integer soluongton) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            SanPhamChiTiet ctSP = sanPhamChiTietAdminRepository.findById(idspct)
                    .orElseThrow(() -> new EntityNotFoundException("Sản phẩm chi tiết không tồn tại!"));
            ctSP.setSoluongton(soluongton);
            return sanPhamChiTietAdminRepository.save(ctSP);
        } else {
            // Người dùng không có quyền, xử lý tùy ý (ví dụ: ném lỗi hoặc trả về null)
            throw new AccessDeniedException("Truy cập bị từ chối");
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
