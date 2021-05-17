import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MidPointCircle {
    public static void main(String [] args){
        new Demo();
    }
}
class Demo{
    public static final int start = 30;
    public static final int step = 40;
    int Min;
    int Length;
    JFrame jFrame;
    JPanel jPanel_Input;
    JPanel jPanel_InputArea;
    JPanel jPanel_Graph;
    JTextField jTextField_R;
    JButton jButton_Go;
    int r;

    Demo(){
        jFrame = new JFrame("改变窗口大小后图形消失，按 Go 键重绘");
        jFrame.setLayout(new BorderLayout());
        jFrame.add(jPanel_Input = new JPanel() , BorderLayout.NORTH);
        jFrame.add(jPanel_Graph = new JPanel() , BorderLayout.CENTER);
        jPanel_Input.setLayout(new BorderLayout());
        jPanel_Input.add(jPanel_InputArea = new JPanel() , BorderLayout.CENTER);
        jPanel_InputArea.setLayout(new GridLayout(1,2));

        class textType extends KeyAdapter {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if(!(keyChar >= '0' && keyChar <= '9')){
                    e.consume();
                }
            }
        }

        jPanel_InputArea.add(new JLabel("R : "));
        jPanel_InputArea.add(jTextField_R = new JTextField());
        jTextField_R.addKeyListener(new textType());

        jPanel_Input.add(jButton_Go = new JButton("Go") , BorderLayout.EAST);
        jButton_Go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r = Integer.parseInt(jTextField_R.getText());
                Length = 2 * r;
                Min = -1 * r;
                Graphics graphics = jPanel_Graph.getGraphics();
                graphics.setColor(new Color(0x39c5bb));
                for(int i = 0 ; i <= Length ; i++){
                    graphics.drawLine(start + i * step , start , start + i * step , start + Length * step);
                    graphics.drawLine(start , start + i * step , start + Length * step , start + i * step);
                    graphics.drawString(String.valueOf(Min + i), start + i * step , start + Length * step);
                    graphics.drawString(String.valueOf(Min + i), start , start + Length * step - i * step);
                }
                graphics.setColor(new Color(0,102,51));
                graphics.drawOval(start , start , Length * step , Length * step);
                MidpointCircle(r);
            }
        });

        jFrame.setVisible(true);
        jFrame.setSize(760 , 760);
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

    void MidpointCircle(int r){
        int x,y;
        double d;
        x = 0;
        y = r;
        d = 1.25 - r;
        drawPixel(x,y);
        drawPixel(x,-y);
        drawPixel(-x,y);
        drawPixel(-x,-y);
        drawPixel(y,x);
        drawPixel(y,-x);
        drawPixel(-y,x);
        drawPixel(-y,-x);
        while (x<=y){
            if(d<0)
                d+=2*x+3;
            else{
                d+=2*(x-y)+5;
                y--;
            }
            x++;
            drawPixel(x,y);
            drawPixel(x,-y);
            drawPixel(-x,y);
            drawPixel(-x,-y);
            drawPixel(y,x);
            drawPixel(y,-x);
            drawPixel(-y,x);
            drawPixel(-y,-x);
        }
    }
}