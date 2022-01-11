//MultiChat.java
import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.w3c.dom.events.MouseEvent;

import java.awt.FileDialog;

public class MultiChat extends JFrame{
	private JPanel invitedUsersPanel;
   private JPanel contentPane;
   private JTextField txtInput;
   // private JTextArea textArea;
    private JTextPane textArea;
    private JButton btnSend;
    private JButton imgBtn;
    private Frame frame;
    private FileDialog fd;
    private MultiChat multiChat;
    JLabel lastMessageLabel;
    JLabel timeLabel;
    JPanel panel;
    private JButton clearTextAreaBtn;
    String invitedFriendsString;
    //private Graphics gc;
   private int pen_size = 2; // minimum 2
   // �׷��� Image�� �����ϴ� �뵵, paint() �Լ����� �̿��Ѵ�.
   private Image panelImage = null; 
   private Graphics gc2 = null;
   JLabel invitedUsersLabel;
   String username;
   int multiNum; // ���ȣ
   String invitedFriendsArr[];
   public ChatFriendList chatFriendList;
   public String bringTime() {
      String presentTime;
      String hourText;
      Calendar cal = Calendar.getInstance();
      int hour = cal.get(Calendar.HOUR_OF_DAY); 
      int minute = cal.get(Calendar.MINUTE);
      if(hour>=12) {
         hourText="����";
         hour%=12;
      }
      else {
         hourText = "����";
      }
      if(hour == 0) {
    	  hour = 12;
    	  
      }
      if(minute <10) {
         presentTime = hourText+" "+hour+":0"+minute;
      }
      else {
         presentTime = hourText+" "+hour+":"+minute;
      }
      
      return presentTime;
      
      
   }
   class Myaction implements ActionListener // ����Ŭ������ �׼� �̺�Ʈ ó�� Ŭ����
    {
       @Override
       public void actionPerformed(ActionEvent e) {
          // Send button�� �����ų� �޽��� �Է��ϰ� Enter key ġ��
          if (e.getSource() == btnSend || e.getSource() == txtInput) {
             String msg = null;
             msg = String.format("%s\n", txtInput.getText());
             String presentTime = bringTime();
             ChatMsg obcm = new ChatMsg(username,"210",msg);
             obcm.presentTime=presentTime;
             obcm.multiChatNum=multiNum;
             obcm.senderImg = new ImageIcon("src/profilesPakage/"+username+".jpg");
             chatFriendList.SendObject(obcm);
             //��ѹ��� �渮��Ʈ
             
             txtInput.setText(""); // �޼����� ������ ���� �޼��� ����â�� ����.
             txtInput.requestFocus(); // �޼����� ������ Ŀ���� �ٽ� �ؽ�Ʈ �ʵ�� ��ġ��Ų��
             if (msg.contains("/exit")) // ���� ó��
                System.exit(0);
          }
       }
    }
   class ImageSendAction implements ActionListener { // �̹��� ������ 
      @Override
      public void actionPerformed(ActionEvent e) {
         // �׼� �̺�Ʈ�� sendBtn�϶� �Ǵ� textField ���� Enter key ġ��
         if (e.getSource() == imgBtn) {
            frame = new Frame("�̹���÷��");
            fd = new FileDialog(frame, "�̹��� ����", FileDialog.LOAD);
            
             fd.setDirectory(".\\");
            fd.setVisible(true);
           
            if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
               ChatMsg obcm = new ChatMsg(username, "300", "IMG"); // �ڵ� 300�� �Բ� �̹��� ���� 
               ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
             
               String presentTime = bringTime();
               obcm.senderImg = new ImageIcon("src/profilesPakage/"+username+".jpg");
               obcm.img = img;
               obcm.multiChatNum=multiNum;
               obcm.data = "image";
               obcm.presentTime = presentTime;
               chatFriendList.SendObject(obcm);
            }
         }
      }
   }
   
   class EmoticonSendAction implements ActionListener { // �̸�Ƽ�� ������ 
         @Override
         public void actionPerformed(ActionEvent e) {
            
          
            Emoticon emoticon = new Emoticon(username, multiNum, chatFriendList,multiChat);
            emoticon.setVisible(true);
            
         }
      }

      class ChatOutAction implements ActionListener{
       @Override
         public void actionPerformed(ActionEvent e) {
            
            ChatMsg obcm = new ChatMsg(username,"560",multiNum+"�濡�� "+username+"����");
            obcm.multiChatNum = multiNum;
            chatFriendList.SendObject(obcm);
            
            //���ڽ����״� �� ä�ù� �гε� �Ⱥ����� �Ű� 
            chatFriendList.deleteThisChat(multiNum);
            multiChat.setVisible(false);
         }
         
      }
      class ClearTextArea implements ActionListener{ // ��ȭ ���� ���� 
          @Override
            public void actionPerformed(ActionEvent e) {
             //textArea.removeAll();
             textArea.setText(null);
            }
            
         }
   public MultiChat(String username, int multiNum,String [] invitedFriendsArr,ChatFriendList chatFriendList,JLabel lastMessageLabel,JLabel timeLabel) {
      this.chatFriendList = chatFriendList;
      this.username = username; 
      this.multiNum = multiNum;
      this.invitedFriendsArr = invitedFriendsArr;
      this.lastMessageLabel=lastMessageLabel;
      this.timeLabel = timeLabel;
      this.multiChat = this;
         setSize(416,634);
         setBounds(100, 100, 390, 634);
         JPanel contentPane = new JPanel();
         contentPane.setBackground(Color.WHITE);
         contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
         setContentPane(contentPane);
         contentPane.setLayout(null);
         JScrollPane scrollPane = new JScrollPane();
       scrollPane.setBounds(0, 49, 376, 432);
       contentPane.add(scrollPane);

       textArea = new JTextPane();
       textArea.setEditable(true);
       textArea.setFont(new Font("���� ����", Font.PLAIN, 14));
       scrollPane.setViewportView(textArea);
       
       
       Color textAreaColor=new Color(155,187,212);  
       textArea.setBackground(textAreaColor);
     
       
       invitedUsersPanel = new JPanel();
       invitedUsersPanel.setBounds(0, 0, 376, 63);
       contentPane.add(invitedUsersPanel);
       Color darkBlueColor = new Color(151, 183, 209);
       invitedUsersPanel.setBackground(darkBlueColor);
       invitedUsersPanel.setLayout(null);
       invitedUsersLabel = new JLabel("New label");
       invitedUsersLabel.setFont(new Font("���� ����", Font.PLAIN, 17));
       invitedUsersLabel.setSize(199,30);
       invitedUsersLabel.setLocation(10,11);
       invitedUsersPanel.add(invitedUsersLabel);
       
       txtInput = new JTextField();
       txtInput.setBounds(10, 515, 288, 30);
       contentPane.add(txtInput);
       txtInput.setColumns(10);
     
       Color yellowColor=new Color(254,240,27);  
       
     
       btnSend = new JButton("\uC804\uC1A1");
       btnSend.setFont(new Font("���� ����", Font.PLAIN, 9));
       btnSend.setBounds(310, 515, 54, 30);
       contentPane.add(btnSend);
       
       btnSend.setBackground(yellowColor);

       Myaction action = new Myaction();
      btnSend.addActionListener(action); // ����Ŭ������ �׼� �����ʸ� ��ӹ��� Ŭ������
      txtInput.addActionListener(action);
       this.setVisible(true);
       
       // ������ �ٲ�
     
       
     invitedFriendsString = String.join(",", invitedFriendsArr);
      
       invitedUsersLabel.setText(invitedFriendsString+" ("+invitedFriendsArr.length+")");
       
       JButton changeColorBtn = new JButton("\uBC30\uACBD\uC0C9 \uBCC0\uACBD");
       changeColorBtn.setFont(new Font("���� ����", Font.PLAIN, 11));
       changeColorBtn.setFocusPainted(false);
       changeColorBtn.setContentAreaFilled(false);
       changeColorBtn.setBounds(259, 18, 105, 23);
       invitedUsersPanel.add(changeColorBtn);
       //changeColorBtn.addActionListener(new MenuActionListener());
       MenuActionListener menuActionListener = new MenuActionListener();
       changeColorBtn.addActionListener(menuActionListener);
       
       //�̺�Ʈ ������ -> ������ �ڱ� �̸��̶� multinum, �������� -> �������� �����ϴ� map���� ������� -> ���� ����Ʈ �ٸ� �����鿡�� ������.
       ChatOutAction chatOutAction = new ChatOutAction();
       
       ImageIcon addImageIcon = new ImageIcon("src/imageBtn.png");
       Image tempImgIcon = addImageIcon.getImage();
       Image changeAddImageIcon = tempImgIcon.getScaledInstance(40,30,Image.SCALE_SMOOTH);
       ImageIcon newAddImageIcon = new ImageIcon(changeAddImageIcon);
       imgBtn = new JButton(newAddImageIcon);
       imgBtn.setBorderPainted(false);
       imgBtn.setContentAreaFilled(false);
       imgBtn.setFocusPainted(false);
       imgBtn.setFont(new Font("���� ����", Font.PLAIN, 16));
       imgBtn.setBounds(10, 556, 40, 30);
       contentPane.add(imgBtn);
       
       ImageSendAction action2 = new ImageSendAction();
      imgBtn.addActionListener(action2);
       
       panel = new JPanel();
      panel.setBorder(new LineBorder(new Color(0, 0, 0)));
      panel.setBackground(Color.WHITE);
      panel.setBounds(376, 10, 400, 520);
      contentPane.add(panel);
      ClearTextArea clearTextArea = new ClearTextArea();
      
      ImageIcon emoticonBtnIcon = new ImageIcon("src/emoticonBtn.png");
      JButton emoticonBtn = new JButton(emoticonBtnIcon); // �̸�Ƽ�� ��ư
      emoticonBtn.setBorderPainted(false);
      emoticonBtn.setContentAreaFilled(false);
      emoticonBtn.setFocusPainted(false);
      emoticonBtn.setBounds(55, 556, 40, 30);
      contentPane.add(emoticonBtn);
      
   
      JButton chatOutBtn = new JButton("\uB098\uAC00\uAE30");
      chatOutBtn.setBounds(290, 563, 74, 23);
      contentPane.add(chatOutBtn);
      
        chatOutBtn.setContentAreaFilled(false);
        chatOutBtn.setFocusPainted(false);
        chatOutBtn.setFont(new Font("���� ����", Font.PLAIN, 11));
        
        clearTextAreaBtn = new JButton("\uB300\uD654 \uB0B4\uC6A9 \uC0AD\uC81C");
        clearTextAreaBtn.setBounds(158, 563, 120, 23);
        contentPane.add(clearTextAreaBtn);
        clearTextAreaBtn.setFont(new Font("���� ����", Font.PLAIN, 11));
        clearTextAreaBtn.setFocusPainted(false);
        clearTextAreaBtn.setContentAreaFilled(false);
        clearTextAreaBtn.addActionListener(clearTextArea);
        chatOutBtn.addActionListener(chatOutAction);
      //->������ ����Ʈ�� ���� ��� ���� �����鿡�� ���� (�������� ����) -> �޾Ƽ� ����Ʈ ���� �ϰ� 
      
      
      
      EmoticonSendAction action3 = new EmoticonSendAction();
      emoticonBtn.addActionListener(action3);
      
  
      
      // Image ���� ������. paint() ���� �̿��Ѵ�.
      panelImage = createImage(panel.getWidth(), panel.getHeight());
      gc2 = panelImage.getGraphics();
      gc2.setColor(panel.getBackground());
      gc2.fillRect(0,0, panel.getWidth(),  panel.getHeight());
      gc2.setColor(Color.BLACK);
      gc2.drawRect(0,0, panel.getWidth()-1,  panel.getHeight()-1);
      
      this.setVisible(false);
      
    
  
   }
   class MenuActionListener implements ActionListener {
	      JColorChooser chooser = new JColorChooser(); //�÷� ���̾�α� ����

	public void actionPerformed(ActionEvent e) {

	    Color selectedColor = chooser.showDialog(null, "Color", Color.YELLOW); // �÷� ���̾�α׸� ����ϰ� ����ڰ� ������ ���� �˾ƿ�.

	    textArea.setBackground(selectedColor);
	    invitedUsersPanel.setBackground(selectedColor);
	    
	    if (selectedColor != null) {
	     textArea.setForeground(selectedColor); // ��� ��ư�� �����ų� ���̾�α׸� �׳� �ݴ� ���
	     invitedUsersPanel.setForeground(selectedColor); 
	    }
	 

	}

	 }



	
   
   public void refreshMultiChatUsers(ArrayList multiChatUsers) {
       invitedFriendsString = String.join(",", multiChatUsers);
        
        invitedUsersLabel.setText(invitedFriendsString+" ("+multiChatUsers.size()+")");
      
   }
   public int getMultiNum() {
      return multiNum;
   }
   public void AppendProfile(String userName,ImageIcon profileImg) {//���ʿ�
      Image image = profileImg.getImage();
       
       image.getScaledInstance(40,40,Image.SCALE_SMOOTH);
       profileImg.setImage(image);
       
          int len = textArea.getDocument().getLength();
          textArea.setCaretPosition(len); // place caret at the end (with no selection)
          Image ori_img = profileImg.getImage();
          Image new_img;
          ImageIcon new_icon;
          int width, height;
          double ratio;
          width = profileImg.getIconWidth();
          height = profileImg.getIconHeight();
          // Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 200 �������� ��ҽ�Ų��.
          if (width > 40 || height > 40) {
              if (width > height) { // ���� ����
                 ratio = (double) height / width;
                 width = 40;
                 height = (int) (width * ratio);
              } else { // ���� ����
                 ratio = (double) width / height;
                 height = 40;
                 width = (int) (height * ratio);
              }
               new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
              new_icon = new ImageIcon(new_img);
              textArea.insertIcon(new_icon);
           } else {
              textArea.insertIcon(profileImg);
              new_img = ori_img;
           }                  
          len = textArea.getDocument().getLength();
          textArea.setCaretPosition(len);
          textArea.replaceSelection(" ");
        

          gc2.drawImage(ori_img,  0,  0, panel.getWidth(), panel.getHeight(), panel);
       
      
       
          userName = userName.trim(); // �յ� blank�� \n�� �����Ѵ�.    
         StyledDocument doc = textArea.getStyledDocument();
         SimpleAttributeSet left = new SimpleAttributeSet();
         StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
         StyleConstants.setForeground(left, Color.BLACK);
         StyleConstants.setFontSize(left, 20);
          doc.setParagraphAttributes(doc.getLength(), 1, left, false);
         try {
            doc.insertString(doc.getLength(), userName+"\n", left );
         } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      
   }
   public void AppendProfileR(String userName) {//���ʿ�     
          userName = userName.trim(); // �յ� blank�� \n�� �����Ѵ�.    
         StyledDocument doc = textArea.getStyledDocument();
         SimpleAttributeSet right = new SimpleAttributeSet();
         StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
         StyleConstants.setForeground(right, Color.BLUE);   
         StyleConstants.setFontSize(right, 20);
         Font allFont = new Font("���� ����", Font.PLAIN, 20);
         textArea.setFont(allFont);
          doc.setParagraphAttributes(doc.getLength(), 1, right, false);
         try {
            doc.insertString(doc.getLength(),userName+"\n", right );//""���ڿ��� ������ ����->�̹���+\n->text
         } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      
   }
   public void AppendTime(String presentTime) {
      int len = textArea.getDocument().getLength();
       textArea.setCaretPosition(len); // place caret at the end (with no selection)
      StyledDocument doc = textArea.getStyledDocument();
         SimpleAttributeSet left = new SimpleAttributeSet();
         StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
         
         StyleConstants.setForeground(left, Color.BLACK);
         StyleConstants.setFontSize(left, 10);
       
         
         StyleContext context = new StyleContext();
         Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
         StyleConstants.setFirstLineIndent(style, 16);
         
         Font allFont = new Font("���� ����", Font.PLAIN, 10);
         textArea.setFont(allFont);
         
          doc.setParagraphAttributes(doc.getLength(), 1, left, false);
         try {
            doc.insertString(doc.getLength(), " "+presentTime+"\n\n", left );
            
         } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         len = textArea.getDocument().getLength();
         textArea.setCaretPosition(len);
         textArea.replaceSelection("\n");
   }
   public void AppendTimeR(String presentTime) {
      StyledDocument doc = textArea.getStyledDocument();
         SimpleAttributeSet right = new SimpleAttributeSet();
         StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
         
         StyleConstants.setForeground(right, Color.BLACK);
         StyleConstants.setFontSize(right, 10);
        
         
         StyleContext context = new StyleContext();
         Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
         StyleConstants.setFirstLineIndent(style, 16);
         
         Font allFont = new Font("���� ����", Font.PLAIN, 10);
         textArea.setFont(allFont);
         
         
          doc.setParagraphAttributes(doc.getLength(), 1, right, false);
         try {
            doc.insertString(doc.getLength(), presentTime+" ", right );
            
         } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
        
   }
   public void AppendText(String msg, String presentTime) {
    
      msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.
      int len = textArea.getDocument().getLength();
      // ������ �̵�
     
      lastMessageLabel.setText(msg);
      timeLabel.setText(presentTime);
      StyledDocument doc = textArea.getStyledDocument();
      SimpleAttributeSet left = new SimpleAttributeSet();
      StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
      
      StyleConstants.setForeground(left, Color.BLACK);
      StyleConstants.setFontSize(left, 16);
      StyleConstants.setBackground(left, Color.WHITE);  
      
      Font allFont = new Font("���� ����", Font.PLAIN, 16);
      textArea.setFont(allFont);
      
      
      StyleContext context = new StyleContext();
      Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
      StyleConstants.setFirstLineIndent(style, 16);
      
       doc.setParagraphAttributes(doc.getLength(), 1, left, false);
      try {
         doc.insertString(doc.getLength(), " "+msg+" ", left );
         
      } catch (BadLocationException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
    

   }
   
   public void AppendNotice(String msg) {
         msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.
         int len = textArea.getDocument().getLength();
         // ������ �̵�

         StyledDocument doc = textArea.getStyledDocument();
         SimpleAttributeSet center = new SimpleAttributeSet();
         StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
         StyleConstants.setForeground(center, Color.BLACK);
         StyleConstants.setFontSize(center, 13);
         
         Font allFont = new Font("���� ����", Font.PLAIN, 13);
         textArea.setFont(allFont);
         
         doc.setParagraphAttributes(doc.getLength(), 1, center, false);
         try {
            doc.insertString(doc.getLength(), msg + "\n", center);
         } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
         len = textArea.getDocument().getLength();
         textArea.setCaretPosition(len);
         textArea.replaceSelection("\n");
      }
   public void paint(Graphics g) {
      super.paint(g);
      // Image ������ �������� �ٽ� ��Ÿ�� �� �׷��ش�.
   
   }
   
   
   public void AppendTextR(String msg, String presentTime) {
      int len = textArea.getDocument().getLength();
       textArea.setCaretPosition(len); // place caret at the end (with no selection)
         msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�. 
         lastMessageLabel.setText(msg);
         timeLabel.setText(presentTime);
   
         StyledDocument doc = textArea.getStyledDocument();
         SimpleAttributeSet right = new SimpleAttributeSet();
         StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
         StyleConstants.setForeground(right, Color.BLACK);   
         StyleConstants.setBackground(right, new Color(254,240,27));   
           
         Font allFont = new Font("���� ����", Font.PLAIN, 16);
         textArea.setFont(allFont);
         
         StyleConstants.setFontSize(right, 16);
          doc.setParagraphAttributes(doc.getLength(), 1, right, false);
         try {
            if(msg.length()>18) {
                   
                String firstMsg = msg.substring(0,17);
                doc.insertString(doc.getLength(), " "+firstMsg+" \n", right );
                this.AppendTimeR(presentTime);
                 String secondMsg = msg.substring(18,msg.length()-1);
                doc.insertString(doc.getLength(), " "+secondMsg+" \n\n", right );
                }
                else {
                   this.AppendTimeR(presentTime);
                   doc.insertString(doc.getLength()," "+msg+" \n\n", right );
                }         
            //""���ڿ��� ������ ����->�̹���+\n->text
         } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         len = textArea.getDocument().getLength();
         textArea.setCaretPosition(len);
         textArea.replaceSelection("\n");

      }
   
public void AppendImage(ImageIcon ori_icon, String ImageType, String presentTime,String leftright) {
      
   Image image = ori_icon.getImage();
    
    image.getScaledInstance(40,40,Image.SCALE_SMOOTH);
    ori_icon.setImage(image);
    
    if(ImageType.equals("image")) {
       lastMessageLabel.setText("������ ���½��ϴ�.");
    }
    else {
       lastMessageLabel.setText("�̸�Ƽ���� ���½��ϴ�.");
    }
    timeLabel.setText(presentTime);
       int len = textArea.getDocument().getLength();
       textArea.setCaretPosition(len); // place caret at the end (with no selection)
       Image ori_img = ori_icon.getImage();
       Image new_img;
       ImageIcon new_icon;
       int width, height;
       double ratio;
       width = ori_icon.getIconWidth();
       height = ori_icon.getIconHeight();
       // Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 200 �������� ��ҽ�Ų��.
       if (width > 200 || height > 200) {
          if (width > height) { // ���� ����
             ratio = (double) height / width;
             width = 200;
             height = (int) (width * ratio);
          } else { // ���� ����
             ratio = (double) width / height;
             height = 200;
             width = (int) (height * ratio);
          }
          new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
          new_icon = new ImageIcon(new_img);
          textArea.insertIcon(new_icon);
       } else {
          textArea.insertIcon(ori_icon);
          new_img = ori_img;
       }
       len = textArea.getDocument().getLength();
       textArea.setCaretPosition(len);
       if(leftright.equals("R")) {
       textArea.replaceSelection("\n");
       }

       gc2.drawImage(ori_img,  0,  0, panel.getWidth(), panel.getHeight(), panel);

    }
}