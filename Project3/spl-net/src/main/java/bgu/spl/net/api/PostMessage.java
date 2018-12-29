package bgu.spl.net.api;

public class PostMessage implements Message {
    private String cont;
    private int opCode;

    public PostMessage(String content){
        opCode=5;
        cont=content;
    }

    @Override
    public int getOpCode() {
        return opCode;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getCont() {
        return cont;
    }
}
