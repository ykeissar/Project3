package bgu.spl.net.api;

public class AckMessage implements Message {
    private int messageOp;
    private int opCode;
    private String[] userList=null;
    private int numPosts;
    private int numFollowers;
    private int numFollowing;

    public AckMessage(int mOp){
        messageOp=mOp;
        opCode=10;
    }

    public void setUserList(String[] userList) {
        this.userList = userList;
    }

    public String[] getUserList() {
        return userList;
    }

    public void setNumPosts(int numPosts) {
        this.numPosts = numPosts;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public int getNumFollowing() {
        return numFollowing;
    }

    public void setNumFollowing(int numFollowing) {
        this.numFollowing = numFollowing;
    }

    public int getNumPosts() {
        return numPosts;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public void setMessageOp(int messageOp) {
        this.messageOp = messageOp;
    }

    public int getMessageOp() {
        return messageOp;
    }

    @Override
    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }
}

