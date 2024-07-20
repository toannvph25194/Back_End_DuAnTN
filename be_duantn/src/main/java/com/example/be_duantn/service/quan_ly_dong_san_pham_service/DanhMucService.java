package com.example.be_duantn.service.quan_ly_dong_san_pham_service;
;
import com.example.be_duantn.dto.request.quan_ly_dong_san_pham_request.DanhMucRequest;
import com.example.be_duantn.dto.respon.quan_ly_dong_san_pham_respon.DanhMucRespon;
import com.example.be_duantn.entity.DanhMuc;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DanhMucService {

    // Hiển thị ra danh sách danh mục
    Page<DanhMucRespon> GetAllDanhMuc(Integer page);

    // Findby danh mục theo id
    Optional<DanhMucRespon> FindByDanhMucID(UUID id);

    // Thêm danh mục
    DanhMuc AddDanhMuc(DanhMucRequest danhmuc);

    // Update danh mục theo id
    DanhMuc UpdateDanhMuc(DanhMucRequest danhMucRequest);

    // Chuyển trạng thái danh mục
    DanhMuc ChuyenTrangThai(UUID id, Integer trangThaiMoi);

    // Hiển thị danh sách danh muc load combobox
    List<DanhMucRespon> GetAllDanhMucLoadComboBox();
}
