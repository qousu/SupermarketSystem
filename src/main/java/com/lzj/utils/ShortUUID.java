package com.lzj.utils;


import java.util.UUID;

@SuppressWarnings("all")
public class ShortUUID {
    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }


}


