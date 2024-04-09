package com.example.be_duantn.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "khachhang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID id;

    @Column(name = "makh")
    private String makh;

    @Column(name = "hovatenkh")
    private String hovatenkh;

    @Column(name = "gioitinh")
    private Boolean gioitinh;

    @Temporal(TemporalType.DATE)
    @Column(name = "ngaysinh")
    private String ngaysinh;

    @Column(name = "taikhoan")
    private String taikhoan;

    @Column(name = "matkhau")
    private String matkhau;

    @Column(name = "sodienthoai")
    private String sodienthoai;

    @Column(name = "email")
    private String email;

    @Column(name = "image")
    private String image;

    @Column(name = "mota")
    private String mota;

    @Column(name = "trangthai")
    private Integer trangthai;

    @OneToMany(mappedBy = "khachhang" , fetch = FetchType.LAZY)
    @JsonManagedReference
    List<DanhSachYeuThich> danhsachyeuthich;

    @OneToMany(mappedBy = "khachhang" , fetch = FetchType.LAZY)
    @JsonManagedReference
    List<DiaChi> diachi;

    @OneToMany(mappedBy = "khachhang" , fetch = FetchType.LAZY)
    @JsonManagedReference
    List<HoaDon> hoadon;

    @OneToMany(mappedBy = "khachhang" , fetch = FetchType.LAZY)
    @JsonManagedReference
    List<HinhThucThanhToan> hinhthucthanhtoan;

    @OneToMany(mappedBy = "khachhang" , fetch = FetchType.LAZY)
    @JsonManagedReference
    List<GioHang> giohang;

    // TODO 1 - N //
    @OneToMany(mappedBy = "khachhang", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<RefeshToken> refeshtoken;
}
