package shaf.uni.prototype;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Toolkit;

public class KitchenMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KitchenMenu frame = new KitchenMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public KitchenMenu() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Pictures\\New folder (3)\\knife-and-fork-logo.png"));
		setTitle("Kitchen ");
	initialize();
	CheckData();
	}
	private void Reload() {
				KitchenMenu frame = new KitchenMenu();
				frame.setVisible(false);	
				frame.setVisible(true);
				dispose();

			}
	private void initialize() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblBarOrderChecking = new JLabel("Kitchen in Progress");
		panel.add(lblBarOrderChecking);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnRecheck = new JButton("Recheck");
		btnRecheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				Reload();				
				
			}

			
		});
		
		
		panel_1.add(btnRecheck);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
	}
	
	public void CheckData(){
		int MoveLblDown = 0;
		int y_pos = 8;

		try {
			// accesing the driver in the Jar File
			Class.forName("com.mysql.jdbc.Driver");
			// creating a variable for the connection called con
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://mi-linux.wlv.ac.uk/db1107471", "1107471",
					"Naruto15");

			PreparedStatement statement = con.prepareStatement("select * from Basket  WHERE In_Progress = 0 AND Other_Details != 'Drink' ");

			// creating a variable to execute the query
			ResultSet result = statement.executeQuery();
		
		
			
			JTextArea Kitchen_panel = new JTextArea(20, 30);
			Kitchen_panel.setEnabled(false);
			Kitchen_panel.setEditable(false);
			
			getContentPane().add(Kitchen_panel, BorderLayout.CENTER);
			Kitchen_panel.setLayout(null);
			
			JScrollPane scroll = new JScrollPane(Kitchen_panel);
			getContentPane().add(scroll);
			
			JLabel lblFood1 = new JLabel("Table no.");
			lblFood1.setBounds(5, y_pos, 70, 14);
			Font font = lblFood1.getFont();
			Map attributes = font.getAttributes();
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			lblFood1.setFont(font.deriveFont(attributes));
			Kitchen_panel.add(lblFood1);
			
			JLabel lblPrice1 = new JLabel("Food");
			lblPrice1.setBounds(60, y_pos, 46, 14);
			Kitchen_panel.add(lblPrice1);
			lblPrice1.setFont(font.deriveFont(attributes));
			
			JLabel lblAdd = new JLabel("Is it?");
			lblAdd.setBounds(300, y_pos, 46, 14);
			Kitchen_panel.add(lblAdd);
			lblAdd.setFont(font.deriveFont(attributes));
			while (result.next()) {

				MoveLblDown = MoveLblDown + 25;
				String databaseDrink = result.getString("Product_Name");
				String databaseTable = result.getString("Table_No");

				final String databaseDrink1 = databaseDrink;
				final String databaseTable1 = databaseTable;
				
				JLabel lblTable = new JLabel(databaseTable);
				Kitchen_panel.add(lblTable);
				lblTable.setBounds(15, MoveLblDown, 70, 14);
				
				JLabel lblFood = new JLabel(databaseDrink);
				Kitchen_panel.add(lblFood);
				lblFood.setBounds(60, MoveLblDown, 70, 14);
				lblFood.setSize(200, 10);
				
				JButton btnPlus = new JButton("In Progress");
				btnPlus.setBounds(290, MoveLblDown, 55, 23);
				btnPlus.setSize(100, 25);

				
				Kitchen_panel.add(btnPlus);
				//event handler for inserting into the database
				btnPlus.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						try{
							Class.forName("com.mysql.jdbc.Driver");
							Connection con = DriverManager.getConnection(
									"jdbc:mysql://mi-linux.wlv.ac.uk/db1107471", "1107471",
									"Naruto15");
							PreparedStatement statement = con.prepareStatement("UPDATE `Basket` "
									 +"SET `In_Progress` =  1 " +
									  "WHERE `In_Progress` = 0 AND `Product_Name` = ? LIMIT 1");
							statement.setString(1,databaseDrink1);

							// creating a variable to execute the query
							statement.executeUpdate();
							Reload();	
						}catch (Exception e){
							try {
								throw e;
							} catch(Exception e1){
								e1.printStackTrace();
							}
						}
				
					}
				});
					
			}
			
			
			statement.close();
			con.close();
			result.close();

		} catch (Exception e) {
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}
