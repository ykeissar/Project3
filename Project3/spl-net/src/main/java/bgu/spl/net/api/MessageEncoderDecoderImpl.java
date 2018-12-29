package bgu.spl.net.api;

import java.nio.charset.StandardCharsets;

public class MessageEncoderDecoderImpl implements MessageEncoderDecoder<Message> {
    private short tempOp=0;
    private byte[] tempByte;
    private Message toReturn;
    private int inserted=0;
    private int count1=0;
    private int count2=0;
    private boolean init=false;
    private int tempOp2=0;


    @Override
    public Message decodeNextByte(byte nextByte) {
        switch (tempOp) {
            //finding opcode, message type
            case 0: {
                if (inserted == 0) {
                    tempByte[inserted] = nextByte;
                    inserted++;
                    return null;
                }
                tempByte[inserted] = nextByte;
                tempOp = bytesToShort(tempByte);
                inserted = 0;
                return null;
            }
            //-----------------------RegisterMessage-----------------------------
            case 1: {
                if (!init) {
                    RegisterMessage toReturn = new RegisterMessage("", "");
                    init = true;
                }
                //case decoding username
                if (count1 == 0) {
                    if (nextByte == '\0') {
                        count1++;
                        ((RegisterMessage) toReturn).setUserName(new String(tempByte, 0, inserted, StandardCharsets.UTF_8));
                        inserted = 0;
                        return null;
                    }
                    tempByte[inserted] = nextByte;
                    inserted++;
                    return null;
                }
                //case decoding password
                if (nextByte == '\0') {
                    ((RegisterMessage) toReturn).setPassword(new String(tempByte, 0, inserted, StandardCharsets.UTF_8));
                    tempOp = 0;
                    count1 = 0;
                    inserted = 0;
                    init = false;
                    return toReturn;
                }
                tempByte[inserted] = nextByte;
                inserted++;
                return null;
            }
            //-----------------------LoginMessage-----------------------------
            case 2: {
                if (!init) {
                    LoginMessage toReturn = new LoginMessage("", "");
                    init = true;
                }
                //case decoding username
                if (count1 == 0) {
                    if (nextByte == '\0') {
                        count1++;
                        ((LoginMessage) toReturn).setUserName(new String(tempByte, 0, inserted, StandardCharsets.UTF_8));
                        inserted = 0;
                        return null;
                    }
                    tempByte[inserted] = nextByte;
                    inserted++;
                    return null;
                }
                //case decoding password
                if (nextByte == '\0') {
                    ((LoginMessage) toReturn).setPassword(new String(tempByte, 0, inserted, StandardCharsets.UTF_8));
                    tempOp = 0;
                    count1 = 0;
                    inserted = 0;
                    init = false;
                    return toReturn;
                }
                tempByte[inserted] = nextByte;
                inserted++;
                return null;

            }
            //-----------------------LogoutMessage-----------------------------
            case 3: {
                tempOp = 0;
                return toReturn = new LogoutMessage();
            }
            //-----------------------FollowMessage-----------------------------
            case 4: {
                if (!init) {
                    FollowMessage toReturn = new FollowMessage(-1, null);
                    init = true;
                }
                //decode FollowUnfollow
                if (count1 == 0) {
                    ((FollowMessage) toReturn).setFollowUnfollow((nextByte & 0xff));
                    count1++;
                    return null;
                }
                //decode num of users
                if (count1 == 1) {
                    if (inserted == 1) {
                        count1++;
                        tempByte[inserted] = nextByte;
                        count2 = bytesToShort(tempByte);
                        ((FollowMessage) toReturn).setUserList(new String[count2]);
                        inserted = 0;
                        return null;
                    }
                    tempByte[inserted] = nextByte;
                    inserted++;
                    return null;
                }
                //decode user name list
                if (count1 == 2) {
                    if (nextByte == '\0') {
                        if (count2 != 0) {
                            ((FollowMessage) toReturn).getUserList()[count2 - 1] = new String(tempByte, 0, inserted, StandardCharsets.UTF_8);
                            inserted = 0;
                            count2--;
                        }
                        if (count2 == 0) {
                            count1 = 0;
                            tempOp = 0;
                            inserted = 0;
                            init = false;
                            return toReturn;
                        }
                        return null;
                    }
                    tempByte[inserted] = nextByte;
                    inserted++;
                    return null;
                }
            }
            //-----------------------PostMessage-----------------------------
            case 5: {
                if (nextByte == '\0') {
                    toReturn = new PostMessage(new String(tempByte, 0, inserted, StandardCharsets.UTF_8));
                    tempOp = 0;
                    count1 = 0;
                    inserted = 0;
                    init = false;
                    return toReturn;
                }
                tempByte[inserted] = nextByte;
                inserted++;
                return null;
            }
            //-----------------------PMMessage-----------------------------
            case 6: {
                if (!init) {
                    PMMessage toReturn = new PMMessage("", "");
                    init = true;
                }
                //case decoding username
                if (count1 == 0) {
                    if (nextByte == '\0') {
                        count1++;
                        ((PMMessage) toReturn).setUserName(new String(tempByte, 0, inserted, StandardCharsets.UTF_8));
                        inserted = 0;
                        return null;
                    }
                    tempByte[inserted] = nextByte;
                    inserted++;
                    return null;
                }
                //case decoding content
                if (nextByte == '\0') {
                    ((PMMessage) toReturn).setCont(new String(tempByte, 0, inserted, StandardCharsets.UTF_8));
                    tempOp = 0;
                    count1 = 0;
                    inserted = 0;
                    init = false;
                    return toReturn;
                }
                tempByte[inserted] = nextByte;
                inserted++;
                return null;
            }
            //-----------------------USERLISTMessages-----------------------------
            case 7: {
                tempOp = 0;
                return toReturn = new UserListMessage();
            }
            //-----------------------STATMessages-----------------------------
            case 8: {
                if (nextByte == '\0') {
                    toReturn = new StatMessage(new String(tempByte, 0, inserted, StandardCharsets.UTF_8));
                    tempOp = 0;
                    count1 = 0;
                    inserted = 0;
                    init = false;
                    return toReturn;
                }
                tempByte[inserted] = nextByte;
                inserted++;
                return null;
            }
//            //-----------------------NotificationMessage-----------------------------
//            case 9: {
//                if (!init) {
//                    NotificationMessage toReturn = new NotificationMessage(-1, "", "");
//                    init = true;
//                }
//                //decode NotificationTye
//                if (count1 == 0) {
//                    if (inserted == 1) {
//                        count1++;
//                        tempByte[inserted] = nextByte;
//                        ((NotificationMessage) toReturn).setNotificationType(Integer.parseInt(new String(tempByte, 0, inserted, StandardCharsets.UTF_8)));
//                        inserted = 0;
//                        return null;
//                    }
//                    tempByte[inserted] = nextByte;
//                    inserted++;
//                    return null;
//                }
//                //decode PostingUser
//                if (count1 == 1) {
//                    if (nextByte == '\0') {
//                        ((NotificationMessage) toReturn).setPostingUser(new String(tempByte, 0, inserted, StandardCharsets.UTF_8));
//                        count1++;
//                        inserted = 0;
//                        return null;
//                    }
//                    tempByte[inserted] = nextByte;
//                    inserted++;
//                    return null;
//                }
//                if (nextByte == '\0') {
//                    ((NotificationMessage) toReturn).setCont(new String(tempByte, 0, inserted, StandardCharsets.UTF_8));
//                    count1 = 0;
//                    tempOp = 0;
//                    inserted = 0;
//                    init = false;
//                    return toReturn;
//                }
//                tempByte[inserted] = nextByte;
//                inserted++;
//                return null;
//
//            }
//            //-----------------------ACKMessage-----------------------------
//            case 10: {
//                switch (tempOp2) {
//                    case 0: {
//                        if (!init) {
//                            AckMessage toReturn = new AckMessage(0);
//                            init = true;
//                        }
//                        if (inserted == 0) {
//                            tempByte[inserted] = nextByte;
//                            inserted++;
//                            return null;
//                        }
//                        tempByte[inserted] = nextByte;
//                        tempOp2=bytesToShort(tempByte);
//                        ((AckMessage) toReturn).setMessageOp(tempOp2);
//                        inserted = 0;
//                        return null;
//                    }
//                    case 1:
//                    case 2:
//                    case 3:
//                    case 5:
//                    case 6:{
//                        tempOp2=0;
//                        tempOp=0;
//                        init=false;
//                        inserted=0;
//                        return toReturn;
//                    }
//                    case 4:
//                    case 7:{
//                        //decode num of users
//                        if (count1 == 0) {
//                            if (inserted == 1) {
//                                count1++;
//                                tempByte[inserted] = nextByte;
//                                count2 = bytesToShort(tempByte);
//                                ((AckMessage) toReturn).setUserList(new String[count2]);
//                                inserted = 0;
//                                return null;
//                            }
//                            tempByte[inserted] = nextByte;
//                            inserted++;
//                            return null;
//                        }
//                        if (nextByte == '\0') {
//                            if (count2 != 0) {
//                                ((AckMessage) toReturn).getUserList()[count2 - 1] = new String(tempByte, 0, inserted, StandardCharsets.UTF_8);
//                                inserted = 0;
//                                count2--;
//                            }
//                            if (count2 == 0) {
//                                count1 = 0;
//                                tempOp = 0;
//                                tempOp2=0;
//                                inserted = 0;
//                                init = false;
//                                return toReturn;
//                            }
//                            return null;
//                        }
//                        tempByte[inserted] = nextByte;
//                        inserted++;
//                        return null;
//                    }
//                    case 8:{
//                        switch (count1) {
//                            case 0: {
//                                if (inserted == 1) {
//                                    count1++;
//                                    tempByte[inserted] = nextByte;
//                                    ((AckMessage) toReturn).setNumPosts(bytesToShort(tempByte));
//                                    inserted = 0;
//                                    return null;
//                                }
//                                tempByte[inserted] = nextByte;
//                                inserted++;
//                                return null;
//                            }
//                            case 1: {
//                                if (inserted == 1) {
//                                    count1++;
//                                    tempByte[inserted] = nextByte;
//                                    ((AckMessage) toReturn).setNumFollowers(bytesToShort(tempByte));
//                                    inserted = 0;
//                                    return null;
//                                }
//                                tempByte[inserted] = nextByte;
//                                inserted++;
//                                return null;
//                            }
//                            case 2: {
//                                if (inserted == 1) {
//                                    count1++;
//                                    tempByte[inserted] = nextByte;
//                                    ((AckMessage) toReturn).setNumFollowing(bytesToShort(tempByte));
//                                    inserted = 0;
//                                    count1 = 0;
//                                    init = false;
//                                    tempOp = 0;
//                                    tempOp2 = 0;
//                                    return toReturn;
//                                }
//                                tempByte[inserted] = nextByte;
//                                inserted++;
//                                return null;
//                            }
//                        }
//                    }
//
//                }
//            }
//            //-----------------------ErrorMessage-----------------------------
//            case 11:{
//
//                if (inserted == 0) {
//                    tempByte[inserted] = nextByte;
//                    inserted++;
//                    return null;
//                }
//                tempByte[inserted] = nextByte;
//                ((ErrorMessage) toReturn).setMessageOp(bytesToShort(tempByte));
//                inserted = 0;
//                return toReturn;
//            }
        }
            return null;
    }


    @Override
    public byte[] encode(Message message){
        String opCode= Integer.toString(message.getOpCode());
        switch(message.getOpCode()){
            //-----------------------NotificationMessage-----------------------------
            case 9:{
                String nT= Integer.toString(((NotificationMessage)message).getNotificationType());
                byte[] bytes= (opCode + nT+((NotificationMessage) message).getPostingUser() + '\0' +((NotificationMessage) message).getCont() + '\0').getBytes();
            return bytes;
            }
            //-----------------------AckMessage--------------------------------------
            case 10:{
                String opM=Integer.toString(((AckMessage) message).getMessageOp());
                switch (((AckMessage) message).getMessageOp()){
                    case 1:
                    case 2:
                    case 3:
                    case 5:
                    case 6:{
                        return (opCode+opM).getBytes();
                    }
                    case 4:
                    case 7:{
                        int i=((AckMessage) message).getUserList().length;
                        String s=Integer.toString(i);
                        s=opCode+opM+s;
                        for(int j=0;j<i;j++){
                            s+=((AckMessage) message).getUserList()[j]+'\0';
                        }
                        return s.getBytes();
                    }
                    case 8:{
                        String s=opCode+opM;
                        String s1=Integer.toString(((AckMessage) message).getNumPosts());
                        s+=s1;
                        s1=Integer.toString(((AckMessage) message).getNumFollowers());
                        s+=s1;
                        s1=Integer.toString(((AckMessage) message).getNumFollowing());
                        s+=s1;
                        return s.getBytes();
                    }
                }
            }
            //-----------------------ErrorMessage--------------------------------------
            case 11:{
                String opM=Integer.toString(((ErrorMessage) message).getMessageOp());
                return (opCode+opM).getBytes();
            }
        }
        return null;
    }

    public short bytesToShort(byte[] byteArr){
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
}
