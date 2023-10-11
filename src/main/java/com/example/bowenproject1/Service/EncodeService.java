package com.example.bowenproject1.Service;


import org.springframework.stereotype.Service;

public interface EncodeService {

    String DesEncrypt(String msg, String key);

    String DesDecrypt(String msg, String key);

}
