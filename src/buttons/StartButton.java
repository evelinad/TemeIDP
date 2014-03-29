package buttons;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import core.Command;
import core.Mediator;

public class StartButton extends JButton implements Command {

	private Mediator med;
	
	public StartButton(String text,ActionListener act, Mediator md,ImageIcon icon) {
		super(text);
		addActionListener(act);
		med = md;
		setIcon(icon);
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		med.startSelectedTransfer();
	}

}
