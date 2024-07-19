package com.example.be_duantn.service.quan_ly_dong_san_pham_service.Impl;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ImageRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ImageRespon;
import com.example.be_duantn.entity.Image;
import com.example.be_duantn.entity.SanPham;
import com.example.be_duantn.repository.quan_ly_dong_san_pham_repository.ImageRepository;
import com.example.be_duantn.service.quan_ly_dong_san_pham_service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Override
    public List<ImageRespon> getImage(UUID IdSP) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            return imageRepository.Getallimage(IdSP);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Bạn không có quyền");
        }
    }

    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public List<Image> addImage(List<ImageRequest> imageRequests) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xử lý tùy thuộc vào quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xử lý cho người dùng có quyền "ADMIN" hoặc "NHANVIEN"

            List<Image> savedImages = new ArrayList<>();

            for (ImageRequest image : imageRequests) {
                Image newImage = new Image();
                newImage.setTenimage(image.getTenimage());
                newImage.setTrangthai(image.getTrangthai());

                SanPham sanPham = new SanPham();
                sanPham.setIdsp(image.getSanpham());
                newImage.setSanpham(sanPham);

                savedImages.add(imageRepository.save(newImage));
            }
            return savedImages;

        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public Image deleteImageById(UUID imageId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra quyền của người dùng và thực hiện xóa ảnh nếu có quyền
        if (hasPermission(authentication.getAuthorities(), "ADMIN", "NHANVIEN")) {
            // Thực hiện xóa ảnh bởi ID nếu người dùng có quyền "ADMIN" hoặc "NHANVIEN"
            Optional<Image> imageOptional = imageRepository.findById(imageId);
                return imageOptional.map(o->{
                    imageRepository.delete(o);
                    return o;
                }).orElse(null);
        } else {
            // Người dùng không có quyền, xử lý theo ý của bạn
            throw new AccessDeniedException("Access denied");
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
