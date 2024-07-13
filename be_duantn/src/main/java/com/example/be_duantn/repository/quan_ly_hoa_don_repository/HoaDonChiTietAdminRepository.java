package com.example.be_duantn.repository.quan_ly_hoa_don_repository;

import com.example.be_duantn.dto.respon.ban_tai_quay_respon.LoadSPTaiQuayRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.HoaDonChiTietRespon;
import com.example.be_duantn.dto.respon.quan_ly_hoa_don_respon.Hoadonrespon;
import com.example.be_duantn.entity.GioHangChiTiet;
import com.example.be_duantn.entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface HoaDonChiTietAdminRepository extends JpaRepository<HoaDonChiTiet, UUID> {

    // detail hoá đơn bằng id sp
    @Query(value = "SELECT dbo.HoaDonChiTiet.*, dbo.SanPham.TenSP, dbo.SanPham.ImageDefaul, dbo.MauSac.TenMauSac, dbo.Size.TenSize\n" +
            "FROM     dbo.HoaDonChiTiet INNER JOIN\n" +
            "                  dbo.SanPhamChiTiet ON dbo.HoaDonChiTiet.IdSPCT = dbo.SanPhamChiTiet.Id INNER JOIN\n" +
            "                  dbo.SanPham ON dbo.SanPhamChiTiet.IdSP = dbo.SanPham.Id INNER JOIN\n" +
            "                  dbo.MauSac ON dbo.SanPhamChiTiet.IdMS = dbo.MauSac.Id INNER JOIN\n" +
            "                  dbo.Size ON dbo.SanPhamChiTiet.IdSize = dbo.Size.Id where dbo.HoaDonChiTiet.IdHD = ?", nativeQuery = true)
    List<HoaDonChiTietRespon> finByidHDCT(UUID idHD);

    @Query(value = "SELECT \n" +
            "    COUNT(spct.Id), \n" +
            "    spct.Id, \n" +
            "    sp.TenSP, \n" +
            "    ms.TenMauSac, \n" +
            "    s.TenSize, \n" +
            "    cl.TenChatLieu, \n" +
            "    sp.ImageDefaul, \n" +
            "    sp.TheLoai, \n" +
            "    spct.SoLuongTon, \n" +
            "    sp.TrangThai, \n" +
            "    sp.GiaBan, \n" +
            "    ISNULL(tgg.sotiengiamcuoicung, NULL) AS DonGiaKhiGiam\n" +
            "FROM \n" +
            "    SanPham sp\n" +
            "LEFT JOIN \n" +
            "    (\n" +
            "        SELECT \n" +
            "            spgg.IdSP, \n" +
            "            MIN(spgg.DonGiaKhiGiam) AS sotiengiamcuoicung\n" +
            "        FROM \n" +
            "            SPGiamGia spgg\n" +
            "        JOIN \n" +
            "            GiamGia gg ON spgg.IdGG = gg.Id\n" +
            "        WHERE \n" +
            "            GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc\n" +
            "        GROUP BY \n" +
            "            spgg.IdSP\n" +
            "    ) tgg ON sp.Id = tgg.IdSP\n" +
            "JOIN \n" +
            "    SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN \n" +
            "    MauSac ms ON ms.Id = spct.IdMS\n" +
            "JOIN \n" +
            "    Size s ON s.Id = spct.IdSize\n" +
            "JOIN \n" +
            "    ChatLieu cl ON cl.Id = sp.IdCL\n" +
            "JOIN \n" +
            "    XuatXu xx ON xx.Id = sp.IdXX\n" +
            "JOIN \n" +
            "    DanhMuc dm ON dm.Id = sp.IdDM\n" +
            "JOIN \n" +
            "    ThuongHieu th ON th.Id = sp.IdTH\n" +
            "WHERE \n" +
            "    spct.SoLuongTon > 0 \n" +
            "    AND sp.TrangThai = 1 \n" +
            "    AND spct.TrangThai = 1 \n" +
            "    AND ms.TrangThai = 1 \n" +
            "    AND s.TrangThai = 1 \n" +
            "    AND cl.TrangThai = 1 \n" +
            "    AND dm.TrangThai = 1 \n" +
            "    AND th.TrangThai = 1 \n" +
            "    AND xx.TrangThai = 1\n" +
            "GROUP BY \n" +
            "    spct.Id, \n" +
            "    sp.TenSP, \n" +
            "    ms.TenMauSac, \n" +
            "    s.TenSize, \n" +
            "    cl.TenChatLieu, \n" +
            "    sp.ImageDefaul, \n" +
            "    sp.TheLoai, \n" +
            "    spct.SoLuongTon, \n" +
            "    sp.TrangThai, \n" +
            "    sp.GiaBan, \n" +
            "    tgg.sotiengiamcuoicung\n",
            nativeQuery = true)
    Page<LoadSPTaiQuayRespon> LoadSPBanTaiQuay(Pageable pageable);


    // Lọc sản phẩm phân trang theo tên sản phẩm sửa hoá đơn
    @Query(value = "SELECT \n" +
            "    COUNT(spct.Id) AS SoLuongSanPhamChiTiet, \n" +
            "    spct.Id, \n" +
            "    sp.TenSP, \n" +
            "    ms.TenMauSac, \n" +
            "    s.TenSize, \n" +
            "    cl.TenChatLieu, \n" +
            "    sp.ImageDefaul, \n" +
            "    sp.TheLoai, \n" +
            "    spct.SoLuongTon, \n" +
            "    sp.GiaBan, \n" +
            "    ISNULL(tgg.sotiengiamcuoicung, NULL) AS DonGiaKhiGiam\n" +
            "FROM \n" +
            "    SanPham sp\n" +
            "LEFT JOIN \n" +
            "    (\n" +
            "        SELECT \n" +
            "            spgg.IdSP, \n" +
            "            MIN(spgg.DonGiaKhiGiam) AS sotiengiamcuoicung\n" +
            "        FROM \n" +
            "            SPGiamGia spgg\n" +
            "        JOIN \n" +
            "            GiamGia gg ON spgg.IdGG = gg.Id\n" +
            "        WHERE \n" +
            "            GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc\n" +
            "        GROUP BY \n" +
            "            spgg.IdSP\n" +
            "    ) tgg ON sp.Id = tgg.IdSP\n" +
            "JOIN \n" +
            "    SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN \n" +
            "    MauSac ms ON ms.Id = spct.IdMS\n" +
            "JOIN \n" +
            "    Size s ON s.Id = spct.IdSize\n" +
            "JOIN \n" +
            "    ChatLieu cl ON cl.Id = sp.IdCL\n" +
            "JOIN \n" +
            "    XuatXu xx ON xx.Id = sp.IdXX\n" +
            "JOIN \n" +
            "    DanhMuc dm ON dm.Id = sp.IdDM\n" +
            "JOIN \n" +
            "    ThuongHieu th ON th.Id = sp.IdTH\n" +
            "WHERE \n" +
            "    spct.SoLuongTon > 0 \n" +
            "    AND sp.TrangThai = 1 \n" +
            "    AND spct.TrangThai = 1 \n" +
            "    AND ms.TrangThai = 1\n" +
            "    AND s.TrangThai = 1 \n" +
            "    AND cl.TrangThai = 1 \n" +
            "    AND xx.TrangThai = 1\n" +
            "    AND dm.TrangThai = 1 \n" +
            "    AND th.TrangThai = 1 \n" +
            "    AND sp.TenSP LIKE %:tensp%\n" +
            "GROUP BY \n" +
            "    spct.Id, \n" +
            "    sp.TenSP, \n" +
            "    ms.TenMauSac, \n" +
            "    s.TenSize, \n" +
            "    cl.TenChatLieu, \n" +
            "    sp.ImageDefaul, \n" +
            "    sp.TheLoai, \n" +
            "    spct.SoLuongTon, \n" +
            "    sp.GiaBan, \n" +
            "    tgg.sotiengiamcuoicung\n", nativeQuery = true)
    Page<LoadSPTaiQuayRespon> LocTenSPSuaHoaDon(Pageable pageable, @Param("tensp") String tensp);

    // Lọc sản phẩm phân trang nhiều tiêu chí sửa hoá đơn
    @Query(value = "SELECT \n" +
            "    COUNT(spct.Id) AS SoLuongSanPhamChiTiet, \n" +
            "    spct.Id, \n" +
            "    sp.TenSP, \n" +
            "    ms.TenMauSac, \n" +
            "    s.TenSize, \n" +
            "    cl.TenChatLieu, \n" +
            "    sp.ImageDefaul, \n" +
            "    sp.TheLoai, \n" +
            "    spct.SoLuongTon, \n" +
            "    sp.GiaBan, \n" +
            "    ISNULL(tgg.sotiengiamcuoicung, NULL) AS DonGiaKhiGiam\n" +
            "FROM \n" +
            "    SanPham sp\n" +
            "LEFT JOIN \n" +
            "    (\n" +
            "        SELECT \n" +
            "            spgg.IdSP, \n" +
            "            MIN(spgg.DonGiaKhiGiam) AS sotiengiamcuoicung\n" +
            "        FROM \n" +
            "            SPGiamGia spgg\n" +
            "        JOIN \n" +
            "            GiamGia gg ON spgg.IdGG = gg.Id\n" +
            "        WHERE \n" +
            "            GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc\n" +
            "        GROUP BY \n" +
            "            spgg.IdSP\n" +
            "    ) tgg ON sp.Id = tgg.IdSP\n" +
            "JOIN \n" +
            "    SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN \n" +
            "    MauSac ms ON ms.Id = spct.IdMS\n" +
            "JOIN \n" +
            "    Size s ON s.Id = spct.IdSize\n" +
            "JOIN \n" +
            "    ChatLieu cl ON cl.Id = sp.IdCL\n" +
            "JOIN \n" +
            "    DanhMuc dm ON dm.Id = sp.IdDM\n" +
            "JOIN \n" +
            "    ThuongHieu th ON th.Id = sp.IdTH\n" +
            "JOIN \n" +
            "    XuatXu xx ON xx.Id = sp.IdXX\n" +
            "WHERE \n" +
            "    spct.SoLuongTon > 0 \n" +
            "    AND sp.TrangThai = 1 \n" +
            "    AND ms.TrangThai = 1\n" +
            "    AND s.TrangThai = 1 \n" +
            "    AND cl.TrangThai = 1 \n" +
            "    AND dm.TrangThai = 1 \n" +
            "    AND th.TrangThai = 1 \n" +
            "    AND xx.TrangThai = 1\n" +
            "    AND (\n" +
            "        ms.TenMauSac LIKE :tenmausac \n" +
            "        OR s.TenSize LIKE :tensize \n" +
            "        OR cl.TenChatLieu LIKE :tenchatlieu \n" +
            "        OR dm.TenDanhMuc LIKE :tendanhmuc \n" +
            "        OR th.TenThuongHieu LIKE :tenthuonghieu \n" +
            "        OR xx.TenXuatXu LIKE :tenxuatxu\n" +
            "    )\n" +
            "GROUP BY \n" +
            "    spct.Id, \n" +
            "    sp.TenSP, \n" +
            "    ms.TenMauSac, \n" +
            "    s.TenSize, \n" +
            "    cl.TenChatLieu, \n" +
            "    sp.ImageDefaul, \n" +
            "    sp.TheLoai, \n" +
            "    spct.SoLuongTon, \n" +
            "    sp.GiaBan, \n" +
            "    tgg.sotiengiamcuoicung\n", nativeQuery = true)
    Page<LoadSPTaiQuayRespon> LocSPNhieuTieuChiSuaHoaDon(Pageable pageable, @Param("tenmausac") String tenmausac, @Param("tensize") String tensize,
                                                         @Param("tenchatlieu") String tenchatlieu, @Param("tendanhmuc") String tendanhmuc,
                                                         @Param("tenthuonghieu") String tenthuonghieu, @Param("tenxuatxu") String tenxuatxu);

    // Tìm kiếm idgh và idspct trong ghct
    HoaDonChiTiet findByHoadonIdhoadonAndSanphamchitietIdspct(UUID idhd, UUID idspct);

    List<HoaDonChiTiet> findByHoadon_Idhoadon(UUID idhd);

}


