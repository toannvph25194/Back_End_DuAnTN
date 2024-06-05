package com.example.be_duantn.service.quan_ly_hoa_don_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_hoa_don_request.HinhThucThanhToanAdminRequest;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HinhThucThanhToanRespon;
import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HinhThucThanhToanAdminRepository;
import com.example.be_duantn.service.quan_ly_hoa_don_service.HinhThucThanhToanAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@Service
public class HinhThucThanhToanAdminAdminServiceImpl implements HinhThucThanhToanAdminService {
    @Autowired
    HinhThucThanhToanAdminRepository hinhThucThanhToanAdminRepository;

    @Override
    public HinhThucThanhToanRespon finByIdHTTT(UUID IdHD) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return hinhThucThanhToanAdminRepository.finByidHTTT(IdHD);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public HinhThucThanhToan updateHTTT(UUID IDHTT, HinhThucThanhToanAdminRequest hinhThucThanhToanAdminRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Kiểm tra và gán các giá trị từ Optional vào đối tượng SanPham
            HinhThucThanhToan hinhThucThanhToan = hinhThucThanhToanAdminRepository.findById(IDHTT)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + IDHTT));

            // Log trạng thái hiện tại của hoaDon
            System.out.println("Trước khi cập nhật: " + hinhThucThanhToan);

            // Cập nhật các thông tin của hóa đơn
            hinhThucThanhToan.setGhichu("Khách hàng đã thanh toán");
            hinhThucThanhToan.setNgaycapnhat(timestamp);
            if (hinhThucThanhToan.getNgaythanhtoan() == null) {
                hinhThucThanhToan.setNgaythanhtoan(timestamp);
            }

            // Log trạng thái sau khi cập nhật nhưng trước khi lưu
            System.out.println("Sau khi cập nhật nhưng trước khi lưu: " + hinhThucThanhToan);

            HinhThucThanhToan thucThanhToan = hinhThucThanhToanAdminRepository.save(hinhThucThanhToan); // Lưu hóa đơn đã cập nhật và trả về nó

            // Log trạng thái sau khi lưu
            System.out.println("Sau khi lưu: " + thucThanhToan);

            return thucThanhToan;

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
