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
		setTitle("oo슈퍼 관리 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		pane = createJTabbedPane();
		contentPane.add(pane, BorderLayout.CENTER);
		setSize(1280, 720);
		setVisible(true);

	}

	JTabbedPane createJTabbedPane() {
		JTabbedPane pane = new JTabbedPane();
		// 탭추가
		pane.add("고객관리", new CustomerPanel());
		pane.add("물건관리", new StockPanel());
		pane.add("직원관리", new EmployeePanel());

		return pane;
	}

	class CustomerPanel extends JPanel {
		ActionHandler handler = new ActionHandler();
		JPanel cpnl[];
		JLabel clbl[];
		JButton cbtn[];
		JTextField ctf[];
		JTextArea cta[];
		String[] lbl_tf = { "전화번호", "이름", "생년월일", "가입일", "포인트" }; // (전화번호는 앞자리만,중복은 상관없음. or 중복없이 11자리 모두 사용)
		String[] lbl_btn = { "추가", "조회", "수정", "삭제" };
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
		
		public Connection makeConnection() { // 드라이브 연결
			System.out.println("makeconn호출함");
			String url = "jdbc:mysql://localhost:3306/customer?serverTimezone=Asia/Seoul";
			String id = "root";
			String password = "1249";
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("드라이브 적재 성공");
				con = DriverManager.getConnection(url, id, password);
				stmt = con.createStatement();
				System.out.println("데이터베이스 연결 성공");
			} catch (ClassNotFoundException e) {
				System.out.println("드라이버를 찾을 수 없습니다");
			} catch (SQLException e) {
				System.out.println("연결에 실패하였습니다");
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
					model.setNumRows(0); // JTable 초기화
					try {
						System.out.println(sql + "\n");
						rs = stmt.executeQuery(sql);
						while (rs.next()) {
							row[0] = rs.getString("phone") + "\t";
							row[1] = rs.getString("name") + "\t";
							row[2] = rs.getString("birth") + "\t";
							row[3] = rs.getString("sign") + "\t";
							row[4] = rs.getString("point") + "\n";
							model.addRow(row); // 추가
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
		JPanel spnl[];
		JLabel slbl[];
		JButton sbtn[];
		JTextField stf[];
		JTextArea sta[];
		String[] lbl_tf = { "상품종류", "상품명", "제조회사", "가격" };
		String[] lbl_btn = { "추가", "조회", "수정", "삭제" };
		JTable table;
		DefaultTableModel model;

		public StockPanel() {
			slbl = new JLabel[4];
			sbtn = new JButton[4];
			stf = new JTextField[5];
			spnl = new JPanel[3];
			setLayout(new BorderLayout());
			for (int i = 0; i < 4; i++) {
				stf[i] = new JTextField(10);
				sbtn[i] = new JButton(lbl_btn[i]);
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
	}

	class EmployeePanel extends JPanel {
		JPanel epnl[];
		JLabel elbl[];
		JButton ebtn[];
		JTextField etf[];
		JTextArea eta[];
		String[] lbl_tf = { "ID", "이름", "주소", "전화번호", "직급" }; // (전화번호는 중복없이 11자리 모두 사용)
		String[] lbl_btn = { "추가", "조회", "수정", "삭제" };
		JTable table;
		DefaultTableModel model;

		public EmployeePanel() {
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
	}

	public class MarketdbView {
		CustomerPanel cp = new CustomerPanel();
		ActionHandler handler = new ActionHandler();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		public MarketdbView() {
			System.out.println("marketdbView()");
			cp.cbtn[0].addActionListener(handler);
			cp.cbtn[1].addActionListener(handler);
			cp.cbtn[2].addActionListener(handler);
			cp.cbtn[3].addActionListener(handler);
		}

		class ActionHandler implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				makeConnection();
				if (e.getSource() == cp.cbtn[0]) {
					try {
						stmt = con.createStatement();
						int st = stmt.executeUpdate("INSERT INTO customer values ('" + cp.ctf[0].getText() + "', '"
								+ cp.ctf[1].getText() + "', '" + cp.ctf[2].getText() + "', '" + cp.ctf[3].getText()
								+ "', '" + cp.ctf[4].getText() + "')");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

				else if (e.getSource() == cp.cbtn[1]) {
					String sql = "SELECT * FROM customer";
					String[] row = new String[5];
					cp.model.setNumRows(0); // JTable 초기화
					try {
						System.out.println(sql + "\n");
						rs = stmt.executeQuery(sql);
						while (rs.next()) {
							row[0] = rs.getString("phone") + "\t";
							row[1] = rs.getString("name") + "\t";
							row[2] = rs.getString("birth") + "\t";
							row[3] = rs.getString("sign") + "\t";
							row[4] = rs.getString("point") + "\n";
							cp.model.addRow(row); // 추가
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else if (e.getSource() == cp.cbtn[2]) {
					try {
						stmt = con.createStatement();
						int st = stmt.executeUpdate("UPDATE customer set phone = '" + cp.ctf[0].getText()
								+ "' where name = '" + cp.ctf[1].getText() + "';");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				} else if (e.getSource() == cp.cbtn[3]) {
					try {
						stmt = con.createStatement();
						int st = stmt
								.executeUpdate("DELETE from customer where phone = '" + cp.ctf[0].getText() + "';");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				disConnection();
			}
		}

		public Connection makeConnection() { // 드라이브 연결
			System.out.println("makeconn호출함");
			String url = "jdbc:mysql://localhost:3306/customer?serverTimezone=Asia/Seoul";
			String id = "root";
			String password = "1249";
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("드라이브 적재 성공");
				con = DriverManager.getConnection(url, id, password);
				stmt = con.createStatement();
				System.out.println("데이터베이스 연결 성공");
			} catch (ClassNotFoundException e) {
				System.out.println("드라이버를 찾을 수 없습니다");
			} catch (SQLException e) {
				System.out.println("연결에 실패하였습니다");
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

	}

	public static void main(String[] args) {
		new Marketdb();
	}
}
