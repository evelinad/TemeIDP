package buttons;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import core.Command;
import core.Mediator;

public class StopButton extends JButton implements Command {
	Mediator med;
	public StopButton() {
		// TODO Auto-generated constructor stub
	}

	public StopButton(String text,ActionListener act, Mediator md,ImageIcon icon) {
		super(text);
		addActionListener(act);
		med = md;
		setIcon(icon);
	}
	public StopButton(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public StopButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public StopButton(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public StopButton(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		med.stopSelectedTransfer();
	}

}
