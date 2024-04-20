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
@Table(name = "giohangchitiet")
public class GioHangChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID id;

    @Column(name = "soluong")
    private Integer soluong;

    @Column(name = "dongia")
    private Double dongia;

    @Column(name = "dongiakhigiam")
    private Double dongiakhigiam;

    @Column(name = "sotiengiam")
    private Double sotiengiam;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaytao")
    private Date ngaytao= new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaycapnhat")
    private Date ngaycapnhat;

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "trangthai")
    private Integer trangthai;

    @ManyToOne
    @JoinColumn(name = "idgh")
    @JsonBackReference
    GioHang giohang;

    @ManyToOne
    @JoinColumn(name = "idspct")
    @JsonBackReference
    SanPhamChiTiet sanphamchitiet;
}
