//JavaObjServer.java 
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class JavaGameServer extends JFrame {
   ArrayList<String> userNameList = new ArrayList<String>();///�߰�

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   JTextArea textArea;
   private JTextField txtPortNumber;
   int multiChatNum =0; // ����� ��ȣ
   private ServerSocket socket; // ��������
   private Socket client_socket; // accept() ���� ������ client ����
   private Vector UserVec = new Vector(); // ����� ����ڸ� ������ ����
   private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
   Map<Integer,ArrayList<String>> multiChatNumNmems= new HashMap<>();
   Map<String,ObjectOutputStream> clientsOutputStream = new HashMap<String,ObjectOutputStream>();
   
   String invitedUsersStr; // �ϳ��� ����濡 �ʴ�� ����� ����Ʈ

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               JavaGameServer frame = new JavaGameServer();
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
   public JavaGameServer() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 338, 440);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);

      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setBounds(12, 10, 300, 298);
      contentPane.add(scrollPane);

      textArea = new JTextArea();
      textArea.setEditable(false);
      scrollPane.setViewportView(textArea);

      JLabel lblNewLabel = new JLabel("Port Number");
      lblNewLabel.setBounds(13, 318, 87, 26);
      contentPane.add(lblNewLabel);

      txtPortNumber = new JTextField();
      txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
      txtPortNumber.setText("30000");
      txtPortNumber.setBounds(112, 318, 199, 26);
      contentPane.add(txtPortNumber);
      txtPortNumber.setColumns(10);

      JButton btnServerStart = new JButton("Server Start");
      btnServerStart.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               socket = new ServerSocket(Integer.parseInt(txtPortNumber.getText()));
            } catch (NumberFormatException | IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            AppendText("Chat Server Running..");
            btnServerStart.setText("Chat Server Running..");
            btnServerStart.setEnabled(false); // ������ ���̻� �����Ű�� �� �ϰ� ���´�
            txtPortNumber.setEnabled(false); // ���̻� ��Ʈ��ȣ ������ �ϰ� ���´�
            AcceptServer accept_server = new AcceptServer();
            accept_server.start();
         }
      });
      btnServerStart.setBounds(12, 356, 300, 35);
      contentPane.add(btnServerStart);
   }

   // ���ο� ������ accept() �ϰ� user thread�� ���� �����Ѵ�.
   class AcceptServer extends Thread {
      @SuppressWarnings("unchecked")
      public void run() {
         while (true) { // ����� ������ ����ؼ� �ޱ� ���� while��
            try {
               AppendText("Waiting new clients ...");
               client_socket = socket.accept(); // accept�� �Ͼ�� �������� ���� �����
               AppendText("���ο� ������ from " + client_socket);
               // User �� �ϳ��� Thread ����
               UserService new_user = new UserService(client_socket);
               UserVec.add(new_user); // ���ο� ������ �迭�� �߰�
               new_user.start(); // ���� ��ü�� ������ ����
               AppendText("���� ������ �� " + UserVec.size());
            } catch (IOException e) {
               AppendText("accept() error");
               // System.exit(0);
            }
         }
      }
   }

   public void AppendText(String str) {
     
      textArea.append(str + "\n");
      textArea.setCaretPosition(textArea.getText().length());
   }

   public void AppendObject(ChatMsg msg) {
     
      textArea.append("code = " + msg.code + "\n");
      textArea.append("id = " + msg.UserName + "\n");
      textArea.append("data = " + msg.data + "\n");
      textArea.append("list = " + msg.al + "\n");
      
      textArea.setCaretPosition(textArea.getText().length());
   }

   // User �� �����Ǵ� Thread
   // Read One ���� ��� -> Write All
   class UserService extends Thread {
      private InputStream is;
      private OutputStream os;
      private DataInputStream dis;
      private DataOutputStream dos;
      ArrayList<ChatMsg> chatList = new ArrayList<ChatMsg>();
      private ObjectInputStream ois;
      private ObjectOutputStream oos;

      private Socket client_socket;
      private Vector user_vc;
      public String UserName = "";
   

      public UserService(Socket client_socket) {
         // TODO Auto-generated constructor stub
         // �Ű������� �Ѿ�� �ڷ� ����
         this.client_socket = client_socket;
         this.user_vc = UserVec;
         try {

            oos = new ObjectOutputStream(client_socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(client_socket.getInputStream());
            //����ٰ� dataoutputstream�� username�� ����.
            clientsOutputStream.put(UserName,oos);

         } catch (Exception e) {
            AppendText("userService error");
         }
      }

      public void Login(String code) {
         AppendText("���ο� ������ " + UserName + " ����.");
         WriteOne("Welcome to Java chat server\n");
         WriteOne(UserName + "�� ȯ���մϴ�.\n"); // ����� ����ڿ��� ���������� �˸�
         String msg = "[" + UserName + "]���� ���� �Ͽ����ϴ�.\n";
         WriteOthers(code); // ���� user_vc�� ���� ������ user�� ���Ե��� �ʾҴ�.
         userNameList.add(UserName);///�߰�
      }

      public void Logout() {
        
         UserVec.removeElement(this); // Logout�� ���� ��ü�� ���Ϳ��� �����
        
         AppendText("����� " + "[" + UserName + "] ����. ���� ������ �� " + UserVec.size());
         //list����
         //list���� ��� 
       userNameList.remove(userNameList.indexOf(UserName));///�߰�
     
         
         //�������Ϥӱ� �� ���� ������ ������ Ŭ���̾�Ʈ����
         ChatMsg logoutMsg = new ChatMsg("SERVER","101","someone logout");
         String userNameListStr = String.join(",", userNameList);
         logoutMsg.data = userNameListStr;
         WriteAllObject(logoutMsg);///�߰�
      }

      // ��� User�鿡�� ���. ������ UserService Thread�� WriteONe() �� ȣ���Ѵ�.
      public void WriteAll(String str) {
         for (int i = 0; i < user_vc.size(); i++) {
            UserService user = (UserService) user_vc.elementAt(i);
       
               user.WriteOne(str);
         }
         if(str.equals("550")) { // ����� ���� �ڵ��� �� ��ȣ 1�� ����l
           multiChatNum++;
           
         }
            
      }
      // ��� User�鿡�� Object�� ���. ä�� message�� image object�� ���� �� �ִ�
      public void WriteAllObject(Object ob) {
         for (int i = 0; i < user_vc.size(); i++) {
            UserService user = (UserService) user_vc.elementAt(i);
          
               user.WriteOneObject(ob);
         }
      }

      // ���� ������ User�鿡�� ���. ������ UserService Thread�� WriteONe() �� ȣ���Ѵ�.
      public void WriteOthers(String code) {
         for (int i = 0; i < user_vc.size(); i++) {
            UserService user = (UserService) user_vc.elementAt(i);
            if (user != this)
               user.WriteOne(code);
         }
      }

      // Windows ó�� message ������ ������ �κ��� NULL �� ����� ���� �Լ�
      public byte[] MakePacket(String msg) {
         byte[] packet = new byte[BUF_LEN];
         byte[] bb = null;
         int i;
         for (i = 0; i < BUF_LEN; i++)
            packet[i] = 0;
         try {
            bb = msg.getBytes("euc-kr");
         } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         for (i = 0; i < bb.length; i++)
            packet[i] = bb[i];
         return packet;
      }

      // UserService Thread�� ����ϴ� Client ���� 1:1 ����
      public void WriteOne(String msg) {
         try {

            if(msg.matches("100")) { // ä�� x ���� o 
               ChatMsg obcm = new ChatMsg("SERVER", "100", msg);
               oos.writeObject(obcm);
            }
            if(msg.matches("101")) { // ���� ����Ʈ �������� �̶��
               String userNameListStr = String.join(",", userNameList);                          
               ChatMsg obcm = new ChatMsg("SERVER", "101", userNameListStr);
               obcm.al = userNameList;
               oos.writeObject(obcm);
               oos.reset();                         
            }
            
            if(msg.matches("550")) { // �ϴ� ��ο��� ����濡 �ʴ�� ����� ����Ʈ ������
                ChatMsg obcm = new ChatMsg("SERVER", "550", invitedUsersStr);
                obcm.multiChatNum=multiChatNum; //ChatMsg�� multiChatNum ����                
                 oos.writeObject(obcm); // Ŭ���̾�Ʈ���� ����
            }
            
            if(msg.matches("700")) { // ������ �̹��� ������Ʈ �� �ҽ��� �˸��� 
                ChatMsg obcm = new ChatMsg("SERVER", "700", "������ �̹��� ������Ʈ");                        
                 oos.writeObject(obcm); // Ŭ���̾�Ʈ���� ����
            }
                      
            else {
               ChatMsg obcm = new ChatMsg("SERVER", "200", msg);
            }
            
         } catch (IOException e) {
            AppendText("dos.writeObject() error");
            try {

               ois.close();
               oos.close();
               client_socket.close();
               client_socket = null;
               ois = null;
               oos = null;
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            Logout(); // �������� ���� ��ü�� ���Ϳ��� �����
         }
      }

      // �ӼӸ� ����
      public void WritePrivate(String msg) {
         try {
            ChatMsg obcm = new ChatMsg("�ӼӸ�", "200", msg);
            oos.writeObject(obcm);
         } catch (IOException e) {
            AppendText("dos.writeObject() error");
            try {
               oos.close();
               client_socket.close();
               client_socket = null;
               ois = null;
               oos = null;
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            Logout(); // �������� ���� ��ü�� ���Ϳ��� �����
         }
      }
      public void WriteOneObject(Object ob) {
         try {
             oos.writeObject(ob);
            //oos.writeObject(userNameListStr);
             oos.reset();
         } 
         catch (IOException e) {
            AppendText("oos.writeObject(ob) error");      
            try {
               ois.close();
               oos.close();
               client_socket.close();
               client_socket = null;
               ois = null;
               oos = null;            
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            Logout();
         }
      }
      
      public void WriteOthersObject(Object ob) {
         for(int i=0; i<user_vc.size(); i++) {
            UserService user = (UserService) user_vc.elementAt(i);
            if(user!=this) { // ���� ������ �ٸ� �����鿡�� 
               user.WriteOneObject(ob);
            }
         }
      }
      public void sendMessage(ChatMsg cm,ArrayList<String> chatMem) {        
         for(int i=0;i<chatMem.size();i++) {
            String chatMemName = chatMem.get(i);        
            for(int j=0;j<user_vc.size();j++) {
               UserService user = (UserService) user_vc.elementAt(j);
               if((user.UserName).equals(chatMemName)) {
     
                  user.WriteOneObject(cm);      
                 
                     }
               }
            }

      }
      public void sendProfileImg(ChatMsg cm) {
         for(int j=0;j<user_vc.size();j++) {
              UserService user = (UserService) user_vc.elementAt(j);
  
                 user.WriteOneObject(cm);      
              }     
      }
      
      public void saveProfile(String img, String userName)
      {
         
          File oldFile = new File(img);
          File newFile = new File("./src/profilesPakage/"+userName+".jpg");
          try {
              FileInputStream input = new FileInputStream(oldFile);
              FileOutputStream output = new FileOutputStream(newFile);

              byte[] buf = new byte[2048];

              int read;

              while((read = input.read(buf)) > 0)
              {
                  output.write(buf, 0, read);
              }

              input.close();
              output.close();
          }
          catch (IOException e)
          {
              e.printStackTrace();
          }                                 
      }
      
      public void run() {
         while (true) { // ����� ������ ����ؼ� �ޱ� ���� while��
            try {
               Object obcm = null;
               String msg = null;
               ChatMsg cm = null;
               if (socket == null)
                  break;
               try {
                  obcm = ois.readObject();
               } catch (ClassNotFoundException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                  return;
               }
               if (obcm == null)
                  break;
               if (obcm instanceof ChatMsg) {
                  cm = (ChatMsg) obcm;
                  AppendObject(cm);
               } else
                  continue;
               if (cm.code.matches("100")) { // ������ �����ϸ�                
                  UserName = cm.UserName;                                    
                  userNameList.add(cm.UserName);
               
                  WriteAll("101");
                  ImageIcon img = new ImageIcon("src/basicProfileImg.jpg"); // �⺻ �̹����� �ϴ� ���� 
                  cm.img = img;
                
                  cm.code = "750"; // ������ ���� Ŭ���̾�Ʈ���� ������ �ڵ� 750
                  sendProfileImg(cm);
               } 
            
               else if(cm.code.matches("500")) { // ����濡 �ʴ�ƴٸ�
                  // ����濡 �ʴ�� ��� ����Ʈ: cm.al, ����� �Ƶ�: i
          
                  AppendText("����� ���� id:"+cm.i+"/������:"+cm.al);                       
                  //���⼭ ����� �����������. 
                  multiChatNumNmems.put(multiChatNum, cm.al); //�������� ������� �����ϱ� ���ؼ� �� �ѹ��� ����Ʈ�� �߰���
                //al�� �ִ� userName ���ϸ鼭 hashmap�� filter�� �ڿ� ����
                 
                  MultiChat multiChat = new MultiChat(multiChatNum,cm.al);//number�� list ����
                
           
                 //���࿡ 580���� msg�ö� �� �޽��� �ȿ��� num�� al�� �־����. 
                  invitedUsersStr = String.join(",", cm.al);
                  //multichat�� �����ߵǰ� multiChatNum�� ��������.
                  WriteAll("550"); // ��ο��� �ϴ� 550 ������.             
                  
               }
               else if(cm.code.matches("210")) {//����濡�� �� �޽��� chatmembers�� ����
                 ArrayList<String> chatmems =multiChatNumNmems.get(cm.multiChatNum);
                 //multichatnum�� �´� �������� ������ ����Ʈ��.
                 
                 sendMessage(cm,chatmems);
               }
               else if(cm.code.matches("560")) { // ä�ù� ���� ���� �������� ����
                  
                  ArrayList<String> chatMems = multiChatNumNmems.get(cm.multiChatNum);
                  chatMems.remove(cm.UserName);
              
                  multiChatNumNmems.put(cm.multiChatNum, chatMems);
               
                  cm.al = chatMems;
                  sendMessage(cm,multiChatNumNmems.get(cm.multiChatNum));//���� ���� ���� ���������׸� ���� 
                }
             
               else if(cm.code.matches("700")) { // �� ������ ���縦 �ٲٸ� 
                    //saveProfile(cm.img.toString(), cm.UserName);
                  
                    
                    
                    cm.code = "750";
                    sendProfileImg(cm); // �ٲ� ������ �̹��� Ŭ���̾�Ʈ���� ���� 
                 
                }
               
        
               
          
               else if(cm.code.matches("300")) { // �̹����� ������ 
                  ArrayList<String> chatmems =multiChatNumNmems.get(cm.multiChatNum);                  
                  sendMessage(cm,chatmems);                                                                                        
               }
               else if (cm.code.matches("400")) { // logout message ó��
                  Logout();
                  break;
               }
               
               
              
               else { // 300, 500, ... ��Ÿ object�� ��� ����Ѵ�.
                  WriteAllObject(cm);
               } 
            } catch (IOException e) {
               AppendText("ois.readObject() error");
               try {

                  ois.close();
                  oos.close();
                  client_socket.close();
                  Logout(); // �������� ���� ��ü�� ���Ϳ��� �����
                  break;
               } catch (Exception ee) {
                  break;
               } // catch�� ��
            } // �ٱ� catch����
         } // while
      } // run
   }

}