package shaf.uni.prototype;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import java.awt.Toolkit;

public class Login extends JFrame {
	public static Login classAInstance = new Login();

	private JPanel contentPane;
	private static JTextField textUsername;
	private static JTextField textPassword;
	String a = "1";

	private static String tableSelected;

	public static void setTable(String Table){
		tableSelected = Table;
	}
	public String getTable() {
		return tableSelected;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Login frame = new Login();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */


	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Pictures\\New folder (3)\\knife-and-fork-logo.png"));
		setTitle("Login");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(147, 63, 75, 14);
		panel.add(lblUsername);

		textUsername = new JTextField();
		//textUsername.setText("shaf");
		textUsername.setBounds(228, 60, 86, 20);
		panel.add(textUsername);
		textUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(147, 105, 75, 14);
		panel.add(lblPassword);

		textPassword = new JPasswordField();
		//textPassword.setText("shafpass");
		textPassword.setBounds(228, 102, 86, 20);
		panel.add(textPassword);
		textPassword.setColumns(10);

		JLabel lblTable = new JLabel("Select Table");
		lblTable.setBounds(147, 158, 75, 14);
		panel.add(lblTable);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String puname = textUsername.getText();
				String ppaswd = textPassword.getText();
				try{

					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection(
							"jdbc:mysql://mi-linux.wlv.ac.uk/db1107471", "1107471",
							"Naruto15");
					PreparedStatement statement = con.prepareStatement("select * from Users where Username='"+puname+"'");


					// creating a variable to execute the query
					ResultSet result = statement.executeQuery();

					if(result.next())
					{

						String databasePassword = result.getString("Password");
						if(databasePassword.equals(ppaswd)){
							JOptionPane.showMessageDialog(contentPane,"Login Successful! ","Success",JOptionPane.PLAIN_MESSAGE);
							CustomerMenu regFace =new CustomerMenu();
							regFace.setVisible(true);
							dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(contentPane,"Login Unsuccessful!","Error",1);
							textUsername.setText("");
							textPassword.setText("");
							textUsername.requestFocus();
						}
					}
					else
					{
						JOptionPane.showMessageDialog(contentPane,"Username not found","Error",1);
						textUsername.setText("");
						textPassword.setText("");
						textUsername.requestFocus();
					}

				}catch (Exception e){
					try {
						throw e;
					} catch(Exception e1){
						e1.printStackTrace();
					}
				}	
			}
		});

		btnLogin.setBounds(147, 205, 100, 23);
		panel.add(btnLogin);

		panel.getRootPane().setDefaultButton(btnLogin);
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboBox.setBounds(228, 155, 50, 20);
		panel.add(comboBox);


		comboBox.addActionListener(new ActionListener() {		

			public void actionPerformed(ActionEvent e) {


				a =(String) comboBox.getSelectedItem();
				Login.setTable(a);
				//System.out.println(a);

			}
		});
		Login.setTable(a);


	}



	class Mylistener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
