package bgu.spl.net.srv;

import bgu.spl.net.api.BidiMessagingProtocol;

import java.io.IOException;

public interface Connections<T> {

    boolean send(int connectionId, T msg);

    void broadcast(T msg);

    void disconnect(int connectionId);


}
