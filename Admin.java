import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Admin {
	public static JButton status_b, return1_b, return2_b, return3_b, issue_b;
	public static JLabel std_id_l, mainLabelIB, book1_id_l, book1_title_l, book1_author_l, book1_time_l, book2_id_l,
			book2_title_l, book2_author_l, book2_time_l, book3_id_l, book3_title_l, book3_author_l, book3_time_l,
			mainLabelRB, bookR_id_l, status_l;
	public static JTextField std_id_t, bookR_id_t;
	private static Color bgcolor = Color.ORANGE;

	public static void display() {
		Login.mainPanel.removeAll();
		Login.mainPanel2.removeAll();
		init1();
		init2();
		Login.mainPanel2.add(std_id_l);
		Login.mainPanel2.add(std_id_t);
		Login.mainPanel2.add(status_b);
		Login.mainFrame.repaint();
	}

	public static void init1() {
		std_id_l = new JLabel("Student ID");
		std_id_l.setBounds(40 * Login.width / 100, 1 * Login.height / 25, 2 * Login.width / 10, 2 * Login.height / 45);
		std_id_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		std_id_l.setHorizontalAlignment(JLabel.CENTER);
		std_id_l.setForeground(Color.decode("#0000FF"));
		std_id_l.setBackground(bgcolor);

		std_id_t = new JTextField("", 15);
		std_id_t.setBounds(41 * Login.width / 100, 2 * Login.height / 25, 3 * Login.width / 18, Login.height / 30);
		std_id_t.setBackground(Color.CYAN);
		Border borderA = BorderFactory.createLineBorder(Color.BLACK);
		std_id_t.setBorder(borderA);

		status_b = new JButton("Status");
		status_b.setBounds(47 * Login.width / 100, 15 * Login.height / 100, Login.width / 16, Login.height / 25);
		status_b.setHorizontalAlignment(JLabel.CENTER);
		status_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseManager.bookStatus(std_id_t.getText());
				Login.mainPanel.add(mainLabelIB);
				Login.mainPanel.add(book1_id_l);
				Login.mainPanel.add(book1_title_l);
				Login.mainPanel.add(book1_author_l);
				Login.mainPanel.add(book1_time_l);
				Login.mainPanel.add(return1_b);
				Login.mainPanel.add(book2_id_l);
				Login.mainPanel.add(book2_title_l);
				Login.mainPanel.add(book2_author_l);
				Login.mainPanel.add(book2_time_l);
				Login.mainPanel.add(return2_b);
				Login.mainPanel.add(book3_id_l);
				Login.mainPanel.add(book3_title_l);
				Login.mainPanel.add(book3_author_l);
				Login.mainPanel.add(book3_time_l);
				Login.mainPanel.add(return3_b);
				Login.mainPanel.add(mainLabelRB);
				Login.mainPanel.add(bookR_id_l);
				Login.mainPanel.add(bookR_id_t);
				Login.mainPanel.add(issue_b);
				Login.mainPanel.add(status_l);
				Login.mainFrame.repaint();
			}
		});
	}

	public static void init2() {
		mainLabelIB = new JLabel("Issued Books");
		mainLabelIB.setBounds(5 * Login.width / 100, Login.height / 100, 2 * Login.width / 8, Login.height / 20);
		mainLabelIB.setFont(new Font(Font.SANS_SERIF, Font.BOLD, Login.height / 25));
		mainLabelIB.setHorizontalAlignment(JLabel.CENTER);
		mainLabelIB.setAlignmentX(0.5f);
		mainLabelIB.setForeground(Color.BLACK);
		mainLabelIB.setBackground(bgcolor);

		book1_id_l = new JLabel("id1");
		book1_id_l.setBounds(5 * Login.width / 100, 3 * Login.height / 25, 2 * Login.width / 10, 2 * Login.height / 45);
		book1_id_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		book1_id_l.setHorizontalAlignment(JLabel.CENTER);
		book1_id_l.setForeground(Color.decode("#0000FF"));
		book1_id_l.setBackground(bgcolor);

		book1_title_l = new JLabel("title1");
		book1_title_l.setBounds(20 * Login.width / 100, 3 * Login.height / 25, 2 * Login.width / 10,
				2 * Login.height / 45);
		book1_title_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		book1_title_l.setHorizontalAlignment(JLabel.CENTER);
		book1_title_l.setForeground(Color.decode("#0000FF"));
		book1_title_l.setBackground(bgcolor);

		book1_author_l = new JLabel("author1");
		book1_author_l.setBounds(50 * Login.width / 100, 3 * Login.height / 25, 2 * Login.width / 10,
				2 * Login.height / 45);
		book1_author_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		book1_author_l.setHorizontalAlignment(JLabel.CENTER);
		book1_author_l.setForeground(Color.decode("#0000FF"));
		book1_author_l.setBackground(bgcolor);

		book1_time_l = new JLabel("time1");
		book1_time_l.setBounds(70 * Login.width / 100, 3 * Login.height / 25, 2 * Login.width / 10,
				2 * Login.height / 45);
		book1_time_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		book1_time_l.setHorizontalAlignment(JLabel.CENTER);
		book1_time_l.setForeground(Color.decode("#0000FF"));
		book1_time_l.setBackground(bgcolor);

		return1_b = new JButton("Return");
		return1_b.setBounds(90 * Login.width / 100, 11 * Login.height / 100, Login.width / 16, Login.height / 25);
		return1_b.setHorizontalAlignment(JLabel.CENTER);
		return1_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseManager.return1();
			}
		});

		book2_id_l = new JLabel("id2");
		book2_id_l.setBounds(5 * Login.width / 100, 5 * Login.height / 25, 2 * Login.width / 10, 2 * Login.height / 45);
		book2_id_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		book2_id_l.setHorizontalAlignment(JLabel.CENTER);
		book2_id_l.setForeground(Color.decode("#0000FF"));
		book2_id_l.setBackground(bgcolor);

		book2_title_l = new JLabel("title2");
		book2_title_l.setBounds(20 * Login.width / 100, 5 * Login.height / 25, 2 * Login.width / 10,
				2 * Login.height / 45);
		book2_title_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		book2_title_l.setHorizontalAlignment(JLabel.CENTER);
		book2_title_l.setForeground(Color.decode("#0000FF"));
		book2_title_l.setBackground(bgcolor);

		book2_author_l = new JLabel("author2");
		book2_author_l.setBounds(50 * Login.width / 100, 5 * Login.height / 25, 2 * Login.width / 10,
				2 * Login.height / 45);
		book2_author_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		book2_author_l.setHorizontalAlignment(JLabel.CENTER);
		book2_author_l.setForeground(Color.decode("#0000FF"));
		book2_author_l.setBackground(bgcolor);

		book2_time_l = new JLabel("time2");
		book2_time_l.setBounds(70 * Login.width / 100, 5 * Login.height / 25, 2 * Login.width / 10,
				2 * Login.height / 45);
		book2_time_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		book2_time_l.setHorizontalAlignment(JLabel.CENTER);
		book2_time_l.setForeground(Color.decode("#0000FF"));
		book2_time_l.setBackground(bgcolor);

		return2_b = new JButton("Return");
		return2_b.setBounds(90 * Login.width / 100, 19 * Login.height / 100, Login.width / 16, Login.height / 25);
		return2_b.setHorizontalAlignment(JLabel.CENTER);
		return2_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseManager.return2();
			}
		});

		book3_id_l = new JLabel("id3");
		book3_id_l.setBounds(5 * Login.width / 100, 7 * Login.height / 25, 2 * Login.width / 10, 2 * Login.height / 45);
		book3_id_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		book3_id_l.setHorizontalAlignment(JLabel.CENTER);
		book3_id_l.setForeground(Color.decode("#0000FF"));
		book3_id_l.setBackground(bgcolor);

		book3_title_l = new JLabel("title3");
		book3_title_l.setBounds(20 * Login.width / 100, 7 * Login.height / 25, 2 * Login.width / 10,
				2 * Login.height / 45);
		book3_title_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		book3_title_l.setHorizontalAlignment(JLabel.CENTER);
		book3_title_l.setForeground(Color.decode("#0000FF"));
		book3_title_l.setBackground(bgcolor);

		book3_author_l = new JLabel("author3");
		book3_author_l.setBounds(50 * Login.width / 100, 7 * Login.height / 25, 2 * Login.width / 10,
				2 * Login.height / 45);
		book3_author_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		book3_author_l.setHorizontalAlignment(JLabel.CENTER);
		book3_author_l.setForeground(Color.decode("#0000FF"));
		book3_author_l.setBackground(bgcolor);

		book3_time_l = new JLabel("time3");
		book3_time_l.setBounds(70 * Login.width / 100, 7 * Login.height / 25, 2 * Login.width / 10,
				2 * Login.height / 45);
		book3_time_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		book3_time_l.setHorizontalAlignment(JLabel.CENTER);
		book3_time_l.setForeground(Color.decode("#0000FF"));
		book3_time_l.setBackground(bgcolor);

		return3_b = new JButton("Return");
		return3_b.setBounds(90 * Login.width / 100, 27 * Login.height / 100, Login.width / 16, Login.height / 25);
		return3_b.setHorizontalAlignment(JLabel.CENTER);
		return3_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseManager.return3();
			}
		});

		mainLabelRB = new JLabel("Books to be issued");
		mainLabelRB.setBounds(5 * Login.width / 100, 40 * Login.height / 100, 2 * Login.width / 8, Login.height / 20);
		mainLabelRB.setFont(new Font(Font.SANS_SERIF, Font.BOLD, Login.height / 25));
		mainLabelRB.setHorizontalAlignment(JLabel.CENTER);
		mainLabelRB.setAlignmentX(0.5f);
		mainLabelRB.setForeground(Color.BLACK);
		mainLabelRB.setBackground(bgcolor);

		bookR_id_l = new JLabel("idR");
		bookR_id_l.setBounds(5 * Login.width / 100, 50 * Login.height / 100, 2 * Login.width / 10,
				2 * Login.height / 45);
		bookR_id_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		bookR_id_l.setHorizontalAlignment(JLabel.CENTER);
		bookR_id_l.setForeground(Color.decode("#0000FF"));
		bookR_id_l.setBackground(bgcolor);

		bookR_id_t = new JTextField("", 15);
		bookR_id_t.setBounds(25 * Login.width / 100, 50 * Login.height / 100, 3 * Login.width / 18, Login.height / 30);
		bookR_id_t.setBackground(Color.CYAN);
		Border borderA = BorderFactory.createLineBorder(Color.BLACK);
		bookR_id_t.setBorder(borderA);

		issue_b = new JButton("Issue");
		issue_b.setBounds(20 * Login.width / 100, 60 * Login.height / 100, Login.width / 16, Login.height / 25);
		issue_b.setHorizontalAlignment(JLabel.CENTER);
		issue_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseManager.issue();
			}
		});
		
		status_l = new JLabel("");
		status_l.setBounds(40*Login.width/100, 60*Login.height/100, Login.width/6, Login.height/25);
		status_l.setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
		status_l.setForeground(Color.RED);
		status_l.setHorizontalAlignment(JLabel.CENTER);
	}
}
