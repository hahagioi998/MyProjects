package com;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.Flushable;
import java.util.Random;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LeftPanel extends JPanel {
	Button[] button = new Button[25];// 设计一个自定义按钮数组
	ImageIcon[] icon = new ImageIcon[25];// 设计一个图片数组
	int[] state = new int[25];// 设计一个存放顺序数组
	int nullbutton;// 空按钮
	int pattern;// 底数
	String path;//路径
	int total;// 总数
	int count;//步数计数

	public LeftPanel(String path, int pattern) {// 创建构造（主）函数
		this.path = path;//全局变量一定要传进来
		this.pattern = pattern;//全局变量一定要传进来
		total = pattern * pattern;//拼图按钮总数
		breakRandom(path, pattern);// 调用按钮设置方法
	}

	public void breakRandom(String path, int pattern) {// 创建按钮设置方法
		count = 0;//步数计数置0
		this.pattern = pattern;//pattern数值传进
		total = pattern * pattern;//拼图按钮总数
		// 调用图片切割的方法，生成图片
		ImageUtil.cutImage(new File(path + "\\index.jpg"), pattern, path + pattern);
		this.removeAll();// 移除所有的组件
		this.updateUI();// 刷新所有的组件
		this.setLayout(new GridLayout(pattern, pattern));// 设置容器布局
		nullbutton = total - 1;// 空按钮赋值
		random(state);// 调用随机方法，生成随机数，放在state数组中
		// 利用循环生成按钮，并放在容器中
		for (int i = 0; i < total; i++) {
			button[i] = new Button();//实例化按钮数组
			button[i].setRow(i/pattern);//设置按钮的行坐标
			button[i].setCol(i%pattern);//设置按钮的列坐标
			this.add(button[i]);//将按钮放进容器中
		}
		// 利用循环将生成的随机图片放在按钮上
		for (int i = 0; i < total - 1; i++) {
			icon[i] = new ImageIcon(path + pattern + "\\" + state[i] + ".jpg");
			button[i].setIcon(icon[i]);
		}
		//利用循环创建按钮鼠标点击监听
		for (int i = 0; i < total; i++) {
			button[i].addMouseListener(new MouseAdapter() { 
				//重写鼠标点击父方法
				@Override
				public void mousePressed(MouseEvent e) {
					Button button = (Button) e.getSource();//获取按钮事件源
					remove(button, path, pattern);//调用移动的方法
				}
			});
		}
	}

	public void random(int a[]) {// 创建随机方法
		Random cd = new Random();// 创建一个随机对象
		a[0] = cd.nextInt(total - 1);// 给第一个位置赋值
		int i = 0;
		// 利用双重嵌套循环，并产生一个随机数，与赋值好的按钮比较是不是相同，是则循环重新开始，否则将新值赋入新的按钮
		for (i = 0; i < total - 1; i++) {
			int temp = cd.nextInt(total - 1);
			for (int j = 0; j < i; j++) {
				if (a[j] != temp) {
					a[i] = temp;
				} else {
					i--;
					break;
				}
			}
		}
		a[i] = total - 1;// 最后的按钮赋值
		System.out.println("图片放置的顺序为:");//控制台输出
		for (i = 0; i < total; i++) {
			System.out.print(a[i] + "  ");//排序控制台输出
		}
	}
	//图片移动方法
	public void remove(Button clicked, String path, int pattern){//设置一个点击按钮为参数
		total = pattern * pattern;//拼图按钮总数
		int rowN = button[nullbutton].getRow();//获取空白按钮的行坐标
		int colN = button[nullbutton].getCol();//获取空白按钮的列坐标
		int rowC = clicked.getRow();//获取点击按钮的行坐标
		int colC = clicked.getCol();//获取点击按钮的列坐标
		//判断按钮是否可换
		if((((rowN - rowC) == 1)&&((colN - colC) == 0))||(((rowN - rowC) == 0)&&((colN - colC) == 1))
				||(((rowN - rowC) == -1)&&((colN - colC) == 0))||(((rowN - rowC) == 0)&&((colN - colC) == -1))){
			ImageIcon icon = (ImageIcon) clicked.getIcon();//获取点击按钮的图片
			button[nullbutton].setIcon(icon);//将空白按钮附上刚点击的图片
			clicked.setIcon(null);//将点击按钮设置为空白按钮
			//将点击按钮的位置数值和空白的位置数值互换(空白位置的数值按钮始终为total-1)
			int clickedState = rowC * pattern + colC;//获取设置按钮的索引
			state[nullbutton] = state[clickedState];//将点击按钮的位置数值赋值给空白的位置数值
			state[clickedState] = total - 1;//空白位置的数值按钮始终为total-1
			nullbutton = clickedState;//产生新的空白按钮的索引
			count++;//每移动一次步数计数自增一次
			check(path, pattern);//调用check方法，传进参数
		}else{
			return;
		}
		
	}
	//检查是否拼图完成
	public void check(String path, int pattern){
		this.path = path;//路径初始化
		this.pattern = pattern;//底数初始化
		total = pattern * pattern;//按钮总数初始化
		//利用循环判断图片是否顺序排放
		for (int i = 0; i < total; i++) {
			if(state[i] != i){
				return;
			}
		}
		//补全最后一张图片，使之完整
		button[total - 1].setIcon(new ImageIcon(path + pattern + "\\" + (total -1) +".jpg"));	
		JOptionPane.showMessageDialog(this, "游戏完成，恭喜您！！！");//完成可弹出对话框提示完成
	}
	//将count返回，方便主窗体调用
	public int getCount(){
		return count;
	}
}
