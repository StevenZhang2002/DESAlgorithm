package com.example.bowenproject1.controller;

import com.example.bowenproject1.Service.EncodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {
    private final EncodeService encodeService;

    @Autowired
    public ServiceController(EncodeService encodeService) {
        this.encodeService = encodeService;
    }

    @GetMapping("/Encrypt")
    public String Encrypt(@RequestParam(name = "plainText") String plainText,
                          @RequestParam(name = "secretKey") String key) {
        return encodeService.DesEncrypt(plainText, key);
    }

    @GetMapping("/Decrypt")
    public String Decrypt(@RequestParam(name = "plainText") String plainText,
                          @RequestParam(name = "secretKey") String key) {
        return encodeService.DesDecrypt(plainText, key);
    }
}
