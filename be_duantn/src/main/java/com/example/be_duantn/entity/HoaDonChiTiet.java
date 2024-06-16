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
@Table(name = "hoadonchitiet")
public class HoaDonChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID idhdct;

    @Column(name = "soluong")
    private Integer soluong;

    @Column(name = "dongia")
    private Double dongia;

    @Column(name = "dongiakhigiam")
    private Double dongiakhigiam;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaytao")
    private Date ngaytao = new Date();

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "trangthai")
    private Integer trangthai;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaycapnhat")
    private Date ngaycapnhat;

    @ManyToOne
    @JoinColumn(name = "idhd")
    @JsonBackReference
    HoaDon hoadon;

    @ManyToOne
    @JoinColumn(name = "idspct")
    @JsonBackReference
    SanPhamChiTiet sanphamchitiet;
}
