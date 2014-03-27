package GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class GUI_Main extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8118352433755008641L;
	private JTable transfers;
	private DefaultTableModel table_model;
	private DefaultListModel user_model = new DefaultListModel<>();
	private DefaultListModel files_model = new DefaultListModel<>();
	private String col[] = {"Source","Destination","File Name", "Progress", "Status"};
	
	
	public GUI_Main() {
		super("P2P File Transfer");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		user_model.addElement("me");
		user_model.addElement("you");
		files_model.addElement("happy");
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {497, 224};
		gridBagLayout.rowHeights = new int[] {233, 212, 20};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JList files = new JList(files_model);
		GridBagConstraints gbc_files = new GridBagConstraints();
		gbc_files.insets = new Insets(0, 0, 5, 5);
		gbc_files.fill = GridBagConstraints.BOTH;
		gbc_files.gridx = 0;
		gbc_files.gridy = 0;
		getContentPane().add(files, gbc_files);
		
		JList users = new JList(user_model);
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
		
		JLabel lblWorkInProgress = new JLabel("Work in progress...");
		GridBagConstraints gbc_lblWorkInProgress = new GridBagConstraints();
		gbc_lblWorkInProgress.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblWorkInProgress.gridwidth = 2;
		gbc_lblWorkInProgress.insets = new Insets(0, 0, 0, 5);
		gbc_lblWorkInProgress.gridx = 0;
		gbc_lblWorkInProgress.gridy = 2;
		getContentPane().add(lblWorkInProgress, gbc_lblWorkInProgress);
		

		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI_Main();
		  }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
