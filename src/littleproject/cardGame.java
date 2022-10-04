package littleproject;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class cardGame extends JFrame implements Runnable {
	private ButtonHandler bh = new ButtonHandler();//功能表單事件
	public JMenuBar menuBar;
	public final int row = 4;
    public final int col = 4;
    public final int LOOK_SEC = 4; //設定一開始可看幾秒
    public final int ADD = 1000, DECR = 100;//得分1000，扣分100
    public final int SEC = 1; //設定翻開的牌不相同時間隔秒數會蓋回去
    public int [] m;//存放每次重新玩的圖片順序
    public int imgH = 0,imgW =0,score = 0; 
    public JLabel scoreL = new JLabel("SCORE : 0");
	public JButton [] imgBtn = new JButton[row * col]; 
	public JMenu game = new JMenu("遊戲"),about = new JMenu("關於");;
	public JMenuItem [] gm = new JMenuItem[3];//遊戲
	public JMenuItem [] abo = new JMenuItem[2];//關於
	public ImageIcon [] img = new ImageIcon[row * col];
	public JPanel jpl = new JPanel(new GridLayout(4,4,0,0));
    private int [] twoImg = {-1,-1}; //記錄目前翻開的兩張圖編號
    private int [] btnIndex = {-1,-1}; //記錄被翻開兩張圖的按鈕位置
    private boolean isChange = false; //用來切換存放兩張圖順序
    private boolean isStart = false; //是否開始
    private boolean [] imgShow = new boolean[8]; //記錄已配對成功過的圖
    private boolean [] btnDown = new boolean [16]; //記錄被按過的按鈕
	
	
	cardGame(){
		super("翻翻看");
		Container c = getContentPane();
		c=this.getContentPane();
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		c.add(menuBar,BorderLayout.NORTH);
		c.add(scoreL,BorderLayout.SOUTH);
		menuBar.add(game);
		gm[0] = new JMenuItem("開始遊戲");
        gm[1] = new JMenuItem("重新遊戲");
        gm[1].setEnabled(false);
        gm[2] = new JMenuItem("離開遊戲");
        game.add(gm[0]);
        game.add(gm[1]);
        game.addSeparator();
        game.add(gm[2]);
        
        menuBar.add(about);
        abo[0] = new JMenuItem("遊戲說明");
        about.add(abo[0]);
        
        //初始化所有按鈕上的圖，預設為不可以按
        for(int i = 0; i < img.length; i++)
        {
         img[i] = new ImageIcon(getClass().getResource("/pic/p0.png"));
         imgBtn[i] = new JButton(img[i]);
         imgBtn[i].addActionListener(bh);
         imgBtn[i].setEnabled(false);
         jpl.add(imgBtn[i]);
         jpl.add(scoreL);
        }
		
		
		
		
      //設定視窗
        imgH = img[0].getIconHeight() * row; //取得圖片高度
        imgW = img[0].getIconWidth() * col;  //取得圖片寬度
        setSize(imgW,imgH);
        setLocation(200,100);
        setResizable(false);//視窗放大按鈕無效
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new cardGame();
	}

	private class ButtonHandler implements  ActionListener
    {
    
        public void actionPerformed(ActionEvent ae)
        {
         //按鈕事件
            for(int i = 0; i < img.length; i++)
            {
             if(ae.getSource() == imgBtn[i])
             {
              checkIsRight(i,imgIndex(m[i]));
              break;
             }
            }
            
            if(ae.getSource() == gm[0]) //開始遊戲
            {
             replay();
            }
            else if(ae.getSource() == gm[1]) //重新遊戲
            {
             replay();
            }
            else if(ae.getSource() == gm[2]) //結束遊戲
            {
             System.exit(0);
            }
            else if(ae.getSource() == abo[0]) //關於
            {
             JOptionPane.showMessageDialog(null,
             "1.圖片為" + row + "乘" + col +"，共" + (row * col) + "張圖\n" +
             "2.一開始會讓玩家看" + LOOK_SEC + "秒記圖片\n"+
             "3.翻錯圖片時，錯誤圖片會停留" + SEC + "秒讓玩家記\n"+
             "4.猜對得" + ADD + "分，猜錯扣" + DECR + "\n" +
             "5.分數顯示在左下方" ,
             "遊戲說明如下：",
             JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }
   
    //回傳一個存放1~N數值亂數陣列
    private int [] rand(int N)
    {
     int temp [] = new int[N];
    
     for(int i = 0; i < N; i++)
     {
      temp[i] = i;
     }
        for(int i = 0; i < N; i++)
        {
         int j = (int) (Math.random() * N);
         int tmp = temp[i];
         temp[i] = temp[j];
         temp[j] = tmp;
        }
     return temp;
    }
   
    //將記錄的圖片索引值0~15轉換為1~8
    private int imgIndex(int n)
    {
     return ((n/2)+1);
    }
   
    //開始(重玩)遊戲
    private void replay()
    {
     gm[0].setEnabled(false);
     gm[1].setEnabled(true);
     m = rand(16); //取得按鈕下次重排的圖案順序
    
        //設定所有圖片為可以翻狀態
     for(int i = 0; i < img.length; i++)
     {
      imgBtn[i].setEnabled(true);
      imgBtn[i].setIcon(new ImageIcon(getClass().getResource("/pic/p" + imgIndex(m[i]) + ".png")));
     }
    
     //清除記錄兩張翻開圖片按鈕與位置
     twoImg[0] = twoImg[1] = btnIndex[0] = btnIndex[1] = -1;
     isChange = false; //重設翻牌順序
     score = 0; //分數歸零
     scoreL.setText("SCORE : " + score); //更新顯示分數
     isStart = false;
    
     new Thread(new Runnable()
     {
      public void run()
      {
       try{
               Thread.sleep(LOOK_SEC * 1000);
              }catch(InterruptedException e){}
              //將圖清改為預設圖，並設定無法按按鍵
                for(int i = 0; i < img.length; i++)
                {
                 img[i] = new ImageIcon(getClass().getResource("/pic/back.png"));
                 imgBtn[i].setIcon(img[i]);
                 btnDown[i] = false; //將記錄過的按鈕清除
                }
                isStart = true;
      }
     }).start();
    
        //清除上局存放配對成功的圖片位置
        for(int i = 0; i < imgShow.length; i++)
         imgShow[i] = false;
    }
   
    //檢查是目前兩張圖是否配對
    private void checkIsRight(int btnN, int jpgNum)
    {
     if(!isStart) return; //判斷是否已讓玩家看完圖片排列，再開始
     if(btnDown[btnN]) return;//如果按下的圖片已翻開，就離開method
     btnDown[btnN] = true; //表示此按鈕已翻開狀態
     imgBtn[btnN].setIcon(new ImageIcon(getClass().getResource("/pic/p" + jpgNum + ".png")));    
    
     //記錄上張與目前圖片編號、按鈕位置twoImg[1]是上一張，twoImg[0]是目前                
     int temp = twoImg[0];
     int btnTemp = btnIndex[0];
     twoImg[0] = jpgNum;
     btnIndex[0] = btnN;
     twoImg[1] = temp;
     btnIndex[1] = btnTemp;
        
        if(isChange)
        {
         if(imgShow[jpgNum-1]) return; //若已配對成功
          if(twoImg[1] == twoImg[0])
      {
       imgShow[jpgNum-1] = true; //記錄已配對的圖片索引值
       twoImg[1] = -1;
       btnIndex[1] = -1;
       isChange = false;
       score += ADD; //配對成功分數增加
       scoreL.setText("SCORE : " + score);
       return;
      }
         
          //設定翻開兩張圖片時，其他圖片不能翻
          for(int i = 0; i < btnDown.length; i++)
           if(!btnDown[i]) imgBtn[i].setEnabled(false);
         
          new Thread(this).start();
     }           
     isChange = !isChange;
    }
   
    public void run ()
    {
      try{
       Thread.sleep(SEC * 1000);
      }catch(InterruptedException e){}
     
  //蓋住原本翻開的圖片        
  btnDown[btnIndex[0]] = false;
  btnDown[btnIndex[1]] = false;
     imgBtn[btnIndex[0]].setIcon(new ImageIcon(getClass().getResource("/pic/back.png")));
     imgBtn[btnIndex[1]].setIcon(new ImageIcon(getClass().getResource("/pic/back.png")));
    
     //設定蓋回兩張翻開圖片時，其他圖片可以回覆可以翻的狀態
     for(int i = 0; i < btnDown.length; i++)
       if(!btnDown[i]) imgBtn[i].setEnabled(true);
    
     score -= DECR; //猜錯扣分
     scoreL.setText("SCORE : " + score);
    }
	

}
