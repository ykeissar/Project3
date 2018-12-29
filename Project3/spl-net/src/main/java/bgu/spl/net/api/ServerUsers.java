package bgu.spl.net.api;

import bgu.spl.net.srv.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerUsers {
    private ConcurrentHashMap<String, User> users;
    private ConcurrentLinkedQueue<String> userByOrder;

    public ConcurrentHashMap<String, User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.put(user.getUserName(),user);
        userByOrder.add(user.getUserName());
    }

    public ConcurrentLinkedQueue<String> getUserByOrder(){
        return userByOrder;
    }
}
