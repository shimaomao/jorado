package com.jorado.core.util;

import java.net.*;
import java.util.Enumeration;

public class IPResolver {

    public static String getIP() {
        String ip = getLocalIp();
        String osName = System.getProperties().getProperty("os.name");
        if (osName != null) {
            osName = osName.toLowerCase();
            if (osName.startsWith("linux")) {
                try {
                    Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
                    while (allNetInterfaces.hasMoreElements()) {
                        NetworkInterface netInterface = allNetInterfaces.nextElement();
                        Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                        if (netInterface.getName().startsWith("eth")) {
                            while (addresses.hasMoreElements()) {
                                InetAddress netAddr = addresses.nextElement();
                                if (netAddr != null && netAddr instanceof Inet4Address) {
                                    return netAddr.getHostAddress();
                                }
                            }
                        }
                    }
                } catch (SocketException exp) {

                }
            } else {
                return getLocalIp();
            }
        }
        return ip;
    }

    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException exp) {

        }
        return null;
    }
}
