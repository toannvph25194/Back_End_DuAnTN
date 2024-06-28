package com.example.be_duantn.service.pdf_service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

public interface PDFGeneratorService {

    void orderCouter(HttpServletResponse response, UUID idHoaDon) throws IOException;



}
