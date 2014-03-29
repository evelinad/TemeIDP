package radiobuttons;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JRadioButton;

import core.Command;
import core.Mediator;

public class ReceiveRadioButton extends JRadioButton implements Command{
	Mediator med;

	public ReceiveRadioButton(String text,ActionListener act, Mediator md) {
		super(text);
		addActionListener(act);
		med = md;
	}
	public ReceiveRadioButton() {
		// TODO Auto-generated constructor stub
	}
	

	public ReceiveRadioButton(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public ReceiveRadioButton(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public ReceiveRadioButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public ReceiveRadioButton(Icon icon, boolean selected) {
		super(icon, selected);
		// TODO Auto-generated constructor stub
	}

	public ReceiveRadioButton(String text, boolean selected) {
		super(text, selected);
		// TODO Auto-generated constructor stub
	}

	public ReceiveRadioButton(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	public ReceiveRadioButton(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		med.initiateReceiveFile();
		
	}

}
