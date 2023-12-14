public class Assembler extends Converter {
    public String getOpCode(String op){
        switch(op){
            case "LDR": return "000001";
            case "STR": return "000010";
            case "LDA": return "000011";
            case "LDX": return "100001";
            case "STX": return "101010";
            case "JZ": return "001000";
            case "JNE": return "001001";
            case "JCC": return "001010";
            case "JMA": return "001011";
            case "JSR": return "001100";
            case "RFS": return "001101";
            case "JGE": return "001111";
            case "AMR": return "000100";
            case "SMR": return "000101";
            case "AIR": return "000110";
            case "SIR": return "000111";
            default: return "none";
        }
    }
}