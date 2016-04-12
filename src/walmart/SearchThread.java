package walmart;

import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

public class SearchThread extends Thread {

	private JTextField searchInput;
	private JList<Item> listModel;
	private Item[] items;
	private JLabel defaultLabel;

	public SearchThread(JTextField searchInput, JList<Item> listModel,
			Item[] items, JLabel defaultLabel) {
		this.searchInput = searchInput;
		this.listModel = listModel;
		this.items = items;
		this.defaultLabel = defaultLabel;
	}

	@Override
	public void run() {
		try {

			listModel.clearSelection(); // refresh list for new searches

			WalmartConnection connection = new WalmartConnection();
			SearchResults results = connection
					.createWalmartConnection(searchInput.getText().trim());
			items = results.getItems();
			defaultLabel.setText("");
			if (items != null) {
				listModel.setListData(items);
			} else {
				searchInput.setText("INVALID ENTRY ");

			}
		} catch (NullPointerException ex2) {
			searchInput.setText("Renenter Search");

		} catch (IOException ex1) {
			searchInput.setText("");
		}

	}

	public Item[] getItems() {
		return this.items;
	}
}