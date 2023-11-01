public class Assembler extends Converter {
    public String getOpCode(String op){
        switch(op){
            case "LDR": return "000001";
            case "STR": return "000010";
            case "LDA": return "000011";
            case "LDX": return "100001";
            case "STX": return "101010";
            case "JZ": return "001000";
            default: return "none";
        }
    }
}