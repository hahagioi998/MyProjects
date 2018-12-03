package com;

import javax.swing.JButton;

public class Button extends JButton {//创建一个新的按钮类
	int row;//定义行坐标参数
	int col;//定义列坐标参数
	//设置行列方法及获取行列方法
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
}
