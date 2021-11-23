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
		JPanel cpnl[];
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
		String[] lbl_tf= {"��ǰ����","��ǰ��","����ȸ��","����"};
		String[] lbl_btn= {"�߰�","��ȸ","����","����"};
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
		String[] lbl_tf= {"ID","�̸�","�ּ�","��ȭ��ȣ","����"}; //(��ȭ��ȣ�� �ߺ����� 11�ڸ� ��� ���)
		String[] lbl_btn= {"�߰�","��ȸ","����","����"};
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
