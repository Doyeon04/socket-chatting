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

   public ChatFriendList chatFriendList;
   ImageIcon [] emoticonIconArr = new ImageIcon[10];
   JPanel [] emoticonPanelArr = new JPanel[10];
   JLabel [] emoticonLabelArr = new JLabel[10];
   public String username;
   public int multiNum;
   private MultiChat multiChat;
   
   public Emoticon(String username, int multiNum, ChatFriendList chatFriendList,MultiChat multiChat) {
      emoticon = this;
      this.username = username;
      this.multiNum = multiNum;
      this.chatFriendList = chatFriendList;
      this.multiChat = multiChat;
      getContentPane().setBackground(Color.WHITE);
      getContentPane().setLayout(null);
      setSize(464, 350);
      setBounds(100, 100, 420, 350);      
      for(int i=0;i<10;i++) {   
         emoticonIconArr[i]=new ImageIcon("src/emoticon/emoticon"+(i+1)+".png");
          
      }      
      for(int i=0;i<4;i++) {
         emoticonPanelArr[i] = new JPanel();
         emoticonPanelArr[i].setBackground(Color.WHITE);
         emoticonPanelArr[i].setBounds(25+92*i, 32, 80, 66);
         getContentPane().add(emoticonPanelArr[i]);
         
         emoticonLabelArr[i] = new JLabel(emoticonIconArr[i]);
         emoticonLabelArr[i].setBackground(Color.WHITE);
         emoticonPanelArr[i].add(emoticonLabelArr[i]);
      }
      int j=0;
      for(int i=4;i<8;i++) {
         emoticonPanelArr[i] = new JPanel();
         emoticonPanelArr[i].setBackground(Color.WHITE);
         emoticonPanelArr[i].setBounds(25+92*j, 121, 80, 66);
         getContentPane().add(emoticonPanelArr[i]);
         
         emoticonLabelArr[i] = new JLabel(emoticonIconArr[i]);
         emoticonLabelArr[i].setBackground(Color.WHITE);
         emoticonPanelArr[i].add(emoticonLabelArr[i]);
         j++;
      }
      j=0;
      for(int i=8;i<10;i++) {
         emoticonPanelArr[i] = new JPanel();
         emoticonPanelArr[i].setBackground(Color.WHITE);
         emoticonPanelArr[i].setBounds(25+92*j, 209, 80, 66);
         getContentPane().add(emoticonPanelArr[i]);
         
         emoticonLabelArr[i] = new JLabel(emoticonIconArr[i]);
         emoticonLabelArr[i].setBackground(Color.WHITE);
         emoticonPanelArr[i].add(emoticonLabelArr[i]);
         j++;
      }

      SelectEmoticon selectEmoticon = new SelectEmoticon();
      for(int i=0;i<10;i++) {
         emoticonPanelArr[i].addMouseListener(selectEmoticon);
      }

   }
   public class SelectEmoticon  extends MouseAdapter{
      public void mouseClicked(MouseEvent e) { 
      
          ChatMsg obcm = new ChatMsg(username, "300", "IMG");
          ImageIcon img = null;
          obcm.senderImg = new ImageIcon("src/profilesPakage/"+username+".jpg");
          for(int i=0;i<10;i++) {
             if(e.getSource()==emoticonPanelArr[i]) {
                img = new ImageIcon("src/emoticon/emoticon"+(i+1)+".png");
             }
          }
         obcm.img = img;
            obcm.multiChatNum=multiNum;
            obcm.data = "emoticon";
            String presentTime = multiChat.bringTime();
            obcm.presentTime = presentTime;
            chatFriendList.SendObject(obcm);
            
      }
   }
}