package main;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Robot;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JTextArea;

import model.Work;
import dao.WorkDao;

import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.JCheckBox;
import javax.swing.JScrollBar;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ApplicationUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	final JPanel gosterPanel = new JPanel();
	JButton btnTklatBana = new JButton("G\u00F6rseli getir");
	
	JRadioButton radioOnur = new JRadioButton("Geli\u015Fig\u00FCzel Sat\u0131rlar");
	JRadioButton radioOzge = new JRadioButton("S\u00F6zc\u00FCk Toplar\u0131");
	JRadioButton radioCloud = new JRadioButton("Kelime Bulutu");

	JCheckBox chckbxDzizgi = new JCheckBox("D\u00FCz \u00C7izgi");
	JCheckBox chckbxCurve = new JCheckBox("Curve");
	JCheckBox chckbxDoldur = new JCheckBox("Doldur");
	
	private final JScrollPane scrollPane = new JScrollPane();
	private final JList list = new JList();
	
	/**
	 * Create the frame.
	 */
	public ApplicationUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 3000, 1700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		gosterPanel.setBounds(816, 28, 2098, 1523);
		contentPane.add(gosterPanel);
		btnTklatBana.setBounds(207, 1360, 405, 83);
		
		
		btnTklatBana.setFont(new Font("Segoe Print", Font.PLAIN, 36));
		btnTklatBana.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//lblInfo.setVisible(true);
				Work selecteWork = (Work)(list.getSelectedValue());
				if(radioOnur.isSelected()){
					boolean isCurve = true;
					if(chckbxCurve.isSelected()){
						isCurve = true;
					}else if(chckbxDzizgi.isSelected()){
						isCurve = false;
					}
					OnurLineGorseli embed = new OnurLineGorseli(gosterPanel.getWidth(),gosterPanel.getHeight(),selecteWork.getWorkID(),selecteWork.getName(),isCurve,chckbxDoldur.isSelected());
					gosterPanel.removeAll();
					gosterPanel.add(embed, BorderLayout.WEST);
					embed.init();
					//JOptionPane.showMessageDialog(getParent(), "tursu");
					//lblInfo.setText("Kelimeler geliþigüzel daðýtýldý ve ayný satýrdaki kelimeler oklarla birbirlerine baðlandý");
				}else if(radioOzge.isSelected()){
					OzgeLine embed = new OzgeLine(gosterPanel.getWidth(),gosterPanel.getHeight(),selecteWork.getWorkID());
					gosterPanel.removeAll();
					gosterPanel.add(embed, BorderLayout.WEST);
					embed.init();
					/*lblInfo.setText("Daireler sözcüklerin þiirdeki konumuna göre yerleþtirilmiþtir. Çaplarýnýn uzunluðu sözcük uzunluðu ile orantýlýdýr. "+
							"Renkli daireler renk bildiren sýfatlarý ifade eder.\n* Þiiri görmek için Space tuþuna basýnýz.");*/
				}else if(radioCloud.isSelected()){
					WordCramCloud embed = new WordCramCloud(gosterPanel.getWidth(),gosterPanel.getHeight(),selecteWork.getWorkID());
					gosterPanel.removeAll();
					gosterPanel.add(embed, BorderLayout.WEST);
					embed.init();
					//lblInfo.setText("Kelimelerin büyüklüðü kullaným sýklýðýný gösterir.");
				}
				

			}
		});
		contentPane.add(btnTklatBana);
		
		JLabel lbliirAra = new JLabel("\u015Eiir Ara:");
		lbliirAra.setBounds(23, 25, 225, 46);
		lbliirAra.setFont(new Font("Segoe Print", Font.PLAIN, 36));
		contentPane.add(lbliirAra);
				
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 36));
		textField.setBounds(204, 25, 513, 56);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblSonular = new JLabel("Sonu\u00E7lar:");
		lblSonular.setBounds(26, 141, 238, 46);
		lblSonular.setFont(new Font("Segoe Print", Font.PLAIN, 30));
		contentPane.add(lblSonular);
		radioOnur.setFont(new Font("Tahoma", Font.PLAIN, 36));
		radioOnur.setBounds(105, 958, 356, 46);
		
		radioOnur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				radioOzge.setVisible(!radioOzge.isVisible());
				radioCloud.setVisible(!radioCloud.isVisible());
				chckbxCurve.setVisible(!chckbxCurve.isVisible());
				chckbxDzizgi.setVisible(!chckbxDzizgi.isVisible());
				chckbxDoldur.setVisible(!chckbxDoldur.isVisible());
			}
		});
		contentPane.add(radioOnur);
		radioOzge.setFont(new Font("Tahoma", Font.PLAIN, 36));
		radioOzge.setBounds(105, 1024, 310, 63);
		contentPane.add(radioOzge);
		radioCloud.setFont(new Font("Tahoma", Font.PLAIN, 36));
		radioCloud.setBounds(103, 1107, 369, 76);
		contentPane.add(radioCloud);
		chckbxDzizgi.setFont(new Font("Tahoma", Font.PLAIN, 34));
		chckbxDzizgi.setBounds(266, 1000, 319, 46);
		contentPane.add(chckbxDzizgi);
		chckbxDzizgi.setVisible(false);
		chckbxCurve.setFont(new Font("Tahoma", Font.PLAIN, 34));
		chckbxCurve.setBounds(266, 1066, 264, 41);
		chckbxCurve.setVisible(false);
		
		///scrollPane.setViewportView(list);
		contentPane.add(chckbxCurve);
		scrollPane.setBounds(20, 191, 697, 719);
		//scrollPane.setViewportView(list);
		contentPane.add(scrollPane);
		list.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		scrollPane.setViewportView(list);
		chckbxDoldur.setFont(new Font("Tahoma", Font.PLAIN, 34));
		
		
		chckbxDoldur.setBounds(266, 1131, 394, 62);
		contentPane.add(chckbxDoldur);
		chckbxDoldur.setVisible(false);
		
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WorkDao workDao = new WorkDao();
				List<Work> findedWorks;
				try {
					 findedWorks = new ArrayList<Work>(workDao.getWorksByWordName(textField.getText()));
					 list.setModel(getListModel(findedWorks));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		
	}
	
	private ListModel getListModel(List<Work> workList){
		DefaultListModel<Work> model = new DefaultListModel<Work>();
		for(int i = 0; i<workList.size(); i++){
			model.addElement(workList.get(i));
		}
		return model;

	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
