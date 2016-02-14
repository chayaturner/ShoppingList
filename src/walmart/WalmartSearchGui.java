package walmart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
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

	private JLabel search;
	private JList<String> resultsList;
	private DefaultListModel<String> listModel;
	private JButton searchButton;
	private JTextField searchInput;
	private JPanel searchPanel;
	private LineBorder border;

	public WalmartSearchGui() {
		setTitle("Walmart Search");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		Container container = getContentPane();
		setLayout(new BorderLayout());

		border = new LineBorder(Color.BLACK);

		// CENTER
		listModel = new DefaultListModel<String>();
		resultsList = new JList<String>(listModel);
		resultsList.setBorder(border);
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setLayoutOrientation(JList.VERTICAL);
		resultsList.setVisibleRowCount(-1);
		container.add(resultsList, BorderLayout.CENTER);

		// NORTH
		searchPanel = new JPanel();
		search = new JLabel("Search Walmart.com :");
		searchInput = new JTextField("                                  ");
		searchButton = new JButton("Search");
		searchButton.setSize(50, 30);
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

		container.add(searchPanel, BorderLayout.NORTH);

	}

	public static void main(String[] args) {
		WalmartSearchGui walmartGui = new WalmartSearchGui();
		walmartGui.setVisible(true);
	}
}