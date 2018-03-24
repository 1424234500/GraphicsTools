package main;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.MouseInfo;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import util.Fun;
import util.RobotUtil;
import util.Tools;
import util.thread.ThreadHelp;


public class ColorMain implements AdjustmentListener, TextListener, ActionListener, MouseMotionListener {
	//rgb值
	private int r = max;
	private int g = max;
	private int b = max;
	//最上方显示颜色的容器
	private Panel display; 
	
	private JButton jbone = new JButton("变色");
	private JButton jb2 = new JButton("色板");
	private JButton jb3 = new JButton("变色2");

	//颜色值文本框
	private TextField tfr = new TextField(Constant.jtfSize4);
	private TextField tfg = new TextField(Constant.jtfSize4);
	private TextField tfb = new TextField(Constant.jtfSize4);
	
	//滚动条
	private Scrollbar sbr;
	private Scrollbar sbg ;
	private Scrollbar sbb ;
	
	//初始化
	public void init(){
		Frame f = new Frame("调色板") ;
		f.setLayout(new BorderLayout(5, 5));
		//最上面的颜色显示部分
		display = new Panel();
		display.setBackground(Color.white);
		f.add(display, "Center");
		//下方设置容器
		Panel set = new Panel(new GridLayout(1, 10, 5, 5));
		f.add(set, "South");

		Panel pleft = new Panel(new GridLayout(3, 3, 5, 5));
		jbone.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		  
		pleft.add(jbone);
		pleft.add(jb2);
		pleft.add(jb3);
	 
		set.add(pleft );
		
		Panel ptextshow = new Panel(new GridLayout(3, 1, 5, 5));
		tfr.addTextListener(this);
		tfg.addTextListener(this);
		tfb.addTextListener(this);
		tfr.setText("0");
		tfg.setText("0");
		tfb.setText("0");
		
		tfr.setName("tfr");
		tfg.setName("tfg");
		tfb.setName("tfb");
		ptextshow.add(new Label("R"));
		ptextshow.add(tfr);
		ptextshow.add(new Label("G"));
		ptextshow.add(tfg);
		ptextshow.add(new Label("B"));
		ptextshow.add(tfb);
		set.add(ptextshow );
		//右边滚动条,构造一个新的滚动条，它具有指定的方向、初始值、可见量、最小值和最大值。
		Panel pscroll = new Panel(new GridLayout(3, 6, 5, 5));
		sbr = new Scrollbar(Scrollbar.HORIZONTAL, max, 0, 0, max);
		sbg = new Scrollbar(Scrollbar.HORIZONTAL, max, 0, 0, max);
		sbb = new Scrollbar(Scrollbar.HORIZONTAL, max, 0, 0, max);
		//注册监听器
		sbr.addAdjustmentListener(this);
		sbg.addAdjustmentListener(this);
		sbb.addAdjustmentListener(this);
		sbr.setName("sbr");
		sbg.setName("sbg");
		sbb.setName("sbb");
		pscroll.add(sbr);
		pscroll.add(sbg);
		pscroll.add(sbb);
		set.add(pscroll );
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		f.setVisible(true);
		f.setSize(800, 600);
		f.setLocation(50, 50);
		f.addMouseMotionListener(this);
		thread.start();
	
	}
	
	 
	public ColorMain(){
		this.init();
	}
	public void adjustmentValueChanged(AdjustmentEvent e) {
		// TODO Auto-generated method stub
		//获取发生监听事件的组件的名称
		String name = ((Scrollbar) e.getSource()).getName();
		//获取发送监听事件的组件的值
		int value = e.getValue();
		if(name.equals("sbr")){
			r = value;
			tfr.setText("" + r);
		}else if(name.equals("sbg")){
			g = value;
			tfg.setText("" + g);
		}else{
			b = value;
			tfb.setText("" + b);
		}
		changeColor();
	}

	public void textValueChanged(TextEvent e) {
		// TODO Auto-generated method stub
		String name = ((TextField) e.getSource()).getName();
		int value;
		if(name.equals("tfr")){
			value = Integer.parseInt(tfr.getText());
			r = value;
			sbr.setValue(value);
		}else if(name.equals("tfg")){
			value = Integer.parseInt(tfg.getText());
			g = value;
			sbg.setValue(value);
		}else{
			value = Integer.parseInt(tfb.getText());
			b = value;
			sbb.setValue(value);
		}
		//changeColor();
	}
	public void changeColor(){
		Color c = new Color(r%max, g%max, b%max);
		display.setBackground(c);
	}
	public void changeColor(int r, int g, int b){
		Color c = new Color(r, g, b);
		display.setBackground(c);
		
		if(ffff == 0){
			list.add(new Color(r, g, b));
		}
	}
	List<Color> list = new ArrayList<Color>();
	static int max = 255;
	
	
	
	int ffff = 0;

	boolean flagThread = false;
	public void threadColor(){
		//r,g,b 
		int deta = 1;
		long sleep = 2;
		//max进制 
		//rmax		g0		b0	
		//rmax		gmax	b0
		//r0		gmax	b0
		//r0		gmax	bmax
		//r0		g0		bmax
		//rmax		g0		bmax
		//rmax		g0		b0
		int r = max, g = 0, b = 0;  
		int ff = 0;
		int oldf = -1;
		while(true){
			if(flagThread){
				
			oldf = ff;
			if(r >= max 	&& g == 0 		&& b == 0) ff = 0;  
			if(r >= max	 	&& g >= max 	&& b == 0) ff = 1;  
			if(r == 0 		&& g >= max 	&& b == 0) ff = 2;  
			if(r == 0	 	&& g >= max 	&& b >= max) ff = 3;  
			if(r == 0		&& g == 0 		&& b >= max) ff = 4;  
			if(r >= max 	&& g == 0 		&& b >= max) ff = 5;  

			if(oldf == 5 && ff == 0){
				ffff = 1;
			}
			
			switch(ff%6){
				case -1:
				case 0:
					g+=deta;			
					tfg.setText(""+g);
					break;
				case 1:
					r-=deta;
					tfr.setText(""+r);
					break;
				case 2:
					b+=deta;
					tfb.setText(""+b);
					break;
				case 3:
					g-=deta;
					tfg.setText(""+g);
					break;
				case 4:
					r+=deta;
					tfr.setText(""+r);
					break;
				case 5:
					b-=deta;
					tfb.setText(""+b);
					break; 
			} 
			
			changeColor(r, g, b);

			}
			ThreadHelp.sleep(sleep);
		}
		
		
		
		
		
		
	}
	 Thread thread = ThreadHelp.thread(new Fun<Long>() {
			public void make(Long obj) {
				threadColor();
			}
	});
	 
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == jbone) {
				flagThread = !flagThread;
			} else if (e.getSource() == jb2) { 
				Graphics g = display.getGraphics() ;
				Rectangle rect = display.getBounds();
				
				float dd =  ((float)rect.width / list.size());
				for(int i = 0; i < list.size(); i++){
					g.setColor(list.get(i));
					g.fillRect((int)(i * dd), 0, (int)(i * dd + dd),  rect.height);
					
				}
				
			}else if (e.getSource() == jb3) {
				 
			}
		
		}catch(Exception ee){
			ee.printStackTrace();
		}
		 
	}
	
	
	public Color pickColor() {
		Color pixel = new Color(0,0,0); 
		Robot robot = null; 
		Point mousepoint; 
		int R,G,B; 
		try { 
			robot = new Robot(); 
		} 
		catch (AWTException e) { e.printStackTrace();  } 
		mousepoint = MouseInfo.getPointerInfo().getLocation(); 
		pixel = robot.getPixelColor(mousepoint.x,mousepoint.y); 
		R = pixel.getRed(); 
		G = pixel.getGreen(); 
		B = pixel.getBlue(); 
		return pixel; 
	}

	int x, y;
	public void mouseDragged(MouseEvent e) {
		  x = e.getX();
          y = e.getY();
          System.out.println("x,y:" + x + ", " + y);
          Color c = RobotUtil.getColor();
          String str = Tools.color2string(c);
          System.out.println(""+str);
          RobotUtil.setSysClipboardText(str);
	}


	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
