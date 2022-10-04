package littleproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import littleproject.productTable.MyModel;

public class saleStart extends JFrame{
	public JButton save,logout,close;
	public MyModel myModel;
	public JTable jTable;
	private int rows;
	private ResultSet dataSet;
	JFrame jf = this;

	public saleStart() {
		super("訂單清單");
		initDB();
		
		setLayout(new BorderLayout());
		save = new JButton("另存圖片");
		logout=new JButton("登出");
		close=new JButton("關閉");
		logout.setFont(new Font("新細明體",Font.PLAIN,16));
		close.setFont(new Font("新細明體",Font.PLAIN,16));
		save.setFont(new Font("新細明體",Font.PLAIN,16));

		JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
		top.add(save);top.add(close);top.add(logout);
		add(top, BorderLayout.NORTH);
		
		myModel = new MyModel();
		jTable = new JTable(myModel);
		
		JScrollPane jsp = new JScrollPane(jTable);
		add(jsp, BorderLayout.CENTER);
		jTable.getTableHeader().setOpaque(false);
		jTable.getTableHeader().setBackground(new Color(197,226,226));
		
		setSize(800, 480);
		setVisible(true);
		//無法調整大小
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addEventListener();
		
	}




	//按鈕-監聽事件
	private void addEventListener() {
		//登出按鈕-監聽事件-關閉原視窗/開啟登入窗
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jf.dispose();
				  JOptionPane.showMessageDialog(null, "帳號已登出", "提示", 
		    			  JOptionPane.CLOSED_OPTION);
				new login();
		    	

				
			}
		});
		
		//關閉按鈕-監聽事件-關閉系統
		close.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}
		});
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage img = 
						new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
					Graphics2D g2d = img.createGraphics();
					paint(g2d);
					
					try {
						ImageIO.write(img, "jpeg", new File("C:/Users/user/Downloads/order.jpg"));
						  JOptionPane.showMessageDialog(null, "已儲存圖片", "提示", 
				    			  JOptionPane.CLOSED_OPTION);
					} catch (IOException e1) {
						System.out.println(e1.toString());
					}				
			}
		});		
	}


	private void initDB() {
		Properties prop = new Properties();
		prop.put("user", "root");
		prop.put("password", "root");
		
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/yucu", prop);
			//這邊不關閉，因為後面要取得資料
			String sqlCount = "SELECT count(*) total FROM porder";
			PreparedStatement ps = conn.prepareStatement(sqlCount);
			ResultSet rs = ps.executeQuery();
			rs.next();
			rows = rs.getInt("total");
			
			String sqlQuery = "SELECT * FROM porder";
			PreparedStatement ps2 = conn.prepareStatement(sqlQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			dataSet = ps2.executeQuery();
			
		}catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	public class MyModel extends DefaultTableModel{
		//幾筆
		@Override
		public int getRowCount() {
			return rows;
		}
		
		//幾個欄位
		@Override
		public int getColumnCount() {
			return 5;
		}

		//欄位名稱
		@Override
		public String getColumnName(int column) {
			switch(column) {
			case 0 :return "ID";
			case 1 :return "收件人";
			case 2 :return "商品ID";
			case 3 :return "數量";
			case 4 :return "下單時間";
			default :return "備註";
			}
		}
		
		@Override
		public Object getValueAt(int row, int column) {
			String ret = "";
			try {
				dataSet.absolute(row+1);
				ret = dataSet.getString(column + 1);
			} catch (Exception e) {
				ret = "叫貨";
				System.out.println(e.toString());
			}
			return ret;
		}
	}
	

	public static void main(String[] args) {
		new saleStart();
	}

}
