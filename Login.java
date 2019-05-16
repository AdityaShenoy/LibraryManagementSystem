import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Login{
	public static JFrame mainFrame;
	public static JPanel mainPanel, mainPanel2;
	public static JLabel mainLabelT,mainLabel, username, password, login;
	public static JTextField untext;
	public static JPasswordField pwtext;
	private static JButton submit, cancel, logout;
	public static int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 45;
	public static int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final String TITLE = "VJTI Library";

	private static void init() {
		mainFrame = new JFrame(TITLE);
		mainFrame.setResizable(false);
		mainFrame.setSize(width, height);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(null);
		
		mainPanel = new JPanel();
		mainPanel.setBounds(0, height/5, width,4*height/5);
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setLayout(null);

		mainPanel2= new JPanel();
		mainPanel2.setBounds(0, 0, width,height/5);
		mainPanel2.setBackground(Color.ORANGE);
		mainPanel2.setLayout(null);
			
		mainLabelT = new JLabel("LIBRARY MANAGEMENT SYSTEM");
		mainLabelT.setBounds(10*width/100, 4*height/100, 80*width/100, 15*height/100);
		mainLabelT.setFont(new Font(Font.SANS_SERIF, Font.BOLD, height/25));
		mainLabelT.setHorizontalAlignment(JLabel.CENTER);
		mainLabelT.setAlignmentX(0.5f);
		mainLabelT.setForeground(Color.BLACK);
		mainLabelT.setBackground(Color.WHITE);
		
		mainLabel = new JLabel("Login");
		mainLabel.setBounds(2*width/8, height/20, 4*width/8, height/5);
		mainLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, height/25));
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		mainLabel.setAlignmentX(0.5f);
		mainLabel.setForeground(Color.BLACK);
		mainLabel.setBackground(Color.WHITE);
		
		login = new JLabel("");
		login.setBounds(2*width/8, 3*height/18, 4*width/8, height/5);
		login.setFont(new Font(Font.SERIF, Font.BOLD, height/30));
		login.setHorizontalAlignment(JLabel.CENTER);
		login.setForeground(Color.decode("#FF0000"));
		login.setBackground(Color.WHITE);
		
		username = new JLabel("Username");
		username.setBounds(9*width/32, 4*height/18, 2*width/8, height/5);
		username.setFont(new Font(Font.SERIF, Font.PLAIN, height/30));
		username.setHorizontalAlignment(JLabel.CENTER);
		username.setForeground(Color.decode("#0000FF"));
		username.setBackground(Color.WHITE);
		
		password = new JLabel("Password");
		password.setBounds(9*width/32, 5*height/18, 2*width/8, height/5);
		password.setFont(new Font(Font.SERIF, Font.PLAIN, height/30));
		password.setHorizontalAlignment(JLabel.CENTER);
		password.setForeground(Color.decode("#0000FF"));
		password.setBackground(Color.WHITE);
	
		untext= new JTextField("",15);
		untext.setBounds(15*width/32, 11*height/36, width/6, height/30);
		untext.setBackground(Color.CYAN);
		Border borderA=BorderFactory.createLineBorder(Color.BLACK);
		untext.setBorder(borderA);
		
		pwtext= new JPasswordField("",15);
		pwtext.setBounds(15*width/32, 13*height/36, width/6, height/30);
		pwtext.setBackground(Color.CYAN);
		pwtext.setBorder(borderA);
		
		submit = new JButton("Login");
		submit.setBounds(27*width/64, 9*height/18, width/16, height/25);
		submit.setHorizontalAlignment(JLabel.CENTER);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int login_type = DatabaseManager.login(untext.getText(), pwtext.getText());
				if(login_type == 2) {
					Admin.display();
					mainPanel2.add(logout);
				}else if(login_type == 1) {
					QueryS.display();
					mainPanel2.add(logout);
				}else
					Login.login.setText("Invalid username or password");
			}
		});
		
		cancel = new JButton("Cancel");
		cancel.setBounds(34*width/64, 9*height/18, width/16, height/25);
		cancel.setHorizontalAlignment(JLabel.CENTER);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Login.login.setText("");
				Login.untext.setText("");
				Login.pwtext.setText("");		
			}
		});
		
		logout = new JButton("logout");
		logout.setBounds(59*width/64, 0, width/16, height/20);
		logout.setFont(new Font(Font.SERIF, Font.PLAIN, height/30));
		logout.setForeground(Color.decode("#0000FF"));
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.removeAll();
				mainPanel2.removeAll();
				login.setText("Logged out successfully.");
				display();
			}
		});
	}
	
	public static void display() {
		mainFrame.add(mainPanel);
		mainFrame.add(mainPanel2);
		mainPanel2.add(mainLabelT);
		mainPanel.add(mainLabel);
		mainPanel.add(login);
		mainPanel.add(username);
		mainPanel.add(password);
		mainPanel.add(untext);
		mainPanel.add(pwtext);
		mainPanel.add(submit);
		mainPanel.add(cancel);
		mainFrame.setVisible(true);
		mainFrame.repaint();
	}
	
	public static void main(String[] args) {
		init();
		display();				
	}
}