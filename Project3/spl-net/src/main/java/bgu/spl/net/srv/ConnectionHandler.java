package bgu.spl.net.srv;

import bgu.spl.net.api.BidiMessagingProtocol;

import java.io.Closeable;
import java.io.IOException;

public interface ConnectionHandler<T> extends Closeable{

    void send(T msg) ;

    void setId(int num);

    BidiMessagingProtocol<T> getProtocol();

}
