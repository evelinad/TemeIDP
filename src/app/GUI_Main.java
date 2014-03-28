package app;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import radiobuttons.ReceiveRadioButton;
import radiobuttons.SendRadioButton;

public class GUI_Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8118352433755008641L;
	private int defaultJFrameHeight = 500;
	private int defaultJFrameWidth = 700;
	private DefaultTableModel transfer_model;
	private DefaultListModel user_model;
	private DefaultListModel files_model;
	Mediator med;	
	
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
		final JList	userJList, fileJList;
		P2PJTable transferJTable;
		String col[] = {"Source","Destination","File Name", "Progress", "Status"};
		med = new Mediator();
		user_model = new DefaultListModel<>();
		files_model = new DefaultListModel<>();	
		med.setFilesModel(files_model);
		med.setUserModel(user_model);
		med.setCurrentUser("user1");
		transfer_model = new DefaultTableModel(col, 0);
		transfer_model.addRow(new Object[]{"me","you","happy","20%","Sending..."} );
		int v=ScrollPaneConstants. VERTICAL_SCROLLBAR_AS_NEEDED;
		int h=ScrollPaneConstants. HORIZONTAL_SCROLLBAR_AS_NEEDED;	
		
		files_model.addElement("happy");
		userJList	= new JList(user_model);
		fileJList	= new JList(files_model);
		transferJTable = new P2PJTable(transfer_model);

		
		MouseListener userMouseListener = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println(userJList.getSelectedIndex()+"baa");
				med.updateTransfer(userJList.getSelectedValue().toString());
				
			}
		};
		userJList.addMouseListener(userMouseListener);
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
		ActionListener radioButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				((Command)e.getSource()).execute();
				
			}
		};
		
		SendRadioButton sendJRadioButton = new SendRadioButton("Send file",radioButtonListener,med);
		ReceiveRadioButton receiveJRadioButton = new ReceiveRadioButton("Receive file",radioButtonListener,med);
		med.registerReceiveRadioButton(receiveJRadioButton);
		med.registerSendRadioButton(sendJRadioButton);
		
		JPanel operationJPanel = new JPanel(new GridLayout(0,2));
		operationJPanel.add(sendJRadioButton);
		operationJPanel.add(receiveJRadioButton);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(sendJRadioButton);
		buttonGroup.add(receiveJRadioButton);
		JButton addJButton = new JButton("Add new operation");
		ActionListener addButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO de inserat in JTable 
				
			}
		};
		addJButton.addActionListener(addButtonListener);
		JPanel fileJPanelLabels = new JPanel(new GridLayout(2,0));
		fileJPanelLabels.add(new JLabel("Hello "+currentUser));
		fileJPanelLabels.add(new JLabel("Select current operation"));
		
		JPanel fileJPanelLabels2 = new JPanel(new GridLayout(2,0));
		fileJPanelLabels2.add(addJButton);	
		fileJPanelLabels2.add(new JLabel("Available files"));
		fileJPanel.add(fileJPanelLabels);
		fileJPanel.add(operationJPanel);
		fileJPanel.add(fileJPanelLabels2);
		fileJPanel.add(new JScrollPane(fileJList,v,h));
		
		JLabel statusJLabel = new JLabel("Work in progress...");
		getContentPane().add(statusJLabel, BorderLayout.SOUTH);
		userJPanel.add(new JLabel("Users"));
		userJPanel.add(new JScrollPane(userJList,v,h));
		JPanel transferJPanelLabels = new JPanel(new GridLayout(1,1));
		transferJPanelLabels.add(new JLabel("Ongoing transfers"));				
		transferJPanel.add(transferJPanelLabels);
		//transferJPanel.add(new JScrollPane(transferJTable,v,h));
		
		
		JPanel buttonsJPanel = new JPanel();
		transferJPanel.add(buttonsJPanel);
		JButton startJButton = new JButton("Start");
		startJButton.setToolTipText("Start file transfer");
		JButton stopJButton = new JButton("Stop");
		stopJButton.setToolTipText("Stop transferring (Closes connection, download will start from scratch)");
		JButton pauseJButton = new JButton("Pause");
		pauseJButton.setToolTipText("Pause download maintaining connection");
		JButton resumeJButton = new JButton("Resume");
		resumeJButton.setToolTipText("Resume paused download");
		buttonsJPanel.add(startJButton);
		buttonsJPanel.add(stopJButton);
		buttonsJPanel.add(resumeJButton);
		buttonsJPanel.add(pauseJButton);
		transferJPanel.add(new JScrollPane(transferJTable,v,h));
		
		//PUSH THE BUTTON!
		ImageIcon myIcon = new ImageIcon(GUI_Main.class.getResource("/app/Play1Normal.png"));
		Image myImage = myIcon.getImage();
		Image scaledImage = myImage.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
		startJButton.setIcon(new ImageIcon(scaledImage));
		myIcon = new ImageIcon(GUI_Main.class.getResource("/app/Stop1NormalBlue.png"));
		myImage = myIcon.getImage();
		scaledImage = myImage.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
		stopJButton.setIcon(new ImageIcon(scaledImage));
		myIcon = new ImageIcon(GUI_Main.class.getResource("/app/StepForwardNormalBlue.png"));
		myImage = myIcon.getImage();
		scaledImage = myImage.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
		resumeJButton.setIcon(new ImageIcon(scaledImage));
		myIcon = new ImageIcon(GUI_Main.class.getResource("/app/Pause.png"));
		myImage = myIcon.getImage();
		scaledImage = myImage.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
		pauseJButton.setIcon(new ImageIcon(scaledImage));
		
		this.setVisible(true);
		
		transferJTable.setRowSelectionAllowed(true);
	}
	
	public static void main(String[] args) {
		new GUI_Main("Eu");
		  }

	

}
