package com.example.be_duantn.service.quan_ly_giam_gia_service;

import com.example.be_duantn.dto.request.quan_ly_giam_gia_request.MaGiamGiaRequest;
import com.example.be_duantn.dto.request.quan_ly_giam_gia_request.VouCherAdminRequest;
import com.example.be_duantn.dto.respon.quan_ly_giam_gia_respon.VouCherAdminRespon;
import com.example.be_duantn.entity.GiamGia;
import com.example.be_duantn.entity.VouCher;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface VouCherServiceAdmin {
    // Hiển thị danh sách voucher
    Page<VouCherAdminRespon> GetAllVoucher(Integer page);

    // Lọc theo ma
    Page<VouCherAdminRespon> TimKiemTheoMa(Integer pageNumber, Integer pageSize, String mavoucher);

    // lọc khoảng ngày
    Page<VouCherAdminRespon> TimKiemTheoKhoangNgay(Integer pageNumber, Integer pageSize, Date startDate, Date endDate);

    // Findby giảm giá theo id
    Optional<VouCherAdminRespon> FindByVoucherID(UUID id);

    // Thêm voucher
    VouCher AddVoucher(VouCherAdminRequest vouCherAdminRequest);

    // update trạng thái voucher
    VouCher UpdateTrangThai(UUID id);

    // update voucher
    VouCher UpdateVoucher(VouCherAdminRequest vouCherAdminRequest, UUID id);


}
