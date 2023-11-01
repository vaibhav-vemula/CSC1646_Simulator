/*
This class contains functions to perform numerical conversions.
 */
public class Converter
{
    public Converter(){
    }
    /*
    Function to convert Binary number to Decimal number in an array.
     */
    public short BinaryToDecimal(char Bin[],int length){
        short result=0;
        short base=1;
        for(int i=0; i <length ; i++){
            if(Bin[length-1-i]==1)
                result += base;
            base *= 2;
        }
        return result;
    }
    /*
    Function to convert Decimal number to Binary number in an array.
     */
    public void DecimalToBinary(short dec, char[] Bin,int length){
        int c=0;
        int d=dec;
        if(d<0){
            Bin[0]=1;
            d+=Short.MAX_VALUE+1;
        }
        while(d>=0){
            if((d & 1) == 1)
                Bin[length -1 -c] = 1;
            else
                Bin[length -1 -c] = 0;
            d >>=1;
            c++;
            if(dec <0) Bin[0]=1;
            if(c==length) break;
        }
    }
    /*
    Function to convert Hex number to Decimal number in an array.
     */
    public short HexToDecimal(String s){
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        short val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            short d = (short)digits.indexOf(c);
            val = (short)(16*val + d);
        }
        return val;
    }

    public static int hexStringToInt(String hexString) {
        // Parse the hexadecimal string to an integer
        return Integer.parseInt(hexString, 16);

    }

    public String inttoHexString(int x) {
        String input = Integer.toHexString(x).toUpperCase(); // The input string
        int desiredLength = 4; // The desired length of the padded string
        // Use String.format to left-pad with zeros
        return String.format("%" + desiredLength + "s", input).replace(' ', '0');
    }
    public String inttoHexString(int x, int desiredLength) {
        String input = Integer.toHexString(x).toUpperCase(); // The input string
         // The desired length of the padded string
        // Use String.format to left-pad with zeros
        return String.format("%" + desiredLength + "s", input).replace(' ', '0');
    }
    public static boolean isAllowedHex(char c) {
        char[] charArray = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F', 'a', 'b', 'c', 'd', 'e','f'};
        for (char chr:
                charArray) {
            if (chr == c) {
                return true;
            }
        }
        return false;
    }
    public String binaryToHex(String binary, int desiredLength) {
        int decimalValue = Integer.parseInt(binary, 2);
        String input = Integer.toHexString(decimalValue).toUpperCase();
        return String.format("%" + desiredLength + "s", input).replace(' ', '0');
    }
    public static String hexToBinary(String hex) {
        int decimalValue = Integer.parseInt(hex, 16);
        return Integer.toBinaryString(decimalValue);
    }
    public String intstrtoBin(String x, int desiredLength){
        String input = Integer.toBinaryString(Integer.parseInt(x));
        return String.format("%" + desiredLength + "s", input).replace(' ', '0');
    }
}
