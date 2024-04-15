package com.example.be_duantn.service.mua_hang_online_service;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SanPhamShopService {

    // Load danh mục sp shop theo trạng thái
    List<DanhMucSPShopRespon> loadDanhMucSPShop();

    // Load màu sắc sp shop theo trạng thái
    List<MauSacSPShopRespon> loadMauSacSPShop();

    // Load size sp shop theo trạng thái
    List<SizeSPShopRespon> loadSizeSPShop();

    // Load sp shop phân trang
    Page<SanPhamShopRespon> loadSPShop(Integer page);

    // Lọc sp shop theo khoanggia
    Page<SanPhamShopRespon> locKhongGiaSPShop(Integer pageNumber, Integer pageSize, Double key1, Double key2);

    // Lọc theo nhiều tiêu chí . tensp, tendanhmuc, tenmausac, tensize
    Page<SanPhamShopRespon> locSPShop(Integer pageNumber, Integer pageSize, String tensp, String tendanhmuc,  String tenmausac, String tensize);
}
