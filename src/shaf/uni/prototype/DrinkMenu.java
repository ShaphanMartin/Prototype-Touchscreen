package shaf.uni.prototype;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DrinkMenu extends JFrame{
	public static int Difference = 0;
	 JFrame frameDrinkMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrinkMenu window = new DrinkMenu();
					window.frameDrinkMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DrinkMenu() {
		initialize();
		PopulateWindow();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameDrinkMenu = new JFrame();
		frameDrinkMenu.setTitle("Drinks Menu");
		frameDrinkMenu.setBounds(100, 100, 450, 360);
		frameDrinkMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel Button_panel = new JPanel();
		FlowLayout fl_Button_panel = (FlowLayout) Button_panel.getLayout();
		fl_Button_panel.setHgap(10);
		fl_Button_panel.setAlignment(FlowLayout.RIGHT);
		frameDrinkMenu.getContentPane().add(Button_panel, BorderLayout.SOUTH);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerMenu frame = new CustomerMenu();
				frame.setVisible(true);
				frameDrinkMenu.setVisible(false);
		
			}
		});
		Button_panel.add(btnBack);
		
		JPanel panel = new JPanel();
		frameDrinkMenu.getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{190, 37, 135, 65, 0};
		gbl_panel.rowHeights = new int[]{23, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnBasket = new JButton("Basket " + BasketMenu.classAInstance.getLength());
		btnBasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasketMenu frameB = new BasketMenu();
				frameB.setVisible(true);
				frameDrinkMenu.setVisible(false);
			}
		});
		
		
		JLabel lblDessert = new JLabel("Drink");
		GridBagConstraints gbc_lblDessert = new GridBagConstraints();
		gbc_lblDessert.anchor = GridBagConstraints.WEST;
		gbc_lblDessert.insets = new Insets(0, 0, 0, 5);
		gbc_lblDessert.gridx = 1;
		gbc_lblDessert.gridy = 0;
		panel.add(lblDessert, gbc_lblDessert);
		GridBagConstraints gbc_btnBasket = new GridBagConstraints();
		gbc_btnBasket.anchor = GridBagConstraints.WEST;
		gbc_btnBasket.gridx = 3;
		gbc_btnBasket.gridy = 0;
		panel.add(btnBasket, gbc_btnBasket);
	}
	public void PopulateWindow() {

		int MoveLblRight =50;
		int y_pos = 7;
		final int Table_no = Integer.valueOf((String) Login.classAInstance.getTable());
		int count = 0;
		int Total =0;
		JTextArea Drink_panel = new JTextArea(10, 165);
		Drink_panel.setEnabled(false);
		Drink_panel.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(Drink_panel);
		
		frameDrinkMenu.getContentPane().add(scroll, BorderLayout.CENTER);
		Drink_panel.setLayout(null);

		try {
			// accesing the driver in the Jar File
			Class.forName("com.mysql.jdbc.Driver");
			// creating a variable for the connection called con
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://mi-linux.wlv.ac.uk/db1107471", "1107471",
					"Naruto15");
			// Database , then database user , password

			// SQL query SELECT

			PreparedStatement statement = con.prepareStatement("select * from Product  WHERE Other_Details = 'Drink'");
	
			// creating a variable to execute the query
			ResultSet result = statement.executeQuery();
			
			
			while (result.next()) {

			
				String databaseDrink = result.getString("Product_Name");
				String databasePrice = result.getString("Price");
				String databaseDetails = result.getString("Other_Details");
				String databaseDescr = result.getString("Description");
				String databaseImage = result.getString("Image_Url");

				final String databaseDrink1 = databaseDrink;
				final String databasePrice1 = databasePrice;
				final String databaseDetails1 = databaseDetails;
				final String databaseDescr1 = databaseDescr;
				final String databaseImage1 = databaseImage;

				JLabel lblFood = new JLabel(databaseDrink1 +" - �" + databasePrice1);
				lblFood.setBounds(MoveLblRight, y_pos, 200, 14);
				Drink_panel.add(lblFood);
				
				JButton btnImage1 = new JButton();
				btnImage1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

					int reply =	JOptionPane.showConfirmDialog(frameDrinkMenu,databaseDescr1 + 
						                    "\nWould you like to add to Basket",databaseDrink1 +" - �" + databasePrice1, JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null);
					if (reply == JOptionPane.YES_OPTION){	
					try{
							DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Date d = new Date();
							
							Class.forName("com.mysql.jdbc.Driver");
							Connection con = DriverManager.getConnection(
									"jdbc:mysql://mi-linux.wlv.ac.uk/db1107471", "1107471",
									"Naruto15");
							PreparedStatement statement = con.prepareStatement("INSERT INTO `Basket`(`Table_No`, `In_Progress`, `Bar_Check`,`Paid`, `Product_Name`,`Price`,`Other_Details`,`Date`) "
									+ "VALUES (?,0,0,0,?,?,?,?)");
							statement.setLong(1,Table_no);
							statement.setString(2,databaseDrink1);
							statement.setString(3,databasePrice1);
							statement.setString(4,databaseDetails1);
							statement.setString(5,dateFormat.format(d));
						
							// creating a variable to execute the query
							statement.executeUpdate();
						}catch (Exception e){
							try {
								throw e;
							} catch(Exception e1){
								e1.printStackTrace();
							}
						}
					}
					}
					
				});
				count++;
				SimpleDateFormat formatter = new SimpleDateFormat("ss");
				
				btnImage1.setBounds(MoveLblRight, 20, 250, 200);
				Image image = null;
				int Start = Integer.valueOf((String) formatter.format(new Date()));
				System.out.println("before "+ count +" " + Start);
		        try {
		            URL url = new URL(databaseImage1);
		            image = ImageIO.read(url);

		        } catch (IOException e) {
		        	e.printStackTrace();
		        }
		 
				btnImage1.setIcon( new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(250, 200, btnImage1.WHEN_FOCUSED)));
				int End = Integer.valueOf((String) formatter.format(new Date()));
				System.out.println("after "+ count +" "  + End);
				Drink_panel.add(btnImage1);
				
				MoveLblRight = MoveLblRight + 300;
			
				Total = End - Start; 
				}
			
			Difference =  count/2;
			
			System.out.println(Difference);
			
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
