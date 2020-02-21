import java.awt.EventQueue;

import javax.swing.JFrame;

public class TestFrame {

	private JFrame frame;

	public TestFrame() {
		initialize();
	}

	private void initialize() {
		System.out.println("Hi there!!");
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
