package com.pmcc.system.utils;

/**
 * @ClassName: FilterHelper <br>
 * @Description: TODO过滤方法
 * @Date: 2020/3/24 15:14 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class FilterHelper {

    private static String key = "wow!@#$%";

    public static boolean isStringInArray(String str, String[] array){
        for (String val:array){
            if(str.equals(val)){
                return true;
            }
        }
        return false;
    }

    public static String encode(String str){
        String enStr = "";
        try {
            enStr = DesUtil.encrypt(str, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return enStr;
    }

    public static String decode(String str) {
        String deStr = "";
        try {
            deStr = DesUtil.decrypt(str, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deStr;
    }
}
