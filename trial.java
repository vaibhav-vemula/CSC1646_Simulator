public class trial {
    public static void main(String[] args) {
        String[] instructions = {
            "LOC 6",
            "Data 10",
            "Data 3",
            "Data End",
            "Data 0",
            "Data 12",
            "Data 9",
            "Data 18",
            "Data 12",
            "LDX 2,7",
            "LDR 3,0,10",
            "LDR 2,2,10",
            "LDR 1,2,10,1",
            "LDA 0,0,0",
            "LDX 1,9",
            "JZ 0,1,0",
            "LOC 1024",
            "End: HLT"
        };

        int locationCounter = 0x0000;
        int endAddress = 0x1024;

        for (String instruction : instructions) {
            if (instruction.startsWith("LOC")) {
                locationCounter = Integer.parseInt(instruction.split(" ")[1]);
                System.out.println(instruction);
            } else if (instruction.startsWith("Data") && (!(instruction.contains("End"))) && (!(instruction.contains(":"))) && !instruction.equals("Data End")) {
                int dataValue = Integer.parseInt(instruction.split(" ")[1]);
                System.out.printf("%04X %04X %s%n", locationCounter, dataValue, instruction);
            } else if (instruction.contains(":")) {
                if (instruction.startsWith("End:")) {
                    endAddress = locationCounter;
                }
                System.out.printf("%04X %s%n", locationCounter, instruction);
            } else {
                String[] parts = instruction.split(" ");
                String opcode = parts[0];
                String operands = parts[1];
                String[] operandParts = operands.split(",");
                
                int l = operandParts.length;

                int i = 0;
                int address = 0;
                int f = 0;
                int tt = 0;
                int s = 0;
                
                switch (opcode) {
                    case "LDX":
                        f = 0b100001;
                        break;
                    case "LDR":
                        f = 0b000001;
                        break;
                    case "JZ":
                        f = 0b001000;
                        break;
                    case "LDA":
                        f = 0b000011;
                        break;
                    default:
                        break;
                }

                if (l >= 2) {
                    address = Integer.parseInt(operandParts[l - 1]);
                    tt = Integer.parseInt(operandParts[l - 2]);
                }

                if (l >= 3) {
                    s = Integer.parseInt(operandParts[l - 3]);
                }

                if (l >= 4) {
                    i = Integer.parseInt(operandParts[l - 4]);
                }

                if (opcode.equals("LDR")) {
                    if (l == 3) {
                        i = 0;  
                    } else if (l == 2) {
                        i = 0;  
                        s = 0;
                    }
                }

                int binaryValue = (f << 10) | (s << 8) | (tt << 6) | (i << 1) | address;
                String binaryString = String.format("%16s", Integer.toBinaryString(binaryValue)).replace(' ', '0');

                
                int hexValue = Integer.parseInt(binaryString, 2);
                String hexString = String.format("%04X", hexValue);

                System.out.printf("%04X %s %s %s%n", locationCounter, hexString, opcode, operands);
            }
            
            if (!(instruction.startsWith("LOC"))) {
                locationCounter++;
            }
        }

        
        for (int i = 0; i < instructions.length; i++) {
            if (instructions[i].startsWith("Data End")) {
                int dataValue = endAddress;
                System.out.printf("%04X %04X %s%n", locationCounter, dataValue, instructions[i]);
            }
        }
    }
}
