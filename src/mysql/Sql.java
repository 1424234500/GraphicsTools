package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import main.Constant;

public class Sql {
	private static final String DetaString = "<->";
	
	static boolean ifFirstTimeOpenDataBase = true;

	String DBName = "mydb";
	String TName = "tab";
	String IpPort = "localhost:3306";
	String User = "root";
	String Pwd = "";
	
	String sqlCreateDb;
	String sqlCreateTb;
	String sqlInsert;
	String sqlQueryAll;

	PreparedStatement staticPs ;
	Statement staticS ;
	Connection staticConn ;
	
	int nowSql = 0;	//0:mysql, 1:oracle
	
	SqlPanelMain pm;
	
	public	Sql(int selectSqls, String host, String user, String pwd, String dbname, String tname, SqlPanelMain ppm) {
		this(dbname, tname, ppm);
		nowSql = selectSqls;
		IpPort	= host;
		User = user;
		Pwd = pwd;
	}
	public	Sql(String DBName, String TName, SqlPanelMain pm) {
		this.DBName = DBName;
		this.TName = TName;
		this.pm = pm;
		
		init();
	}
	public Sql(){}
	public void	initSet(int selectSqls, String host, String user, String pwd, String dbname, String tname ) {
		initSet(dbname, tname );
		nowSql = selectSqls;
		IpPort	= host;
		User = user;
		Pwd = pwd;
	}
	public void	initSet(String DBName, String TName ) {
		this.DBName = DBName;
		this.TName = TName;
		init();
	}
	public void initSet(){}



	public String testConn(){
		String str = "connect success !!";
		try{
			Connection conn = getConn();
			if(conn == null ){
				str = "connect error ,get none conn";
			}
			conn.close();
		}catch(Exception e){
			str = "connect exception ,"+ e.toString();
		}
		return str;
	}
	
	public void init(){
		sqlInsert = "insert into "
				+ TName
				+ " (name, singer, ablum, timeLength,  timeIn, timeChange, type, path, id, truePath) values(?,?,?,?,?,?,?,?,?,?)";
		sqlQueryAll = "select * from " + TName + "";
		sqlCreateDb = "create database if not exists " + this.DBName
				+ "  default charset=utf8 ";
		// useUnicode=true&characterEncoding=utf8
		sqlCreateTb = "create table if not exists "
				+ this.TName
				+ " (name VARCHAR(64), singer VARCHAR(20), "
				+ "ablum VARCHAR(20), timeLength VARCHAR(20), "
				+ "timeIn VARCHAR(20), timeChange VARCHAR(20), "
				+ "type VARCHAR(20) ,  path VARCHAR(20)   ,  id VARCHAR(10) ,  truePath VARCHAR(128)    )";
	} 

	public Connection getConn() {
		Connection conn = null; 
		if(nowSql == 0){
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				out("find mysql class error ! "+e.toString());
				e.printStackTrace();
			}
			String url = "jdbc:mysql://"+IpPort+"/" + this.DBName;
			try {
				conn = DriverManager.getConnection(url + "?useUnicode=true&characterEncoding=utf8", User, Pwd);
			} catch (SQLException e) { 
				out("GetConn mysql error " + e.toString()); 
			}
		}else{
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				out("find oracle class error ! "+e.toString());
				e.printStackTrace();
 			}//jdbc:oracle:thin:@192.168.3.9:1521:orcl";
			// "jdbc:oracle:" + "thin:@127.0.0.1:1521:XE";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String url = "jdbc:oracle:thin:@"+IpPort+"";
			try { 
				conn = DriverManager.getConnection(url, User, Pwd);
			} catch (SQLException e) { 
				out("GetConn oracle error " + e.toString()); 
			}
		}
		return conn;
	}
	
	public void BeginMuchInsert(String strs[]){//通常由Column.mysql.get来初始化 begin
		try {
				staticConn = getConn();
				sqlInsert = "insert into " + TName + "  values ( ";
				for (int i = 0; i < strs.length - 1; i++) {	//由于Column获取列号多了一个列？？？？？？？？？？？
					sqlInsert = sqlInsert + "?, ";
				}
				sqlInsert = sqlInsert + "?)";
		//		out("Begin much insert>> "+sqlInsert + "#######################");
				staticPs = staticConn.prepareStatement(sqlInsert);

				
			} catch (SQLException e) {
				out("beginMuchInsert error" + e.toString());
			}

	}
	public void MuchInsert(String strs[]){
		try{ 
			for (int i = 0; i < strs.length; i++) {
				staticPs.setString(i + 1, strs[i]);
			}
			staticPs.executeUpdate(); 
		} catch ( Exception e) {
		 	out("muchinsert error! " +Constant.getStringByStrings(strs));
			e.printStackTrace();
		}
	}
	public void EndMuchInsert(){
		try{
		staticPs.close();
		staticConn.close();
	//	out("EndMuchInsert############################################ ");

	} catch ( Exception e) {
		out("EndMuchInsert error " + e.toString());
	}
	}
	
 

	public int insert(String strs[]) {
		
		try { 
			Connection conn = getConn();
			sqlInsert = "insert into " + TName + "  values ( ";
			for (int i = 0; i < strs.length - 1; i++) {
				sqlInsert = sqlInsert + "?, ";
			}
			sqlInsert = sqlInsert + "?)";
			out(sqlInsert);
			PreparedStatement ps = conn.prepareStatement(sqlInsert);

			for (int i = 0; i < strs.length; i++) {
				ps.setString(i + 1, strs[i]);
			}
			ps.executeUpdate(); 
 
			ps.close();
			conn.close();
			return 0;
		} catch (SQLException e) {
			out(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	public int update(String strs[]) {
		Connection conn = getConn();
		int i = 0;
		String sqlUpdate = null;
		// sqlUpdate = "update " + TName + " set name= '" + data.name  + "' where id= '" + data.id + "'";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sqlUpdate);
			out(" change ");  
			i = pstmt.executeUpdate();

			pstmt.close();
			conn.close();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public void outPutAllDataToTable() {
		Connection conn = getConn();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + this.TName);
			out(" outPutAllDate to table ");  
			while (rs.next()) {
				pm.addTableLine(Constant.getdataByResultSet(rs));
			}
			stmt.close();
			conn.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
			out(e.toString());
		}
	}
 

	public int sqlExecute(String sql) {
		try {
			Connection conn = getConn();
			Statement s = conn.createStatement();
			s.execute(sql);
			out("sqlExecute Succuss !! "+ sql );
			s.close();
			conn.close();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			out("sqlExecute error !! "+ sql + " -- "+e.toString());
			pm.out("sqlExecute error !! "+ sql + " -- "+e.toString());
			
			return 1;
		}
	}

	public int sqlQueryExecute(String sql) {
		try {
			Connection conn = getConn();
			Statement s = conn.createStatement();
			
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				pm.addTableLine(Constant.getdataByResultSet(rs));
				//out(Constant.getStringByStrings(Constant.getdataByResultSet(rs)));
			}
			s.close();
			conn.close();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			pm.out("sqlQueryExecute error! "+sql+" -- "+e.toString());
			out("sqlQueryExecute error! "+sql+" -- "+e.toString());
			return 1;
		}
	}

	public String delDB() {
		String str = "delete db " + DBName+ "success !";
		try {
			Connection conn = getConn();
			Statement s = conn.createStatement();
			s .execute("drop database  " + this.DBName);
			s.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			str = "delete db " + DBName+ " error  !";
			out(e.toString());
			pm.out(e.toString());
		}
		return str;
	}

	public String delTB() {
		String str =  "delete "+ TName + " success ! ";
		try { 
				Connection conn = getConn();
				Statement s = conn.createStatement();
			s.execute("drop table  " + this.TName);
			s.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			pm.out(e.toString());
			out(e.toString());
			str = "delete "+ TName + " error ! ";
		}
		return str;
	}

	public int deleteBy(String name, String value) { 
		String sql  = "";
		try {
			Connection conn = getConn();
			Statement stmt;
			sql = "delete from " + TName + " where  "+name+"='" + value + "'"; 
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
			conn.close();
			out("deleteby succuss! " + sql);
			return 0;
		} catch (SQLException e) {
			out("deleteby error! "+sql + " "+ e.toString());
			pm.out("deleteby error! "+sql + " "+ e.toString());
			return 1;
		}
	} 
	static void out(String s) {
		if (true) {
			System.out.println("Sql:>> " + s);
		}
	}


	public String getAId() { 
		Connection conn = getConn();
		int i = 0;
		boolean flag = true;
		try {
			Statement s = conn.createStatement();

			ResultSet rs = s.executeQuery(sqlQueryAll);
			if (rs.next()) 
				while (true) {
					rs.first(); 
					flag = true;
					do { 
						if (rs.getString("id").equals("" + i)) { 
							flag = false;
							break;
						}
					} while (rs.next());
					if (flag) { 
						out("id:" + i);
						return "" + i;  
					}
					i++;
				}
			s.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			out(e.toString());
		}
		return "" + i;
	}

	public String[] getColumns() {
		
		String str = "";
		try {
			Connection conn = getConn();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from " + this.TName);

			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				str = str + rsmd.getColumnLabel(i) + Constant.detaString;
			}
			s.close();
			conn.close();
			out("getColumns success! " + str);
		} catch (Exception e) {
			pm.out("getColumns error! " + str + " "+e.toString());
			e.printStackTrace();
		}
		return str.split(Constant.detaString);
	}

	
	public String[] getADataBy(String name, String value){
		try {
			this.BeginStateAndConn();
			ResultSet rs = this.getResultSetBySql(
					"select * from " + this.TName + " where " + name + " = " + value);
			String[] strs = this.getdataByResultSet(rs);
			this.EndStateAndConn();
			
			return strs;
		} catch ( Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	//查询语句
	public ResultSet getResultSetBySql(String sql) {
		try {  
			ResultSet rs = staticS.executeQuery(sql);	//#######如果在state和conn关闭后将不能在使用rs##########
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void BeginStateAndConn() {
		try {
			staticConn = getConn();
			staticS = staticConn.createStatement();  
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	public void EndStateAndConn() {
			try {
				staticConn.close();;
				staticS .close();;  
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	
	public String[] getdataByResultSet(ResultSet rs) {
		try {
			String str = "";
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				str = str + rs.getString(rsmd.getColumnLabel(i)) + this.DetaString;
			}
			return str.split(this.DetaString);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet getResultAll() {
		try {
			ResultSet rs = staticS.executeQuery( "select * from " + this.TName);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
