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
	private int defaultJFrameHeight = 500;
	private int defaultJFrameWidth = 700;
	private DefaultTableModel transfer_model;
	private DefaultListModel user_model;
	private DefaultListModel files_model;
	
	
	public GUI_Main(String currentUser) {
		super("P2P File Transfer");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		JList	userJList, fileJList,transferJList;
		P2PJTable transferJTable;
		String col[] = {"Source","Destination","File Name", "Progress", "Status"};
		user_model = new DefaultListModel<>();
		files_model = new DefaultListModel<>();		
		transfer_model = new DefaultTableModel(col, 0);
		transfer_model.addRow(new Object[]{"me","you","happy","20%","Sending..."} );
		int v=ScrollPaneConstants. VERTICAL_SCROLLBAR_AS_NEEDED;
		int h=ScrollPaneConstants. HORIZONTAL_SCROLLBAR_AS_NEEDED;	
		
		user_model.addElement("me");
		user_model.addElement("you");
		files_model.addElement("happy");
		userJList	= new JList(user_model);
		fileJList	= new JList(files_model);
		transferJTable = new P2PJTable(transfer_model);

		TableColumn col_aux = transferJTable.getColumnModel().getColumn(3);
		col_aux.setCellRenderer(new ProgressCellRenderer());
		
		JPanel fileJPanel = new JPanel();
		JPanel transferJPanel = new JPanel();
		JPanel userJPanel = new JPanel();
		fileJPanel.setLayout(new BoxLayout(fileJPanel, BoxLayout.PAGE_AXIS)); // 1 row, any number of columns
		transferJPanel.setLayout(new BoxLayout(transferJPanel, BoxLayout.PAGE_AXIS));
		userJPanel.setLayout(new BoxLayout(userJPanel, BoxLayout.PAGE_AXIS));
		fileJPanel.setMinimumSize(new Dimension(defaultJFrameWidth*3/4, defaultJFrameHeight/2-30));
		transferJPanel.setMinimumSize(new Dimension(defaultJFrameWidth*3/4, defaultJFrameHeight/2-30));
		userJPanel.setMinimumSize(new Dimension(defaultJFrameWidth/4-30,defaultJFrameHeight));
		JSplitPane vertJSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,fileJPanel,transferJPanel);
		JSplitPane horizJSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, vertJSplitPane, userJPanel);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(horizJSplitPane,BorderLayout.CENTER);
		JRadioButton sendJRadioButton = new JRadioButton("Send file");
		JRadioButton receiveJRadioButton = new JRadioButton("Receive file");
		sendJRadioButton.setSelected(true);
		JPanel operationJPanel = new JPanel(new GridLayout(0,2));
		operationJPanel.add(sendJRadioButton);
		operationJPanel.add(receiveJRadioButton);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(sendJRadioButton);
		buttonGroup.add(receiveJRadioButton);
		sendJRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("ma ta");
				
			}
		});
		receiveJRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("ma ta");
			}
		});
		JPanel fileJPanelLabels = new JPanel(new GridLayout(2,0));
		fileJPanelLabels.add(new JLabel("Hello "+currentUser));
		fileJPanelLabels.add(new JLabel("Select current operation"));
		JPanel fileJPanelLabels2 = new JPanel(new GridLayout(1,0));
		fileJPanelLabels2.add(new JLabel("Available files"));
		fileJPanel.add(fileJPanelLabels);
		fileJPanel.add(operationJPanel);
		fileJPanel.add(fileJPanelLabels2);
		fileJPanel.add(new JScrollPane(fileJList,v,h));
		userJPanel.add(new JLabel("Users"));
		userJPanel.add(new JScrollPane(userJList,v,h));
		JPanel transferJPanelLabels = new JPanel(new GridLayout(1,1));
		transferJPanelLabels.add(new JLabel("Ongoing transfers"));				
		transferJPanel.add(transferJPanelLabels);
		transferJPanel.add(new JScrollPane(transferJTable,v,h));
		
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI_Main("Eu");
		  }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
