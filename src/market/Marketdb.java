package market;
//작업시 패키지 맞출것
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
//탭 패인을 이용했기에 프레임에서 사용하지 말고 패널에서 모든것을 처리 할 것
	public Marketdb() {
		setTitle("oo슈퍼 관리 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		pane = createJTabbedPane();
		contentPane.add(pane, BorderLayout.CENTER);
		setSize(1280,720);
		setVisible(true);
	}
// 탭 패인 추가
	JTabbedPane createJTabbedPane() {
		JTabbedPane pane = new JTabbedPane();
		// 탭추가
		pane.add("결제관리", new PaymentPanel());
		pane.add("직원관리", new EmployeePanel());
		pane.add("고객관리", new CustomerPanel());
		pane.add("상품관리", new StockPanel());

		return pane;
	}
//아래의 코드는 DB팀이 DB연동으 위해 어떻게 연결해야 하는지 참고 할것 (GUI는 456줄로 이동)
//	class CustomerPanel extends JPanel {
//		ActionHandler handler = new ActionHandler();
//		JPanel cpnl[];
//		JLabel clbl[];
//		JButton cbtn[];
//		JTextField ctf[];
//		JTextArea cta[];
//		String[] lbl_tf = { "전화번호", "이름", "생년월일", "가입일", "포인트" }; // (전화번호는 앞자리만,중복은 상관없음. or 중복없이 11자리 모두 사용)
//		String[] lbl_btn = { "추가", "조회", "수정", "삭제" };
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
//		public Connection makeConnection() { // 드라이브 연결
//			System.out.println("고객 makeconn 호출함");
//			String url = "jdbc:mysql://localhost:3306/customer?serverTimezone=Asia/Seoul";
//			String id = "root";
//			String password = "1249";
//			try {
//				Class.forName("com.mysql.cj.jdbc.Driver");
//				System.out.println("드라이브 적재 성공");
//				con = DriverManager.getConnection(url, id, password);
//				stmt = con.createStatement();
//				System.out.println("데이터베이스 연결 성공");
//			} catch (ClassNotFoundException e) {
//				System.out.println("드라이버를 찾을 수 없습니다");
//			} catch (SQLException e) {
//				System.out.println("연결에 실패하였습니다");
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
//					model.setNumRows(0); // JTable 초기화
//					try {
//						System.out.println(sql + "\n");
//						rs = stmt.executeQuery(sql);
//						while (rs.next()) {
//							row[0] = rs.getString("phone") + "\t";
//							row[1] = rs.getString("name") + "\t";
//							row[2] = rs.getString("birth") + "\t";
//							row[3] = rs.getString("sign") + "\t";
//							row[4] = rs.getString("point") + "\n";
//							model.addRow(row); // 추가
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
//		String[] lbl_tf = { "상품종류", "상품명", "제조회사", "가격" };
//		String[] lbl_btn = { "추가", "조회", "수정", "삭제" };
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
//		public Connection makeConnection() { // 드라이브 연결
//			System.out.println("상품 makeconn호출함");
//			String url = "jdbc:mysql://localhost:3306/customer?serverTimezone=Asia/Seoul";
//			String id = "root";
//			String password = "1249";
//			try {
//				Class.forName("com.mysql.cj.jdbc.Driver");
//				System.out.println("드라이브 적재 성공");
//				con = DriverManager.getConnection(url, id, password);
//				stmt = con.createStatement();
//				System.out.println("데이터베이스 연결 성공");
//			} catch (ClassNotFoundException e) {
//				System.out.println("드라이버를 찾을 수 없습니다");
//			} catch (SQLException e) {
//				System.out.println("연결에 실패하였습니다");
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
//					model.setNumRows(0); // JTable 초기화
//					try {
//						System.out.println(sql + "\n");
//						rs = stmt.executeQuery(sql);
//						while (rs.next()) {
//							row[0] = rs.getString("kinds") + "\t";
//							row[1] = rs.getString("name") + "\t";
//							row[2] = rs.getString("company") + "\t";
//							row[3] = rs.getString("price") + "\n";
//							model.addRow(row); // 추가
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
//		String[] lbl_tf = { "ID", "이름", "주소", "전화번호", "직급" }; // (전화번호는 중복없이 11자리 모두 사용)
//		String[] lbl_btn = { "추가", "조회", "수정", "삭제" };
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
//		public Connection makeConnection() { // 드라이브 연결
//			System.out.println("직원 makeconn 호출함");
//			String url = "jdbc:mysql://localhost:3306/customer?serverTimezone=Asia/Seoul";
//			String id = "root";
//			String password = "1249";
//			try {
//				Class.forName("com.mysql.cj.jdbc.Driver");
//				System.out.println("드라이브 적재 성공");
//				con = DriverManager.getConnection(url, id, password);
//				stmt = con.createStatement();
//				System.out.println("데이터베이스 연결 성공");
//			} catch (ClassNotFoundException e) {
//				System.out.println("드라이버를 찾을 수 없습니다");
//			} catch (SQLException e) {
//				System.out.println("연결에 실패하였습니다");
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
//					model.setNumRows(0); // JTable 초기화
//					try {
//						System.out.println(sql + "\n");
//						rs = stmt.executeQuery(sql);
//						while (rs.next()) {
//							row[0] = rs.getString("id") + "\t";
//							row[1] = rs.getString("name") + "\t";
//							row[2] = rs.getString("address") + "\t";
//							row[3] = rs.getString("phone") + "\t";
//							row[4] = rs.getString("rank") + "\n";
//							model.addRow(row); // 추가
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
//결제 관리 패널 : 계획페이지에 있는 것을 그대로 썼는데 어색한 면이 있음
	class PaymentPanel extends JPanel {
		JPanel ppnl[];
		JLabel plbl[];
		JButton pbtn[];
		JTextField ptf[];
		JTextArea pta[];
		String[] lbl_tf1 = { "번호", "상품코드", "상품명", "수량", "금액" };
		String[] lbl_tf2 = { "상품코드", "상품명", "수량", "총 결제금액", "결제 수단" };
		String[] lbl_btn = { "추가","삭제","결제" };
		String[] pkind = { "현금", "카드", "지역화페", "포인트" };
		JTable table;
		DefaultTableModel model;
//위에서 지정한 것들을 아래에서 지정함
		public PaymentPanel() {
			plbl = new JLabel[5];
			pbtn = new JButton[3];
			ptf = new JTextField[4];
			ppnl = new JPanel[3];
			setLayout(new BorderLayout());

			JComboBox<String> pcombo = new JComboBox<String>(pkind); //결제방법의 콤보박스화 (아래 패널들도 비슷하게 설정해뒀음)
// 배열수 만큼 for문을 돌려 제작함
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
//테이도 만들었음 (lbl_tf1을 기준으로 만들었음)
			model = new DefaultTableModel(lbl_tf1, 0) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table = new JTable(model);
//패널에 레이아웃을 지정해 탭패널에 추가함			
			add(ppnl[0], BorderLayout.NORTH);
			add(ppnl[1], BorderLayout.CENTER);
			add(ppnl[2], BorderLayout.SOUTH);
//for문을 이용해 위에서 만든 정보들을 라벨과 버튼 등을 추가했음
			for (int i = 0; i < 5; i++) {
				ppnl[0].add(plbl[i]);
				if (i < 4) {
					ppnl[0].add(ptf[i]);
				}
			}
			ppnl[0].add(pcombo); //콤보박스도 추가함
			ppnl[1].add(new JScrollPane(table));
			for (int i = 0; i < 3; i++) {
				ppnl[2].add(pbtn[i]);
			}
		}
	}
//이것이 GUI의 큰 틀이며 모든 것들을 대부분 이렇게 사용하였음. DB팀은 이 코드를 가지고 버튼에 핸들러를 달아서 GUI에 연결 시키고 연동이 되는지 확인 할 것.
//아래 코드도 위의 결제 패널과 비슷하지만 헷갈리는 것은 주석으로 다 달아주도록 하겠음
	class EmployeePanel extends JPanel {
		JPanel epnl[];
		JLabel elbl[];
		JButton ebtn[];
		JTextField etf[];
		JTextArea eta[];
		String[] lbl_tf = { "ID", "이름", "전화번호", "주소", "직급", "근무시간","이름 검색" }; // (전화번호는 중복없이 11자리 모두 사용)
		String[] lbl_btn = { "등록", "수정", "삭제", "조회" };
		String[] erank = { "매니저", "직원", "알바생" };
		String[] etime = { "아침조", "점심조", "마감조" };
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

			for (int i = 0; i < 4; i++) { // ID 부터 전화번호까지 순서대로 추가
				epnl[0].add(elbl[i]);
				epnl[0].add(etf[i]);
			}
			// 콤보박스가 따로 있어 직급과 근무시간은 따로 순서대로 추가
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
		String[] lbl_tf = { "회원번호", "이름", "전화번호", "생년월일", "포인트","이름 검색" }; // (*전화번호는 앞자리만,중복은 상관없음. or 중복없이 11자리 모두 사용, 조회는 전자를 선호)
		String[] tab_tf = { "회원번호", "이름", "전화번호", "생년월일", "포인트"}; //(*정보 표기는 후자를 선호)
		String[] lbl_btn = { "등록", "수정", "삭제", "조회" };
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
		String[] lbl_tf1 = { "상품번호", "상품명", "수량", "유통기한","물품 검색" };
		String[] lbl_btn = {"입고","출고","검색"};
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
			spnl[0].add(sbtn[0]); //입고
			spnl[0].add(sbtn[1]); //출고
			spnl[1].add(new JScrollPane(table));
			spnl[2].add(slbl[4]); //검색
			spnl[2].add(stf[4]);
			spnl[2].add(sbtn[2]);
		}
	}
			

	public static void main(String[] args) {
		new Marketdb();
	}
}

// DB틀을 짰으니 레이아웃 정리는 인하가 하고 ,DB는 위에서 말했듯이 성찬,성민씨가 하시면 되요. 틀을 짜뒀으니 뭐 레이아웃은 FlowLayout을 쓰든 setBound를 쓰든 상관 쓰기 않을테니 
//쉬운파트라고 생각하고 넘겨드릴게요. DB는 SQL 연결하시고 스키마,테이블 만든걸로 버튼 핸들러로 연결시켜 작동이 되는지 확인해 주세요. 핸들러 넣는 법을 모르시면 41~454줄의 코드를 확인해보세요.
