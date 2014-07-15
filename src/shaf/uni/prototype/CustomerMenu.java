package shaf.uni.prototype;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;

public class CustomerMenu extends JFrame {

	//static String uniqueID = UUID.randomUUID().toString();

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerMenu frame = new CustomerMenu();
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
	public CustomerMenu() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Pictures\\New folder (3)\\knife-and-fork-logo.png"));
		setTitle("Menu's");
	//	System.out.println(uniqueID);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnStarter = new JButton("Starter");
		btnStarter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				StarterMenu sm = new StarterMenu();
				sm.frmStarterMenu.setVisible(true);
				setVisible(false);
				dispose();

			}
		});
		btnStarter.setBounds(94, 80, 120, 84);
		contentPane.add(btnStarter);

		JButton btnMains = new JButton("Mains");
		btnMains.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu sm = new MainMenu();
				sm.frameMainMenu.setVisible(true);
				setVisible(false);
			}
		});
		btnMains.setBounds(254, 83, 120, 79);
		contentPane.add(btnMains);

		JButton btnBeverages = new JButton("Beverages");
		btnBeverages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DrinkMenu frame = new DrinkMenu();
				frame.frameDrinkMenu.setVisible(true);
				//ProgrssTotal = DrinkMenu.Difference;
				setVisible(false);

			}
		});
		btnBeverages.setBounds(94, 194, 120, 79);
		contentPane.add(btnBeverages);

		JButton btnDessert = new JButton("Dessert");
		btnDessert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DessertMenu frame = new DessertMenu();
				frame.frameDessertMenu.setVisible(true);
				setVisible(false);
			}

		});
		btnDessert.setBounds(254, 194, 117, 79);
		contentPane.add(btnDessert);

		JLabel lblMainMenu = new JLabel("Main Menu");
		lblMainMenu.setFont(new Font("Times New Roman", Font.PLAIN, 33));
		lblMainMenu.setBounds(168, 23, 164, 32);
		contentPane.add(lblMainMenu);

		JButton btnBasket = new JButton("Basket " + BasketMenu.classAInstance.getLength());
		btnBasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasketMenu frameB = new BasketMenu();
				frameB.setVisible(true);
				setVisible(false);

			}
		});
		btnBasket.setBounds(388, 11, 100, 23);
		contentPane.add(btnBasket);
		
		final String Table_no =Login.classAInstance.getTable();
		JLabel lblTableNo = new JLabel("Table no: " + Table_no);
		lblTableNo.setBounds(10, 15, 100, 14);
		contentPane.add(lblTableNo);
		
	
	}
}
