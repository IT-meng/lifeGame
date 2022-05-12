package logical;

import gui.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class GameControler {
    private Timer timer;
    private GameFrame gf;

//    private JButton write;

   GameControler(){

//       write = new JButton("写文件");
//       write.addActionListener(new ActionListener() {
//           @Override
//           public void actionPerformed(ActionEvent e) {
//               try {
//                   PrintStream printStream = new PrintStream(new FileOutputStream("config.txt"));
//                   for (int i = 0; i < gf.cells.length; i++) {
//                       for (int j = 0; j < gf.cells[0].length; j++) {
//                           if(gf.cells[i][j]==1){
//                               printStream.print("["+i+","+j+"]");
//                           }
//                       }
//                   }
//               } catch (FileNotFoundException fileNotFoundException) {
//                   fileNotFoundException.printStackTrace();
//               }
//           }
//
//       });

       gf = GameFrame.getInstance();
       gf.confirm.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String rowStr = gf.row_col_field.getText();
               String miaoStr = gf.time_field.getText();


               int rowInt = 0;
               int miaoInt =0;
               if(rowStr!=null&&!rowStr.equals("")&&miaoStr!=null&&!miaoStr.equals("")){
                   rowInt = Integer.parseInt(rowStr);
                   miaoInt=Integer.parseInt(miaoStr);

                   gf.cells = new int[rowInt][rowInt];
                   gf.buttons = new JButton[rowInt][rowInt];
                   GridLayout gridLayout = new GridLayout();
                   gridLayout.setRows(rowInt);
                   gf. buttonsArea = new JPanel();
                   gf.secondFrame = new JFrame();
                   gf.secondFrame.setSize(750,700);
                   gf. buttonsArea.setSize(700,700);
                   gf.buttonsArea.setLayout(gridLayout);
                   gf.frame.setVisible(false);
                   for (int i=0;i<rowInt;i++){
                       for (int j = 0; j < rowInt; j++) {
                           gf.buttons[i][j] = new JButton();
                           //将按钮设为白色
                          gf. buttons[i][j].setBackground(Color.WHITE);
                         gf.  buttonsArea.add(gf.buttons[i][j]);
                       }
                   }

                   gf.secondFrame.addWindowListener(new WindowAdapter() {
                       @Override
                       public void windowClosing(WindowEvent e) {
                           gf.secondFrame.setVisible(false);
                           gf.frame.setVisible(true);
                           timer.stop();
                       }
                   });


                   timer = new Timer(miaoInt, new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                           gameOfLife(gf.cells);
                           gf.fresh();
                       }
                   });


                  gf. secondFrame.add(gf.buttonsArea,BorderLayout.CENTER);
                  gf.secondFrame.repaint();
                   //将set和start按钮添加到第二个frame
                   Box setBox= Box.createHorizontalBox();
                   setBox.add(Box.createHorizontalStrut(5));
                   setBox.add(gf.set);
                   setBox.add(Box.createHorizontalStrut(5));

                   Box startBox = Box.createHorizontalBox();
                   startBox.add(Box.createHorizontalStrut(5));
                   startBox.add(gf.start);
                   startBox.add(Box.createHorizontalStrut(5));

                   Box pauseBox = Box.createHorizontalBox();
                   pauseBox.add(Box.createHorizontalStrut(5));
                   pauseBox.add(gf.pause);
                   pauseBox.add(Box.createHorizontalStrut(5));

                   Box randBox = Box.createHorizontalBox();
                   randBox.add(Box.createHorizontalStrut(5));
                   randBox.add(gf.rand);
                   randBox.add(Box.createHorizontalStrut(5));

                   Box right = Box.createVerticalBox();
                   right.add(Box.createVerticalStrut(200));
                   right.add(setBox);
                   right.add(Box.createVerticalStrut(20));
                   right.add(startBox);
                   right.add(Box.createVerticalStrut(20));
                   right.add(pauseBox);
                   right.add(Box.createVerticalStrut(20));
                   right.add(randBox);

                   right.add(Box.createVerticalStrut(100));


                   gf.setSecondFrameButtonListener();

                   gf.secondFrame.add(right,BorderLayout.EAST);
                   gf.secondFrame.setLocationRelativeTo(null);
                   gf.secondFrame.setVisible(true);
               }else {
                   JOptionPane.showMessageDialog(gf.frame,"请输入相关参数","error",JOptionPane.INFORMATION_MESSAGE);
               }
           }
       });






       gf.pause.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               timer.stop();
           }
       });

       gf.start.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               timer.start();
           }
       });
   }


    public static void main(String[] args) {
        GameControler controler = new GameControler();
        controler.gf.frame.setVisible(true);

    }

    public void gameOfLife(int[][] board) {

        int[][] copy = new int[board.length][board[0].length];
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                int count = getLivedCell(board,i,j);
                if(board[i][j]==1){
                    //当前位置是活细胞
                    if(count<2){
                        copy[i][j] = 0;
                    }else if(count==2 || count==3){
                        copy[i][j] =1;
                    }else if(count>=4){
                        copy[i][j]=0;
                    }
                }else{
                    //当前位置是死细胞
                    if(count==3){
                        copy[i][j]=1;
                    }else{
                        copy[i][j]=0;
                    }

                }
            }
        }

        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                board[i][j]=copy[i][j];
            }
        }
        copy=null;

    }


    public int getLivedCell(int[][] board,int i,int j){
        //返回board[i][j]周围的8个位置的活细胞数量
        int count=0;
        //8个位置分别是：
        //(i-1,j)(i+1,j)(i,j-1)(i,j+1)(i-1,j-1)(i+1,j-1)(i-1,j+1)(i+1,j+1)

        if(i-1>=0){
            if(board[i-1][j]==1)count++;
        }

        if(i+1<board.length){
            if(board[i+1][j]==1)count++;
        }

        if(j-1>=0){
            if(board[i][j-1]==1)count++;;
        }
        if(j+1<board[0].length){
            if(board[i][j+1]==1)count++;
        }

        if(i-1>=0&&j-1>=0){
            if(board[i-1][j-1]==1)count++;
        }

        if(j-1>=0&&i+1<board.length){
            if(board[i+1][j-1]==1)count++;
        }
        if(i-1>=0&&j+1<board[0].length){
            if(board[i-1][j+1]==1)count++;
        }
        if(i+1<board.length&&j+1<board[0].length){
            if(board[i+1][j+1]==1)count++;
        }

        return count;
    }
}
