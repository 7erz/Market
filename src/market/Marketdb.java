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

public class Marketdb extends JFrame{
	JTabbedPane pane;
	Container contentPane;
	
	public Marketdb() {
		setTitle("oo슈퍼 관리 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		pane = createJTabbedPane();
		contentPane.add(pane,BorderLayout.CENTER);
		setSize(1280,720);
		setVisible(true);
		
	}
	JTabbedPane createJTabbedPane() {
		JTabbedPane pane = new JTabbedPane();
		//탭추가
		pane.add("고객관리",new CustomerPanel());
		pane.add("물건관리",new StockPanel());
		pane.add("직원관리",new EmployeePanel());
		
		return pane;
	}
	
	
	class CustomerPanel extends JPanel {
		JPanel spnl[];
		JLabel clbl[];
		JButton cbtn[];
		JTextField ctf[];
		JTextArea cta[];
		String[] lbl_tf= {"전화번호","이름","생년월일","가입일","포인트"}; //(전화번호는 앞자리만,중복은 상관없음. or 중복없이 11자리 모두 사용)
		String[] lbl_btn= {"추가","조회","수정","삭제"};
		JTable table;
		DefaultTableModel model;
	      
		public CustomerPanel() {
			clbl = new JLabel[5];
			cbtn = new JButton[4];
			ctf = new JTextField[5];
			spnl = new JPanel[3];
			setLayout(new BorderLayout());
			for(int i = 0;i < 5;i++) {
				clbl[i] = new JLabel(lbl_tf[i]);
				ctf[i] = new JTextField(10);
				if(i<4) {
					cbtn[i] = new JButton(lbl_btn[i]);
				}
				if(i<3) {
					spnl[i] = new JPanel();
				}
			}
			model = new DefaultTableModel(lbl_tf,0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { 
				return false;
				}
			};
			table = new JTable(model);
			
			add(spnl[0],BorderLayout.NORTH);
			add(spnl[1],BorderLayout.CENTER);
			add(spnl[2],BorderLayout.SOUTH);
			
			for(int i = 0;i<5;i++) {
				spnl[0].add(clbl[i]);
				spnl[0].add(ctf[i]);
			}
			spnl[1].add(new JScrollPane(table));
			for(int i = 0;i<4;i++) {
				spnl[2].add(cbtn[i]);
			}
		}
		
		
	}
	
	
	class StockPanel extends JPanel {
		JPanel spnl[];
		JLabel slbl[];
		JButton sbtn[];
		JTextField stf[];
		JTextArea sta[];
		String[] lbl_tf= {"상품종류","상품명","제조회사","가격"};
		String[] lbl_btn= {"추가","조회","수정","삭제"};
		JTable table;
		DefaultTableModel model;
		
		public StockPanel() {
			slbl = new JLabel[4];
			sbtn = new JButton[4];
			stf = new JTextField[5];
			spnl = new JPanel[3];
			setLayout(new BorderLayout());
			for(int i = 0;i < 4;i++) {
				stf[i] = new JTextField(10);
				sbtn[i] = new JButton(lbl_btn[i]);
				slbl[i] = new JLabel(lbl_tf[i]);
				if(i<3) {
					spnl[i] = new JPanel();
				}
			}
			model = new DefaultTableModel(lbl_tf,0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { 
				return false;
				}
			};
			table = new JTable(model);
			
			add(spnl[0],BorderLayout.NORTH);
			add(spnl[1],BorderLayout.CENTER);
			add(spnl[2],BorderLayout.SOUTH);
			
			for(int i = 0;i<4;i++) {
				spnl[0].add(slbl[i]);
				spnl[0].add(stf[i]);
			}
			spnl[1].add(new JScrollPane(table));
			for(int i = 0;i<4;i++) {
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
		String[] lbl_tf= {"ID","이름","주소","전화번호","직급"}; //(전화번호는 중복없이 11자리 모두 사용)
		String[] lbl_btn= {"추가","조회","수정","삭제"};
		JTable table;
		DefaultTableModel model;
		
		
		public EmployeePanel() {
			elbl = new JLabel[5];
			ebtn = new JButton[4];
			etf = new JTextField[5];
			epnl = new JPanel[3];
			setLayout(new BorderLayout());
			for(int i = 0;i < 5;i++) {
				elbl[i] = new JLabel(lbl_tf[i]);
				etf[i] = new JTextField(10);
				if(i<4) {
					ebtn[i] = new JButton(lbl_btn[i]);
				}
				if(i<3) {
					epnl[i] = new JPanel();
				}
			}
			model = new DefaultTableModel(lbl_tf,0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { 
				return false;
				}
			};
			table = new JTable(model);
			
			add(epnl[0],BorderLayout.NORTH);
			add(epnl[1],BorderLayout.CENTER);
			add(epnl[2],BorderLayout.SOUTH);
			
			for(int i = 0;i<5;i++) {
				epnl[0].add(elbl[i]);
				epnl[0].add(etf[i]);
			}
			epnl[1].add(new JScrollPane(table));
			for(int i = 0;i<4;i++) {
				epnl[2].add(ebtn[i]);
			}
		}
	}
	
	
	public class MarketdbView {
		   StockPanel sp = new StockPanel();
		   ActionHandler handler=new ActionHandler();
		   Connection con=null;
		   Statement stmt=null;
		   ResultSet rs=null;
		   PreparedStatement ps = null;
		   
		   public MarketdbView() {
		      sp.sbtn[0].addActionListener(handler);
		      sp.sbtn[1].addActionListener(handler);
		      sp.sbtn[2].addActionListener(handler);
		      sp.sbtn[3].addActionListener(handler);
		   }
	
	class ActionHandler implements ActionListener {
	      public void actionPerformed(ActionEvent e) {
	         makeConnection();
	         if(e.getSource()==sp.sbtn[0]) {
	            try {
	                  stmt = con.createStatement();
	                  int st = stmt.executeUpdate(
	                          "INSERT INTO book values ('" + sp.stf[0].getText() + "', '" + sp.stf[1].getText() + "', '" + sp.stf[2].getText() + "', '" + sp.stf[3].getText() + "')");
	              } catch (SQLException e1) {
	                  e1.printStackTrace();
	              }
	         }
	         
	         else if(e.getSource()==sp.sbtn[1]) {
	            String sql="SELECT * FROM book";
	            String[] row=new String[4];
	            sp.model.setNumRows(0); // JTable 초기화
	            try {
	               System.out.println(sql+"\n");
	               rs=stmt.executeQuery(sql);
	               while(rs.next()) {
	                  row[0]=rs.getString("id")+"\t";
	                  row[1]=rs.getString("title")+"\t";
	                  row[2]=rs.getString("publisher")+"\t";
	                  row[3]=rs.getString("price")+"\n";
	                  sp.model.addRow(row); // 추가
	               }
	            } catch (SQLException e1) {
	               e1.printStackTrace();
	            }
	         }
	         else if(e.getSource()==sp.sbtn[2]) {
	            try {
	                  stmt = con.createStatement();
	                  int st = stmt.executeUpdate("UPDATE book set id = '" + sp.stf[0].getText() + "' where title = '" + sp.stf[1].getText() +"';");
	              } catch (SQLException e1) {
	                  e1.printStackTrace();
	              }
	            
	         }
	         else if(e.getSource()==sp.sbtn[3]) {
	            try {
	                  stmt = con.createStatement();
	                  int st = stmt.executeUpdate("DELETE from book where id = '" + sp.stf[0].getText() + "';" );
	              } catch (SQLException e1) {
	                  e1.printStackTrace();
	              }
	         }
	         disConnection();      
	      }
	   }

	
	public Connection makeConnection(){ //드라이브 연결
	      String url="jdbc:mysql://localhost:3306/book_db?serverTimezone=Asia/Seoul";
	      String id="root";
	      String password="1234";
	      try{
	         Class.forName("com.mysql.cj.jdbc.Driver");
	         System.out.println("드라이브 적재 성공");
	         con=DriverManager.getConnection(url, id, password);
	         stmt=con.createStatement();
	         System.out.println("데이터베이스 연결 성공");
	      }catch(ClassNotFoundException e){
	         System.out.println("드라이버를 찾을 수 없습니다");
	      }catch(SQLException e){
	         System.out.println("연결에 실패하였습니다");
	      }
	      return con;
	   }

	   public void disConnection() {
	      try{
	         rs.close();
	         stmt.close();
	         con.close();
	      }catch(SQLException e){System.out.println(e.getMessage());}
	   }
	
	}
	public static void main(String[] args) {
	      new Marketdb();
	   }
}
