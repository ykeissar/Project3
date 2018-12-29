package bgu.spl.net.api;

public class PMMessage implements Message {
    private String userName;
    private String cont;
    private int opCode;

    public PMMessage(String uN, String content){
        userName=uN;
        cont=content;
        opCode=6;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    @Override
    public int getOpCode() {
        return opCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getCont() {
        return cont;
    }
}
