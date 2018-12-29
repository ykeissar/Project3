package bgu.spl.net.srv;

import java.util.HashMap;

public class ConnectionsImpl<T> implements Connections<T> {
    private static int idCount = 0;
    private HashMap<Integer, ConnectionHandler<T>> map;

    public ConnectionsImpl() {
        map = new HashMap<>();
    }

    public void broadcast(T msg) {
        for (Integer i : map.keySet()) {
            map.get(i).send(msg);
        }
    }

    public void connect(ConnectionHandler<T> cH) {
        idCount++;
        cH.setId(idCount);
        map.put(idCount, cH);
    }

    public void disconnect(int connectionId) {
        map.remove(connectionId);
    }

    public boolean send(int connectionId, T msg) {
        if (map.containsKey(connectionId)) {
            map.get(connectionId).send(msg);
            return true;
        }
        return false;
    }

    public ConnectionHandler<T> getChById(int id){
        return map.get(id);
    }

    public HashMap<Integer, ConnectionHandler<T>> getMap(){
        return map;
    }
}

