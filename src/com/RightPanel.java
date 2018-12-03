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
	String path;//路径
	int pattern;//底数
	int total;//拼图按钮总数
	long stateTime;//开始时间

	public RightPanel(){
		this.setBackground(Color.BLACK);//右面板背景色
		JButton showPic = new JButton();//右面板按钮
		showPic.setBackground(Color.BLACK);//按钮背景色
		showPic.setBorderPainted(false);//按钮边框不可见
		showPic.setIcon(new ImageIcon("img\\show\\2.jpg"));//按钮附图
		
		JLabel jl1 = new JLabel();//创建标签
		jl1.setForeground(Color.RED);//标签前景色
		jl1.setText("欢迎:   Jn.Camel  !!!");//标签内容
		JLabel jl2 = new JLabel();//标签
		jl2.setForeground(Color.YELLOW);//标签前景色
		jl2.setText("天空飘来五个字,      ");//标签内容
		JLabel jl3 = new JLabel();//标签
		jl3.setForeground(Color.YELLOW);//标签前景色
		jl3.setText("            那都不是事!");//标签内容
		this.add(showPic);//显示按钮放进右边面板
		this.add(jl1);//标签放入
		this.add(jl2);//标签放入
		this.add(jl3);//标签放入
		
		//实现显示按钮监听
		showPic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Random rd = new Random();//创建随机数
				int i = rd.nextInt(4);//获取随机数
				showPic.setIcon(new ImageIcon("img\\show\\" + i + ".jpg"));//将随机数放进按钮更改图片
			}
		});
	}
}
