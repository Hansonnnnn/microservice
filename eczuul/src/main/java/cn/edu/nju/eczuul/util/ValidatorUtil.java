package cn.edu.nju.eczuul.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class ValidatorUtil {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return MOBILE_PATTERN.matcher(value).matches();
    }
}
