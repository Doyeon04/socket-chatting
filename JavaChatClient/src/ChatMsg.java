
// ChatMsg.java ä�� �޽��� ObjectStream ��.
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
   private static final long serialVersionUID = 1L;
   public String code; // 100:�α���, 400:�α׾ƿ�, 200:ä�ø޽���, 300:Image, 500: Mouse Event
   public String UserName;
   public String data;
   public String presentTime;
   public ImageIcon img;
   public ImageIcon senderImg;
   public MouseEvent mouse_e;
   public int pen_size; // pen size
   public ArrayList<String> al;
   public String friend[];
   public int i;
   public int multiChatNum;
   //public String chatMems;
   
   public ChatMsg(String UserName, String code, String msg) {
      this.code = code;
      this.UserName = UserName;
      this.data = msg;
   }
   public void setList(ArrayList<String> al) {
      this.al = al;
      System.out.println(al);
   }
   
   public Object getList() {
      // TODO Auto-generated method stub
      return al;
   }
}