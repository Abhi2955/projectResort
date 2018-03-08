import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;
public class BillPrint implements ActionListener,Printable
{
	private JFrame jf;
	private JPanel jp,buttonPanel;
	private JLabel resortName,conactNo,contactMail,itemName[],itemQuantity[],itemPrice[],totalItemPrice[],
					taxName,taxAmount,totalAmount,grossTotal;
	private JScrollPane jsp;
	private JButton jb;
	private Font resortNameFont = new Font("Lucida Sans Unicode",Font.PLAIN,40);
	private void makeGui()
	{
		jf = new JFrame("Bill");
		jp = new JPanel();
		jp.setLayout(null);
		jp.setSize(400,430);
		jp.setBackground(new java.awt.Color(255,255,255));
		buttonPanel = new JPanel();
		resortName = new JLabel("Test-Best Restro");
		Insets insets = jp.getInsets();
		Dimension size = resortName.getPreferredSize();
		resortName.setBounds(25+insets.left,40+insets.top,size.width+400,size.height+30);
		resortName.setFont(resortNameFont);
		jb = new JButton("Print");
		buttonPanel.add(jb);
		jp.add(resortName);
		jf.add(jp);
		jf.add(buttonPanel);
		jb.addActionListener(this);
		jf.setVisible(true);
		jf.setSize(400,450);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	public void actionPerformed(ActionEvent ae)
	{
        if(ae.getSource()==jb)
        {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(this);
            job.setJobName("Bill");
            boolean ok = job.printDialog();
            if (ok)
            {
                try
                {
                    job.print();
                }
                catch (PrinterException ex)
                {

                }
            }
        }
	}
	  public int print(Graphics g, PageFormat pf, int page) throws   PrinterException
    {

        if (page > 0)
            return NO_SUCH_PAGE;
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        jp.printAll(g);
        return PAGE_EXISTS;
    }
	public static void main(String[] args) 
	{
		new BillPrint().makeGui();
	}
}