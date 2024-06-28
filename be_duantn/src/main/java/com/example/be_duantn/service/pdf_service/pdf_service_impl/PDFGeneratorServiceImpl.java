package com.example.be_duantn.service.pdf_service.pdf_service_impl;

import com.example.be_duantn.entity.HinhThucThanhToan;
import com.example.be_duantn.entity.HoaDon;
import com.example.be_duantn.entity.HoaDonChiTiet;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HoaDonAdminRepository;
import com.example.be_duantn.repository.quan_ly_hoa_don_repository.HoaDonChiTietAdminRepository;
import com.example.be_duantn.service.pdf_service.PDFGeneratorService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PDFGeneratorServiceImpl implements PDFGeneratorService {
    private static final String FONT_PATH = "D:\\DuAnTotNghiep\\Back_End\\be_duantn\\lib\\font\\oki.ttf";

    @Autowired
    private HoaDonAdminRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietAdminRepository hoaDonChiTietAdminRepository;

    @Override
    public void orderCouter(HttpServletResponse response, UUID idHoaDon) throws IOException {
        Optional<HoaDon> hoaDonOptional = hoaDonRepository.findById(idHoaDon);
        if (!hoaDonOptional.isPresent()) {
            throw new IllegalArgumentException("Hóa đơn không tồn tại");
        }

        HoaDon hoaDon = hoaDonOptional.get();
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDon.getHoadonchitiet();
        List<HinhThucThanhToan> hinhThucThanhToans = hoaDon.getHinhthucthanhtoan();

        // Create PDF document
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            BaseFont bf = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontTitle = new Font(bf, 18, Font.BOLD);
            Font fontParagraph = new Font(bf, 12, Font.NORMAL);
            Font fontBold = new Font(bf, 12, Font.BOLD);
            Font fontItalic = new Font(bf, 12, Font.ITALIC);

            // Header section
            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);
            PdfPCell headerCell = new PdfPCell(new Phrase("2TH-SHOP", fontTitle));
            headerCell.setBorder(Rectangle.NO_BORDER);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTable.addCell(headerCell);
            document.add(headerTable);

            Paragraph contactInfo = new Paragraph();
            contactInfo.setFont(fontParagraph);
            contactInfo.add(new Phrase("Số điện thoại: 0355346928\n", fontParagraph));
            contactInfo.add(new Phrase("Email: 2thshoppoly@gmail.com\n", fontParagraph));

            contactInfo.setAlignment(Element.ALIGN_CENTER);
            document.add(contactInfo);

            // Invoice title
            Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Invoice information
            Paragraph invoiceDetails = new Paragraph();
            invoiceDetails.setFont(fontParagraph);
            invoiceDetails.add(new Phrase("Mã hóa đơn: " + hoaDon.getMahoadon() + "\n", fontParagraph));
            invoiceDetails.add(new Phrase("Ngày mua: " + hoaDon.getNgaytao() + "\n", fontParagraph));
            invoiceDetails.add(new Phrase("Khách hàng: " + hoaDon.getTennguoinhan() + "\n", fontParagraph));
            if (hoaDon.getDiachinhanhang() != null) {
                invoiceDetails.add(new Phrase("Địa chỉ: " + hoaDon.getDiachinhanhang() + "\n", fontParagraph));
            }
            if (hoaDon.getSdtnguoinhan() != null) {
                invoiceDetails.add(new Phrase("Số điện thoại: " + hoaDon.getSdtnguoinhan() + "\n", fontParagraph));
            }
            if (hoaDon.getNhanvien() != null) {
                invoiceDetails.add(new Phrase("Nhân viên bán hàng: " + hoaDon.getNhanvien().getHovatennv() + "\n", fontParagraph));
            }
            String trangThai;
            switch (hoaDon.getTrangthai()) {
                case 1:
                    trangThai = "Chờ xác nhận";
                    break;
                case 2:
                    trangThai = "Đã xác nhận";
                    break;
                case 3:
                    trangThai = "Chờ giao";
                    break;
                case 4:
                    trangThai = "Đang giao";
                    break;
                case 5:
                    trangThai = "Hoàn thành";
                    break;
                case 6:
                    trangThai = "Hủy";
                    break;
                default:
                    trangThai = "Không xác định"; // Trạng thái mặc định nếu không khớp với bất kỳ giá trị nào
            }

            invoiceDetails.add(new Phrase("Trạng thái: " + trangThai + "\n", fontParagraph));
            document.add(invoiceDetails);

            // Thêm một dòng trống

            PdfPTable productTable = new PdfPTable(5);
            productTable.setWidthPercentage(100);
            productTable.setWidths(new int[]{1, 3, 1, 3, 2});
            document.add(new Phrase("Sản phẩm:", fontParagraph));
            String[] tableHeaders = {"STT", "Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
            for (String header : tableHeaders) {
                PdfPCell headerCellf = new PdfPCell(new Phrase(header, fontBold));
                headerCellf.setHorizontalAlignment(Element.ALIGN_CENTER);
                productTable.addCell(headerCellf);
            }

            // Table content
            int stt = 1;
            BigDecimal tongTienSanPham = BigDecimal.ZERO;
            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {
                productTable.addCell(Integer.toString(stt)); // STT
                productTable.addCell(new Phrase(hoaDonChiTiet.getSanphamchitiet().getSanpham().getTensp()+"-"+hoaDonChiTiet.getSanphamchitiet().getMausac().getTenmausac()+"-"+ hoaDonChiTiet.getSanphamchitiet().getSize().getTensize(), fontParagraph)); // Tên sản phẩm// Tên sản phẩm
                productTable.addCell(Integer.toString(hoaDonChiTiet.getSoluong())); // Số lượng

                // Assuming these are initialized correctly
                BigDecimal dongia = BigDecimal.valueOf(hoaDonChiTiet.getDongia());
                String giaTri;

                if (hoaDonChiTiet.getDongiakhigiam() == null) {
                    giaTri = dongia.toPlainString() + " VND";
                    productTable.addCell(giaTri);
                } else {
                    BigDecimal dongiakhigiam = BigDecimal.valueOf(hoaDonChiTiet.getDongiakhigiam());

                    // Create font with strikethrough for the original price
                    Font strikethroughFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.STRIKETHRU, BaseColor.GRAY);
                    Chunk dongiaChunk = new Chunk(dongia.toPlainString(), strikethroughFont);

                    // Create font for the discounted price
                    Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
                    Chunk dongiakhigiamChunk = new Chunk("/" + dongiakhigiam.toPlainString(), normalFont);

                    // Create font for VND
                    Chunk vndChunk = new Chunk(" VND", normalFont);

                    // Combine all chunks into a single Phrase
                    Phrase phrase = new Phrase();
                    phrase.add(dongiaChunk);
                    phrase.add(dongiakhigiamChunk);
                    phrase.add(vndChunk);

                    // Add the phrase to the table cell
                    PdfPCell cell = new PdfPCell(phrase);
                    productTable.addCell(cell);
                }


                BigDecimal thanhTien;
                if (hoaDonChiTiet.getDongiakhigiam() == null) {
                    thanhTien = new BigDecimal(hoaDonChiTiet.getDongia())
                            .multiply(new BigDecimal(hoaDonChiTiet.getSoluong()))
                            .setScale(1, RoundingMode.HALF_UP);
                } else {
                    thanhTien = new BigDecimal(hoaDonChiTiet.getDongiakhigiam())
                            .multiply(new BigDecimal(hoaDonChiTiet.getSoluong()))
                            .setScale(1, RoundingMode.HALF_UP);
                }
                productTable.addCell(thanhTien.toString() + " VND");


                tongTienSanPham = tongTienSanPham.add(thanhTien);
                stt++;
            }

            // Total amount
            Paragraph totalAmount = new Paragraph("Tổng tiền sản phẩm: " + tongTienSanPham + " VND", fontItalic);
            totalAmount.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalAmount);

            // Add tables to document
            document.add(productTable);
             // Thêm một dòng trống
            // Hình thức thanh toán
            PdfPTable productTable1 = new PdfPTable(5);
            productTable1.setWidthPercentage(100);
            productTable1.setWidths(new int[]{1, 2, 2, 2, 3});

            // Table headers
            String[] tableHeaders1 = {"STT", "Mã giao dịch", "số tiền", "hình thức", "ghi chú"};
            for (String header : tableHeaders1) {
                PdfPCell headerCellf = new PdfPCell(new Phrase(header, fontBold));
                headerCellf.setHorizontalAlignment(Element.ALIGN_CENTER);
                productTable1.addCell(headerCellf);
            }

            // Table content
            int stt1 = 1;
            for (HinhThucThanhToan hinhThucThanhToan : hinhThucThanhToans) {
                productTable1.addCell(Integer.toString(stt1)); // STT
                productTable1.addCell(hinhThucThanhToan.getMagiaodich()); // Tên sản phẩm// Tên sản phẩm
                productTable1.addCell(Double.toString(hinhThucThanhToan.getSotientra()) + " VND"); // Số lượng

                if (hinhThucThanhToan.getHinhthucthanhtoan() == 1) {
                    productTable1.addCell(new Phrase("Tiền mặt", fontParagraph));
                } else if (hinhThucThanhToan.getHinhthucthanhtoan() == 2) {
                    productTable1.addCell(new Phrase("Chuyển khoản", fontParagraph));
                } else if (hinhThucThanhToan.getHinhthucthanhtoan() == 3) {
                    productTable1.addCell(new Phrase("Hoàn tiền", fontParagraph));
                } else {
                    // Nếu giá trị không phù hợp với các điều kiện trên, có thể xử lý theo ý của bạn, ví dụ:
                    productTable1.addCell(new Phrase("Không xác định", fontParagraph));
                }
                productTable1.addCell(new Phrase(hinhThucThanhToan.getGhichu(), fontParagraph)); // Thành tiền
                stt1++;
            }
            document.add(new Phrase("Hình thức thanh toán:", fontParagraph));
            document.add(productTable1);
            document.add(new Paragraph("\n"));
            if (hoaDon.getTiengiaohang() != null) {
                BigDecimal tienShip = BigDecimal.valueOf(hoaDon.getTiengiaohang());
                Paragraph paragraph20 = new Paragraph("Tiền ship: " + tienShip.toString() + " VND", fontParagraph);
                paragraph20.setAlignment(Element.ALIGN_LEFT);
                document.add(paragraph20);
            }

            Paragraph paragraph19;
            BigDecimal tienGiamGia;
            if (hoaDon.getGiatrigiam() == null) {
                tienGiamGia = BigDecimal.ZERO;
            } else {
                tienGiamGia = BigDecimal.valueOf(hoaDon.getGiatrigiam());
            }
            paragraph19 = new Paragraph("Tiền giảm giá: " + tienGiamGia.toString() + " VND", fontParagraph);
            paragraph19.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph19);

            BigDecimal tongTienPhaiThanhToan = BigDecimal.valueOf(hoaDon.getThanhtien());
            Paragraph paragraph18 = new Paragraph("Tổng tiền phải thanh toán: " + tongTienPhaiThanhToan.toString() + " VND", fontParagraph);
            paragraph18.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph18);


        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }
}
