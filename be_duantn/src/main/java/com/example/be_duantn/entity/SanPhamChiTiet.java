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
@Table(name = "sanphamchitiet")
public class SanPhamChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID id;

    @Column(name = "soluongton")
    private Integer soluongton;

    @Column(name = "mota")
    private String mota;

    @Column(name = "qrcode")
    private String qrcode;

    @Column(name = "trangthai")
    private Integer trangthai;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaytao")
    private Date ngaytao = new Date();

    // TODO N - 1 //
    @ManyToOne
    @JoinColumn(name = "idsp")
    @JsonBackReference
    SanPham sanpham;

    @ManyToOne
    @JoinColumn(name = "idms")
    @JsonBackReference
    MauSac mausac;

    @ManyToOne
    @JoinColumn(name = "idsize")
    @JsonBackReference
    Size size;

    // TODO 1 - N //
    @OneToMany(mappedBy = "sanphamchitiet" , fetch = FetchType.LAZY)
    @JsonManagedReference
    List<HoaDonChiTiet> hoadonchitiet;

    @OneToMany(mappedBy = "sanphamchitiet" , fetch = FetchType.LAZY)
    @JsonManagedReference
    List<GioHangChiTiet> giohangchitiet;
}
