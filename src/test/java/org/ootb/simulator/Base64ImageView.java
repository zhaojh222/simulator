package org.ootb.simulator;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by jiazhao on 11/13/17.
 */
public class Base64ImageView extends ImageView {

    private URL url;

    public Base64ImageView(Element elem) {
        super(elem);

        populateImage();
    }

    private void populateImage() {
        Dictionary<URL, Image> cache = (Dictionary<URL, Image>) getDocument()
                .getProperty("imageCache");
        if (cache == null) {
            cache = new Hashtable<URL, Image>();
            getDocument().putProperty("imageCache", cache);
        }

        URL src = getImageURL();
        cache.put(src, loadImage());

    }

    private Image loadImage() {
        String b64 = getBASE64Image();
        BufferedImage newImage = null;
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(
                    Base64.decodeBase64(b64.getBytes()));
            newImage = ImageIO.read(bais);
        } catch (Throwable ex) {
        }
        return newImage;
    }

    private String getBASE64Image() {
        String src = (String) getElement().getAttributes()
                .getAttribute(HTML.Attribute.SRC);
        if (!isBase64Encoded(src)) {
            return null;
        }
        return src.substring(src.indexOf("base64,") + 7, src.length() - 1);
    }

    @Override
    public URL getImageURL() {
        String src = (String) getElement().getAttributes()
                .getAttribute(HTML.Attribute.SRC);
        if (isBase64Encoded(src)) {

            this.url = Base64ImageView.class.getProtectionDomain()
                    .getCodeSource().getLocation();

            return this.url;
        }
        return super.getImageURL();
    }

    private boolean isBase64Encoded(String src) {
        return src != null && src.contains("base64,");
    }

}
