package main;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Constant {
	public static String detaString = "<->"; 
	public static String help = "cmd>> name:x,y,z:1:a,b,c:1<:1000> / sql:select * from stu; \n "; 
	public static Color colorBackground = Color.WHITE;
	public static Color colorLine = Color.lightGray; 

	public static int tableOnAddRowHeight = 26;
	public static int tableOnShowRowHeight = 24;
	
 	public static int windowX = 80;
	public static int windowY = 50;
 	public static int screenWidth = 1100;
	public static int screenHeight = 666;
	 
//北部位置
	public static int npX = screenWidth *  1 / 40;
	public static int npY = screenHeight * 0 / 40;		//0
	public static int npW = screenWidth * 38 / 40;
	public static int npH = screenHeight * 2 / 40;		//2
//北部位置22222
		public static int npX2 = screenWidth *  1 / 40;
		public static int npY2 = screenHeight * 2 / 40;		//2
		public static int npW2 = screenWidth * 38 / 40;
		public static int npH2 = screenHeight * 2 / 40;		//4
//中心位置
	public static int ccX = screenWidth  * 1 / 40;		//
	public static int ccY = screenHeight * 5 / 40 ;		//6
	public static int ccW = screenWidth *  38 / 40 ;
	public static int ccH = screenHeight * 20 / 40 ;	//25

//南部位置
	public static int spX = screenWidth  * 1  / 40;
	public static int spY = screenHeight * 25 / 40;		//25
	public static int spW = screenWidth * 38  / 40;
	public static int spH = screenHeight * 15 / 40;		//40
 
	
	//西部位置
		public static int wwX = screenWidth * 0 / 40;
		public static int wwY = screenHeight* 6 / 40 ;
		public static int wwW = screenWidth * 7 / 40;
		public static int wwH = screenHeight * 30 / 40;
	
	//输入框的长度控制
	public static int jtfSize1 = 3;
	public static int jtfSize2 = 4;
	public static int jtfSize4 = 8;
	public static int jtfSize8 = 10;
	public static int jtfSize16 = 20;
	public static int jtfSize32 = 40;
	
	
	
	public static String[] getdataByResultSet(ResultSet rs) {
		try {
			String str = "";
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				str = str + rs.getString(rsmd.getColumnLabel(i)) + Constant.detaString;
			}
			return str.split(Constant.detaString);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getStringByStrings(String[] ss){
		String res = "[ ";
		int i = 0;
		for(i = 0; i < ss.length - 1; i++){
			res += ss[i] + " - ";
		}
		res += ss[i];
		res += " ]";
				return res;
	}
	

}
