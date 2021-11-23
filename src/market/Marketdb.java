package market;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Marketdb extends JFrame{
	JPanel pnl[]; //4?
	JLabel lbl[]; //4?
	JButton btn[]; //4
	JTextArea ta;
	JTextField tf[]; //5?
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
		JPanel cpnl[];
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
			cpnl = new JPanel[3];
			setLayout(new BorderLayout());
			for(int i = 0;i < 5;i++) {
				clbl[i] = new JLabel(lbl_tf[i]);
				ctf[i] = new JTextField(10);
				if(i<4) {
					cbtn[i] = new JButton(lbl_btn[i]);
				}
				if(i<3) {
					cpnl[i] = new JPanel();
				}
			}
			model = new DefaultTableModel(lbl_tf,0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { 
				return false;
				}
			};
			table = new JTable(model);
			
			add(cpnl[0],BorderLayout.NORTH);
			add(cpnl[1],BorderLayout.CENTER);
			add(cpnl[2],BorderLayout.SOUTH);
			
			for(int i = 0;i<5;i++) {
				cpnl[0].add(clbl[i]);
				cpnl[0].add(ctf[i]);
			}
			cpnl[1].add(new JScrollPane(table));
			for(int i = 0;i<4;i++) {
				cpnl[2].add(cbtn[i]);
			}
		}
		
		
	}
	
	
	class StockPanel extends JPanel {
		JPanel cpnl[];
		JLabel clbl[];
		JButton cbtn[];
		JTextField ctf[];
		JTextArea cta[];
		String[] lbl_tf= {"상품종류","상품명","제조회사","가격"};
		String[] lbl_btn= {"추가","조회","수정","삭제"};
		JTable table;
		DefaultTableModel model;
		
		public StockPanel() {
			clbl = new JLabel[4];
			cbtn = new JButton[4];
			ctf = new JTextField[5];
			cpnl = new JPanel[3];
			setLayout(new BorderLayout());
			for(int i = 0;i < 4;i++) {
				ctf[i] = new JTextField(10);
				cbtn[i] = new JButton(lbl_btn[i]);
				clbl[i] = new JLabel(lbl_tf[i]);
				if(i<3) {
					cpnl[i] = new JPanel();
				}
			}
			model = new DefaultTableModel(lbl_tf,0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { 
				return false;
				}
			};
			table = new JTable(model);
			
			add(cpnl[0],BorderLayout.NORTH);
			add(cpnl[1],BorderLayout.CENTER);
			add(cpnl[2],BorderLayout.SOUTH);
			
			for(int i = 0;i<4;i++) {
				cpnl[0].add(clbl[i]);
				cpnl[0].add(ctf[i]);
			}
			cpnl[1].add(new JScrollPane(table));
			for(int i = 0;i<4;i++) {
				cpnl[2].add(cbtn[i]);
			}
		}
	}
	
	
	class EmployeePanel extends JPanel {
		JPanel cpnl[];
		JLabel clbl[];
		JButton cbtn[];
		JTextField ctf[];
		JTextArea cta[];
		String[] lbl_tf= {"ID","이름","주소","전화번호","직급"}; //(전화번호는 중복없이 11자리 모두 사용)
		String[] lbl_btn= {"추가","조회","수정","삭제"};
		JTable table;
		DefaultTableModel model;
		
		
		public EmployeePanel() {
			clbl = new JLabel[5];
			cbtn = new JButton[4];
			ctf = new JTextField[5];
			cpnl = new JPanel[3];
			setLayout(new BorderLayout());
			for(int i = 0;i < 5;i++) {
				clbl[i] = new JLabel(lbl_tf[i]);
				ctf[i] = new JTextField(10);
				if(i<4) {
					cbtn[i] = new JButton(lbl_btn[i]);
				}
				if(i<3) {
					cpnl[i] = new JPanel();
				}
			}
			model = new DefaultTableModel(lbl_tf,0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { 
				return false;
				}
			};
			table = new JTable(model);
			
			add(cpnl[0],BorderLayout.NORTH);
			add(cpnl[1],BorderLayout.CENTER);
			add(cpnl[2],BorderLayout.SOUTH);
			
			for(int i = 0;i<5;i++) {
				cpnl[0].add(clbl[i]);
				cpnl[0].add(ctf[i]);
			}
			cpnl[1].add(new JScrollPane(table));
			for(int i = 0;i<4;i++) {
				cpnl[2].add(cbtn[i]);
			}
		}
	}
	
	
	
	public static void main(String[] args) {
	      new Marketdb();
	   }
}
