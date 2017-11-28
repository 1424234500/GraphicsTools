package map;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.*;

public class MapDesigner extends JFrame
implements ActionListener
{
	int row;
	int col;
	
	MapDesignPanel mdp;
	JScrollPane jsp;
	JButton jbGenerate=new JButton("jbGenerate");
	JButton jbGenerateD=new JButton("jbGenerateD");
	JButton jbGenerateC=new JButton("jbGenerateC");
	 
	JRadioButton jrBlack=new JRadioButton("jrBlack",null,true);
	JRadioButton jrWhite=new JRadioButton("jrWhite",null,false);
	JRadioButton jrCrystal=new JRadioButton("jrCrystal",null,false);
	JRadioButton jrCamera=new JRadioButton("jrCamera",null,false);
	ButtonGroup bg=new ButtonGroup();
	
	Image icrystal; 
	Image iCamera;
	
	JPanel jp=new JPanel();
	JComboBox<Map<String,Object>> comboBox=new JComboBox<Map<String,Object>>();  
	ArrayList<Map<String, Object>> colors = new ArrayList<Map<String, Object>>();
	public MapDesigner(int row,int col)
	{
		this.row=row;
		this.col=col;		
		this.setTitle("地图编辑");
		
		
		for(int i = 0; i < 20; i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("color", getColor(i) ); 
			map.put("i", i);
			colors.add(map);
	        comboBox.addItem(map);  
		}
		
		icrystal=new ImageIcon("img/Diamond.png").getImage();
		iCamera=new ImageIcon("img/camera.png").getImage();
		
		
		mdp=new MapDesignPanel(row,col,this);
		
		JFrame jf = new JFrame();
		jf.add(mdp);
		jf.setBounds(10, 10, 200, 200);
		jf.setVisible(true);
		
		//jsp=new JScrollPane(mdp);
		jsp=new JScrollPane(mdp);
			
		this.add(jsp);
		
		jp.add(jbGenerate);jp.add(jbGenerateD);jp.add(jbGenerateC);
		
		jp.add( comboBox);

		
		jp.add(jrBlack);bg.add(jrBlack);
		jp.add(jrWhite);bg.add(jrWhite);
		jp.add(jrCrystal);bg.add(jrCrystal);
		jp.add(jrCamera);bg.add(jrCamera);	
		this.add(jp,BorderLayout.NORTH);		// 
		jbGenerate.addActionListener(this);
		jbGenerateD.addActionListener(this);
		jbGenerateC.addActionListener(this);
		
		this.setBounds(10,10,800,600);
		this.setVisible(true);
		this.mdp.requestFocus(true);	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public Color getColor(int i){
		//i = 255 * 255 * 255 / 30 * i;//平分 30份颜色,i取到20
		int d = 255 / 30;
		Color res = new Color(Math.abs(i*d +i*20)%255,  Math.abs(i*d+200*i)%255, Math.abs(200-i*d)%255);
		
		return res;
	}
	public void actionPerformed(ActionEvent e)
	{		
	    if(e.getSource()==this.jbGenerate)
	    { 
	    	String s="public static final int[][] MAP=//0  1  jbGenerate \n{";
			for(int i=0;i<mdp.row;i++)
			{
				s=s+"\n\t{";
				for(int j=0;j<mdp.col;j++)
				{
					s=s+mdp.mapData[i][j]+",";
				}
				s=s.substring(0,s.length()-1)+"},";
			}
			s=s.substring(0,s.length()-1)+"\n};";
			
			new CodeFrame(s,"jbGenerate");			
	    }
	    else if(e.getSource()==this.jbGenerateD)
	    {// 
	    	String s="public static final int[][] MAP_OBJECT=// jbGenerateD \n{";
			
			int ccount=0;
			
			for(int i=0;i<mdp.row;i++)
			{
				s=s+"\n\t{";
				for(int j=0;j<mdp.col;j++)
				{
					s=s+mdp.diamondMap[i][j]+",";
					if(mdp.diamondMap[i][j]==1)
					{
						ccount++;
					}
				}
				s=s.substring(0,s.length()-1)+"},";
			}
			s=s.substring(0,s.length()-1)+"\n};";
			s=s+"\npublic static final int OBJECT_COUNT="
			  +ccount+";// OBJECT_COUNT";
			
			
			new CodeFrame(s," jbGenerateD");
	    }
		else if(e.getSource()==this.jbGenerateC)
		{// 
			String s="public static final int CAMERA_COL="
			+this.mdp.cameraCol+";//jbGenerateC\n"
			+"public static final int CAMERA_ROW="+this.mdp.cameraRow+";";			
			new CodeFrame(s,"jbGenerateC");
		}
	}
	
 
}