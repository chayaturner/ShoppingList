package walmart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ShoppingCartFrame extends JFrame {

	private JList<Item> shoppingList;
	private ArrayList<Item> items;
	private JLabel displayTotal, numInCart, remove;
	private double totalPrice;
	private int num;
	private WalmartComponents components;
	
	public ShoppingCartFrame(JList<Item> shoppingList, ArrayList<Item> items,
			double totalPrice, int num,  WalmartComponents components) {
		
		this.shoppingList = shoppingList;
		this.items = items;
		this.totalPrice = totalPrice;
		this.num = num;
		this.components=components;
		
		setTitle("My Cart");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		instantiateItems();
		setData(num, totalPrice);
	}

	private void instantiateItems(){
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		Font font = new Font("Berlin Sans FB", Font.BOLD, 20);
		Color lightBlue = new Color(173, 216, 230);
		
		JPanel north = new JPanel(new BorderLayout());
		north.setBackground(Color.WHITE);
		
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		center.setBackground(lightBlue);

		add(center, BorderLayout.CENTER);
		add(north, BorderLayout.NORTH);

		JLabel logoLabel = new JLabel(new ImageIcon("./mediumLogo.png"));
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel shoppingCart = new JLabel();
		shoppingCart.setBackground(Color.WHITE);
		shoppingCart.setIcon(new ImageIcon("smallCart.png"));
		
		numInCart = new JLabel();
		numInCart.setText("" + num);
		numInCart.setFont(new Font("Arial", Font.BOLD, 20));
		numInCart.setForeground(new Color(0, 0, 139));
		

		north.add(logoLabel, BorderLayout.WEST);
		north.add(shoppingCart, BorderLayout.CENTER);
		north.add(numInCart, BorderLayout.EAST);

		shoppingList.setForeground(Color.decode("#FF9C00"));
		shoppingList.setBackground(lightBlue);
		shoppingCartListener();
		add(shoppingList);

		displayTotal = new JLabel();
		displayTotal.setFont(font);
		displayTotal.setForeground(Color.blue);
		displayTotal.setBackground(lightBlue);
		displayTotal.setText("            TOTAL : $0.0");

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBackground(Color.decode("#FF9C00"));

		
		remove=new JLabel("Remove Item");
		remove.setFont(font);
		remove.setForeground(Color.blue);
		remove.setBackground(lightBlue);
		RemoveItemMouseListener();
		bottomPanel.add(remove, BorderLayout.EAST);
		bottomPanel.add(displayTotal, BorderLayout.WEST);
		add(bottomPanel, BorderLayout.SOUTH);

	}

	private void shoppingCartListener() {
		shoppingList.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int index = shoppingList.locationToIndex(e.getPoint());
					new ProductFrame(items.get(index)).setVisible(true);				
			}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
								
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
		
		});
	}
	
	

	private void RemoveItemMouseListener() {
		remove.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(!items.isEmpty()){
				int removeItem = JOptionPane.showConfirmDialog(null, 
						   "Are you sure you want to remove this item from your cart?",null, JOptionPane.YES_NO_OPTION);

				if (removeItem == JOptionPane.YES_OPTION) {

					Item selected = shoppingList.getSelectedValue();
					items.remove(selected);
					totalPrice-=selected.getSalePrice();
					num--;
					setData(num , totalPrice);
					components.setValues(num, totalPrice);

				} else {
					JOptionPane.getRootFrame().dispose(); 
			
			}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
			
		});
	}
	private void setData(int num, double totalPrice) {
		Item[] temp = new Item[items.size()];
		items.toArray(temp);
		shoppingList.setListData(temp);
		displayTotal.setText("          TOTAL: $"
				+ String.format("%.2f", totalPrice));
		numInCart.setText(""+num);
		
	
		
	}

}
