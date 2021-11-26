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
		setTitle("oo���� ���� ���α׷�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		pane = createJTabbedPane();
		contentPane.add(pane,BorderLayout.CENTER);
		setSize(1280,720);
		setVisible(true);
		
	}
	JTabbedPane createJTabbedPane() {
		JTabbedPane pane = new JTabbedPane();
		//���߰�
		pane.add("������",new CustomerPanel());
		pane.add("���ǰ���",new StockPanel());
		pane.add("��������",new EmployeePanel());
		
		return pane;
	}
	
	
	class CustomerPanel extends JPanel {
		JPanel spnl[];
		JLabel clbl[];
		JButton cbtn[];
		JTextField ctf[];
		JTextArea cta[];
		String[] lbl_tf= {"��ȭ��ȣ","�̸�","�������","������","����Ʈ"}; //(��ȭ��ȣ�� ���ڸ���,�ߺ��� �������. or �ߺ����� 11�ڸ� ��� ���)
		String[] lbl_btn= {"�߰�","��ȸ","����","����"};
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
		String[] lbl_tf= {"��ǰ����","��ǰ��","����ȸ��","����"};
		String[] lbl_btn= {"�߰�","��ȸ","����","����"};
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
		String[] lbl_tf= {"ID","�̸�","�ּ�","��ȭ��ȣ","����"}; //(��ȭ��ȣ�� �ߺ����� 11�ڸ� ��� ���)
		String[] lbl_btn= {"�߰�","��ȸ","����","����"};
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
	            sp.model.setNumRows(0); // JTable �ʱ�ȭ
	            try {
	               System.out.println(sql+"\n");
	               rs=stmt.executeQuery(sql);
	               while(rs.next()) {
	                  row[0]=rs.getString("id")+"\t";
	                  row[1]=rs.getString("title")+"\t";
	                  row[2]=rs.getString("publisher")+"\t";
	                  row[3]=rs.getString("price")+"\n";
	                  sp.model.addRow(row); // �߰�
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

	
	public Connection makeConnection(){ //����̺� ����
	      String url="jdbc:mysql://localhost:3306/book_db?serverTimezone=Asia/Seoul";
	      String id="root";
	      String password="1234";
	      try{
	         Class.forName("com.mysql.cj.jdbc.Driver");
	         System.out.println("����̺� ���� ����");
	         con=DriverManager.getConnection(url, id, password);
	         stmt=con.createStatement();
	         System.out.println("�����ͺ��̽� ���� ����");
	      }catch(ClassNotFoundException e){
	         System.out.println("����̹��� ã�� �� �����ϴ�");
	      }catch(SQLException e){
	         System.out.println("���ῡ �����Ͽ����ϴ�");
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
