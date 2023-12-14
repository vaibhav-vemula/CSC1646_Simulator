import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
/* This file contains all of the front panel interface's GUI Components and event handlers. This file is also used for all input and output actions. */

public class GUI extends JFrame {
    // This class defines all of the classes and variables that will be utilized
    // later.
    private JTextField textField, textField2;
    private JLabel GPR[], X[], PC, MAR, MBR, IR, MFR, Priv, KeyBoard, Printer, ent, near;
    private Label gpr0_arr[],gpr1_arr[],gpr2_arr[],gpr3_arr[];
    private Label XLabel[][],pclab[],marlab[],mbrlab[],mfrlab[], irlab[], privlab, hlt, Run;
    private JButton LDarr[], store, st_plus, load, init, ss, run, assemble, inputNumber, FindNearest;
    private CPU cpu;
    private Memory mem;
    private File file;
    private ArrayList<StringStruct> Code;
    // private Assembler assem;
    private ArrayList<JButton> switches;
    private JPanel Pan[];
    private Devices dev;
    char swarr[]; // Array for the switches pressed
    private Map<String, String> labelMap = new HashMap<>();
    // private Map<String, String> outMap = new HashMap<>();
    private int pcount=0;
    private String hexPcount = "";
    private List<String> hexAdd = new ArrayList<String>();
    private List<String> hexDat = new ArrayList<String>();
    private List<Integer> NumbersList = new ArrayList<Integer>();

    private Assembler assem;

    public GUI() throws NullPointerException {
        super();
        /**
         * This places the "PC", "MAR", "MBR", "IR", "MFR", and "Privilege" labels to
         * the left of the panels and changes the label color to black.
         **/
        Code = new ArrayList<StringStruct>();
        hlt = new Label();
        dev = new Devices();
        cpu = new CPU(dev);
        mem = new Memory();
        assem = new Assembler();
        hlt.setBounds(530, 520, 20, 20);
        hlt.setBackground(Color.BLACK);
        Run = new Label();
        Run.setBounds(610, 520, 20, 20);
        Run.setBackground(Color.BLACK);
        swarr = new char[16];
        for (int i = 0; i < 16; i++)
            swarr[i] = 0;
        Pan = new JPanel[5];
        GPR = new JLabel[4];
        X = new JLabel[3];
        PC = new JLabel("PC");
        MAR = new JLabel("MAR");
        MBR = new JLabel("MBR");
        IR = new JLabel("IR");
        MFR = new JLabel("MFR");
        PC.setBounds(730, 150, 40, 20);
        MAR.setBounds(730, 180, 40, 20);
        MBR.setBounds(630, 210, 40, 20);
        IR.setBounds(630, 240, 40, 20);
        MFR.setBounds(930, 270, 40, 20);
        Priv = new JLabel("Privilege");
        Priv.setBounds(980, 300, 60, 20);
        LDarr = new JButton[12];
        switches = new ArrayList<JButton>();
        gpr0_arr = new Label[16];
        gpr1_arr = new Label[16];
        gpr2_arr = new Label[16];
        gpr3_arr = new Label[16];
        mbrlab = new Label[16];
        irlab = new Label[16];
        XLabel = new Label[3][];
        pclab = new Label[12];
        marlab = new Label[12];
        mfrlab = new Label[4];
        privlab = new Label();
        privlab.setBounds(1055, 300, 20, 20);
        privlab.setBackground(Color.black);
        this.add(privlab);
        for (int i = 0; i < 3; i++)
            XLabel[i] = new Label[16];
        /* Setting The Layout for Different Panels */
        for (int i = 0; i < 5; i++) {
            /** Creating the cyan boxes around the switches at the bottom of the screen */
            Pan[i] = new JPanel();
            Pan[i].setLayout(new FlowLayout(FlowLayout.CENTER, 1, 5));
            Pan[i].setBackground(Color.getHSBColor((float) 0.533, (float) 0.8, (float) 0.75));
            this.add(Pan[i]);
        }
        /** Creating the labels below the switches at the bottom of the GUI */
        Pan[0].setBounds(160, 25, 310, 70);
        JLabel OpCode = new JLabel("OpCode");
        OpCode.setBounds(295, 95, 100, 20);
        OpCode.setFont(new Font("Arial", Font.BOLD, 17));

        Pan[1].setBounds(480, 25, 100, 70);
        JLabel gprLabel = new JLabel("GPR");
        gprLabel.setBounds(520, 95, 100, 20);
        gprLabel.setFont(new Font("Arial", Font.BOLD, 17));

        Pan[2].setBounds(590, 25, 100, 70);
        JLabel ixrLabel = new JLabel("IXR");
        ixrLabel.setBounds(630, 95, 50, 20);
        ixrLabel.setFont(new Font("Arial", Font.BOLD, 17));

        Pan[3].setBounds(700, 25, 50, 70);
        JLabel indLabel = new JLabel("I");
        indLabel.setBounds(725, 95, 40, 20);
        indLabel.setFont(new Font("Arial", Font.BOLD, 17));

        Pan[4].setBounds(760, 25, 270, 70);
        JLabel addLabel = new JLabel("Address");
        addLabel.setBounds(850, 95, 100, 20);
        addLabel.setFont(new Font("Arial", Font.BOLD, 17));

        for (int i = 0; i < 10; i++) {
            /** A loop to create the LD button next to each panel */
            LDarr[i] = new JButton("Load");
            LDarr[i].setFont(new Font("Arial", Font.BOLD, 13));
            if (i >= 0 && i < 4)
                LDarr[i].setBounds(480, 150 + (i * 30), 50, 20);
            else if (i >= 4 && i < 7)
                LDarr[i].setBounds(480, 290 + ((i - 4) * 30), 50, 20);
            else
                LDarr[i].setBounds(1080, 150 + ((i - 7) * 30), 50, 20);

            LDarr[i].addActionListener(e -> LoadButtonAction(e));
            this.add(LDarr[i]);
        }
        for (int i = 0; i < 4; i++) {
            /** A loop to create the "R" labels next to the 4 top left panels */
            GPR[i] = new JLabel("GPR " + i);
            GPR[i].setBounds(30, 150 + (i * 30), 45, 20);
            GPR[i].setFont(new Font("Arial", Font.BOLD, 15));
            mfrlab[i] = new Label();
            mfrlab[i].setBounds(980 + (i * 25), 270, 20, 20);
            mfrlab[i].setBackground(Color.black);
            mfrlab[i].setFont(new Font("Arial", Font.BOLD, 15));
            this.add(GPR[i]);
            this.add(mfrlab[i]);
        }
        for (int i = 1; i < 4; i++) {
            /** A loop to create the "X" labels next to the 3 left panels */
            X[i - 1] = new JLabel("IXR " + i);
            X[i - 1].setBounds(35, 290 + ((i - 1) * 30), 40, 20);
            X[i - 1].setFont(new Font("Arial", Font.BOLD, 15));
            this.add(X[i - 1]);
        }

        for (int i = 0; i < 16; i++) {
            /**
             * Making the 16 switch buttons at the bottom of the GUI, relocating them, and
             * changing their color to black
             */
            gpr0_arr[i] = new Label();
            gpr0_arr[i].setBounds(80 + (i * 25), 150, 20, 20);
            gpr0_arr[i].setBackground(Color.black);
            gpr1_arr[i] = new Label();
            gpr1_arr[i].setBounds(80 + (i * 25), 180, 20, 20);
            gpr1_arr[i].setBackground(Color.black);
            gpr2_arr[i] = new Label();
            gpr2_arr[i].setBounds(80 + (i * 25), 210, 20, 20);
            gpr2_arr[i].setBackground(Color.black);
            gpr3_arr[i] = new Label();
            gpr3_arr[i].setBounds(80 + (i * 25), 240, 20, 20);
            gpr3_arr[i].setBackground(Color.black);
            XLabel[0][i] = new Label();
            XLabel[0][i].setBounds(80 + (i * 25), 290, 20, 20);
            XLabel[0][i].setBackground(Color.black);
            XLabel[1][i] = new Label();
            XLabel[1][i].setBounds(80 + (i * 25), 320, 20, 20);
            XLabel[1][i].setBackground(Color.black);
            XLabel[2][i] = new Label();
            XLabel[2][i].setBounds(80 + (i * 25), 350, 20, 20);
            XLabel[2][i].setBackground(Color.black);
            mbrlab[i] = new Label();
            mbrlab[i].setBounds(680 + (i * 25), 210, 20, 20);
            // mbrlab[i].setFont(new Font("Arial",Font.BOLD,15));
            mbrlab[i].setBackground(Color.black);
            irlab[i] = new Label();
            irlab[i].setBounds(680 + (i * 25), 240, 20, 20);
            irlab[i].setBackground(Color.black);
            switches.add(new JButton());
            switches.get(i).setText("" + (15 - i));
            switches.get(i).addActionListener(e -> switchAction(e));
            switches.get(i).setPreferredSize(new Dimension(48, 60));
            switches.get(i).setFont(new Font("Arial", Font.BOLD, 17));

            if (i < 6) {
                Pan[0].add(switches.get(i));
            } else if (i >= 6 && i < 8) {
                Pan[1].add(switches.get(i));
            } else if (i >= 8 && i < 10) {
                Pan[2].add(switches.get(i));
            } else if (i == 10) {
                Pan[3].add(switches.get(i));
            } else if (i >= 11 && i < 16) {
                Pan[4].add(switches.get(i));
            }
            this.add(gpr0_arr[i]);
            this.add(gpr1_arr[i]);
            this.add(gpr2_arr[i]);
            this.add(gpr3_arr[i]);
            this.add(XLabel[0][i]);
            this.add(XLabel[1][i]);
            this.add(XLabel[2][i]);
            this.add(mbrlab[i]);
            this.add(irlab[i]);
        }
        for (int i = 0; i < 12; i++) {
            /*
             * This controls the placement and color of the PC and MAR LED indicators on the
             * top two panels.
             */
            pclab[i] = new Label();
            pclab[i].setBounds(780 + (i * 25), 150, 20, 20);
            pclab[i].setBackground(Color.black);
            marlab[i] = new Label();
            marlab[i].setBounds(780 + (i * 25), 180, 20, 20);
            marlab[i].setBackground(Color.black);
            this.add(pclab[i]);
            this.add(marlab[i]);
        }
        // Halt and Run Indicator
        JLabel lhlt = new JLabel("Halt");
        JLabel lrun = new JLabel("Run");
        lhlt.setBounds(500, 520, 40, 20);
        lrun.setBounds(580, 520, 40, 20);
        JMenuItem openFile = new JMenuItem("Open File");
        JMenuItem resethlt = new JMenuItem("Reset Halt");
        JMenuItem resetall = new JMenuItem("Reset All");
        JMenuItem ccView = new JMenuItem("View Console and Cache");
        openFile.addActionListener(e -> {
            try {
                LoadFileIntoMemory(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        resethlt.addActionListener(e -> resetHalt(e));
        resetall.addActionListener(e -> resetAll(e));
        ccView.addActionListener(e -> viewConsoleCache(e));
        this.setTitle("Computer System Architecture Project");
        this.add(hlt);
        this.add(Run);
        this.add(lhlt);
        this.add(lrun);
        this.add(OpCode);
        this.add(gprLabel);
        this.add(ixrLabel);
        this.add(indLabel);
        this.add(addLabel);
        this.add(PC);
        this.add(MAR);
        this.add(MBR);
        this.add(IR);
        this.add(MFR);
        this.add(Priv);
    }

    private void resetHalt(ActionEvent e) {
        hlt.setBackground(Color.black);
    }

    private void resetAll(ActionEvent e) {
        cpu.Reset(mem);
        dev.emptyConsole();
        for (int i = 0; i < 11; i++)
            RefreshLeds(i);
    }

    private void viewConsoleCache(ActionEvent e) {
        if (dev.isActive() == false)
            dev.Run();
        else
            dev.setVisible(false);
    }

    /**
     * The LEDs will be refreshed whenever the internal registers are updated.
     */
    private void RefreshLeds(int buttonpress) {
        if (buttonpress != 7 && buttonpress != 8 && buttonpress != 11)
            for (int i = 0; i < 16; i++) {
                switch (buttonpress) {
                    case 0:
                        if (cpu.R0[i] == 1)
                            gpr0_arr[i].setBackground(Color.green);
                        else
                            gpr0_arr[i].setBackground(Color.black);
                        break;
                    case 1:
                        if (cpu.R1[i] == 1)
                            gpr1_arr[i].setBackground(Color.green);
                        else
                            gpr1_arr[i].setBackground(Color.black);
                        break;
                    case 2:
                        if (cpu.R2[i] == 1)
                            gpr2_arr[i].setBackground(Color.green);
                        else
                            gpr2_arr[i].setBackground(Color.black);
                        break;
                    case 3:
                        if (cpu.R3[i] == 1)
                            gpr3_arr[i].setBackground(Color.green);
                        else
                            gpr3_arr[i].setBackground(Color.black);
                        break;
                    case 4:
                        if (cpu.X1[i] == 1)
                            XLabel[0][i].setBackground(Color.green);
                        else
                            XLabel[0][i].setBackground(Color.black);
                        break;
                    case 5:
                        if (cpu.X2[i] == 1)
                            XLabel[1][i].setBackground(Color.green);
                        else
                            XLabel[1][i].setBackground(Color.black);
                        break;
                    case 6:
                        if (cpu.X3[i] == 1)
                            XLabel[2][i].setBackground(Color.green);
                        else
                            XLabel[2][i].setBackground(Color.black);
                        break;
                    case 9:
                        if (cpu.MBR[i] == 1)
                            mbrlab[i].setBackground(Color.blue);
                        else
                            mbrlab[i].setBackground(Color.black);
                    case 10: // Case only for extra situtations for Instruction Register
                        if (cpu.IR[i] == 1)
                            irlab[i].setBackground(Color.green);
                        else
                            irlab[i].setBackground(Color.black);
                        break;
                    default:
                        break;
                }
            }
        else if (buttonpress == 7 || buttonpress == 8)
            for (int i = 0; i < 12; i++) {
                /**
                 * If the corresponding buttons are pressed, the light on the panel will turn
                 * yellow or orange. If not, the light will stay black
                 */
                if (buttonpress == 7) {
                    if (cpu.PC[i] == 1)
                        pclab[i].setBackground(Color.yellow);
                    else
                        pclab[i].setBackground(Color.black);
                } else {
                    if (cpu.MAR[i] == 1)
                        marlab[i].setBackground(Color.orange);
                    else
                        marlab[i].setBackground(Color.black);
                }
            }
        else
            for (int i = 0; i < 4; i++) {
                if (cpu.MFR[i] == 1)
                    mfrlab[i].setBackground(Color.red);
                else
                    mfrlab[i].setBackground(Color.black);
            }
    }

    private void LoadButtonAction(ActionEvent e) {
        JButton j = (JButton) e.getSource();
        int buttonpress = 0;
        /**
         * This loop employs a switch case that is actuated when the LD button for the
         * relevant panel is pressed.
         */
        for (int i = 0; i < 12; i++) {
            if (j == LDarr[i])
                buttonpress = i;
        }
        switch (buttonpress) {
            case 0:
                cpu.setGPR0(cpu.BinaryToDecimal(swarr, 16));
                break;
            case 1:
                cpu.setGPR1(swarr, 16);
                break;
            case 2:
                cpu.setGPR2(swarr, 16);
                break;
            case 3:
                cpu.setGPR3(swarr, 16);
                break;
            case 4:
                cpu.setX1(swarr, 16);
                break;
            case 5:
                cpu.setX2(swarr, 16);
                break;
            case 6:
                cpu.setX3(swarr, 16);
                break;
            case 7:
                cpu.setPC(cpu.BinaryToDecimal(swarr, 16));
                break;
            case 8:
                cpu.setMAR(cpu.BinaryToDecimal(swarr, 16));
                break;
            case 9:
                cpu.setMBR(swarr, 16);
                break;
            default:
                break;
        }
        RefreshLeds(buttonpress);
    }

    private void switchAction(ActionEvent e) {
        /**
         * When one of the switches at the bottom of the screen is pressed, the color of
         * the switches changes.
         */
        JButton j = (JButton) e.getSource();
        int click = 15 - Integer.parseInt(j.getText());
        if (swarr[click] == 0) {
            swarr[click] = 1;
            j.setBackground(Color.getHSBColor((float)0.0, (float)1.0, (float)1.0));
            j.setForeground(Color.getHSBColor((float)0.5,(float)0.5,(float)0.8));
        } else {
            swarr[click] = 0;
            j.setBackground(new JButton().getBackground());
            j.setForeground(Color.black);
        }
    }

    private void Store(ActionEvent e) {
        /*
         * This will store the memory and print to the screen that the store was
         * successful
         */
        try {
            System.out.println("Store Invoked");
            short EA = cpu.BinaryToDecimal(cpu.MAR, 12);
            if ((EA >= 0 && EA <= 9)) {
                cpu.MFR[3] = 1;
                RefreshLeds(11);
                cpu.MFHandle(mem);
                return;
            }
            short value = cpu.BinaryToDecimal(cpu.MBR, 16);
            mem.Data[EA] = value;
        } catch (Exception ee) {
            cpu.MFR[0] = 1;
            RefreshLeds(11);
        }
    }

    private void StorePlus(ActionEvent e) {
        /*
         * This will store the memory and print to the screen that the store was
         * successful
         */
        // MAR is incremented here after storing
        System.out.println("Store+ Invoked");
        short EA = cpu.BinaryToDecimal(cpu.MAR, 12);
        if ((EA >= 0 && EA <= 9)) {
            cpu.MFR[3] = 1;
            RefreshLeds(11);
            cpu.MFHandle(mem);
            return;
        }
        short value = cpu.BinaryToDecimal(cpu.MBR, 16);
        try {
            mem.Data[EA] = value;
            EA++;
            cpu.DecimalToBinary(EA, cpu.MAR, 12);
            RefreshLeds(8);
        } catch (IndexOutOfBoundsException ioobe) {
            cpu.MFR[0] = 1;
            RefreshLeds(11);
        }
    }

    private void LoadValue(ActionEvent e) {
        /*
         * This will load the memory and print to the screen for the user that the load
         * was successful in the MBR. It the memory was out of bounds, it will print an
         * error message dialog to the screen
         */
        System.out.println("Load Invoked");
        try {
            short EA = cpu.BinaryToDecimal(cpu.MAR, 12);
            cpu.DecimalToBinary((short) mem.Data[EA], cpu.MBR, 16);
            RefreshLeds(9);
        } catch (IndexOutOfBoundsException i) {
            JOptionPane.showMessageDialog(this, "Illegal Operation with memory Access", "Error",
                    JOptionPane.ERROR_MESSAGE);
            cpu.MFR[0] = 1;
            RefreshLeds(11);
        }
    }

    private void LoadFileIntoMemory(ActionEvent e) throws IOException {
        JFileChooser fCh = new JFileChooser();
        fCh.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int res = fCh.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            file = new File(fCh.getSelectedFile().getAbsolutePath());
            String filename = file.getAbsolutePath();
            JOptionPane.showMessageDialog(this, filename, "File Load Successful", JOptionPane.PLAIN_MESSAGE);
            try (FileReader reader = new FileReader(filename)) {
                short EA = cpu.BinaryToDecimal(cpu.MAR, 12);
                int i = EA;
                int c;
                while ((c = reader.read()) != -1) {
                    mem.Data[i++] = (short) c;
                }
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            }
        }
    }

    private void loadFile(ActionEvent e) {
        /*This will prompt the user to search for and load a file into the simulator*/
        JFileChooser fCh = new JFileChooser();
        fCh.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int res = fCh.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            file = new File(fCh.getSelectedFile().getAbsolutePath());
            String filename = file.getAbsolutePath();
            JOptionPane.showMessageDialog(this, filename, "File Load Successful", JOptionPane.PLAIN_MESSAGE);
            try {
                ProcessFile();
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            }
        }
    }

    private void loadFileForAssemble(ActionEvent e) throws IOException {
        /*This will prompt the user to search for and load a file into the simulator*/
        JFileChooser fCh = new JFileChooser();
        fCh.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int res = fCh.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            file = new File(fCh.getSelectedFile().getAbsolutePath());
            String filename = file.getAbsolutePath();
            JOptionPane.showMessageDialog(this, filename, "File Load Successful", JOptionPane.PLAIN_MESSAGE);
            try {
                ProcessFileForAssemble();
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            }
        }
    }

    private void ProcessFile() throws FileNotFoundException {
        /*
        To scan wether a file has been selected to load into the simulator, if not, it will throw out an error
        */
        Scanner s = new Scanner(file);
        while (s.hasNext()) {
            String loc = s.next();
            String val = s.next();
            Code.add(new StringStruct(loc, val));
            short hexloc = cpu.HexToDecimal(loc);
            short hexval = cpu.HexToDecimal(val);
            mem.Data[hexloc] = hexval;
            System.out.println(hexloc + " " + hexval);
        }
        s.close();
    }

    private void ProcessFileForAssemble() throws IOException {
        /*
        To scan wether a file has been selected to load into the simulator, if not, it will throw out an error
        */
        Scanner s = new Scanner(file);
        Scanner s2 = new Scanner(file);
        while (s.hasNext()) {
            
            String loc = s.next();
            String val = s.next();
            if(loc.equals("LOC")){
                pcount = Integer.parseInt(val);
                hexPcount = cpu.inttoHexString(Integer.parseInt(val));
            }
            if(loc.contains(":")){
                labelMap.put(loc.substring(0, loc.length() - 1).toLowerCase(),Integer.toString(pcount));
            }
        }
        labelMap.forEach((key, value) -> System.out.println(key + " " + value));
        
        s.close();
        pcount=0;
        hexPcount=cpu.inttoHexString(pcount);

        while (s2.hasNext()) {
            String loc = s2.next();
            String val = s2.next();
            if(loc.equals("LOC")){
                pcount = Integer.parseInt(val);
                hexPcount = cpu.inttoHexString(Integer.parseInt(val));
            }
            if(loc.toLowerCase().equals("data")){
                if (val.chars().allMatch( Character::isDigit)) {
                    hexAdd.add(hexPcount);
                    hexDat.add(cpu.inttoHexString(Integer.parseInt(val)));
                }
                else{
                    hexAdd.add(hexPcount);
                    hexDat.add(cpu.inttoHexString(Integer.parseInt(labelMap.get(val.toLowerCase()))));
                }
            }
            else if(labelMap.containsKey(loc.substring(0, loc.length() - 1).toLowerCase())){
                hexAdd.add(hexPcount);
                if(val.equals("HLT")){
                    hexDat.add("0000");
                }
                
            }
            else{
                String op = assem.getOpCode(loc.toUpperCase());
                if (op == "none") {
                    continue;
                }
                String[] operands = val.split(",");
                int l = operands.length;
                String address = "";

                if (l == 2) {
                    address = op + "00"+ cpu.intstrtoBin(operands[0],2) + "0" + cpu.intstrtoBin(operands[1],5);
                }

                if (l == 3) {
                    address = op + cpu.intstrtoBin(operands[0],2) + cpu.intstrtoBin(operands[1],2) + "0" + cpu.intstrtoBin(operands[2],5);
                }

                if (l == 4) {
                    address = op + cpu.intstrtoBin(operands[0],2) + cpu.intstrtoBin(operands[1],2) + "1" + cpu.intstrtoBin(operands[2],5);
                    System.out.println(address);
                }

                hexAdd.add(hexPcount);
                hexDat.add(cpu.binaryToHex(address, 4));
            }
            pcount = pcount + 1;
            hexPcount = cpu.inttoHexString(pcount);
        }
        final FileWriter outWriter = new FileWriter("AssemblerOutput.txt");

        for (int i = 0; i < hexAdd.size(); i++) {
            outWriter.write(hexAdd.get(i)+" "+ hexDat.get(i)+"\n");
        }
    s2.close();
    outWriter.close();
    }

    /*
     * Main Handler for updating LEDs and execution of CPU code 
     * Can be used independently as single step and part of Run Button
     */
    private void execCode(ActionEvent e) {
        for (int i = 0; i < 12; i++)
            RefreshLeds(i);
        short EA = cpu.BinaryToDecimal(cpu.PC, 12);
        if (EA > 2047) {
            cpu.MFR[0] = 1;
            RefreshLeds(11);
            cpu.MFHandle(mem);
            mem.Data[4]++;
            cpu.DecimalToBinary(mem.Data[4], cpu.PC, 12);
            RefreshLeds(7);
            return;
        }
        cpu.DecimalToBinary(mem.Data[EA], cpu.IR, 16);
        cpu.Execute(mem, dev);

        for (int i = 0; i < 12; i++)
            RefreshLeds(i);
        short val = cpu.BinaryToDecimal(cpu.IR, 6); // Get The IR Values to check for Conditions for Jumping
        if (val >= 0x08 && val <= 0x0F) {
            EA = cpu.BinaryToDecimal(cpu.PC, 12);
        } else if (cpu.BinaryToDecimal(cpu.MFR, 4) > 0) {
            cpu.MFHandle(mem);
            EA = mem.Data[4];
            EA++;
        } else
            EA++;
        cpu.DecimalToBinary(EA, cpu.PC, 12);
        RefreshLeds(7);
    }

    private void RunProg(ActionEvent e) throws InterruptedException {
        if (hlt.getBackground() == Color.red && Run.getBackground() == Color.black) {
            JOptionPane.showMessageDialog(this, "System halted. Go to options to reset halt status",
                    "Error: System Halt", JOptionPane.ERROR_MESSAGE);
            return;
        }
        short OpCode;
        do {
            Thread.sleep(1);
            execCode(e);
            hlt.setBackground(Color.black);
            Run.setBackground(Color.green);
            OpCode = cpu.BinaryToDecimal(cpu.IR, 6);
        } while (OpCode != CPU.HLT);
        Run.setBackground(Color.black);
        hlt.setBackground(Color.red);
        
    }

    private void runMainLoop() {
        /* This will set create and set the size for the main background of the GUI */
        this.setSize(1200, 860);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.getHSBColor((float) 0.533, (float) 0.8, (float) 0.75));
        this.setLayout(null);
        this.setVisible(true);
        // dev.Run();
    }


    private int takeInput(ActionEvent e) throws IOException {

        NumbersList.add(Integer.parseInt(textField.getText()));
        textField2.setText(textField.getText());
        textField.setText("");

        if(NumbersList.size() == 20){
            String userInput = JOptionPane.showInputDialog(null, "Enter any number to find closest", "", JOptionPane.QUESTION_MESSAGE);
            textField.setText(userInput);
            // textField2.setText(userInput);
            ent.setText("Entered Value");
            near.setText("Nearest Value");

            int nearestNumber = NumbersList.get(0);
            int minDifference = Math.abs(nearestNumber - Integer.parseInt(userInput));

            for (int number : NumbersList) {
                int difference = Math.abs((number - Integer.parseInt(userInput)));
                if (difference < minDifference) {
                    minDifference = difference;
                    nearestNumber = number;
                }
            textField2.setText(""+nearestNumber);
        }
            NumbersList = new ArrayList<Integer>();
            return 1;
        }
        return 0;
        
    }

    public void LoadGui() {
        /** This creates the "Store" button on the GUI */
        store = new JButton("Store");
        store.addActionListener(e -> Store(e));
        store.setBounds(375, 430, 70, 35);
        this.add(store);
        /** This creates the "St+" on the GUI */
        st_plus = new JButton("St+");
        st_plus.addActionListener(e -> StorePlus(e));
        st_plus.setBounds(450, 430, 70, 35);
        this.add(st_plus);
        /** This creates the "Load" on the GUI */
        load = new JButton("Load");
        load.addActionListener(e -> LoadValue(e));
        load.setBounds(525, 430, 70, 35);
        this.add(load);
        /** This creates the "Init" button to the GUI */
        init = new JButton("Init");
        init.setBounds(750, 430, 70, 35);
        init.setBackground(Color.RED);
        init.setForeground(Color.black);
        init.setOpaque(true);
        init.setBorderPainted(false);
        init.addActionListener(e -> loadFile(e));
        this.add(init);

        assemble = new JButton("Assemble");
        assemble.setBounds(850, 420, 120, 50);
        assemble.setBackground(Color.RED);
        assemble.setForeground(Color.black);
        assemble.setOpaque(true);
        assemble.setBorderPainted(false);
        assemble.addActionListener(e -> {
            try {
                loadFileForAssemble(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        this.add(assemble);

        ent = new JLabel("");
        ent.setBounds(285, 690, 200, 50);
        ent.setFont(new Font("Arial", Font.BOLD, 20));

        near = new JLabel("");
        near.setBounds(790, 690, 200, 50);
        near.setFont(new Font("Arial", Font.BOLD, 20));

        this.add(ent);
        this.add(near);

        KeyBoard = new JLabel("Keyboard");
        textField = new JTextField();
        KeyBoard.setBounds(310, 550, 200, 50);    
        KeyBoard.setFont(new Font("Arial", Font.BOLD, 20));       
        textField.setBounds(250, 600, 200, 100);
        textField.setFont(new Font("Arial", Font.BOLD, 42));
        this.add(KeyBoard);
        this.add(textField);

        Printer = new JLabel("Printer");
        textField2 = new JTextField();
        Printer.setBounds(840, 550, 200, 50);  
        Printer.setFont(new Font("Arial", Font.BOLD, 20));      
        textField2.setBounds(770, 600, 200, 100);
        textField2.setFont(new Font("Arial", Font.BOLD, 42));
        this.add(Printer);
        this.add(textField2);

        inputNumber = new JButton("Load");
        inputNumber.setBounds(300, 730, 80, 40);
        inputNumber.setBackground(Color.RED);
        inputNumber.setForeground(Color.black);
        inputNumber.setOpaque(true);
        inputNumber.setBorderPainted(false);
        inputNumber.setFont(new Font("Arial", Font.BOLD, 14));
        inputNumber.addActionListener(e -> {
            try {
                takeInput(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        this.add(inputNumber);

        FindNearest = new JButton("EXIT");
        FindNearest.setBounds(800, 730, 160, 40);
        FindNearest.setBackground(Color.RED);
        FindNearest.setForeground(Color.black);
        FindNearest.setOpaque(true);
        FindNearest.setBorderPainted(false);
        FindNearest.setFont(new Font("Arial", Font.BOLD, 14));
        FindNearest.addActionListener(e -> {
            System.exit(0);
        });
        this.add(FindNearest);

        /** This creates the "SS" button to the GUI */
        ss = new JButton("SS");
        ss.setBounds(600, 405, 65, 80);
        ss.addActionListener(e -> execCode(e));
        /** This creates the "Run" button to the GUI */
        run = new JButton("Run");
        run.setBounds(675, 405, 65, 80);
        run.addActionListener(e -> {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    RunProg(e);
                    return null;
                }
            };
            worker.execute();
        });
        run.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.add(ss);
        this.add(run);
        runMainLoop();
    }
}
