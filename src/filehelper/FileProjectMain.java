package filehelper;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.qt.datapicker.DatePicker;

import main.Constant;
import util.DatePickTextField;
import util.FileDialog;
import util.FileUtil;
import util.Fun;
import util.Tools;
import util.UniqueRandom;
import util.setting.Setting;

public class FileProjectMain extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1777279418131350603L;

	int screenWidth = Constant.screenWidth;
	int screenHeight = Constant.screenHeight;
	// ///////////////////////////////////////////////////////
	String helpstr = "目录文件筛选 时间段yyyy-MM-dd HH:mm:ss，正则ad123mn==(.*d\\w+d121.*1.+\\w+)|(.8d.*[1-3]{2,3}.*n) 复制结构到指定目录， "
			+ " ";
	JPanel jpn = new JPanel();// panel north

	JButton jbchosedir = new JButton("选择"); // 点击后自动显示文件s
	JTextField jtfchosedir = new JTextField(Constant.jtfSize16);

	JButton jbshowfiles = new JButton("显示");

	JButton jbchosesavedir = new JButton("选择存储"); // 点击后自动显示文件s
	JTextField jtfchosesavedir = new JTextField(Constant.jtfSize16);

	JButton jbexe = new JButton("复制文件"); 
	JLabel jlfromtype = new JLabel("类型");
	JTextField jtffromtype = new JTextField(Constant.jtfSize4);

	JLabel jlfreg = new JLabel("正则");
	JTextField jtfreg = new JTextField(Constant.jtfSize8);
	
	JButton jbdatefrom = new JButton("日期from");
	JButton jbdateto = new JButton("日期to");
	DatePickTextField jtfdatefrom = new DatePickTextField(Constant.jtfSize8);
	DatePickTextField jtfdateto = new DatePickTextField(Constant.jtfSize8);

	
	JCheckBox jcbshowdir = new JCheckBox("显示目录"); // 点击后自动显示文件s
	JComboBox<String> comboBoxOrder=new JComboBox<String>();   
	JLabel jlhelp = new JLabel(helpstr);

	JPanel jpn2 = new JPanel();// panel north
 

	JPanel jps = new JPanel(); // panel south

	JScrollPane jstextarea;
	JTextArea jtaout = new JTextArea(25, 86);
	JButton jbcleartext = new JButton("ClearInfo");

	// ///////////////////////////////////////////////////////
	public FileProjectMain() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭窗体是执行
		this.setTitle("DataBaseManageSystem");
		this.setBounds(Constant.windowX, Constant.windowY,
				Constant.screenWidth, Constant.screenHeight);

		// //////////////////////
 

		Component cs[] = { jbchosedir, jtfchosedir, jbshowfiles ,jcbshowdir,  jbchosesavedir,
				jtfchosesavedir };
		for (Component c : cs) {
			this.jpn.add(c);
		}
		jbexe.addActionListener(this);
		jbchosedir.addActionListener(this);
		jbchosesavedir.addActionListener(this);
		jbshowfiles.addActionListener(this);
		jbcleartext.addActionListener(this);
		 
		jbdatefrom.addActionListener(this);
		jbdateto.addActionListener(this);

		jcbshowdir.setSelected(true);
		jtffromtype.setText("jsp,class");
		
		comboBoxOrder.addItem("no");  
		comboBoxOrder.addItem("time");  
		comboBoxOrder.addItem("name");  
		comboBoxOrder.addItem("size");  
		
		Component cs2[] = { jlfromtype, jtffromtype,jlfreg, jtfreg,jbdatefrom,jtfdatefrom,jbdateto,jtfdateto,/*comboBoxOrder, */  jbexe, jbcleartext };
		for (Component c : cs2) {
			this.jpn2.add(c);
		}
		jpn2.setBounds(Constant.npX2, Constant.npY2, Constant.npW2, Constant.npH2);

		// ///////////////////////////////
		this.jstextarea = new JScrollPane(this.jtaout);
		// jstextarea.setBounds(Constant.ccX, Constant.ccY, Constant.ccW,
		// Constant.ccH*2);
		jps.add(this.jstextarea);

		jpn.setBounds(Constant.npX, Constant.npY, Constant.npW, Constant.npH);
		jpn2.setBounds(Constant.npX2, Constant.npY2, Constant.npW2,
				Constant.npH2);
		jps.setBounds(Constant.ccX, Constant.ccY + 20, Constant.ccW,
				Constant.ccH * 2);

		this.add(jpn);
		this.add(jpn2);
		jlhelp.setBounds(Constant.npX2, Constant.npY2 + 30, Constant.npW2,
				Constant.npH2);

		this.add(jlhelp);
		this.add(jps);
		this.setBackground(Constant.colorBackground);
		this.jpn.setLayout(new FlowLayout());
		this.setLayout(null);

		this.setVisible(true);

		init();
		
	}
	public void init(){
		jtfchosedir.setText(Setting.getProperty("dir", "F:\\testspace"));
		jcbshowdir.setSelected(Setting.getProperty("showdir", "false").equals("true"));
		jtfchosesavedir.setText(Setting.getProperty("savedir", "F:\\desktop\\make"));
		jtffromtype.setText(Setting.getProperty("fromtype", "jsp,class"));
		jtfreg.setText(Setting.getProperty("reg", ".*.*"));

		jtfdatefrom.setText(Setting.getProperty("datefrom", ""));
		jtfdateto.setText(Setting.getProperty("dateto", "")); 
	}
	public void save(){
		Setting.saveManyProperty("dir", jtfchosedir.getText()
				,"showdir", jcbshowdir.isSelected()?"true":"false"
				,"savedir", jtfchosesavedir.getText()
				,"fromtype", jtffromtype.getText()
				,"reg", jtfreg.getText()
				,"datefrom", jtfdatefrom.getText()
				,"dateto", jtfdateto.getText()
				); 
 
	}
	
	
	int startid = 0;//起点
	int countid = 0;	//计数
	int showDirLenTemp = 0;
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == jbdatefrom) {
				String datefrom = Tools.getNowTimeL();
				jtfdatefrom.setText(datefrom); 
//			     DatePicker dp = new DatePicker(jtfdatefrom, new Date());
//                 // previously selected date
//                 Date selectedDate = dp.parseDate(jtfdatefrom.getText());
//                 dp.setSelectedDate(selectedDate);
//                 dp.start(jbdatefrom);
			}else if (e.getSource() == jbdateto) { 
				jtfdateto.setText(Tools.getNowTimeL());
//				  DatePicker dp = new DatePicker(jtfdateto, new Date());
//                 // previously selected date
//                 Date selectedDate = dp.parseDate(jtfdateto.getText());
//                 dp.setSelectedDate(selectedDate);
//                 dp.start(jtfdateto);
			}else if (e.getSource() == jbchosedir) {
				String path = FileDialog.getDirPath(jtfchosedir.getText());
				if(!path .equals("")){
					this.jtfchosedir.setText(path);
					//this.jbshowfiles.doClick();
				}
			} else if (e.getSource() == jbshowfiles) {
				save();
				this.jbcleartext.doClick();
				
				String path = jtfchosedir.getText();
				final String fromtype = this.jtffromtype.getText();
				final String fromreg = this.jtfreg.getText();
				final String fromtime = this.jtfdatefrom.getText();
				final String totime = this.jtfdateto.getText();
				
				
				FileUtil.showDir(path, new Fun<File>() {
					public void make(File obj) {
						if(obj.isFile()){
							long mtime = obj.lastModified();
							long size = obj.length();
							if(ifExt(obj.getName(), fromtype))
								if(ifReg(obj.getAbsolutePath(), fromreg)){
									if(ifTime(fromtime, totime, mtime)){
										if(jcbshowdir.isSelected())
										{
											out(Tools.getFill(showDirLenTemp, " ") + obj.getName() 
											+ "\t" + new Date(mtime).toLocaleString()
											+ "  " + Tools.getStringBySize(size)
													);
										}else{
											out(obj.getAbsolutePath() 
											+ "\t" + new Date(mtime).toLocaleString()
											+ "  " + Tools.getStringBySize(size)
										);
										}
										
									}
								}
							}else if(obj.isDirectory()){
							showDirLenTemp =   obj.getAbsolutePath().length() ;
							if(jcbshowdir.isSelected())
								out("" + obj.getAbsolutePath()  );

						}else {
							 
						}
					}
				});
				
 
			} else if (e.getSource() == jbchosesavedir) {
				String path = FileDialog.getDirPath(jtfchosesavedir.getText());
				this.jtfchosesavedir.setText(path);
			}else if (e.getSource() == jbcleartext) {
				jtaout.setText("");
				
			} else if (e.getSource() == jbexe) {
				save();
				
				final String path = jtfchosedir.getText();
				final String fromtype = this.jtffromtype.getText();
				final String fromreg = this.jtfreg.getText();
				final String fromtime = this.jtfdatefrom.getText();
				final String totime = this.jtfdateto.getText();
			    final String topath = jtfchosesavedir.getText();

			
			FileUtil.showDir(path, new Fun<File>() {
				public void make(File obj) {
					if(obj.isFile()){
						long mtime = obj.lastModified();
						long size = obj.length();
						if(ifExt(obj.getName(), fromtype))
							if(ifReg(obj.getAbsolutePath(), fromreg)){
								if(ifTime(fromtime, totime, mtime)){
									//copy to the dir;
									String fromfile = obj.getAbsolutePath();
									String tofile = topath + "\\" + fromfile.substring(path.length()) ;

									if(jcbshowdir.isSelected())
									{
										out(obj.getName() 
										+ "  " + new Date(mtime).toLocaleString()
										+ "  " + Tools.getStringBySize(size)
										+ " -> " + tofile
												);
									}else{
										out(obj.getAbsolutePath() 
										+ "  " + new Date(mtime).toLocaleString()
										+ "  " + Tools.getStringBySize(size)
										+ " -> " + tofile
									);
									} 
							
									FileUtil.copyFile(fromfile, tofile);
								}
							}
						}else if(obj.isDirectory()){
						showDirLenTemp =   obj.getAbsolutePath().length() ;
						if(jcbshowdir.isSelected())
							out("" + obj.getAbsolutePath()  );

					}else {
						 
					}
				}
			});
 
				  
				  
			}else{
				
			}
		}catch(Exception ee){
			ee.printStackTrace();
		}
	}
	//2017-8-25 15:05:28
	public boolean ifTime(String fromtime, String totime, long mtime){
		boolean res = false;

		long ftime = Tools.dateFormat(fromtime, "yyyy-MM-dd HH:mm:ss").getTime();
		long ttime = Tools.dateFormat(totime, "yyyy-MM-dd HH:mm:ss").getTime();
		
		if(fromtime.equals("")){
			res = true;
		}else if(ftime <= mtime){
			if(totime.equals("")){
				res = true;
			}else if(ttime >= mtime){
				res = true;
			}
		}

		return res;
	}
	public boolean ifExt(String filename,String exts){
		boolean res = false;
		String ext =  FileUtil.getFileType(filename);
		String ss[] = exts.split(",");
		for(String s : ss){
			if(ext.equals(s) || s.equals("*")){
				res = true;
				break;
			}
		}
		
		return res;
	}
	public boolean ifReg(String filepath,String reg){
		boolean res = false; 
		if(reg == null || reg.equals("")) res = true;
		else{
			if(filepath.matches(reg)){
				//if(filepath.contains(reg)){
				res = true;
			}
		} 
		 
		return res;
	}
	
	
	
	void out(String s) {
		if (true) {
			System.out.println("FileProject>> " + s);
			this.jtaout.append("\n" + s);
			this.jtaout.setCaretPosition(this.jtaout.getText().length()); // 锁定最底滚动
			//this.jtaout.invalidate();
		}
	}

	public void alert(String s) {
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
