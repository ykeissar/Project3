package bgu.spl.net.api;

public class RegisterMessage implements Message {
    private String userName;
    private String password;
    private int opCode;


    public RegisterMessage(String un,String pw){
        userName=un;
        password=pw;
        opCode=1;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }
}
