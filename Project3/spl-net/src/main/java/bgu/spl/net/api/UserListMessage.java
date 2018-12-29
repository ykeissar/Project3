package bgu.spl.net.api;

public class UserListMessage implements Message {
    private int opCode;

    public UserListMessage(){
        opCode=7;
    }

    @Override
    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }
}
