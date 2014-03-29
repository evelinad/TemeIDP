package buttons;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import core.Command;
import core.Mediator;

public class PauseButton extends JButton implements Command{

	Mediator med;
	public PauseButton() {
		// TODO Auto-generated constructor stub
	}

	public PauseButton(String text,ActionListener act, Mediator md, ImageIcon icon) {
		super(text);
		addActionListener(act);
		med = md;
		setIcon(icon);
	}
	public PauseButton(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public PauseButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public PauseButton(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public PauseButton(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		med.pauseSelectedTransfer();
	}

}
