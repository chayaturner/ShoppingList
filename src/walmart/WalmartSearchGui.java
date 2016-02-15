package walmart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class WalmartSearchGui extends JFrame {

	private static final long serialVersionUID = 1L; // default

	private JLabel search, logoLabel;
	private JList<String> resultsList;
	private DefaultListModel<String> listModel;
	private JButton searchButton;
	private JTextField searchInput;
	private JPanel topPanel, searchPanel;
	private LineBorder border;
	private ImageIcon logo;

	public WalmartSearchGui() {
		setTitle("Walmart Search");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		Container container = getContentPane();
		setLayout(new BorderLayout());

		Color wmBlue = new Color(65, 105, 250);
		Color wmOrange = new Color(240, 160, 0);
		Color lightBlue = new Color(173, 216, 230);
		border = new LineBorder(Color.BLACK);

		// CENTER
		listModel = new DefaultListModel<String>();
		resultsList = new JList<String>(listModel);
		resultsList.setForeground(new Color(0, 0, 139));
		resultsList.setBackground(lightBlue);
		resultsList.setBorder(border);
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setLayoutOrientation(JList.VERTICAL);
		resultsList.setVisibleRowCount(-1);
		container.add(resultsList, BorderLayout.CENTER);

		// NORTH
		topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		search = new JLabel("Search Walmart.com:");
		search.setForeground(wmOrange);
		searchInput = new JTextField("                                   ");
		searchInput.setBorder(new LineBorder(wmBlue));
		searchInput.setForeground(wmBlue);
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

		searchButton.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// create thread to do the search, and display the list of
				// results
				SearchThread thread = new SearchThread(searchInput, resultsList, listModel);
				thread.start();

			}
		});

		topPanel.add(logoLabel);
		topPanel.add(searchPanel);
		container.add(topPanel, BorderLayout.NORTH);

	}

	public static void main(String[] args) {
		WalmartSearchGui walmartGui = new WalmartSearchGui();
		walmartGui.setVisible(true);
	}
}