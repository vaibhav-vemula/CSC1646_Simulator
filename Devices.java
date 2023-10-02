import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.ArrayList;

/* This file contains instructions for printing and connecting to input and output devices while the simulator is operating. */
public class Devices extends JFrame{
    private ArrayList<JPanel> panel;
    private JTextArea ConsoleOut,ConsoleIn;
    public int printerStatus=0; // check for console printer : 0 if unused, 1 if used
    public Devices(){
        super("Console");
        this.setSize(960,480);
        this.setLayout(null);
        panel = new ArrayList<JPanel>();
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());
        
        TitledBorder ConsoleOutBorder = new TitledBorder("Console Printer");
        ConsoleOutBorder.setTitleJustification(TitledBorder.CENTER);
        ConsoleOutBorder.setTitlePosition(TitledBorder.TOP);
        TitledBorder ConsoleInBorder = new TitledBorder("Console Keyboard");
        ConsoleInBorder.setTitleJustification(TitledBorder.CENTER);
        ConsoleInBorder.setTitlePosition(TitledBorder.TOP);

        ConsoleIn = new JTextArea("", 1, 20);
        ConsoleOut = new JTextArea(16,38);
        ConsoleOut.setFont(new Font("Courrier New",Font.BOLD,14));
        //ConsoleOut.setBounds(0,0,350,300);
        ConsoleOut.setEditable(false);
        ConsoleOut.setBackground(Color.BLACK);
        ConsoleOut.setForeground(Color.green);

        panel.get(0).setBorder(ConsoleOutBorder);       
        panel.get(0).add(ConsoleOut);

        panel.get(1).setBorder(ConsoleInBorder);
        panel.get(1).add(ConsoleIn);

        panel.get(0).setBounds(10, 10, 480, 340);
        panel.get(1).setBounds((480-250)/2+10, 360, 250, 60);
        panel.get(2).setBounds(500,10,440,420);
        for(int i=0;i<3;i++)
            this.add(panel.get(i));
    }
    public void emptyConsole(){
        ConsoleOut.setText(null);
        ConsoleIn.setText(null);
    }
    public void LoadDevices(short devid, String input){
        switch (devid) {
            case 1:
                //keyboard();
                break;
            case 2:
                //printer(input);
                break;
        }

    }
    public int boardstatus=0;
    public int keyboardStatus(){
        if(ConsoleIn.getText().length()>0) boardstatus=1; //Text in Keyboard
        else boardstatus=0; // Empty to Use.
        return boardstatus;
    }
    public void keyboard(char []Reg){
        Converter conv = new Converter();
        try{
            short c = (short)ConsoleIn.getText().charAt(0);
            conv.DecimalToBinary(c, Reg, 16);
            if(ConsoleIn.getText().length()>1){
                ConsoleIn.setText(ConsoleIn.getText().substring(1));
            }else if(ConsoleIn.getText().length()==1)
                ConsoleIn.setText(null);
        }catch(Exception e){
            conv.DecimalToBinary((short)0, Reg, 16);
            System.out.println("No Input Found");
        }
    }

    public void printer(char[] Reg){
        printerStatus=1;
        Converter conv = new Converter();
        Character dec = (char)conv.BinaryToDecimal(Reg, 16);
        StringBuilder build = new StringBuilder();
        build.append(dec);
        String s = new String(build.toString());
        try{
            ConsoleOut.append(s);
        }catch(Exception e){
            return;
        }
    }
    public void Run(){
        this.setVisible(true);
    }
}