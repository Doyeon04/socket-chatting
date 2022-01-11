import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Icon;

public class Emoticon extends JFrame {
	private Emoticon emoticon;
	private JPanel oneEmoticonPanel1;
	private JPanel oneEmoticonPanel2;
	private JPanel oneEmoticonPanel3;
	private JPanel oneEmoticonPanel4;
	private JPanel oneEmoticonPanel5;
	private JPanel oneEmoticonPanel6;
	private JPanel oneEmoticonPanel7;
	private JPanel oneEmoticonPanel8;
	private JPanel oneEmoticonPanel9;
	private JPanel oneEmoticonPanel10;
	private JPanel oneEmoticonPanel11;
	private JPanel oneEmoticonPanel12;
	public ChatFriendList chatFriendList;
	
	public String username;
	public int multiNum;
	
	public Emoticon(String username, int multiNum, ChatFriendList chatFriendList) {
		emoticon = this;
		this.username = username;
		this.multiNum = multiNum;
		this.chatFriendList = chatFriendList;
		
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setSize(464, 350);
		setBounds(100, 100, 420, 350);
		
		/*
		ImageIcon emoticonIcon1 = new ImageIcon("C:\\Users\\user\\Desktop\\KoKoaTalkProject\\JavaGameClient\\emoticon1.png"); // 이미지 아이콘 	
		ImageIcon emoticonIcon2 = new ImageIcon("C:\\Users\\user\\Desktop\\KoKoaTalkProject\\JavaGameClient\\emoticon2.png");
		ImageIcon emoticonIcon3 = new ImageIcon("C:\\Users\\user\\Desktop\\KoKoaTalkProject\\JavaGameClient\\emoticon3.png");
		ImageIcon emoticonIcon4 = new ImageIcon("C:\\Users\\user\\Desktop\\KoKoaTalkProject\\JavaGameClient\\emoticon4.png");
		ImageIcon emoticonIcon5 = new ImageIcon("C:\\Users\\user\\Desktop\\KoKoaTalkProject\\JavaGameClient\\emoticon5.png");
		ImageIcon emoticonIcon6 = new ImageIcon("C:\\Users\\user\\Desktop\\KoKoaTalkProject\\JavaGameClient\\emoticon6.png");
		ImageIcon emoticonIcon7 = new ImageIcon("C:\\Users\\user\\Desktop\\KoKoaTalkProject\\JavaGameClient\\emoticon7.png");
		ImageIcon emoticonIcon8 = new ImageIcon("C:\\Users\\user\\Desktop\\KoKoaTalkProject\\JavaGameClient\\emoticon8.png");
		ImageIcon emoticonIcon9 = new ImageIcon("C:\\Users\\user\\Desktop\\KoKoaTalkProject\\JavaGameClient\\emoticon9.png");
		ImageIcon emoticonIcon10 = new ImageIcon("C:\\Users\\user\\Desktop\\KoKoaTalkProject\\JavaGameClient\\emoticon10.png");

       */
		
		ImageIcon emoticonIcon1 = new ImageIcon("./emoticon1.png");
		ImageIcon emoticonIcon2 = new ImageIcon("./emoticon2.png");
		ImageIcon emoticonIcon3 = new ImageIcon("./emoticon3.png");
		ImageIcon emoticonIcon4 = new ImageIcon("./emoticon4.png");
		ImageIcon emoticonIcon5 = new ImageIcon("./emoticon5.png");
		ImageIcon emoticonIcon6 = new ImageIcon("./emoticon6.png");
		ImageIcon emoticonIcon7 = new ImageIcon("./emoticon7.png");
		ImageIcon emoticonIcon8 = new ImageIcon("./emoticon8.png");
		ImageIcon emoticonIcon9 = new ImageIcon("./emoticon9.png");
		ImageIcon emoticonIcon10 = new ImageIcon("./emoticon10.png");
		
		
		oneEmoticonPanel1 = new JPanel(); // 이모티콘 파넬 
		oneEmoticonPanel1.setBackground(Color.WHITE);
		oneEmoticonPanel1.setBounds(25, 32, 80, 66);
		getContentPane().add(oneEmoticonPanel1);
		
		JLabel oneEmoticonLabel1 = new JLabel(emoticonIcon1); // 이모티콘 라벨 
		oneEmoticonLabel1.setBackground(Color.WHITE);
		oneEmoticonPanel1.add(oneEmoticonLabel1);
		
		oneEmoticonPanel2 = new JPanel();
		oneEmoticonPanel2.setBackground(Color.WHITE);
		oneEmoticonPanel2.setBounds(117, 32, 80, 66);
		getContentPane().add(oneEmoticonPanel2);
		
		JLabel oneEmoticonLabel2 = new JLabel(emoticonIcon2);
		oneEmoticonPanel2.add(oneEmoticonLabel2);
		
		oneEmoticonPanel3 = new JPanel();
		oneEmoticonPanel3.setBackground(Color.WHITE);
		oneEmoticonPanel3.setBounds(209, 32, 80, 66);
		getContentPane().add(oneEmoticonPanel3);
		
		JLabel oneEmoticonLabel3 = new JLabel(emoticonIcon3);
		oneEmoticonPanel3.add(oneEmoticonLabel3);
		
		oneEmoticonPanel4 = new JPanel();
		oneEmoticonPanel4.setBackground(Color.WHITE);
		oneEmoticonPanel4.setBounds(301, 32, 80, 66);
		getContentPane().add(oneEmoticonPanel4);
		
		JLabel oneEmoticonLabel4 = new JLabel(emoticonIcon4);
		oneEmoticonPanel4.add(oneEmoticonLabel4);
		
		oneEmoticonPanel5 = new JPanel();
		oneEmoticonPanel5.setBackground(Color.WHITE);
		oneEmoticonPanel5.setBounds(25, 121, 80, 66);
		getContentPane().add(oneEmoticonPanel5);
		
		JLabel oneEmoticonLabel15 = new JLabel(emoticonIcon5);
		oneEmoticonPanel5.add(oneEmoticonLabel15);
		
		oneEmoticonPanel6 = new JPanel();
		oneEmoticonPanel6.setBackground(Color.WHITE);
		oneEmoticonPanel6.setBounds(117, 121, 80, 66);
		getContentPane().add(oneEmoticonPanel6);
		
		JLabel oneEmoticonLabel16 = new JLabel(emoticonIcon6);
		oneEmoticonPanel6.add(oneEmoticonLabel16);
		
		oneEmoticonPanel7 = new JPanel();
		oneEmoticonPanel7.setBackground(Color.WHITE);
		oneEmoticonPanel7.setBounds(209, 121, 80, 66);
		getContentPane().add(oneEmoticonPanel7);
		
		JLabel oneEmoticonLabel7 = new JLabel(emoticonIcon7);
		oneEmoticonPanel7.add(oneEmoticonLabel7);
		
		oneEmoticonPanel8 = new JPanel();
		oneEmoticonPanel8.setBackground(Color.WHITE);
		oneEmoticonPanel8.setBounds(301, 121, 80, 66);
		getContentPane().add(oneEmoticonPanel8);
		
		JLabel oneEmoticonLabel8 = new JLabel(emoticonIcon8);
		oneEmoticonPanel8.add(oneEmoticonLabel8);
		
		oneEmoticonPanel9 = new JPanel();
		oneEmoticonPanel9.setBackground(Color.WHITE);
		oneEmoticonPanel9.setBounds(25, 209, 80, 66);
		getContentPane().add(oneEmoticonPanel9);
		
		JLabel oneEmoticonLabel9 = new JLabel(emoticonIcon9);
		oneEmoticonPanel9.add(oneEmoticonLabel9);
		
		oneEmoticonPanel10 = new JPanel();
		oneEmoticonPanel10.setBackground(Color.WHITE);
		oneEmoticonPanel10.setBounds(117, 209, 80, 66);
		getContentPane().add(oneEmoticonPanel10);
		
		JLabel oneEmoticonLabel10 = new JLabel(emoticonIcon1);
		oneEmoticonPanel10.add(oneEmoticonLabel10);
		
		oneEmoticonPanel11 = new JPanel();
		oneEmoticonPanel11.setBackground(Color.WHITE);
		oneEmoticonPanel11.setBounds(209, 209, 80, 66);
		getContentPane().add(oneEmoticonPanel11);
		
		JLabel oneEmoticonLabel11 = new JLabel((Icon) null);
		oneEmoticonPanel11.add(oneEmoticonLabel11);
		
		oneEmoticonPanel12 = new JPanel();
		oneEmoticonPanel12.setBackground(Color.WHITE);
		oneEmoticonPanel12.setBounds(301, 209, 80, 66);
		getContentPane().add(oneEmoticonPanel12);
		
		JLabel oneEmoticonLabel12 = new JLabel((Icon) null);
		oneEmoticonPanel12.add(oneEmoticonLabel12);

		SelectEmoticon selectEmoticon = new SelectEmoticon();
		oneEmoticonPanel1.addMouseListener(selectEmoticon); //이 아이콘 누르면 이모티콘 보내지게
		oneEmoticonPanel2.addMouseListener(selectEmoticon); 
		oneEmoticonPanel3.addMouseListener(selectEmoticon); 
		oneEmoticonPanel4.addMouseListener(selectEmoticon); 
		oneEmoticonPanel5.addMouseListener(selectEmoticon); 
		oneEmoticonPanel6.addMouseListener(selectEmoticon); 
		oneEmoticonPanel7.addMouseListener(selectEmoticon); 
		oneEmoticonPanel8.addMouseListener(selectEmoticon); 
		oneEmoticonPanel9.addMouseListener(selectEmoticon); 
		oneEmoticonPanel10.addMouseListener(selectEmoticon); 
		oneEmoticonPanel11.addMouseListener(selectEmoticon); 
		oneEmoticonPanel12.addMouseListener(selectEmoticon); 

	}
	
	public class SelectEmoticon  extends MouseAdapter{
		public void mouseClicked(MouseEvent e) { 
			System.out.println(e.getSource());
			 ChatMsg obcm = new ChatMsg(username, "300", "IMG");
			 ImageIcon img = null;
			 
			if(e.getSource() == oneEmoticonPanel1) {

			 img = new ImageIcon("./emoticon1.png");
	 
			}
			if(e.getSource() == oneEmoticonPanel2) {
				img = new ImageIcon("./emoticon2.png");
				
			}
			if(e.getSource() == oneEmoticonPanel3) {
				img = new ImageIcon("./emoticon3.png");				
			}
			if(e.getSource() == oneEmoticonPanel4) {
				img = new ImageIcon("./emoticon4.png");				
			}
			if(e.getSource() == oneEmoticonPanel5) {
				img = new ImageIcon("./emoticon5.png");				
			}
			if(e.getSource() == oneEmoticonPanel6) {
				img = new ImageIcon("./emoticon6.png");				
			}
			if(e.getSource() == oneEmoticonPanel7) {
				img = new ImageIcon("./emoticon7.png");				
			}
			if(e.getSource() == oneEmoticonPanel8) {
				img = new ImageIcon("./emoticon8.png");				
			}
			if(e.getSource() == oneEmoticonPanel9) {
				img = new ImageIcon("./emoticon9.png");				
			}
			if(e.getSource() == oneEmoticonPanel10) {
				img = new ImageIcon("./emoticon10.png");				
			}
			
			
			
			obcm.img = img;
            obcm.multiChatNum=multiNum;
            
            chatFriendList.SendObject(obcm);
            
		}
	}
}
