package buttons;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import core.Command;
import core.Mediator;

/**
 * 
 * Class for pausing a transfer
 * 
 */

@SuppressWarnings("serial")
public class PauseButton extends JButton implements Command {

	Mediator med;

	public PauseButton() {
	}

	public PauseButton(String text, ActionListener act, Mediator md,
			ImageIcon icon) {
		super(text);
		addActionListener(act);
		med = md;
		setIcon(icon);
	}

	public PauseButton(Icon icon) {
		super(icon);
	}

	public PauseButton(String text) {
		super(text);
	}

	public PauseButton(Action a) {
		super(a);
	}

	public PauseButton(String text, Icon icon) {
		super(text, icon);
	}

	@Override
	public void execute() {
		med.pauseSelectedTransfer();
	}

}
