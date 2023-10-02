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
}
