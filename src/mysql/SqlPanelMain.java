package mysql;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
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

public class SqlPanelMain extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1777279418131350603L;
	Sql mysql ;
	List<Cmd> list = new ArrayList<Cmd>();	//批量插入参数集合
	String columns[];
	
	static int numAllInsert  = 0;		//
	static long beginTime = 0;		//开始插入时间
	static int nowFinishFlag = 0;	//当前线程完成数量
	static int nowThreads = 0;	//当前线程数量
	static int maxThread = 50;	//最大线程数量
	static int numAver = 50;	//平均插入数量
	static int nowNum = 40;		//当前每个线程插入数量

	
	int screenWidth = Constant.screenWidth;
	int screenHeight = Constant.screenHeight;
/////////////////////////////////////////////////////////
	 
	JRadioButton jrbmysql = new JRadioButton("Mysql"); 
	JRadioButton jrboracle = new JRadioButton("Oracle");
	ButtonGroup bgjrs = new ButtonGroup();
	
	
	JPanel jpn = new JPanel();//panel north
	JLabel jlhost = new JLabel("host:pot");
	JTextField jtfhost = new JTextField(Constant.jtfSize8);
	
	JLabel jluser = new JLabel("user:");
	JTextField jtfuser = new JTextField(Constant.jtfSize2);
	JLabel jlpwd = new JLabel("pwd:");
	JTextField jtfpwd = new JTextField(Constant.jtfSize2);
	
	JButton jbconn = new JButton("Conn");
	
	JLabel jldbname = new JLabel("dbName:");
	JTextField jtfdb = new JTextField(Constant.jtfSize2);
	JLabel jltbname = new JLabel("tbName:");
	JTextField jtftb = new JTextField(Constant.jtfSize2);
	
	JButton jbopen = new JButton("ShowTab");
	JButton jbdeldb = new JButton("DelDb");
	JButton jbdeltb = new JButton("DelTab");
	JButton jbdeldate = new JButton("DelDate*");
	
	///////////////////////////////////////
	JPanel jpn2 = new JPanel();//panel north
	JLabel jlcmd = new JLabel("Cmd:");
	JTextField jtfcmd = new JTextField(Constant.jtfSize16);
	JButton jbcmd = new JButton("Carry");
	
	
	JLabel jlthreadnum = new JLabel("Threads:");
	JTextField jtfthreadnum = new JTextField(Constant.jtfSize2);
	JLabel jlnum = new JLabel("num:");
	JTextField jtfnum = new JTextField(Constant.jtfSize2);
	JButton jbsetmax = new JButton("SetNumInsert##");
	JButton jbclear = new JButton("CloTab");

	
	
/////////////////////////////////////////////////////////
	JTable table = new JTable();
	JScrollPane jstable;
	DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

/////////////////////////////////////////////////////////

	
	JPanel jps = new JPanel();	//panel south 

	JScrollPane jstextarea;
	JTextArea jtaout = new JTextArea(12, 86);
	JButton jbcleartext = new JButton("ClearInfo");
//	JButton jbclear = new JButton("ClearTab");
//	JButton jbclear = new JButton("ClearTab");
/////////////////////////////////////////////////////////
	public SqlPanelMain() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭窗体是执行
		this.setTitle("DataBaseManageSystem");
		this.setBounds(Constant.windowX, Constant.windowY, Constant.screenWidth, Constant.screenHeight);
		
////////////////////////
		this.jpn.setLayout(new FlowLayout());
		
		this.jrbmysql.setSelected(true);
		this.bgjrs.add(jrbmysql);
		this.bgjrs.add(jrboracle);
		
		Component cs[] = { jrbmysql,  jrboracle,
				jlhost,jtfhost,jluser,jtfuser,jlpwd,jtfpwd,jbconn,
				jldbname, jtfdb, jltbname, jtftb, jbopen, jbdeldb,jbdeltb,  	
				 };
		for (Component c : cs) {
			this.jpn.add(c);
		}
		
		this.jpn2.setLayout(new FlowLayout());
		Component cs2[]   = {  jlcmd, jtfcmd, jbcmd,  jlthreadnum,jtfthreadnum,  jlnum,jtfnum,  jbsetmax,jbclear,jbdeldate};
		for (Component c : cs2) {
			this.jpn2.add(c);
		}  
		////////////////////////
		this.jstable = new JScrollPane(this.table);
		/////////////////////////////////
		this.jstextarea = new JScrollPane(this.jtaout);
		jstextarea.setBounds(Constant.spX, Constant.spY, Constant.spW  , Constant.spH);
		jps.add(this.jstextarea); 
		jps.add(this.jbcleartext);
		
		jpn.setBounds(Constant.npX, Constant.npY, Constant.npW, Constant.npH);
		jpn2.setBounds(Constant.npX2, Constant.npY2, Constant.npW2, Constant.npH2);
		jstable.setBounds(Constant.ccX, Constant.ccY, Constant.ccW, Constant.ccH);
		jps.setBounds(Constant.spX, Constant.spY, Constant.spW, Constant.spH);
	
		this.setLayout(null);
		this.add(jpn );
		this.add(jpn2 );
		
		this.add(jstable );
		this.add(jps );

		this.setBackground(Constant.colorBackground);

		this.setVisible(true);

		
		this.jtfhost.setText("localhost:3306");
		this.jtfuser.setText("root");
		this.jtfpwd.setText("");
		this.jtfdb.setText("mydb");
		this.jtftb.setText("stu");
		this.jtfthreadnum.setText(""+ maxThread);
		this.jtfcmd.setText("id:0,0,0:0::0:10000");
		jtaout.setText(Constant.help);
		
		this.jbconn.addActionListener(this);
		this.jbopen.addActionListener(this);
		this.jbdeldb.addActionListener(this);
		this.jbdeltb.addActionListener(this);
		this.jbcmd.addActionListener(this);
		this.jbsetmax .addActionListener(this);
		this.jbclear .addActionListener(this);
		this.jbcleartext .addActionListener(this);
		this.jbdeldate .addActionListener(this);
		
		this.jrbmysql.addActionListener(this);
		this.jrboracle.addActionListener(this);
			
	}

	public void actionPerformed(ActionEvent e) {
		this.mysql = GetASql();

		if (e.getSource() == jbopen) {
			clearTable();
			this.mysql.outPutAllDataToTable();
		}else if(e.getSource() == jbconn){
			out(this.mysql.testConn());
		}else if(e.getSource() == jbcmd){//sql:select * from stu; 
			String str = jtfcmd.getText();
			//for if oracle max be upchar and no ';'in the end
			if( jrboracle.isSelected()){
				str = str.toUpperCase(); 
				 str.replace(';', ' ');
			} 

			String[] strs = str.split(":");//strs[0]=sql        :     strs[1]= select * from stu; 
			int ff = 1;
			if(strs.length >= 2)
			if(strs[0].equals("sql") || strs[0].equals("SQL")){	//cmd执行数据库指令//sql
				String strss[] = strs[1].split(" ");// select      *         from              stu; 
				if(strss[0].equals("select") || strss[0].equals("SELECT")){//查询指令
					this.clearTable();
					ff = mysql.sqlQueryExecute(strs[1]) ;
				}else{	//非查询指令
					ff = mysql.sqlExecute(strs[1]);
					 this.jbopen.doClick();
				}
				
			}else if(strs.length >= 5){	//cmd表项 添加数值
				list.add(new Cmd(str, this));
				ff = 0;
			}
			if(ff == 0){
				out("Run:>> " + str);
			}else{
				out("Exception:>> " + str);
				out(Constant.help);
			} 
		}else if(e.getSource() == jbsetmax){
			 SetMaxInsert();
			
		}
		else if(e.getSource() == jbdeldb){
			if(ifok("确定要删除 数据库:" + mysql.DBName + "吗########" )){
				out(mysql.delDB());
			}
		}else if(e.getSource() == jbdeltb){
			if(ifok("确定要删除 表:" + mysql.TName + "吗########" )){
				out(mysql.delTB());
			}
		}	
		else if(e.getSource() == jbclear){
			this.clearTable();
		}	else if(e.getSource() == jbcleartext){
			this.jtaout.setText("");
		}else if(e.getSource() == jbdeldate){
			if(ifok("确定要删除 表:" + mysql.TName + " 的所有数据吗########" )){
				this.jtfcmd.setText("sql:delete from " + this.jtftb.getText());
				this.jbcmd.doClick();
			}
			
		}else if(e.getSource() == jrbmysql){
			this.jtfhost.setText("localhost:3306");
			this.jtfuser.setText("root");
			this.jtfpwd.setText("");
			this.jtfdb.setText("mydb");
			this.jtftb.setText("tab");
		}else if(e.getSource() == jrboracle){
			this.jtfhost.setText("localhost:1521:XE");
			this.jtfuser.setText("scott");
			this.jtfpwd.setText("tiger");
			this.jtfdb.setText("");
			this.jtftb.setText("dept");
		}
		
	}
	 void SetMaxInsert() {
		 //计算当前需要开启多少个线程 并为每一个分配插入量及其区间   单独开启并插入剩余部分，其他批量线程插入
		 try{
			 numAllInsert = Integer.parseInt(this.jtfnum.getText());
			 maxThread = Integer.parseInt(this.jtfthreadnum.getText());
		 }catch(Exception e){
			 numAllInsert = 0;
			 maxThread = 10;
			 out("error!! get the num to insert");
		 }
//			static int nowThreads = 1;	//当前线程数量
//			static int maxThread = 50;	//最大线程数量
//			static int numAver = 60;	//平均插入数量
//			static int nowNum = 60;		//当前每个线程插入数量
		 
		 int leftNum = 0;
		 
		 if(numAllInsert <= maxThread * numAver){//只开部分线程，每个插入numAver,感觉没必要开太多线程，数量少时
			 nowThreads = numAllInsert / numAver;
			 leftNum = numAllInsert % numAver;
			 nowNum = numAver;
		 }else{//线程全开，每个超载量插入
			 nowThreads = maxThread;
			 nowNum = numAllInsert / maxThread;
			 leftNum = numAllInsert % (maxThread * nowNum);
		 }  
		Date ddd = new Date();
		beginTime =  ddd.getTime() ;
		out("@@@@@@@@@@@Begin:>> AllNums:" + numAllInsert + ". " + "ThreadsNum:" + nowThreads + " X " + nowNum + " left:" + leftNum + ". At " + ddd.toLocaleString())  ;

		int begin = 0;
		int num = 0;
		int nowI = 0;
		for(nowI = 0; nowI < nowThreads; nowI++) {
			begin = nowI * nowNum;
			num = nowNum;
			new MyThread(nowI,begin,num).start();
		}
		//插入剩余的余数数据
		begin = nowNum * nowThreads;
		nowThreads++;	//为了避免先前冲突 而这里又想要用 线程开启
		new MyThread(nowI,begin,leftNum).start();
		 
	}
	 
///////////////////////////////////////////////////////
class MyThread extends Thread {
	//批量线程插入类
	int id, begin, num;//id标识， 起始，数量

	MyThread(int idd, int begin, int num){
		this.id = idd; this.begin = begin;this.num = num;
	} 
	public void run() {
		Date dd = new Date();
		long old = dd.getTime();
		
		//Test();
		Insert();
		
		Date ddd = new Date();
		long oldd = ddd.getTime() ;
		out("###########Thread:"+id+ " >> Time:" + (oldd-old)/1000 + "S." + "Num:" + num + ". ")  ;
		nowFinishFlag ++;
		if(nowFinishFlag >= nowThreads){
			//所有线程完毕 信号处理
			out("@@@@@@@@@@@Carry All Over>> Time:" + (oldd-beginTime)/1000 + "S." + "Num:" + numAllInsert + ". At " + ddd.toLocaleString())  ;
			SqlPanelMain.this.jbopen.doClick();
			nowFinishFlag = 0;
		}
	} 
	public void Insert(){
		Sql mysql = GetASql();
		
		String cols[] = mysql.getColumns(); 
		
		mysql.BeginMuchInsert(cols);
		
		for(int i = 0; i < num; i++){	//插入最大数量，i并 为 偏移值
			String val[] = new String[cols.length];
			for(int j = 0; j < cols.length; j++){
				val[j] = ""; 
				int k = getListId (cols[j]);	
				if(k >= 0){//查看链表是否存在该列的基数集	存在则取出随机值否则默认值null
					val[j] = list.get(k).getString(begin + i);
				}
			}
		 	out("Thread:"+ id+" >>Insert:"+Constant.getStringByStrings(val));
			mysql.MuchInsert(val);
		 }
		
		mysql.EndMuchInsert();
		
	
		
	}
	public void Test(){
		Sql mysql = GetASql();
		 
		
		String cols[] = mysql.getColumns(); 
		
		for(int i = 0; i < nowNum; i++){
			String val[] = new String[cols.length];
			for(int j = 0; j < cols.length; j++){
				val[j] = "null"; 
				int k = getListId (cols[j]);	
				if(k >= 0){//查看链表是否存在该列的基数集	存在则取出随机值否则默认值null
					val[j] = list.get(k).getString(id*nowNum + i);
				}
			}
			out("Thread:"+ id+" >>"+Constant.getStringByStrings(val));
		} 
		 
	}
}

 /////////////////////////////////////
	 int getListId(String str){
		 int i = -1;
		 for(int j = 0; j < list.size(); j++){
			 if(list.get(j).colName.equals(str)){	//不跳出 以便于后面添加的指令cmd为优先
				 i = j;
			 }
		 }
		 return i; 
	 }
	 Sql GetASql(){
		return new Sql(jrbmysql.isSelected()?0:1,jtfhost.getText(),jtfuser.getText(),jtfpwd.getText(),this.jtfdb.getText(), this.jtftb.getText(), this);
	 }
	void clearTable(){
		this.tableModel.setRowCount(0);
		this.tableModel.setColumnCount(0);
		this.columns = mysql.getColumns();
		for (String s : columns) {
			this.tableModel.addColumn(s);
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

	public void addTableLine(String[] ss) {
		this.tableModel.addRow(ss);
	}

}



