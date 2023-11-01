/**
 * This section encompasses the definitions of registers and variables for CPU's functionality.
 */
public class CPU extends Converter
{
    /** CPU COMPONENT DECLARATIONS **/
    /** GENERAL PURPOSE REGISTERS (GPR0, GPR1, GPR2, GPR3) **/
    public char GPR0[];
    public char GPR1[];
    public char GPR2[];
    public char GPR3[];

    /** PROGRAM COUNTER(PC) **/
    public char PC[];

    /** MEMORY ADDRESS REGISTER(MAR) **/
    public char MAR[];

    /** MEMORY BUFFER REGISTER(MBR) **/
    public char MBR[];

    /** INSTRUCTION REGISTER(IR) **/
    public char IR[];

    /** MEMORY FAULT REGISTER(MFR)**/
    public char MFR[];

    /** INDEX REGISTERS (X1,X2,X3) **/
    public char X1[],X2[],X3[];

    /** CONDITION CODE(CC) **/
    public char CC[];

    /** ------------------- END OF CPU COMPONENT DECLARATIONS -------------------**/
    /** Device Interface **/
    // private Devices dev;

    /** -------------------OPCODE DEFINITIONS --------------------**/
    static final short LDR = 0x01; /** Load Register from Memory[Effective Address]*/
    static final short STR = 0x02; /** Store Register to Memory[Effective Address]*/
    static final short LDA = 0x03; /** Load Register with Memory[Effective Address]*/
    static final short LDX = 0x21; /** Load Index Register with Memory[Effective Address]*/
    static final short STX = 0x22; /** Store Index Register to Memory[Effective Address]*/
    static final short HLT = 0x00; /** HALTS MACHINE**/

    /** -------------------SETTERS FOR CPU COMPONENTS --------------------**/
    public void setGPR0(short value){
        DecimalToBinary(value, GPR0, 16);
    }
    public void setGPR1(char arr[],int len){
        CopyArray_src_dest(arr,GPR1,16);
    }
    public void setGPR2(char arr[],int len){
        CopyArray_src_dest(arr,GPR2,16);
    }
    public void setGPR3(char arr[],int len){
        CopyArray_src_dest(arr,GPR3,16);
    }
    public void setX1(char arr[],int len){
        CopyArray_src_dest(arr,X1,16);
    }
    public void setX2(char arr[],int len){
        CopyArray_src_dest(arr,X2,16);
    }
    public void setX3(char arr[],int len){
        CopyArray_src_dest(arr,X3,16);
    }
    public void setPC(short value){
        DecimalToBinary(value, PC, 12);
    }
    public void setMAR(short value){
        DecimalToBinary(value, MAR, 12);
    }
    public void setMBR(char arr[],int len){
        CopyArray_src_dest(arr,MBR,16);
    }

    /** -------------------END OF SETTERS FOR CPU COMPONENTS --------------------**/
    public char[] getRegister(short rx){
        switch(rx){
            case 0: return GPR0;
            case 1: return GPR1;
            case 2: return GPR2;
            case 3: return GPR3;
            default: return GPR0;
        }
    }
    /**
     * Constructor to Initialize the CPU
     */
    public CPU(Devices dev)
    {
        /**
         * general purpose registers
         */
        GPR0 = new char[16];
        GPR1 = new char[16];
        GPR2 = new char[16];
        GPR3 = new char[16];

        /**
         * Memory registers
         */
        MAR = new char[12];
        MBR = new char[16];
        MFR = new char[4];

        /**
         * Instruction Register
         */
        IR = new char[16];

        /**
         * Control Registers
         */
        PC = new char[12];
        CC = new char[4];

        /**
         * Index register
         */
        X1 = new char[16];
        X2 = new char[16];
        X3 = new char[16];
        // Setting the machine's state to 0 upon startup
        for(int i=0;i<16;i++){
            GPR0[i] = 0;
            GPR1[i] = 0;
            GPR2[i] = 0;
            GPR3[i] = 0;
            X1[i]   = 0;
            X2[i]   = 0;
            X3[i]   = 0;
            MBR[i]  = 0;
            IR[i]   = 0;

        }
        for(int i=0;i<12;i++){
            PC[i] = 0;
            MAR[i] = 0;
        }
        for(int i=0;i<4;i++){
            MFR[i] = 0;
            CC[i] = 0;
        }
    }
    public void CopyArray_src_dest(char src[],char des[],int len){
        for(int i=0;i<len;i++)
            des[i] = src[i];
    }

     /**
     * Places the Value from memory[EFFECTIVE ADDRESS] into the Designated Register
     */
    private void LoadRegister(char rx[],short EA,Memory m){
        byte RValue= (byte)BinaryToDecimal(rx,2);
        DecimalToBinary(EA,MAR,12);
        DecimalToBinary(m.Data[EA],MBR,16);
        switch(RValue){
            case 0:
                CopyArray_src_dest(MBR, GPR0, 16);
                break;
            case 1:
                CopyArray_src_dest(MBR, GPR1, 16);
                break;
            case 2:
                CopyArray_src_dest(MBR, GPR2, 16);
                break;
            case 3:
                CopyArray_src_dest(MBR, GPR3, 16);
                break;
        }
    }
    /** End of LoadRegister **/
    /**
     * Places the Value from memory[EFFECTIVE ADDRESS] into the Designated INDEX REGISTER
     */
    private void LoadIndexRegister(char ix[],short EA,Memory m){
        byte RVal= (byte)BinaryToDecimal(ix,2);
        DecimalToBinary(EA,MAR,12);
        DecimalToBinary(m.Data[EA],MBR,16);
        switch(RVal){
            case 0:
                break;
            case 1:
                CopyArray_src_dest(MBR, X1, 16);
                break;
            case 2:
                CopyArray_src_dest(MBR, X2, 16);
                break;
            case 3:
                CopyArray_src_dest(MBR, X3, 16);
                break;
            default: break;
        }
    }
    /** End of LoadIndexRegister **/
    /**
     * Loads the register with Effective address
     */
    private void StoreRegisterwithEA(char rx[],short EA){
        byte RVal= (byte)BinaryToDecimal(rx,2);
        char[] R_x = getRegister((short)RVal);
        DecimalToBinary(EA,MAR,12);
        ReverseCopy_Array_src_dst(MAR, R_x, 16, 12);
    }
    /** End of StoreRegisterwithEA **/
    public void ReverseCopy_Array_src_dst(char src[],char des[],int length,int srclen){
        for(int i=0;i<srclen;i++)
            des[length-i-1] = src[srclen-i-1];
    }
    /**
     * Store to memory[effective address] the value from register
     */
    private void RStoretoMem(char rx[],short EA,Memory m){
        byte RVal = (byte)BinaryToDecimal(rx,2);
        char[] R_x = getRegister((short)RVal);
        DecimalToBinary(EA,MAR,12);
        CopyArray_src_dest(R_x, MBR, 16);
        if(EA>=0 && EA<=9) {
            MFR[3]=1;
            m.Data[4] = BinaryToDecimal(PC, 12);
            return;
        }
        m.Data[EA] = BinaryToDecimal(MBR,16);
    }
    /** End of RStoretoMem **/
    /**
     * Store to memory[effective address] the value from Index register
     */
    private void IXStoretoMem(char ix[],short EA,Memory m){
        byte XVal = (byte)BinaryToDecimal(ix,2);
        DecimalToBinary(EA,MAR,12);
        switch(XVal){
            case 0:
                break;
            case 1:
                CopyArray_src_dest(X1, MBR, 16);
                break;
            case 2:
                CopyArray_src_dest(X2, MBR, 16);
                break;
            case 3:
                CopyArray_src_dest(X3, MBR, 16);
                break;
        }
        if(EA>=0 && EA<=9) {
            MFR[3]=1;
            m.Data[4] = BinaryToDecimal(PC, 12);
            return;
        }
        m.Data[EA] = BinaryToDecimal(MBR,16);
    }
    /** End of IXStoretoMem **/
    /**
     * Resets Machine
     */
    public void Reset(Memory m){
        for(int i=0;i<16;i++){
            GPR0[i] = 0;
            GPR1[i] = 0;
            GPR2[i] = 0;
            GPR3[i] = 0;
            X1[i] = 0;
            X2[i] = 0;
            X3[i] = 0;
            IR[i] = 0;
            MBR[i] = 0;
        }
        for(int i=0;i<12;i++){
            MAR[i] = 0;
            PC[i] = 0;
        }
        for(int i=0;i<4;i++){
            MFR[i] = 0;
            CC[i] = 0;
        }
        for(int i=0;i<m.Data.length;i++)
            m.Data[i]=0;
    }
    /**
     * EFFECTIVE ADDRESS COMPUTATION
     */
    private short get_Effective_Address(char ix[], char addr[], Memory m,char I){
        byte IX_Val = (byte)BinaryToDecimal(ix,2);/** INDEX REGISTER VALUE**/
        short EA = BinaryToDecimal(addr,5);/** EFFECTIVE ADDRESS**/
        switch(IX_Val){
            case 0:
                break;
            case 1:
                EA += BinaryToDecimal(X1,16);
                break;
            case 2:
                EA += BinaryToDecimal(X2,16);
                break;
            case 3:
                EA += BinaryToDecimal(X3,16);
                break;
            default:
                break;
        }
        if(I==1) {
            m.Data[6] = EA;
            return m.Data[EA];
        }
        return EA;
    }
    /**
     * END OF EFFECTIVE ADDRESS COMPUTATION
     */
    /**
     * Fetches and executes the Instructions from Memory
     */
    public void Execute(Memory m,Devices d){
        /**
         * Parsing Input
         */
        char bin_opcode[] = new char[6];
        for(int i=0;i<6;i++)
            bin_opcode[i] = IR[i];
        char RX[] = new char[2];
        for(int i=6;i<8;i++)
            RX[i-6] = IR[i];
        char IX[] = new char[2];
        for(int i=8;i<10;i++)
            IX[i-8] = IR[i];
        char I = IR[10];
        char Address[] = new char[5];
        for(int i=11;i<16;i++)
            Address[i-11] = IR[i];
        char Count[] = new char[4];
        for(int i=12;i<16;i++)
            Count[i-12] = IR[i];
        short OpCode = BinaryToDecimal(bin_opcode,6); // Converts binary opcode to decimal OpCode Value
        short EA=get_Effective_Address(IX,Address,m,I);
        try{
            switch(OpCode){
                case HLT: break;
                case LDR: LoadRegister(RX,EA,m); break;
                case STR: RStoretoMem(RX,EA,m); break;
                case LDA: StoreRegisterwithEA(RX,EA); break;
                case LDX: LoadIndexRegister(IX, EA, m); break;
                case STX: IXStoretoMem(IX, EA, m); break;
                default:
                    MFR[1]=1;
                    m.Data[4] = BinaryToDecimal(PC, 12);
                    break;
            }
        }catch(IndexOutOfBoundsException ioobe){
            MFR[0]=1;
            m.Data[4] = BinaryToDecimal(PC, 12);
        }
        m.Data[1] = BinaryToDecimal(MFR, 4);
    }
    /**
     * Machine Fault Handler
     * @param mem
     */
    public void MFHandle(Memory mem){
        if(MFR[0]==1) {
            DecimalToBinary((short) 10, PC, 12);
        }
        MFR[0] = 0;
        MFR[1] = 0;
        MFR[2] = 0;
        MFR[3] = 0;
        mem.Data[4] = BinaryToDecimal(PC, 12);
    }
}