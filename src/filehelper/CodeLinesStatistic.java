package filehelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import util.Tools;

public class CodeLinesStatistic {

	private long sums = 0;// 总共行数
	public long arrs[]; // 字符统计
	private String suffixs[];

	private String target;

	private StringBuffer statistics = new StringBuffer();

	public void out(String str) {
		pm.out(str);
	}

	FilePanelMain pm;

	// 多参数 处理，第一个 为目标文件夹，之后的都为 待计数的 文件类型
	public CodeLinesStatistic(String fromdir, String[] args, FilePanelMain pm) {
		this.pm = pm;
		// 这里模拟命令行下的参数进行测试
		// "F:/Dev/alisoft_space/ExtSysDataImportTask", // 这里是项目的根目录
		// "java"
		// "xml",
		// "properties"........... }; // 这里是统计文件的后缀名
		this.arrs = new long[128];
		for (int i = 0; i < arrs.length; i++) {
			arrs[i] = 0;
		}
		long startTimes = System.currentTimeMillis();

		this.suffixs = args;
		this.target = fromdir;

		File targetFile = new File(target);
		out("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		if (targetFile.exists()) {

			try {
				statistic(targetFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				out(e.toString());
				e.printStackTrace();
			}
			out("@@Complete!. total [" + sums + "] lines code. ");

		} else {
			out("@@File or Dir not exist : " + target);
		}
		if(pm.jcbchar.isSelected()){
			out("@@字符统计结果%%%%%%%%%%%%%");
			String rr = "";
			for (int i = 0; i < arrs.length; i++) {
				if (arrs[i] != 0) {
					if ((char) i == '\t')
						rr += (Tools.fillStringBy("'/t'", " ", 9, 0));
					else if ((char) i == '\n')
						rr += (Tools.fillStringBy("'/n'", " ", 9, 0));
					else
						rr += (Tools.fillStringBy("'" + (char) i + "'", " ", 9, 0));
				}
			}
			out(rr);
			rr = "";
			for (int i = 0; i < arrs.length; i++) {
				if (arrs[i] != 0)
					rr += (Tools.fillStringBy(arrs[i] + "", " ", 9, 0));
			}
			out(rr);
		}
		
		out("@@Cost times "
				+ Tools.getStringByTime(System.currentTimeMillis() - startTimes));
		out("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

	}

	/**
	 * 
	 * 深度优先,统计文件行数
	 * 
	 * 
	 * 
	 * @param file
	 * 
	 * @throws IOException
	 */

	private void statistic(File file) throws IOException {

		if (file.isDirectory()) {

			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {

				statistic(files[i]);

			}

		}

		if (file.isFile()) {

			if (isMatchSuffixs(file)) {

				sums += countFileTextLines(file);

			}

		}

	}

	/**
	 * 
	 * 检查文件是否是制定后缀的文件
	 * 
	 * 
	 * 
	 * @param file
	 * 
	 * @return
	 */

	private boolean isMatchSuffixs(File file) {

		String fileName = file.getName();

		if (fileName.indexOf(".") != -1) {

			String extName = fileName.substring(fileName.indexOf(".") + 1);

			for (int i = 0; i < suffixs.length; i++) {

				if (suffixs[i].equals(extName)) {

					return true;

				}

			}

		}

		return false;

	}

	/**
	 * 
	 * 统计文件行数
	 * 
	 * 
	 * 
	 * @param file
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */

	private long countFileTextLines(File file) throws IOException {

		long result = 0;

		statistics = new StringBuffer();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str;
		int i = 0, len = 0, kk = 0;
		while ((str = br.readLine()) != null) {
			result++;
			if(pm.jcbchar.isSelected()){

				// 统计字符使用频率
				len = str.length();
				// out(str);
				for (i = 0; i < len; i++) {
					kk = (int) (str.charAt(i));
					if (kk < arrs.length)
						arrs[kk]++;
					// out((int)(str.charAt(i))+"=" + (str.charAt(i))+"" +
					// (char)(int)(str.charAt(i)));
				}
			}
		}
		br.close();

		statistics.append("#" + Tools.fillStringBy("" + result, " ", 7, 0))
				.append(" << ");
		statistics.append(" ").append(file.getAbsolutePath());

		out(statistics.toString());

		return result;

	}

}