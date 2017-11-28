package mysql;

import main.Constant;

public class Cmd {
	String colName = "";

	int headNum = 0;
	String[] heads;

	int otherNum = 0;
	String[] others;

	int base = -1;	//-1表示没有序列计算

	Cmd(String s, SqlPanelMain pm) {
		String[] ss = s.split(":");
		if (ss.length < 5) {
			out("eeeeeeeeeeeeeeeeeeeeee");
		}
		colName = ss[0]; // 列名
		headNum = Integer.parseInt(ss[2]);// 一段的 数
		otherNum = Integer.parseInt(ss[4]); // 二段的数
		heads = ss[1].split(","); // 一段的基数
		others = ss[3].split(",");// 二段的基数
		if (ss.length >  5) {
			base = Integer.parseInt(ss[5]); // 序列号基数
		}
		// name:张,李,刘:1:三,四,五,六:1 ++ :1202
		// pm.outPut("colNmae: " + colName + "  n1, n2=" + headNum + ", "
		// + otherNum);
		// for (int i = 0; i < heads.length; i++) {
		// pm.outPut("headRadoms:" + i + ":" + heads[i]);
		// }id:2012,2013,2014,2015,2011:1:01,02,03,04,05:1:1000 	:password:1234:1:5:1:6000		
		// for (int i = 0; i < others.length; i++) {
		// pm.outPut("otherRadoms:" + i + ":" + others[i]);
		// }
		out(colName);
		out(Constant.getStringByStrings(heads) );
		out(Constant.getStringByStrings(others) );
		out("" + base);
		out("get a test: "+getString());
		
	}


	String getString(int offset) {
		String s = getRandomString(headNum, heads) + getRandomString(otherNum, others);	//组装一级和二级序列随机
		if (base >= 0)	//如果有序列基础则加上 后缀序号
			s += "" + (base + offset); // base基数序列递增
		return s;
	}
	
	String getString() {
		String s = getRandomString(headNum, heads) + getRandomString(otherNum, others);	//组装一级和二级序列随机
		if (base >= 0)	//如果有序列基础则加上 后缀序号
			s += "" + base++; // base基数序列递增
		return s;
	}

	String getRandomString(int n, String[] ss) {
		String s = "";
		for (int i = 0; i < n; i++) {
			s += ss[(int) (Math.random() * ss.length)];
		}
		return s;
	}
	
	void out(String s){
		System.out.println("Cmd:>> " + s);
	}
}
