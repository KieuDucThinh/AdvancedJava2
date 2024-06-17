package com.ltc.btl_javafx.application;

public class readPrice {
    private static final String[] units = new String[]{"", "nghìn", "triệu", "tỷ"};
    private static final String[] ones = new String[]{"", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};
    private static final String[] teens = new String[]{"", "mười", "mười một", "mười hai", "mười ba", "mười bốn", "mười lăm", "mười sáu", "mười bảy", "mười tám", "mười chín"};
    private static final String[] tens = new String[]{"", "mười", "hai mươi", "ba mươi", "bốn mươi", "năm mươi", "sáu mươi", "bảy mươi", "tám mươi", "chín mươi"};

    public readPrice() {
    }

    public static String readNumberInWords(long number) {
        if (number == 0L) {
            return "không";
        } else {
            String result = "";

            for(int chunkCount = 0; number > 0L; ++chunkCount) {
                long chunk = number % 1000L;
                if (chunk != 0L) {
                    String var10000 = convertChunk(chunk);
                    result = var10000 + " " + units[chunkCount] + " " + result;
                }

                number /= 1000L;
            }

            return result.trim();
        }
    }

    private static String convertChunk(long chunk) {
        if (chunk == 0L) {
            return "";
        } else if (chunk < 10L) {
            return ones[(int)chunk];
        } else if (chunk < 20L) {
            return teens[(int)(chunk - 10L + 1L)];
        } else {
            String var10000;
            if (chunk < 100L) {
                var10000 = tens[(int)(chunk / 10L)];
                return var10000 + " " + (chunk % 10L == 1L ? "mốt" : ones[(int)(chunk % 10L)]);
            } else {
                var10000 = ones[(int)(chunk / 100L)];
                return var10000 + " trăm " + convertChunk(chunk % 100L);
            }
        }
    }
}
