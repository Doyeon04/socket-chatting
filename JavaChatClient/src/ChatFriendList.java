
// ChatFriendList.java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
// Java Client ����import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.event.*;

import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import com.sun.tools.javac.Main;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.awt.*;

public class ChatFriendList extends JFrame {
   ArrayList<String> userNameList = new ArrayList<String>();
   Map<String, PicturePanel> userPicturePanel = new HashMap<String, PicturePanel>();
   ArrayList<String> selectedNameList = new ArrayList<String>();
   ArrayList<String> sendSelectedNameList = new ArrayList<String>();
   ArrayList<ListPanel> userOneListPanel = new ArrayList<ListPanel>();
   ArrayList<MultiChat> myMultiChat = new ArrayList<MultiChat>();
   ArrayList<OneChatRoomPanel> oneChatRoomPanelList = new ArrayList<OneChatRoomPanel>();
   private JPanel contentPane;
   private JTextField txtIpAddress;
   private JTextField txtPortNumber;

   public ChatFriendList chatFriendList;
   public MultiChat multiChat;

   /**
    * Launch the application.
    */
   private static final long serialVersionUID = 1L;

   private String UserName;
   private JButton btnSend;
   private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
   private Socket socket; // �������
   private InputStream is;
   private OutputStream os;
   private DataInputStream dis;
   private DataOutputStream dos;

   private ObjectInputStream ois;
   private ObjectOutputStream oos;
   JPanel contentPane_1;
   private JLabel lblUserName;
   private JTextPane textArea;

   private Frame frame;
   private FileDialog fd;
   private JButton imgBtn;
   JPanel chatPanel;
   JPanel panel;
   private JLabel lblMouseEvent;
   private Graphics gc;
   private int pen_size = 2; // minimum 2
   // �׷��� Image�� �����ϴ� �뵵, paint() �Լ����� �̿��Ѵ�.
   private Image panelImage = null;
   private Graphics gc2 = null;

   private JLabel userListLabel;

   private JPanel userListPanel;

   private JButton testButton;
   private int i = 0;
   private int myRoomNum = 0;

   private ImageIcon icon; // ������ �̹���

   public JPanel userImgPanel; // �� ������ �̹��� �г�
   public JLabel userImgLabel; // �� ������ �̹��� ��

 

   /**
    * Create the frame.
    */
   public ChatFriendList(String username, String ip_addr, String port_no) {
      chatFriendList = this;

      this.UserName = username;
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(400, 600);
      setBounds(100, 100, 386, 512);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);

      contentPane_1 = new JPanel();
      contentPane_1.setBackground(Color.WHITE);
      contentPane_1.setLayout(null);
      contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPane_1.setBounds(61, 0, 311, 485);
      contentPane.add(contentPane_1);

      JLabel FriendLabel = new JLabel("\uCE5C\uAD6C");
      FriendLabel.setFont(new Font("���� ���", Font.BOLD, 18));
      FriendLabel.setBounds(23, 23, 76, 34);
      contentPane_1.add(FriendLabel);

      JLabel userNameLabel = new JLabel(username);
      userNameLabel.setFont(new Font("���� ���", Font.BOLD, 14));
      userNameLabel.setBounds(77, 76, 68, 15);
      contentPane_1.add(userNameLabel);

      userImgPanel = new JPanel(); // �� ������ ���� �г�
      userImgPanel.setBackground(Color.WHITE);
      userImgPanel.setBounds(23, 66, 42, 42);
      contentPane_1.add(userImgPanel);

      icon = new ImageIcon("src/basicProfileImg.jpg");

      Image img = icon.getImage();
      Image changeImg = img.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
      ImageIcon changeIcon = new ImageIcon(changeImg);
      userImgLabel = new JLabel(changeIcon);

      userImgPanel.add(userImgLabel);


      ImageIcon friendIcon = new ImageIcon("src/basicProfileImg.jpg");
      JLabel friendIconlabel = new JLabel(friendIcon);


      JLabel friendNameLabel = new JLabel("");
      friendNameLabel.setFont(new Font("���� ���", Font.BOLD, 14));
      friendNameLabel.setBounds(77, 134, 68, 15);
      contentPane_1.add(friendNameLabel);

      class FriendAction extends MouseAdapter // ����Ŭ������ �׼� �̺�Ʈ ó�� Ŭ����
      {

         public void mouseClicked(MouseEvent e) {
            contentPane_1.setVisible(true);
            chatPanel.setVisible(false);

         }
      }

      FriendAction friendAction = new FriendAction();
      JPanel friendIconPanel = new JPanel();
      friendIconPanel.setBounds(12, 47, 40, 40);
      contentPane.add(friendIconPanel);
      friendIconPanel.addMouseListener(friendAction);
      ImageIcon menuIcon1 = new ImageIcon("src/menuIcon1.png");
      JLabel MenuIconlabel = new JLabel(menuIcon1);
      friendIconPanel.add(MenuIconlabel);

      JPanel chatIconPanel = new JPanel();
      chatIconPanel.setBounds(12, 119, 40, 40);
      contentPane.add(chatIconPanel);

      ImageIcon menuIcon2 = new ImageIcon("src/menuIcon2.png");

      chatPanel = new JPanel();// ä��â ��� ����� setvisible(false);
      chatPanel.setBounds(61, 0, 311, 485);
      chatPanel.setVisible(false);
      chatPanel.setBackground(Color.WHITE);
      chatPanel.setLayout(null);
      contentPane.add(chatPanel);

      JLabel chatLabel = new JLabel("\uCC44\uD305");
      chatLabel.setFont(new Font("���� ���", Font.BOLD, 18));
      chatLabel.setBounds(23, 23, 76, 34);
      chatPanel.add(chatLabel);

      class ChatAction extends MouseAdapter // ����Ŭ������ �׼� �̺�Ʈ ó�� Ŭ����
      {

         public void mouseClicked(MouseEvent e) { // chat ������ ������ ä��â ��� ȭ������ ����
            contentPane_1.setVisible(false);
            chatPanel.setVisible(true); // ä��â ��� ȭ�� �������� ��

         }

      }
      ChatAction chatIconeve = new ChatAction();
      JLabel chatIconLabel = new JLabel(menuIcon2);
      chatIconPanel.add(chatIconLabel);
      chatIconPanel.addMouseListener(chatIconeve);// �� ������ ������ ä��â �ߵ���


      try {
         socket = new Socket(ip_addr, Integer.parseInt(port_no));

         oos = new ObjectOutputStream(socket.getOutputStream());
         oos.flush();
         ois = new ObjectInputStream(socket.getInputStream());

         ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");// connect
         // ���� ������ ������ ������ �Դϴ�.
         SendObject(obcm);// send
         EnterUserNetwork net = new EnterUserNetwork();
         net.start();

      } catch (NumberFormatException | IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      
      }


      JLabel label_1 = new JLabel((Icon) null);

      userListPanel = new JPanel();
      userListPanel.setBackground(Color.WHITE);
      userListPanel.setPreferredSize(new Dimension(287, 1000));
      userListPanel.setLayout(null);

      JScrollPane scrollPane = new JScrollPane(userListPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 10));
      scrollPane.setBounds(12, 183, 287, 273);
      //scrollPane.setViewportBorder(BorderFactory.createLineBorder(Color.gray));
      //scrollPane.setViewportBorder(null);
    
      scrollPane.setViewportView(userListPanel);
      contentPane_1.add(scrollPane);

      class SendNameList extends MouseAdapter {
         // private static final String ListPanel = null;

         public void mouseClicked(MouseEvent e) { // ����� �ʴ��ϱ�
            ChatMsg chatMsg = null;
            chatMsg = new ChatMsg(UserName, "500", "send selected string list");
            chatMsg.i = i;
            selectedNameList.add(UserName); // ���α��� �߰���
            if (selectedNameList.size() < 2) {
               JOptionPane.showMessageDialog(null,"�ʴ��� ������ �����ϼ���.","Notice",JOptionPane.INFORMATION_MESSAGE);
            } else {

             
               sendSelectedNameList = selectedNameList;
               chatMsg.setList(sendSelectedNameList);

               i++; // ���̵� ����
               SendObject(chatMsg); 
               String selectedNameListStr = String.join(",", selectedNameList);

               sendSelectedNameList.clear();

               for (int j = 0; j < userOneListPanel.size(); j++) {
                  userOneListPanel.get(j).getCheckBox().setSelected(false);

               }
               chatMsg = null;
               selectedNameList.clear();

            }
         }

      }

      ImageIcon inviteImg = new ImageIcon("src/newRoomImg.png");
      testButton = new JButton(inviteImg);
      testButton.setBorderPainted(false);
      testButton.setContentAreaFilled(false);
      testButton.setFocusPainted(false);
      testButton.setBounds(253, 150, 30, 30);
      contentPane_1.add(testButton);
      SendNameList sendName = new SendNameList();
      testButton.addMouseListener(sendName);

    

      JLabel onlineFriendLabel = new JLabel("\uC811\uC18D\uC790");
      onlineFriendLabel.setFont(new Font("���� ���", Font.BOLD, 14));
      onlineFriendLabel.setBounds(23, 134, 76, 34);
      contentPane_1.add(onlineFriendLabel);

      JButton changeProfileBtn = new JButton("\uD504\uB85C\uD544 \uBCC0\uACBD");
      changeProfileBtn.setFont(new Font("���� ���", Font.PLAIN, 11));
      changeProfileBtn.setContentAreaFilled(false);
      changeProfileBtn.setFocusPainted(false);
      changeProfileBtn.setBounds(197, 85, 102, 23);
      contentPane_1.add(changeProfileBtn);


      class SendMyName extends MouseAdapter { // ������ ä�ù� ����� 
         public void mouseClicked(MouseEvent e) { 
            ChatMsg chatMsg = null;
            chatMsg = new ChatMsg(UserName, "500", "send selected string list");
            chatMsg.i = i;
            selectedNameList.clear();
            selectedNameList.add(UserName); 
            sendSelectedNameList = selectedNameList;
            chatMsg.setList(sendSelectedNameList);

            i++; // ���̵� ����

            SendObject(chatMsg); 

            String selectedNameListStr = String.join(",", selectedNameList);

            sendSelectedNameList.clear();

            chatMsg = null;
            selectedNameList.clear();
            // (ListPanel)userListPanel.getCompontent(0);
         }

      }
      JButton chatWithMeBtn = new JButton("\uB098\uC640\uC758 \uCC44\uD305");
      chatWithMeBtn.setFont(new Font("���� ���", Font.PLAIN, 11));
      chatWithMeBtn.setFocusPainted(false);
      chatWithMeBtn.setContentAreaFilled(false);
      chatWithMeBtn.setBounds(197, 64, 102, 23);
      contentPane_1.add(chatWithMeBtn);

      
      SendMyName sendMyName = new SendMyName();
      chatWithMeBtn.addMouseListener(sendMyName);

  

      class RefreshProfile extends MouseAdapter { // ������ ���縦 ������

         public void mouseClicked(MouseEvent e) { // ����� �ʴ��ϱ�
            refreshPanel();
         }

      }

      RefreshProfile refreshProfile = new RefreshProfile();

      class SelectProfile extends MouseAdapter { // ������ ���縦 ������

         public void mouseClicked(MouseEvent e) { // ����� �ʴ��ϱ�
            

            repaint();

            if (e.getSource() == changeProfileBtn) {
               frame = new Frame("�̹���÷��");
               fd = new FileDialog(frame, "�̹��� ����", FileDialog.LOAD);
               fd.setVisible(true);
          
               if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
                  ChatMsg obcm = new ChatMsg(username, "700", "IMG"); // �ڵ� 700�� �Բ� ������ ������ �̹��� ����
                  ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
                
                  obcm.img = img;

  
                  refreshMyProfile(fd.getDirectory() + fd.getFile()); // �ڱ� ������ ���� ������Ʈ
                  SendObject(obcm);

               }
            }
         }

      }

      SelectProfile selectProfile = new SelectProfile();
      changeProfileBtn.addMouseListener(selectProfile);


   }

   public void setMyImg(String img) {

      userImgPanel.revalidate();
      userImgPanel.repaint();
      userImgLabel.revalidate();
      userImgLabel.repaint();

      userImgPanel.remove(userImgLabel);
      revalidate();
      repaint();

      ImageIcon icon1 = new ImageIcon(img);
      Image img2 = icon1.getImage();
      Image changeImg1 = img2.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
      ImageIcon changeIcon1 = new ImageIcon(changeImg1);

      userImgLabel.setIcon(changeIcon1);
      userImgPanel.add(userImgLabel);



   }

   public void refreshMyProfile(String img) { // �� ������ ���� ������Ʈ

      setMyImg(img);

   }

   public void refreshPanel() { 
      for (int i = 0; i < userNameList.size(); i++) {

         String eachUserName = userNameList.get(i);// arraylist get(0),get(1) => 0��°�� ����� username
        
         if (eachUserName.equals(UserName))
            continue;
         userPicturePanel.get(userNameList.get(i)).setImg(eachUserName);// map key:string(username)
         // value:picturepanel

      } // ���ڽ��� ������ ������ ����
      this.revalidate();
      this.repaint();

   }

   public class CheckAction implements ItemListener {
      String userName;

      public CheckAction(String name) {
         this.userName = name;
      }

      @Override
      public void itemStateChanged(ItemEvent e) {
         // TODO Auto-generated method stub
         switch (e.getStateChange()) {
         case 1:
            // ������ ������ ���õ� ����� �̸� ������
            selectedNameList.add(userName);
            break;
         case 2:
            selectedNameList.remove(userName);

            break;
         }
      }

   }
  

   public class PicturePanel extends JPanel { // ������ ���� ���̱�
      JLabel picturelabel;

      public PicturePanel(String name) {

         ImageIcon icon = new ImageIcon("src/profilesPakage/" + name + ".jpg");
         Image img = icon.getImage();
         Image changeImg = img.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
         ImageIcon changeIcon = new ImageIcon(changeImg);
         picturelabel = new JLabel(changeIcon);
         this.add(picturelabel); // �ĳڿ� label�� ����
         userPicturePanel.put(name, this); // �̸��� �ĳ��� ����

       
         this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
             
    
            	 JFrame frame = new JFrame(name+"�� ������ ����");
            	  BufferedImage img1= null;
            	  
            	  try {
            		  img1 = ImageIO.read(new File("src/profilesPakage/"+name+".jpg"));
            	  }catch(IOException e1) {
            		  
            	  }
                  ImageIcon icon = new ImageIcon(img1);
                  Image im2 = icon.getImage(); 
                  ImageIcon icon2 = new ImageIcon(im2);                 
                  /*
                   * JLabel img = new JLabel();
                   * 
                   * img.setIcon(icon2);
                   */               
                  // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  int width, height;
                  double ratio;
                  width = icon.getIconWidth();
                  height = icon.getIconHeight();
                  // Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 200 �������� ��ҽ�Ų��.
                  if (width > 300 || height > 300) {
                     if (width > height) { // ���� ����
                        ratio = (double) height / width;
                        width = 300;
                        height = (int) (width * ratio);
                     } else { // ���� ����
                        ratio = (double) width / height;
                        height = 300;
                        width = (int) (height * ratio);
                     }
                  }
                  else {
                     width+=60;
                     height+=60;
                  }
                   Image  new_img = im2.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                   ImageIcon new_icon = new ImageIcon(new_img);
                   JLabel img = new JLabel(new_icon);
                   frame.getContentPane().add(img);
                  frame.setSize(width+100, height+100); // ������
                  frame.setLocation(300, 200); // ��ġ ����
                  frame.setVisible(true); // ȭ�鿡 ���̱� 
             
             
            }
         });
         

      }

      public void setImg(String eachUserName) {

         this.remove(picturelabel);
         this.revalidate();
         ImageIcon icon1 = new ImageIcon("src/profilesPakage/" + eachUserName + ".jpg");
       
         Image img2 = icon1.getImage();
         Image changeImg1 = img2.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
         ImageIcon changeIcon1 = new ImageIcon(changeImg1);
         picturelabel.setIcon(changeIcon1);
         this.add(picturelabel);

      }

   }

   class ChatPanelEvent extends MouseAdapter {
      int multiChatNum;

      public ChatPanelEvent(int multiChatNum) {
         this.multiChatNum = multiChatNum;
      }

      @Override
      public void mouseClicked(MouseEvent e) {
         for (int i = 0; i < myMultiChat.size(); i++) {
            if (myMultiChat.get(i).multiNum == multiChatNum) {
               myMultiChat.get(i).setVisible(true);
             
            }
         }
      }
   }

   public class OneChatRoomPanel extends JPanel {
      int multiChatNum;
      MultiChat multiChat;
      public JLabel oneChatRoomLabel;

      public OneChatRoomPanel(int multiChatNum, MultiChat multiChat) {
         this.multiChatNum = multiChatNum;
         ChatPanelEvent chatPanelEvent = new ChatPanelEvent(multiChatNum);
         this.addMouseListener(chatPanelEvent);
      }

      int getMultiChatNum() {
         return multiChatNum;
      }

      public void setChatRoomLabel(JLabel oneChatRoomLabel) {
         this.oneChatRoomLabel = oneChatRoomLabel;
         this.add(oneChatRoomLabel);
      }

   }

   public class ListPanel extends JPanel {
      JCheckBox checkBox;
      PicturePanel picturePanel;

      public ListPanel(JLabel userNameLabel, int y, String name) { // ������ ����, �̸��� �ִ� �� ��
         this.setLayout(new GridLayout(1, 3));
         picturePanel = new PicturePanel(name);
         picturePanel.revalidate();
         picturePanel.repaint();
         picturePanel.setBackground(Color.WHITE);
         this.setBackground(Color.WHITE);
       
         this.add(picturePanel);

         this.add(userNameLabel);
         this.setSize(280, 60);
         this.setLocation(0, y);

         this.checkBox = new JCheckBox();
         this.checkBox.setBackground(Color.WHITE);
        
         checkBox.setVisible(true);
         CheckAction checkAction = new CheckAction(name);
         checkBox.addItemListener(checkAction);
         this.add(checkBox);
      }

      public JCheckBox getCheckBox() {
         return this.checkBox;
         
      }
   }

   public void saveProfileInClient(String img, String userName) {

      File oldFile = new File(img);

      File newFile = new File("./src/profilesPakage/" + userName + ".jpg");

      try {
         FileInputStream input = new FileInputStream(oldFile);
         FileOutputStream output = new FileOutputStream(newFile);

         byte[] buf = new byte[2048];

         int read;

         while ((read = input.read(buf)) > 0) {
            output.write(buf, 0, read);
         }

         input.close();
         output.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   class EnterUserNetwork extends Thread {

      public void run() {
         while (true) {
            try {

               Object obcm = null;
               String msg = null;
               ChatMsg cm;
               try {
                  obcm = ois.readObject();
               } catch (ClassNotFoundException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                  break;
               }
               if (obcm == null)
                  break;
               if (obcm instanceof ChatMsg) {
                  cm = (ChatMsg) obcm;
                  msg = String.format("[%s]\n%s", cm.UserName, cm.data);
               } else
                  continue;
               switch (cm.code) {
              

               case "101": // ���� ����Ʈ �޾Ҵٸ�
                
                  userNameList = cm.al;

                
                  String[] userListString = cm.data.split(",");

                  ListPanel userOnePanel;
                  JLabel userOneLabel;
                  userListPanel.removeAll();
                  userOneListPanel.clear();

                  int j = 0;
                  for (int i = 0; i < userListString.length; i++) {
                    
                     if (!UserName.equals(userListString[i])) {
                        userOneLabel = new JLabel();

                        userOneLabel.setText(userListString[i]);
                        userOneLabel.setBackground(Color.WHITE);
                        userOneLabel.setFont(new Font("���� ���", Font.BOLD, 13));
                        userOnePanel = new ListPanel(userOneLabel, j * 60, userListString[i]);
                        userOnePanel.setBackground(Color.WHITE);
                        j++;
                        userListPanel.add(userOnePanel);// ��ġ for�� ���鼭 ������ �������� ��.
                        userOneListPanel.add(userOnePanel);

                     }

                  }
                  userListPanel.revalidate();
                  userListPanel.repaint();

                  break;

               case "550": 

                  String[] invitedUsersArr = cm.data.split(",");

                  JLabel lastMessageLabel = new JLabel();
                  JLabel timeLabel = new JLabel();

                  OneChatRoomPanel oneChatRoomPanel = new OneChatRoomPanel(cm.multiChatNum, multiChat);
              
        
                  for (int i = 0; i < invitedUsersArr.length; i++) { // �ʴ�� �������� ���鼭
                     if (UserName.equals(invitedUsersArr[i])) { // �ڱ� �ڽ��� �� �� ���ԵǾ� ������ ������� �߰��Ѵ�.
                      
                        multiChat = new MultiChat(UserName, cm.multiChatNum, invitedUsersArr, chatFriendList,
                              lastMessageLabel, timeLabel);

                        myMultiChat.add(multiChat);
                        // ���� �ϳ��� �ĳ�
                        oneChatRoomPanel.setLayout(null);
                        oneChatRoomPanel.setSize(310, 60);
                   
                        MatteBorder b6 = new MatteBorder(0, 0, 1, 0, new Color(234, 234, 234));                                        
                        oneChatRoomPanel.setBorder(b6);

                        String invitedUsersStr = String.join(", ", invitedUsersArr);
                        JLabel oneChatRoomLabel = new JLabel(invitedUsersStr); // ���� �ϳ��� ����Ʈ
                        oneChatRoomLabel.setFont(new Font("���� ���", Font.BOLD, 14));
                        oneChatRoomLabel.setSize(200, 35);
                        oneChatRoomLabel.setLocation(30, 1);
                        oneChatRoomPanel.setChatRoomLabel(oneChatRoomLabel);

                        lastMessageLabel.setFont(new Font("���� ���", Font.BOLD, 10));
                        lastMessageLabel.setSize(200, 30);
                        lastMessageLabel.setLocation(30, 25);

                        oneChatRoomPanel.add(lastMessageLabel);

                        timeLabel.setFont(new Font("���� ���", Font.BOLD, 8));
                        timeLabel.setSize(100, 30);
                        timeLabel.setLocation(220, 5);
                        oneChatRoomPanel.add(timeLabel);
                        oneChatRoomPanel.setBackground(Color.WHITE);

                        oneChatRoomPanelList.add(oneChatRoomPanel);
                        break;
                     }

                  }

                  paintChatRoomPanel();
              
                  // number�� list ����
                  // oneChatPanel���ٰ� addevent�ؼ� ������ multichat frame�� �����ǵ���

                  break;
               case "560":

                  for (int i = 0; i < myMultiChat.size() && myMultiChat.size() > 0; i++) {
                     if (oneChatRoomPanelList.size() > 0
                           && cm.multiChatNum == oneChatRoomPanelList.get(i).multiChatNum) {
  
                        String chatMems = String.join(",", cm.al);
                        oneChatRoomPanelList.get(i).oneChatRoomLabel.setText(chatMems);
                     }
                  }

                  for (int i = 0; i < myMultiChat.size(); i++) {// ������ ������ �ִ� ��Ƽê���߿� � ê�濡�� �޽����� �°��� Ȯ��
                     MultiChat findMultiRoom = myMultiChat.get(i);
                     if (findMultiRoom.getMultiNum() == cm.multiChatNum) {
                        findMultiRoom.AppendNotice(cm.UserName + "���� �������ϴ�.");

                        findMultiRoom.refreshMultiChatUsers(cm.al);

                     }
                  }

                  break;
               case "210": // ����濡�� ���� �޽����� ������ ���� ����.
                
                  // cm.multiChatNum�� �����ؼ� multiChat�� ����
                  for (int i = 0; i < myMultiChat.size(); i++) {// ������ ������ �ִ� ��Ƽê���߿� � ê�濡�� �޽����� �°��� Ȯ��
                     MultiChat findMultiRoom = myMultiChat.get(i);
                     if (findMultiRoom.getMultiNum() == cm.multiChatNum) {
                        if (cm.UserName.equals(UserName)) {
                           findMultiRoom.AppendTextR(cm.data, cm.presentTime); // �� �޼����� ������

                        } else {
                           findMultiRoom.AppendProfile(cm.UserName, cm.senderImg);
                           findMultiRoom.AppendText(cm.data, cm.presentTime);
                           findMultiRoom.AppendTime(cm.presentTime);
                        }

                     }
                  }

                  break;
               case "300": // Image ÷��
             
                  for (int i = 0; i < myMultiChat.size(); i++) {// ������ ������ �ִ� ��Ƽê���߿� � ê�濡�� �޽����� �°��� Ȯ��
                     MultiChat findMultiRoom = myMultiChat.get(i);
                     if (findMultiRoom.getMultiNum() == cm.multiChatNum) {
                        if (cm.UserName.equals(UserName)) {
                           findMultiRoom.AppendProfileR("");                        
                           findMultiRoom.AppendTimeR(cm.presentTime);
                           findMultiRoom.AppendImage(cm.img, cm.data, cm.presentTime,"R");
                        } else {

                           findMultiRoom.AppendProfile(cm.UserName, cm.senderImg);
                           findMultiRoom.AppendImage(cm.img, cm.data, cm.presentTime,"L");
                           findMultiRoom.AppendTime(cm.presentTime);
                        }
                     }
                  }

                  break;

              

               case "750":

                  saveProfileInClient(cm.img.toString(), cm.UserName);
                  refreshPanel();
                  break;
               
                   
               }
               
            } catch (IOException e) {
               AppendText("ois.readObject() error");
               try {

                  ois.close();
                  oos.close();
                  socket.close();

                  break;
               } catch (Exception ee) {
                  break;
               } // catch�� ��
            } // �ٱ� catch����

         }
      }
   }

   public void deleteThisChat(int multiNum) {

      if (oneChatRoomPanelList.size() > 0) {
         for (int i = 0; i < oneChatRoomPanelList.size(); i++) {
  
            if (oneChatRoomPanelList.get(i).multiChatNum == multiNum) {
               myRoomNum--;
               oneChatRoomPanelList.remove(i);

            }
            if (myMultiChat.get(i).multiNum == multiNum) {
               myMultiChat.remove(i);
            }

            // �ڽ��� ������ ä�ù��� ��ȣ�� �޾Ƽ� 1.list���� ���� �������� ���̰�. �׸��� ���������� 2.myMultiChat���� ����.
         }
      }
      paintChatRoomPanel();
   }

   public void paintChatRoomPanel() {
      chatPanel.removeAll();

      chatPanel.revalidate();
      chatPanel.repaint();
      JLabel chatLabel = new JLabel("\uCC44\uD305");
      chatLabel.setFont(new Font("���� ���", Font.BOLD, 18));
      chatLabel.setBounds(23, 23, 76, 34);
      chatPanel.add(chatLabel);
      if (oneChatRoomPanelList.size() > 0) {
         for (int i = 0; i < oneChatRoomPanelList.size(); i++) {

            oneChatRoomPanelList.get(i).setLocation(0, (i + 1) * 60);
            chatPanel.add(oneChatRoomPanelList.get(i));
          
         }
      }
   }

   public void AppendText(String msg) {

      msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.
      int len = textArea.getDocument().getLength();
      // ������ �̵�


      StyledDocument doc = textArea.getStyledDocument();
      SimpleAttributeSet left = new SimpleAttributeSet();
      StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
      StyleConstants.setForeground(left, Color.BLACK);
      doc.setParagraphAttributes(doc.getLength(), 1, left, false);
      try {
         doc.insertString(doc.getLength(), msg + "\n", left);
      } catch (BadLocationException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }

   public void SendObject(Object ob) { // ������ �޼����� ������ �޼ҵ�
      try {

         oos.writeObject(ob);
         oos.reset();
      } catch (IOException e) {
         // textArea.append("�޼��� �۽� ����!!\n");
         AppendText("SendObject Error");
      }
   }
}
