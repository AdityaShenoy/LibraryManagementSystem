import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DatabaseManager {
	private static Connection conn;
	private static Statement stmt;
	private static int page_no;
	private static JButton next_b, previous_b;
	private static String id;

	public static void connect() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "1234");
		stmt = conn.createStatement();
	}

	public static int login(String u, String p) {
		try {
			connect();
			ResultSet rs = stmt.executeQuery(
					"select count(username) from login where username like '" + u + "' and password like '" + p + "';");
			rs.next();
			int result = rs.getInt(1);
			rs = stmt.executeQuery("select count(username) from login where username like '" + u
					+ "' and username like 'admin%' and password like '" + p + "';");
			rs.next();
			result += rs.getInt(1);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void search(String title, String author, String publisher) {
		try {
			Login.mainPanel.removeAll();
			page_no = 0;
			String query = "from book where title like '%" + title + "%' and author like '%" + author
					+ "%' and publisher like '%" + publisher + "%'";
			ResultSet rs3 = stmt
					.executeQuery("select count(book_id) from book where book_id in (select book_id " + query + ");");
			rs3.next();
			int no_of_rows = rs3.getInt(1);
			JLabel[][] labels = new JLabel[no_of_rows + 1][7];
			JPanel[] panels = new JPanel[(no_of_rows < 8) ? no_of_rows + 1 : 8];
			int[] width_partition = new int[] { 1, 10, 10, 10, 2, 2, 2 };
			int partition_sum = 0;
			for (int x : width_partition)
				partition_sum += x;
			int[] width_loc = new int[7];
			width_loc[0] = 0;
			for (int i = 1; i < 7; i++)
				width_loc[i] = width_loc[i - 1] + width_partition[i - 1];
			for (int i = 0; i < panels.length; i++) {
				panels[i] = new JPanel();
				panels[i].setBounds(0, (i * 4 * Login.height) / 50, Login.width, (4 * Login.height) / 50);
				panels[i].setBackground(
						(i == 0) ? Color.WHITE : (i % 2 == 0) ? Color.decode("#c1ff70") : Color.decode("#ff5454"));
			}
			for (int i = 0; i < no_of_rows + 1; i++)
				for (int j = 0; j < 7; j++) {
					labels[i][j] = new JLabel();
					labels[i][j].setBounds((width_loc[j] * Login.width) / (partition_sum),
							(((i % 8) + (i / 8)) * 4 * Login.height) / 50,
							(width_partition[j] * Login.width) / (partition_sum), (4 * Login.height) / 50);
					labels[i][j].setFont(new Font(Font.SERIF, Font.PLAIN, Login.height / 30));
					labels[i][j].setHorizontalAlignment(JLabel.CENTER);
					labels[i][j].setForeground(Color.BLACK);
					labels[i][j].setBackground(
							(i == 0) ? Color.WHITE : (i % 2 == 0) ? Color.decode("#550000") : Color.decode("#005500"));
				}
			labels[0][0].setText("No.");
			labels[0][1].setText("Title");
			labels[0][2].setText("Author");
			labels[0][3].setText("Publisher");
			labels[0][4].setText("Shelf");
			labels[0][5].setText("Row");
			labels[0][6].setText("Qty");
			ResultSet rs1 = stmt.executeQuery("select * " + query + ";");
			for (int i = 1; i < no_of_rows + 1; i++) {
				rs1.next();
				labels[i][0].setText(String.valueOf(i));
				labels[i][1].setText(rs1.getString(2));
				labels[i][2].setText(rs1.getString(3));
				labels[i][3].setText(rs1.getString(4));
				labels[i][6].setText(rs1.getString(6));
			}
			ResultSet rs2 = stmt.executeQuery(
					"select shelf_no, row_no from location where location_id in (select location " + query + ");");
			for (int i = 1; i < no_of_rows + 1; i++) {
				rs2.next();
				labels[i][4].setText(rs2.getString(1));
				labels[i][5].setText(rs2.getString(2));
			}
			for (int i = 0; i < 8 && i < no_of_rows + 1; i++)
				for (int j = 0; j < 7; j++)
					Login.mainPanel.add(labels[i][j]);
			for (int i = 0; i < panels.length; i++)
				Login.mainPanel.add(panels[i]);
			next_b = new JButton("Next");
			next_b.setBounds(26 * Login.width / partition_sum, 33 * Login.height / 50, 2 * Login.width / partition_sum,
					Login.height / 25);
			previous_b = new JButton("Previous");
			previous_b.setBounds(9 * Login.width / partition_sum, 33 * Login.height / 50,
					2 * Login.width / partition_sum, Login.height / 25);
			next_b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					page_no++;
					Login.mainPanel.removeAll();
					for (int i = 0; i < 7; i++)
						Login.mainPanel.add(labels[0][i]);
					Login.mainPanel.add(panels[0]);
					for (int i = 0; i < 7; i++)
						for (int j = 0; j < 7; j++)
							try {
								Login.mainPanel.add(labels[page_no * 7 + i + 1][j]);
								Login.mainPanel.add(panels[i + 1]);
							} catch (ArrayIndexOutOfBoundsException e) {
								break;
							}
					next_b.setEnabled((page_no + 1) * 7 < no_of_rows + 1);
					previous_b.setEnabled(page_no != 0);
					Login.mainPanel.add(next_b);
					Login.mainPanel.add(previous_b);
					Login.mainPanel.repaint();
					Login.mainFrame.repaint();
				}
			});
			previous_b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					page_no--;
					Login.mainPanel.removeAll();
					for (int i = 0; i < 7; i++)
						Login.mainPanel.add(labels[0][i]);
					Login.mainPanel.add(panels[0]);
					for (int i = 0; i < 7; i++)
						for (int j = 0; j < 7; j++)
							try {
								Login.mainPanel.add(labels[page_no * 7 + i + 1][j]);
								Login.mainPanel.add(panels[i + 1]);
							} catch (ArrayIndexOutOfBoundsException e) {
								break;
							}
					next_b.setEnabled((page_no + 1) * 7 < no_of_rows + 1);
					previous_b.setEnabled(page_no != 0);
					Login.mainPanel.add(next_b);
					Login.mainPanel.add(previous_b);
					Login.mainPanel.repaint();
					Login.mainFrame.repaint();
				}
			});
			next_b.setEnabled(7 < no_of_rows);
			previous_b.setEnabled(false);
			Login.mainPanel.add(next_b);
			Login.mainPanel.add(previous_b);
			Login.mainPanel.repaint();
			Login.mainFrame.repaint();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void bookStatus(String id) {
		try {
			ResultSet rs = stmt.executeQuery("select count(student_id) from student where student_id = '" + id + "';");
			rs.next();
			if (rs.getInt(1) == 0) {
				Admin.std_id_l.setText("Enter a valid student ID");
				Admin.book1_id_l.setText("-");
				Admin.book1_author_l.setText("-");
				Admin.book1_title_l.setText("-");
				Admin.book1_time_l.setText("-");
				Admin.return1_b.setEnabled(false);
				Admin.book2_id_l.setText("-");
				Admin.book2_author_l.setText("-");
				Admin.book2_title_l.setText("-");
				Admin.book2_time_l.setText("-");
				Admin.return2_b.setEnabled(false);
				Admin.book3_id_l.setText("-");
				Admin.book3_author_l.setText("-");
				Admin.book3_title_l.setText("-");
				Admin.book3_time_l.setText("-");
				Admin.bookR_id_l.setText("-");
				Admin.return3_b.setEnabled(false);
				Admin.issue_b.setEnabled(false);
				return;
			}
			DatabaseManager.id = id;
			Admin.std_id_l.setText("Student ID");
			Admin.bookR_id_l.setText("Book ID");
			Admin.issue_b.setEnabled(true);
			rs = stmt.executeQuery(
					"select count(book_id) from book where book_id in (select book1 from student where student_id ='"
							+ id + "');");
			rs.next();
			if (rs.getInt(1) == 0) {
				Admin.book1_id_l.setText("-");
				Admin.book1_author_l.setText("-");
				Admin.book1_title_l.setText("-");
				Admin.book1_time_l.setText("-");
				Admin.return1_b.setEnabled(false);
				Admin.book2_id_l.setText("-");
				Admin.book2_author_l.setText("-");
				Admin.book2_title_l.setText("-");
				Admin.book2_time_l.setText("-");
				Admin.return2_b.setEnabled(false);
				Admin.book3_id_l.setText("-");
				Admin.book3_author_l.setText("-");
				Admin.book3_title_l.setText("-");
				Admin.book3_time_l.setText("-");
				Admin.return3_b.setEnabled(false);
				return;
			}
			Admin.return1_b.setEnabled(true);
			rs = stmt.executeQuery(
					"select book_id, title, author from book where book_id in (select book1 from student where student_id = '"
							+ id + "');");
			rs.next();
			Admin.book1_id_l.setText(rs.getString(1));
			Admin.book1_title_l.setText(rs.getString(2));
			Admin.book1_author_l.setText(rs.getString(3));
			rs = stmt.executeQuery(
					"select due_date from book_status where status_id in (select book1_status from student where student_id = "
							+ id + ");");
			rs.next();
			Admin.book1_time_l.setText(rs.getString(1));

			rs = stmt.executeQuery(
					"select count(book_id) from book where book_id in (select book2 from student where student_id ='"
							+ id + "');");
			rs.next();
			if (rs.getInt(1) == 0) {
				Admin.book2_id_l.setText("-");
				Admin.book2_author_l.setText("-");
				Admin.book2_title_l.setText("-");
				Admin.book2_time_l.setText("-");
				Admin.return2_b.setEnabled(false);
				Admin.book3_id_l.setText("-");
				Admin.book3_author_l.setText("-");
				Admin.book3_title_l.setText("-");
				Admin.book3_time_l.setText("-");
				Admin.return3_b.setEnabled(false);
				return;
			}
			Admin.return2_b.setEnabled(true);
			rs = stmt.executeQuery(
					"select book_id, title, author from book where book_id in (select book2 from student where student_id = '"
							+ id + "');");
			rs.next();
			Admin.book2_id_l.setText(rs.getString(1));
			Admin.book2_title_l.setText(rs.getString(2));
			Admin.book2_author_l.setText(rs.getString(3));
			rs = stmt.executeQuery(
					"select due_date from book_status where status_id in (select book2_status from student where student_id = "
							+ id + ");");
			rs.next();
			Admin.book2_time_l.setText(rs.getString(1));

			rs = stmt.executeQuery(
					"select count(book_id) from book where book_id in (select book3 from student where student_id ='"
							+ id + "');");
			rs.next();
			if (rs.getInt(1) == 0) {
				Admin.book3_id_l.setText("-");
				Admin.book3_author_l.setText("-");
				Admin.book3_title_l.setText("-");
				Admin.book3_time_l.setText("-");
				Admin.return3_b.setEnabled(false);
				return;
			}
			Admin.return3_b.setEnabled(true);
			rs = stmt.executeQuery(
					"select book_id, title, author from book where book_id in (select book3 from student where student_id = '"
							+ id + "');");
			rs.next();
			Admin.book3_id_l.setText(rs.getString(1));
			Admin.book3_title_l.setText(rs.getString(2));
			Admin.book3_author_l.setText(rs.getString(3));
			rs = stmt.executeQuery(
					"select due_date from book_status where status_id in (select book3_status from student where student_id = "
							+ id + ");");
			rs.next();
			Admin.book3_time_l.setText(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	public static void issue() {
		try {
			String book_id = Admin.bookR_id_t.getText();
			ResultSet rs = stmt.executeQuery("select count(book_id) from book where book_id = '" + book_id + "';");
			rs.next();
			if (rs.getInt(1) == 0) {
				Admin.bookR_id_l.setText("Enter valid book ID");
				return;
			}
			Admin.bookR_id_l.setText("Book ID");
			rs = stmt.executeQuery("select quantity from book where book_id = '" + book_id + "';");
			rs.next();
			if (rs.getInt(1) == 0) {
				Admin.status_l.setText("Book not available");
				return;
			}
			rs = stmt.executeQuery("select count(status_id) from book_status;");
			rs.next();
			int new_status_id = rs.getInt(1) + 1;
			rs = stmt.executeQuery(
					"select count(book_id) from book where book_id in (select book1 from student where student_id = '"
							+ id + "');");
			rs.next();
			if (rs.getInt(1) == 0) {
				stmt.executeUpdate("insert into book_status values(" + new_status_id + ", " + book_id
						+ ", date_add(curdate(), interval 7 day));");
				stmt.executeUpdate("update student set book1 = " + book_id + ", book1_status = " + new_status_id
						+ " where student_id = '" + id + "';");
				stmt.executeUpdate("update book set quantity = quantity - 1 where book_id = " + book_id + ";");
				bookStatus(id);
				Admin.status_l.setText("");
			} else {
				rs = stmt.executeQuery(
						"select count(book_id) from book where book_id in (select book2 from student where student_id = '"
								+ id + "');");
				rs.next();
				if (rs.getInt(1) == 0) {
					rs = stmt.executeQuery("select count(book_id) from book where book_id = " + book_id
							+ " and book_id in (select book1 from student where student_id = '" + id + "');");
					rs.next();
					if (rs.getInt(1) == 0) {
						stmt.executeUpdate("insert into book_status values(" + new_status_id + ", " + book_id
								+ ", date_add(curdate(), interval 7 day));");
						stmt.executeUpdate("update student set book2 = " + book_id + ", book2_status = " + new_status_id
								+ " where student_id = '" + id + "';");
						stmt.executeUpdate("update book set quantity = quantity - 1 where book_id = " + book_id + ";");
						bookStatus(id);
						Admin.status_l.setText("");
					} else {
						Admin.status_l.setText("Book already issued");
					}
				} else {
					rs = stmt.executeQuery(
							"select count(book_id) from book where book_id in (select book3 from student where student_id = '"
									+ id + "');");
					rs.next();
					if (rs.getInt(1) == 0) {
						rs = stmt.executeQuery("select count(book_id) from book where book_id = " + book_id
								+ " and ((book_id in (select book1 from student where student_id = '" + id
								+ "')) or (book_id in (select book2 from student where student_id = '" + id + "')));");
						rs.next();
						if (rs.getInt(1) == 0) {
							stmt.executeUpdate("insert into book_status values(" + new_status_id + ", " + book_id
									+ ", date_add(curdate(), interval 7 day));");
							stmt.executeUpdate("update student set book3 = " + book_id + ", book3_status = "
									+ new_status_id + " where student_id = '" + id + "';");
							stmt.executeUpdate(
									"update book set quantity = quantity - 1 where book_id = " + book_id + ";");
							bookStatus(id);
							Admin.status_l.setText("");
						} else {
							Admin.status_l.setText("Book already issued");
						}
					} else {
						Admin.status_l.setText("3 books issued");
					}
				}
			}
			Login.mainPanel.repaint();
			Login.mainFrame.repaint();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void return1() {
		try {
			stmt.executeUpdate(
					"update book set quantity = quantity +1 where book_id in (select book1 from student where student_id ='"
							+ id + "');");
			ResultSet rs = stmt.executeQuery(
					"select count(book_id) from book where book_id in (select book2 from student where student_id = '"
							+ id + "');");
			rs.next();
			if (rs.getInt(1) == 1) {
				rs = stmt.executeQuery("select book2, book2_status from student where student_id = '" + id + "';");
				rs.next();
				stmt.executeUpdate("update student set book1 = " + rs.getInt(1) + ", book1_status = " + rs.getInt(2)
						+ " where student_id = '" + id + "';");
				Admin.book1_id_l.setText(Admin.book2_id_l.getText());
				Admin.book1_title_l.setText(Admin.book2_title_l.getText());
				Admin.book1_author_l.setText(Admin.book2_author_l.getText());
				Admin.book1_time_l.setText(Admin.book2_time_l.getText());
				rs = stmt.executeQuery(
						"select count(book_id) from book where book_id in (select book3 from student where student_id = '"
								+ id + "');");
				rs.next();
				if (rs.getInt(1) == 1) {
					rs = stmt.executeQuery("select book3, book3_status from student where student_id = '" + id + "';");
					rs.next();
					stmt.executeUpdate("update student set book2 = " + rs.getInt(1) + ", book2_status = " + rs.getInt(2)
							+ " where student_id = '" + id + "';");
					Admin.book2_id_l.setText(Admin.book3_id_l.getText());
					Admin.book2_title_l.setText(Admin.book3_title_l.getText());
					Admin.book2_author_l.setText(Admin.book3_author_l.getText());
					Admin.book2_time_l.setText(Admin.book3_time_l.getText());
					stmt.executeUpdate(
							"update student set book3 = null, book3_status = null where student_id = '" + id + "';");
					Admin.book3_id_l.setText("-");
					Admin.book3_author_l.setText("-");
					Admin.book3_title_l.setText("-");
					Admin.book3_time_l.setText("-");
					Admin.return3_b.setEnabled(false);
				} else {
					stmt.executeUpdate(
							"update student set book2 = null, book2_status = null where student_id = '" + id + "';");
					Admin.book2_id_l.setText("-");
					Admin.book2_author_l.setText("-");
					Admin.book2_title_l.setText("-");
					Admin.book2_time_l.setText("-");
					Admin.return2_b.setEnabled(false);
				}
			} else {
				stmt.executeUpdate(
						"update student set book1 = null, book1_status = null where student_id = '" + id + "';");
				Admin.book1_id_l.setText("-");
				Admin.book1_author_l.setText("-");
				Admin.book1_title_l.setText("-");
				Admin.book1_time_l.setText("-");
				Admin.return1_b.setEnabled(false);
			}
			Login.mainPanel.repaint();
			Login.mainFrame.repaint();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void return2() {
		try {
			stmt.executeUpdate(
					"update book set quantity = quantity +1 where book_id in (select book2 from student where student_id ='"
							+ id + "');");
			ResultSet rs = stmt.executeQuery(
					"select count(book_id) from book where book_id in (select book3 from student where student_id = '"
							+ id + "');");
			rs.next();
			if (rs.getInt(1) == 1) {
				rs = stmt.executeQuery("select book3, book3_status from student where student_id = '" + id + "';");
				rs.next();
				stmt.executeUpdate("update student set book2 = " + rs.getInt(1) + ", book2_status = " + rs.getInt(2)
						+ " where student_id = '" + id + "';");
				Admin.book2_id_l.setText(Admin.book3_id_l.getText());
				Admin.book2_title_l.setText(Admin.book3_title_l.getText());
				Admin.book2_author_l.setText(Admin.book3_author_l.getText());
				Admin.book2_time_l.setText(Admin.book3_time_l.getText());
				stmt.executeUpdate(
						"update student set book3 = null, book3_status = null where student_id = '" + id + "';");
				Admin.book3_id_l.setText("-");
				Admin.book3_author_l.setText("-");
				Admin.book3_title_l.setText("-");
				Admin.book3_time_l.setText("-");
				Admin.return3_b.setEnabled(false);
			} else {
				stmt.executeUpdate(
						"update student set book2 = null, book2_status = null where student_id = '" + id + "';");
				Admin.book2_id_l.setText("-");
				Admin.book2_author_l.setText("-");
				Admin.book2_title_l.setText("-");
				Admin.book2_time_l.setText("-");
				Admin.return2_b.setEnabled(false);
			}
			Login.mainPanel.repaint();
			Login.mainFrame.repaint();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void return3() {
		try {
			stmt.executeUpdate(
					"update book set quantity = quantity +1 where book_id in (select book3 from student where student_id ='"
							+ id + "');");
			stmt.executeUpdate("update student set book3 = null, book3_status = null where student_id = '" + id + "';");
			Admin.book3_id_l.setText("-");
			Admin.book3_author_l.setText("-");
			Admin.book3_title_l.setText("-");
			Admin.book3_time_l.setText("-");
			Admin.return3_b.setEnabled(false);
			Login.mainPanel.repaint();
			Login.mainFrame.repaint();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}