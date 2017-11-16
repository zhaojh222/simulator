package org.ootb.simulator;

import com.trolltech.qt.core.QFile;
import com.trolltech.qt.core.QIODevice;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by jiazhao on 11/13/17.
 */
public class ReceiptPrintImgTest {

    @Test
    public void testWebKit2Png() throws Exception{
        final WebkitRenderer renderer = new WebkitRenderer();
        final QFile qFile = new QFile("img1.png");
        qFile.open(QIODevice.OpenModeFlag.WriteOnly, QIODevice.OpenModeFlag.Truncate);
        String url = "https://api-devshowcase.selling.gapinc.dev/orders/151070626897305955060131201149001/receipt/print";

        renderer.renderToFile(url, qFile);
        qFile.close();
    }

    @Test
    public void testCreatePng() throws Exception{
        String url = "https://api-devshowcase.selling.gapinc.dev/orders/151070626897305955060131201149001/receipt/print";
        int width = 600;
        int height = 800;

        BufferedImage image = create(url,width,height);
        ImageIO.write(image, "png", new File("img6.png"));
    }

    public static BufferedImage create(String src, int width, int height) throws MalformedURLException {
        BufferedImage image = null;
        JEditorPane pane = new JEditorPane();
        Kit kit = new Kit();
        pane.setEditorKit(kit);
        pane.setEditable(false);
        pane.setMargin(new Insets(0,0,0,0));
        try {
            pane.setPage(src);
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            Container c = new Container();
            SwingUtilities.paintComponent
                    (g, pane, c, 0, 0, width, height);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e);
        }
        return image;
    }
}
