package hello;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SystemInfoUtil {
    public static String getLocalIpAddress() {
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            return socket.getLocalAddress().getHostAddress();
        } catch (UnknownHostException | SocketException e) {
            return "127.0.0.1";
        }
    }

    public static String getMacAddress() {
        byte[] mac;
        try {
            mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
        } catch (UnknownHostException | SocketException e) {
            return ("00-00-00-00-00-00");
        }

        if ((mac == null) || (mac.length == 0)) {
            return ("00-00-00-00-00-00");
        }
        StringBuilder macAddressBuilder = new StringBuilder();
        for (byte b : mac) {
            macAddressBuilder.append("-");
            String str = Integer.toHexString(b & 0xff);
            if (str.length() == 1) {
                macAddressBuilder.append("0").append(str);
            } else {
                macAddressBuilder.append(str);
            }
        }
        return macAddressBuilder.toString().substring(1).toUpperCase();
    }



    public static String getSystemVersion() {
        return System.getProperty("os.name") + ", 版本 " + System.getProperty("os.version");
    }
}

