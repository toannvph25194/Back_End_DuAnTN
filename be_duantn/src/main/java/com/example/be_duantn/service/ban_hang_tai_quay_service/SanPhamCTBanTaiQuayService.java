package com.example.be_duantn.service.ban_hang_tai_quay_service;
import com.example.be_duantn.dto.respon.ban_tai_quay_respon.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SanPhamCTBanTaiQuayService {

    // Load sản phẩm phân trang lên bán hàng tại quầy
    Page<LoadSPTaiQuayRespon> LoadSPBanTaiQuay(Integer page);

    // Load màu săc lên bán hàng tại quầy
    List<LoadMSTaiQuayRespon> LoadMauSacBanTaiQuay();

    // Load size lên bán hàng tại quầy
    List<LoadSizeTaiQuayRespon> LoadSizeBanTaiQuay();

    // Load chất liệu lên bán hàng tại quầy
    List<LoadChatLieuTaiQuayRespon> LoadChatLieuBanTaiQuay();

    // Load danh mục lên bán hàng tại quầy
    List<LoadDanhMucTaiQuayRespon> LoadDanhMucBanTaiQuay();

    // Load thương hiệu lên bán hàng tại quầy
    List<LoadThuongHieuTaiQuayRespon> LoadThuongHieuBanTaiQuay();

    // Load xuất xứ lên bán hàng tại quầy
    List<LoadXuatXuTaiQuayRespon> LoadXuatXuBanTaiQuay();

    // Lọc sản phẩm phân trang theo tên sản phẩm bán hàng tạ quầy
    Page<LoadSPTaiQuayRespon> LocTenSPBanTaiQuay(Integer page, String tensp);

    // Lọc sản phẩm phân trang nhiều tiêu chí bán hàng tạ quầy
    Page<LoadSPTaiQuayRespon> LocSPNhieuTieuChiBanTaiQuay(Integer page, String tenmausac, String tensize, String tenchatlieu, String tendanhmuc, String tenthuonghieu, String tenxuatxu);
}
