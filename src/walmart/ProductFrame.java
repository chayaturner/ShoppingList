package walmart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProductFrame extends JFrame {

	private JLabel imageLabel;
	private JLabel name;
	private JLabel desc;
	private JLabel price;
	private JLabel availableOnline;
	private JLabel empty;

	public ProductFrame(Item item) {

		setTitle("PRODUCT");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		Container container = getContentPane();
		container.setLayout(new BorderLayout());

		Font font = new Font("Berlin Sans FB", Font.PLAIN, 20);

		JPanel north = new JPanel(new BorderLayout());
		north.setBackground(Color.WHITE);
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		center.setBackground(new Color(173, 216, 230));

		add(center, BorderLayout.CENTER);
		add(north, BorderLayout.NORTH);

		URL imageURL = null;
		Image image = null;
		try {
			imageURL = new URL(item.getThumbnailImage());
			image = ImageIO.read(imageURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageIcon icon = new ImageIcon(image);
		imageLabel = new JLabel(icon);
		imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel logoLabel = new JLabel(new ImageIcon("./smallLogo.png"));
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		north.add(logoLabel);

		// empty label is used to make a nice space between logo and image
		empty = new JLabel();
		empty.setAlignmentX(Component.CENTER_ALIGNMENT);
		empty.setPreferredSize(new Dimension(100, 20));
		empty.setText(" ");

		name = new JLabel();
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		name.setForeground(Color.decode("#FF9C00"));
		name.setFont(font);
		name.setText(item.getName());

		desc = new JLabel();
		desc.setAlignmentX(Component.CENTER_ALIGNMENT);
		desc.setForeground(Color.decode("#FF9C00"));
		desc.setFont(font);
		desc.setText(item.getShortDescription());

		price = new JLabel();
		price.setAlignmentX(Component.CENTER_ALIGNMENT);
		price.setForeground(Color.decode("#003399"));
		price.setFont(new Font("Berlin Sans FB", Font.PLAIN, 28));
		price.setText(String.format("$%.2f", item.getSalePrice()));

		availableOnline = new JLabel();
		availableOnline.setAlignmentX(Component.CENTER_ALIGNMENT);
		availableOnline.setForeground(Color.decode("#FF9C00"));
		availableOnline.setFont(font);

		String available = "Available Online";
		String notAvailable = "Not Available Online";

		if (item.getAvailableOnline()) {
			availableOnline.setText(available + "\n");
		} else {
			availableOnline.setText(notAvailable + "\n");
		}

		center.add(empty);
		center.add(imageLabel);
		center.add(name);
		center.add(desc);
		center.add(price, BorderLayout.SOUTH);
		center.add(availableOnline);

		setVisible(true);
	}
}
