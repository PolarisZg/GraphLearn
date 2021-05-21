编写者
----------
PolarisZg 计算01  <br>
[GitHub](https://github.com/PolarisZg/GraphLearn)
# 计算机图形学

## 文件结构

> 画直线
>> GraphLearn
>>
> 画圆
>> MidPointCircle
>>
> 多边形的直线扫描算法
>> PyPolyFill

### 画直线

Midpoint.java为所有画直线算法的Java源文件<br>
> 第81-83行为三个画线算法
GraphLearn为IDEA生成的project文件，可以在IDEA中打开该文件夹<br>

### 画圆

MidPointCircle.java为所有画直线算法的Java源文件<br>
GraphLearn为IDEA生成的project文件，可以在IDEA中打开该文件夹<br>

### 多边形的直线扫描算法

真的是，课本各种优化，我一个排序直接把时间复杂度拉高。谁叫咱菜呢，不会写简洁的代码又要去完成复杂的作业，只能靠牺牲时间复杂度空间复杂度去硬算。<br>
直到我写完这次作业，我也没完成特殊的一群多边形(挖了个洞的多边形啦，有相交边的多边形啦，或者一个图里面有好多个互相不接触的多边形啦)<br>
而且也没完成在图形界面上输入数据的工作，只能通过修改代码去改变多边形的形状 

## 已知Bug

由于JavaGUI设计的问题(当然主要原因是我没有能力去搞所有数据的序列化存储)，改变窗口大小会导致已经绘制的图像消失，只能通过重新点击 `Go` 这个JButton重绘

#### 结
本次主要的功力都用在了在Java的JPanel里面绘制坐标系，下次尝试其他语言的绘图方法。不过既然已经造好了Java绘制坐标系这个轮子，可能之后偷懒就一直复制粘贴使用这个Demo类了。<br>
2021-5-21&ensp;依旧使用的Java，下次再改用其他语言吧