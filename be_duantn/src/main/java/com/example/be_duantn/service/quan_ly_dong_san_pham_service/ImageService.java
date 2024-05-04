package com.example.be_duantn.service.quan_ly_dong_san_pham_service;

import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.ImageRequest;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.SanPhamChiTietAdminRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.ImageRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SanPhamChiTietAdminRespon;
import com.example.be_duantn.entity.Image;
import com.example.be_duantn.entity.SanPhamChiTiet;

import java.util.List;
import java.util.UUID;

public interface ImageService {
    // hiển thị ảnh theo idsp
    public List<ImageRespon> getImage(UUID IdSP);
    // Thêm ảnh
    List<Image> addImage(List<ImageRequest> imageRequests);
    // Xóa ảnh bởi ID
    Image deleteImageById(UUID imageId);
}
