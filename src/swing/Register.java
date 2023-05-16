package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Register implements ActionListener{

	
	JLabel l1,l2,l3,l4,l5,l6;
	JTextField t1,t2,t3,t4;
	JButton b1,b2,b3,b4,b5,b6;
	JRadioButton r1,r2;
	ButtonGroup bg1;
	JTable tb1;
	JScrollPane sp;
	
	
	
	public Register()
	{
		JFrame fr = new JFrame("REGISTRATION FORM");
		fr.setVisible(true);
		fr.setSize(1200,1000);
		fr.setLayout(null);
		
		l1 = new JLabel("Registration Form");
		l1.setBounds(160, 40, 140, 40);
		fr.add(l1);
		
		l2 = new JLabel("ID");
		l2.setBounds(100, 100, 120, 20);
		fr.add(l2);
		
		l3 = new JLabel("Name");
		l3.setBounds(100, 130, 120, 20);
		fr.add(l3);
		
		l4 = new JLabel("Gender");
		l4.setBounds(100, 160, 120, 20);
		fr.add(l4);
		
		l5 = new JLabel("Address");
		l5.setBounds(100, 190, 120, 20);
		fr.add(l5);
		
		l6 = new JLabel("Contact");
		l6.setBounds(100, 220, 120, 20);
		fr.add(l6);
		
		t1 = new JTextField();
		t1.setBounds(200, 100, 140, 20);
		fr.add(t1);
		
		t2 = new JTextField();
		t2.setBounds(200, 130, 140, 20);
		fr.add(t2);
		
		t3 = new JTextField();
		t3.setBounds(200, 190, 140, 20);
		fr.add(t3);
		
		t4 = new JTextField();
		t4.setBounds(200, 220, 140, 20);
		fr.add(t4);
		
		b1 = new JButton("Exit");
		b1.setBounds(100, 270, 120, 20);
		fr.add(b1);
		
		b2 = new JButton("Register");
		b2.setBounds(230, 270, 120, 20);
		fr.add(b2);
		
		b3 = new JButton("Delete");
		b3.setBounds(100, 300, 120, 20);
		fr.add(b3);
		
		b4 = new JButton("Update");
		b4.setBounds(230, 300, 120, 20);
		fr.add(b4);
		
		b5 = new JButton("Reset");
		b5.setBounds(170, 330, 120, 20);
		fr.add(b5);
		
		b6 = new JButton("Refresh Table");
		b6.setBounds(100, 360, 250, 30);
		fr.add(b6);
		
		r1 = new JRadioButton("Male");
		r1.setBounds(200, 160, 70, 20);
		fr.add(r1);
		
		r2 = new JRadioButton("Female");
		r2.setBounds(270, 160, 90, 20);
		fr.add(r2);
		
		bg1 = new ButtonGroup();
		bg1.add(r1);
		bg1.add(r2);
		
		sp = new JScrollPane();
		sp.setBounds(460, 70, 500, 400);
		fr.add(sp);
		
		tb1 = new JTable();
		sp.setViewportView(tb1);
		
		try {
			
			Connection conn = Register.createConnection();
			String sql = "select * from student";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			DefaultTableModel model = (DefaultTableModel)tb1.getModel();
			
			int cols = rsmd.getColumnCount();
			String[] colName = new String[cols];
			for(int i=0;i<cols;i++)
			{
				colName[i] = rsmd.getColumnName(i+1);
			}
			model.setColumnIdentifiers(colName);
			
			String S_No,ID,Name,Gender,Address,Contact;
			
			while(rs.next())
			{
				S_No = String.valueOf(rs.getInt("S_No"));
				ID = String.valueOf(rs.getInt("ID"));
				Name = rs.getString("Name");
				Gender = rs.getString("Gender");
				Address = rs.getString("Address");
				Contact = String.valueOf(rs.getLong("Contact"));
				String row[] = {S_No,ID,Name,Gender,Address,Contact};
				model.addRow(row);
			}
			
			int i = tb1.getSelectedRow();
			TableModel model1 = tb1.getModel();
			t1.setText(model1.getValueAt(i, 1).toString());
			t2.setText(model1.getValueAt(i, 2).toString());
			String gender = model1.getValueAt(i, 3).toString();
				if(gender.equals("Male")) {
					r1.setSelected(true);
				}
				else {
					r2.setSelected(true);
				}
			t3.setText(model1.getValueAt(i, 4).toString());
			t4.setText(model1.getValueAt(i, 5).toString());
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		
	}
	
	public static void main(String[] args) {
		new Register();
	}

	public static Connection createConnection()
	{
		Connection conn = null;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/swingAss","root","");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == b1)
		{
			System.out.println("Exit button clicked");
			System.exit(0);
		}
		else if(e.getSource() == b2)
		{
			System.out.println("Register button clicked");
			int id = Integer.parseInt(t1.getText());
			String name = t2.getText();
			String gender="";
			if(r1.isSelected())
			{
				gender = "Male";
			}
			else if(r2.isSelected())
			{
				gender = "Female";
			}
			String address = t3.getText();
			Long contact = Long.parseLong(t4.getText());
			
			try {
				
				Connection conn = Register.createConnection();
				String sql = "insert into student(ID,Name,Gender,Address,Contact) values(?,?,?,?,?)";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setInt(1, id);
				pst.setString(2, name);
				pst.setString(3, gender);
				pst.setString(4, address);
				pst.setLong(5, contact);
				pst.executeUpdate();
				System.out.println("Data inserted Successfully");
				JOptionPane.showMessageDialog(null, "Registration Successful");
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		else if(e.getSource() == b3)
		{
			System.out.println("Delete button clicked");
		}
		else if(e.getSource() == b4)
		{
			System.out.println("Update button clicked");
		}
		else if(e.getSource() == b5)
		{
			System.out.println("Reset Button clicked");
			t1.setText("");
			t2.setText("");
			bg1.clearSelection();
			t3.setText("");
			t4.setText("");
		}
		else if(e.getSource() == b6)
		{
			DefaultTableModel tbmodel = (DefaultTableModel)tb1.getModel();
			tbmodel.setRowCount(0);
			try {
				
				Connection conn = Register.createConnection();
				String sql = "select * from student";
				PreparedStatement pst = conn.prepareStatement(sql);
				ResultSet rs = pst.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				DefaultTableModel model = (DefaultTableModel)tb1.getModel();
				
				int cols = rsmd.getColumnCount();
				String[] colName = new String[cols];
				for(int i=0;i<cols;i++)
				{
					colName[i] = rsmd.getColumnName(i+1);
				}
				model.setColumnIdentifiers(colName);
				
				String S_No,ID,Name,Gender,Address,Contact;
				
				while(rs.next())
				{
					S_No = String.valueOf(rs.getInt("S_No"));
					ID = String.valueOf(rs.getInt("ID"));
					Name = rs.getString("Name");
					Gender = rs.getString("Gender");
					Address = rs.getString("Address");
					Contact = String.valueOf(rs.getLong("Contact"));
					String row[] = {S_No,ID,Name,Gender,Address,Contact};
					model.addRow(row);
				}
				
				
				
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		}
	}
	
	
}
