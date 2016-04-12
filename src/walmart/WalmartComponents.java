package walmart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class WalmartComponents extends Container {

	private JLabel logoLabel, shoppingCart, defaultLabel, totalLabel;
	private JList<Item> resultsList;
	private JList<Item> shoppingList;
	private ArrayList<Item> itemsInCart;
	private JButton searchButton, addButton;
	private JTextField searchInput;
	private JPanel topPanel, bottomPanel, searchPanel;
	private BackgroundPanel centerPanel;
	private ImageIcon logo;
	private Item[] items;
	private SearchThread thread;
	private double totalPrice = 0.0;
	private int numInCart = 0; // default to zero
	private WalmartComponents components;

	@Inject
	public WalmartComponents() {
		Font font = new Font("Berlin Sans FB", Font.PLAIN, 20);
		Color wmBlue = new Color(65, 105, 250);
		Color wmOrange = new Color(240, 160, 0);
		Color lightBlue = new Color(173, 216, 230);
		Color darkBlue = new Color(0, 0, 139);

		setLayout(new BorderLayout());

		this.components = this;
		this.itemsInCart = new ArrayList<Item>();

		centerPanel = new BackgroundPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBackground(lightBlue);

		shoppingList = new JList<Item>();
		shoppingList.setFont(font);

		resultsList = new JList<Item>();
		resultsList.setForeground(Color.WHITE);
		resultsList.setOpaque(false);
		((javax.swing.DefaultListCellRenderer) resultsList.getCellRenderer())
				.setOpaque(false);

		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setLayoutOrientation(JList.VERTICAL);
		resultsList.setFont(font);
		MouseListener mouseListener = resultsMouseListener();
		resultsList.addMouseListener(mouseListener);

		centerPanel.add(resultsList, BorderLayout.NORTH);
		add(centerPanel);

		JScrollPane pane = new JScrollPane(centerPanel);
		pane.setOpaque(false);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(pane);

		topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 3));
		topPanel.setBackground(Color.WHITE);

		searchInput = new JTextField("", 10);
		searchInput.setFont(font);
		searchInput.setHorizontalAlignment(JTextField.CENTER);
		searchInput.setBorder(new LineBorder(wmBlue));
		searchInput.setForeground(wmBlue);
		addEnterKeyListener();
		addFocusListener();

		logo = new ImageIcon("logo.png");
		logoLabel = new JLabel(logo);

		searchPanel = new JPanel(new GridLayout(3, 1));
		searchPanel.setBackground(Color.WHITE);

		searchButton = new JButton("Search");
		searchButton.setForeground(wmOrange);
		searchButton.setSize(50, 30);
		addSearchActionListener();

		searchPanel.add(searchInput);
		searchPanel.add(searchButton);

		shoppingCart = new JLabel();
		shoppingCart.setIcon(new ImageIcon("cart.png"));
		shoppingCartClick();

		defaultLabel = new JLabel("Results will display here.");
		defaultLabel.setFont(new Font("Arial", Font.BOLD, 50));
		defaultLabel.setForeground(Color.ORANGE);
		defaultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(defaultLabel);

		totalLabel = new JLabel();
		totalLabel.setFont(new Font("Arial", Font.BOLD, 25));
		totalLabel.setForeground(darkBlue);

		topPanel.add(logoLabel);
		topPanel.add(searchPanel);
		topPanel.add(shoppingCart);
		topPanel.add(totalLabel);
		add(topPanel, BorderLayout.NORTH);

		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBackground(lightBlue);

		addButton = new JButton("Like it? Add to shopping cart!");
		addButton.setForeground(Color.BLUE);
		addButton.setBackground(wmOrange);
		addButton.setFont(new Font("Arial", Font.BOLD, 21));
		addButton.setMaximumSize(new Dimension(300, 35));
		addItemActionListener();

		bottomPanel.add(addButton, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

	}

	public void setValues(int num, double price) {
		this.numInCart = num;
		this.totalPrice = price;
		this.totalLabel.setText("" + numInCart);

	}

	private MouseListener resultsMouseListener() {
		MouseListener mouseListener = new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int index = resultsList.locationToIndex(e.getPoint());

					items = thread.getItems();
					new ProductFrame(items[index]).setVisible(true);
				}
			}
		};
		return mouseListener;
	}

	private void shoppingCartClick() {
		shoppingCart.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				new ShoppingCartFrame(shoppingList, itemsInCart, totalPrice,
						numInCart, components).setVisible(true);
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

	private void addItemActionListener() {
		addButton.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				// add to the shopping list
				numInCart++;
				totalLabel.setText("" + numInCart);
				Item selected = resultsList.getSelectedValue();
				itemsInCart.add(selected);
				totalPrice += selected.getSalePrice();
				}
			

		});
	}

	// user can click on button
	private void addSearchActionListener() {
		searchButton.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				// reset old searches
				resultsList.clearSelection();

				// get a new search result list, set items.
				thread = new SearchThread(searchInput, resultsList, items,
						defaultLabel);
				thread.start();

			}
		});
	}

	// or press enter
	private void addEnterKeyListener() {
		searchInput.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					resultsList.clearSelection();

					// get a new search result list, set items.
					thread = new SearchThread(searchInput, resultsList, items,
							defaultLabel);
					thread.start();

				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

		}

		);
	}

	private void addFocusListener() {
		searchInput.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				searchInput.setText("");

			}

			@Override
			public void focusLost(FocusEvent arg0) {
			}

		});
	}
}
