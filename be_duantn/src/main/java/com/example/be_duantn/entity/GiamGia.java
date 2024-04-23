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
@Table(name = "giamgia")
public class GiamGia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "id")
    private UUID idgiamgia;

    @Column(name = "magiamgia")
    private String magiamgia;

    @Column(name = "tengiamgia")
    private String tengiamgia;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaytao")
    private Date ngaytao = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaycapnhat")
    private Date ngaycapnhat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaybatdau")
    private Date ngaybatdau;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngayketthuc")
    private Date ngayketthuc;

    @Column(name = "hinhthucgiam")
    private Integer hinhthucgiam;

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "trangthai")
    private Integer trangthai;

    @OneToMany(mappedBy = "giamgia", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<SPGiamGia> spgiamgia;

}
