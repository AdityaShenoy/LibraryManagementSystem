import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class QueryS {
	public static JButton search_b,cancel_b;
	public static JLabel title_l,author_l,publisher_l;
	public static JTextField title_t,author_t,publisher_t;
	private static Color bgcolor = Color.ORANGE;
	
	public static void display() {
		Login.mainPanel.removeAll();
		Login.mainPanel2.removeAll();
		init();
		Login.mainPanel2.add(title_l);
		Login.mainPanel2.add(author_l);
		Login.mainPanel2.add(publisher_l);
		Login.mainPanel2.add(title_t);
		Login.mainPanel2.add(author_t);
		Login.mainPanel2.add(publisher_t);
		Login.mainPanel2.add(search_b);
		Login.mainPanel2.add(cancel_b);
		Login.mainFrame.repaint();
	}
	
	public static void init() {
		title_l= new JLabel("Title");
		title_l.setBounds(12*Login.width/100, 1*Login.height/25, 2*Login.width/10, 2*Login.height/45);
		title_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height/30));
		title_l.setHorizontalAlignment(JLabel.CENTER);
		title_l.setForeground(Color.decode("#0000FF"));
		title_l.setBackground(bgcolor);
		
		author_l = new JLabel("Author");
		author_l.setBounds(40*Login.width/100, 1*Login.height/25, 2*Login.width/10, 2*Login.height/45);
		author_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height/30));
		author_l.setHorizontalAlignment(JLabel.CENTER);
		author_l.setForeground(Color.decode("#0000FF"));
		author_l.setBackground(bgcolor);
		
		publisher_l= new JLabel("Publisher");
		publisher_l.setBounds(66*Login.width/100, 1*Login.height/25, 2*Login.width/10, 2*Login.height/45);
		publisher_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height/30));
		publisher_l.setHorizontalAlignment(JLabel.CENTER);
		publisher_l.setForeground(Color.decode("#0000FF"));
		publisher_l.setBackground(bgcolor);
	
		title_t= new JTextField("",15);
		title_t.setBounds(12*Login.width/100, 2*Login.height/25, 4*Login.width/18, Login.height/30);
		title_t.setBackground(Color.CYAN);
		Border borderA=BorderFactory.createLineBorder(Color.BLACK);
		title_t.setBorder(borderA);
		
		author_t= new JTextField("",15);
		author_t.setBounds(39*Login.width/100, 2*Login.height/25, 4*Login.width/18, Login.height/30);
		author_t.setBackground(Color.CYAN);
		author_t.setBorder(borderA);
		
		publisher_t= new JTextField("",15);
		publisher_t.setBounds(66*Login.width/100, 2*Login.height/25, 4*Login.width/18, Login.height/30);
		publisher_t.setBackground(Color.CYAN);
		publisher_t.setBorder(borderA);
		
		search_b = new JButton("Search");
		search_b.setBounds(42*Login.width/100, 15*Login.height/100, Login.width/16, Login.height/25);
		search_b.setHorizontalAlignment(JLabel.CENTER);
		search_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str1, str2, str3;
				str1 = title_t.getText();
				str2 = author_t.getText();
				str3 = publisher_t.getText();
				DatabaseManager.search(str1, str2, str3);
			}
		});
		
		cancel_b = new JButton("Cancel");
		cancel_b.setBounds(52*Login.width/100, 15*Login.height/100, Login.width/16, Login.height/25);
		cancel_b.setHorizontalAlignment(JLabel.CENTER);
		cancel_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				title_t.setText("");
				author_t.setText("");
				publisher_t.setText("");		
			}
		});
	}
}