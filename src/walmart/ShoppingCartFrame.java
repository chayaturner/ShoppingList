package walmart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class ShoppingCartFrame extends JFrame{
	
	private JList<Item>shoppingList;
	 private ArrayList<Item> items;
	 private JLabel displayTotal;
	 private Double totalPrice;
	 
	public ShoppingCartFrame(JList<Item>shoppingList, ArrayList<Item> items, Double totalPrice){
		setTitle("My Cart");
		setSize(600,400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		Font font = new Font("Berlin Sans FB", Font.BOLD, 20);
		Color lightBlue= new Color(173, 216, 230);
		
		this.items=items;
		this.totalPrice=totalPrice;
	
		JPanel north = new JPanel(new BorderLayout());
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		center.setBackground(lightBlue);
		
		add(center, BorderLayout.CENTER);
		add(north, BorderLayout.NORTH);

		JLabel logoLabel = new JLabel(new ImageIcon("./mediumLogo.png"));
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel shoppingCart=new JLabel();
		shoppingCart.setBackground(Color.WHITE);
		shoppingCart.setIcon(new ImageIcon("smallCart.png"));
		
		north.add(logoLabel, BorderLayout.CENTER);
		north.add(shoppingCart, BorderLayout.EAST);
		
		this.shoppingList=shoppingList;
		shoppingList.setForeground(Color.decode("#FF9C00"));
		shoppingList.setBackground(lightBlue);
		add(shoppingList);
					
		displayTotal = new JLabel();
		displayTotal.setFont(font);
		displayTotal.setForeground(Color.blue);
		displayTotal.setBackground(lightBlue);
		displayTotal.setText("            TOTAL : $0.0");
			
		JPanel bottomPanel=new JPanel(new BorderLayout());
		bottomPanel.setBackground(Color.decode("#FF9C00"));
		bottomPanel.add(displayTotal, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		setData();
	}

	private void setData() {
		Item[] temp=new Item[items.size()];
		items.toArray(temp);
		shoppingList.setListData(temp);
		displayTotal.setText("          TOTAL: $"
				+ String.format("%.2f", totalPrice));
		
	}

}
