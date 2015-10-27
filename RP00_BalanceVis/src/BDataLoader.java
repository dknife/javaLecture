import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class BDataLoader extends JFrame {
	
	static double balanceData[][] = new double[4000][2];
	
	BDataLoader () {
		setTitle("BalanceData Loader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		
		JButton btn = new JButton("Action");
		btn.setBackground(Color.YELLOW);
		add(btn);
		
		setSize(300,150);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		String input = "../../BalanceData/00003.txt";
		String output = "../../BalanceData/00003.ans";
		
		try {
			FileInputStream fin = new FileInputStream(input); // source file
			FileOutputStream fout = new FileOutputStream(output);
			DataOutputStream dout = new DataOutputStream(fout);
			
			
			int c;
			boolean readingX = true;
			double x; double y;
			int count=0;
			
            while ( (c=fin.read()) != -1 ) {
                if(c=='+') {
                	int base = 100; x=0; y=0;
                	for(int i=0;i<3;i++) {
                		c=fin.read();
                		if(readingX) x += (c-'0')*base;
                		else y+= (c-'0')*base;
                		base /= 10;
                	}
                	if(readingX) balanceData[count][0] = x;
                	else balanceData[count][1] = y;
                	readingX = readingX?false:true;
                	if(readingX) count++;
                }
                else if(c=='-') {
                	int base = 100; x=0; y=0;
                	for(int i=0;i<3;i++) {
                		c=fin.read();
                		if(readingX) x += (c-'0')*base;
                		else y+= (c-'0')*base;
                		base /= 10;
                	}
                	if(readingX) x*=-1.0;
                	else y*=-1.0;
                	if(readingX) balanceData[count][0] = x;
                	else balanceData[count][1] = y;
                	readingX = readingX?false:true;
                	if(readingX) count++;
                }
            }
            for(int i=0;i<40;i++) {
            	dout.writeDouble(balanceData[i][0]);
            	dout.writeDouble(balanceData[i][1]);
            }
            fin.close();
            fout.close();
            
		}
		catch( IOException e) {
			System.out.println("io exception");
		}
		System.out.println("done");
		new BDataLoader();
	}
}