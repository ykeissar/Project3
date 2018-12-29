package bgu.spl.net.srv;

import bgu.spl.net.api.Message;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.NotificationMessage;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class User {
    private int id;
    private Queue<String> following;
    private Queue<String> followers;
//    private ConnectionHandler cH;
    private int posts=0;
    private Queue<Message> unreadMessages;
    private boolean loggedin=false;
    private String userName;
    private String password;

    public User(int id,String name,String pw){
        this.id=id;
        userName=name;
        password=pw;
        following=new ConcurrentLinkedQueue<String>();
        followers=new ConcurrentLinkedQueue<String>();
        unreadMessages=new ConcurrentLinkedQueue<Message>();
    }

    public String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }

    public boolean isLoggedin() {
        return loggedin;
    }

    public void login() {
        loggedin=true;

    }

    public void logout() {
        loggedin = false;
    }

    public Message getUnReadM(){
        if (!unreadMessages.isEmpty())
            return unreadMessages.poll();
        return null;
    }

    public void addUnread(Message m){
        unreadMessages.add(m);
    }

    public void setId(int id) {
        this.id = id;
    }

    public Queue<String> getFollowing() {
        return following;
    }

    public Queue<String> getFollowers() {
        return followers;
    }

    public int getId() {
        return id;
    }

    public void addPost() {
        posts++;
    }

    public int getPosts() {
        return posts;
    }
}


