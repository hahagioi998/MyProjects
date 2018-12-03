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
	Button[] button = new Button[25];// ���һ���Զ��尴ť����
	ImageIcon[] icon = new ImageIcon[25];// ���һ��ͼƬ����
	int[] state = new int[25];// ���һ�����˳������
	int nullbutton;// �հ�ť
	int pattern;// ����
	String path;//·��
	int total;// ����
	int count;//��������

	public LeftPanel(String path, int pattern) {// �������죨��������
		this.path = path;//ȫ�ֱ���һ��Ҫ������
		this.pattern = pattern;//ȫ�ֱ���һ��Ҫ������
		total = pattern * pattern;//ƴͼ��ť����
		breakRandom(path, pattern);// ���ð�ť���÷���
	}

	public void breakRandom(String path, int pattern) {// ������ť���÷���
		count = 0;//����������0
		this.pattern = pattern;//pattern��ֵ����
		total = pattern * pattern;//ƴͼ��ť����
		// ����ͼƬ�и�ķ���������ͼƬ
		ImageUtil.cutImage(new File(path + "\\index.jpg"), pattern, path + pattern);
		this.removeAll();// �Ƴ����е����
		this.updateUI();// ˢ�����е����
		this.setLayout(new GridLayout(pattern, pattern));// ������������
		nullbutton = total - 1;// �հ�ť��ֵ
		random(state);// ����������������������������state������
		// ����ѭ�����ɰ�ť��������������
		for (int i = 0; i < total; i++) {
			button[i] = new Button();//ʵ������ť����
			button[i].setRow(i/pattern);//���ð�ť��������
			button[i].setCol(i%pattern);//���ð�ť��������
			this.add(button[i]);//����ť�Ž�������
		}
		// ����ѭ�������ɵ����ͼƬ���ڰ�ť��
		for (int i = 0; i < total - 1; i++) {
			icon[i] = new ImageIcon(path + pattern + "\\" + state[i] + ".jpg");
			button[i].setIcon(icon[i]);
		}
		//����ѭ��������ť���������
		for (int i = 0; i < total; i++) {
			button[i].addMouseListener(new MouseAdapter() { 
				//��д�����������
				@Override
				public void mousePressed(MouseEvent e) {
					Button button = (Button) e.getSource();//��ȡ��ť�¼�Դ
					remove(button, path, pattern);//�����ƶ��ķ���
				}
			});
		}
	}

	public void random(int a[]) {// �����������
		Random cd = new Random();// ����һ���������
		a[0] = cd.nextInt(total - 1);// ����һ��λ�ø�ֵ
		int i = 0;
		// ����˫��Ƕ��ѭ����������һ����������븳ֵ�õİ�ť�Ƚ��ǲ�����ͬ������ѭ�����¿�ʼ��������ֵ�����µİ�ť
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
		a[i] = total - 1;// ���İ�ť��ֵ
		System.out.println("ͼƬ���õ�˳��Ϊ:");//����̨���
		for (i = 0; i < total; i++) {
			System.out.print(a[i] + "  ");//�������̨���
		}
	}
	//ͼƬ�ƶ�����
	public void remove(Button clicked, String path, int pattern){//����һ�������ťΪ����
		total = pattern * pattern;//ƴͼ��ť����
		int rowN = button[nullbutton].getRow();//��ȡ�հװ�ť��������
		int colN = button[nullbutton].getCol();//��ȡ�հװ�ť��������
		int rowC = clicked.getRow();//��ȡ�����ť��������
		int colC = clicked.getCol();//��ȡ�����ť��������
		//�жϰ�ť�Ƿ�ɻ�
		if((((rowN - rowC) == 1)&&((colN - colC) == 0))||(((rowN - rowC) == 0)&&((colN - colC) == 1))
				||(((rowN - rowC) == -1)&&((colN - colC) == 0))||(((rowN - rowC) == 0)&&((colN - colC) == -1))){
			ImageIcon icon = (ImageIcon) clicked.getIcon();//��ȡ�����ť��ͼƬ
			button[nullbutton].setIcon(icon);//���հװ�ť���ϸյ����ͼƬ
			clicked.setIcon(null);//�������ť����Ϊ�հװ�ť
			//�������ť��λ����ֵ�Ϳհ׵�λ����ֵ����(�հ�λ�õ���ֵ��ťʼ��Ϊtotal-1)
			int clickedState = rowC * pattern + colC;//��ȡ���ð�ť������
			state[nullbutton] = state[clickedState];//�������ť��λ����ֵ��ֵ���հ׵�λ����ֵ
			state[clickedState] = total - 1;//�հ�λ�õ���ֵ��ťʼ��Ϊtotal-1
			nullbutton = clickedState;//�����µĿհװ�ť������
			count++;//ÿ�ƶ�һ�β�����������һ��
			check(path, pattern);//����check��������������
		}else{
			return;
		}
		
	}
	//����Ƿ�ƴͼ���
	public void check(String path, int pattern){
		this.path = path;//·����ʼ��
		this.pattern = pattern;//������ʼ��
		total = pattern * pattern;//��ť������ʼ��
		//����ѭ���ж�ͼƬ�Ƿ�˳���ŷ�
		for (int i = 0; i < total; i++) {
			if(state[i] != i){
				return;
			}
		}
		//��ȫ���һ��ͼƬ��ʹ֮����
		button[total - 1].setIcon(new ImageIcon(path + pattern + "\\" + (total -1) +".jpg"));	
		JOptionPane.showMessageDialog(this, "��Ϸ��ɣ���ϲ��������");//��ɿɵ����Ի�����ʾ���
	}
	//��count���أ��������������
	public int getCount(){
		return count;
	}
}
