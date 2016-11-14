/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_encryption_system;

import Affan.AES;
import Affan.CLIENT;
import Affan.COMM;
import Affan.ChatBox;
import Affan.SERVER;
import Affan.SOUND;

/**
 *
 * @author affan
 */
public class Message_Receiver {
    private COMM comm_obj;
    private ChatBox cb;
    public static final int SERVER_COMM = 0;
    public static final int CLIENT_COMM = 1;

    public Message_Receiver(COMM comm_obj, ChatBox cb) {
        this.comm_obj = comm_obj;
        this.cb = cb;
    }

    public ChatBox getCb() {
        return cb;
    }

    public void setCb(ChatBox cb) {
        this.cb = cb;
    }

    public COMM getComm_obj() {
        return comm_obj;
    }

    public void setComm_obj(COMM comm_obj) {
        this.comm_obj = comm_obj;
    }
    
    public void Start(int COMM_TYPE){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    switch (COMM_TYPE) {
                        case SERVER_COMM:
                            while(true){
                                SERVER s = (SERVER) comm_obj;
                                String message = AES.decrypt(s.Receive());
                                if(message != null){
                                    cb.addMessage(message,ChatBox.RIGHT_USER);
                                    new SOUND().Notification();
                                }    
                            }
                        case CLIENT_COMM:
                            while(true){
                                CLIENT s = (CLIENT) comm_obj;
                                String message = AES.decrypt(s.Receive());
                                if(message != null){
                                    cb.addMessage(message,ChatBox.RIGHT_USER);
                                    new SOUND().Notification();
                                }                         
                            }
                        default:
                            break;
                    }
                }catch(Exception e){
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    
    
}
