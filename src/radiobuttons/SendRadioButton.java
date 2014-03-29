package radiobuttons;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JRadioButton;

import core.Command;
import core.Mediator;

public class SendRadioButton extends JRadioButton implements Command{
	Mediator med;

	public SendRadioButton(String text,ActionListener act, Mediator md) {
		super(text);
		addActionListener(act);
		med = md;
	}
	public SendRadioButton() {
		// TODO Auto-generated constructor stub
	}

	public SendRadioButton(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public SendRadioButton(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public SendRadioButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public SendRadioButton(Icon icon, boolean selected) {
		super(icon, selected);
		// TODO Auto-generated constructor stub
	}

	public SendRadioButton(String text, boolean selected) {
		super(text, selected);
		// TODO Auto-generated constructor stub
	}

	public SendRadioButton(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	public SendRadioButton(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		med.initiateSendFile();
		
	}

}
