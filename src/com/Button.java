package com;

import javax.swing.JButton;

public class Button extends JButton {//����һ���µİ�ť��
	int row;//�������������
	int col;//�������������
	//�������з�������ȡ���з���
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
