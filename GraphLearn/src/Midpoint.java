import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Demo {

    public static final int start = 30;
    public static final int step = 40;
    int Min;
    int Length;
    JFrame jFrame;
    JPanel jPanel_Input;
    JPanel jPanel_InputArea;
    JPanel jPanel_Graph;
    JTextField jTextField_x0;
    JTextField jTextField_y0;
    JTextField jTextField_x1;
    JTextField jTextField_y1;
    JButton jButton_Go;
    int x0 , y0 , x1 , y1;

    Demo(){
        jFrame = new JFrame("改变窗口大小后图形消失，按 Go 键重绘");
        jFrame.setLayout(new BorderLayout());
        jFrame.add(jPanel_Input = new JPanel() , BorderLayout.NORTH);
        jFrame.add(jPanel_Graph = new JPanel() , BorderLayout.CENTER);
        jPanel_Input.setLayout(new BorderLayout());
        jPanel_Input.add(jPanel_InputArea = new JPanel() , BorderLayout.CENTER);
        jPanel_InputArea.setLayout(new GridLayout(2,4));

        class textType extends KeyAdapter{
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if(false && !(keyChar >= '0' && keyChar <= '9')){
                    e.consume();
                }
            }
        }

        jPanel_InputArea.add(new JLabel("x0 : "));
        jPanel_InputArea.add(jTextField_x0 = new JTextField());
        jTextField_x0.addKeyListener(new textType());

        jPanel_InputArea.add(new JLabel("y0 : "));
        jPanel_InputArea.add(jTextField_y0 = new JTextField());
        jTextField_y0.addKeyListener(new textType());

        jPanel_InputArea.add(new JLabel("x1 : "));
        jPanel_InputArea.add(jTextField_x1 = new JTextField());
        jTextField_x1.addKeyListener(new textType());

        jPanel_InputArea.add(new JLabel("y1 : "));
        jPanel_InputArea.add(jTextField_y1 = new JTextField());
        jTextField_y1.addKeyListener(new textType());

        jPanel_Input.add(jButton_Go = new JButton("Go") , BorderLayout.EAST);
        jButton_Go.addActionListener(e -> {
            x0 = Integer.parseInt(jTextField_x0.getText());
            y0 = Integer.parseInt(jTextField_y0.getText());
            x1 = Integer.parseInt(jTextField_x1.getText());
            y1 = Integer.parseInt(jTextField_y1.getText());
            int Max = Math.max(Math.max(x0 , x1) , Math.max(y0 , y1));
            Min = Math.min(Math.min(x0 , x1) , Math.min(y0 , y1));
            Length = Max - Min;
            Graphics graphics = jPanel_Graph.getGraphics();
            graphics.setColor(new Color(0x39c5bb));
            for(int i = 0 ; i <= Length ; i++){
                graphics.drawLine(start + i * step , start , start + i * step , start + Length * step);
                graphics.drawLine(start , start + i * step , start + Length * step , start + i * step);
                graphics.drawString(String.valueOf(Min + i), start + i * step , start + Length * step);
                graphics.drawString(String.valueOf(Min + i), start , start + Length * step - i * step);
            }
            graphics.setColor(new Color(0,102,51));
            graphics.drawLine(
                    start + (x0 - Min) * step ,
                    start + Length * step - (y0 - Min) * step ,
                    start + (x1 - Min) * step ,
                    start + Length * step - (y1 - Min) * step);
            drawPixel(x1,y1);
            //MidpointLine(x0,y0,x1,y1);  // 中点画线法
            //DDA(x0 , y0 , x1 , y1);   // DDA算法
            BresenhamLine(x0 , y0 , x1 , y1);
            /*
            * 三个画线算法，两个已被注释掉，仅保留了一个
            * */
        });

        jFrame.setVisible(true);
        jFrame.setSize(500,500);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void drawPixel(int x , int y){
        Graphics graphics = jPanel_Graph.getGraphics();
        graphics.setColor(new Color(0x9F2D20));
        graphics.fillRect(
                start + (x - Min) * step ,
                start + Length * step - (y - Min) * step ,
                5 ,
                5
        );
    }

    /*------------------------------
    * 中点画线法
    * ------------------------------*/
    void MidpointLine(int x0, int y0, int x1, int y1){
        int a , b , d1 , d2 , d , x , y;

        int dx = x1 - x0;
        int dy = y1 - y0;
        drawPixel(x0 , y0);
        drawPixel(x1 , y1);
        int i = ((Math.abs(dx) >= Math.abs(dy) ? 1 : 0) << 1) + (dx * dy >= 0 ? 1 : 0);
        // 01 45~90
        // 11 0~45
        // 10 -45~0
        // 00 -90~45
        System.out.println(i);
        if(i == 0 || i == 2){
            y0 = -y0;
            y1 = -y1;
        }
        if(i == 0 || i == 1){
            int com = x0;
            x0 = y0;
            y0 = com;

            com = x1;
            x1 = y1;
            y1 = com;
        }
        a = -Math.abs(y0 - y1);
        b = Math.abs(x1 - x0);
        d = 2 * a + b;
        d1 = 2 * a;
        d2 = 2 * (a + b);
        x = Math.min(x0,x1);
        y = Math.min(y0,y1);
        x1 = Math.max(x0,x1);
        while(x < x1){
            x++;
            if(d < 0){
                y++;
                d = d + d2;
            }
            else{
                d = d + d1;
            }
            if(i == 3)
                drawPixel(x , y);
            if(i == 1)
                drawPixel(y , x);
            if(i == 2)
                drawPixel(x , -y);
            if(i == 0){
                drawPixel(y , -x);
            }
        }
    }

    /*------------------------------
    * DDA算法
    * 已被注释掉 81行
    * ------------------------------*/
    void DDA(int x0 , int y0 , int x1 , int y1){
        drawPixel(x0 , y0);
        drawPixel(x1 , y1);

        int dx = x1 - x0;
        int dy = y1 - y0;
        drawPixel(x0 , y0);
        drawPixel(x1 , y1);
        int i = ((Math.abs(dx) >= Math.abs(dy) ? 1 : 0) << 1) + (dx * dy >= 0 ? 1 : 0);
        // 01 45~90
        // 11 0~45
        // 10 -45~0
        // 00 -90~45
        if(i == 0 || i == 2){
            y0 = -y0;
            y1 = -y1;
        }
        if(i == 0 || i == 1){
            int com = x0;
            x0 = y0;
            y0 = com;

            com = x1;
            x1 = y1;
            y1 = com;
        }

        double k = ((y1 - y0) * 1.0) / ((x1 - x0) * 1.0);
        System.out.println(k);

        int x = Math.min(x0 , x1);
        double y = Math.min(y0 , y1);
        while (x < Math.max(x0 , x1)){
            x++;
            y = y + k;
            if(i == 3)
                drawPixel(x , FourFive(y));
            if(i == 1)
                drawPixel(FourFive(y), x);
            if(i == 2)
                drawPixel(x , -FourFive(y));
            if(i == 0)
                drawPixel(FourFive(y), -x);
        }
    }

    int FourFive(double x){
        if(x >= 0)
            return (int) (x + 0.5);
        else{
            return (int) (x - 0.5);
        }
    }

    /*------------------------------
    * Bresenham算法
    * 已被注释掉
    * ------------------------------*/
    void BresenhamLine(int x0 , int y0 , int x1 , int y1){
        int dx = x1 - x0;
        int dy = y1 - y0;
        drawPixel(x0 , y0);
        drawPixel(x1 , y1);
        int i = ((Math.abs(dx) >= Math.abs(dy) ? 1 : 0) << 1) + (dx * dy >= 0 ? 1 : 0);
        // 01 45~90
        // 11 0~45
        // 10 -45~0
        // 00 -90~45
        System.out.println(i);
        if(i == 0 || i == 2){
            y0 = -y0;
            y1 = -y1;
        }
        if(i == 0 || i == 1){
            int com = x0;
            x0 = y0;
            y0 = com;

            com = x1;
            x1 = y1;
            y1 = com;
        }
        int x , y;
        double e , k;
        dx = x1 - x0;
        dy = y1 - y0;
        k = (dy * 1.0) / (dx * 1.0);
        e = -0.5;
        x = x0;
        y = y0;
        for(int j = 0 ; j <= dx ; j++){
            if(i == 3)
                drawPixel(x , y);
            if(i == 1)
                drawPixel(y , x);
            if(i == 2)
                drawPixel(x , -y);
            if(i == 0){
                drawPixel(y , -x);
            }
            x++;
            e = e + k;
            if(e >= 0){
                y++;
                e = e - 1;
            }
        }
    }
}

public class Midpoint{
    public static void main(String [] args){
        new Demo();
    }
}