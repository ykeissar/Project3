package bgu.spl.net.api;

public class NotificationMessage implements Message {
    private int notificationType;
    private String postingUser;
    private String cont;
    private int opCode;


    public NotificationMessage(int type,String pUser,String content){
        notificationType=type;
        postingUser=pUser;
        cont=content;
        opCode=9;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public void setPostingUser(String postingUser) {
        this.postingUser = postingUser;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public int getOpCode() {
        return opCode;
    }


    public int getNotificationType() {
        return notificationType;
    }

    public String getPostingUser() {
        return postingUser;
    }

    public String getCont() {
        return cont;
    }
}
