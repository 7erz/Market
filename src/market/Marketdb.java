package market;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Marketdb extends JFrame {
	JTabbedPane pane;
	Container contentPane;

	public Marketdb() {
		setTitle("oo���� ���� ���α׷�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		pane = createJTabbedPane();
		contentPane.add(pane, BorderLayout.CENTER);
		setSize(1280, 720);
		setVisible(true);

	}

	JTabbedPane createJTabbedPane() {
		JTabbedPane pane = new JTabbedPane();
		// ���߰�
		pane.add("������", new CustomerPanel());
		pane.add("���ǰ���", new StockPanel());
		pane.add("��������", new EmployeePanel());

		return pane;
	}

	class CustomerPanel extends JPanel {
		ActionHandler handler = new ActionHandler();
		JPanel cpnl[];
		JLabel clbl[];
		JButton cbtn[];
		JTextField ctf[];
		JTextArea cta[];
		String[] lbl_tf = { "��ȭ��ȣ", "�̸�", "�������", "������", "����Ʈ" }; // (��ȭ��ȣ�� ���ڸ���,�ߺ��� �������. or �ߺ����� 11�ڸ� ��� ���)
		String[] lbl_btn = { "�߰�", "��ȸ", "����", "����" };
		JTable table;
		DefaultTableModel model;
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		public CustomerPanel() {
			con = makeConnection();
			clbl = new JLabel[5];
			cbtn = new JButton[4];
			ctf = new JTextField[5];
			cpnl = new JPanel[3];
			setLayout(new BorderLayout());
			for (int i = 0; i < 5; i++) {
				clbl[i] = new JLabel(lbl_tf[i]);
				ctf[i] = new JTextField(10);
				if (i < 4) {
					cbtn[i] = new JButton(lbl_btn[i]);
					cbtn[i].addActionListener(handler);
				} 
				if (i < 3) {
					cpnl[i] = new JPanel();
				}
			}
			model = new DefaultTableModel(lbl_tf, 0) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table = new JTable(model);

			add(cpnl[0], BorderLayout.NORTH);
			add(cpnl[1], BorderLayout.CENTER);
			add(cpnl[2], BorderLayout.SOUTH);

			for (int i = 0; i < 5; i++) {
				cpnl[0].add(clbl[i]);
				cpnl[0].add(ctf[i]);
			}
			cpnl[1].add(new JScrollPane(table));
			for (int i = 0; i < 4; i++) {
				cpnl[2].add(cbtn[i]);
			}
		}
		
		public Connection makeConnection() { // ����̺� ����
			System.out.println("�� makeconn ȣ����");
			String url = "jdbc:mysql://localhost:3306/customer?serverTimezone=Asia/Seoul";
			String id = "root";
			String password = "1249";
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("����̺� ���� ����");
				con = DriverManager.getConnection(url, id, password);
				stmt = con.createStatement();
				System.out.println("�����ͺ��̽� ���� ����");
			} catch (ClassNotFoundException e) {
				System.out.println("����̹��� ã�� �� �����ϴ�");
			} catch (SQLException e) {
				System.out.println("���ῡ �����Ͽ����ϴ�");
			}
			return con;
		}

		public void disConnection() {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		class ActionHandler implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				makeConnection();
				if (e.getSource() == cbtn[0]) {
					try {
						stmt = con.createStatement();
						int st = stmt.executeUpdate("INSERT INTO customer values ('" + ctf[0].getText() + "', '"
								+ ctf[1].getText() + "', '" + ctf[2].getText() + "', '" + ctf[3].getText()
								+ "', '" + ctf[4].getText() + "')");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

				else if (e.getSource() == cbtn[1]) {
					String sql = "SELECT * FROM customer";
					String[] row = new String[5];
					model.setNumRows(0); // JTable �ʱ�ȭ
					try {
						System.out.println(sql + "\n");
						rs = stmt.executeQuery(sql);
						while (rs.next()) {
							row[0] = rs.getString("phone") + "\t";
							row[1] = rs.getString("name") + "\t";
							row[2] = rs.getString("birth") + "\t";
							row[3] = rs.getString("sign") + "\t";
							row[4] = rs.getString("point") + "\n";
							model.addRow(row); // �߰�
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else if (e.getSource() == cbtn[2]) {
					try {
						stmt = con.createStatement();
						int st = stmt.executeUpdate("UPDATE customer set phone = '" + ctf[0].getText()
								+ "' where name = '" + ctf[1].getText() + "';");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				} else if (e.getSource() == cbtn[3]) {
					try {
						stmt = con.createStatement();
						int st = stmt
								.executeUpdate("DELETE from customer where phone = '" + ctf[0].getText() + "';");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				disConnection();
			}
		}
	}

	class StockPanel extends JPanel {
		ActionHandler handler = new ActionHandler();
		JPanel spnl[];
		JLabel slbl[];
		JButton sbtn[];
		JTextField stf[];
		JTextArea sta[];
		String[] lbl_tf = { "��ǰ����", "��ǰ��", "����ȸ��", "����" };
		String[] lbl_btn = { "�߰�", "��ȸ", "����", "����" };
		JTable table;
		DefaultTableModel model;
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		public StockPanel() {
			con = makeConnection();
			slbl = new JLabel[4];
			sbtn = new JButton[4];
			stf = new JTextField[5];
			spnl = new JPanel[3];
			setLayout(new BorderLayout());
			for (int i = 0; i < 4; i++) {
				stf[i] = new JTextField(10);
				sbtn[i] = new JButton(lbl_btn[i]);
				sbtn[i].addActionListener(handler);
				slbl[i] = new JLabel(lbl_tf[i]);
				if (i < 3) {
					spnl[i] = new JPanel();
				}
			}
			model = new DefaultTableModel(lbl_tf, 0) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table = new JTable(model);

			add(spnl[0], BorderLayout.NORTH);
			add(spnl[1], BorderLayout.CENTER);
			add(spnl[2], BorderLayout.SOUTH);

			for (int i = 0; i < 4; i++) {
				spnl[0].add(slbl[i]);
				spnl[0].add(stf[i]);
			}
			spnl[1].add(new JScrollPane(table));
			for (int i = 0; i < 4; i++) {
				spnl[2].add(sbtn[i]);
			}
		}
		public Connection makeConnection() { // ����̺� ����
			System.out.println("��ǰ makeconnȣ����");
			String url = "jdbc:mysql://localhost:3306/customer?serverTimezone=Asia/Seoul";
			String id = "root";
			String password = "1249";
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("����̺� ���� ����");
				con = DriverManager.getConnection(url, id, password);
				stmt = con.createStatement();
				System.out.println("�����ͺ��̽� ���� ����");
			} catch (ClassNotFoundException e) {
				System.out.println("����̹��� ã�� �� �����ϴ�");
			} catch (SQLException e) {
				System.out.println("���ῡ �����Ͽ����ϴ�");
			}
			return con;
		}

		public void disConnection() {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		class ActionHandler implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				makeConnection();
				if (e.getSource() == sbtn[0]) {
					try {
						stmt = con.createStatement();
						int st = stmt.executeUpdate("INSERT INTO stock values ('" + stf[0].getText() + "', '"
								+ stf[1].getText() + "', '" + stf[2].getText() + "', '" + stf[3].getText() + "')");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

				else if (e.getSource() == sbtn[1]) {
					String sql = "SELECT * FROM stock";
					String[] row = new String[4];
					model.setNumRows(0); // JTable �ʱ�ȭ
					try {
						System.out.println(sql + "\n");
						rs = stmt.executeQuery(sql);
						while (rs.next()) {
							row[0] = rs.getString("kinds") + "\t";
							row[1] = rs.getString("name") + "\t";
							row[2] = rs.getString("company") + "\t";
							row[3] = rs.getString("price") + "\n";
							model.addRow(row); // �߰�
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else if (e.getSource() == sbtn[2]) {
					try {
						stmt = con.createStatement();
						int st = stmt.executeUpdate("UPDATE stock set kinds = '" + stf[0].getText()
								+ "' where name = '" + stf[1].getText() + "';");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				} else if (e.getSource() == sbtn[3]) {
					try {
						stmt = con.createStatement();
						int st = stmt
								.executeUpdate("DELETE from stock where name = '" + stf[1].getText() + "';");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				disConnection();
			}
		}
	}

	class EmployeePanel extends JPanel {
		ActionHandler handler = new ActionHandler();
		JPanel epnl[];
		JLabel elbl[];
		JButton ebtn[];
		JTextField etf[];
		JTextArea eta[];
		String[] lbl_tf = { "ID", "�̸�", "�ּ�", "��ȭ��ȣ", "����" }; // (��ȭ��ȣ�� �ߺ����� 11�ڸ� ��� ���)
		String[] lbl_btn = { "�߰�", "��ȸ", "����", "����" };
		JTable table;
		DefaultTableModel model;

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		public EmployeePanel() {
			con = makeConnection();
			elbl = new JLabel[5];
			ebtn = new JButton[4];
			etf = new JTextField[5];
			epnl = new JPanel[3];
			setLayout(new BorderLayout());
			for (int i = 0; i < 5; i++) {
				elbl[i] = new JLabel(lbl_tf[i]);
				etf[i] = new JTextField(10);
				if (i < 4) {
					ebtn[i] = new JButton(lbl_btn[i]);
					ebtn[i].addActionListener(handler);
				}
				if (i < 3) {
					epnl[i] = new JPanel();
				}
			}
			model = new DefaultTableModel(lbl_tf, 0) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table = new JTable(model);

			add(epnl[0], BorderLayout.NORTH);
			add(epnl[1], BorderLayout.CENTER);
			add(epnl[2], BorderLayout.SOUTH);

			for (int i = 0; i < 5; i++) {
				epnl[0].add(elbl[i]);
				epnl[0].add(etf[i]);
			}
			epnl[1].add(new JScrollPane(table));
			for (int i = 0; i < 4; i++) {
				epnl[2].add(ebtn[i]);
			}
		}
		public Connection makeConnection() { // ����̺� ����
			System.out.println("���� makeconn ȣ����");
			String url = "jdbc:mysql://localhost:3306/customer?serverTimezone=Asia/Seoul";
			String id = "root";
			String password = "1249";
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("����̺� ���� ����");
				con = DriverManager.getConnection(url, id, password);
				stmt = con.createStatement();
				System.out.println("�����ͺ��̽� ���� ����");
			} catch (ClassNotFoundException e) {
				System.out.println("����̹��� ã�� �� �����ϴ�");
			} catch (SQLException e) {
				System.out.println("���ῡ �����Ͽ����ϴ�");
			}
			return con;
		}

		public void disConnection() {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		class ActionHandler implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				makeConnection();
				if (e.getSource() == ebtn[0]) {
					try {
						stmt = con.createStatement();
						int st = stmt.executeUpdate("INSERT INTO employee values ('" + etf[0].getText() + "', '"
								+ etf[1].getText() + "', '" + etf[2].getText() + "', '" + etf[3].getText()
								+ "', '" + etf[4].getText() + "')");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

				else if (e.getSource() == ebtn[1]) {
					String sql = "SELECT * FROM employee";
					String[] row = new String[5];
					model.setNumRows(0); // JTable �ʱ�ȭ
					try {
						System.out.println(sql + "\n");
						rs = stmt.executeQuery(sql);
						while (rs.next()) {
							row[0] = rs.getString("id") + "\t";
							row[1] = rs.getString("name") + "\t";
							row[2] = rs.getString("address") + "\t";
							row[3] = rs.getString("phone") + "\t";
							row[4] = rs.getString("rank") + "\n";
							model.addRow(row); // �߰�
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else if (e.getSource() == ebtn[2]) {
					try {
						stmt = con.createStatement();
						int st = stmt.executeUpdate("UPDATE employee set id = '" + etf[0].getText()
								+ "' where name = '" + etf[1].getText() + "';");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				} else if (e.getSource() == ebtn[3]) {
					try {
						stmt = con.createStatement();
						int st = stmt
								.executeUpdate("DELETE from employee where id = '" + etf[0].getText() + "';");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				disConnection();
			}
		}
	}

//	public class MarketdbView {
//		CustomerPanel cp = new CustomerPanel();
//		ActionHandler handler = new ActionHandler();
//		Connection con = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		PreparedStatement ps = null;
//
//		public MarketdbView() {
//			System.out.println("marketdbView()");
//			cp.cbtn[0].addActionListener(handler);
//			cp.cbtn[1].addActionListener(handler);
//			cp.cbtn[2].addActionListener(handler);
//			cp.cbtn[3].addActionListener(handler);
//		}
//
//		class ActionHandler implements ActionListener {
//			public void actionPerformed(ActionEvent e) {
//				makeConnection();
//				if (e.getSource() == cp.cbtn[0]) {
//					try {
//						stmt = con.createStatement();
//						int st = stmt.executeUpdate("INSERT INTO customer values ('" + cp.ctf[0].getText() + "', '"
//								+ cp.ctf[1].getText() + "', '" + cp.ctf[2].getText() + "', '" + cp.ctf[3].getText()
//								+ "', '" + cp.ctf[4].getText() + "')");
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				}
//
//				else if (e.getSource() == cp.cbtn[1]) {
//					String sql = "SELECT * FROM customer";
//					String[] row = new String[5];
//					cp.model.setNumRows(0); // JTable �ʱ�ȭ
//					try {
//						System.out.println(sql + "\n");
//						rs = stmt.executeQuery(sql);
//						while (rs.next()) {
//							row[0] = rs.getString("phone") + "\t";
//							row[1] = rs.getString("name") + "\t";
//							row[2] = rs.getString("birth") + "\t";
//							row[3] = rs.getString("sign") + "\t";
//							row[4] = rs.getString("point") + "\n";
//							cp.model.addRow(row); // �߰�
//						}
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				} else if (e.getSource() == cp.cbtn[2]) {
//					try {
//						stmt = con.createStatement();
//						int st = stmt.executeUpdate("UPDATE customer set phone = '" + cp.ctf[0].getText()
//								+ "' where name = '" + cp.ctf[1].getText() + "';");
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//
//				} else if (e.getSource() == cp.cbtn[3]) {
//					try {
//						stmt = con.createStatement();
//						int st = stmt
//								.executeUpdate("DELETE from customer where phone = '" + cp.ctf[0].getText() + "';");
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				}
//				disConnection();
//			}
//		}
//
//		public Connection makeConnection() { // ����̺� ����
//			System.out.println("makeconnȣ����");
//			String url = "jdbc:mysql://localhost:3306/customer?serverTimezone=Asia/Seoul";
//			String id = "root";
//			String password = "1249";
//			try {
//				Class.forName("com.mysql.cj.jdbc.Driver");
//				System.out.println("����̺� ���� ����");
//				con = DriverManager.getConnection(url, id, password);
//				stmt = con.createStatement();
//				System.out.println("�����ͺ��̽� ���� ����");
//			} catch (ClassNotFoundException e) {
//				System.out.println("����̹��� ã�� �� �����ϴ�");
//			} catch (SQLException e) {
//				System.out.println("���ῡ �����Ͽ����ϴ�");
//			}
//			return con;
//		}
//
//		public void disConnection() {
//			try {
//				rs.close();
//				stmt.close();
//				con.close();
//			} catch (SQLException e) {
//				System.out.println(e.getMessage());
//			}
//		}
//
//	}

	public static void main(String[] args) {
		new Marketdb();
	}
}
