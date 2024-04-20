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
@Table(name = "diachi")
public class DiaChi {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID id;

    @Column(name = "diachichitiet")
    private String diachichitiet;

    @Column(name = "phuongxa")
    private String phuongxa;

    @Column(name = "quanhuyen")
    private String quanhuyen;

    @Column(name = "tinhthanh")
    private String tinhthanh;

    @Column(name = "quocgia")
    private String quocgia;

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

    @ManyToOne
    @JoinColumn(name = "idkh")
    @JsonBackReference
    KhachHang khachhang;
}
