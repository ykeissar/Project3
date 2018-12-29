package bgu.spl.net.api;

public class ErrorMessage implements Message {
    private int messageOp;
    private int opCode;

    public ErrorMessage(int mOp){
        messageOp=mOp;
        opCode=11;
    }

    public int getMessageOp() {
        return messageOp;
    }

    public void setMessageOp(int messageOp) {
        this.messageOp = messageOp;
    }

    @Override
    public int getOpCode() {
        return opCode;
    }
}
