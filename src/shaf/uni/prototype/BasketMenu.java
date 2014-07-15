package shaf.uni.prototype;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;

public class BasketMenu extends JFrame {
	
	public static BasketMenu classAInstance = new BasketMenu();
	private static int length;
	double totalprice = 0;
	String uniqueID = UUID.randomUUID().toString();

	public static int getLength() {
		return length;
	}

	public static void setLength(int length) {
		BasketMenu.length = length;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel frameBasketMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BasketMenu window = new BasketMenu();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});   
	}

	/**
	 * Create the frame.
	 */
	public BasketMenu() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Pictures\\New folder (3)\\knife-and-fork-logo.png"));
		setTitle("Basket");
		initialize();
		PopulateWindow();

	}
	private void Reload() {
		BasketMenu window = new BasketMenu();
		window.setVisible(false);
		window.setVisible(true);
		dispose();
		
	}
	private void initialize() {
		//frameBasketMenu = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		frameBasketMenu = new JPanel();
		frameBasketMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
		frameBasketMenu.setLayout(new BorderLayout(0, 0));
		setContentPane(frameBasketMenu);
		

		JPanel Button_panel = new JPanel();
		frameBasketMenu.add(Button_panel, BorderLayout.SOUTH);
		GridBagLayout gbl_Button_panel = new GridBagLayout();
		gbl_Button_panel.columnWidths = new int[] {10, 138, 0, 30, 30, 0, 138, 30, 30, 0, 138, 10};
		gbl_Button_panel.rowHeights = new int[]{23, 0};
		gbl_Button_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_Button_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		Button_panel.setLayout(gbl_Button_panel);
		
		

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reload();
			}


		});
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.fill = GridBagConstraints.BOTH;
		gbc_btnRefresh.insets = new Insets(0, 0, 0, 5);
		gbc_btnRefresh.gridx = 2;
		gbc_btnRefresh.gridy = 0;
		Button_panel.add(btnRefresh, gbc_btnRefresh);

		JButton btnRequestTheBill = new JButton("Checkout");
		btnRequestTheBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date d = new Date();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection(
							"jdbc:mysql://mi-linux.wlv.ac.uk/db1107471", "1107471",
							"Naruto15");
					PreparedStatement UpdateSql = con.prepareStatement("UPDATE `Basket` "
							 +"SET `Paid` =  1 " +
							  "WHERE `Paid` = 0 AND `Table_No` = ?");
					UpdateSql.setString(1,Login.classAInstance.getTable());
					PreparedStatement InsertSql = con.prepareStatement("INSERT INTO `Orders`(`Customer_Id`, `Total Price`,`Date`) "
							+ "VALUES (?,?,?)");
					InsertSql.setString(1, uniqueID);
					InsertSql.setDouble(2, Double.valueOf(totalprice));
					InsertSql.setString(3,dateFormat.format(d));
					
					// creating a variable to execute the query
					PreparedStatement RunningTotalSql = con.prepareStatement(
					"SELECT a.Meal_ID, a.`Total Price`, SUM(b.`Total Price`)" +
					"FROM Orders a, Orders b " +
					"WHERE b.Meal_ID <= a.Meal_ID "+
					"GROUP BY a.Meal_ID, a.`Total Price` "+
					"ORDER BY a.Meal_ID");
					
					RunningTotalSql.executeQuery();
					UpdateSql.executeUpdate();
					InsertSql.executeUpdate();
					BasketMenu.setLength(0);

					Reload();

				}catch (Exception e1){
					try {
						throw e1;
					} catch(Exception e11){
						e11.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(frameBasketMenu, "Your bill is being processed, please wait \n a waiter will be with you shortly.");
				
			}
		});
		GridBagConstraints gbc_btnRequestTheBill = new GridBagConstraints();
		gbc_btnRequestTheBill.insets = new Insets(0, 0, 0, 5);
		gbc_btnRequestTheBill.gridx = 6;
		gbc_btnRequestTheBill.gridy = 0;
		Button_panel.add(btnRequestTheBill, gbc_btnRequestTheBill);

		JButton btnBack = new JButton("Back");
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.fill = GridBagConstraints.BOTH;
		gbc_btnBack.gridx = 9;
		gbc_btnBack.gridy = 0;
		Button_panel.add(btnBack, gbc_btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerMenu frame = new CustomerMenu();
				frame.setVisible(true);
				setVisible(false);
			}
		});

		JPanel panel = new JPanel();
		frameBasketMenu.add(panel, BorderLayout.NORTH);

		JLabel lblDrinks = new JLabel("Basket");
		panel.add(lblDrinks);
	}


	public void PopulateWindow() {
		double [] arrayPrice = new double[100] ;
		int MoveLblDown = 0;
		int y_pos = 8;
		int count = 0;
		final int Table_no = Integer.valueOf((String) Login.classAInstance.getTable());
		try {
			// accesing the driver in the Jar File
			Class.forName("com.mysql.jdbc.Driver");
			// creating a variable for the connection called con
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://mi-linux.wlv.ac.uk/db1107471", "1107471",
					"Naruto15");

			PreparedStatement statement = con.prepareStatement("select * from Basket  WHERE Table_No = ? AND (In_Progress =? AND Bar_Check =? AND Paid = ?)");
			//change this for the actual table number
			statement.setLong(1,Table_no);
			statement.setString(2,"0");
			statement.setString(3,"0");
			statement.setString(4,"0");
			// creating a variable to execute the query
			ResultSet result = statement.executeQuery();

			//JPanel Basket_panel = new JPanel();
			JTextArea Basket_panel = new JTextArea(50, 30);
			Basket_panel.setEnabled(false);
			Basket_panel.setEditable(false);


			getContentPane().add(Basket_panel, BorderLayout.CENTER);
			Basket_panel.setLayout(null);

			JScrollPane scroll = new JScrollPane(Basket_panel);
			getContentPane().add(scroll);
			
			JLabel lblTable = new JLabel("Table " + Table_no);
			lblTable.setBounds(0, y_pos, 70, 14);
			Basket_panel.add(lblTable);
			lblTable.setSize(200, 10);
			
			JLabel lblOrder = new JLabel("Order");
			lblOrder.setBounds(50, y_pos, 70, 14);
			Font font = lblOrder.getFont();
			Map attributes = font.getAttributes();
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			lblOrder.setFont(font.deriveFont(attributes));
			Basket_panel.add(lblOrder);

			JLabel lblPrice1 = new JLabel("Price");
			lblPrice1.setBounds(199, y_pos, 46, 14);
			Basket_panel.add(lblPrice1);
			lblPrice1.setFont(font.deriveFont(attributes));

			JLabel lblAdd = new JLabel("Add");
			lblAdd.setBounds(240, y_pos, 46, 14);
			Basket_panel.add(lblAdd);
			lblAdd.setFont(font.deriveFont(attributes));

			JLabel lblMinus = new JLabel("Minus");
			lblMinus.setBounds(300, y_pos, 46, 14);
			Basket_panel.add(lblMinus);
			lblMinus.setFont(font.deriveFont(attributes));

			while (result.next()) {
				
				count++;
				MoveLblDown = MoveLblDown + 25;
				String databaseDrink = result.getString("Product_Name");
				String databasePrice = result.getString("Price");
				String databaseDetails = result.getString("Other_Details");

				final String databaseDrink1 = databaseDrink;
				final String databasePrice1 = databasePrice;
				final String databaseDetails1 = databaseDetails;

				JLabel lblFood = new JLabel(databaseDrink);
				Basket_panel.add(lblFood);
				lblFood.setBounds(50, MoveLblDown, 70, 14);
				lblFood.setSize(200, 10);

				JLabel lblPrice = new JLabel("£"+databasePrice);
				lblPrice.setBounds(199, MoveLblDown, 46, 14);
				Basket_panel.add(lblPrice);

				JButton btnPlus = new JButton("+");
				btnPlus.setBounds(240, MoveLblDown, 55, 23);
				Basket_panel.add(btnPlus);

				//event handler for inserting into the database
				btnPlus.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

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

				BasketMenu.setLength(count);
				
				JButton btnMinus = new JButton("-");
				btnMinus.setBounds(300, MoveLblDown, 55, 23);
				Basket_panel.add(btnMinus);

				//event handler for Removing from the basket database
				btnMinus.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						try{

							Class.forName("com.mysql.jdbc.Driver");
							Connection con = DriverManager.getConnection(
									"jdbc:mysql://mi-linux.wlv.ac.uk/db1107471", "1107471",
									"Naruto15");
							PreparedStatement statement = con.prepareStatement("DELETE FROM `Basket` WHERE `Product_Name` = ? ORDER BY `Basket_ID` DESC LIMIT 1");
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
				//String tmp = databasePrice;

				arrayPrice[count] = result.getDouble("Price");

				//System.out.println(totalprice);
			}

			for (double i :arrayPrice)
				totalprice +=i;
			int BottomOfBasket = (int) ((count*23.2)+100);
		
			DecimalFormat df = new DecimalFormat("#.##");      
			totalprice = Double.valueOf(df.format(totalprice));
			
			JLabel lblPrice = new JLabel("£"+totalprice);
			lblPrice.setBounds(199, BottomOfBasket, 46, 14);
			Basket_panel.add(lblPrice);
			lblPrice.setSize(200, 10);
			
			JLabel lblFood = new JLabel("Total Price");
			Basket_panel.add(lblFood);
			lblFood.setBounds(50, BottomOfBasket, 70, 14);
			lblFood.setSize(200, 10);
			
			//System.out.println(totalprice);
			//System.out.println(length);
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
