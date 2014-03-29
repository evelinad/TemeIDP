package buttons;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import core.Command;
import core.Mediator;

public class StartButton extends JButton implements Command{
	Mediator med;
	public StartButton() {
		// TODO Auto-generated constructor stub
	}

	public StartButton(String text,ActionListener act, Mediator md,ImageIcon icon) {
		super(text);
		addActionListener(act);
		med = md;
		setIcon(icon);
	}
	public StartButton(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public StartButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public StartButton(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public StartButton(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		med.startSelectedTransfer();
		
	}

}
