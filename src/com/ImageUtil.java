package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	public static boolean cutImage(File sourcePath, int cutNumber, String savePath) {
		try {
			BufferedImage image = ImageIO.read(sourcePath);// 使用IO流读取图片
			int allwidth = image.getWidth();// 获取图片宽度
			int allheight = image.getHeight();// 获取图片长度
			int width = allwidth / cutNumber;// 获取截取后图片宽度
			int height = allheight / cutNumber;// 获取截取后图片长度
			// 采用双重循环来截取图片
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
