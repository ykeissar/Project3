package bgu.spl.net.api;

public class FollowMessage implements Message {
    private int followUnfollow;
    private String[] userList;
    private int opCode;


    public FollowMessage(int fUf,String[] uL){
        followUnfollow=fUf;
        userList=uL;
        opCode=4;
    }

    public String[] getUserList() {
        return userList;
    }

    public void setUserList(String[] userList) {
        this.userList = userList;
    }

    public int getFollowUnfollow() {
        return followUnfollow;
    }

    public void setFollowUnfollow(int followUnfollow) {
        this.followUnfollow = followUnfollow;
    }

    @Override
    public int getOpCode() {
        return opCode;
    }
}
