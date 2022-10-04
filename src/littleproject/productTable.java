package littleproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;




public class productTable extends JFrame {
	public static final String sqlAppendAccount = null;
	JFrame jf = this;
	static JDialog jd ;
	public JTable jTable;
	public JButton save,logout,close;
	public int rows;
	public ResultSet dataSet;
	public MyModel myModel;
	public static int a;
	public String ql;
	public JLabel mname;
	public JLabel qchi = new JLabel("叫貨數量：");
	public static JTextField qLog;
	static PreparedStatement checkStatement, appendStatement;
	public static String aa;
	static final String sqlAppendAccount1 = "INSERT INTO porder (account,proid,quantity) VALUES (?,?,?)";


	
	public productTable() {
		super("庫存清單");
		initDB();
		
		setLayout(new BorderLayout());
		
		save = new JButton("另存圖片");
		logout=new JButton("登出");
		close=new JButton("關閉");
		qLog = new JTextField(17);
		ql = qLog.getText();
		qchi.setFont(new Font("新細明體",Font.PLAIN,16));
		logout.setFont(new Font("新細明體",Font.PLAIN,16));
		close.setFont(new Font("新細明體",Font.PLAIN,16));
		save.setFont(new Font("新細明體",Font.PLAIN,16));

		JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
		top.add(qchi) ;top.add(qLog);top.add(save);top.add(close);top.add(logout);
		add(top, BorderLayout.NORTH);
		
		myModel = new MyModel();
		jTable = new JTable(myModel);
		
		JScrollPane jsp = new JScrollPane(jTable);
		add(jsp, BorderLayout.CENTER);
		
		jTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
		jTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JTextField()));
		jTable.getTableHeader().setOpaque(false);
		jTable.getTableHeader().setBackground(new Color(255,219,184));
        
   		setSize(800, 480);
		setVisible(true);
		//無法調整大小
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addEventListener();
		

	}
	
	//按鈕-監聽事件
	public void addEventListener() {
		
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
						ImageIO.write(img, "jpeg", new File("C:/Users/user/Downloads/products.jpg"));
						  JOptionPane.showMessageDialog(null, "已儲存圖片", "提示", 
				    			  JOptionPane.CLOSED_OPTION);
					} catch (IOException e1) {
						System.out.println(e1.toString());
					}				
			}
		});
	
		
	}
	
	
	public void initDB() {
		Properties prop = new Properties();
		prop.put("user", "root");
		prop.put("password", "root");
		
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/yucu", prop);
			//這邊不關閉，因為後面要取得資料
			String sqlCount = "SELECT count(*) total FROM product";
			PreparedStatement ps = conn.prepareStatement(sqlCount);
			ResultSet rs = ps.executeQuery();
			rs.next();
			rows = rs.getInt("total");
			
			String sqlQuery = "SELECT * FROM product";
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
			return 6;
		}

		//欄位名稱
		@Override
		public String getColumnName(int column) {
			switch(column) {
			case 0 :return "ID";
			case 1 :return "品牌";
			case 2 :return "品名";
			case 3 :return "口味";
			case 4 :return "單價";
			case 5 :return "動作";
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
	
	// CLASSES BELOW ARE ME OVERRIDING AN INDIA BOY'S CODE AND GOT NO IDEA OF WHAT HE IS WRITING 
	// BUTTON RENDERER CLASS
	class ButtonRenderer extends JButton implements  TableCellRenderer
	{

	  //CONSTRUCTOR
	  public ButtonRenderer() {
	    //SET BUTTON PROPERTIES
	    setOpaque(true);
	  }
	  @Override
	  public Component getTableCellRendererComponent(JTable table, Object obj,
	      boolean selected, boolean focused, int row, int col) {

	    //SET PASSED OBJECT AS BUTTON TEXT
	      setText((obj==null) ? "":obj.toString());

	    return this;
	  }

	}

	//BUTTON EDITOR CLASS 
	class ButtonEditor extends DefaultCellEditor
	{
	  protected JButton btn;
	   private String lbl;
	   private Boolean clicked;

	   public ButtonEditor(JTextField txt) {
	    super(txt);

	    btn=new JButton();
	    btn.setOpaque(true);

	    //WHEN BUTTON IS CLICKED
	    btn.addActionListener(new ActionListener() {
	      private char[] ql;

		@Override
	      public void actionPerformed(ActionEvent e) {
	    	  JOptionPane.showMessageDialog(null, "已完成叫貨", "已完成叫貨", 
	    			  JOptionPane.CLOSED_OPTION);
	    	// 尋找這個button是在第幾row, 並取第0欄傳送friendID製作chatroom
	    	a = jTable.getSelectedRow();
	    	System.out.println(a+1);
	    	String aa = Integer.toString(a+1);
	    	//new ChatRoom(userId, userName, friendId);
	    	System.out.println(qLog.getText());
	    	System.out.println(aa);
	    	System.out.println(login.ac);
	    	orderIn(login.ac , aa , qLog.getText());
	      }

		//新增資料-事件
		public void orderIn(String account, String proid, String quantity) {
			
			String account1 = login.ac;
			String proid1 = Integer.toString(a+1);
			String quantity1 = productTable.qLog.getText();
		
			Properties prop = new Properties();
			prop.put("user", "root");
			prop.put("password", "root");
			
			try {
				Connection conn =DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/yucu",prop);
				
				appendStatement = conn.prepareStatement(sqlAppendAccount1);
			
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			try {
				if (appendData(account1, proid1, quantity1)) {
					System.out.println("Success");}
			} catch (Exception e) {
				System.out.println(e.toString());
			}			
		}
	    });
	  }
	   
		static boolean appendData(String account1,String proid1, String quantity1) throws Exception {
			appendStatement.setString(1, account1);
			appendStatement.setString(2, proid1);
			appendStatement.setString(3, quantity1);
			int n =appendStatement.executeUpdate();
			return n!=0;
		}

	   //OVERRIDE A COUPLE OF METHODS
	   @Override
	  public Component getTableCellEditorComponent(JTable table, Object obj,
	      boolean selected, int row, int col) {

	      //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
	     lbl=(obj==null) ? "":obj.toString();
	     btn.setText(lbl);
	     clicked=true;
	    return btn;
	  }

	  //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
	   @Override
	  public Object getCellEditorValue() {

	     if(clicked)
	      {
	      //SHOW US SOME MESSAGE
	    	
	        JOptionPane.showMessageDialog(btn, lbl+" Clicked");
	      }
	    //SET IT TO FALSE NOW THAT ITS CLICKED
	    clicked=false;
	    return new String(lbl);
	  }

	   @Override
	  public boolean stopCellEditing() {

	         //SET CLICKED TO FALSE FIRST
	      clicked=false;
	    return super.stopCellEditing();
	  }

	   @Override
	  protected void fireEditingStopped() {
	    super.fireEditingStopped();
	  }
	}

	
	public static void main(String[] args) {
		new productTable();
	}
	
	

}
