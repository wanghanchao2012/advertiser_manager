package com.emarbox.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliasUtil {

    private static final long base_num = 10000L;
    private static final int base_len = 4;

    public static String encode(Long num) {
        String result = null;
        try {
            String ts = String.valueOf(System.currentTimeMillis());
            ts = ts.substring(ts.length() - base_len, ts.length()).replace('0',
                    '1');
            long n = num * base_num + Long.valueOf(ts);
            result = Long.toString(n, Character.MAX_RADIX).toLowerCase();

        } catch (Exception e) {
            log.error("encode failed: {}", e);
        }
        return result;

    }


    public static Long decode(String str) {
        Long num = 0L;
        try {
            num = Long.parseLong(str, Character.MAX_RADIX);
            num = num / base_num;
        } catch (Exception e) {
            log.error("decode failed: {}", e);
        }
        return num;

    }
}
