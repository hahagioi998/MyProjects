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
	JMenuBar menuBar;// �����˵���
	// �����˵�,ѡ��˵���ͼƬѡ�� �ȼ�ѡ�񣬰����˵�
	JMenu menu, menuSelect, menuChange, menuRank, menuHelp;
	// �����˵������ʼ��ԭͼ���˳�
	JMenuItem itemState, itemView, itemExit;
	// ������ѡ�˵�������
	JRadioButtonMenuItem pic_Change[] = new JRadioButtonMenuItem[4];
	JRadioButtonMenuItem game_Rank[] = new JRadioButtonMenuItem[3];
	JLabel total_time;//����ʱ���ǩ
	JLabel total_count;//����������ǩ
	long stateTime;//������ʼʱ��ʱ��
	long endTime;//�������ʱ��ʱ��

	String path;// ����·��
	int pattern;// ���õ���
	LeftPanel leftpanel;// ������ߵ����
	RightPanel rightpanel;//�����ұߵ����
	
	public Puzzle() {
		stateTime = System.currentTimeMillis();//��ȡ��ʼʱ�ĺ�����
		
		menuBar = new JMenuBar();// �����˵�������

		menu = new JMenu("�˵�");// ʵ�����˵�
		itemState = new JMenuItem("��ʼ");// ʵ������ʼ�˵���
		itemView = new JMenuItem("ԭͼ");// ʵ����ԭͼ�˵���
		itemExit = new JMenuItem("�˳�");// ʵ�����˳��˵���
		menu.add(itemState);// ����ʼ�˵���Ž��˵�
		menu.addSeparator();// �ָ���
		menu.add(itemView);// ��ԭͼ�˵���Ž��˵�
		menu.addSeparator();// �ָ���
		menu.add(itemExit);// ���˳��˵���Ž��˵�

		menuSelect = new JMenu("ѡ��");// ʵ����ѡ��
		menuChange = new JMenu("ͼƬ����");// ʵ����ͼƬѡ��
		menuRank = new JMenu("�ȼ��л�");// ʵ�����ȼ��л�
		menuSelect.add(menuChange);// ��ͼƬ�л��Ž�ѡ��˵�
		menuSelect.addSeparator();// �ָ���
		menuSelect.add(menuRank);// ���ȼ��л��Ž�ѡ��˵�
		
		ButtonGroup groupChange = new ButtonGroup();// ����ͼƬ������ť��
		// ����forѭ��������ť����İ�ť�����������롰ͼƬ�������˵���
		for (int i = 0; i < pic_Change.length; i++) {
			pic_Change[i] = new JRadioButtonMenuItem("0" + (i + 1) + ".jpg");
			groupChange.add(pic_Change[i]);
			menuChange.add(pic_Change[i]);
			// ����ָ���
			if (i != 3)
				menuChange.addSeparator();
		}
		pic_Change[0].setSelected(true);//����һ��ͼƬ����ΪĬ��

		ButtonGroup groupRank = new ButtonGroup();// �����ȼ��л���ť��
		String content;// ����һ����ʱ�ַ�������
		// ����forѭ��������ť����İ�ť�����������롰�ȼ��л����˵���
		for (int i = 0; i < game_Rank.length; i++) {
			if (i == 0)
				content = "����";
			else if (i == 1)
				content = "����";
			else
				content = "����";
			game_Rank[i] = new JRadioButtonMenuItem(content);
			groupRank.add(game_Rank[i]);
			menuRank.add(game_Rank[i]);
			// ����ָ���
			if (i != 2)
				menuRank.addSeparator();
		}
		game_Rank[0].setSelected(true);//����һ���ȼ��Ѷ�����ΪĬ��

		menuHelp = new JMenu("����");// ʵ���������˵�		
		
		total_time = new JLabel();//ʵ����ʱ���ǩ
		total_count = new JLabel();//ʵ����������ǩ
		
		menuBar.add(menu);// ���˵��Ž��˵���
		menuBar.add(menuSelect);// ��ѡ��Ž��˵���
		menuBar.add(menuHelp);// ���˵��Ž��˵���
		menuBar.add(new JLabel("                                              "));//�ո�
		menuBar.add(total_time);//ʱ���ǩ�Ž��˵���
		menuBar.add(new JLabel("                        "));//�ո�
		menuBar.add(total_count);//������ǩ�Ž��˵���
		this.setJMenuBar(menuBar);// �ڴ��ڴ����˵���
		
		//������ʼ���˵�����Ӽ���
		itemState.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				breakState();//����breakState������ʹ�á���ʼ���˵������
			}
		});
		//����ԭͼ���˵�����Ӽ���
		itemView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				itemView.setEnabled(false);//����ԭͼ���˵������ò��ɵ��
				JFrame showPic = new JFrame("ģ��չʾ");//������ԭͼ������
				showPic.setBounds(120, 200, 360, 405);//��������λ�ã����ڴ�С
				showPic.setResizable(false);//���岻���϶�
				showPic.setUndecorated(true);//���ô������󻯣���С�������رհ�ť������
				showPic.setVisible(true);//���ڿɼ�
				
				JButton pic = new JButton();//����һ����ť
				pic.setIcon(new ImageIcon(path + "\\index.jpg"));//��Ҫ��ʾ��ͼƬ���ڰ�ť��
				JButton flush = new JButton("ˢ��");//����һ����ˢ�¡���ť������û�и��µ�ԭͼ����
				JButton hide = new JButton("����");//����һ�������ء���ť�����ڹر�ԭͼ����
				showPic.add(pic);//��ͼƬ��ť�Ž�����
				JPanel sjp = new JPanel();//����һ�����á�ˢ�¡��͡����ء������
				sjp.add(flush,BorderLayout.EAST);//����ˢ�¡���ť�Ž����������Ķ�����
				sjp.add(hide, BorderLayout.WEST);//�������ء���ť�Ž�����������������
				showPic.add(sjp, BorderLayout.SOUTH);//�����������Ž�ԭͼ������ϲ�
				
				//��ӡ�ˢ�¡���ť�ļ��������ڵ��֮�����ԭͼ�����е�ͼƬ
				flush.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						pic.setIcon(new ImageIcon(path + "\\index.jpg"));//����ԭͼ
					}
				});
				//��ӡ����ء���ť����
				hide.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						showPic.dispose();//������ر�
						itemView.setEnabled(true);//����ɼ�
					}
				});
			}
		});
		//�����˳����˵�����Ӽ���
		itemExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);//������˳���ʱ���رճ���
			}
		});
		
		this.setBounds(480, 200, 490, 405);//����������λ�ü���С
		this.setTitle("����ƴͼ");//���������
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//������رշ���
		this.setResizable(false);//�����岻���϶���С
		this.setVisible(true);//������ɼ�
		Container c = getContentPane();//��ȡ�����������
		this.setPath();// ��ȡ·��
		this.setPattern();// ��ȡ����

		JSplitPane jsp = new JSplitPane();//�����ָ����
		jsp.setDividerLocation(360);//�̶�������Ĵ�С
		jsp.setDividerSize(4);//�ָ����ķָ�����С
		jsp.setEnabled(false);//�ָ��������϶�
		c.add(jsp, BorderLayout.CENTER);//���ָ����Ž�������

		leftpanel = new LeftPanel(path, pattern);// ������ʵ����
		rightpanel = new RightPanel();//�����ұ�������
		jsp.setLeftComponent(leftpanel);// �������������
		jsp.setRightComponent(rightpanel);//�����������ұ�
		
		JButton restate = new JButton("��ʼ");//�����ұ����Ŀ�ʼ��ť
		restate.setBorderPainted(false);//��ť�߿򲻿ɼ�
		restate.setBackground(Color.BLACK);//��ť����ɫ
		restate.setForeground(Color.CYAN);//��ťǰ��ɫ
		rightpanel.add(restate, BorderLayout.SOUTH);//��ʼ��ť�����ұ������ϲ�
		
		//�ұ߿�ʼ��ť�ļ���
		restate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				restate.setText("����");//�޸İ�ť�ı�
				breakState();//���ÿ�ʼ����
			}
		});
	}

	public void setPath() {//��ȡ·��
		//��·���������
		for (int i = 0; i < pic_Change.length; i++) {
			if(pic_Change[i].isSelected())
				path = "img\\type" + (i+1) + "\\";
		}
	}

	public void setPattern() {//��ȡ����
		//�������������
		for (int i = 0; i < game_Rank.length; i++) {
			if(game_Rank[i].isSelected()){
				if(i == 0) pattern = 3;
				else if(i == 1) pattern = 4;
				else pattern = 5;
			}
		}
	}
	
	//����Runnable�ӿ��е�run����
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			endTime = System.currentTimeMillis();//��ȡ����ʱ�ĺ�����
			int time = (int)((endTime - stateTime)/1000);//����ʱ�䣬������ת��Ϊ�룬����Ҳǿ��ת��
			total_time.setText("ʱ��:" + time);//��ǩ��ʾʱ������
			total_time.setForeground(Color.RED);//����ʱ������������ɫ
			total_count.setText("����:" + leftpanel.getCount());//��ǩ��ʾ��������
			total_count.setForeground(Color.RED);//���ò�������������ɫ
		}
	}
	//��ʼ����
	public void breakState(){
		stateTime = System.currentTimeMillis();//��ȡʱ��ĺ�����
		setPath();//����·��
		setPattern();//���õ���
		leftpanel.breakRandom(path, pattern);//����leftpanel���е�breakRandom�������������
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Puzzle pl = new Puzzle();
		Thread th = new Thread(pl);
		th.start();
	}
}
