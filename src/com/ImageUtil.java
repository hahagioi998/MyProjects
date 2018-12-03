package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	public static boolean cutImage(File sourcePath, int cutNumber, String savePath) {
		try {
			BufferedImage image = ImageIO.read(sourcePath);// ʹ��IO����ȡͼƬ
			int allwidth = image.getWidth();// ��ȡͼƬ���
			int allheight = image.getHeight();// ��ȡͼƬ����
			int width = allwidth / cutNumber;// ��ȡ��ȡ��ͼƬ���
			int height = allheight / cutNumber;// ��ȡ��ȡ��ͼƬ����
			// ����˫��ѭ������ȡͼƬ
			for (int i = 0; i < cutNumber; i++) {
				for (int j = 0; j < cutNumber; j++) {
					ImageIO.write(image.getSubimage(j * width, i * height, width, height), "jpg",
							new File(savePath + "\\" + (i * cutNumber + j) + ".jpg"));
				}
			}
			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
