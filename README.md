编写者
----------
PolarisZg 计算01  <br>
[GitHub](https://github.com/PolarisZg/GraphLearn)
# 计算机图形学--第一次作业

## 文件结构

> 画直线
>> GraphLearn
>>
> 画圆
>> MidPointCircle

### 画直线文件夹

Midpoint.java为所有画直线算法的Java源文件<br>
> 第81-83行为三个画线算法
GraphLearn为IDEA生成的project文件，可以在IDEA中打开该文件夹<br>

### 画原文件夹

MidPointCircle.java为所有画直线算法的Java源文件<br>
GraphLearn为IDEA生成的project文件，可以在IDEA中打开该文件夹<br>

## 已知Bug

由于JavaGUI设计的问题(当然主要原因是我没有能力去搞所有数据的序列化存储)，改变窗口大小会导致已经绘制的图像消失，只能通过重新点击 `Go` 这个JButton重绘

#### 结
本次主要的功力都用在了在Java的JPanel里面绘制坐标系，下次尝试其他语言的绘图方法。不过既然已经造好了Java绘制坐标系这个轮子，可能之后偷懒就一直复制粘贴使用这个Demo类了。<br>