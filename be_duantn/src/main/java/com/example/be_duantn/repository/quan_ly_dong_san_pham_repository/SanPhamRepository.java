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
            "INNER JOIN\n" +
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
            "    sp.TrangThai\n", nativeQuery = true)
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
            "JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms ON ms.Id = spct.IdMS\n" +
            "JOIN Size s ON s.Id = spct.IdSize\n" +
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
            "JOIN SanPhamChiTiet spct ON spct.IdSP = sp.Id\n" +
            "JOIN MauSac ms ON ms.Id = spct.IdMS\n" +
            "JOIN Size s ON s.Id = spct.IdSize\n" +
            "WHERE dm.TenDanhMuc LIKE :tendanhmuc OR ms.TenMauSac LIKE :tenmausac OR s.TenSize LIKE :tensize OR cl.TenChatLieu LIKE :tenchatlieu OR xx.TenXuatXu LIKE :tenxuatxu OR th.TenThuongHieu LIKE :tenthuonghieu\n" +
            "GROUP BY sp.Id, sp.MaSP, sp.TenSP, sp.TheLoai, sp.ImageDefaul, sp.GiaBan, sp.GiaNhap, sp.TrangThai", nativeQuery = true)
    Page<SanPhamAdminRespon> locSPShopNTC(Pageable pageable, @Param("tendanhmuc") String tendanhmuc,
                                          @Param("tenmausac") String tenmausac,
                                          @Param("tensize") String tensize,
                                          @Param("tenchatlieu") String tenchatlieu,
                                          @Param("tenxuatxu") String tenxuatxu,
                                          @Param("tenthuonghieu") String tenthuonghieu


    );
}
