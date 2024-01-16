package com.example.be_duantn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "nhanvien")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID id;

    @Column(name = "manv")
    private String manv;

    @Column(name = "hotennv")
    private String hotennv;

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

    @Column(name = "diachi")
    private String diachi;

    @Column(name = "trangthai")
    private Integer trangthai;

    // TODO N - 1 //
    @ManyToOne
    @JoinColumn(name = "idcv")
    @JsonBackReference
    ChucVu chucvu;

    // TODO 1 - N //
    @OneToMany(mappedBy = "nhanvien", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<RefeshToken> refeshtoken;

    @OneToMany(mappedBy = "nhanvien", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<HoaDon> hoadon;

    @OneToMany(mappedBy = "nhanvien", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<HinhThucThanhToan> hinhthucthanhtoan;

    @OneToMany(mappedBy = "nhanvien", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<GioHang> giohang;
}
