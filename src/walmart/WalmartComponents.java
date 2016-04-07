package walmart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class WalmartComponents extends Container {

	private JLabel search, logoLabel;
	private JList<Item> resultsList;
	private JList<Item> shoppingList;
	private DefaultListModel<Item> shoppingListModel;
	private JButton searchButton, addButton;
	private JTextField searchInput;
	private JPanel topPanel, bottomPanel, centerPanel, searchPanel,
			productDetails, resultsPanel;
	private LineBorder border;
	private ImageIcon logo;
	private Item[] items;
	private SearchThread thread;
	private Double totalPrice;
	private JLabel displayTotal;

	@Inject
	public WalmartComponents() {
		Font font = new Font("Arial", Font.BOLD, 16);
		Color wmBlue = new Color(65, 105, 250);
		Color wmOrange = new Color(240, 160, 0);
		Color lightBlue = new Color(173, 216, 230);
		border = new LineBorder(Color.BLACK);

		setLayout(new BorderLayout());

		// CENTER
		this.totalPrice = 0.0;
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.LINE_AXIS));
		centerPanel.setBackground(lightBlue);

		shoppingListModel = new DefaultListModel<Item>();
		shoppingList = new JList<Item>(shoppingListModel);
		shoppingList.setForeground(wmBlue);
		shoppingList.setBackground(new Color(255, 231, 186));
		shoppingList.setPreferredSize(new Dimension(200, Short.MAX_VALUE));
		shoppingList.setMaximumSize(new Dimension(200, Short.MAX_VALUE));
		shoppingList.setBorder(border);
		shoppingList.setFont(font);
		centerPanel.add(shoppingList);
		resultsPanel = new JPanel(new GridLayout(2, 1));
		resultsPanel.setBackground(lightBlue);
		resultsList = new JList<Item>();
		resultsList.setForeground(new Color(0, 0, 139));
		resultsList.setBackground(lightBlue);
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setLayoutOrientation(JList.VERTICAL);
		resultsList.setVisibleRowCount(-1);
		resultsList.setFont(font);
		productDetails = new JPanel(new GridLayout(4, 1));
		productDetails.setBackground(lightBlue);
		resultsPanel.add(resultsList);
		resultsPanel.add(productDetails);
		centerPanel.add(resultsPanel);

		add(centerPanel, BorderLayout.CENTER);

		// NORTH
		topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		search = new JLabel("Search Walmart.com:");
		search.setForeground(wmOrange);
		searchInput = new JTextField("                                   ");
		searchInput.setFont(font);
		searchInput.setBorder(new LineBorder(wmBlue));
		searchInput.setForeground(wmBlue);

		addEnterKeyListener();

		logo = new ImageIcon("logo.png");
		logoLabel = new JLabel(logo);
		searchButton = new JButton("Search");
		searchButton.setForeground(wmOrange);
		searchButton.setSize(50, 30);
		searchPanel = new JPanel(new GridLayout(3, 1));
		searchPanel.setBackground(Color.WHITE);
		searchPanel.add(search);
		searchPanel.add(searchInput);
		searchPanel.add(searchButton);

		AddSearchActionListener();

		topPanel.add(logoLabel);
		topPanel.add(searchPanel);
		add(topPanel, BorderLayout.NORTH);

		// SOUTH
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBackground(lightBlue);
		add(bottomPanel, BorderLayout.SOUTH);
		addButton = new JButton("Like it? Add to shopping list!");
		addButton.setForeground(Color.BLUE);
		addButton.setBackground(wmOrange);

		addButton.setFont(new Font("Arial", Font.BOLD, 21));
		addButton.setMaximumSize(new Dimension(300, 35));
		displayTotal = new JLabel();

		displayTotal.setFont(font);
		displayTotal.setForeground(Color.blue);
		displayTotal.setText("            TOTAL : $0.0");
		bottomPanel.add(displayTotal, BorderLayout.NORTH);
		bottomPanel.add(addButton, BorderLayout.CENTER);

		AddItemActionListener();

		MouseListener mouseListener = new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int index = resultsList.locationToIndex(e.getPoint());

					items = thread.getItems();
					System.out.println(items[index]);
					new ProductFrame(items[index]).setVisible(true);
				}
			}
		};

		resultsList.addMouseListener(mouseListener);

		MouseListener mouseListenerList = new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int index = shoppingList.locationToIndex(e.getPoint());

					items = thread.getItems();
					System.out.println(items[index]);
					new ProductFrame(items[index]).setVisible(true);
				}
			}
		};
		shoppingList.addMouseListener(mouseListenerList);
	}

	private void AddItemActionListener() {
		addButton.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				// add to the shopping list
				Item selected = resultsList.getSelectedValue();
				shoppingListModel.addElement(selected);
				totalPrice += selected.getSalePrice();
				displayTotal.setText("          TOTAL: $"
						+ String.format("%.2f", totalPrice));

			}

		});
	}

	// user can click on button
	private void AddSearchActionListener() {
		searchButton.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				// reset old searches
				resultsList.clearSelection();

				// get a new search result list, set items.
				thread = new SearchThread(searchInput, resultsList, items);
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
					thread = new SearchThread(searchInput, resultsList, items);
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
}
