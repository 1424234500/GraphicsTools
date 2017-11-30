package util;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.Constant;

public class DemoPanelMain extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1777279418131350603L;

	private static final String help = "中文拼英及其首字母序列编码";
 
	int screenWidth = Constant.screenWidth;
	int screenHeight = Constant.screenHeight;
/////////////////////////////////////////////////////////
	 
	 
	JPanel jps = new JPanel();	//panel south 
	JPanel jpn = new JPanel();	//panel nouth 

	JScrollPane jstextareaout, jstextareain ;
	JTextArea jtain = new JTextArea(12, 86);
	JTextArea jtaout = new JTextArea(12, 86);
	JButton jbclear = new JButton("clear");
/////////////////////////////////////////////////////////
	public DemoPanelMain() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭窗体是执行
		this.setTitle("编码控制");
		this.setBounds(Constant.windowX, Constant.windowY, Constant.screenWidth, Constant.screenHeight);
		
///////////////////////////
		JButton bs[] = {   jbclear };
		for(JButton jb: bs){
			jb .addActionListener(this);
		}	
////////////////////////
		this.jpn.setLayout(new FlowLayout());
		 
		Component cs[] = {jbclear   };
		for (Component c : cs) {
			this.jpn.add(c);
		}
		  
		/////////////////////////////////
		this.jstextareain = new JScrollPane(this.jtain);
		this.jstextareaout = new JScrollPane(this.jtaout);
		jstextareain.setBounds(Constant.spX, Constant.spY, Constant.spW  , Constant.spH);
		jps.add(this.jstextareain); 
		jps.add(this.jstextareaout); 
		
		jpn.setBounds(Constant.npX, Constant.npY, Constant.npW, Constant.npH);
		jps.setBounds(Constant.spX, Constant.spY, Constant.spW, Constant.spH);
	
		this.setLayout(null);
		this.add(jpn ); 
		
		
		this.setBackground(Constant.colorBackground);
		this.setVisible(true);
 
		jtaout.setText(this.help);
		
		 
			
	}

	public void actionPerformed(ActionEvent e) {
		 
		if (e.getSource() == jbclear) {

		}else if(e.getSource() == jbclear){

		}else if(e.getSource() == jbclear){

		}
		
	}
 
	void out(String s) {
		if (true){
			System.out.println("PM:>> " + s);
			this.jtaout.append("\n" + s);
			this.jtaout.setCaretPosition(this.jtaout.getText().length());	//锁定最底滚动
		}
	}

	public   void alert(String s) {
		out("Alert! @ " + s);
		// 弹出的是警告提示框!", "系统信息", JOptionPane.WARNING_MESSAGE);
		JOptionPane.showMessageDialog(null, s, "警告", 1);
	}

	public static boolean ifok(String s) {
		int option = JOptionPane.showConfirmDialog(null, s, "警告！",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
		switch (option) {
		case JOptionPane.YES_NO_OPTION: {
			return true;
		}
		default:
			return false;
		}
	} 
}



