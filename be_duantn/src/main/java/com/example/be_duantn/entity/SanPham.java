package com.example.be_duantn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "sanpham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID idsp;

    @Column(name = "masp")
    private String masp;

    @Column(name = "tensp")
    private String tensp;

    @Column(name = "theloai")
    private Integer theloai;

    @Column(name = "imagedefaul")
    private String imagedefaul;

    @Column(name = "gianhap")
    private Double gianhap;

    @Column(name = "dongiakhigiam")
    private Double dongiakhigiam;

    @Column(name = "giaban")
    private Double giaban;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaythem")
    private Date ngaythem = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaythemgiamgia")
    private Date ngaythemgiamgia;

    @Column(name = "mota")
    private String mota;

    @Column(name = "trangthai")
    private Integer trangthai;

    // TODO N - 1 //
    @ManyToOne
    @JoinColumn(name = "iddm")
    @JsonBackReference
    DanhMuc danhmuc;

    @ManyToOne
    @JoinColumn(name = "idth")
    @JsonBackReference
    ThuongHieu thuonghieu;

    @ManyToOne
    @JoinColumn(name = "idxx")
    @JsonBackReference
    XuatXu xuatxu;

    @ManyToOne
    @JoinColumn(name = "idcl")
    @JsonBackReference
    ChatLieu chatlieu;

    @ManyToOne
    @JoinColumn(name = "idgg")
    @JsonBackReference
    GiamGia giamgia;

    // TODO 1 - N //
    @OneToMany(mappedBy = "sanpham", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Image> image;

    @OneToMany(mappedBy = "sanpham", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<SanPhamChiTiet> sanphamchitiet;
}
