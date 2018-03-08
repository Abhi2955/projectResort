import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.*;
import java.sql.*;
///import java.net.URL;
class Login_Resort extends Thread implements ActionListener
{
	private  Connection connect1,connect2;
	private Statement state1,state2;
	private static boolean dbFlag;
	private static JFrame panelEdiltBill,frameEdiltcategory,frame,welcomeFrame,setUpLoginFrame,forgetPassFrame,mainFrame
							,panelProducts,panelSuperAdmin;
	private static JButton loginButton,forgetpassButton,forgetPassFrame_Ok,forgetPassFrame_Cancel,
					welcomeContinueButton,setUpNext,setUpBack;
	private static JPasswordField sequrity_Answer1,sequrity_Answer2,sequrity_Answer3,usernameText,passwordText;
	private static JTextField resortName,productValue[],ownerName,gstinNo,contactNo,mail,noOfTabels,productText,productPrice;
	private static String usernameString,passwordString,returnResortName="";
	private static JPasswordField forgetPassFrameUserdata,resortUsername,resortPassword,resortRePassword,
					sequrity_Answer01,sequrity_Answer02,sequrity_Answer03;
	private String dataResortName,dataResortContact,dataResortMail,dataResortTin,itemName,addTableName,addProductName;
	private JMenuBar menuBar;
	private JPanel panel,editPanel,panelDescription,panelFunction,panelDetail,panelTables,
					panelCategories,panelActivity,panelBill,panelButton,editCategoryPanel,superAdminPanel,
					categoryPanel,editCategoryPanelValues,editCategoryPanelFunction,productPanel,productPanelButt;
	private JMenu invetory,invoice,hr,admin,setting;
	private JMenuItem inventoryAddCategory,inventoryAddProduct,inventoryTables,invetoryCategories,invoiceManage,
					hrManage,settingLogin,settingResortInfo,settingPassword,settingTax,settingDiscount,adminLogs,adminReport,
					adminLogOut,adminRestart,settingAddUser,settingManageUser;
	private JButton categoryAddNew,categorySave,categoryDeleteButton[],editButton[],buttonTable[],buttonCategries[],
					buttonPay,buttonCancel,buttonEdit,productAdd,productDelete[],productEdit[],editButtonForEdit[],
					adminInfoButton,resortInfoButton;
	private int loadTables,loadCategories,loadCategories1,index,resultTable,aButton = 0,intAddCat=0,addProductValue=0;
	private Color tableButtonColor,tableBookedColor;
	static Login_Resort loginObject = new Login_Resort();
	private static Font frameFont = new Font("David",Font.PLAIN,22);
	private String dataItem,tableName,nameItem[],categoriesNameString[],pressedCategory;
	private int dataQuantity,indexProduct=1,getIndexNo,prductNo,quanta,items=0,returnItems=0,loadQuantity[],
				editLeft=25,editTop=50,editHeight=20,editWidth=20,productNo,errorMsgProduct,errorMsgCat,
				errorMsgEdit;
	private char userType;
	private double dataPrice,dataAmount,prize;
	private JDialog editDialog,editCategoryDialog,productDialog,superAdminDialog;
	private JLabel editItemLabel[],editQualityLabel[],editTableNameLabel,description,productName[],productPriceValue[];
	private JScrollPane scrollEditBill,scrollPanel;
	String nextLineString=" \n", itemString = " Item \n\n",quantityString="Quantity \n\n",priceString = "Price \n\n",
			amountString= "Amount \n\n",indexString,stringProductName[],stringProductValue[],stringProductPrice[],
			stringDialogProductName[];
	private JTextArea dataAreaItem,dataAreaPrice,dataAreaQuantity,dataAreaTotal,dataAreaTable,categoryTextAreaPanel[];
	private JComboBox <String> listCategory;
	private double doubleProductValue[];
	private static Image image = new ImageIcon("z_icon.png").getImage();
	private static String sequrityQuestionString1 = "who is special person for you ?",
					sequrityQuestionString2 = "what is your first school name ?"
					,sequrityQuestionString3 = "what is your favourite color ?",upperChar = "(.*[A-Z].*)",lowerChar = "(.*[a-z].*)",num = "(.*[0-9].*)"
					,specialChar = "(.*[,~,!,@,#,$,%,^,&,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,.,,,?].*$)";
	final ImageIcon iconAlert = new ImageIcon("alert.png");
	final ImageIcon iconError = new ImageIcon("error.png");
	final ImageIcon iconOk = new ImageIcon("check.png");
	Validation validObject = new Validation();
	public void run()
	{
		createForgetPassGui();
		dataBase_Connection1();
		dataBase_Connection2();
		loadTablesCategries();
		mainFrameGui();
		editBillFrame();
	}

	private void dataBase_Connection1()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connect1 = DriverManager.getConnection("jdbc:mysql://localhost/resort","root","");
			state1 = connect1.createStatement();
		}
		catch(Exception r)
		{

		}
	}
	private void dataBase_Connection2()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connect2 = DriverManager.getConnection("jdbc:mysql://localhost/resort","root","");
			state2 = connect2.createStatement();
		}
		catch(Exception r)
		{

		}
	}
	private void incorrect_Username()
	{
	JOptionPane.showMessageDialog(frame, "This username is Invalid", "Inalid Username", JOptionPane.ERROR_MESSAGE,iconError);
	}
	private void incorrect_Pass()
	{
		JOptionPane.showMessageDialog(frame, "Password mismatch from system Or Invalid Password.", "Error Password", JOptionPane.ERROR_MESSAGE,iconError);
	}
	private void error_Database_Connection1()
	{
		JOptionPane.showMessageDialog(frame, "Please check your connection or it may be server problem", "Connection Error !!!", JOptionPane.ERROR_MESSAGE,iconError);
		dataBase_Connection1();
		run();
	}
	private void error_Database_Connection2()
	{
		JOptionPane.showMessageDialog(frame, "Please check your connection or it may be server problem", "Connection Error !!!", JOptionPane.ERROR_MESSAGE,iconError);
		dataBase_Connection2();
		run();
	}
	private void error_Empty_Text()
	{
		JOptionPane.showMessageDialog(frame, "Please fill the Username and password to login", "Empty Box !!!", JOptionPane.ERROR_MESSAGE,iconAlert);
	}

	private void loadTablesCategries()
	{
		dataBase_Connection2();
		try
		{
			try
			{
				ResultSet rs = state2.executeQuery("SELECT tables FROM adminlog WHERE type LIKE '"+'s'+"'");
				while(rs.next())
				{
					loadTables = rs.getInt("tables");
				}
				rs = state2.executeQuery("SELECT DISTINCT COUNT(*) from categories_list");
				while(rs.next())
				{
					loadCategories = rs.getInt("COUNT(*)");
				}
				rs.close();
				connect2.close();
			}
			catch(SQLException sqlExpLoadCat)
			{
			}
			dataAreaTotal.setText("Amount");
			dataAreaPrice.setText("Price");
			dataAreaQuantity.setText("Quantity");
			dataAreaItem.setText("Item");
			dataAreaTable.setText("No Table ");
			tableName = "No Table";
		}
		catch(Exception payExp)
		{

		}
	}
	///
	private void deleteDataTable()
	{
		try
		{
			state1.executeUpdate("DELETE FROM table_items WHERE booked_table LIKE '"+tableName+"'");
			dataAreaTotal.setText("Amount");
			dataAreaPrice.setText("Price");
			dataAreaQuantity.setText("Quantity");
			dataAreaItem.setText("Item");
			dataAreaTable.setText("No Table ");
			tableName = "No Table";
		}
		catch(Exception payExp)
		{

		}
	}
	///
	private void reLoadBill()
	{
		nextLineString=" \n"; itemString = "Item \n\n";quantityString="Quantity \n\n";priceString = "Price \n\n";amountString= "Amount \n\n";
		dataAreaTable.setText(tableName);
		dataAreaItem.setText(itemString);
		dataAreaQuantity.setText(quantityString);
		dataAreaPrice.setText(priceString);
		dataAreaTotal.setText(amountString);
		dataBase_Connection2();
		try
		{
			ResultSet rs = state2.executeQuery("SELECT item,quantity,price FROM table_items WHERE booked_table Like '"+tableName+"' ");
			while(rs.next())
			{	dataItem = rs.getString("item");
				dataQuantity = rs.getInt("quantity");
				dataPrice = rs.getDouble("price");
				dataAmount = dataQuantity*dataPrice;
				itemString += dataItem;
				itemString += nextLineString;
				dataAreaItem.setText(itemString);
				quantityString += dataQuantity;
				quantityString += nextLineString;
				dataAreaQuantity.setText(quantityString);
				priceString += dataPrice;
				priceString += nextLineString;
				dataAreaPrice.setText(priceString);
				amountString += dataAmount;
				amountString += nextLineString;
				dataAreaTotal.setText(amountString);
				returnItems = items;
			}
			rs.close();
			connect2.close();
		}
		catch (Exception getReloadExp)
		{
			error_Database_Connection2();
		}
	}
	///
	private void editBillFrame()
	{
		dataBase_Connection2();
		int errorint=0;
		panelEdiltBill = new JFrame();
		panelEdiltBill.setVisible(false);
		editDialog = new JDialog(mainFrame, "Edit Bill", true);
		editDialog.setLocation(300,90);
		editDialog.setResizable(false);
		editPanel = new JPanel();
		editPanel.setLayout(null);
		Insets insetsEdit = editPanel.getInsets();
		editDialog.add(editPanel);
		editPanel.setBackground(new java.awt.Color(102,102,102));
		editPanel.setPreferredSize(new Dimension(500,750));
		scrollEditBill = new JScrollPane(editPanel);
		editPanel.setAutoscrolls(true);
		scrollEditBill.setPreferredSize(new Dimension(500,550));
		editDialog.add(scrollEditBill);
		///
		editTableNameLabel = new JLabel("No Table");
		JLabel itemsLabel = new JLabel("Items");
		JLabel quantityLabel = new JLabel("Quantity");
		JLabel deleteLabel = new JLabel("Action");
		editTableNameLabel.setBounds(220+editLeft,5,editWidth+125,10);
		editTableNameLabel.setForeground(new java.awt.Color(255,255,255));
		editPanel.add(editTableNameLabel);
		itemsLabel.setBounds(30+editLeft,20,editWidth+125,editHeight+10);
		quantityLabel.setBounds(190+editLeft,20,editWidth+115,editHeight+10);
		deleteLabel.setBounds(340+editLeft,20,editWidth+85,editHeight+10);
		itemsLabel.setFont(frameFont);
		quantityLabel.setFont(frameFont);
		deleteLabel.setFont(frameFont);
		itemsLabel.setForeground(new java.awt.Color(255,255,255));
		quantityLabel.setForeground(new java.awt.Color(255,255,255));
		deleteLabel.setForeground(new java.awt.Color(255,255,255));
		editPanel.add(itemsLabel);
		editPanel.add(quantityLabel);
		editPanel.add(deleteLabel);
		editItemLabel = new JLabel[returnItems];
		editQualityLabel = new JLabel[returnItems];
		editButton = new JButton[returnItems];
		editButtonForEdit = new JButton[returnItems];
		nameItem = new String[returnItems];
		loadQuantity = new int[returnItems];
		for (int i=0;i<returnItems;i++)
		{
			nameItem[i]="";
			loadQuantity[i]=0;
		}
		if(items != 0)
		{
			try
			{
				ResultSet ab = state2.executeQuery("SELECT item,quantity From table_items WHERE booked_table Like '"+tableName+"'");
				while(ab.next())
				{

						nameItem[errorint] = ab.getString("item");
						loadQuantity[errorint] = ab.getInt("quantity");
						errorint++;
				}
				ab.close();
				connect2.close();
			}
			catch(Exception itemsLoadExcep)
			{

			}
			///
			for (int i=0;i<returnItems;i++)
			{
				if(loadQuantity[i]!=0)
				{
					editTableNameLabel.setText(tableName);
					editItemLabel[i] = new JLabel(nameItem[i]);
					editQualityLabel[i] = new JLabel(""+loadQuantity[i]);
					editQualityLabel[i].setFont(frameFont);
					editItemLabel[i].setFont(frameFont);
					editItemLabel[i].setForeground(new java.awt.Color(255,255,255));
					editQualityLabel[i].setForeground(new java.awt.Color(255,255,255));
					java.net.URL imageURL = this.getClass().getResource("delete_Button.png");
					ImageIcon icon = new ImageIcon(imageURL);
					editButton[i] = new JButton(icon);
					java.net.URL imageURLEdit = this.getClass().getResource("edit_Button.png");
					ImageIcon iconEdit = new ImageIcon(imageURLEdit);
					editButtonForEdit[i] = new JButton(iconEdit);
					editItemLabel[i].setBounds(30+editLeft,editTop,editWidth+125,editHeight+10);
					editQualityLabel[i].setBounds(250+editLeft,editTop,editWidth+125,editHeight+10);
					editButton[i].setBounds(340+editLeft,editTop,editWidth+10,editHeight+10);
					editButtonForEdit[i].setBounds(390+editLeft,editTop,editWidth+10,editHeight+10);
					editPanel.add(editItemLabel[i]);
					editPanel.add(editQualityLabel[i]);
					editPanel.add(editButton[i]);
					editPanel.add(editButtonForEdit[i]);
					editButton[i].addActionListener(this);
					editButtonForEdit[i].addActionListener(this);
					editTop = editTop+40;
				}
			}
			editTop = 50;
			///
		}
	}
	///
	private void addData(String addTableName, String addProductName,int quanta, double prize)
	{
		this.addTableName = addTableName;
		this.addProductName = addProductName;
		this.quanta = quanta;
		this.prize = prize;
		try
		{
			state1.executeUpdate("insert into table_items values('"+addTableName+"','"+addProductName+"','"+quanta+"','"+prize+"')");
		}
		catch(Exception tableExp)
		{
			error_Database_Connection1();
		}
	}
	private void getData(String tableName)
	{
		dataBase_Connection2();
		nextLineString=" \n"; itemString = "Item \n\n";quantityString="Quantity \n\n";priceString = "Price \n\n";amountString= "Amount \n\n";
		this.tableName = tableName;
		dataAreaTable.setText(tableName);
		items=0;
		try
		{
			ResultSet rs = state2.executeQuery("SELECT item,quantity,price FROM table_items WHERE booked_table Like '"+tableName+"' ");
			while(rs.next())
			{		dataItem = rs.getString("item");
					dataQuantity = rs.getInt("quantity");
					dataPrice = rs.getDouble("price");
					dataAmount = dataQuantity*dataPrice;
					itemString += dataItem;
					itemString += nextLineString;
					dataAreaItem.setText(itemString);
					quantityString += dataQuantity;
					quantityString += nextLineString;
					dataAreaQuantity.setText(quantityString);
					priceString += dataPrice;
					priceString += nextLineString;
					dataAreaPrice.setText(priceString);
					amountString += dataAmount;
					amountString += nextLineString;
					dataAreaTotal.setText(amountString);
					items++;
					returnItems = items;
					editBillFrame();
			}
			rs.close();
			connect2.close();
		}
		catch (Exception getTablesData)
		{
			error_Database_Connection2();
		}
	}
	///
	private void login()
	{
		dataBase_Connection2();
		String returnUser="", returnPass="";
		char[] usernameArray = usernameText.getPassword();
		usernameString= new String(usernameArray);
		char[] passwordArray = passwordText.getPassword();
		passwordString= new String(passwordArray);
		if (usernameString.equals("")||passwordString.equals(""))
		{
			error_Empty_Text();
		}
		else
		{
			try
			{
				ResultSet rs = state2.executeQuery("SELECT username, password FROM adminlog WHERE username Like '"+usernameString+"' ");
				while(rs.next())
				{
					returnUser = rs.getString("username");
					returnPass = rs.getString("password");
				}
				rs = state2.executeQuery("SELECT type FROM adminlog WHERE username LIKE '"+usernameString+"'");
				while(rs.next())
				{
					String userTypeString = rs.getString("type");
					userType = userTypeString.charAt(0);
				}
				rs.close();
				connect2.close();
				if (returnUser.equals(usernameString))
				{
					if(returnPass.equals(passwordString))
					{
						for(int i=0;i<loadTables;++i)
						{
							int j = i+1;
							buttonTable[i].setBackground(tableButtonColor);
							buttonTable[i].setText("Table "+j);
						}
						nextLineString=" \n"; itemString = "Item \n\n";quantityString="Quantity \n\n";priceString = "Price \n\n";amountString= "Amount \n\n";
						dataAreaTable.setText(tableName);
						dataAreaItem.setText(itemString);
						dataAreaQuantity.setText(quantityString);
						dataAreaPrice.setText(priceString);
						dataAreaTotal.setText(amountString);
						frame.setVisible(false);
						mainFrame.setVisible(true);
					}
					else
					{
						incorrect_Pass();
					}
				}
				else
				{
					incorrect_Username();
				}
			}
			catch(Exception exp)
			{
				error_Database_Connection2();
				run();
			}
		}
	}
	private void errorSuperAdmin()
	{
		JOptionPane.showMessageDialog(frame, "You are not a admin. Contact to admin for any kind of changes.", "authentication Error", JOptionPane.ERROR_MESSAGE,iconError);
	}
	private void setUpNext_Button()
	{
		String returnUsername,returnPassword,returnRePassword,returnAnswer1,returnAnswer2,returnAnswer3,returnTableNo;
		try
		{
			char[] returnUsernameArray = resortUsername.getPassword();
			char[] returnPasswordArray = resortPassword.getPassword();
			char[] returnRePasswordArray = resortRePassword.getPassword();
			char[] returnAnswer1Array = sequrity_Answer1.getPassword();
			char[] returnAnswer2Array = sequrity_Answer2.getPassword();
			char[] returnAnswer3Array = sequrity_Answer3.getPassword();
			returnUsername = new String(returnUsernameArray);
			returnPassword = new String (returnPasswordArray);
			returnRePassword = new String(returnRePasswordArray);
			returnAnswer1 = new String(returnAnswer1Array);
			returnAnswer2 = new String(returnAnswer2Array);
			returnAnswer3 = new String(returnAnswer3Array);
			returnTableNo = noOfTabels.getText();
			int tableNo = Integer.parseInt(returnTableNo);
			boolean passwordValid;
			try
		 	{
		 	    state1.executeUpdate("DELETE FROM adminlog");
		    }
		 	catch(Exception deleteDataBase)
		    {
		 	    JOptionPane.showMessageDialog(welcomeFrame,deleteDataBase,"Error",JOptionPane.ERROR_MESSAGE,iconError);
		 	    dataBase_Connection1();
		 	}

		 	if(returnUsername.equals("")||returnPassword.equals("")||returnRePassword.equals("")||
		 	   	returnAnswer1.equals("")||returnAnswer2.equals("")||returnAnswer3.equals(""))
		 	{
		 	  	JOptionPane.showMessageDialog(welcomeFrame,"Fill All Details to Continue","Empty Text ",JOptionPane.ERROR_MESSAGE,iconAlert);
		 	}
		 	else
		 	{
		 	   if(returnPassword.equals(returnRePassword))
		 	    {
					if (tableNo>60)
					{
						JOptionPane.showMessageDialog(setUpLoginFrame,"You cannot add more then 60 tables","Alert Table ",JOptionPane.ERROR_MESSAGE,iconAlert);
					}
					else
					{
						passwordValid = validObject.passwordValidation(returnPassword,returnUsername);
						if(passwordValid==false)
						{
							errorPasswordFormat();
						}
						else
						{
							try
							{
								state1.executeUpdate("insert into adminlog values('"+returnUsername+"','"+returnPassword+"','"+returnAnswer1+"','"+returnAnswer2+"','"+returnAnswer3+"',"+tableNo+",'"+'s'+"')");
								try
								{
									String byteString = "true";
									FileOutputStream nameResort = new FileOutputStream("resortSetUp.txt");
        		  					byte byteData[] = byteString.getBytes();
          							nameResort.write(byteData);
          							nameResort.close();
          							JOptionPane.showMessageDialog(setUpLoginFrame,"Set up is completed,\n Application will be close\nplease Restart this","restart",JOptionPane.INFORMATION_MESSAGE,iconOk);
									System.exit(0);
								}
								catch(Exception fileError)
								{
									JOptionPane.showMessageDialog(welcomeFrame,"Error occured  During File Writing","Error File",JOptionPane.ERROR_MESSAGE,iconError);
								}
							}
							catch(Exception error_exe)
							{
								error_Database_Connection1();
								run();
							}
						}
					}
	 	    	}
	 	    	else
	 	    	{
		     		JOptionPane.showMessageDialog(setUpLoginFrame,"Password and ReEnter Password Didn't match","Password Mismatch",JOptionPane.ERROR_MESSAGE,iconAlert);
		     	}
		    }
		}
		catch(Exception fileData)
		{

		}
	}
	private void errorPasswordFormat()
	{
		JOptionPane.showMessageDialog(setUpLoginFrame,"Password format is invalid\nyour password must be contains:\n1. A Upper Case Character\n2. A Lower Case Character\n3. A special Character and A number \n4.Password And Username must not be same and \n5. password must have 8 to 32 Characters",
										"Password Validation Error",JOptionPane.ERROR_MESSAGE,iconAlert);
	}
	///
	private void addCategoriesPanel()
	{
		if(intAddCat==0)
		{
			dataBase_Connection2();
			try
			{
				int k=0;
				ResultSet rs = state2.executeQuery("SELECT DISTINCT COUNT(name_categories) from categories_list");
				while(rs.next())
				{
					loadCategories1 = rs.getInt("COUNT(name_categories)");
				}
				rs = state2.executeQuery("SELECT DISTINCT name_categories FROM categories_list");
				while(rs.next())
				{
					categoriesNameString[k] = rs.getString("name_categories");
					k++;
				}
				rs.close();
				connect2.close();
			}
			catch(Exception loadCat)
			{
			}
			categoryDeleteButton = new JButton[loadCategories1];
			categoryTextAreaPanel = new JTextArea[loadCategories1];
			frameEdiltcategory = new JFrame();
			frameEdiltcategory.setVisible(false);
			editCategoryDialog = new JDialog(mainFrame, "Edit Categories", true);
			if(loadCategories1<11)
			{
				editCategoryDialog.setLocation(400,60);
				editCategoryDialog.setSize(240,550);
			}
			else if(loadCategories1<21)
			{
				editCategoryDialog.setLocation(300,60);
				editCategoryDialog.setSize(440,550);
			}
			else if(loadCategories1<31)
			{
				editCategoryDialog.setLocation(200,60);
				editCategoryDialog.setSize(665,550);
			}
			else if(loadCategories1<41)
			{
				editCategoryDialog.setLocation(100,60);
				editCategoryDialog.setSize(890,550);
			}
			else if(loadCategories1<51)
			{
				editCategoryDialog.setLocation(45,60);
				editCategoryDialog.setSize(1115,550);
			}
			editCategoryDialog.setResizable(false);
			editCategoryPanel = new JPanel(new BorderLayout());
			///
			editCategoryPanelValues = new JPanel();
			editCategoryPanelValues.setLayout(null);
			editCategoryPanelFunction = new JPanel(new BorderLayout());
			///
			java.net.URL imageURLicon = this.getClass().getResource("delete_Button.png");
			ImageIcon iconDelete = new ImageIcon(imageURLicon);
			int countRow=1,countColumn=1;
			for (int i=0;i<loadCategories1;i++)
			{
				if(i<50)
				{
					///
					if(i==49)
					{
						JOptionPane.showMessageDialog(frameEdiltcategory,"This is your Last Entry","Alert Category",JOptionPane.PLAIN_MESSAGE,iconAlert);
					}
					if(countRow<11&&countColumn<7)
					{
						categoryTextAreaPanel[i]=new JTextArea();
						categoryTextAreaPanel[i].setText(categoriesNameString[i]);
						categoryTextAreaPanel[i].setFont(frameFont);
						categoryTextAreaPanel[i].setCaretColor(new Color(255,204,0));
						categoryDeleteButton[i] = new JButton(iconDelete);
						categoryTextAreaPanel[i].setBounds(editLeft,editTop,editWidth+125,editHeight+10);
						categoryDeleteButton[i].setBounds(150+editLeft,editTop,editWidth+10,editHeight+10);
						editCategoryPanelValues.add(categoryTextAreaPanel[i]);
						editCategoryPanelValues.add(categoryDeleteButton[i]);
						categoryDeleteButton[i].addActionListener(this);
						editTop = editTop+40;
						countRow++;
					}
					else
					{
						countRow=1;
						countColumn++;
						editTop = 50;
						editLeft = editLeft+210;
						categoryTextAreaPanel[i]=new JTextArea();
						categoryTextAreaPanel[i].setText(categoriesNameString[i]);
						categoryTextAreaPanel[i].setFont(frameFont);
						categoryTextAreaPanel[i].setCaretColor(new Color(255,204,0));
						categoryDeleteButton[i] = new JButton(iconDelete);
						categoryTextAreaPanel[i].setBounds(editLeft,editTop,editWidth+125,editHeight+10);
						categoryDeleteButton[i].setBounds(150+editLeft,editTop,editWidth+10,editHeight+10);
						editCategoryPanelValues.add(categoryTextAreaPanel[i]);
						editCategoryPanelValues.add(categoryDeleteButton[i]);
						categoryDeleteButton[i].addActionListener(this);
						editTop = editTop+40;
						countRow++;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(frameEdiltcategory,"You can Add Maximum 50 Categories","Error Categories",JOptionPane.ERROR_MESSAGE,iconError);
				}
			}
			editTop = 50;
			editLeft = 25;
			///
			editCategoryPanelValues.setBackground(new java.awt.Color(102,102,102));
			editCategoryPanelFunction.setBackground(new java.awt.Color(102,102,102));
			java.net.URL imageURL1 = this.getClass().getResource("save_icon.png");
			ImageIcon icon1 = new ImageIcon(imageURL1);
			categorySave= new JButton(" Save  ",icon1);
			categorySave.addActionListener(this);
			///
			categorySave.setHorizontalTextPosition(SwingConstants.RIGHT);
			editCategoryPanelFunction.add(categorySave,BorderLayout.EAST);
			editCategoryDialog.add(editCategoryPanel);
			editCategoryPanel.add(editCategoryPanelValues,BorderLayout.CENTER);
			editCategoryPanel.add(editCategoryPanelFunction,BorderLayout.PAGE_END);
		}
		else
		{
			JOptionPane.showMessageDialog(mainFrame,"Restart Application","Alert",JOptionPane.ERROR_MESSAGE,iconAlert);
			System.exit(0);
		}
	}
	///
	private void addNewCategory()
	{
		if(userType=='s')
		{
			String categoryString="";
			try
			{
				categoryString = JOptionPane.showInputDialog(mainFrame,"Input New Category : ");
				if(!(categoryString.equals("")))
				{
					state1.executeUpdate("insert into categories_list values('"+categoryString+"')");
					if(intAddCat==0)
					{
						JOptionPane.showMessageDialog(mainFrame,"Changes Updated Successfully.To keep them Restart Application","Update Data Warning Message",JOptionPane.WARNING_MESSAGE,iconAlert);
						intAddCat++;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(mainFrame,"Input a valid Name","Error Category",JOptionPane.ERROR_MESSAGE);
				}
			}
			catch(Exception addCategoriesExp)
			{
			}
		}
		else
		{
			errorSuperAdmin();
		}
	}
	///
	private void showProductPanel(String pressedCategory)
	{
		if(intAddCat==0)
		{
			JLabel localLabel[];
			String localString[];
			localLabel = new JLabel[5];
			localString = new String[5];
			localString[0]= "ProductName";
			localString[1]= "Price";
			localString[2]= "Quantity";
			localString[3]= "Delete";
			localString[4]= "Edit";
			this.pressedCategory = pressedCategory;
			panelProducts = new JFrame();
			panelProducts.setVisible(false);
			productDialog = new JDialog(mainFrame, pressedCategory, true);
			productDialog.setLayout(new BorderLayout());
			productDialog.setLocation(200,90);
			productDialog.setResizable(false);
			productPanel = new JPanel();
			productPanel.setLayout(null);
			productPanelButt = new JPanel(new BorderLayout());
			//productPanelButt.setBackground(new java.awt.Color(102,102,102));
			Insets insetsPanel = productPanel.getInsets();
			productDialog.add(productPanel,BorderLayout.CENTER);
			productDialog.add(productPanelButt,BorderLayout.PAGE_END);
			productPanel.setBackground(new java.awt.Color(102,102,102));
			productPanel.setPreferredSize(new Dimension(740,750));
			scrollPanel = new JScrollPane(productPanel);
			productPanel.setAutoscrolls(true);
			scrollPanel.setPreferredSize(new Dimension(740,550));
			productDialog.add(scrollPanel);
			java.net.URL imageURLAdd = this.getClass().getResource("save_icon.png");
			ImageIcon iconAdd = new ImageIcon(imageURLAdd);
			productAdd= new JButton(" Add  ",iconAdd);
			productAdd.addActionListener(this);
			productPanelButt.add(productAdd,BorderLayout.EAST);
			///
			for(int i=0;i<5;i++)
			{
				localLabel[i] = new JLabel(localString[i]);
				localLabel[i].setForeground(new java.awt.Color(251,251,251));
			}
			localLabel[0].setBounds(insetsPanel.left+20,insetsPanel.top,250,30);
			localLabel[1].setBounds(insetsPanel.left+290,insetsPanel.top,100,30);
			localLabel[2].setBounds(insetsPanel.left+420,insetsPanel.top,150,30);
			localLabel[3].setBounds(insetsPanel.left+560,insetsPanel.top,100,30);
			localLabel[4].setBounds(insetsPanel.left+670,insetsPanel.top,100,30);
			for(int i=0;i<5;i++)
				productPanel.add(localLabel[i]);
			try
			{
				dataBase_Connection2();
				ResultSet rs = state2.executeQuery("SELECT DISTINCT COUNT(*) from product WHERE category LIKE '"+pressedCategory+"'");
				while(rs.next())
				{
					productNo = rs.getInt("COUNT(*)");
				}
				productName = new JLabel[productNo];
				productPriceValue = new JLabel[productNo];
				productValue = new JTextField[productNo];
				productDelete = new JButton[productNo];
				productEdit = new JButton[productNo];
				doubleProductValue = new double[productNo];
				stringProductName = new String[productNo];
				stringProductValue = new String[productNo];
				stringProductPrice = new String[productNo];
				stringDialogProductName = new String[productNo];
				rs = state2.executeQuery("SELECT DISTINCT productname,price from product WHERE category LIKE '"+pressedCategory+"'");
				int j=0;
				while(rs.next())
				{
					stringDialogProductName[j]=rs.getString("productname");
					doubleProductValue[j]= rs.getDouble("price");
					j++;
				}
				rs.close();
				connect2.close();
				java.net.URL imageURLDel = this.getClass().getResource("delete_Button.png");
				ImageIcon iconDel = new ImageIcon(imageURLDel);
				java.net.URL imageURLEdit = this.getClass().getResource("edit_Button.png");
				ImageIcon iconEdit = new ImageIcon(imageURLEdit);
				for(int i=0;i<productNo;i++)
				{
					productName[i] = new JLabel(stringDialogProductName[i]);
					productPriceValue[i] = new JLabel(""+doubleProductValue[i]);
					productValue[i] = new JTextField();
					productDelete[i] = new JButton(iconDel);
					productEdit[i] = new JButton(iconEdit);
					productName[i].setForeground(new java.awt.Color(251,251,251));
					productPriceValue[i].setForeground(new java.awt.Color(251,251,251));
					productValue[i].setBackground(new java.awt.Color(102,102,102));
					productValue[i].setForeground(new java.awt.Color(251,251,251));
					productValue[i].setPreferredSize(new Dimension(100,30));
					productValue[i].setCaretColor(new Color(255,204,0));
					productName[i].setBounds(insetsPanel.left+20,insetsPanel.top+editTop,250,30);
					productPriceValue[i].setBounds(insetsPanel.left+290,insetsPanel.top+editTop,80,30);
					productValue[i].setBounds(insetsPanel.left+400,insetsPanel.top+editTop,120,30);
					productDelete[i].setBounds(insetsPanel.left+570,insetsPanel.top+editTop,30,30);
					productEdit[i].setBounds(insetsPanel.left+670,insetsPanel.top+editTop,30,30);
					productName[i].setFont(frameFont);
					productPriceValue[i].setFont(frameFont);
					productValue[i].setFont(frameFont);
					productPanel.add(productName[i]);
					productPanel.add(productPriceValue[i]);
					productPanel.add(productValue[i]);
					productPanel.add(productDelete[i]);
					productPanel.add(productEdit[i]);
					productDelete[i].addActionListener(this);
					productEdit[i].addActionListener(this);
					editTop = editTop+40;
				}
				editTop = 50;
			}
			catch(Exception expProductCount)
			{
				System.out.println(expProductCount);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(mainFrame,"Restart Application","Alert",JOptionPane.ERROR_MESSAGE,iconAlert);
			System.exit(0);
		}
		productDialog.setSize(770,450);
		productDialog.setVisible(true);
	}
	///
	private void addNewUser()
	{
		String allotaedUsername="",allotedPassword="";
		JTextField usernameTextField = new JTextField();
		JTextField passwordTextField = new JTextField();
		Object components[] = {"Username",usernameTextField,"Password",passwordTextField};
		JOptionPane newUserOption = new JOptionPane();
		newUserOption.setMessage(components);
		newUserOption.setMessageType(JOptionPane.PLAIN_MESSAGE);
		JDialog newUserDailog = newUserOption.createDialog(null,"Add New User");
		newUserDailog.setVisible(true);
		allotaedUsername = usernameTextField.getText();
		allotedPassword = passwordTextField.getText();
		///
		boolean passwordValid;
		passwordValid=validObject.passwordValidation(allotedPassword,allotaedUsername);
		if(passwordValid==false)
		{
			if(!(allotaedUsername.equals("")||allotedPassword.equals("")))
			{
				errorPasswordFormat();
				addNewUser();
			}
		}
		else
		{
			try
			{
				state1.executeUpdate("insert into adminlog (username,password,type) values('"+allotaedUsername+"','"+allotedPassword+"','a') ");
				JOptionPane.showMessageDialog(frame,"User Added Successfully!!","Sucess",JOptionPane.INFORMATION_MESSAGE,iconOk);
			}
			catch(Exception adduserExp)
			{
				System.out.println(adduserExp);
			}
		}
	}
	private void superAdminPanel(String typePanel)
	{
		String panelType = typePanel;
		superAdminDialog = new JDialog(mainFrame,panelType,true);;
		String userpassword="";
		userpassword = JOptionPane.showInputDialog(frame,"Input your exsiting Password ");
		if(!(userpassword.equals("")))
		{
			if(passwordString.equals(userpassword))
			{
				panelSuperAdmin = new JFrame();
				panelSuperAdmin.setVisible(false);
				superAdminDialog.setLocation(300,90);
				superAdminPanel = new JPanel();
				superAdminPanel.setLayout(null);
				Insets insetsEdit = superAdminPanel.getInsets();
				superAdminDialog.add(superAdminPanel);
				superAdminPanel.setBackground(new java.awt.Color(102,102,102));
				superAdminPanel.setPreferredSize(new Dimension(500,750));
				JScrollPane scrollSuperAdmin = new JScrollPane(superAdminPanel);
				superAdminPanel.setAutoscrolls(true);
				scrollSuperAdmin.setPreferredSize(new Dimension(500,550));
				superAdminDialog.add(scrollSuperAdmin);
				superAdminDialog.setSize(550,450);
				superAdminDialog.setVisible(true);
				if(panelType.equals("Setting Logs"))
				{

				}
				else if(panelType.equals("Info Resort"))
				{

				}
			}
		}
		///
	}
	private void userUpdate()
	{
		String userpassword="",newUsername="";
		userpassword = JOptionPane.showInputDialog(frame,"Input your exsiting Password");
		if(!(userpassword.equals("")))
		{
			if(passwordString.equals(userpassword))
			{
				newUsername = JOptionPane.showInputDialog(frame,"Enter New Username ");
				try
				{
					state1.executeUpdate("UPDATE adminlog SET username='"+newUsername+"' WHERE username LIKE '"+usernameString+"' ");
					usernameString=newUsername;
				}
				catch(Exception userUpdateExp)
				{
				}
			}
			else
			{
				incorrect_Pass();
			}
		}
	}
	private void addProductName()
	{
		if(userType=='s')
		{
			try
			{
				int tempCatNo=0,k=0;
				String tempCatName[];
				dataBase_Connection2();
				ResultSet rs = state2.executeQuery("SELECT DISTINCT COUNT(*) FROM categories_list");
				while(rs.next())
				{
					tempCatNo = rs.getInt("COUNT(*)");
				}
				tempCatName = new String[tempCatNo];
				for(int i=0; i<tempCatNo;i++)
				{
					tempCatName[i]="";
				}
				rs = state2.executeQuery("SELECT DISTINCT name_categories FROM categories_list");
				while(rs.next())
				{
					tempCatName[k]=rs.getString("name_categories");
					k++;
				}
				///
				String productString="",productPriceString="";
				double priceDouble;
				listCategory = new JComboBox <String>(tempCatName);
				productText = new JTextField();
				productPrice = new JTextField();
				Object components[] = {"Select Category",listCategory,"New Product Name",productText,"Price ",productPrice};
				JOptionPane productOption = new JOptionPane();
				productOption.setMessage(components);
				productOption.setMessageType(JOptionPane.PLAIN_MESSAGE);
				JDialog productDialog = productOption.createDialog(null,"Add Product");
				productDialog.setVisible(true);
				///
				getIndexNo = listCategory.getSelectedIndex();
				indexString = (String)listCategory.getItemAt(getIndexNo);
				productString = productText.getText();
				productPriceString = productPrice.getText();
				indexProduct = getIndexNo;
				if(!(indexString.equals("") && productPriceString.equals("")))
				{
					priceDouble = Double.parseDouble(productPriceString);
					state1.executeUpdate("insert into product values ('"+productString+"','"+indexString+"','"+priceDouble+"') ");
					if(addProductValue==0)
					{
						JOptionPane.showMessageDialog(mainFrame,"Changes Updated Successfully","Success Message",JOptionPane.WARNING_MESSAGE,iconOk);
						addProductValue++;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(mainFrame,"Input a valid Entries","Error Entries",JOptionPane.ERROR_MESSAGE,iconError);
				}
			}
			catch(Exception addProductExp)
			{

			}
		}
		else
		{
			errorSuperAdmin();
		}
	}
	private void saveCategory()
	{
		String returnNameCategories[];
		returnNameCategories = new String[loadCategories1];
		for(int i=0;i<loadCategories1;i++)
		{
			returnNameCategories[i] = categoryTextAreaPanel[i].getText();
			if(!(returnNameCategories[i].equals("")))
			{
				if(!(returnNameCategories[i].equals(categoriesNameString[i])))
				{
					try
					{
						state1.executeUpdate("UPDATE categories_list SET name_categories = '"+returnNameCategories[i]+"' WHERE name_categories LIKE '"+categoriesNameString[i]+"'");
						state1.executeUpdate("UPDATE product SET category = '"+returnNameCategories[i]+"' WHERE category LIKE '"+categoriesNameString[i]+"'");
						int dialogButton = JOptionPane.YES_NO_OPTION;
						int dialogResult = JOptionPane.showConfirmDialog(null,"Changes Updated Successfully.To keep them Restart Application","Update Data Warning Message",dialogButton);
						if(dialogResult == JOptionPane.YES_OPTION)
						{
							System.exit(0);
						}
					}
					catch(Exception saveCategoryExp)
					{

					}
				}
			}
		}
		editCategoryDialog.dispose();
	}
	///
	private void loginFrameLogout()
	{
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(null,"Do you Want to logging Out ?\nAll Billing data will be lost","Warning Message",dialogButton);
		if(dialogResult == JOptionPane.YES_OPTION)
		{
			usernameText.setText("");
			passwordText.setText("");
			usernameString="";
			mainFrame.setVisible(false);
			frame.setVisible(true);
			try
			{
				state1.executeUpdate("DELETE FROM table_items");
			}
			catch(Exception loggingOutFrame)
			{
			}
		}
		else if(dialogResult == JOptionPane.NO_OPTION)
		{

		}
	}

	///
	private void check_forgetFrame_Ok_Button()
	{
		dataBase_Connection2();
		char[] adminTextBoxArray = forgetPassFrameUserdata.getPassword();
		String adminTextBox = new String(adminTextBoxArray);
		char[] answer1Array = sequrity_Answer01.getPassword();
		String answer1 = new String(answer1Array);
		char[] answer2Array = sequrity_Answer02.getPassword();
		String answer2 = new String(answer2Array);
		char[] answer3Array = sequrity_Answer03.getPassword();
		String answer3 = new String(answer3Array);
		String returnUsername="", returnAnswer1="", returnAnswer2="", returnAnswer3="";
		int error=0;
		if (adminTextBox.equals(""))
		{
			JOptionPane.showMessageDialog(forgetPassFrame,"Input your Username","Empty Text Box",JOptionPane.ERROR_MESSAGE,iconAlert);
		}
		else
		{
			if(answer1.equals(""))
			{
				error++;
			}
			if(answer2.equals(""))
			{
				error++;
			}
			if(answer3.equals(""))
			{
				error++;
			}

			if (!(error == 1))
			{
			JOptionPane.showMessageDialog(forgetPassFrame,"Give exact 2 answer of questions","Sequrity Question Alert",JOptionPane.ERROR_MESSAGE,iconAlert);
			}
			else
			{
				try
				{
					ResultSet rs = state2.executeQuery("SELECT username,sequrity_Answer1,sequrity_Answer2,sequrity_Answer3 FROM adminlog WHERE username LIKE'"+adminTextBox+"' ");
					while(rs.next())
					{
						returnUsername = rs.getString("username");
						returnAnswer1 = rs.getString("sequrity_Answer1");
						returnAnswer2 = rs.getString("sequrity_Answer2");
						returnAnswer3 = rs.getString("sequrity_Answer3");
					}
					rs.close();
					connect2.close();
					if (returnUsername.equals(adminTextBox))
					{
						if (answer1.equals(""))
						{
							if (returnAnswer2.equals(answer2)&&returnAnswer3.equals(answer3))
							{
								updateForgetPass();
							}
							else
							{
								JOptionPane.showMessageDialog(forgetPassFrame,"Sequrity answers Doesn't match!!","Sequrity Question Error",JOptionPane.ERROR_MESSAGE,iconError);
							}
						}
						else if(answer2.equals(""))
						{
							if (returnAnswer1.equals(answer1)&&returnAnswer3.equals(answer3))
							{
								updateForgetPass();
							}
							else
							{
								JOptionPane.showMessageDialog(forgetPassFrame,"Sequrity answers Doesn't match!!","Sequrity Question Error",JOptionPane.ERROR_MESSAGE,iconError);
							}
						}
						else if(answer3.equals(""))
						{
							if (returnAnswer2.equals(answer2)&&returnAnswer1.equals(answer1))
							{
								updateForgetPass();
							}
							else
							{
								JOptionPane.showMessageDialog(forgetPassFrame,"Sequrity answers Doesn't match!!","Sequrity Question Error",JOptionPane.ERROR_MESSAGE,iconError);
							}
						}
					}
					else
					{
						incorrect_Username();
					}
				}
				catch(Exception exp)
				{
					JOptionPane.showMessageDialog(forgetPassFrame,"can't Proceed\nServer Problem Detected","Server Error",JOptionPane.ERROR_MESSAGE,iconError);
					dataBase_Connection2();
				}
			}
		}
	}
	private void welcomeContinue_Button()
	{
		int welcomeError=0;
		String returnOwnerName,returngstinNo,returnContactNo,returnMail;
		try
		{
			try
			{
				returnResortName = resortName.getText();
				returnOwnerName = ownerName.getText();
				returngstinNo = gstinNo.getText();
				returnContactNo = contactNo.getText();
				returnMail = mail.getText();
				try
	 		    {
	 		    	state1.executeUpdate("DELETE FROM info_resort");
					state1.executeUpdate("DELETE FROM categories_list");
					state1.executeUpdate("DELETE FROM adminlog");
					state1.executeUpdate("DELETE FROM product");
	 	    	}
	 	    	catch(Exception deleteDataBase)
	 	    	{
	 	    		JOptionPane.showMessageDialog(welcomeFrame,deleteDataBase,"Error",JOptionPane.ERROR_MESSAGE,iconError);
	 	    	}
	 	    	boolean mailValid = validObject.emailValidation(returnMail);
	 	    	boolean numberValid = validObject.contactValidation(returnContactNo);
	 	    	if(numberValid==false)
	 	    	{
	 	    		JOptionPane.showMessageDialog(welcomeFrame,"Enter A Valid Contact No","Invalid Contact",JOptionPane.ERROR_MESSAGE,iconError);
	 	    		welcomeError++;
	 	    	}
	 	    	if(mailValid==false)
	 	    	{
	 	    		JOptionPane.showMessageDialog(welcomeFrame,"Enter A Valid Mail Id","Invalid Mail",JOptionPane.ERROR_MESSAGE,iconError);
	 	    		welcomeError++;
	 	    	}
				if (returnResortName.equals("")||returnOwnerName.equals("")
					||returngstinNo.equals("")||returnContactNo.equals("")||returnMail.equals(""))
				{
					JOptionPane.showMessageDialog(welcomeFrame,"Fill All Details to Continue","Empty TextBox ",JOptionPane.ERROR_MESSAGE,iconError);
					welcomeError++;
				}
				if(welcomeError==0)
				{
					state1.executeUpdate("insert into info_resort values('"+returnResortName+"','"+returnOwnerName+"','"+returnContactNo+"','"+returnMail+"','"+returngstinNo+"')");
					try
					{
						FileOutputStream nameResort = new FileOutputStream("resortName.txt");
        	  			byte byteData[] = returnResortName.getBytes();
          				nameResort.write(byteData);
          				nameResort.close();
          				welcomeFrame.setVisible(false);
          				setUpLoginFrame.setTitle(returnResortName);
						setUpLoginFrame.setVisible(true);
					}
					catch(Exception fileError)
					{
						JOptionPane.showMessageDialog(welcomeFrame,"Error occured During File Writing","Error File",JOptionPane.ERROR_MESSAGE,iconError);
					}
				}
			}
			catch (Exception expDataBase)
			{
				JOptionPane.showMessageDialog(welcomeFrame,"can't Proceed\nServer Problem Detected","Server Error",JOptionPane.ERROR_MESSAGE,iconError);
				dataBase_Connection1();
			}
		}
		catch(Exception fileData)
		{

		}
	}
	private void updatePassword()
	{
		String exsitingPass="",returnPass="";
		dataBase_Connection2();
		try
		{
			ResultSet rs= state2.executeQuery("SELECT password FROM adminlog WHERE username LIKE '"+usernameString+"' ");
			while(rs.next())
			{
				returnPass = rs.getString("password");
			}
			rs.close();
			connect2.close();
		}
		catch(Exception updatePassExp)
		{
			System.out.println(updatePassExp);
		}
		exsitingPass= JOptionPane.showInputDialog(mainFrame,"Enter Old Password");
		if(exsitingPass.equals(returnPass))
		{
			enterNewPass(exsitingPass);
		}
	}
	private void enterNewPass(String thisPass)
	{
		String newPassword="", reEnterPassword="",returnUserId;
		returnUserId = thisPass;
		int error = 0;
		try
		{
			newPassword = JOptionPane.showInputDialog(mainFrame,"Enter New Password");
			if (!(newPassword.equals("")))
			{
				reEnterPassword = JOptionPane.showInputDialog(mainFrame,"ReEnter Password");
				if (newPassword.equals(reEnterPassword))
				{
					boolean passwordValid = validObject.passwordValidation(newPassword,returnUserId);

					if(passwordValid==false)
					{
						errorPasswordFormat();
						enterNewPass(returnUserId);
					}
					else
					{
						try
						{
							state1.executeUpdate("UPDATE adminlog SET password = '"+newPassword+"' WHERE username LIKE '"+usernameString+"'");
							JOptionPane.showMessageDialog(mainFrame,"Password updated Successfully!!!","Passwrod Updated",JOptionPane.INFORMATION_MESSAGE,iconOk);
						}
						catch(Exception error_Forget)
						{
							JOptionPane.showMessageDialog(mainFrame,"Can't Update Password\nServer Issue detected","Server Error",JOptionPane.ERROR_MESSAGE,iconError);
						}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(mainFrame,"Password doesn't match\ntry again","Password mismatch",JOptionPane.ERROR_MESSAGE,iconError);
					enterNewPass(returnUserId);
				}
			}
		}
		catch (Exception expforget)
		{

		}
	}
	private void updateForgetPass()
	{
		String newPassword="", reEnterPassword="",returnUserId;
		char[] returnUserIdArray = forgetPassFrameUserdata.getPassword();
		returnUserId = new String(returnUserIdArray);
		boolean passwordValid;
		try
		{
			newPassword = JOptionPane.showInputDialog(forgetPassFrame,"Enter New Password");
			if (!(newPassword.equals("")))
			{
				reEnterPassword = JOptionPane.showInputDialog(forgetPassFrame,"ReEnter Password");
				if (newPassword.equals(reEnterPassword))
				{
					passwordValid = validObject.passwordValidation(newPassword,returnUserId);
					if(passwordValid==false)
					{
						JOptionPane.showMessageDialog(forgetPassFrame,"Password formate is invalid\nyour password must be contains:\n1. A Upper Case Character\n2. A Lower Case Character\n3. A special Character and A number \n4.Password And Username must not be same and \n5. password must have 8 to 32 Characters","Password Validation Error",JOptionPane.ERROR_MESSAGE,iconError);
						updateForgetPass();
					}
					else
					{
						try
						{
							state1.executeUpdate("UPDATE adminlog SET password = '"+newPassword+"' WHERE username LIKE '"+returnUserId+"'");
							JOptionPane.showMessageDialog(forgetPassFrame,"Password updated Successfully!!!","Passwrod Updated",JOptionPane.INFORMATION_MESSAGE,iconOk);
							usernameText.setText("");
							passwordText.setText("");
							usernameString="";
							forgetPassFrame.setVisible(false);
							frame.setVisible(true);
						}
						catch(Exception error_Forget)
						{
							JOptionPane.showMessageDialog(forgetPassFrame,"Can't Update Password\nServer Issue detected","Server Error",JOptionPane.ERROR_MESSAGE,iconError);
							dataBase_Connection1();
						}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(forgetPassFrame,"Password doesn't match\ntry again","Password mismatch",JOptionPane.ERROR_MESSAGE,iconError);
					updateForgetPass();
				}
			}
		}
		catch (Exception expforget)
		{

		}
	}
	private void forget()
	{
		frame.setVisible(false);
		forgetPassFrame.setVisible(true);
	}
	///
	public void updateTables()
	{
		String tablesString="";
		try
		{
			tablesString = JOptionPane.showInputDialog(mainFrame,"Input No of Tables: ");
			int tablesInt = Integer.parseInt(tablesString);
			if(!(tablesString.equals("")))
			{
				if(tablesInt!=0 && tablesInt>0)
				{
					state1.executeUpdate("UPDATE adminlog SET tables = '"+tablesInt+"' WHERE type LIKE '"+'s'+"' ");
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null,"Changes Updated Successfully.To keep them Restart Application","Update Data Warning Message",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION)
					{
						System.exit(0);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(mainFrame,"Please Input A Valid Value","Update Error",JOptionPane.ERROR_MESSAGE,iconError);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(mainFrame,"Please Input A Valid Value","Update Error",JOptionPane.ERROR_MESSAGE,iconError);
			}
		}
		catch(Exception updateTablesExp)
		{

		}
	}
	private void addProduct()
	{
		for(int i=0;i<productNo;i++)
		{
			stringProductName[i] = productName[i].getText();
			stringProductPrice[i] = productPriceValue[i].getText();
			stringProductValue[i] = productValue[i].getText();
			if(!(stringProductValue[i].equals("")))
			{
				int valueProduct = Integer.parseInt(stringProductValue[i]);
				double priceProduct = Double.parseDouble(stringProductPrice[i]);
				addData("Table "+(resultTable),stringProductName[i],valueProduct,priceProduct);
				getData("Table "+(resultTable));
			}
			productDialog.dispose();
		}
	}
	/// Login Frame Declartion
	private static void loginPaneComponents(Container loginPane)
	{
		loginPane.setLayout(null);
		Color mainFrameButtonColor = new Color(255,204,51);
		JLabel usernameLabel = new JLabel("Username :");
		usernameLabel.setFont(frameFont);
	  	usernameText = new JPasswordField();
		usernameText.setPreferredSize(new Dimension(250,40));
		usernameText.setFont(frameFont);
		usernameText.setBackground(new java.awt.Color(102,102,102));
		usernameLabel.setForeground(new java.awt.Color(255,255,255));
		usernameText.setForeground(new java.awt.Color(255,255,255));
		usernameText.setCaretColor(new Color(255,204,0));
		JLabel passwordLabel = new JLabel("Password :");
		passwordLabel.setFont(frameFont);
		passwordText = new JPasswordField();
		passwordText.setPreferredSize(new Dimension(250,40));
		passwordText.setFont(frameFont);
		passwordText.setBackground(new java.awt.Color(102,102,102));
		passwordLabel.setForeground(new java.awt.Color(255,255,255));
		passwordText.setForeground(new java.awt.Color(255,255,255));
		passwordText.setCaretColor(new Color(255,204,0));
		forgetpassButton = new JButton("Forget Pass");
		forgetpassButton.setFont(frameFont);
		forgetpassButton.setBackground(mainFrameButtonColor);
		forgetpassButton.setOpaque(true);
		forgetpassButton.setBorderPainted(false);
		forgetpassButton.addActionListener(loginObject);
		loginButton = new JButton("Login");
		loginButton.setFont(frameFont);
		loginButton.setBackground(mainFrameButtonColor);
		loginButton.setOpaque(true);
		loginButton.setBorderPainted(false);
		loginButton.addActionListener(loginObject);
		loginPane.setBackground(new java.awt.Color(102,102,102));
		loginPane.add(usernameLabel);
		loginPane.add(usernameText);
		loginPane.add(passwordLabel);
		loginPane.add(passwordText);
		loginPane.add(forgetpassButton);
		loginPane.add(loginButton);
		Insets insets = loginPane.getInsets();
		Dimension size = usernameLabel.getPreferredSize();
		usernameLabel.setBounds(395 + insets.left, 245 + insets.top,size.width, size.height);
		size = usernameText.getPreferredSize();
		usernameText.setBounds(550 + insets.left, 235+insets.top,size.width, size.height);
		size = passwordLabel.getPreferredSize();
		passwordLabel.setBounds(395+insets.left, 295+insets.top,size.width, size.height);
		size = passwordText.getPreferredSize();
		passwordText.setBounds(550+insets.left, 285+insets.top,size.width, size.height);
		size = forgetpassButton.getPreferredSize();
		forgetpassButton.setBounds(395+insets.left, 345+insets.top,size.width, size.height);
		size = loginButton.getPreferredSize();
		loginButton.setBounds(635+insets.left, 345+insets.top,size.width, size.height);
	}
	private static void createLoginGui()
	{
		frame = new JFrame(returnResortName);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(image);
		try
		{
        	frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("restro_back.jpg")))));
        }
        catch (IOException expBack)
        {
        	//expBack.printStackTrace();
        }
        loginPaneComponents(frame.getContentPane());
		frame.setSize(1200,650);
		frame.setVisible(false);
		frame.setResizable(false);
		frame.setLocation(5,5);
	}
	/// Main Frame Ends Here
	/// Welcome Frame
	private static void welcomePaneComponents(Container welcomePane)
	{

		welcomePane.setLayout(null);
		Color mainFrameButtonColor = new Color(255,204,51);
		JLabel resortNameLabel = new JLabel("Resort Name:");
		resortNameLabel.setFont(frameFont);
	  	resortName = new JTextField();
		resortName.setPreferredSize(new Dimension(250,40));
		resortName.setFont(frameFont);
		resortName.setBackground(new java.awt.Color(102,102,102));
		resortNameLabel.setForeground(new java.awt.Color(255,255,255));
		resortName.setForeground(new java.awt.Color(255,255,255));
		resortName.setCaretColor(new Color(255,204,0));
		JLabel ownerNameLabel = new JLabel("Owner Name:");
		ownerNameLabel.setFont(frameFont);
		ownerName = new JTextField();
		ownerName.setPreferredSize(new Dimension(250,40));
		ownerName.setFont(frameFont);
		ownerName.setBackground(new java.awt.Color(102,102,102));
		ownerNameLabel.setForeground(new java.awt.Color(255,255,255));
		ownerName.setForeground(new java.awt.Color(255,255,255));
		ownerName.setCaretColor(new Color(255,204,0));
		JLabel gstinNoLabel = new JLabel("GSTIN no:");
		gstinNoLabel.setFont(frameFont);
		gstinNo = new JTextField();
		gstinNo.setPreferredSize(new Dimension(250,40));
		gstinNo.setFont(frameFont);
		gstinNo.setBackground(new java.awt.Color(102,102,102));
		gstinNoLabel.setForeground(new java.awt.Color(255,255,255));
		gstinNo.setForeground(new java.awt.Color(255,255,255));
		gstinNo.setCaretColor(new Color(255,204,0));
		JLabel contactNoLabel = new JLabel("Contact no:");
		contactNoLabel.setFont(frameFont);
		contactNo = new JTextField();
		contactNo.setPreferredSize(new Dimension(250,40));
		contactNo.setFont(frameFont);
		contactNo.setBackground(new java.awt.Color(102,102,102));
		contactNoLabel.setForeground(new java.awt.Color(255,255,255));
		contactNo.setForeground(new java.awt.Color(255,255,255));
		contactNo.setCaretColor(new Color(255,204,0));
		JLabel mailLabel = new JLabel("Email :");
		mailLabel.setFont(frameFont);
		mail = new JTextField();
		mail.setPreferredSize(new Dimension(250,40));
		mail.setFont(frameFont);
		mail.setBackground(new java.awt.Color(102,102,102));
		mailLabel.setForeground(new java.awt.Color(255,255,255));
		mail.setForeground(new java.awt.Color(255,255,255));
		mail.setCaretColor(new Color(255,204,0));
		welcomeContinueButton = new JButton("Continue");
		welcomeContinueButton.setFont(frameFont);
		welcomeContinueButton.setBackground(mainFrameButtonColor);
		welcomeContinueButton.setOpaque(true);
		welcomeContinueButton.setBorderPainted(false);
		welcomeContinueButton.addActionListener(loginObject);
		welcomePane.setBackground(new java.awt.Color(102,102,102));
		welcomePane.add(resortNameLabel);
		welcomePane.add(resortName);
		welcomePane.add(ownerNameLabel);
		welcomePane.add(ownerName);
		welcomePane.add(gstinNoLabel);
		welcomePane.add(gstinNo);
		welcomePane.add(mailLabel);
		welcomePane.add(mail);
		welcomePane.add(contactNoLabel);
		welcomePane.add(contactNo);
		welcomePane.add(welcomeContinueButton);
		Insets insets = welcomePane.getInsets();
		Dimension size = resortNameLabel.getPreferredSize();
		resortNameLabel.setBounds(385 + insets.left, 145 + insets.top,size.width, size.height);
		size = resortName.getPreferredSize();
		resortName.setBounds(550 + insets.left, 135+insets.top,size.width, size.height);
		size = ownerNameLabel.getPreferredSize();
		ownerNameLabel.setBounds(385+insets.left, 195+insets.top,size.width, size.height);
		size = ownerName.getPreferredSize();
		ownerName.setBounds(550+insets.left, 185+insets.top,size.width, size.height);
		size = gstinNoLabel.getPreferredSize();
		gstinNoLabel.setBounds(385 + insets.left, 245 + insets.top,size.width, size.height);
		size = gstinNo.getPreferredSize();
		gstinNo.setBounds(550 + insets.left, 235+insets.top,size.width, size.height);
		size = contactNoLabel.getPreferredSize();
		contactNoLabel.setBounds(385+insets.left, 295+insets.top,size.width, size.height);
		size = contactNo.getPreferredSize();
		contactNo.setBounds(550+insets.left, 285+insets.top,size.width, size.height);
		size = mailLabel.getPreferredSize();
		mailLabel.setBounds(385+insets.left, 345+insets.top,size.width, size.height);
		size = mail.getPreferredSize();
		mail.setBounds(550+insets.left, 335+insets.top,size.width, size.height);
		size = welcomeContinueButton.getPreferredSize();
		welcomeContinueButton.setBounds(550+insets.left, 445+insets.top,30+size.width, size.height);
	}
	private static void createWelcomeGui()
	{
		welcomeFrame = new JFrame("Welcome");
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try
		{
        	welcomeFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("welcome_back.jpg")))));
        }
        catch (IOException expBack) {}
		welcomeFrame.setIconImage(image);
		welcomePaneComponents(welcomeFrame.getContentPane());
		welcomeFrame.setSize(1200,650);
		welcomeFrame.setVisible(false);
		welcomeFrame.setResizable(false);
		welcomeFrame.setLocation(5,5);
	}
	/// Welcome frame Ends Here
	/// login Id set up Layout Declartion
	private static void loginSetUpComponents(Container setUpPane)
	{
		setUpPane.setLayout(null);
		Color mainFrameButtonColor = new Color(255,204,51);
		JLabel resortUsernameLabel = new JLabel("Choose Username:");
		resortUsernameLabel.setFont(frameFont);
		resortUsername = new JPasswordField();
		resortUsername.setPreferredSize(new Dimension(250,40));
		resortUsername.setFont(frameFont);
		resortUsername.setBackground(new java.awt.Color(102,102,102));
		resortUsernameLabel.setForeground(new java.awt.Color(255,255,255));
		resortUsername.setForeground(new java.awt.Color(255,255,255));
		resortUsername.setCaretColor(new Color(255,204,0));
		JLabel resortPasswordLabel = new JLabel("Choose Password:");
		resortPasswordLabel.setFont(frameFont);
		resortPassword = new JPasswordField();
		resortPassword.setPreferredSize(new Dimension(250,40));
		resortPassword.setFont(frameFont);
		resortPassword.setBackground(new java.awt.Color(102,102,102));
		resortPasswordLabel.setForeground(new java.awt.Color(255,255,255));
		resortPassword.setForeground(new java.awt.Color(255,255,255));
		resortPassword.setCaretColor(new Color(255,204,0));
		JLabel resortRePasswordLabel = new JLabel("ReEnter Password:");
		resortRePasswordLabel.setFont(frameFont);
		resortRePassword = new JPasswordField();
		resortRePassword.setPreferredSize(new Dimension(250,40));
		resortRePassword.setFont(frameFont);
		resortRePassword.setBackground(new java.awt.Color(102,102,102));
		resortRePasswordLabel.setForeground(new java.awt.Color(255,255,255));
		resortRePassword.setForeground(new java.awt.Color(255,255,255));
		resortRePassword.setCaretColor(new Color(255,204,0));
		JLabel sequrity_Question1 = new JLabel(sequrityQuestionString1);
		sequrity_Question1.setFont(frameFont);
		sequrity_Question1.setForeground(new java.awt.Color(255,255,255));
		sequrity_Answer1 = new JPasswordField();
		sequrity_Answer1.setPreferredSize(new Dimension(250,40));
		sequrity_Answer1.setFont(frameFont);
		sequrity_Answer1.setBackground(new java.awt.Color(102,102,102));
		sequrity_Answer1.setForeground(new java.awt.Color(255,255,255));
		sequrity_Answer1.setCaretColor(new Color(255,204,0));
		JLabel	sequrity_Question2 = new JLabel(sequrityQuestionString2);
		sequrity_Question2.setFont(frameFont);
		sequrity_Question2.setForeground(new java.awt.Color(255,255,255));
		sequrity_Answer2 = new JPasswordField();
		sequrity_Answer2.setPreferredSize(new Dimension(250,40));
		sequrity_Answer2.setFont(frameFont);
		sequrity_Answer2.setBackground(new java.awt.Color(102,102,102));
		sequrity_Answer2.setForeground(new java.awt.Color(255,255,255));
		sequrity_Answer2.setCaretColor(new Color(255,204,0));
		JLabel	sequrity_Question3 = new JLabel(sequrityQuestionString3);
		sequrity_Question3.setFont(frameFont);
		sequrity_Question3.setForeground(new java.awt.Color(255,255,255));
		sequrity_Answer3 = new JPasswordField();
		sequrity_Answer3.setPreferredSize(new Dimension(250,40));
		sequrity_Answer3.setFont(frameFont);
		sequrity_Answer3.setBackground(new java.awt.Color(102,102,102));
		sequrity_Answer3.setForeground(new java.awt.Color(255,255,255));
		sequrity_Answer3.setCaretColor(new Color(255,204,0));
		JLabel noOfTabelsLabel = new JLabel("NO of Tables in Restaurant");
		noOfTabelsLabel.setFont(frameFont);
		noOfTabelsLabel.setForeground(new java.awt.Color(255,255,255));
		noOfTabels = new JTextField();
		noOfTabels.setPreferredSize(new Dimension(250,40));
		noOfTabels.setFont(frameFont);
		noOfTabels.setBackground(new java.awt.Color(102,102,102));
		noOfTabels.setForeground(new java.awt.Color(255,255,255));
		noOfTabels.setCaretColor(new Color(255,204,0));
		setUpNext = new JButton("Next");
		setUpNext.setFont(frameFont);
		setUpNext.setBackground(mainFrameButtonColor);
		setUpNext.setOpaque(true);
		setUpNext.setBorderPainted(false);
		setUpNext.addActionListener(loginObject);
		setUpBack = new JButton("Back");
		setUpBack.setFont(frameFont);
		setUpBack.setBackground(mainFrameButtonColor);
		setUpBack.setOpaque(true);
		setUpBack.setBorderPainted(false);
		setUpBack.addActionListener(loginObject);
		setUpPane.setBackground(new java.awt.Color(102,102,102));
		setUpPane.add(resortUsernameLabel);
		setUpPane.add(resortUsername);
		setUpPane.add(resortPasswordLabel);
		setUpPane.add(resortPassword);
		setUpPane.add(resortRePasswordLabel);
		setUpPane.add(resortRePassword);
		setUpPane.add(sequrity_Question1);
		setUpPane.add(sequrity_Question2);
		setUpPane.add(sequrity_Question3);
		setUpPane.add(sequrity_Answer1);
		setUpPane.add(sequrity_Answer2);
		setUpPane.add(sequrity_Answer3);
		setUpPane.add(noOfTabelsLabel);
		setUpPane.add(noOfTabels);
		setUpPane.add(setUpNext);
		setUpPane.add(setUpBack);
		Insets insets = setUpPane.getInsets();
		Dimension size = resortUsernameLabel.getPreferredSize();
		resortUsernameLabel.setBounds(305+insets.left, 145+insets.top,size.width, size.height);
		size = resortUsername.getPreferredSize();
		resortUsername.setBounds(600+insets.left, 135+insets.top,size.width, size.height);
		size = resortPasswordLabel.getPreferredSize();
		resortPasswordLabel.setBounds(305 + insets.left, 195 + insets.top,size.width, size.height);
		size = resortPassword.getPreferredSize();
		resortPassword.setBounds(600 + insets.left, 185+insets.top,size.width, size.height);
		size = resortRePasswordLabel.getPreferredSize();
		resortRePasswordLabel.setBounds(305+insets.left, 245+insets.top,size.width, size.height);
		size = resortRePassword.getPreferredSize();
		resortRePassword.setBounds(600+insets.left, 235+insets.top,size.width, size.height);
		size = sequrity_Question1.getPreferredSize();
		sequrity_Question1.setBounds(305 + insets.left, 295 + insets.top,size.width, size.height);
		size = sequrity_Answer1.getPreferredSize();
		sequrity_Answer1.setBounds(600 + insets.left, 285+insets.top,size.width, size.height);
		size = sequrity_Question2.getPreferredSize();
		sequrity_Question2.setBounds(305+insets.left, 345+insets.top,size.width, size.height);
		size = sequrity_Answer2.getPreferredSize();
		sequrity_Answer2.setBounds(600+insets.left, 335+insets.top,size.width, size.height);
		size = sequrity_Question3.getPreferredSize();
		sequrity_Question3.setBounds(305+insets.left, 395+insets.top,size.width, size.height);
		size = sequrity_Answer3.getPreferredSize();
		sequrity_Answer3.setBounds(600+insets.left, 385+insets.top,size.width, size.height);
		size = noOfTabelsLabel.getPreferredSize();
		noOfTabelsLabel.setBounds(305+insets.left, 445+insets.top,size.width, size.height);
		size = noOfTabels.getPreferredSize();
		noOfTabels.setBounds(600+insets.left, 435+insets.top,size.width, size.height);
		size = setUpBack.getPreferredSize();
		setUpBack.setBounds(420+insets.left, 495+insets.top,size.width, size.height);
		size = setUpNext.getPreferredSize();
		setUpNext.setBounds(700+insets.left, 495+insets.top,size.width, size.height);

	}
	private static void createSetUpGui()
	{
		setUpLoginFrame = new JFrame(returnResortName);
		setUpLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try
		{
        	setUpLoginFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("welcome_back.jpg")))));
        }
        catch (IOException expBack) {}
		setUpLoginFrame.setIconImage(image);
		loginSetUpComponents(setUpLoginFrame.getContentPane());
		setUpLoginFrame.setSize(1200,650);
		setUpLoginFrame.setVisible(false);
		setUpLoginFrame.setResizable(false);
		setUpLoginFrame.setLocation(5,5);
	}
	/// login Id set up Layout Declartion Ends here
	/// forgetPassFrame declartion starts here
	private static void forgetPassComponents(Container forgetPassPane)
	{
		forgetPassPane.setLayout(null);
		Color mainFrameButtonColor = new Color(255,204,51);
		forgetPassPane.setBackground(new java.awt.Color(102,102,102));
		JLabel forgetPassFrameUserName = new JLabel("User Name:");
		JLabel tempLabel = new JLabel("Answer any two questions :");
		JLabel sequrity_Question1 = new JLabel(sequrityQuestionString1);
		JLabel sequrity_Question2 = new JLabel(sequrityQuestionString2);
		JLabel sequrity_Question3 = new JLabel(sequrityQuestionString3);
		forgetPassFrameUserdata = new JPasswordField();
		sequrity_Answer01 = new JPasswordField();
		sequrity_Answer02 = new JPasswordField();
		sequrity_Answer03 = new JPasswordField();
		forgetPassFrame_Cancel = new JButton("Cancel");
		forgetPassFrame_Cancel.addActionListener(loginObject);
		forgetPassFrame_Ok = new JButton("Ok");
		forgetPassFrame_Ok.addActionListener(loginObject);
		forgetPassFrame_Cancel.setFont(frameFont);
		forgetPassFrame_Ok.setFont(frameFont);
		forgetPassFrame_Cancel.setBackground(mainFrameButtonColor);
		forgetPassFrame_Cancel.setOpaque(true);
		forgetPassFrame_Cancel.setBorderPainted(false);
		forgetPassFrame_Ok.setBackground(mainFrameButtonColor);
		forgetPassFrame_Ok.setOpaque(true);
		forgetPassFrame_Ok.setBorderPainted(false);
		forgetPassFrameUserdata.setPreferredSize(new Dimension(200,30));
		sequrity_Answer01.setPreferredSize(new Dimension(200,30));
		sequrity_Answer02.setPreferredSize(new Dimension(200,30));
		sequrity_Answer03.setPreferredSize(new Dimension(200,30));
		tempLabel.setForeground(new java.awt.Color(0,0,0));
		forgetPassFrameUserName.setForeground(new java.awt.Color(0,0,0));
		sequrity_Question1.setForeground(new java.awt.Color(0,0,0));
		sequrity_Question2.setForeground(new java.awt.Color(0,0,0));
		sequrity_Question3.setForeground(new java.awt.Color(0,0,0));
		forgetPassFrameUserdata.setForeground(new java.awt.Color(255,255,255));
		sequrity_Answer01.setForeground(new java.awt.Color(255,255,255));
		sequrity_Answer02.setForeground(new java.awt.Color(255,255,255));
		sequrity_Answer03.setForeground(new java.awt.Color(255,255,255));
		forgetPassFrameUserName.setFont(frameFont);
		sequrity_Question1.setFont(frameFont);
		sequrity_Question2.setFont(frameFont);
		sequrity_Question3.setFont(frameFont);
		tempLabel.setFont(frameFont);
		forgetPassFrameUserdata.setFont(frameFont);
		sequrity_Answer01.setFont(frameFont);
		sequrity_Answer02.setFont(frameFont);
		sequrity_Answer03.setFont(frameFont);
		forgetPassFrameUserdata.setBackground(new java.awt.Color(102,102,102));
		sequrity_Answer01.setBackground(new java.awt.Color(102,102,102));
		sequrity_Answer02.setBackground(new java.awt.Color(102,102,102));
		sequrity_Answer03.setBackground(new java.awt.Color(102,102,102));
		forgetPassFrameUserdata.setCaretColor(new Color(255,204,0));
		sequrity_Answer01.setCaretColor(new Color(255,204,0));
		sequrity_Answer02.setCaretColor(new Color(255,204,0));
		sequrity_Answer03.setCaretColor(new Color(255,204,0));
		forgetPassPane.add(forgetPassFrameUserName);
		forgetPassPane.add(sequrity_Question1);
		forgetPassPane.add(sequrity_Question2);
		forgetPassPane.add(sequrity_Question3);
		forgetPassPane.add(tempLabel);
		forgetPassPane.add(forgetPassFrameUserdata);
		forgetPassPane.add(sequrity_Answer01);
		forgetPassPane.add(sequrity_Answer02);
		forgetPassPane.add(sequrity_Answer03);
		forgetPassPane.add(forgetPassFrame_Cancel);
		forgetPassPane.add(forgetPassFrame_Ok);
		Insets insets = forgetPassPane.getInsets();
		Dimension size = forgetPassFrameUserName.getPreferredSize();
		forgetPassFrameUserName.setBounds(305+insets.left,145+insets.top,size.width,size.height);
		size = forgetPassFrameUserdata.getPreferredSize();
		forgetPassFrameUserdata.setBounds(630+insets.left,135+insets.top,size.width,size.height);
		size = tempLabel.getPreferredSize();
		tempLabel.setBounds(420+insets.left, 185+insets.top, size.width, size.height);
		size = sequrity_Question1.getPreferredSize();
		sequrity_Question1.setBounds(305+insets.left,245+insets.top, size.width, size.height);
		size = sequrity_Answer01.getPreferredSize();
		sequrity_Answer01.setBounds(630+insets.left, 235+insets.top, size.width, size.height);
		size = sequrity_Question2.getPreferredSize();
		sequrity_Question2.setBounds(305+insets.left, 295+insets.top, size.width, size.height);
		size = sequrity_Answer02.getPreferredSize();
		sequrity_Answer02.setBounds(630+insets.left,285+insets.top, size.width, size.height);
		size = sequrity_Question3.getPreferredSize();
		sequrity_Question3.setBounds(305+insets.left, 345+insets.top, size.width, size.height);
		size = sequrity_Answer03.getPreferredSize();
		sequrity_Answer03.setBounds(630+insets.left,335+insets.top, size.width, size.height);
		size = forgetPassFrame_Cancel.getPreferredSize();
		forgetPassFrame_Cancel.setBounds(350+insets.left,445+insets.top,50+size.width, size.height);
		size = forgetPassFrame_Ok.getPreferredSize();
		forgetPassFrame_Ok.setBounds(670+insets.left, 445+insets.top, 50+size.width, size.height);

	}
	private static void createForgetPassGui()
	{

		forgetPassFrame = new JFrame(returnResortName);
		forgetPassFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		forgetPassFrame.setIconImage(image);
		try
		{
        	forgetPassFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("forget_pass.jpg")))));
        }
        catch (IOException expBackPass) {}
		forgetPassComponents(forgetPassFrame.getContentPane());
		forgetPassFrame.setSize(1200,650);
		forgetPassFrame.setVisible(false);
		forgetPassFrame.setResizable(false);
		forgetPassFrame.setLocation(5,5);
	}
	/// Forget Pass frame Ends Here
	/// Main Frame Define
	public void mainFrameGui()
	{
		tableButtonColor = new Color(102,204,255);
		tableBookedColor = new Color(255,102,102);
		menuBar = new JMenuBar();
		invetory = new JMenu("Inventory");
		invetory.setMnemonic(KeyEvent.VK_I);
		menuBar.add(invetory);
		///
		inventoryAddCategory = new JMenuItem("Add Category");
		inventoryAddCategory.setAccelerator(KeyStroke.getKeyStroke('A',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		inventoryAddCategory.addActionListener(loginObject);
		invetory.addActionListener(this);
		invetory.add(inventoryAddCategory);
		///
		inventoryAddProduct = new JMenuItem("Add Product");
		inventoryAddProduct.setAccelerator(KeyStroke.getKeyStroke('P',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		inventoryAddProduct.addActionListener(loginObject);
		invetory.addActionListener(this);
		invetory.add(inventoryAddProduct);
		///
		inventoryTables = new JMenuItem("Manage Tables");
		inventoryTables.setAccelerator(KeyStroke.getKeyStroke('T',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		inventoryTables.addActionListener(loginObject);
		invetory.addActionListener(this);
		invetory.add(inventoryTables);
		//invetory.addSeparator();
		invetoryCategories = new JMenuItem("Manage Categories");
		invetoryCategories.setAccelerator(KeyStroke.getKeyStroke('C',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		invetoryCategories.addActionListener(this);
		invetory.add(invetoryCategories);
		invoice = new JMenu("Invoice");
		invoice.setMnemonic(KeyEvent.VK_V);
		menuBar.add(invoice);
		invoiceManage = new JMenuItem("Manage Invoice");
		invoiceManage.setAccelerator(KeyStroke.getKeyStroke('I',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		invoice.add(invoiceManage);
		invoiceManage.addActionListener(this);
		hr = new JMenu("HR Manage");
		hr.setMnemonic(KeyEvent.VK_H);
		menuBar.add(hr);
		hrManage = new JMenuItem("Manage Staff");
		hrManage.setAccelerator(KeyStroke.getKeyStroke('H',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		hrManage.addActionListener(this);
		hr.add(hrManage);
		setting = new JMenu("Setting");
		setting.setMnemonic(KeyEvent.VK_S);
		menuBar.add(setting);
		settingLogin = new JMenuItem("Update Login Info");
		settingLogin.setAccelerator(KeyStroke.getKeyStroke('L',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		settingLogin.addActionListener(this);
		setting.add(settingLogin);
		//setting.addSeparator();
		settingResortInfo = new JMenuItem("Update Resort Info");
		settingResortInfo.setAccelerator(KeyStroke.getKeyStroke('Z',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		settingResortInfo.addActionListener(this);
		setting.add(settingResortInfo);
		///
		settingPassword = new JMenuItem("Update Password");
		settingPassword.setAccelerator(KeyStroke.getKeyStroke('U',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		settingPassword.addActionListener(this);
		setting.add(settingPassword);
		//setting.addSeparator();
		settingDiscount = new JMenuItem("Setup Discounts");
		settingDiscount.setAccelerator(KeyStroke.getKeyStroke('D',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		settingDiscount.addActionListener(this);
		setting.add(settingDiscount);
		//setting.addSeparator();
		settingTax = new JMenuItem("Setup Tax");
		settingTax.setAccelerator(KeyStroke.getKeyStroke('S',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		settingTax.addActionListener(this);
		setting.add(settingTax);
		settingAddUser = new JMenuItem("Add User");
		settingAddUser.setAccelerator(KeyStroke.getKeyStroke('J',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		settingAddUser.addActionListener(this);
		setting.add(settingAddUser);
		settingManageUser = new JMenuItem("Manage User");
		settingManageUser.setAccelerator(KeyStroke.getKeyStroke('M',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		settingManageUser.addActionListener(this);
		setting.add(settingManageUser);
		///
		admin = new JMenu("Admin");
		admin.setMnemonic(KeyEvent.VK_A);
		menuBar.add(admin);
		adminLogs =new JMenuItem("Logs");
		adminLogs.setAccelerator(KeyStroke.getKeyStroke('V',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		adminLogs.addActionListener(this);
		admin.add(adminLogs);
		//admin.addSeparator();
		adminReport =new JMenuItem("Report Manager");
		adminReport.setAccelerator(KeyStroke.getKeyStroke('R',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		adminReport.addActionListener(this);
		admin.add(adminReport);
		adminLogOut = new JMenuItem("Log Out");
		adminLogOut.setAccelerator(KeyStroke.getKeyStroke('O',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		adminLogOut.addActionListener(this);
		admin.add(adminLogOut);
		adminRestart = new JMenuItem("Restart");
		adminRestart.setAccelerator(KeyStroke.getKeyStroke('E',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		adminRestart.addActionListener(this);
		admin.add(adminRestart);
		/// panel Layout
		panel= new JPanel();
		panel.setLayout(new GridLayout(2,1));
		panel.setOpaque(true);
		/// function layout
		panelFunction = new JPanel();
		panelFunction.setLayout(new GridLayout(1,2));
		panelFunction.setOpaque(true);
		panel.add(panelFunction);
		///
		panelTables = new JPanel();
		panelTables.setOpaque(true);
		if(loadTables<16)
		{
			panelTables.setPreferredSize(new Dimension(450,400));
		}
		else if (loadTables<31)
		{
			panelTables.setPreferredSize(new Dimension(450,850));
		}
		else if (loadTables<46)
		{
			panelTables.setPreferredSize(new Dimension(450,1300));
		}
		else if(loadTables<61)
		{
			panelTables.setPreferredSize(new Dimension(450,1750));
		}
		panelTables.setLayout(null);
		JScrollPane scrollPanelTable = new JScrollPane(panelTables);
		panelTables.setAutoscrolls(true);
		scrollPanelTable.setPreferredSize(new Dimension(400,300));
		panelFunction.add(scrollPanelTable);
		///
		int count =0, leftSide=10, topSide=5, buttonWidth = 180, buttonHeight = 80;
		Insets insets = panelTables.getInsets();
		buttonTable = new JButton[loadTables];
		buttonCategries = new JButton[loadCategories];
		for(int i=0;i<loadTables;++i)
		{
			int j = i+1;
			buttonTable[i] = new JButton("Table "+j);
			buttonTable[i].addActionListener(this);
			buttonTable[i].setBackground(tableButtonColor);
			panelTables.add(buttonTable[i]);
			if (count == 0)
			{
				buttonTable[i].setBounds(leftSide+insets.left, topSide+insets.top, buttonWidth, buttonHeight);
				count++;
			}
			else if (count == 1)
			{
				buttonTable[i].setBounds((leftSide+buttonWidth+10)+insets.left, topSide+insets.top, buttonWidth, buttonHeight);
				count++;
			}
			else if (count == 2)
			{
				buttonTable[i].setBounds((leftSide+2*(buttonWidth+10))+insets.left, topSide+insets.top, buttonWidth, buttonHeight);
				count = 0;
				topSide = topSide+buttonHeight+5;
			}
		}
		///
		panelTables.setBackground(new java.awt.Color(102,102,102));
		///
		panelCategories = new JPanel();
		panelCategories.setOpaque(true);
		if (loadCategories<21)
		{
			panelCategories.setPreferredSize(new Dimension(400,470));
		}
		else if (loadCategories<41)
		{
			panelCategories.setPreferredSize(new Dimension(400,950));
		}
		else if (loadCategories<61)
		{
			panelCategories.setPreferredSize(new Dimension(400,1390));
		}
		panelCategories.setLayout(null);
		panelFunction.add(panelCategories);
		JScrollPane scrollPanelCategories = new JScrollPane(panelCategories);
		panelCategories.setAutoscrolls(true);
		scrollPanelCategories.setPreferredSize(new Dimension(400,300));
		panelFunction.add(scrollPanelCategories);
		int countCat =0, leftSideCat=7, topSideCat=10, buttonWidthCat = 105, buttonHeightCat = 105;
		Insets inset = panelCategories.getInsets();
		for(int i=0;i<loadCategories;i++)
		{
			dataBase_Connection2();
			categoriesNameString = new String[loadCategories];
			for(int j=0;j<loadCategories;j++)
			{
				categoriesNameString[j]="";
			}
			try
			{
				int k=0;
				ResultSet rs = state2.executeQuery("SELECT DISTINCT name_categories FROM categories_list");
				while(rs.next())
				{
					categoriesNameString[k] = rs.getString("name_categories");
					k++;
				}
				rs.close();
				connect2.close();
			}
			catch(Exception categoriesExp)
			{
				System.out.println(categoriesExp);
			}
			buttonCategries[i] = new JButton(categoriesNameString[i]);
			buttonCategries[i].addActionListener(this);
			buttonCategries[i].setBackground(tableButtonColor);
			panelCategories.add(buttonCategries[i]);
			if (countCat == 0)
			{
				buttonCategries[i].setBounds(leftSideCat+inset.left, topSideCat+inset.top, buttonWidthCat, buttonHeightCat);
				countCat++;
			}
			else if (countCat == 1)
			{
				buttonCategries[i].setBounds((leftSideCat+buttonWidthCat+10)+inset.left, topSideCat+inset.top, buttonWidthCat, buttonHeightCat);
				countCat++;
			}
			else if (countCat == 2)
			{
				buttonCategries[i].setBounds((leftSideCat+2*(buttonWidthCat+10))+inset.left, topSideCat+inset.top, buttonWidthCat, buttonHeightCat);
				countCat++;
			}
			else if (countCat == 3)
			{
				buttonCategries[i].setBounds((leftSideCat+3*(buttonWidthCat+10))+inset.left, topSideCat+inset.top, buttonWidthCat, buttonHeightCat);
				countCat++;
			}
			else if (countCat == 4)
			{
				buttonCategries[i].setBounds((leftSideCat+4*(buttonWidthCat+10))+inset.left, topSideCat+inset.top, buttonWidthCat, buttonHeightCat);
				countCat = 0;
				topSideCat = topSideCat+buttonHeightCat+10;
			}
		}
		panelCategories.setBackground(new java.awt.Color(102,102,102));
		/// Detail Layout
		panelDetail = new JPanel();
		panelDetail.setOpaque(true);
		panelDetail.setLayout(new FlowLayout());
		panel.add(panelDetail);
		panelDetail.setBackground(new java.awt.Color(204,204,255));
		///
		panelActivity = new JPanel();
		panelActivity.setOpaque(true);
		panelDetail.add(panelActivity);
		panelActivity.setBackground(new java.awt.Color(102,102,102));
		panelActivity.setPreferredSize(new Dimension(300,300));
		///
		panelBill = new JPanel();
		panelBill.setOpaque(true);
		panelDetail.add(panelBill);
		panelBill.setLayout(null);
		JScrollPane scrollPanelBill = new JScrollPane(panelBill);
		panelBill.setAutoscrolls(true);
		scrollPanelBill.setPreferredSize(new Dimension(560,300));
		panelDetail.add(scrollPanelBill);
		dataAreaItem = new JTextArea("Item");
		panelBill.add(dataAreaItem);
		dataAreaItem.setEditable(false);
		dataAreaQuantity = new JTextArea("Quantity");
		dataAreaQuantity.setEditable(false);
		panelBill.add(dataAreaQuantity);
		dataAreaPrice = new JTextArea("Price");
		dataAreaPrice.setEditable(false);
		panelBill.add(dataAreaPrice);
		dataAreaTotal = new JTextArea("Amount");
		dataAreaTotal.setEditable(false);
		panelBill.add(dataAreaTotal);
		Insets insetBill = panelBill.getInsets();
		dataAreaItem.setBounds(10+insetBill.left,10+insetBill.top,190,480);
		dataAreaQuantity.setBounds(200+insetBill.left,10+insetBill.top,90,480);
		dataAreaPrice.setBounds(290+insetBill.left,10+insetBill.top,130,480);
		dataAreaTotal.setBounds(420+insetBill.left,10+insetBill.top,130,480);
		panelBill.setBackground(new java.awt.Color(255,255,255));
		panelBill.setPreferredSize(new Dimension(590,480));
		//addLabel();
		///
		panelButton = new JPanel();
		panelButton.setOpaque(true);
		panelDetail.add(panelButton);
		panelButton.setLayout(null);
		dataAreaTable = new JTextArea();
		dataAreaTable.setBackground(new java.awt.Color(102,102,102));
		dataAreaTable.setForeground(new java.awt.Color(255,255,255));
		dataAreaTable.setText("No Table ");
		dataAreaTable.setFont(frameFont);
		Insets insetsButton = panelButton.getInsets();
		dataAreaTable.setBounds(100+insetsButton.left,20+insetsButton.top,200,30);
		dataAreaTable.setEditable(false);
		panelButton.add(dataAreaTable);
		buttonPay = new JButton("PAY");
		panelButton.add(buttonPay);
		buttonPay.setBounds(80+insetsButton.left,60+insetsButton.top,150,55);
		buttonPay.setBackground(new java.awt.Color(0,204,204));
		buttonCancel = new JButton("CANCEL");
		panelButton.add(buttonCancel);
		buttonCancel.setBounds(80+insetsButton.left,125+insetsButton.top,150,55);
		buttonCancel.setBackground(new java.awt.Color(0,204,204));
		buttonEdit = new JButton("EDIT");
		panelButton.add(buttonEdit);
		buttonEdit.setBounds(80+insetsButton.left,190+insetsButton.top,150,55);
		buttonEdit.setBackground(new java.awt.Color(0,204,204));
		buttonPay.addActionListener(this);
		buttonCancel.addActionListener(this);
		buttonEdit.addActionListener(this);
		panelButton.setBackground(new java.awt.Color(102,102,102));
		panelButton.setPreferredSize(new Dimension(300,300));
		/// End Layout
		description = new JLabel("Powered By: AndroWeb Techno Solution");
		description.setFont(frameFont);
		panelDescription = new JPanel();
		panelDescription.setOpaque(true);
		panelDescription.setBackground(new java.awt.Color(255,102,102));
		panelDescription.add(description);
		mainFrame = new JFrame(returnResortName);
		mainFrame.add(panel,BorderLayout.CENTER);
		mainFrame.add(panelDescription,BorderLayout.PAGE_END);
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setIconImage(image);
		mainFrame.setSize(1200,650);
		mainFrame.setVisible(false);
		mainFrame.setResizable(false);
		mainFrame.setLocation(5,5);
		mainFrame.addWindowListener(new AreYouSure());
	}
	/// Main Frame Defination Ends Here
	/// Window Listener
	 private class AreYouSure extends WindowAdapter
    {
      public void windowClosing( WindowEvent e )
      {
			JOptionPane.showMessageDialog(mainFrame,"Press Ok to close Application\nYour All temp data will be lost","Warning",JOptionPane.WARNING_MESSAGE,iconAlert);
			try
			{
				state1.executeUpdate("DELETE FROM table_items");
				System.exit(0);
			}
			catch(Exception closeWindow)
			{
			}
      }
    }

	///
	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() == loginButton)
		{
			login();
		}
		else if (ae.getSource()== forgetpassButton)
		{
			forgetPassFrameUserdata.setText("");
			sequrity_Answer01.setText("");
			sequrity_Answer02.setText("");
			sequrity_Answer03.setText("");
			forgetPassFrame.setTitle(returnResortName);
			forget();
		}
		else if (ae.getSource()==welcomeContinueButton)
		{
			welcomeContinue_Button();
		}
		else if(ae.getSource()==setUpNext)
		{
			setUpNext_Button();
		}
		else if(ae.getSource()==setUpBack)
		{
			resortName.setText("");
			ownerName.setText("");
			gstinNo.setText("");
			contactNo.setText("");
			mail.setText("");
			setUpLoginFrame.setVisible(false);
			welcomeFrame.setVisible(true);
		}
		///
		else if (ae.getSource()== forgetPassFrame_Cancel)
		{
			forgetPassFrame.setVisible(false);
			usernameText.setText("");
			passwordText.setText("");
			frame.setTitle(returnResortName);
			frame.setVisible(true);
		}
		else if (ae.getSource()== forgetPassFrame_Ok)
		{
			check_forgetFrame_Ok_Button();
		}
		else if(ae.getSource()==inventoryAddCategory)
		{
			addNewCategory();
		}
		else if(ae.getSource()==inventoryAddProduct)
		{
			addProductName();
		}
		else if (ae.getSource() == inventoryTables)
		{
			if(userType=='s')
			{
				updateTables();
			}
			else
			{
				errorSuperAdmin();
			}
		}
		else if(ae.getSource() == invetoryCategories)
		{
			if(userType=='s')
			{
				addCategoriesPanel();
				editCategoryDialog.setVisible(true);
			}
			else
			{
				errorSuperAdmin();
			}
		}
		else if(ae.getSource() == invoiceManage)
		{

		}
		else if(ae.getSource() == hrManage)
		{

		}
		else if(ae.getSource() == settingLogin)
		{
			if(userType=='s')
			{
				superAdminPanel("Setting Logs");
			}
			else
			{
				userUpdate();
			}
		}
		else if(ae.getSource()==settingResortInfo)
		{
			if(userType=='s')
			{
				superAdminPanel("Info Resort");
			}
			else
			{
				errorSuperAdmin();
			}
		}
		else if(ae.getSource() == settingPassword)
		{
			updatePassword();
		}
		else if(ae.getSource() == settingTax)
		{

		}
		else if(ae.getSource() == settingDiscount)
		{

		}
		else if(ae.getSource()==settingAddUser)
		{
			if(userType=='s')
			{
				addNewUser();
			}
			else
			{
				errorSuperAdmin();
			}
		}
		else if(ae.getSource()==settingManageUser)
		{
			if(userType=='s')
			{

			}
			else
			{
				errorSuperAdmin();
			}
		}
		else if(ae.getSource() == adminLogs)
		{

		}
		else if(ae.getSource() == adminReport)
		{

		}
		else if(ae.getSource() == adminLogOut)
		{
			loginFrameLogout();
		}
		else if (ae.getSource()== adminRestart)
		{
			JOptionPane.showMessageDialog(mainFrame,"Application is close","Message Close",JOptionPane.PLAIN_MESSAGE,iconAlert);
			System.exit(0);
		}
		for (int i=0;i<loadTables;i++)
		{
				if(ae.getSource() == buttonTable[i])
				{
					buttonTable[i].setBackground(tableBookedColor);
					buttonTable[i].setText((i+1)+" is Booked");
					resultTable = i+1;
					nextLineString=" \n"; itemString = " Item \n\n";quantityString="Quantity \n\n";priceString = "Price \n\n";amountString= "Amount \n\n";
					dataAreaItem.setText(itemString);
					dataAreaQuantity.setText(quantityString);
					dataAreaPrice.setText(priceString);
					dataAreaTotal.setText(amountString);
					getData("Table "+(resultTable));
				}

		}
		for (int i=0;i<loadCategories;i++)
		{
			if(ae.getSource() == buttonCategries[i])
			{
				if (!(resultTable ==0))
				{
					String stringData = buttonCategries[i].getText();
					showProductPanel(stringData);
					errorMsgProduct=0;
				}
				else
				{
					JOptionPane.showMessageDialog(mainFrame,"First Select A table \nthen choose a category ","Warning",JOptionPane.WARNING_MESSAGE,iconAlert);
				}
			}
		}
		///
		if(ae.getSource()== buttonPay)
		{
			aButton = resultTable;
			if(aButton!=0)
			{
				aButton = aButton-1;
				buttonTable[aButton].setBackground(tableButtonColor);
				buttonTable[aButton].setText("Table "+resultTable);
				deleteDataTable();
				resultTable = 0;
				editTableNameLabel.setText("No Table");
			}
		}
		else if (ae.getSource()==buttonCancel)
		{
			aButton = resultTable;
			if(aButton!=0)
			{
				aButton = aButton-1;
				buttonTable[aButton].setBackground(tableButtonColor);
				buttonTable[aButton].setText("Table "+resultTable);
				deleteDataTable();
				resultTable = 0;
				editTableNameLabel.setText("No Table");
			}
		}
		else if(ae.getSource()==buttonEdit)
		{
			editBillFrame();
			editDialog.setSize(550,450);
			editDialog.setVisible(true);
  		}
  		else if(ae.getSource() == productAdd)
		{
			addProduct();
		}
		for(int i=0;i<productNo;i++)
		{
			if(ae.getSource()== productDelete[i])
			{
				if(userType=='s')
				{
					String nameProduct = productName[i].getText();
					String categoryName = productDialog.getTitle();
					try
					{
						if(errorMsgProduct==0)
						{
							int dialogButton = JOptionPane.YES_NO_OPTION;
							int dialogResult = JOptionPane.showConfirmDialog(null,"Press Yes to Delete From System","Delete Warning",dialogButton);
							if(dialogResult == JOptionPane.YES_OPTION)
							{
								state1.executeUpdate("DELETE FROM product WHERE productname LIKE '"+nameProduct+"' AND category LIKE '"+categoryName+"' ");
								errorMsgProduct++;
								productName[i].setVisible(false);
								productPriceValue[i].setVisible(false);
								productValue[i].setVisible(false);
								productDelete[i].setVisible(false);
								productEdit[i].setVisible(false);
								productDialog.dispose();
								showProductPanel(categoryName);
							}
						}
						else
						{
							state1.executeUpdate("DELETE FROM product WHERE productname LIKE '"+nameProduct+"' AND category LIKE '"+categoryName+"' ");
							productName[i].setVisible(false);
							productPriceValue[i].setVisible(false);
							productValue[i].setVisible(false);
							productDelete[i].setVisible(false);
							productEdit[i].setVisible(false);
							productDialog.dispose();
							showProductPanel(categoryName);
						}
					}
					catch(Exception expDeleteProduct)
					{
					}
				}
				else
				{
					errorSuperAdmin();
				}

			}
			else if(ae.getSource()==productEdit[i])
			{
				if(userType=='s')
				{
					int returnVal = 0;
					String stringLocalName = productName[i].getText();
					String stringLocalPrice = productPriceValue[i].getText();
					String localPanelName = productDialog.getTitle();
					JTextField editProduct = new JTextField(stringLocalName);
					JTextField editPrice = new JTextField(stringLocalPrice);
					Object components[] = {"Product Name",editProduct,"Product Price",editPrice};
					JOptionPane productOption = new JOptionPane();
					productOption.setMessage(components);
					productOption.setMessageType(JOptionPane.PLAIN_MESSAGE);
					JDialog localProductDialog = productOption.createDialog(null,"Edit Product :"+stringLocalName);
					localProductDialog.setVisible(true);
					String afterClickName = editProduct.getText();
					String afterClickPrice = editPrice.getText();
					try
					{
						if(!(stringLocalName.equals(afterClickName)))
						{
							state1.executeUpdate("UPDATE product SET productname = '"+afterClickName+"' WHERE productname LIKE '"+stringLocalName+"'");
							returnVal++;
						}
						if(!(stringLocalPrice.equals(afterClickPrice)))
						{
							double afterClickValue = Double.parseDouble(afterClickPrice);
							state1.executeUpdate("UPDATE product SET price = '"+afterClickValue+"' WHERE productname LIKE '"+afterClickName+"'");
							returnVal++;
						}
					}
					catch(Exception editProductExp)
					{

					}
					if(returnVal!=0)
					{
						productDialog.dispose();
						showProductPanel(localPanelName);
					}
				}
				else
				{
					errorSuperAdmin();
				}
			}
		}
		///
		if (items!=0)
		{
			for (int i=0;i<returnItems;i++)
			{
				if(ae.getSource()== editButton[i])
				{
					String editItemName =editItemLabel[i].getText();
					String editQuantity = editQualityLabel[i].getText();
					int intEditQuantity = Integer.parseInt(editQuantity);
					try
					{
						if(errorMsgEdit==0)
						{
							int dialogButton = JOptionPane.YES_NO_OPTION;
							int dialogResult = JOptionPane.showConfirmDialog(null,"Press Yes to Delete From System","Delete Warning",dialogButton);
							if(dialogResult == JOptionPane.YES_OPTION)
							{
								state1.executeUpdate("DELETE FROM table_items WHERE booked_table LIKE '"+tableName+"' AND item LIKE '"+editItemName+"'AND quantity LIKE '"+intEditQuantity+"' ");
								editItemLabel[i].setVisible(false);
								editQualityLabel[i].setVisible(false);
								editButton[i].setVisible(false);
								editButtonForEdit[i].setVisible(false);
								reLoadBill();
								errorMsgEdit++;
							}
						}
						else
						{
							state1.executeUpdate("DELETE FROM table_items WHERE booked_table LIKE '"+tableName+"' AND item LIKE '"+editItemName+"'AND quantity LIKE '"+intEditQuantity+"' ");
							editItemLabel[i].setVisible(false);
							editQualityLabel[i].setVisible(false);
							editButton[i].setVisible(false);
							editButtonForEdit[i].setVisible(false);
							reLoadBill();
						}
					}
					catch(Exception editExpButton)
					{
					}
				}
				if (ae.getSource()==editButtonForEdit[i])
				{
					String stringLocalName = editItemLabel[i].getText();
					String stringLocalQuantity = editQualityLabel[i].getText();
					JTextField editPrice = new JTextField(stringLocalQuantity);
					Object components[] = {stringLocalName,editPrice};
					JOptionPane editItemOption = new JOptionPane();
					editItemOption.setMessage(components);
					editItemOption.setMessageType(JOptionPane.PLAIN_MESSAGE);
					JDialog editItemDialog = editItemOption.createDialog(null,"Edit Item :"+stringLocalName);
					editItemDialog.setVisible(true);
					String newStringLocalQuantity = editPrice.getText();
					if(!(stringLocalQuantity.equals(newStringLocalQuantity)))
					{
						int localQuantity = Integer.parseInt(newStringLocalQuantity);
						try
						{
							state1.executeUpdate("UPDATE table_items SET quantity = '"+localQuantity+"' WHERE item LIKE '"+stringLocalName+"' ");
						}
						catch(Exception editIteemExp)
						{}
						reLoadBill();
						editDialog.dispose();
					}
				}
			}
		}
		///
		if(ae.getSource()==categorySave)
		{
			saveCategory();
		}

		for(int i=0;i<loadCategories1;i++)
		{
		if(ae.getSource()==categoryDeleteButton[i])
			{
				String deleteCat = categoryTextAreaPanel[i].getText();
				categoryTextAreaPanel[i].setText("");
				try
				{
					if(errorMsgCat==0)
					{
						int dialogButton = JOptionPane.YES_NO_OPTION;
						int dialogResult = JOptionPane.showConfirmDialog(null,"Press Yes to Delete From System","Delete Warning",dialogButton);
						if(dialogResult == JOptionPane.YES_OPTION)
						{
							state1.executeUpdate("DELETE FROM categories_list WHERE name_categories LIKE '"+deleteCat+"' ");
							state1.executeUpdate ("DELETE FROM product WHERE category LIKE '"+deleteCat+"' ");
							errorMsgCat++;
							categoryTextAreaPanel[i].setVisible(false);
							categoryDeleteButton[i].setVisible(false);
						}
					}
					else
					{
						state1.executeUpdate("DELETE FROM categories_list WHERE name_categories LIKE '"+deleteCat+"' ");
						state1.executeUpdate ("DELETE FROM product WHERE category LIKE '"+deleteCat+"' ");
						categoryTextAreaPanel[i].setVisible(false);
						categoryDeleteButton[i].setVisible(false);
					}
				}
				catch(Exception expDelCat)
				{

				}
			}
		}
	}

	Login_Resort()
	{
		super("Resort Thread");
		start();
	}

	public static void main(String[] args)
	{
		 javax.swing.SwingUtilities.invokeLater(new Runnable()
		 {
		 	public void run()
		 	{
		 		String fdatas="",fileData="";
		 		try
		 		{
		 			FileInputStream fin = new FileInputStream("resortName.txt");
              		int size=fin.available();
              		byte b[]=new byte[size];
              		fin.read(b);
              		returnResortName=fdatas=new String(b);
              		fin.close();
		 		}
		 		catch(Exception fileOpen)
		 		{

		 		}

		 		try
		 		{
		 			FileInputStream fin = new FileInputStream("resortSetUp.txt");
              		int size=fin.available();
              		byte b[]=new byte[size];
              		fin.read(b);
              		fileData=new String(b);
              		fin.close();
		 		}
		 		catch(Exception fileOpen)
		 		{

		 		}

		 		if (fdatas.equals(""))
		 		{
		 			DbExsit dbExsit = new DbExsit();
		 			dbFlag=dbExsit.isDbExsit();
		 			if(dbFlag==true)
		 			{

		 			}
		 			else
		 			{

		 			}
		 		    createWelcomeGui();
		 		    welcomeFrame.setVisible(true);
		 		    createSetUpGui();
		 		    createLoginGui();
		 		}
		 		else
		 		{
		 			if (fileData.equals("true"))
		 			{
		 				createLoginGui();
		 				frame.setVisible(true);
		 			}
		 			else
		 			{
		 				createSetUpGui();
		 				createLoginGui();
		 				setUpLoginFrame.setVisible(true);
		 			}
		 		}
		 	}
		 });
	}
}
