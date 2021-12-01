package market;
//�۾��� ��Ű�� �����
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
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
//�� ������ �̿��߱⿡ �����ӿ��� ������� ���� �гο��� ������ ó�� �� ��
	public Marketdb() {
		setTitle("oo���� ���� ���α׷�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		pane = createJTabbedPane();
		contentPane.add(pane, BorderLayout.CENTER);
		setSize(1280,720);
		setVisible(true);
	}
// �� ���� �߰�
	JTabbedPane createJTabbedPane() {
		JTabbedPane pane = new JTabbedPane();
		// ���߰�
		pane.add("��������", new PaymentPanel());
		pane.add("��������", new EmployeePanel());
		pane.add("������", new CustomerPanel());
		pane.add("��ǰ����", new StockPanel());

		return pane;
	}
//�Ʒ��� �ڵ�� DB���� DB������ ���� ��� �����ؾ� �ϴ��� ���� �Ұ� (GUI�� 456�ٷ� �̵�)
//	class CustomerPanel extends JPanel {
//		ActionHandler handler = new ActionHandler();
//		JPanel cpnl[];
//		JLabel clbl[];
//		JButton cbtn[];
//		JTextField ctf[];
//		JTextArea cta[];
//		String[] lbl_tf = { "��ȭ��ȣ", "�̸�", "�������", "������", "����Ʈ" }; // (��ȭ��ȣ�� ���ڸ���,�ߺ��� �������. or �ߺ����� 11�ڸ� ��� ���)
//		String[] lbl_btn = { "�߰�", "��ȸ", "����", "����" };
//		JTable table;
//		DefaultTableModel model;
//		
//		Connection con = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		public CustomerPanel() {
//			con = makeConnection();
//			clbl = new JLabel[5];
//			cbtn = new JButton[4];
//			ctf = new JTextField[5];
//			cpnl = new JPanel[3];
//			setLayout(new BorderLayout());
//			for (int i = 0; i < 5; i++) {
//				clbl[i] = new JLabel(lbl_tf[i]);
//				ctf[i] = new JTextField(10);
//				if (i < 4) {
//					cbtn[i] = new JButton(lbl_btn[i]);
//					cbtn[i].addActionListener(handler);
//				} 
//				if (i < 3) {
//					cpnl[i] = new JPanel();
//				}
//			}
//			model = new DefaultTableModel(lbl_tf, 0) {
//				private static final long serialVersionUID = 1L;
//
//				public boolean isCellEditable(int row, int column) {
//					return false;
//				}
//			};
//			table = new JTable(model);
//
//			add(cpnl[0], BorderLayout.NORTH);
//			add(cpnl[1], BorderLayout.CENTER);
//			add(cpnl[2], BorderLayout.SOUTH);
//
//			for (int i = 0; i < 5; i++) {
//				cpnl[0].add(clbl[i]);
//				cpnl[0].add(ctf[i]);
//			}
//			cpnl[1].add(new JScrollPane(table));
//			for (int i = 0; i < 4; i++) {
//				cpnl[2].add(cbtn[i]);
//			}
//		}
//		
//		public Connection makeConnection() { // ����̺� ����
//			System.out.println("�� makeconn ȣ����");
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
//		class ActionHandler implements ActionListener {
//			public void actionPerformed(ActionEvent e) {
//				makeConnection();
//				if (e.getSource() == cbtn[0]) {
//					try {
//						stmt = con.createStatement();
//						int st = stmt.executeUpdate("INSERT INTO customer values ('" + ctf[0].getText() + "', '"
//								+ ctf[1].getText() + "', '" + ctf[2].getText() + "', '" + ctf[3].getText()
//								+ "', '" + ctf[4].getText() + "')");
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				}
//
//				else if (e.getSource() == cbtn[1]) {
//					String sql = "SELECT * FROM customer";
//					String[] row = new String[5];
//					model.setNumRows(0); // JTable �ʱ�ȭ
//					try {
//						System.out.println(sql + "\n");
//						rs = stmt.executeQuery(sql);
//						while (rs.next()) {
//							row[0] = rs.getString("phone") + "\t";
//							row[1] = rs.getString("name") + "\t";
//							row[2] = rs.getString("birth") + "\t";
//							row[3] = rs.getString("sign") + "\t";
//							row[4] = rs.getString("point") + "\n";
//							model.addRow(row); // �߰�
//						}
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				} else if (e.getSource() == cbtn[2]) {
//					try {
//						stmt = con.createStatement();
//						int st = stmt.executeUpdate("UPDATE customer set phone = '" + ctf[0].getText()
//								+ "' where name = '" + ctf[1].getText() + "';");
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//
//				} else if (e.getSource() == cbtn[3]) {
//					try {
//						stmt = con.createStatement();
//						int st = stmt
//								.executeUpdate("DELETE from customer where phone = '" + ctf[0].getText() + "';");
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				}
//				disConnection();
//			}
//		}
//	}
//
//	class StockPanel extends JPanel {
//		ActionHandler handler = new ActionHandler();
//		JPanel spnl[];
//		JLabel slbl[];
//		JButton sbtn[];
//		JTextField stf[];
//		JTextArea sta[];
//		String[] lbl_tf = { "��ǰ����", "��ǰ��", "����ȸ��", "����" };
//		String[] lbl_btn = { "�߰�", "��ȸ", "����", "����" };
//		JTable table;
//		DefaultTableModel model;
//		
//		Connection con = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		public StockPanel() {
//			con = makeConnection();
//			slbl = new JLabel[4];
//			sbtn = new JButton[4];
//			stf = new JTextField[5];
//			spnl = new JPanel[3];
//			setLayout(new BorderLayout());
//			for (int i = 0; i < 4; i++) {
//				stf[i] = new JTextField(10);
//				sbtn[i] = new JButton(lbl_btn[i]);
//				sbtn[i].addActionListener(handler);
//				slbl[i] = new JLabel(lbl_tf[i]);
//				if (i < 3) {
//					spnl[i] = new JPanel();
//				}
//			}
//			model = new DefaultTableModel(lbl_tf, 0) {
//				private static final long serialVersionUID = 1L;
//
//				public boolean isCellEditable(int row, int column) {
//					return false;
//				}
//			};
//			table = new JTable(model);
//
//			add(spnl[0], BorderLayout.NORTH);
//			add(spnl[1], BorderLayout.CENTER);
//			add(spnl[2], BorderLayout.SOUTH);
//
//			for (int i = 0; i < 4; i++) {
//				spnl[0].add(slbl[i]);
//				spnl[0].add(stf[i]);
//			}
//			spnl[1].add(new JScrollPane(table));
//			for (int i = 0; i < 4; i++) {
//				spnl[2].add(sbtn[i]);
//			}
//		}
//		public Connection makeConnection() { // ����̺� ����
//			System.out.println("��ǰ makeconnȣ����");
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
//		class ActionHandler implements ActionListener {
//			public void actionPerformed(ActionEvent e) {
//				makeConnection();
//				if (e.getSource() == sbtn[0]) {
//					try {
//						stmt = con.createStatement();
//						int st = stmt.executeUpdate("INSERT INTO stock values ('" + stf[0].getText() + "', '"
//								+ stf[1].getText() + "', '" + stf[2].getText() + "', '" + stf[3].getText() + "')");
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				}
//
//				else if (e.getSource() == sbtn[1]) {
//					String sql = "SELECT * FROM stock";
//					String[] row = new String[4];
//					model.setNumRows(0); // JTable �ʱ�ȭ
//					try {
//						System.out.println(sql + "\n");
//						rs = stmt.executeQuery(sql);
//						while (rs.next()) {
//							row[0] = rs.getString("kinds") + "\t";
//							row[1] = rs.getString("name") + "\t";
//							row[2] = rs.getString("company") + "\t";
//							row[3] = rs.getString("price") + "\n";
//							model.addRow(row); // �߰�
//						}
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				} else if (e.getSource() == sbtn[2]) {
//					try {
//						stmt = con.createStatement();
//						int st = stmt.executeUpdate("UPDATE stock set kinds = '" + stf[0].getText()
//								+ "' where name = '" + stf[1].getText() + "';");
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//
//				} else if (e.getSource() == sbtn[3]) {
//					try {
//						stmt = con.createStatement();
//						int st = stmt
//								.executeUpdate("DELETE from stock where name = '" + stf[1].getText() + "';");
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				}
//				disConnection();
//			}
//		}
//	}
//
//	class EmployeePanel extends JPanel {
//		ActionHandler handler = new ActionHandler();
//		JPanel epnl[];
//		JLabel elbl[];
//		JButton ebtn[];
//		JTextField etf[];
//		JTextArea eta[];
//		String[] lbl_tf = { "ID", "�̸�", "�ּ�", "��ȭ��ȣ", "����" }; // (��ȭ��ȣ�� �ߺ����� 11�ڸ� ��� ���)
//		String[] lbl_btn = { "�߰�", "��ȸ", "����", "����" };
//		JTable table;
//		DefaultTableModel model;
//
//		Connection con = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		public EmployeePanel() {
//			con = makeConnection();
//			elbl = new JLabel[5];
//			ebtn = new JButton[4];
//			etf = new JTextField[5];
//			epnl = new JPanel[3];
//			setLayout(new BorderLayout());
//			for (int i = 0; i < 5; i++) {
//				elbl[i] = new JLabel(lbl_tf[i]);
//				etf[i] = new JTextField(10);
//				if (i < 4) {
//					ebtn[i] = new JButton(lbl_btn[i]);
//					ebtn[i].addActionListener(handler);
//				}
//				if (i < 3) {
//					epnl[i] = new JPanel();
//				}
//			}
//			model = new DefaultTableModel(lbl_tf, 0) {
//				private static final long serialVersionUID = 1L;
//
//				public boolean isCellEditable(int row, int column) {
//					return false;
//				}
//			};
//			table = new JTable(model);
//
//			add(epnl[0], BorderLayout.NORTH);
//			add(epnl[1], BorderLayout.CENTER);
//			add(epnl[2], BorderLayout.SOUTH);
//
//			for (int i = 0; i < 5; i++) {
//				epnl[0].add(elbl[i]);
//				epnl[0].add(etf[i]);
//			}
//			epnl[1].add(new JScrollPane(table));
//			for (int i = 0; i < 4; i++) {
//				epnl[2].add(ebtn[i]);
//			}
//		}
//		public Connection makeConnection() { // ����̺� ����
//			System.out.println("���� makeconn ȣ����");
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
//		class ActionHandler implements ActionListener {
//			public void actionPerformed(ActionEvent e) {
//				makeConnection();
//				if (e.getSource() == ebtn[0]) {
//					try {
//						stmt = con.createStatement();
//						int st = stmt.executeUpdate("INSERT INTO employee values ('" + etf[0].getText() + "', '"
//								+ etf[1].getText() + "', '" + etf[2].getText() + "', '" + etf[3].getText()
//								+ "', '" + etf[4].getText() + "')");
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				}
//
//				else if (e.getSource() == ebtn[1]) {
//					String sql = "SELECT * FROM employee";
//					String[] row = new String[5];
//					model.setNumRows(0); // JTable �ʱ�ȭ
//					try {
//						System.out.println(sql + "\n");
//						rs = stmt.executeQuery(sql);
//						while (rs.next()) {
//							row[0] = rs.getString("id") + "\t";
//							row[1] = rs.getString("name") + "\t";
//							row[2] = rs.getString("address") + "\t";
//							row[3] = rs.getString("phone") + "\t";
//							row[4] = rs.getString("rank") + "\n";
//							model.addRow(row); // �߰�
//						}
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				} else if (e.getSource() == ebtn[2]) {
//					try {
//						stmt = con.createStatement();
//						int st = stmt.executeUpdate("UPDATE employee set id = '" + etf[0].getText()
//								+ "' where name = '" + etf[1].getText() + "';");
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//
//				} else if (e.getSource() == ebtn[3]) {
//					try {
//						stmt = con.createStatement();
//						int st = stmt
//								.executeUpdate("DELETE from employee where id = '" + etf[0].getText() + "';");
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				}
//				disConnection();
//			}
//		}
//	}
//���� ���� �г� : ��ȹ�������� �ִ� ���� �״�� ��µ� ����� ���� ����
	class PaymentPanel extends JPanel {
		JPanel ppnl[];
		JLabel plbl[];
		JButton pbtn[];
		JTextField ptf[];
		JTextArea pta[];
		String[] lbl_tf1 = { "��ȣ", "��ǰ�ڵ�", "��ǰ��", "����", "�ݾ�" };
		String[] lbl_tf2 = { "��ǰ�ڵ�", "��ǰ��", "����", "�� �����ݾ�", "���� ����" };
		String[] lbl_btn = { "�߰�","����","����" };
		String[] pkind = { "����", "ī��", "����ȭ��", "����Ʈ" };
		JTable table;
		DefaultTableModel model;
//������ ������ �͵��� �Ʒ����� ������
		public PaymentPanel() {
			plbl = new JLabel[5];
			pbtn = new JButton[3];
			ptf = new JTextField[4];
			ppnl = new JPanel[3];
			setLayout(new BorderLayout());

			JComboBox<String> pcombo = new JComboBox<String>(pkind); //��������� �޺��ڽ�ȭ (�Ʒ� �гε鵵 ����ϰ� �����ص���)
// �迭�� ��ŭ for���� ���� ������
			for (int i = 0; i < 5; i++) {
				plbl[i] = new JLabel(lbl_tf2[i]);
				if (i < 4) {
					ptf[i] = new JTextField(10);
				}
				if (i < 3) {
					ppnl[i] = new JPanel();
					pbtn[i] = new JButton(lbl_btn[i]);
				}
			}
//���̵� ������� (lbl_tf1�� �������� �������)
			model = new DefaultTableModel(lbl_tf1, 0) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table = new JTable(model);
//�гο� ���̾ƿ��� ������ ���гο� �߰���			
			add(ppnl[0], BorderLayout.NORTH);
			add(ppnl[1], BorderLayout.CENTER);
			add(ppnl[2], BorderLayout.SOUTH);
//for���� �̿��� ������ ���� �������� �󺧰� ��ư ���� �߰�����
			for (int i = 0; i < 5; i++) {
				ppnl[0].add(plbl[i]);
				if (i < 4) {
					ppnl[0].add(ptf[i]);
				}
			}
			ppnl[0].add(pcombo); //�޺��ڽ��� �߰���
			ppnl[1].add(new JScrollPane(table));
			for (int i = 0; i < 3; i++) {
				ppnl[2].add(pbtn[i]);
			}
		}
	}
//�̰��� GUI�� ū Ʋ�̸� ��� �͵��� ��κ� �̷��� ����Ͽ���. DB���� �� �ڵ带 ������ ��ư�� �ڵ鷯�� �޾Ƽ� GUI�� ���� ��Ű�� ������ �Ǵ��� Ȯ�� �� ��.
//�Ʒ� �ڵ嵵 ���� ���� �гΰ� ��������� �򰥸��� ���� �ּ����� �� �޾��ֵ��� �ϰ���
	class EmployeePanel extends JPanel {
		JPanel epnl[];
		JLabel elbl[];
		JButton ebtn[];
		JTextField etf[];
		JTextArea eta[];
		String[] lbl_tf = { "ID", "�̸�", "��ȭ��ȣ", "�ּ�", "����", "�ٹ��ð�","�̸� �˻�" }; // (��ȭ��ȣ�� �ߺ����� 11�ڸ� ��� ���)
		String[] lbl_btn = { "���", "����", "����", "��ȸ" };
		String[] erank = { "�Ŵ���", "����", "�˹ٻ�" };
		String[] etime = { "��ħ��", "������", "������" };
		JTable table;
		DefaultTableModel model;

		public EmployeePanel() {
			elbl = new JLabel[7];
			ebtn = new JButton[4];
			etf = new JTextField[5];
			epnl = new JPanel[3];

			JComboBox<String> ecombo1 = new JComboBox<String>(erank);
			JComboBox<String> ecombo2 = new JComboBox<String>(etime);

			setLayout(new BorderLayout());
			for (int i = 0; i < 7; i++) {
				elbl[i] = new JLabel(lbl_tf[i]);
				if (i < 5) {
					etf[i] = new JTextField(10);
				}
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

			for (int i = 0; i < 4; i++) { // ID ���� ��ȭ��ȣ���� ������� �߰�
				epnl[0].add(elbl[i]);
				epnl[0].add(etf[i]);
			}
			// �޺��ڽ��� ���� �־� ���ް� �ٹ��ð��� ���� ������� �߰�
			epnl[0].add(elbl[4]);
			epnl[0].add(ecombo1);
			epnl[0].add(elbl[5]);
			epnl[0].add(ecombo2);

			epnl[1].add(new JScrollPane(table));
			for (int i = 0; i < 3; i++) {
				epnl[2].add(ebtn[i]);
			}
			epnl[2].add(elbl[6]);
			epnl[2].add(etf[4]);
			epnl[2].add(ebtn[3]);
		}
	}

	class CustomerPanel extends JPanel {
		JPanel cpnl[];
		JLabel clbl[];
		JButton cbtn[];
		JTextField ctf[];
		JTextArea cta[];
		String[] lbl_tf = { "ȸ����ȣ", "�̸�", "��ȭ��ȣ", "�������", "����Ʈ","�̸� �˻�" }; // (*��ȭ��ȣ�� ���ڸ���,�ߺ��� �������. or �ߺ����� 11�ڸ� ��� ���, ��ȸ�� ���ڸ� ��ȣ)
		String[] tab_tf = { "ȸ����ȣ", "�̸�", "��ȭ��ȣ", "�������", "����Ʈ"}; //(*���� ǥ��� ���ڸ� ��ȣ)
		String[] lbl_btn = { "���", "����", "����", "��ȸ" };
		String[] year = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20",
				"21","22","23","24","25","26","27","28","29","30",
				"31","32","33","34","35","36","37","38","39","40",
				"41","42","43","44","45","46","47","48","49","50",
				"51","52","53","54","55","56","57","58","59","60",
				"61","62","63","64","65","66","67","68","69","70",
				"71","72","73","74","75","76","77","78","79","80",
				"81","82","83","84","85","86","87","88","89","90",
				"91","92","93","94","95","96","97","98","99"};
		String[] month ={"01","02","03","04","05","06","07","08","09","10","11","12"};
		String[] day = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20",
				"21","22","23","24","25","26","27","28","29","30","31"};
		JTable table;
		DefaultTableModel model;

		public CustomerPanel() {
			clbl = new JLabel[6];
			cbtn = new JButton[4];
			ctf = new JTextField[4];
			cpnl = new JPanel[3];
			
			JComboBox<String> ccombo1 = new JComboBox<String>(year);
			JComboBox<String> ccombo2 = new JComboBox<String>(month);
			JComboBox<String> ccombo3 = new JComboBox<String>(day);
			
			setLayout(new BorderLayout());
			for (int i = 0; i < 6; i++) {
				clbl[i] = new JLabel(lbl_tf[i]);
//				if (i < 5) {
//					
//				}
				if (i < 4) {
					cbtn[i] = new JButton(lbl_btn[i]);
					ctf[i] = new JTextField(10);
				}
				if (i < 3) {
					cpnl[i] = new JPanel();
				}
			}
			model = new DefaultTableModel(tab_tf, 0) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table = new JTable(model);

			add(cpnl[0], BorderLayout.NORTH);
			add(cpnl[1], BorderLayout.CENTER);
			add(cpnl[2], BorderLayout.SOUTH);

			for (int i = 0; i < 3; i++) {
				cpnl[0].add(clbl[i]);
				cpnl[0].add(ctf[i]);
			}
			cpnl[0].add(clbl[3]);
			cpnl[0].add(ccombo1);
			cpnl[0].add(ccombo2);
			cpnl[0].add(ccombo3);
			
			cpnl[1].add(new JScrollPane(table));
			for (int i = 0; i < 3; i++) {
				cpnl[2].add(cbtn[i]);
			}
			cpnl[2].add(clbl[5]);
			cpnl[2].add(ctf[3]);
			cpnl[2].add(cbtn[3]);
		}
	}

	class StockPanel extends JPanel {
		JPanel spnl[];
		JLabel slbl[];
		JButton sbtn[];
		JTextField stf[];
		JTextArea sta[];
		String[] lbl_tf1 = { "��ǰ��ȣ", "��ǰ��", "����", "�������","��ǰ �˻�" };
		String[] lbl_btn = {"�԰�","���","�˻�"};
		JTable table;
		DefaultTableModel model;

		public StockPanel() {
			slbl = new JLabel[5]; //0~6
			sbtn = new JButton[3];
			stf = new JTextField[5];
			spnl = new JPanel[3];
			setLayout(new BorderLayout());
			for (int i = 0; i < 5; i++) {
				stf[i] = new JTextField(10);
				slbl[i] = new JLabel(lbl_tf1[i]);
//				if (i <4) {
//					
//					}
				if (i < 3) {
					sbtn[i] = new JButton(lbl_btn[i]);
					spnl[i] = new JPanel();
				}
			}
			
			model = new DefaultTableModel(lbl_tf1, 0) {
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
			spnl[0].add(sbtn[0]); //�԰�
			spnl[0].add(sbtn[1]); //���
			spnl[1].add(new JScrollPane(table));
			spnl[2].add(slbl[4]); //�˻�
			spnl[2].add(stf[4]);
			spnl[2].add(sbtn[2]);
		}
	}
			

	public static void main(String[] args) {
		new Marketdb();
	}
}

// DBƲ�� ®���� ���̾ƿ� ������ ���ϰ� �ϰ� ,DB�� ������ ���ߵ��� ����,���ξ��� �Ͻø� �ǿ�. Ʋ�� ¥������ �� ���̾ƿ��� FlowLayout�� ���� setBound�� ���� ��� ���� �����״� 
//������Ʈ��� �����ϰ� �Ѱܵ帱�Կ�. DB�� SQL �����Ͻð� ��Ű��,���̺� ����ɷ� ��ư �ڵ鷯�� ������� �۵��� �Ǵ��� Ȯ���� �ּ���. �ڵ鷯 �ִ� ���� �𸣽ø� 41~454���� �ڵ带 Ȯ���غ�����.
