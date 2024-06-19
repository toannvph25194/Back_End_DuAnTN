package com.example.be_duantn.service.ban_hang_tai_quay_service.Impl;

import com.example.be_duantn.config.VNPayConfigOnline;
import com.example.be_duantn.config.VnPayConfigTaiQuay;
import com.example.be_duantn.dto.respon.mua_hang_online_respon.hoa_don_respon.VNPayOnlineRespon;
import com.example.be_duantn.service.ban_hang_tai_quay_service.VNPayOnlineBanTaiQuayService;
import com.example.be_duantn.service.mua_hang_online_service.hoa_don_service.VNPayOnlineService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Service
public class VNPayOnlineBanTaiQuayServiceImpl implements VNPayOnlineBanTaiQuayService {
    @Override
    public VNPayOnlineRespon callPaymentApiOnline(HttpServletRequest req, Long amountParam) {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";

        String vnp_TxnRef = VnPayConfigTaiQuay.getRandomNumber(8);
        String vnp_IpAddr = VnPayConfigTaiQuay.getIpAddress(req);
        String vnp_TmnCode = VnPayConfigTaiQuay.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amountParam * 100) );
        vnp_Params.put("vnp_CurrCode", "VND");
        if ("NCB" != null && !"NCB".isEmpty()) {
            vnp_Params.put("vnp_BankCode", "NCB");
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thông tin đặt hàng");
        vnp_Params.put("vnp_OrderType", "Chuyển khoản");

        if ("vn" != null && !"vn".isEmpty()) {
            vnp_Params.put("vnp_Locale", "vn");
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VnPayConfigTaiQuay.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        // Add Params of 2.0.1 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        // Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator iterator = fieldNames.iterator();
        while (iterator.hasNext()) {
            String fieldName = (String) iterator.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (iterator.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfigTaiQuay.hmacSHA512(VnPayConfigTaiQuay.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return VNPayOnlineRespon.builder().url(VnPayConfigTaiQuay.vnp_PayUrl + "?" + queryUrl).build();
    }
}
