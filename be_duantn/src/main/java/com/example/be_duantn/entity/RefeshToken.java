package com.example.be_duantn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "refeshtoken")
public class RefeshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer idtoken;

    @Column(name = "token")
    private String token;

    @Column(name = "thoigianhethan")
    private LocalDate thoigianhethan;

    @ManyToOne
    @JoinColumn(name = "idnv")
    @JsonBackReference
    NhanVien nhanvien;

    @ManyToOne
    @JoinColumn(name = "idkh")
    @JsonBackReference
    KhachHang khachhang;
}
