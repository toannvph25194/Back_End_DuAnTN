package com.example.be_duantn.entity;

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
@Table(name = "voucher")
public class VouCher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID id;

    @Column(name = "mavoucher")
    private String mavoucher;

    @Column(name = "tenvoucher")
    private String tenvoucher;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaytao")
    private Date ngaytao = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaytbatdau")
    private Date ngaytbatdau;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaycapnhat")
    private Date ngaycapnhat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngayketthuc")
    private Date ngayketthuc;

    @Column(name = "soluongma")
    private Integer soluongma;

    @Column(name = "soluongdung")
    private Integer soluongdung;

    @Column(name = "giatrigiam")
    private Double giatrigiam;

    @Column(name = "dieukientoithieuhoadon")
    private Double dieukientoithieuhoadon;

    @Column(name = "hinhthucgian")
    private Integer hinhthucgian;

    @Column(name = "loaivoucher")
    private Integer loaivoucher;

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "trangthai")
    private Integer trangthai;

    @OneToMany(mappedBy = "voucher" , fetch = FetchType.LAZY)
    @JsonManagedReference
    List<HoaDon> hoadon;
}
