import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class WordOccuranceGUI {

	private JFrame frmTextAnalyzer;

	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WordOccuranceGUI window = new WordOccuranceGUI();
					window.frmTextAnalyzer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 

	/**
	 * Create the application.
	 */
	public WordOccuranceGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTextAnalyzer = new JFrame();
		frmTextAnalyzer.setTitle("Text Analyzer");
		frmTextAnalyzer.getContentPane().setBackground(Color.BLACK);
		frmTextAnalyzer.getContentPane().setForeground(Color.BLACK);
		frmTextAnalyzer.setBounds(100, 100, 683, 417);
		frmTextAnalyzer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTextAnalyzer.getContentPane().setLayout(null);
		
		File input = new File("input.txt");
		File test1 = new File("test1.txt");

		
		JButton start = new JButton("Begin");
		
		start.setBounds(124, 262, 124, 46);
		frmTextAnalyzer.getContentPane().add(start);
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setBounds(442, 262, 111, 46);
		frmTextAnalyzer.getContentPane().add(exit);
		
		JLabel title2 = new JLabel("By Mohamed Hamdy");
		title2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		title2.setForeground(Color.RED);
		title2.setBounds(442, 144, 180, 31);
		frmTextAnalyzer.getContentPane().add(title2);
		
		JLabel title1 = new JLabel("Text Analyzer");
		title1.setForeground(Color.RED);
		title1.setFont(new Font("Times New Roman", Font.BOLD, 38));
		title1.setHorizontalAlignment(SwingConstants.CENTER);
		title1.setBounds(0, 51, 659, 51);
		frmTextAnalyzer.getContentPane().add(title1);
		
		JButton nxt = new JButton("Next");
		
		nxt.setBounds(285, 262, 111, 46);
		frmTextAnalyzer.getContentPane().add(nxt);
		
		JTextArea results = new JTextArea();
		results.setForeground(Color.RED);
		results.setLineWrap(true);
		results.setWrapStyleWord(true);
		results.setBackground(Color.BLACK);
		results.setBounds(10, 40, 659, 500);
		frmTextAnalyzer.getContentPane().add(results);
		nxt.setVisible(false);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				title1.setText("Text: The Raven by Edgar Allen Poe");
				title2.setVisible(false);
				start.setVisible(false);
				exit.setVisible(false);
				nxt.setVisible(true);
				
				
			}
		});
		nxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					title1.setBounds(0, 10, 659, 51);
					title1.setText("Results:");
					results.setVisible(true);
				results.setText(wordOccurances(input));
				nxt.setVisible(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public static Connection getConnection() {
		  try{
		   String driver = "com.mysql.jdbc.Driver";
		   String url = "jdbc:mysql://localhost:3306/word_occurances";
		   String username = "root";
		   String password = "Dimasamo#1";
		   Class.forName(driver);
		   
		   Connection conn = DriverManager.getConnection(url,username,password);
		   System.out.println("Connected to DB");
		   return conn;
		  } catch(Exception e){System.out.println(e);}
		  
		  
		  return null;
		 }
	 
	 
	 public static void post(String word) throws Exception{
		   
	        try{
	            Connection con = getConnection();
	            PreparedStatement posted = con.prepareStatement("insert into word_occurances.word (name) values ('"+word+"');");
	            
	            posted.executeUpdate();
	        } catch(Exception e){System.out.println(e);}
	        finally {
	            System.out.println("Inserted.");
	        }
	    }
	 
	 public static void  parseFile(File input) throws Exception
		{
			  Connection con = getConnection();
			 Scanner scan = new Scanner(input);
			 while (scan.hasNext()) //
			    {
			        String temp = scan.next(); //inserts into db
			       	post(temp);
			    }
			 
		}
	 
	 public static ArrayList<String> getValues()
		{
			 try{
		            Connection con = getConnection();
		            //gets 20 most used words from DB
		            PreparedStatement statement = con.prepareStatement("SELECT       `name`,\r\n" + 
		            		"             COUNT(`name`) AS `value_occurrence` \r\n" + 
		            		"    FROM     `word`\r\n" + 
		            		"    GROUP BY `name`\r\n" + 
		            		"    ORDER BY `value_occurrence` DESC\r\n" + 
		            		"    LIMIT    20;");
		            
		            ResultSet res = statement.executeQuery();
		            
		            ArrayList<String> arr = new ArrayList<String>(); //puts results into an array list
		            while(res.next()){
		            	 arr.add(res.getString("name"));
		               
		                
		              
		                
		              
		            }
		           
		            return arr;
		            
		        }catch(Exception e){System.out.println(e);}
		        return null;
		        
		}
	 

public String  wordOccurances(File input) throws Exception
{
	 getConnection(); //connects to db
	StringBuilder str = new StringBuilder(); //string builder for final list
	String result = null;
  
   
   ArrayList<String> sort = new ArrayList<String>();
   parseFile(input); //inserts into db
   
   sort = getValues(); //sorts 20 most used words from db
    
//prints the list
    for(int i = 0; i < 20; i++) {   
    	
      str.append( (i+1) + "." + " " + sort.get(i) + "\n ") ;
    }  
    
   result = str.toString();
 
    
    return result; 
	}
}
