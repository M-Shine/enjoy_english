package com.example.enjoy_english.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码
 */
public class VerificationCode {
    private int width = 100;
    private int height = 50;
    private String[] fontNames = {"宋体", "楷体", "隶书", "微软雅黑"};
    private Color bgColor = new Color(255, 255, 255);   //白色背景
    private Random random = new Random();
    private String codes = "0123456789";
    private String text;

    //获取一种随机颜色
    private Color randomColor(){
        int red = random.nextInt(150);
        int green = random.nextInt(150);
        int blue = random.nextInt(150);
        return new Color(red, green, blue);
    }

    //获取一种随机字体
    private Font randomFont(){
        String name = fontNames[random.nextInt(fontNames.length)];
        int style = random.nextInt(4);
        int size = random.nextInt(5) + 24;
        return new Font(name, style, size);
    }

    //获取一个随机字符
    private char randomChar(){
        return codes.charAt(random.nextInt(codes.length()));
    }

    //创建一个空白的BufferedImage对象
    private BufferedImage createImage(){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics2D = (Graphics2D)image.getGraphics();
        graphics2D.setColor(bgColor);
        graphics2D.fillRect(0, 0, width, height);
        return image;
    }

    public BufferedImage getImage(){
        BufferedImage image = createImage();
        Graphics2D graphics2D = (Graphics2D)image.getGraphics();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 4; i++){
            String s = randomChar() + "";
            stringBuffer.append(s);
            graphics2D.setColor(randomColor());
            graphics2D.setFont(randomFont());
            float x = i * width * 1.0f / 4;
            graphics2D.drawString(s, x, height - 15);
        }
        this.text = stringBuffer.toString();
        drawLine(image);
        return image;
    }

    // 绘制干扰线
    private  void drawLine(BufferedImage image){
        Graphics2D graphics2D = (Graphics2D)image.getGraphics();
        int num = 5;
        for (int i = 0; i < num; i++){
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            graphics2D.setColor(randomColor());
            graphics2D.setStroke(new BasicStroke(1.5f));
            graphics2D.drawLine(x1,y1,x2,y2);
        }
    }

    //获取图片上的文本
    public String getText(){
        return text;
    }

    //输出验证图
    public static void output(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image, "JPEG", out);
    }
}
