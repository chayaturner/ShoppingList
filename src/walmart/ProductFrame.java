package walmart;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProductFrame extends JFrame {

	private JLabel imageLabel;

	public ProductFrame(Item item) {

		setTitle("Walmart Search");
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());

		JPanel center = new JPanel(new BorderLayout());

		URL imageURL = null;
		Image image = null;
		try {
			imageURL = new URL(item.getMediumImage());
			image = ImageIO.read(imageURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(image);
		imageLabel = new JLabel(icon);
		center.add(imageLabel);
		setVisible(true);
	}
}
