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

import main.Constant;
import util.FileDialog;
import util.Tools;
import util.RandomUtil;

public class FilePanelMain extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1777279418131350603L;

	int screenWidth = Constant.screenWidth;
	int screenHeight = Constant.screenHeight;
	// ///////////////////////////////////////////////////////
	String helpstr = "文件名组装特殊输入符号#$!!($x代表原文件名的分割后的数组的序号,eg:filename=2-12.png $0=2,$1=12)(#x代表自增长int型,eg:#0=0,1,2,3,x: 该型最多一个)";
	JPanel jpn = new JPanel();// panel north

	JButton jbchosedir = new JButton("选择文件夹"); // 点击后自动显示文件s
	JTextField jtfchosedir = new JTextField(Constant.jtfSize16);

	JButton jbshowfiles = new JButton("显示");

	JButton jbchosesavedir = new JButton("选择存储文件夹"); // 点击后自动显示文件s
	JTextField jtfchosesavedir = new JTextField(Constant.jtfSize16);

	JButton jbexe = new JButton("执行批量处理");
	JButton jbcalclines = new JButton("计算代码行");

	JLabel jlsplitname = new JLabel("原名分割符");
	JTextField jtfsplitname = new JTextField(Constant.jtfSize2);
	JLabel jlfromtype = new JLabel("处理后缀类型");
	JTextField jtffromtype = new JTextField(Constant.jtfSize2);
	JCheckBox jcbchar = new JCheckBox("统计字符"); // 点击后自动显示文件s
	JComboBox<String> comboBoxOrder=new JComboBox<String>();  
	
	JCheckBox jcborder = new JCheckBox("倒序"); //  
	JCheckBox jcbran = new JCheckBox("乱序重命名"); //  

	JLabel jlhelp = new JLabel(helpstr);

	JPanel jpn2 = new JPanel();// panel north

	JLabel jlfirstname = new JLabel("文件名前缀:");
	JTextField jtffirstname = new JTextField(Constant.jtfSize2);
	JLabel jlsecondname = new JLabel("文件名中缀:");
	JTextField jtfsecondname = new JTextField(Constant.jtfSize2);
	JLabel jlthridname = new JLabel("文件名后缀:");
	JTextField jtfthridname = new JTextField(Constant.jtfSize2);

	JLabel jlfiletype = new JLabel("文件扩展名:");
	JTextField jtffiletype = new JTextField(Constant.jtfSize2);

	JPanel jps = new JPanel(); // panel south

	JScrollPane jstextarea;
	JTextArea jtaout = new JTextArea(25, 86);
	JButton jbcleartext = new JButton("ClearInfo");

	// ///////////////////////////////////////////////////////
	public FilePanelMain() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭窗体是执行
		this.setTitle("DataBaseManageSystem");
		this.setBounds(Constant.windowX, Constant.windowY,
				Constant.screenWidth, Constant.screenHeight);

		// //////////////////////

		Component cs[] = { jbchosedir, jtfchosedir, jbshowfiles, jlsplitname,
				jtfsplitname, jlfromtype, jtffromtype, jbchosesavedir,
				jtfchosesavedir };
		for (Component c : cs) {
			this.jpn.add(c);
		}
		jbexe.addActionListener(this);
		jbchosedir.addActionListener(this);
		jbchosesavedir.addActionListener(this);
		jbshowfiles.addActionListener(this);
		jbcleartext.addActionListener(this);
		jbcalclines.addActionListener(this);


		comboBoxOrder.addItem("no");  
		comboBoxOrder.addItem("time");  
		comboBoxOrder.addItem("name");  
		comboBoxOrder.addItem("size");  
		
		Component cs2[] = {comboBoxOrder,jcborder,jcbran, jcbchar, jbcalclines, jlfirstname, jtffirstname, jlsecondname,
				jtfsecondname, jlthridname, jtfthridname, jlfiletype,
				jtffiletype, jbexe, jbcleartext };
		for (Component c : cs2) {
			this.jpn2.add(c);
		}
		jpn2.setBounds(Constant.npX2, Constant.npY2, Constant.npW2,
				Constant.npH2);

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

	}

	int startid = 0;//起点
	int countid = 0;	//计数
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == jbchosedir) {
				String path = FileDialog.getDirPath(jtfchosedir.getText());
				this.jtfchosedir.setText(path);
				this.jtfchosesavedir.setText(path);
				this.jbshowfiles.doClick();

			} else if (e.getSource() == jbshowfiles) {
				String path = jtfchosedir.getText();

				File file = new File(path);
				File tempList[] = file.listFiles();
				
				
				//排序???
				String order = (String) comboBoxOrder.getSelectedItem();
				boolean orderRe = jcborder.isSelected();
				tempList = orderBy(tempList, order, orderRe);
				
				out("---------------------------------------##");
				out("该目录下对象个数：" + tempList.length);
				out("文件夹-------------$\n");
				for (int i = 0; i < tempList.length; i++) { // 先添加所有目录
					if (tempList[i].isDirectory()) {
						out("~/ " + tempList[i].getName());
					}
				}
				out("\n文件------------$\n");
				for (int i = 0; i < tempList.length; i++) { // 再添加所有文件
					if (tempList[i].isFile()) {
						out("" + tempList[i].getName());
					}
				}
				out("-----------------------------------------##");
			} else if (e.getSource() == jbchosesavedir) {
				String path = FileDialog.getDirPath(jtfchosesavedir.getText());
				this.jtfchosesavedir.setText(path);
			}else if (e.getSource() == jbcalclines) {
				//计算所选择的文件夹里 的 所有 处理扩展名的 代码行数
				String fromdir = this.jtfchosedir.getText();
				String pattern = this.jtffromtype.getText();
				String[] pas = pattern.split(",");
				new CodeLinesStatistic(fromdir, pas, this);
				
			} else if (e.getSource() == jbexe) {
				  startid = 0;//起点
				  countid = 0;	//计数
				String fromdir = this.jtfchosedir.getText();
				String todir = this.jtfchosesavedir.getText();

				String split = this.jtfsplitname.getText();
				split = split.equals("") ? " " : split;
				
				String fromtype = this.jtffromtype.getText();
				
				String totype = this.jtffiletype.getText();

				// "文件名组装特殊输入符号#$!!
				// ($x代表原文件名的分割后的数组的序号,eg:filename=2-12.png $0 -> 2, $1 -> 12)
				// (#x代表自增长int型,eg:#0 -> 0,1,2,3,x: 该型最多一个)";
				//乱序 勾选 则生成随机数组 乱序重命名
				
				String first = this.jtffirstname.getText();
				String second = this.jtfsecondname.getText();
				String thrid = this.jtfthridname.getText();
				// out("----" + first + "  " + second + " " + thrid);
				if (!first.equals(""))
					if (first.charAt(0) == '#') {
						startid = Integer.parseInt(first.substring(1, first.length()));
					}
				if (!second.equals(""))
					if (second.charAt(0) == '#') {
						startid = Integer.parseInt(second.substring(1, second.length()));
					}
				if (!thrid.equals(""))
					if (thrid.charAt(0) == '#') {
						startid = Integer.parseInt(thrid.substring(1, thrid.length()));
					}
				// out("------" + startid);


				File file = new File(fromdir);
				File tempList[] = file.listFiles();
				
				//排序???
				String order = (String) comboBoxOrder.getSelectedItem();
				boolean orderRe = jcborder.isSelected();
				tempList = orderBy(tempList, order, orderRe);
			
				
				int dirslen = 0;
				int fileslen = 0;
				out("---------------------------------------##");
				out("该目录下对象个数：" + tempList.length);
				out("文件夹-------------$\n");
				for (int i = 0; i < tempList.length; i++) { // 先添加所有目录
					if (tempList[i].isDirectory()) {
						out("~/ " + tempList[i].getName());
						dirslen ++;
					}
				}
				fileslen = (tempList.length-dirslen);
				out("个数." + dirslen + "  \n文件------------个数."+ fileslen + " $\n");
				sequence = RandomUtil.getSequence(fileslen, 0);
				for (int i = 0; i < tempList.length; i++) { // 再添加所有文件
					if ( tempList[i].isFile()) {

						String filename = tempList[i].getName();
						String lastname = filename.substring(filename .lastIndexOf(".") + 1);//扩展名
						String toname = "";

						// out(filename + " 后缀 "+lastname);
						if (fromtype.equals("")  || fromtype.equals(lastname)) {

							// "文件名组装特殊输入符号#$!!
							// ($x代表原文件名的分割后的数组的序号,name=2-12.png $0->2,$1->12)
							// (#x代表自增长int型,eg:#0 -> 0,1,2,3,x: 该型最多一个)";
							toname += makeName(filename, first, split);
							toname += makeName(filename, second, split);
							toname += makeName(filename, thrid, split);

							totype = totype.equals("")? lastname: totype;
							toname += "." + totype;
							if (tempList[i].exists()) {
								tempList[i].renameTo(new File(todir + "/" + toname));
							}
							out("##" + fromdir + "/" + tempList[i].getName() + "-------->" + todir + "/" + toname);
							fileslen ++;
						}
					}
				}
				out("-----------------------------------------##");

			}
		} catch (Exception ee) {
			out(ee.toString());
			ee.printStackTrace();
		}
	}

	
	private File[] orderBy(File[] tempList, final String order, final boolean orderRe) {
		if(order.equals("no"))return tempList;
		if(order.equals("null"))return tempList;
		
		
		List<File> sortfiles = Arrays.asList(tempList);  
		Collections.sort(sortfiles, new Comparator<File>(){  
		    public int compare(File o1, File o2) {  
		    	int res = 0;
		    	if(order.equals("time")){
	    		   String str1 = String.valueOf(o1.lastModified());  
		           String str2 = String.valueOf(o2.lastModified());  
		    		res = str1.compareTo(str2);
		    	}else 	if(order.equals("name")){
		    		res = o1.getName().compareTo(o2.getName());  
		    	} 
		    	
		    	if(orderRe){
		    		res = 0-res;
		    	}
		           
	           return res;   
		    }  
		});  
		for(int i = 0; i < sortfiles.size(); i++){
			tempList[i] = sortfiles.get(i);
		}
		
		return tempList;
	}

	static int[] sequence;
	private String makeName(String filename, String first, String split) {
		String toname = "";
		if(first.equals(""))return "";//空不处理
		
		if (first.charAt(0) == '#') {
			if(jcbran.isSelected()){
				toname += (sequence[countid] + startid);
			}else{
				toname += (countid + startid);
			}
			countid++;
		} else if (first.charAt(0) == '$') {
			// 去除后缀
			filename = filename.substring(0, filename.length()
					- filename.substring(filename.lastIndexOf(".") + 1)
							.length() - 1);
			int i = Integer.parseInt(first.substring(1, first.length()));
			String ss[] = filename.split(split);
			//out(Constant.getStringByStrings(ss));
			if (i < ss.length) {
				toname += ss[i];
			} else {
				toname += ss[0];
			}
		} else {
			toname += first;
		}
		return toname;
	}

	void out(String s) {
		if (true) {
			System.out.println("PM_file:>> " + s);
			this.jtaout.append("\n" + s);
			this.jtaout.setCaretPosition(this.jtaout.getText().length()); // 锁定最底滚动
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
