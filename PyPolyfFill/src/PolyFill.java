import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class PolyFill {
    public static void main(String [] args){
        Demo demo = new Demo(
                new int[]{3, 5, 5, 8, 10, 7, 5, 5, 1, 1, 3},
                new int[]{1, 1, 4, 4, 6, 8, 8, 7, 7, 3, 1}
//                new int[]{5,2,2,5,11,11,5},
//                new int[]{1,2,7,5,8, 3, 1}
        );
        //demo.Poly_Fill();
        demo.DrawDemo();
    }
}
class Demo{
    public static final int start = 15;
    public static final int step = 20;
    int Min;
    int Max;
    int Length;
    JFrame jFrame;
    JPanel jPanel_Input;
    JPanel jPanel_Graph;
    JButton jButton_Go;
    int[] x;
    int[] y;

    Demo(int[] X, int[] Y){
        x = X;
        y = Y;
        Min = x[0];
        Max = x[0];
        for(int i = 0 ; i < x.length ; i++){
            if(Min > Math.min(x[i] , y[i])){
                Min = Math.min(x[i] , y[i]);
            }
            if(Max < Math.max(x[i] , y[i])){
                Max = Math.max(x[i] , y[i]);
            }
        }
        Length = Max - Min;
    }

    void DrawDemo(){

        jFrame = new JFrame("改变窗口大小后图形消失 , 按 Go 键重绘");
        jFrame.setLayout(new BorderLayout());
        jFrame.add(jPanel_Input = new JPanel() , BorderLayout.NORTH);
        jFrame.add(jPanel_Graph = new JPanel() , BorderLayout.CENTER);
        jPanel_Input.setLayout(new BorderLayout());

        jPanel_Input.add(jButton_Go = new JButton("Go") , BorderLayout.CENTER);
        jButton_Go.addActionListener(e -> {
            Graphics graphics = jPanel_Graph.getGraphics();
            graphics.setColor(new Color(0x39c5bb));
            for(int i = 0 ; i <= Length ; i++){
                graphics.drawLine(start + i * step , start , start + i * step , start + Length * step);
                graphics.drawLine(start , start + i * step , start + Length * step , start + i * step);
                graphics.drawString(String.valueOf(Min + i), start + i * step , start + Length * step);
                graphics.drawString(String.valueOf(Min + i), start , start + Length * step - i * step);
            }
            graphics.setColor(new Color(0,102,51));
            for(int i = 0 ; i < x.length - 1 ; i++){
                graphics.drawLine(
                        start + (x[i] - Min) * step,
                        start + Length * step - (y[i] - Min) * step,
                        start + (x[i + 1] - Min) * step,
                        start + Length * step - (y[i + 1] - Min) * step
                );
            }
            Poly_Fill();
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

    void Poly_Fill(){

        /*
        * 按照点集建立边集
        * 每条边的第一个点为 y 坐标最小的点*/
        int[][] borders = new int[x.length - 1][4];
        for(int i = 0 ; i < x.length - 1 ; i++){
            if(y[i] < y[i + 1]){
                borders[i][0] = x[i];
                borders[i][1] = y[i];
                borders[i][2] = x[i + 1];
                borders[i][3] = y[i + 1];
            }
            else{
                borders[i][0] = x[i + 1];
                borders[i][1] = y[i + 1];
                borders[i][2] = x[i];
                borders[i][3] = y[i];
            }
        }

        /*
        * 对边集中每条边按照 y 值从大到小进行排序 , 方便扫描线从上向下扫描*/
        Arrays.sort(borders, Comparator.comparingInt(o -> o[1]));

        /*
        * 构建新边表
        * 新边表用 HashMap 去构建 , 因为如果单纯使用 ArrayList 若 y_min过大 , 会造成空间浪费*/
        HashMap<Integer , ArrayList<double[]>> NET = new HashMap<>();
        for (int[] border : borders) {
            int y_min = border[1];
            double[] comBorder = new double[]{border[0],(border[2] * 1.0 - border[0] * 1.0) / (border[3] * 1.0 - border[1] * 1.0), border[3]};
            ArrayList<double[]> arrayList;
            if (NET.containsKey(y_min)) {
                arrayList = NET.get(y_min);
            } else {
                arrayList = new ArrayList<>();
            }
            arrayList.add(comBorder);
            NET.put(y_min, arrayList);
        }

        HashMap<Integer , ArrayList<double[]>> AET = new HashMap<>();
        for(int i = Min ; i <= Max ; i++){
            ArrayList<double[]> arrayList_Now = new ArrayList<>();
            if(NET.containsKey(i)){
                arrayList_Now = NET.get(i);
            }

            if(AET.containsKey(i - 1)){
                ArrayList<double[]> arrayList_Pre = AET.get(i - 1);
                for (double[] com : arrayList_Pre) {
                    if (com[2] != i - 1) {
                        arrayList_Now.add(new double[]{com[0] + com[1], com[1], com[2]});
                    }
                }
            }

            /*
            * 对活性边表里面的链表按 x 的顺序排序*/
            arrayList_Now.sort(Comparator.comparingDouble(o -> o[0]));

            /*
            * 开始在活性边表里面删点
            * 因为活性边表的 size 为奇数的时候没法去画图 , 所以在 size 为奇数时 ， 删掉没用的点 */
            if(arrayList_Now.size() % 2 == 1){
                for(int j = 0 ; j < arrayList_Now.size() ; j++){
                    if((int)(arrayList_Now.get(j))[2] == i){
                        arrayList_Now.remove(j);
                        break; // 删一个点就够画图的了 , 所以 break 出去 , 要么总是报 problem
                    }
                }
            }

            for(int j = 0 ; j < arrayList_Now.size() - 1 ; j += 2){
                int x0 = FourFive((arrayList_Now.get(j))[0]);
                int x1 = FourFive((arrayList_Now.get(j + 1))[0]);
                for(int k = x0 ; k <= x1 ; k++){
                    drawPixel(k , i);
                }
            }
            AET.put(i , arrayList_Now);
        }
    }

    /*
    * 四舍五入的规则*/
    int FourFive(double x){
        if(x >= 0)
            return (int) (x + 0.5);
        else{
            return (int) (x - 0.5);
        }
    }
}
