package bgu.spl.net.api;

public class LoginMessage implements Message {
    private String userName;
    private String password;
    private int opCode;

    public LoginMessage(String un,String pw){
        userName=un;
        password=pw;
        opCode=2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
