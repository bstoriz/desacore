package pstoriz.desacore.graphics;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	private String path1, path2;
	private Image transpImg;
	public final int SIZE;
	private BufferedImage[] m_images;
	private int width, length;
	public int pixels[];
		
	public ImageLoader(String path1, String path2, int width, int length)
	{
		this.width = width;
		this.length = length;
		this.path1 = path1;
		this.path2 = path2;
		SIZE = width * length;
		pixels = new int[length * width];
	    load();
	}
	
	public void load() {
		try {
			m_images = new BufferedImage[3];
		    m_images[0] = ImageIO.read(ImageLoader.class.getResource(path1));
		    m_images[1] = ImageIO.read(ImageLoader.class.getResource(path2));
		    transpImg = TransformGrayToTransparency(m_images[1]);
		    m_images[2] = ApplyTransparency(m_images[0], transpImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image getImage() {
		return m_images[2];
	}
	
	private Image TransformGrayToTransparency(BufferedImage image)
	{
	    ImageFilter filter = new RGBImageFilter()
	    {
	        public final int filterRGB(int x, int y, int rgb)
	        {
	            return (rgb << 8) & 0xFF000000;
	        }
	    };

	    ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(ip);
	}

	private BufferedImage ApplyTransparency(BufferedImage image, Image mask)
	{
	    BufferedImage dest = new BufferedImage(
	            image.getWidth(), image.getHeight(),
	            BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = dest.createGraphics();
	    g2.drawImage(image, 0, 0, null);
	    AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_IN, 1.0F);
	    g2.setComposite(ac);
	    g2.drawImage(mask, 0, 0, null);
	    g2.dispose();
	    return dest;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getLength() {
		return length;
	}

}
