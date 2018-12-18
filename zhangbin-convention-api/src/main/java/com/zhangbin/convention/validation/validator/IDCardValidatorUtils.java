package com.zhangbin.convention.validation.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangbin
 * @Type IDCardValidatorUtil
 * @Desc
 * @date 2018-10-19
 * @Version V1.0
 */
public class IDCardValidatorUtils {
    private static final int[] ID_CARD_W = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
    private static final String[] ID_CARD_VAL_CODE = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };

    private static final String ID_CARD_REGEX_18 = "[0-9]{18}|[0-9]{17}X";
    private static final String ID_CARD_REGEX_15 = "[0-9]{15}";

    /**
     * 身份证验证18位 验证长度、出生年月、校验码，只允许2代身份证
     *
     * @param idCard 身份证
     * @return
     */
    public static boolean validate(String idCard) {
        return validate(idCard, true);
    }

    private static boolean validateIdCardBirthday(String idCard) {
        return validateBirthday(idCard.substring(6, 14));
    }

    private static boolean validateBirthday(String birthday) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setLenient(false);
        try {
            Date birthdayDate = sdf.parse(birthday);
            Date now = new Date();
            return birthdayDate.getTime() <= now.getTime();
        } catch (ParseException e) {
            return false;
        }
    }

    private static boolean validateIdCardLastNumber(String idCard) {
        char[] idCharArray = idCard.toCharArray();
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum = sum + (Integer.parseInt(String.valueOf(idCharArray[i])) * ID_CARD_W[i]);
        }
        String checkNumber = ID_CARD_VAL_CODE[sum % 11];
        return checkNumber.equals(String.valueOf(idCharArray[17]));
    }

    /**
     * 身份证验证18位 验证长度、出生年月、校验码，只允许2代身份证
     *
     * @param idCard 身份证号码
     * @param checkLastCode 是否校验最后一位校验位
     * @return
     */
    public static boolean validate(String idCard, boolean checkLastCode) {
        if (idCard == null) {
            return false;
        }
        String upperIdCard = idCard.toUpperCase();
        return upperIdCard.matches(ID_CARD_REGEX_18) && validateIdCardBirthday(upperIdCard)
                && (!checkLastCode || validateIdCardLastNumber(upperIdCard));
    }

    /**
     * 校验15位身份证号码 验证长度、出生年月
     *
     * @param idCard 身份证号码
     * @return
     */
    public static boolean validateOld(String idCard) {
        return idCard != null && idCard.matches(ID_CARD_REGEX_15) && validateOldIdCardBirthday(idCard);
    }

    private static boolean validateOldIdCardBirthday(String idCard) {
        String birthdayStr = "19" + idCard.substring(6, 12);
        return validateBirthday(birthdayStr);
    }
}
