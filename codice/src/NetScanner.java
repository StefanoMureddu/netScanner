/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author stefano.mureddu
 */
import java.io.*; 
import java.net.*;
import java.lang.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.Pattern;

public class NetScanner{

        private static final String IPV4_REGEX =
            "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
 
        private static final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);
	public int[] ip = {0,0,0,0};
	private int[] ipF = {0,0,0,0};
	public int[] porte;
        public boolean valido;

	public NetScanner(String addS, String addF, String porte){
                valido = true;
		String str = addS;
                if(isValid(str)){
                    String[] strIp = str.split("\\.",4);
                    for(int i = 0; i<strIp.length;i++){
                            this.ip[i] = Integer.parseInt(strIp[i]);
                    }
                }else{
                    valido = false;
                    System.out.print("Ip iniziale non riconosciuto, scriverlo nel formato x.x.x.x");
                }
		String strF = addF;
                if(isValid(strF)){
		String[] strIpF = strF.split("\\.",4);
                    for(int i = 0; i<strIpF.length;i++){
                            this.ipF[i] = Integer.parseInt(strIpF[i]);
                    }
                }else{
                    valido = false;
                    System.out.print("Ip finale non riconosciuto, scriverlo nel formato x.x.x.x");
                }
		String strP = porte;
		String[] strPS = strP.split(",");
		this.porte = new int[strPS.length];
		for(int i = 0; i<strPS.length;i++){
			this.porte[i] = Integer.parseInt(strPS[i]);
		}
	}

	public static void sendPingRequest(String ipAddress) throws UnknownHostException, IOException { 
		InetAddress geek = InetAddress.getByName(ipAddress); 
		System.out.println("Sending Ping Request to " + ipAddress); 

		if (geek.isReachable(5000)) 
		System.out.println("Host is reachable"); 
		else
		System.out.println("Sorry ! We can't reach to this host"); 
	}

	public static boolean pingPort(String ip, int port){
		try{
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, port), 6);
			socket.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

        public static boolean isValid(String ip)
        {
            if (ip == null) {
            return false;
        }
 
        if (!IPv4_PATTERN.matcher(ip).matches())
            return false;
 
        String[] parts = ip.split("\\.");
 
        // verify that each of the four subgroups of IPv4 address is legal
        try {
            for (String segment: parts) {
                // x.0.x.x is accepted but x.01.x.x is not
                if (Integer.parseInt(segment) > 255 ||
                            (segment.length() > 1 && segment.startsWith("0"))) {
                    return false;
                }
            }
        } catch(NumberFormatException e) {
            return false;
        }
 
        return true;
        }
            
	public static int cicli(NetScanner scanner){
		int primo = (scanner.ipF[0]-scanner.ip[0]);
		int secondo = (scanner.ipF[1]-scanner.ip[1]);
		int terzo = (scanner.ipF[2]-scanner.ip[2]);
		int quarto = (scanner.ipF[3]-scanner.ip[3])+1;
		return (quarto + 255*terzo + 255*255*secondo + 255*255*255*primo)*255;
	}

        /*
        
        public static void pinga(String[]args)throws UnknownHostException, IOException{
		NetScanner scanner = new NetScanner();
		for(int k = 0; k<cicli(scanner);k++){
			String ipAddress = scanner.ip[0] + "."+scanner.ip[1] + "."+scanner.ip[2] + "."+scanner.ip[3]; 
			for(int i = 0;i<scanner.porte.length;i++){
				if(pingPort(ipAddress,scanner.porte[i])){
					System.out.println(ipAddress+" funziona sulla porta "+scanner.porte[i]);
				}else{
					System.out.println(ipAddress+" non funziona sulla porta "+scanner.porte[i]);
				}
			}
			//sendPingRequest(ipAddress);
			if(scanner.ip[3]==255){
				scanner.ip[3] = 0;
				if(scanner.ip[2]==255){
					scanner.ip[2]=0;
					if(scanner.ip[1]==255){
						scanner.ip[1]=0;
						scanner.ip[0]++;
					}else{
						scanner.ip[1]++;
					}
				}else{
					scanner.ip[2]++;
				}
			}else{
				scanner.ip[3]++;
			}
		}
/*
		String ipAddress = scanner.ip[0] + "."+scanner.ip[1] + "."+scanner.ip[2] + "."+scanner.ip[3]; 
		sendPingRequest(ipAddress);

		scanner.ip[3]++;
		ipAddress = scanner.ip[0] + "."+scanner.ip[1] + "."+scanner.ip[2] + "."+scanner.ip[3];
		sendPingRequest(ipAddress);
		
	}*/

}