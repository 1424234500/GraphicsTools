package main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import filehelper.FilePanelMain;
import filehelper.FileProjectMain;
import map.MapColRowDialog;
import mysql.SqlPanelMain;

public class PanelMain extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1777279418131350603L;

	int screenWidth = Constant.screenWidth;
	int screenHeight = Constant.screenHeight;
	// ///////////////////////////////////////////////////////
 
	JButton jbsql = new JButton("数据库管理"); // 点击后自动显示文件s
	JButton jbfile = new JButton("文件名批处理");
	JButton jbfileproject = new JButton("补丁制作");
	JButton jbmap = new JButton("地图生成");
	
	JButton jbencode = new JButton("编码及Excel");
	JButton jbcolor = new JButton("调色器");
	
	JButton jbe = new JButton("aaa");
	JButton jbe1 = new JButton("bbbbb");
	JButton jbe2 = new JButton("cccccccc");
	 
	// ///////////////////////////////////////////////////////
	PanelMain() {
		this.setTitle("HelpSystem");
		this.setBounds(500, 100, 400, 400);
		this.setLayout(new FlowLayout());
		this.add(jbsql);
		this.add(jbfile);
		this.add(jbfileproject);
		
		this.add(jbcolor);
		this.add(jbencode);
		this.add(jbmap);
		 
		jbfile.addActionListener(this);
		jbfileproject.addActionListener(this);
		jbsql.addActionListener(this);
		jbcolor.addActionListener(this);
		jbmap.addActionListener(this);
		jbencode.addActionListener(this);
		jbmap.addActionListener(this);
 
		this.setBackground(Constant.colorBackground);

		this.setVisible(true);
 
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == jbfile) {
			 new FilePanelMain();
		} else if (e.getSource() == jbsql) {
			 new SqlPanelMain();
		} else if(e.getSource() == jbmap){
			new MapColRowDialog();
		}else if(e.getSource() == jbencode){
			new EncodeMain();
		}else if(e.getSource() == jbmap){
			new MapColRowDialog();
		}else if(e.getSource() == jbcolor){
			new ColorMain();
		}else if(e.getSource() == jbfileproject){
			new FileProjectMain();
		}else if(e.getSource() == jbmap){
			new MapColRowDialog();
		}else if(e.getSource() == jbmap){
			new MapColRowDialog();
		}else if(e.getSource() == jbmap){
			new MapColRowDialog();
		}else if(e.getSource() == jbmap){
			new MapColRowDialog();
		}else if(e.getSource() == jbmap){
			new MapColRowDialog();
		}
		
		//this.dispose();

	}
 

}
