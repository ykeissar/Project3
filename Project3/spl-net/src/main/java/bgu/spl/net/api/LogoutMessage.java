package bgu.spl.net.api;

public class LogoutMessage implements Message {
    private int opCode;
    public LogoutMessage(){
        opCode=3;
    }

    @Override
    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }
}
