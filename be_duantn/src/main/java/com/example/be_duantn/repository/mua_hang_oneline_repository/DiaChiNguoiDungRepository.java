package com.example.be_duantn.repository.mua_hang_oneline_repository;

import com.example.be_duantn.dto.respon.mua_hang_online_respon.DiaChiNguoiDungRespon;
import com.example.be_duantn.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DiaChiNguoiDungRepository extends JpaRepository<DiaChi, UUID> {

    // finby dịa chỉ người dùng theo idkh
    @Query(value = "SELECT dc.id, dc.DiaChiChiTiet, dc.TinhThanh, dc.QuanHuyen, dc.PhuongXa\n" +
            "FROM DiaChi dc JOIN KhachHang kh ON dc.IdKH = kh.Id  \n" +
            "Where dc.TrangThai = 1 AND kh.Id = ?", nativeQuery = true)
    List<DiaChiNguoiDungRespon> finByDiaChiNguoiDung(UUID idkh);
}
