package assessment.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import assessment.controller.Controller;

/**
 * <h1>PPA assignment 13 </h1> <br>
 * Computer Science <br>
 * Year 1 
 * <p>
 * This class defines viewer for A.I. window
 * 
 * @author Wonjoon Seol (k1631098), Yuji Xing (k1630591)
 */
public class AIFrame extends JFrame implements Observer{

	private JButton jbStart;
	private JTextArea jtaMessage;
	private JScrollPane jspPlane;
	
	public AIFrame(Controller controller) {
		super("AI Solver");
		
		setLayout(new BorderLayout());
		jbStart = new JButton("Delegate to A.I.");
		jbStart.setName("AIbutton");
		jbStart.addActionListener(controller);
		jtaMessage = new JTextArea();
		jspPlane = new JScrollPane(jtaMessage);

		add(jbStart, BorderLayout.PAGE_START);
		add(jspPlane, BorderLayout.CENTER);
		setPreferredSize(new Dimension(400, 400));
		pack();

		jtaAppend("Hello, this is a primitive AI puzzle solver ver.02032017");
	}
	
	/**
	 * This method appends supplied string to the jtaMessage and updates carat position to the latest position
	 * @param str String	String to be added
	 */
	public void jtaAppend(String str){
		jtaMessage.append(str + "\n");
	    jtaMessage.setCaretPosition(jtaMessage.getDocument().getLength());	// get the number of characters of contents and set it as its carat position
	}

	@Override
	public void update(Observable o, Object arg) {
		jtaAppend((String)arg);
	}

}
