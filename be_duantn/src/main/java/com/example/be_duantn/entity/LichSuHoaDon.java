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
@Table(name = "lichsuhoadon")
public class LichSuHoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "idhd")
    private UUID idhd;

    @Column(name = "nguoithaotac")
    private String nguoithaotac;

    @Column(name = "ghichu")
    private String ghichu;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaytao")
    private Date ngaytao;

    @Column(name = "trangthai")
    private Integer trangthai;

    @ManyToOne
    @JoinColumn(name = "idhd", insertable = false, updatable = false)
    @JsonBackReference
    private HoaDon hoadon;

    @PrePersist
    protected void onCreate() {
        ngaytao = new Date();
    }
}
