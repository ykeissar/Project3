package bgu.spl.net.api;

import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.Connections;

import java.util.LinkedList;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol<Message> {
    private int connectionId;
    private Connections<Message> connections;
    private User user=null;
    private ServerUsers users;
    private boolean shouldTerminate=false;

    public void start(int connectionId, Connections<Message> connections){
        this.connectionId=connectionId;
        this.connections=connections;
    }

    public void process(Message message){
        int op=message.getOpCode();
        switch(op){
    //------------------------RegisterMessage------------------------------
            case 1:{
                if(users.getUsers().contains(((RegisterMessage) message).getUserName())) {
                    connections.send(connectionId, new ErrorMessage(1));
                }
                else{
                    users.addUser(new User(connectionId,(((RegisterMessage) message).getUserName()),(((RegisterMessage) message).getPassword())));
                    connections.send(connectionId, new AckMessage(1));
                }

            }
    //------------------------LoginMessage---------------------------------
            case 2:{
                if(user!=null ||
                        ((LoginMessage) message).getPassword()!=users.getUsers().get(((LoginMessage) message).getUserName()).getPassword() ||
                        user.isLoggedin()){
                    connections.send(connectionId, new ErrorMessage(2));
                }
                else{
                    user= users.getUsers().get(((LoginMessage) message).getUserName());
                    user.setId(connectionId);
                    user.login();
                    connections.send(connectionId, new AckMessage(2));
                    Message m= user.getUnReadM();
                    while (m!=null){
                        connections.send(connectionId,m);
                        m= user.getUnReadM();
                    }
                }
            }
   //-------------------------LogoutMessage--------------------------------
            case 3:{
                if (user==null)
                    connections.send(connectionId, new ErrorMessage(3));
                else {
                    user.logout();
                    user=null;
                    connections.send(connectionId, new AckMessage(3));
                    shouldTerminate=true;
                }
            }
   //-------------------------FollowMessage--------------------------------
            case 4:{
                if(user==null)
                    connections.send(connectionId, new ErrorMessage(4));
                else {
                    boolean added=false;
                    LinkedList<String> ll= new LinkedList<>();
                    if (((FollowMessage) message).getFollowUnfollow() == 0) //follow
                        for (int i=0 ; i<((FollowMessage) message).getUserList().length ; i++) {
                            if (users.getUsers().containsKey(((FollowMessage) message).getUserList()[i])) //if user registered
                                if (!user.getFollowing().contains(((FollowMessage) message).getUserList()[i])) { //check if is not following
                                    user.getFollowing().add(((FollowMessage) message).getUserList()[i]); //add to following
                                    (users.getUsers().get(((FollowMessage) message).getUserList()[i])).getFollowers().add(user.getUserName());// add to followers
                                    ll.add(((FollowMessage) message).getUserList()[i]);
                                    added = true;
                                }
                        }
                    else { //unfollow
                        for (int i=0 ; i<((FollowMessage) message).getUserList().length ; i++) {
                            if (users.getUsers().containsKey(((FollowMessage) message).getUserList()[i])) //if user registered
                                if (user.getFollowing().contains(((FollowMessage) message).getUserList()[i])) { ////check if is already following
                                    user.getFollowing().remove(((FollowMessage) message).getUserList()[i]); //remove from following
                                    (users.getUsers().get(((FollowMessage) message).getUserList()[i])).getFollowers().remove(user.getUserName());// remove from followers
                                    ll.add(((FollowMessage) message).getUserList()[i]);
                                    added = true;
                                }
                        }
                    }
                    if (ll.isEmpty())
                        connections.send(connectionId, new ErrorMessage(4));
                    else {
                        String[] s = new String[ll.size()];
                        for (int i=0 ; i<s.length ; i++)
                            s[i]=ll.remove();
                        AckMessage aM=new AckMessage(4);
                        aM.setUserList(s);
                        connections.send(connectionId, aM);
                    }
                }
            }
   //-------------------------PostMessage----------------------------------
            case 5:{
                if(user==null)
                    connections.send(connectionId, new ErrorMessage(5));
                else{
                    user.addPost();
                    NotificationMessage nm=new NotificationMessage(1,user.getUserName(),((PostMessage) message).getCont());
                    for(String name:user.getFollowers())
                        send(name,nm);
                    LinkedList<String> l=new LinkedList<>();
                    int i=((PostMessage) message).getCont().indexOf('@',0);
                    while(i>=0){
                        int j=((PostMessage) message).getCont().indexOf(' ',i);
                        if(j==-1)
                            j=((PostMessage) message).getCont().length();
                        l.add(((PostMessage) message).getCont().substring(i+1,j));
                        i=((PostMessage) message).getCont().indexOf('@',i);
                    }
                    for (String name:l)
                        if(!user.getFollowers().contains(name))
                            send(name,nm);
                    connections.send(connectionId, new AckMessage(5));
                }
            }
   //-------------------------PMMessage------------------------------------
            case 6:{
                if(user==null||!users.getUsers().contains(((PMMessage) message).getUserName()))
                    connections.send(connectionId, new ErrorMessage(6));
                else{
                    NotificationMessage nm=new NotificationMessage(0,user.getUserName(),((PMMessage) message).getCont());
                    send(((PMMessage) message).getUserName(),nm);
                    connections.send(connectionId, new AckMessage(6));
                }
            }
    //------------------------UserListMessage------------------------------
            case 7:{
                if(user==null)
                    connections.send(connectionId, new ErrorMessage(7));
                else{
                    AckMessage m=new AckMessage(7);
                    m.setUserList((String[])users.getUserByOrder().toArray());
                    connections.send(connectionId,m);
                }
            }
    //------------------------StatMessage----------------------------------
            case 8:{
                if(user==null||users.getUsers().contains(((StatMessage) message).getUserName()))
                    connections.send(connectionId, new ErrorMessage(8));
                else{
                    AckMessage m=new AckMessage(8);
                    m.setNumFollowing(users.getUsers().get(((StatMessage) message).getUserName()).getFollowing().size());
                    m.setNumFollowers(users.getUsers().get(((StatMessage) message).getUserName()).getFollowers().size());
                    m.setNumPosts(users.getUsers().get(((StatMessage) message).getUserName()).getPosts());
                    connections.send(connectionId, new AckMessage(8));
                }

            }

        }
    }

    public boolean shouldTerminate(){
        return shouldTerminate;
    }

    public void send(String name,Message m){
        if(users.getUsers().get(name).isLoggedin())
            connections.send(users.getUsers().get(name).getId(),m);
        else
            users.getUsers().get(name).addUnread(m);
    }
}

