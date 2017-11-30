package map;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MapColRowDialog extends JFrame implements ActionListener
{
	JLabel jlRow=new JLabel("行");
	JLabel jlCol=new JLabel("列");
	JTextField jtfRow=new JTextField("20");
	JTextField jtfCol=new JTextField("20");
	
	JButton jbOk=new JButton("确认");
	
	public MapColRowDialog()
	{
		this.setTitle("配置");
		
		this.setLayout(null);
		jlRow.setBounds(10,5,60,20);
		this.add(jlRow);
		jtfRow.setBounds(70,5,100,20);
		this.add(jtfRow);
		
		jlCol.setBounds(10,30,60,20);
		this.add(jlCol);
		jtfCol.setBounds(70,30,100,20);
		this.add(jtfCol);
		
		jbOk.setBounds(180,5,60,20);
		this.add(jbOk);
		jbOk.addActionListener(this);
		
		this.setBounds(440,320,300,100);
		this.setVisible(true);		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		int row=Integer.parseInt(jtfRow.getText().trim());
		int col=Integer.parseInt(jtfCol.getText().trim());
		
		new MapDesigner(row,col);
		this.dispose();
		
	}
 
}