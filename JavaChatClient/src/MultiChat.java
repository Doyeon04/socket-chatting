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
   // 그려진 Image를 보관하는 용도, paint() 함수에서 이용한다.
   private Image panelImage = null; 
   private Graphics gc2 = null;
   JLabel invitedUsersLabel;
   String username;
   int multiNum; // 방번호
   String invitedFriendsArr[];
   public ChatFriendList chatFriendList;
   public String bringTime() {
      String presentTime;
      String hourText;
      Calendar cal = Calendar.getInstance();
      int hour = cal.get(Calendar.HOUR_OF_DAY); 
      int minute = cal.get(Calendar.MINUTE);
      if(hour>=12) {
         hourText="오후";
         hour%=12;
      }
      else {
         hourText = "오전";
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
   class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
    {
       @Override
       public void actionPerformed(ActionEvent e) {
          // Send button을 누르거나 메시지 입력하고 Enter key 치면
          if (e.getSource() == btnSend || e.getSource() == txtInput) {
             String msg = null;
             msg = String.format("%s\n", txtInput.getText());
             String presentTime = bringTime();
             ChatMsg obcm = new ChatMsg(username,"210",msg);
             obcm.presentTime=presentTime;
             obcm.multiChatNum=multiNum;
             obcm.senderImg = new ImageIcon("src/profilesPakage/"+username+".jpg");
             chatFriendList.SendObject(obcm);
             //방넘버랑 방리스트
             
             txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
             txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
             if (msg.contains("/exit")) // 종료 처리
                System.exit(0);
          }
       }
    }
   class ImageSendAction implements ActionListener { // 이미지 보내기 
      @Override
      public void actionPerformed(ActionEvent e) {
         // 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
         if (e.getSource() == imgBtn) {
            frame = new Frame("이미지첨부");
            fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
            
             fd.setDirectory(".\\");
            fd.setVisible(true);
           
            if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
               ChatMsg obcm = new ChatMsg(username, "300", "IMG"); // 코드 300과 함께 이미지 보냄 
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
   
   class EmoticonSendAction implements ActionListener { // 이모티콘 보내기 
         @Override
         public void actionPerformed(ActionEvent e) {
            
          
            Emoticon emoticon = new Emoticon(username, multiNum, chatFriendList,multiChat);
            emoticon.setVisible(true);
            
         }
      }

      class ChatOutAction implements ActionListener{
       @Override
         public void actionPerformed(ActionEvent e) {
            
            ChatMsg obcm = new ChatMsg(username,"560",multiNum+"방에서 "+username+"나감");
            obcm.multiChatNum = multiNum;
            chatFriendList.SendObject(obcm);
            
            //나자신한테는 이 채팅방 패널도 안보여야 돼고 
            chatFriendList.deleteThisChat(multiNum);
            multiChat.setVisible(false);
         }
         
      }
      class ClearTextArea implements ActionListener{ // 대화 내용 삭제 
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
       textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
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
       invitedUsersLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
       invitedUsersLabel.setSize(199,30);
       invitedUsersLabel.setLocation(10,11);
       invitedUsersPanel.add(invitedUsersLabel);
       
       txtInput = new JTextField();
       txtInput.setBounds(10, 515, 288, 30);
       contentPane.add(txtInput);
       txtInput.setColumns(10);
     
       Color yellowColor=new Color(254,240,27);  
       
     
       btnSend = new JButton("\uC804\uC1A1");
       btnSend.setFont(new Font("맑은 고딕", Font.PLAIN, 9));
       btnSend.setBounds(310, 515, 54, 30);
       contentPane.add(btnSend);
       
       btnSend.setBackground(yellowColor);

       Myaction action = new Myaction();
      btnSend.addActionListener(action); // 내부클래스로 액션 리스너를 상속받은 클래스로
      txtInput.addActionListener(action);
       this.setVisible(true);
       
       // 변수명 바꿈
     
       
     invitedFriendsString = String.join(",", invitedFriendsArr);
      
       invitedUsersLabel.setText(invitedFriendsString+" ("+invitedFriendsArr.length+")");
       
       JButton changeColorBtn = new JButton("\uBC30\uACBD\uC0C9 \uBCC0\uACBD");
       changeColorBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
       changeColorBtn.setFocusPainted(false);
       changeColorBtn.setContentAreaFilled(false);
       changeColorBtn.setBounds(259, 18, 105, 23);
       invitedUsersPanel.add(changeColorBtn);
       //changeColorBtn.addActionListener(new MenuActionListener());
       MenuActionListener menuActionListener = new MenuActionListener();
       changeColorBtn.addActionListener(menuActionListener);
       
       //이벤트 리스너 -> 서버에 자기 이름이랑 multinum, 프로토콜 -> 서버에서 관리하는 map에서 멤버수정 -> 수정 리스트 다른 유저들에게 보내기.
       ChatOutAction chatOutAction = new ChatOutAction();
       
       ImageIcon addImageIcon = new ImageIcon("src/imageBtn.png");
       Image tempImgIcon = addImageIcon.getImage();
       Image changeAddImageIcon = tempImgIcon.getScaledInstance(40,30,Image.SCALE_SMOOTH);
       ImageIcon newAddImageIcon = new ImageIcon(changeAddImageIcon);
       imgBtn = new JButton(newAddImageIcon);
       imgBtn.setBorderPainted(false);
       imgBtn.setContentAreaFilled(false);
       imgBtn.setFocusPainted(false);
       imgBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
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
      JButton emoticonBtn = new JButton(emoticonBtnIcon); // 이모티콘 버튼
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
        chatOutBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
        
        clearTextAreaBtn = new JButton("\uB300\uD654 \uB0B4\uC6A9 \uC0AD\uC81C");
        clearTextAreaBtn.setBounds(158, 563, 120, 23);
        contentPane.add(clearTextAreaBtn);
        clearTextAreaBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
        clearTextAreaBtn.setFocusPainted(false);
        clearTextAreaBtn.setContentAreaFilled(false);
        clearTextAreaBtn.addActionListener(clearTextArea);
        chatOutBtn.addActionListener(chatOutAction);
      //->수정한 리스트를 나간 사람 외의 유저들에게 전송 (프로토콜 생성) -> 받아서 리스트 수정 하고 
      
      
      
      EmoticonSendAction action3 = new EmoticonSendAction();
      emoticonBtn.addActionListener(action3);
      
  
      
      // Image 영역 보관용. paint() 에서 이용한다.
      panelImage = createImage(panel.getWidth(), panel.getHeight());
      gc2 = panelImage.getGraphics();
      gc2.setColor(panel.getBackground());
      gc2.fillRect(0,0, panel.getWidth(),  panel.getHeight());
      gc2.setColor(Color.BLACK);
      gc2.drawRect(0,0, panel.getWidth()-1,  panel.getHeight()-1);
      
      this.setVisible(false);
      
    
  
   }
   class MenuActionListener implements ActionListener {
	      JColorChooser chooser = new JColorChooser(); //컬러 다이얼로그 생성

	public void actionPerformed(ActionEvent e) {

	    Color selectedColor = chooser.showDialog(null, "Color", Color.YELLOW); // 컬러 다이얼로그를 출력하고 사용자가 선택한 색을 알아옴.

	    textArea.setBackground(selectedColor);
	    invitedUsersPanel.setBackground(selectedColor);
	    
	    if (selectedColor != null) {
	     textArea.setForeground(selectedColor); // 취소 버튼을 누르거나 다이얼로그를 그냥 닫는 경우
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
   public void AppendProfile(String userName,ImageIcon profileImg) {//왼쪽용
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
          // Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
          if (width > 40 || height > 40) {
              if (width > height) { // 가로 사진
                 ratio = (double) height / width;
                 width = 40;
                 height = (int) (width * ratio);
              } else { // 세로 사진
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
       
      
       
          userName = userName.trim(); // 앞뒤 blank와 \n을 제거한다.    
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
   public void AppendProfileR(String userName) {//왼쪽용     
          userName = userName.trim(); // 앞뒤 blank와 \n을 제거한다.    
         StyledDocument doc = textArea.getStyledDocument();
         SimpleAttributeSet right = new SimpleAttributeSet();
         StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
         StyleConstants.setForeground(right, Color.BLUE);   
         StyleConstants.setFontSize(right, 20);
         Font allFont = new Font("맑은 고딕", Font.PLAIN, 20);
         textArea.setFont(allFont);
          doc.setParagraphAttributes(doc.getLength(), 1, right, false);
         try {
            doc.insertString(doc.getLength(),userName+"\n", right );//""문자열로 오른쪽 정렬->이미지+\n->text
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
         
         Font allFont = new Font("맑은 고딕", Font.PLAIN, 10);
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
         
         Font allFont = new Font("맑은 고딕", Font.PLAIN, 10);
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
    
      msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
      int len = textArea.getDocument().getLength();
      // 끝으로 이동
     
      lastMessageLabel.setText(msg);
      timeLabel.setText(presentTime);
      StyledDocument doc = textArea.getStyledDocument();
      SimpleAttributeSet left = new SimpleAttributeSet();
      StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
      
      StyleConstants.setForeground(left, Color.BLACK);
      StyleConstants.setFontSize(left, 16);
      StyleConstants.setBackground(left, Color.WHITE);  
      
      Font allFont = new Font("맑은 고딕", Font.PLAIN, 16);
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
         msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
         int len = textArea.getDocument().getLength();
         // 끝으로 이동

         StyledDocument doc = textArea.getStyledDocument();
         SimpleAttributeSet center = new SimpleAttributeSet();
         StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
         StyleConstants.setForeground(center, Color.BLACK);
         StyleConstants.setFontSize(center, 13);
         
         Font allFont = new Font("맑은 고딕", Font.PLAIN, 13);
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
      // Image 영역이 가려졌다 다시 나타날 때 그려준다.
   
   }
   
   
   public void AppendTextR(String msg, String presentTime) {
      int len = textArea.getDocument().getLength();
       textArea.setCaretPosition(len); // place caret at the end (with no selection)
         msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다. 
         lastMessageLabel.setText(msg);
         timeLabel.setText(presentTime);
   
         StyledDocument doc = textArea.getStyledDocument();
         SimpleAttributeSet right = new SimpleAttributeSet();
         StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
         StyleConstants.setForeground(right, Color.BLACK);   
         StyleConstants.setBackground(right, new Color(254,240,27));   
           
         Font allFont = new Font("맑은 고딕", Font.PLAIN, 16);
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
            //""문자열로 오른쪽 정렬->이미지+\n->text
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
       lastMessageLabel.setText("사진을 보냈습니다.");
    }
    else {
       lastMessageLabel.setText("이모티콘을 보냈습니다.");
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
       // Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
       if (width > 200 || height > 200) {
          if (width > height) { // 가로 사진
             ratio = (double) height / width;
             width = 200;
             height = (int) (width * ratio);
          } else { // 세로 사진
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