package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RightPanel extends JPanel {
	String path;//·��
	int pattern;//����
	int total;//ƴͼ��ť����
	long stateTime;//��ʼʱ��

	public RightPanel(){
		this.setBackground(Color.BLACK);//����屳��ɫ
		JButton showPic = new JButton();//����尴ť
		showPic.setBackground(Color.BLACK);//��ť����ɫ
		showPic.setBorderPainted(false);//��ť�߿򲻿ɼ�
		showPic.setIcon(new ImageIcon("img\\show\\2.jpg"));//��ť��ͼ
		
		JLabel jl1 = new JLabel();//������ǩ
		jl1.setForeground(Color.RED);//��ǩǰ��ɫ
		jl1.setText("��ӭ:   Jn.Camel  !!!");//��ǩ����
		JLabel jl2 = new JLabel();//��ǩ
		jl2.setForeground(Color.YELLOW);//��ǩǰ��ɫ
		jl2.setText("���Ʈ�������,      ");//��ǩ����
		JLabel jl3 = new JLabel();//��ǩ
		jl3.setForeground(Color.YELLOW);//��ǩǰ��ɫ
		jl3.setText("            �Ƕ�������!");//��ǩ����
		this.add(showPic);//��ʾ��ť�Ž��ұ����
		this.add(jl1);//��ǩ����
		this.add(jl2);//��ǩ����
		this.add(jl3);//��ǩ����
		
		//ʵ����ʾ��ť����
		showPic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Random rd = new Random();//���������
				int i = rd.nextInt(4);//��ȡ�����
				showPic.setIcon(new ImageIcon("img\\show\\" + i + ".jpg"));//��������Ž���ť����ͼƬ
			}
		});
	}
}
