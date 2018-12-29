package bgu.spl.net.api;

public class StatMessage implements Message {
    private String userName;
    private int opCode;

    public StatMessage(String uN){
        userName=uN;
        opCode=8;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }
}
