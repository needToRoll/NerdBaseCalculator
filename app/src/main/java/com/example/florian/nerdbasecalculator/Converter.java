package com.example.florian.nerdbasecalculator;


import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by Florian on 11.04.2016.
 */
public class Converter {
    public static final String baseChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String calcNewBase(int sourceBase, String sourceNumber, int targetBase) {
        if (sourceBase < 2 || targetBase < 2) {
            return null;
        }
        if (sourceBase > 36 || targetBase > 36) {
            return null;
        }
        if(sourceNumber.equals("")||sourceNumber==null){
            return null;
        }
        String sourceChars = baseChars.substring(0,sourceBase);
        double baseTen;
        try {
            baseTen = getBaseTen(sourceChars, sourceNumber, sourceBase);
        } catch (InvalidOperationException e){
            return null;
        }
        sourceChars = baseChars.substring(0,targetBase);
        return getTargetStringByBaseTen(sourceChars,baseTen,targetBase);

    }

    private static String getTargetStringByBaseTen(String sourceChars, double baseTen,int targetBase){
        StringBuilder result = new StringBuilder();
        double baseTenRemaining = baseTen;
        int max =sourceChars.length()-1;
        for(int exponent = getRundedLog(targetBase,baseTen);exponent>=0;exponent--){
            double tempValue = Math.pow(targetBase,exponent);
            for(int i=max;i>=0;i--){
                double tempResult = tempValue*i;
                if(baseTenRemaining-tempResult>=0){
                    baseTenRemaining-=tempResult;
                    char c = sourceChars.charAt(i);
                    result.append(c);
                    break;
                }
            }
        }
        return result.toString();
    }

    private static double getBaseTen(String sourceChars,String sourceNumber, int sourceBase) throws InvalidOperationException {
        sourceNumber = sourceNumber.toUpperCase();

        int index = 0;
        char[] chars = sourceNumber.toCharArray();
        double result = 0;
        ArrayUtils.reverse(chars);
        if(chars.length==0){
            throw new InvalidOperationException();
        }
        for(char c : chars){
            int baseTenCharValue = sourceChars.indexOf(c);
            if(baseTenCharValue==-1){
                throw new InvalidOperationException();
            }
            double powResult = Math.pow(sourceBase,index);
            result+=powResult*baseTenCharValue;
            index++;
        }
        return result;
    }



    private static double logOfBase(int base, double num) {
        return Math.log(num) / Math.log(base);
    }

    private static int getRundedLog(int base, double num) {
        return ((int) Math.floor(logOfBase(base, num)));
    }
}
