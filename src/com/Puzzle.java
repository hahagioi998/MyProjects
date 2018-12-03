package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JWindow;

public class Puzzle extends JFrame implements Runnable{
	JMenuBar menuBar;// 创建菜单栏
	// 创建菜单,选择菜单，图片选择， 等级选择，帮助菜单
	JMenu menu, menuSelect, menuChange, menuRank, menuHelp;
	// 创建菜单项，即开始，原图，退出
	JMenuItem itemState, itemView, itemExit;
	// 创建单选菜单项数组
	JRadioButtonMenuItem pic_Change[] = new JRadioButtonMenuItem[4];
	JRadioButtonMenuItem game_Rank[] = new JRadioButtonMenuItem[3];
	JLabel total_time;//声明时间标签
	JLabel total_count;//声明步数标签
	long stateTime;//声明开始时的时间
	long endTime;//声明完成时的时间

	String path;// 设置路径
	int pattern;// 设置底数
	LeftPanel leftpanel;// 创建左边的面板
	RightPanel rightpanel;//创建右边的面板
	
	public Puzzle() {
		stateTime = System.currentTimeMillis();//获取开始时的毫秒数
		
		menuBar = new JMenuBar();// 创建菜单栏对象

		menu = new JMenu("菜单");// 实例化菜单
		itemState = new JMenuItem("开始");// 实例化开始菜单项
		itemView = new JMenuItem("原图");// 实例化原图菜单项
		itemExit = new JMenuItem("退出");// 实例化退出菜单项
		menu.add(itemState);// 将开始菜单项放进菜单
		menu.addSeparator();// 分割线
		menu.add(itemView);// 将原图菜单项放进菜单
		menu.addSeparator();// 分割线
		menu.add(itemExit);// 将退出菜单项放进菜单

		menuSelect = new JMenu("选择");// 实例化选择
		menuChange = new JMenu("图片更换");// 实例化图片选择
		menuRank = new JMenu("等级切换");// 实例化等级切换
		menuSelect.add(menuChange);// 将图片切换放进选择菜单
		menuSelect.addSeparator();// 分割线
		menuSelect.add(menuRank);// 将等级切换放进选择菜单
		
		ButtonGroup groupChange = new ButtonGroup();// 创建图片更换按钮组
		// 利用for循环，将按钮组里的按钮命名，并加入“图片更换”菜单中
		for (int i = 0; i < pic_Change.length; i++) {
			pic_Change[i] = new JRadioButtonMenuItem("0" + (i + 1) + ".jpg");
			groupChange.add(pic_Change[i]);
			menuChange.add(pic_Change[i]);
			// 加入分割条
			if (i != 3)
				menuChange.addSeparator();
		}
		pic_Change[0].setSelected(true);//将第一张图片设置为默认

		ButtonGroup groupRank = new ButtonGroup();// 创建等级切换按钮组
		String content;// 创建一个临时字符串变量
		// 利用for循环，将按钮组里的按钮命名，并加入“等级切换”菜单中
		for (int i = 0; i < game_Rank.length; i++) {
			if (i == 0)
				content = "菜鸟";
			else if (i == 1)
				content = "常人";
			else
				content = "大神";
			game_Rank[i] = new JRadioButtonMenuItem(content);
			groupRank.add(game_Rank[i]);
			menuRank.add(game_Rank[i]);
			// 加入分隔条
			if (i != 2)
				menuRank.addSeparator();
		}
		game_Rank[0].setSelected(true);//将第一个等级难度设置为默认

		menuHelp = new JMenu("帮助");// 实例化帮助菜单		
		
		total_time = new JLabel();//实例化时间标签
		total_count = new JLabel();//实例化步数标签
		
		menuBar.add(menu);// 将菜单放进菜单栏
		menuBar.add(menuSelect);// 将选择放进菜单栏
		menuBar.add(menuHelp);// 将菜单放进菜单栏
		menuBar.add(new JLabel("                                              "));//空格
		menuBar.add(total_time);//时间标签放进菜单栏
		menuBar.add(new JLabel("                        "));//空格
		menuBar.add(total_count);//步数标签放进菜单栏
		this.setJMenuBar(menuBar);// 在窗口创建菜单栏
		
		//给“开始”菜单项添加监听
		itemState.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				breakState();//调用breakState方法，使得“开始”菜单项可用
			}
		});
		//给“原图”菜单项添加监听
		itemView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				itemView.setEnabled(false);//将“原图”菜单项设置不可点击
				JFrame showPic = new JFrame("模板展示");//创建“原图”窗口
				showPic.setBounds(120, 200, 360, 405);//设置坐标位置，窗口大小
				showPic.setResizable(false);//窗体不可拖动
				showPic.setUndecorated(true);//设置窗体的最大化，最小化，及关闭按钮不空用
				showPic.setVisible(true);//窗口可见
				
				JButton pic = new JButton();//创建一个按钮
				pic.setIcon(new ImageIcon(path + "\\index.jpg"));//将要显示的图片放在按钮上
				JButton flush = new JButton("刷新");//创建一个“刷新”按钮，用于没有更新的原图更新
				JButton hide = new JButton("隐藏");//设置一个“隐藏”按钮，用于关闭原图界面
				showPic.add(pic);//将图片按钮放进窗体
				JPanel sjp = new JPanel();//创建一个放置“刷新”和“隐藏”的面板
				sjp.add(flush,BorderLayout.EAST);//将“刷新”按钮放进创建的面板的东部中
				sjp.add(hide, BorderLayout.WEST);//将“隐藏”按钮放进创建的面板的西部中
				showPic.add(sjp, BorderLayout.SOUTH);//将创建的面板放进原图界面的南部
				
				//添加“刷新”按钮的监听，用于点击之后更新原图界面中的图片
				flush.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						pic.setIcon(new ImageIcon(path + "\\index.jpg"));//更新原图
					}
				});
				//添加“隐藏”按钮监听
				hide.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						showPic.dispose();//将窗体关闭
						itemView.setEnabled(true);//窗体可见
					}
				});
			}
		});
		//给“退出”菜单项添加监听
		itemExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);//点击“退出”时，关闭程序
			}
		});
		
		this.setBounds(480, 200, 490, 405);//设置主窗体位置及大小
		this.setTitle("迷你拼图");//主窗体标题
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//主窗体关闭法则
		this.setResizable(false);//主窗体不可拖动大小
		this.setVisible(true);//主窗体可见
		Container c = getContentPane();//获取主窗体的容器
		this.setPath();// 获取路径
		this.setPattern();// 获取底数

		JSplitPane jsp = new JSplitPane();//创建分割面板
		jsp.setDividerLocation(360);//固定左边面板的大小
		jsp.setDividerSize(4);//分割面板的分割条大小
		jsp.setEnabled(false);//分割条不可拖动
		c.add(jsp, BorderLayout.CENTER);//将分割面板放进主容器

		leftpanel = new LeftPanel(path, pattern);// 左边面板实例化
		rightpanel = new RightPanel();//创建右边面板对象
		jsp.setLeftComponent(leftpanel);// 将左面板放入左边
		jsp.setRightComponent(rightpanel);//将右面板放入右边
		
		JButton restate = new JButton("开始");//创建右边面板的开始按钮
		restate.setBorderPainted(false);//按钮边框不可见
		restate.setBackground(Color.BLACK);//按钮背景色
		restate.setForeground(Color.CYAN);//按钮前景色
		rightpanel.add(restate, BorderLayout.SOUTH);//开始按钮放在右边面板的南部
		
		//右边开始按钮的监听
		restate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				restate.setText("重来");//修改按钮文本
				breakState();//调用开始方法
			}
		});
	}

	public void setPath() {//获取路径
		//将路径设置灵活
		for (int i = 0; i < pic_Change.length; i++) {
			if(pic_Change[i].isSelected())
				path = "img\\type" + (i+1) + "\\";
		}
	}

	public void setPattern() {//获取底数
		//将底数设置灵活
		for (int i = 0; i < game_Rank.length; i++) {
			if(game_Rank[i].isSelected()){
				if(i == 0) pattern = 3;
				else if(i == 1) pattern = 4;
				else pattern = 5;
			}
		}
	}
	
	//重新Runnable接口中的run方法
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			endTime = System.currentTimeMillis();//获取结束时的毫秒数
			int time = (int)((endTime - stateTime)/1000);//计算时间，将毫秒转换为秒，类型也强制转换
			total_time.setText("时间:" + time);//标签显示时间内容
			total_time.setForeground(Color.RED);//设置时间内容字体颜色
			total_count.setText("步数:" + leftpanel.getCount());//标签显示步数内容
			total_count.setForeground(Color.RED);//设置步数内容字体颜色
		}
	}
	//开始方法
	public void breakState(){
		stateTime = System.currentTimeMillis();//获取时间的毫秒数
		setPath();//设置路径
		setPattern();//设置底数
		leftpanel.breakRandom(path, pattern);//调用leftpanel类中的breakRandom方法，代入参数
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Puzzle pl = new Puzzle();
		Thread th = new Thread(pl);
		th.start();
	}
}
