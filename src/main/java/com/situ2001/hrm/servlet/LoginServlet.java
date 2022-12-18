package com.situ2001.hrm.servlet;

import com.google.gson.Gson;
import com.situ2001.hrm.dao.impl.UserDaoImpl;
import com.situ2001.hrm.pojo.R;
import com.situ2001.hrm.pojo.User;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet(urlPatterns = {"/jsp/login", "/getCpacha"})
public class LoginServlet extends HttpServlet {
    //    接收get请求
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        验证码功能
        //通过java中awt中提供的类绘制验证码图片
//        1.创建一张图片
        int height = 90;//验证码高度
        int width = 300;//验证码宽度
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//实例化一个图片对象
//        2.绘制图片对象，从图片对象中获取绘制图片的笔
        Graphics2D pen = image.createGraphics();
        //a.绘制背景
        pen.setColor(getRandomColor());
        pen.fillRect(0, 0, width, height);//绘制实心矩形

        //b.绘制验证码字符串
        int letterNum = 4;//验证码图片上的字符的个数
        int space = 20;//验证码图片上两个字母之间的空隙
        int letterWidth = (width - (letterNum + 1) * space) / letterNum;//计算每个字母占据的宽度

//        for循环每循环一次，绘制一个字母（小写字母的ascii码：97-122）
        Random random = new Random();
        String code = "";
        for (int i = 0; i < letterNum; i++) {
//            随机生成一个小写字母
            int ascii = random.nextInt(26) + 97;//97-122
            byte[] bytes = {(byte) ascii};
            String letter = new String(bytes);
            code = code + letter;//为了保存验证码字符到session
//            绘制字母
            pen.setColor(getRandomColor());
            pen.setFont(new Font("Gulim", Font.BOLD, 70));
            pen.drawString(letter, space + (letterWidth + space) * i, height - space);//把该字母写在画布上

        }
        HttpSession session = request.getSession();
        session.setAttribute("code", code);
        //添加图片干扰，防止机器自动识别
//            图片绘制完成后，将图片通过response输出流响应到客户端
        ImageIO.write(image, "png", response.getOutputStream());
    }

    private Color getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        Color color = new Color(r, g, b);
        return color;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String vcode = req.getParameter("vcode");
        System.out.println("用户" + username + " 密码" + password + "--vcode：" + vcode); // TODO a better logger

        HttpSession session = req.getSession();
        String code = (String) session.getAttribute("code");
        System.out.println(code);
        if (code.equals(vcode)) {
            // TODO get info from DAO
            var userImpl = new UserDaoImpl();
            var user = userImpl.login(username, password);
            System.out.println(user);
            if (user != null) {
//                System.out.println(user.getUsername());
//                resp.getWriter().print(new Gson().toJson(R.ok("登录成功")));
                // TODO save user info
                session.setAttribute("user",user);

                // 抽象泄漏...
                R r = new R();
                r.put("user", user);
                r.put("msg","登陆成功");
                var ret = new Gson().toJson(r);
                resp.getWriter().print(ret);
            } else {
                resp.getWriter().print(new Gson().toJson(R.error("账号或密码错误")));
            }
        } else {
            resp.getWriter().print(new Gson().toJson(R.error("验证码错误，请重新输入")));
        }
    }
}
