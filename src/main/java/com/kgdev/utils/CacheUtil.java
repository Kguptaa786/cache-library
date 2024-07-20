package com.kgdev.utils;

import com.kgdev.constant.BaseConstant;

import java.util.regex.Pattern;

public class CacheUtil {
    public static void validateCacheKey(String key){
        key = key.trim();
        if(key.length() > BaseConstant.MAX_ALLOWED_LENGTH_FOR_KEY || !Pattern.matches(BaseConstant.ALLOWED_KEY_CHARACTER_REGEX, key)){
            throw new IllegalArgumentException("Key is Invalid. Please verify the key with constraint.");
        }
    }
}
