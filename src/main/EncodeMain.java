package main;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.ExcelUtil;
import util.FileDialog;
import util.FileUtil;
import util.Fun;
import util.encode.Pinyin;

public class EncodeMain extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1777279418131350603L;

	private static final String help = ""
			+ "文件打开：中文拼英及其首字母序列编码在输入框中输入路径或者选择路径打开文件\n"
			+ "随机中文：输入汉字个数生成随机中文eg:10,10 = 10个一串，生成10次 \n"
			+ "随机名字：输入名字生成次数eg:10 = 生成10次名字\n"
			+ ""
			+ ""
			+ "";
 
	int screenWidth = Constant.screenWidth;
	int screenHeight = Constant.screenHeight;
/////////////////////////////////////////////////////////
	 
	 
	JPanel jps = new JPanel();	//panel south 
	JPanel jpn = new JPanel();	//panel nouth 

	JScrollPane jstextareaout, jstextareain ;
	JTextArea jtain = new JTextArea(30, 46);
	JTextArea jtaout = new JTextArea(30, 46);
	JButton jbclear = new JButton("clear");
	
	JButton jbchosesavedir = new JButton("选择文件"); // 点击后自动显示文件s
	JTextField jtfchosefile = new JTextField(Constant.jtfSize16);
	
	
	JButton jbrandomname = new JButton("随机名字");
	JButton jbrandom = new JButton("随机中文组合");
	JButton jbencode = new JButton("拼音");
	JButton jbencodesimple = new JButton("拼音首字母");
	JButton jbopenfile = new JButton("打开文件");
	JButton jbclear1 = new JButton("clear");
/////////////////////////////////////////////////////////
	public EncodeMain() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭窗体是执行
		this.setTitle("编码控制");
		this.setBounds(Constant.windowX, Constant.windowY, Constant.screenWidth, Constant.screenHeight);
		this.setLayout(new FlowLayout());
		//this.setLayout(null);
		
		
		jtain.setText(this.help);

		
		this.jpn.setLayout(new FlowLayout());

///////////////////////////
		JButton bs[] =  { jbchosesavedir, jbclear1 ,jbopenfile, jbrandomname,  jbencode, jbencodesimple,jbrandom,jbclear };
		for(JButton jb: bs){
			jb .addActionListener(this);
		}	
////////////////////////
		Component cc[] = { jbclear1 ,jbchosesavedir, jtfchosefile, jbopenfile, jbencode, jbencodesimple,jbrandom,jbrandomname,jbclear,};
		for(Component c: cc){
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
	
		
		this.add(jpn ); 
		this.add(jps);
		
		
		
		this.setBackground(Constant.colorBackground);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		 
		if (e.getSource() == jbchosesavedir) {
			String path = FileDialog.getDirOrFilePath(this.jtfchosefile.getText());
			this.jtfchosefile.setText(path);
			if((new File(path).isFile())){
				this.jbopenfile.doClick();
			}
		}else if(e.getSource() == jbopenfile){
			final String path = this.jtfchosefile.getText();
			jtain.setText("");
			FileUtil.mkdir(FileUtil.getFilePath(path)+"/" + "ToolSave");

			if((new File(path).isFile())){
				FileUtil.readByType(path,  new Fun<String>() {
					public void make(String obj) {
						jtain.append(obj +"\n" ); 
						jtain.setCaretPosition(jtain.getText().length());	//锁定最底滚动
					}
				}, new Fun<ArrayList<ArrayList<Object>>>() {
					public void make(ArrayList<ArrayList<Object>> obj) {
						for(ArrayList al : obj){
							for(Object o : al){
								jtain.append(" " + o.toString() + "\t ");
							}
							jtain.append("\n");
						}
						ExcelUtil.writeExcel(obj, FileUtil.getFilePath(path)+"/" + "ToolSave"+"/"+FileUtil.getFileNameOnly(path)+".xls");
					}
				});
				jtain.append("\n"); 
				
			}else{
				FileUtil.controlDirs(path, new Fun<File>() {
					public void make(File file) {
						final String ppath = file.getAbsolutePath();
						FileUtil.mkdir(FileUtil.getFilePath(ppath)+"/" + "ToolSave");
						jtain.append("\nFile: " + file.getAbsolutePath() +  "\n"
								+ "############-start##############-\n" );
						FileUtil.readByType(file.getAbsolutePath(),  new Fun<String>() {
							public void make(String obj) {
								jtain.append(obj +"\n" ); 
							}
						}, new Fun<ArrayList<ArrayList<Object>>>() {
							public void make(ArrayList<ArrayList<Object>> obj) {
								for(ArrayList al : obj){
									for(Object o : al){
										jtain.append(" " + o.toString() + "\t ");
									}
									jtain.append("\n");
								}
								ExcelUtil.writeExcel(obj, FileUtil.getFilePath(ppath)+"/" + "ToolSave"+"/"+FileUtil.getFileNameOnly(ppath)+".xls");
							}
						});
						jtain.append("\n############-end##############-\n" );
						//jtain.setCaretPosition(jtain.getText().length());	//锁定最底滚动
					}
				});
			}
		}else if(e.getSource() == jbencode){
			out("####################-");
			out(Pinyin.getPinYin( jtain.getText() ) );
			out("####################-");
		}else if(e.getSource() == jbencodesimple){
			out("####################-");
			out(Pinyin.getPinYinHeadChar( jtain.getText() ) );
			out("####################-");
		}else if(e.getSource() == jbrandom){
			out(help);
			out("生成随机中文####################-");
			String ss[] = jtfchosefile.getText().split(",");
			if(ss.length < 2){
				out("参数错误");
				out(help);
			}else{
				int len = Integer.parseInt(ss[0]);
				int count = Integer.parseInt(ss[1]);
				in("\n############-start##############-\n" + "len,count= " +len+","+count);
				Pinyin.random(len,count, new Fun<String>(){
					public void make(String obj) {
						in(obj);
					}
				} );
				in("\n############-end##############-\n" );
			}
		}else if(e.getSource() == jbrandomname){
			out("生成随机中文名字####################-");
			int count = Integer.parseInt(jtfchosefile.getText());
			in("\n############-start##############-\n" + " count= "  +count);
			Pinyin.randomName(count, new Fun<String>(){
				public void make(String obj) {
					in(obj);
				}
			} );
			in("\n############-end##############-\n" );
		}else if(e.getSource() == jbclear1){
			jtain.setText("");
		}else if(e.getSource() == jbclear){
			jtaout.setText(help);
		}else if(e.getSource() == jbclear){
			jtaout.setText(help);
		}else if(e.getSource() == jbclear){
			jtaout.setText(help);
		}else if(e.getSource() == jbclear){
			jtaout.setText(help);
		}
		
	}
 
	void out(String s) {
		if (true){
			System.out.println("EM:>> " + s);
			this.jtaout.append("\n" + s);
			this.jtaout.setCaretPosition(this.jtaout.getText().length());	//锁定最底滚动
		}
	}
	void in(String s) {
		if (true){
			System.out.println("EM:>> " + s);
			this.jtain.append("\n" + s);
			this.jtain.setCaretPosition(this.jtain.getText().length());	//锁定最底滚动
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



