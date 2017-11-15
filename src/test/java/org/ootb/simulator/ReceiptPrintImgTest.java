package org.ootb.simulator;

import gui.ava.html.image.generator.HtmlImageGenerator;
import org.junit.Test;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by jiazhao on 11/13/17.
 */
public class ReceiptPrintImgTest {

    @Test
    public void html2ImageTest() throws Exception{
        String uuid = "1510553280030059550501254588194";
        String server = "http://localhost:8080/orders/";
        String url = server + uuid + "/receipt/print";

        HtmlImageGenerator generator = new HtmlImageGenerator();
        generator.loadUrl(new URL(url));
        generator.saveAsImage(uuid + ".png");
        generator.saveAsHtmlWithMap(uuid + ".html",uuid + ".png");
    }

    @Test
    public void testDemoCode() throws Exception{
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();

//        imageGenerator.loadHtml("<b>Hello World!</b> Please goto <a title=\"Goto Google\" href=\"http://www.google.com\">Google</a>.");
        String content = new String(Files.readAllBytes(Paths.get("demo.htm")));
        imageGenerator.loadHtml(content);

        imageGenerator.setSize(new Dimension(1400,900));
        imageGenerator.saveAsImage("hello-world.png");

        imageGenerator.saveAsHtmlWithMap("hello-world.html", "hello-world.png");
    }

    @Test
    public void testSaveURL2Image() throws Exception{

        JEditorPane ed = new JEditorPane(new URL("http://www.google.com"));
        ed.setSize(1400,900);

        //create a new image
        BufferedImage image = new BufferedImage(ed.getWidth(), ed.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        //paint the editor onto the image
        SwingUtilities.paintComponent(image.createGraphics(),
                ed,
                new JPanel(),
                0, 0, image.getWidth(), image.getHeight());
        //save the image to file
        ImageIO.write((RenderedImage)image, "png", new File("html.png"));
    }

    @Test
    public void testSave4() throws Exception{
        String content = new String(Files.readAllBytes(Paths.get("demo.htm")));
        int width = 900, height = 1800;

        BufferedImage image = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDefaultConfiguration()
                .createCompatibleImage(width, height);

        Graphics graphics = image.createGraphics();

        JEditorPane jep = new JEditorPane("text/html", content);
        jep.setSize(width, height);
        jep.print(graphics);

        ImageIO.write(image, "png", new File("Image.png"));
    }

    @Test
    public void testFlyingSaucerPdf() throws Exception{
        String content = new String(Files.readAllBytes(Paths.get("print.htm")));
        ITextRenderer render = new ITextRenderer();
        render.setDocumentFromString(content);
        render.layout();

        FileOutputStream fos = new FileOutputStream(new File("img5.pdf"));
        render.createPDF(fos);

        fos.close();
    }

    @Test
    public void testCreatePng() throws Exception{
        String url = "http://localhost:8080/orders/1510553280030059550501254588194/receipt/print/";
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
