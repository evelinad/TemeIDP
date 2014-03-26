package GUI;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class GUI_Main extends JFrame  {
	private JTable transfers;
	private DefaultTableModel table_model;
	private DefaultListModel user_model;
	private DefaultListModel files_model;
	private String col[] = {"Source","Destination","File Name", "Progress", "Status"};
	public GUI_Main() {
		super("P2P File Transfer");
		this.setSize(700, 500);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {497, 224};
		gridBagLayout.rowHeights = new int[] {233, 212, 20};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JList files = new JList();
		GridBagConstraints gbc_files = new GridBagConstraints();
		gbc_files.insets = new Insets(0, 0, 5, 5);
		gbc_files.fill = GridBagConstraints.BOTH;
		gbc_files.gridx = 0;
		gbc_files.gridy = 0;
		getContentPane().add(files, gbc_files);
		
		JList users = new JList();
		GridBagConstraints gbc_users = new GridBagConstraints();
		gbc_users.gridheight = 2;
		gbc_users.insets = new Insets(0, 0, 5, 0);
		gbc_users.fill = GridBagConstraints.BOTH;
		gbc_users.gridx = 1;
		gbc_users.gridy = 0;
		getContentPane().add(users, gbc_users);
		
		table_model = new DefaultTableModel(col, 0);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
				
		transfers = new JTable(table_model);
		table_model.addRow(new Object[]{"me","you","happy","20%","Sending..."} );
		
		TableColumn col_aux = transfers.getColumnModel().getColumn(3);
		col_aux.setCellRenderer(new ProgressCellRenderer());

		JScrollPane scrollPane = new JScrollPane(transfers);
		panel.add(scrollPane);
		

		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		  }

}
