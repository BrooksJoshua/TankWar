
import com.lee.tank.PropertyManager;
import com.lee.tank.ResourceMgr;
import com.lee.tank.TankFrame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.text.ParseException;


public class ImageLoader {


    @Test
    public void test() {

        try {
            // The path passed as the argument to getResourceAsStream() should be relative to the classpath set.
            //如果是简单的java项目, classpath是src, 如果是maven项目, 是resources,
            BufferedImage image = ImageIO.read(ImageLoader.class.getClassLoader().getResourceAsStream("images/0.gif"));
            assertNotNull(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2() {

        TankFrame tankFrame = new TankFrame();
        tankFrame.setVisible(true);
        tankFrame.setSize(800, 600);
        tankFrame.setResizable(true);
        tankFrame.setTitle("Tank War 2.0");
        tankFrame.setVisible(true);

        /*添加窗口监听, 方便关闭窗口, 否则只能通过停止程序才能关闭窗口*/
        tankFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX() + "......" + e.getY());
            }
        });
        try {
            new Thread().sleep(100000000);
        } catch (InterruptedException e) {

        }
    }
    @Test
    public void test3() throws ParseException, FileNotFoundException {
        int initialInvadorCount = (int)PropertyManager.get("initialInvadorCount_int");
        System.out.println(initialInvadorCount);
    }
    @Test
    public void test4(){
        class Singleton01{
            private Singleton01 instance=new Singleton01();
            private Singleton01(){}
            public Singleton01 getInstance(){return instance;}
            Proxy
        }

    }




}
