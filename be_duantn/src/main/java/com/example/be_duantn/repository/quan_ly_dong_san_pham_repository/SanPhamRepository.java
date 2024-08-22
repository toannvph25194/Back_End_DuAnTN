package com.example.be_duantn.repository.quan_ly_dong_san_pham_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.SanPhamShopRespon;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.SanPhamAdminRespon;
import com.example.be_duantn.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, UUID> {
    Optional<SanPham> findByMasp(String masp);

    @Query(value = "SELECT \n" +
            "    sp.Id, \n" +
            "    sp.MaSP, \n" +
            "    sp.TenSP, \n" +
            "    sp.TheLoai, \n" +
            "    sp.ImageDefaul, \n" +
            "    sp.GiaBan, \n" +
            "    sp.GiaNhap, \n" +
            "    sp.NgayThem, \n" +
            "    sp.TrangThai\n" +
            "FROM     \n" +
            "    dbo.SanPham sp\n" +
            "LEFT JOIN\n" +
            "    dbo.SanPhamChiTiet spc ON sp.Id = spc.IdSP\n" +
            "GROUP BY \n" +
            "    sp.Id, \n" +
            "    sp.MaSP, \n" +
            "    sp.TenSP, \n" +
            "    sp.TheLoai, \n" +
            "    sp.ImageDefaul, \n" +
            "    sp.GiaBan, \n" +
            "    sp.GiaNhap, \n" +
            "    sp.NgayThem, \n" +
            "    sp.TrangThai\n" +
            "ORDER BY \n" +
            "    sp.NgayThem DESC;\n", nativeQuery = true)
    Page<SanPhamAdminRespon> ShowSanPham(Pageable pageable);

    // tìm theo idsp
    @Query(value = "SELECT dbo.SanPham.Id, dbo.SanPham.IdDM AS danhmuc, dbo.SanPham.IdTH AS thuonghieu, dbo.SanPham.IdXX AS xuatxu, dbo.SanPham.IdCL AS chatlieu, dbo.SanPham.MaSP, dbo.SanPham.TenSP, dbo.SanPham.TheLoai, dbo.SanPham.ImageDefaul, dbo.SanPham.GiaNhap, \n" +
            "                  dbo.SanPham.GiaBan, dbo.SanPham.NgayThem, dbo.SanPham.MoTa AS motasp, dbo.SanPham.TrangThai, dbo.ChatLieu.TenChatLieu, dbo.XuatXu.TenXuatXu, dbo.DanhMuc.TenDanhMuc, dbo.ThuongHieu.TenThuongHieu\n" +
            "FROM     dbo.ChatLieu INNER JOIN\n" +
            "                  dbo.SanPham ON dbo.ChatLieu.Id = dbo.SanPham.IdCL INNER JOIN\n" +
            "                  dbo.XuatXu ON dbo.SanPham.IdXX = dbo.XuatXu.Id INNER JOIN\n" +
            "                  dbo.ThuongHieu ON dbo.SanPham.IdTH = dbo.ThuongHieu.Id INNER JOIN\n" +
            "                  dbo.DanhMuc ON dbo.SanPham.IdDM = dbo.DanhMuc.Id where dbo.SanPham.Id = ? ", nativeQuery = true)
    List<SanPhamAdminRespon> ShowSanPhamTheoId(UUID idsp);

    // Lọc theo tensp
    @Query(value = "SELECT COUNT(DISTINCT sp.Id), sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan, sp.GiaNhap, sp.TrangThai, sp.NgayThem\n" +
            "FROM SanPham sp\n" +
            "JOIN DanhMuc dm ON dm.Id = sp.IdDM\n" +
            "JOIN XuatXu xx ON xx.Id = sp.IdXX\n" +
            "JOIN ThuongHieu th ON th.Id = sp.IdTH\n" +
            "JOIN ChatLieu cl ON cl.Id = sp.IdCL\n" +
            "LEFT JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "LEFT JOIN MauSac ms ON ms.Id = spct.IdMS\n" +
            "LEFT JOIN Size s ON s.Id = spct.IdSize\n" +
            "WHERE sp.TenSP LIKE %:tensp%\n" +
            "GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan, sp.GiaNhap, sp.TrangThai, sp.NgayThem\n", nativeQuery = true)
    Page<SanPhamAdminRespon> locTenSP(Pageable pageable, @Param("tensp") String tensp);

    // Lọc theo nhiều tiêu chí. tendanhmuc, tenmausac, tensize
    @Query(value = "SELECT COUNT(DISTINCT sp.Id), sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan, sp.GiaNhap, sp.TrangThai\n" +
            "FROM SanPham sp\n" +
            "JOIN DanhMuc dm ON dm.Id = sp.IdDM\n" +
            "JOIN XuatXu xx ON xx.Id = sp.IdXX\n" +
            "JOIN ThuongHieu th ON th.Id = sp.IdTH\n" +
            "JOIN ChatLieu cl ON cl.Id = sp.IdCL\n" +
            "LEFT JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "LEFT JOIN MauSac ms ON ms.Id = spct.IdMS\n" +
            "LEFT JOIN Size s ON s.Id = spct.IdSize\n" +
            "WHERE dm.TenDanhMuc LIKE :tendanhmuc OR ms.TenMauSac LIKE :tenmausac OR s.TenSize LIKE :tensize OR cl.TenChatLieu LIKE :tenchatlieu OR xx.TenXuatXu LIKE :tenxuatxu OR th.TenThuongHieu LIKE :tenthuonghieu\n" +
            "GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan, sp.GiaNhap, sp.TrangThai", nativeQuery = true)
    Page<SanPhamAdminRespon> locSPShopNTC(Pageable pageable, @Param("tendanhmuc") String tendanhmuc,
                                          @Param("tenmausac") String tenmausac,
                                          @Param("tensize") String tensize,
                                          @Param("tenchatlieu") String tenchatlieu,
                                          @Param("tenxuatxu") String tenxuatxu,
                                          @Param("tenthuonghieu") String tenthuonghieu


    );

    @Query(value = "SELECT \n" +
            "    COUNT(DISTINCT sp.Id) AS TotalProducts,\n" +
            "    sp.Id,\n" +
            "    sp.MaSP,\n" +
            "    sp.TenSP,\n" +
            "    sp.ImageDefaul,\n" +
            "    sp.TheLoai,\n" +
            "    sp.GiaBan\n" +
            "FROM \n" +
            "    SanPham sp\n" +
            "JOIN \n" +
            "    DanhMuc dm ON dm.Id = sp.IdDM\n" +
            "JOIN \n" +
            "    XuatXu xx ON xx.Id = sp.IdXX\n" +
            "JOIN \n" +
            "    ThuongHieu th ON th.Id = sp.IdTH\n" +
            "JOIN \n" +
            "    ChatLieu cl ON cl.Id = sp.IdCL\n" +
            "JOIN \n" +
            "    SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN \n" +
            "    MauSac ms ON ms.Id = spct.IdMS\n" +
            "JOIN \n" +
            "    Size s ON s.Id = spct.IdSize\n" +
            "WHERE \n" +
            "    sp.TrangThai = 1 \n" +
            "    AND spct.SoLuongTon > 0 \n" +
            "    AND dm.TrangThai = 1 \n" +
            "    AND xx.TrangThai = 1 \n" +
            "    AND th.TrangThai = 1\n" +
            "    AND cl.TrangThai = 1 \n" +
            "    AND ms.TrangThai = 1 \n" +
            "    AND s.TrangThai = 1\n" +
            "GROUP BY \n" +
            "    sp.Id, \n" +
            "    sp.MaSP, \n" +
            "    sp.TenSP, \n" +
            "    sp.ImageDefaul, \n" +
            "    sp.TheLoai, \n" +
            "    sp.GiaBan;", nativeQuery = true)
    Page<SanPhamAdminRespon> ShowSanPhamThemHoaDon(Pageable pageable);

    // Lọc theo tenspThemhd
    @Query(value = "SELECT COUNT(DISTINCT sp.Id), sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan \n" +
            "FROM SanPham sp\n" +
            "JOIN DanhMuc dm ON dm.Id = sp.IdDM\n" +
            "JOIN XuatXu xx ON xx.Id = sp.IdXX\n" +
            "JOIN ThuongHieu th ON th.Id = sp.IdTH\n" +
            "JOIN ChatLieu cl ON cl.Id = sp.IdCL\n" +
            "JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms ON ms.Id = spct.IdMS\n" +
            "JOIN Size s ON s.Id = spct.IdSize\n" +
            "WHERE spct.SoLuongTon > 0 AND sp.TrangThai = 1 AND dm.TrangThai = 1\n" +
            "AND xx.TrangThai = 1 AND th.TrangThai = 1 AND cl.TrangThai = 1\n" +
            "AND ms.TrangThai = 1 AND s.TrangThai = 1\n" +
            "AND sp.TenSP LIKE CONCAT('%', :tensp, '%')\n" +
            "GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan;", nativeQuery = true)
    Page<SanPhamAdminRespon> locTenSPThemHD(Pageable pageable, @Param("tensp") String tensp);

    // Lọc theo nhiều tiêu chí. tendanhmuc, tenmausac, tensize thêm hoá đơn
    @Query(value = "SELECT COUNT(DISTINCT sp.Id), sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan \n" +
            "FROM SanPham sp\n" +
            "JOIN DanhMuc dm ON dm.Id = sp.IdDM\n" +
            "JOIN XuatXu xx ON xx.Id = sp.IdXX\n" +
            "JOIN ThuongHieu th ON th.Id = sp.IdTH\n" +
            "JOIN ChatLieu cl ON cl.Id = sp.IdCL\n" +
            "JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms ON ms.Id = spct.IdMS\n" +
            "JOIN Size s ON s.Id = spct.IdSize\n" +
            "WHERE spct.SoLuongTon > 0 AND sp.TrangThai = 1 AND dm.TrangThai = 1\n" +
            "AND xx.TrangThai = 1 AND th.TrangThai = 1 AND cl.TrangThai = 1\n" +
            "AND ms.TrangThai = 1 AND s.TrangThai = 1\n" +
            "AND (dm.TenDanhMuc LIKE :tendanhmuc OR ms.TenMauSac LIKE :tenmausac\n" +
            "OR s.TenSize LIKE :tensize OR cl.TenChatLieu LIKE :tenchatlieu OR xx.TenXuatXu LIKE :tenxuatxu OR th.TenThuongHieu LIKE :tenthuonghieu )\n" +
            "GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan;\n", nativeQuery = true)
    Page<SanPhamAdminRespon> locSPShopNTCThemHoaDon(Pageable pageable, @Param("tendanhmuc") String tendanhmuc,
                                                    @Param("tenmausac") String tenmausac,
                                                    @Param("tensize") String tensize,
                                                    @Param("tenchatlieu") String tenchatlieu,
                                                    @Param("tenxuatxu") String tenxuatxu,
                                                    @Param("tenthuonghieu") String tenthuonghieu


    );
}
