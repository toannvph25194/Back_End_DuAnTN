package com.example.be_duantn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "hinhthucthanhtoan")
public class HinhThucThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID id;

    @Column(name = "magiaodich")
    private String magiaodich;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaythanhtoan")
    private Date ngaythanhtoan = new Date();

    @Column(name = "sotientra")
    private Double sotientra;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaytao")
    private Date ngaytao = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaycapnhat")
    private Date ngaycapnhat;

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "trangthai")
    private Integer trangthai;

    @Column(name = "hinhthucthanhtoan")
    private Integer hinhthucthanhtoan;

    @ManyToOne
    @JoinColumn(name = "idhd")
    @JsonBackReference
    HoaDon hoadon;

    @ManyToOne
    @JoinColumn(name = "idnv")
    @JsonBackReference
    NhanVien nhanvien;

    @ManyToOne
    @JoinColumn(name = "idkh")
    @JsonBackReference
    KhachHang khachhang;

}
