package com.example.be_duantn.repository.mua_hang_oneline_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.*;
import com.example.be_duantn.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet , UUID> {

    // load top 4 sp mới nhất
    @Query(value = "SELECT DISTINCT TOP 4 sp.Id,MaSP,TenSP,ImageDefaul,TheLoai,NgayThem,GiaBan from SanPham sp\n" +
            "JOIN DanhMuc dm on dm.Id = sp.IdDM\n" +
            "JOIN ChatLieu cl on cl.Id = sp.IdCL\n" +
            "JOIN ThuongHieu th on th.Id = sp.IdTH\n" +
            "JOIN XuatXu xx on xx.Id = sp.IdXX\n" +
            "JOIN SanPhamChiTiet spct on spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "Where dm.TrangThai = 1 AND cl.TrangThai = 1 AND th.TrangThai = 1\n" +
            "AND xx.TrangThai = 1 AND ms.TrangThai = 1 AND s.TrangThai = 1\n" +
            "ORDER BY sp.NgayThem DESC", nativeQuery = true)
    List<SanPhamRespon> loadSPCTNew();

    // detail sản phẩm bằng id sp
    @Query(value = "SELECT DISTINCT sp.Id, sp.TenSP, sp.TheLoai, sp.ImageDefaul, xx.TenXuatXu, th.TenThuongHieu, cl.TenChatLieu, sp.GiaBan,\n" +
            "            CASE WHEN tgg.sotiengiamcuoicung IS NOT NULL THEN tgg.sotiengiamcuoicung ELSE NULL END AS DonGiaKhiGiam\n" +
            "            FROM SanPham sp\n" +
            "            JOIN SanPhamChiTiet spct ON sp.Id = spct.IdSP\n" +
            "            JOIN XuatXu xx ON xx.Id = sp.IdXX\n" +
            "            JOIN ThuongHieu th ON th.Id = sp.IdTH\n" +
            "            JOIN ChatLieu cl ON cl.Id = sp.IdCL\n" +
            "            LEFT JOIN (\n" +
            "                SELECT spgg.IdSP, MIN(spgg.DonGiaKhiGiam) AS sotiengiamcuoicung FROM SPGiamGia spgg\n" +
            "                JOIN GiamGia gg ON spgg.IdGG = gg.Id\n" +
            "                WHERE GETDATE() >= gg.NgayBatDau AND GETDATE() <= gg.NgayKetThuc\n" +
            "                GROUP BY spgg.IdSP\n" +
            "            ) tgg ON sp.Id = tgg.IdSP\n" +
            "            WHERE sp.TrangThai = 1 AND spct.SoLuongTon > 0 AND sp.Id = ?", nativeQuery = true)
    SanPhamChiTietRespon finByidSP(UUID idsp);

    // load list img theo idsp
    @Query(value = "SELECT DISTINCT sp.Id, img.TenImage FROM SanPhamChiTiet spct\n" +
            "JOIN SanPham sp on sp.Id = spct.IdSP\n" +
            "JOIN Image img on img.IdSP = sp.Id\n" +
            "Where sp.TrangThai = 1 AND spct.SoLuongTon > 0\n" +
            "AND img.TrangThai = 1 AND sp.Id = ?1", nativeQuery = true)
    List<ListImageSPRespon> loadListImageSP(UUID idsp);

    // load màu sắc theo idsp
    @Query(value = "SELECT DISTINCT ms.id, ms.TenMauSac FROM MauSac ms \n" +
            "JOIN SanPhamChiTiet spct on spct.IdMS = ms.Id \n" +
            "JOIN SanPham sp on sp.Id = spct.IdSP\n" +
            "Where ms.TrangThai = 1 AND sp.Id = ?1", nativeQuery = true)
    List<LoadMauSacByIdSP> loadMauSacByIdSP(UUID idsp);

    // load size theo idsp
    @Query(value = "SELECT DISTINCT s.Id, s.TenSize FROM Size s \n" +
            "JOIN SanPhamChiTiet spct on spct.IdSize = s.Id \n" +
            "JOIN SanPham sp on sp.Id = spct.IdSP\n" +
            "Where s.TrangThai = 1 AND sp.Id = ?1", nativeQuery = true)
    List<LoadSizeByIdSP> loadSizeByIdSP(UUID idsp);

    // finByMauSac theo idsp và idsize
    @Query(value = "SELECT ms.TenMauSac FROM MauSac ms \n" +
            "JOIN SanPhamChiTiet spct on spct.IdMS = ms.Id \n" +
            "JOIN SanPham sp on sp.Id = spct.IdSP\n" +
            "JOIN Size s on s.Id = spct.IdSize\n" +
            "Where ms.TrangThai = 1 AND sp.Id = ? AND s.Id = ?", nativeQuery = true)
    List<FinMauSacByIdSPAndIdsize> finMauSacByIdSanPhamAndIdSize(UUID idsp, UUID idsize);

    // finSize theo idsp và idmausac
    @Query(value = "SELECT s.TenSize FROM Size s \n" +
            "JOIN SanPhamChiTiet spct on spct.IdSize = s.Id \n" +
            "JOIN SanPham sp on sp.Id = spct.IdSP\n" +
            "JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "Where ms.TrangThai = 1 AND sp.Id = ? AND ms.Id = ?", nativeQuery = true)
    List<FinSizeByIdSPAndIdMauSac> finSizeIdSanPhamAndIdMauSac(UUID idsp, UUID idmausac);

    // tính tổng tất cả SoLuongTon spct theo idsp
    @Query(value = "SELECT SUM(spct.SoLuongTon) AS TongSoLuongTonSP FROM SanPhamChiTiet spct\n" +
            "INNER JOIN SanPham sp on sp.Id = spct.IdSP\n" +
            "where spct.TrangThai = 1 AND sp.Id = ?",nativeQuery = true)
    TongSoLuongTonSPCTRespon tinhTongSoLuongTonSPCT(UUID idsp);

    // fin idspct and soluonton theo idsp, idmausac và idsize
    @Query(value = "SELECT spct.Id,SoLuongTon FROM SanPhamChiTiet spct\n" +
            "INNER JOIN SanPham sp on sp.id = spct.IdSP\n" +
            "INNER JOIN MauSac ms on ms.Id = spct.IdMS\n" +
            "INNER JOIN Size s on s.Id = spct.IdSize\n" +
            "Where sp.Id = ? AND ms.Id = ? AND s.Id = ?",nativeQuery = true)
    FinByIdSPCTAndSoLuongTon finByIdSPCTAndSoLuongTon(UUID idsp, UUID idms, UUID Idsize);

}
