package littleproject;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import littleproject.login.action;

public class start extends JFrame {
	public JButton btn1,btn2;

	public start() {
		super();
		
		btn1 = new JButton("庫存");
		btn2 = new JButton("遊戲");
		
		setLayout(new FlowLayout());
		add(btn1);add(btn2);
		

		setSize(250, 200);
		setVisible(true);
		//無法調整大小
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//按鈕事件
		btn1.addActionListener(new action());
		btn2.addActionListener(new action());


		
		
	}

	public static void main(String[] args) {
		new start();
	}

	
	class action implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new cardGame();
		}
		
	}
}
