package com.kgdev.object;

import com.kgdev.constant.BaseConstant;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class CacheKey {
    private final String key;
    public CacheKey(String key) {
        key = key.trim();
        if(key.trim().length() > BaseConstant.MAX_ALLOWED_LENGTH_FOR_KEY || !Pattern.matches(BaseConstant.ALLOWED_KEY_CHARACTER_REGEX, key)){
            throw new IllegalArgumentException("Key is Invalid. Please verify the key with constraint.");
        }
        this.key = key;
    }
}
