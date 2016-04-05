package walmart;

import java.awt.BorderLayout;
import java.awt.Container;
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
import javax.swing.JTextArea;

public class ProductFrame extends JFrame {

	private JLabel imageLabel;
	private JTextArea area;
	private JLabel name;
	private JLabel desc;
	private JLabel price;
	private JLabel availableOnline;
	
	
	public ProductFrame(Item item) {

		setTitle("Walmart Search");
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		Container container = getContentPane();
		
		container.setLayout(new BorderLayout());

		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		
		

		add(center, BorderLayout.CENTER);
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
		center.add(imageLabel, BorderLayout.SOUTH);
		
		name = new JLabel();
		desc = new JLabel();
		price = new JLabel();
		availableOnline = new JLabel();
		
		name.setText(item.getName());
		
		desc.setText(item.getShortDescription());
		
		price.setText(String.format("%.2f",item.getSalePrice()));
		
		String available = "Available Online";
		String notAvailable = "Not Available Online";
		
		if(item.getAvailableOnline()){
			availableOnline.setText(available+ "\n");
		}
		else{
			availableOnline.setText(notAvailable+ "\n");
		}
		
		center.add(name, BorderLayout.NORTH);
		center.add(name);
		center.add(desc);
		center.add(price);
		center.add(availableOnline);
		
		area = new JTextArea();
		
		area.append(item.getName() + "\n");
		area.append(item.getShortDescription()+ "\n");
		area.append(String.format("%.2f",item.getSalePrice())+ "\n");
		
		
		if(item.getAvailableOnline()){
			area.append(available+ "\n");
		}
		else{
		area.append(notAvailable+ "\n");
		}
		
		//center.add(area, BorderLayout.CENTER);
		setVisible(true);
	}
}
