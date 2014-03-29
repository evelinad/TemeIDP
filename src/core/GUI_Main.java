package core;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import buttons.PauseButton;
import buttons.ResumeButton;
import buttons.AddButton;
import buttons.StartButton;
import buttons.StopButton;
import radiobuttons.ReceiveRadioButton;
import radiobuttons.SendRadioButton;
import tables.P2PJTable;
import test.Test;

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
		final P2PJTable transferJTable;
		String col[] = {"Source","Destination","File Name", "Progress", "Status"};
		med = new Mediator();
		user_model = new DefaultListModel<>();
		files_model = new DefaultListModel<>();	
		med.setFilesModel(files_model);
		med.setUserModel(user_model);
		med.setCurrentUser("user1");
		transfer_model = new DefaultTableModel(col, 0);
		med.setTransferModel(transfer_model);
		/*transfer_model.addRow(new Object[]{"me","you","happy","20%","Sending..."} );*/
		int v=ScrollPaneConstants. VERTICAL_SCROLLBAR_AS_NEEDED;
		int h=ScrollPaneConstants. HORIZONTAL_SCROLLBAR_AS_NEEDED;	
		
		/*files_model.addElement("happy");*/
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
				med.updateTransferSelectedUser(userJList.getSelectedValue().toString());
				
			}
		};
		userJList.addMouseListener(userMouseListener);
		
		MouseListener fileMouseListener = new MouseListener() {
			
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
				System.out.println(fileJList.getSelectedIndex()+"baa");
				//med.updateTransferSelectedUser(//user);;//;(userJList.getSelectedValue().toString());
				med.setFileValue(fileJList.getSelectedValue().toString());
			}
		};
		fileJList.addMouseListener(fileMouseListener);
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
				fileJList.clearSelection();
				userJList.clearSelection();
				files_model.clear();
				((Command)e.getSource()).execute();
				
			}
		};
		
		SendRadioButton sendJRadioButton = new SendRadioButton("Send file",radioButtonListener,med);
		ReceiveRadioButton receiveJRadioButton = new ReceiveRadioButton("Receive file",radioButtonListener,med);
		
		JPanel operationJPanel = new JPanel(new GridLayout(0,2));
		operationJPanel.add(sendJRadioButton);
		operationJPanel.add(receiveJRadioButton);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(sendJRadioButton);
		buttonGroup.add(receiveJRadioButton);
		ActionListener buttonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO de inserat in JTable 
				fileJList.clearSelection();
				userJList.clearSelection();				
				((Command)e.getSource()).execute();
			}
		};
		JPanel fileJPanelLabels = new JPanel(new GridLayout(2,0));
		fileJPanelLabels.add(new JLabel("Hello "+currentUser));
		fileJPanelLabels.add(new JLabel("Select current operation"));
		
		JPanel fileJPanelLabels2 = new JPanel(new GridLayout(2,0));
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
		JPanel buttonsJPanel = new JPanel();
		transferJPanel.add(buttonsJPanel);
		AddButton addButton = new AddButton("Add",buttonListener,med,new ImageIcon(GUI_Main.class.getResource("/res/AddIcon.png")));
		StartButton startButton = new StartButton("Start",buttonListener,med,new ImageIcon(GUI_Main.class.getResource("/res/Play1Normal.png")));
		StopButton stopButton = new StopButton("Stop",buttonListener,med,new ImageIcon(GUI_Main.class.getResource("/res/Stop1NormalBlue.png")));
		PauseButton pauseButton = new PauseButton("Pause",buttonListener,med,new ImageIcon(GUI_Main.class.getResource("/res/Pause.png")));
		ResumeButton resumeButton = new ResumeButton("Resume",buttonListener,med,new ImageIcon(GUI_Main.class.getResource("/res/StepForwardNormalBlue.png")));
		buttonsJPanel.add(addButton);
		buttonsJPanel.add(startButton);
		buttonsJPanel.add(stopButton);
		buttonsJPanel.add(resumeButton);
		buttonsJPanel.add(pauseButton);
		transferJPanel.add(new JScrollPane(transferJTable,v,h));
		
		this.setVisible(true);
		
		transferJTable.setRowSelectionAllowed(true);
		transferJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		MouseListener tableMouseListener = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				med.setSelectedTransfer(transferJTable.getSelectedRow());
			}
		};
		transferJTable.addMouseListener(tableMouseListener);
		
		Test tester = new Test(med);
		tester.execute();
	}
	
	public static void main(String[] args) {
		new GUI_Main("Eu");
		  }

	

}
