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
@Table(name = "hoadon")
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID idhoadon;

    @Column(name = "mahoadon")
    private String mahoadon;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaytao")
    private Date ngaytao = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaythanhtoan")
    private Date ngaythanhtoan;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaycapnhat")
    private Date ngaycapnhat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaygiaohang")
    private Date ngaygiaohang;

    @Column(name = "donvigiaohang")
    private String donvigiaohang;

    @Column(name = "tennguoigiao")
    private String tennguoigiao;

    @Column(name = "sdtnguoigiao")
    private String sdtnguoigiao;

    @Column(name = "tiengiaohang")
    private Double tiengiaohang;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaynhanhang")
    private Date ngaynhanhang;

    @Column(name = "tennguoinhan")
    private String tennguoinhan;

    @Column(name = "sdtnguoinhan")
    private String sdtnguoinhan;

    @Column(name = "emailnguoinhan")
    private String emailnguoinhan;

    @Column(name = "diachinhanhang")
    private String diachinhanhang;

    @Column(name = "giatrigiam")
    private Double giatrigiam;

    @Column(name = "tienkhachtra")
    private Double tienkhachtra;

    @Column(name = "thanhtien")
    private Double thanhtien;

    @Column(name = "tienthua")
    private Double tienthua;

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "loaihoadon")
    private Integer loaihoadon;

    @Column(name = "trangthai")
    private Integer trangthai;


    // TODO N - 1 //
    @ManyToOne
    @JoinColumn(name = "idnv")
    @JsonBackReference
    NhanVien nhanvien;

    @ManyToOne
    @JoinColumn(name = "idkh")
    @JsonBackReference
    KhachHang khachhang;

    @ManyToOne
    @JoinColumn(name = "idvoucher")
    @JsonBackReference
    VouCher voucher;

    // TODO 1 - N //
    @OneToMany(mappedBy = "hoadon" , fetch = FetchType.LAZY)
    @JsonManagedReference
    List<HoaDonChiTiet> hoadonchitiet;

    @OneToMany(mappedBy = "hoadon" , fetch = FetchType.LAZY)
    @JsonManagedReference
    List<HinhThucThanhToan> hinhthucthanhtoan;

}
