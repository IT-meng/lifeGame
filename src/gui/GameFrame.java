package gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class GameFrame {

  public JFrame secondFrame;
  public JButton[][] buttons;
  public  int[][] cells;
  public Label row_col_lable;
  public Label time_lable;
  public JTextField  row_col_field;
  public JTextField  time_field;
  public JFrame frame;
  public JButton confirm;
  public JButton set;
  public JButton start;
  public JPanel buttonsArea;
  public JButton pause;
  public JButton rand;

  private static GameFrame gf=null;
  public static GameFrame getInstance(){
      if(gf==null){
          gf = new GameFrame();
          return gf;
      }else {
          return gf;
      }
  }
    private   GameFrame(){
        rand = new JButton("系统初值");
        rand.setContentAreaFilled(false);
       frame = new JFrame("生命游戏");
       secondFrame = new JFrame("生命游戏");
       row_col_lable = new Label("行列数量：");
       row_col_lable.setFont(new Font("楷体",0,25));
       time_lable = new Label("演化间隔时间(ms)：");
       time_lable.setFont(new Font("楷体",Font.LAYOUT_NO_LIMIT_CONTEXT,25));
       confirm = new JButton("确定");
       confirm.setContentAreaFilled(false);
       set = new JButton("设置初值");
       start = new JButton("开始模拟");
       pause = new JButton("停止模拟");
       pause.setBackground(Color.WHITE);
       set.setContentAreaFilled(false);
       start.setContentAreaFilled(false);
       buttonsArea = new JPanel();
       row_col_field = new JTextField();
       time_field = new JTextField();
       Box first = Box.createHorizontalBox();
       Box second = Box.createHorizontalBox();
       first.add(row_col_lable);
       first.add(Box.createHorizontalStrut(50));
       first.add(row_col_field);
       first.add(Box.createHorizontalStrut(25));

       second.add(time_lable);
       second.add(Box.createHorizontalStrut(10));
       second.add(time_field);
       second.add(Box.createHorizontalStrut(30));

       Box vBox = Box.createVerticalBox();
       vBox.add(Box.createVerticalStrut(10));
       vBox.add(first);
       vBox.add(Box.createVerticalStrut(50));
       vBox.add(second);
       vBox.add(Box.createVerticalStrut(50));

       vBox.add(confirm);
       vBox.add(Box.createVerticalStrut(10));

       frame.add(vBox,BorderLayout.CENTER);
       frame.setSize(600,250);

       frame.setLocationRelativeTo(null);
       frame.setDefaultCloseOperation(3);

        rand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileInputStream fin = null;
                try {
                    fin = new FileInputStream("config.txt");
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                if(fin==null){
                    System.out.println("fin = null");
                }
                Scanner sc = new Scanner(fin);
                for (int i = 0; i < gf.cells.length; i++) {
                    for (int j = 0; j < gf.cells[0].length; j++) {
                        gf.cells[i][j]=0;
                    }
                }
                while(sc.hasNextInt()){
                    int i= sc.nextInt();
                    int j= sc.nextInt();
                    gf.cells[i][j]=1;
                }

                gf.fresh();

            }
        });


   }

   public void setSecondFrameButtonListener(){
       set.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               for (int i = 0; i < buttons.length; i++) {
                   for (int j = 0; j < buttons[0].length; j++) {
                       int ii = i;
                       int jj = j;
                       buttons[i][j].addActionListener(new ActionListener() {
                           @Override
                           public void actionPerformed(ActionEvent e) {
                               buttons[ii][jj].setBackground(Color.red);
                               cells[ii][jj]=1;
                           }
                       });
                   }
               }
           }
       });

   }

   public void fresh(){
       for (int i = 0; i < cells.length; i++) {
           for (int j = 0; j < cells[0].length; j++) {
               if(cells[i][j]==1){
                   buttons[i][j].setBackground(Color.red);
               }else if(cells[i][j]==0){
                   buttons[i][j].setBackground(Color.WHITE);
               }
           }
       }
   }

}
