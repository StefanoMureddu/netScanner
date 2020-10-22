import java.io.*; 
import java.net.*;
import java.lang.*;

public class NetScanner{

	private int[] ip = {0,0,0,0};
	private int[] ipF = {0,0,0,0};

	public NetScanner(/*InetAddress addS, InetAddress addF*/){
		String str = "10.20.4.249";
		String[] strIp = str.split("\\.",4);
		for(int i = 0; i<strIp.length;i++){
			this.ip[i] = Integer.parseInt(strIp[i]);
		}
		String strF = "10.20.5.4";
		String[] strIpF = strF.split("\\.",4);
		for(int i = 0; i<strIp.length;i++){
			this.ipF[i] = Integer.parseInt(strIpF[i]);
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

	public static int cicli(NetScanner scanner){
		int primo = (scanner.ipF[0]-scanner.ip[0]);
		int secondo = (scanner.ipF[1]-scanner.ip[1]);
		int terzo = (scanner.ipF[2]-scanner.ip[2]);
		int quarto = (scanner.ipF[3]-scanner.ip[3])+1;
		return quarto + 255*terzo + 255*255*secondo + 255*255*255*primo;
	}

	public static void main(String[]args)throws UnknownHostException, IOException{
		NetScanner scanner = new NetScanner();
		for(int k = 0; k<cicli(scanner);k++){
			String ipAddress = scanner.ip[0] + "."+scanner.ip[1] + "."+scanner.ip[2] + "."+scanner.ip[3]; 
			sendPingRequest(ipAddress);
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
		*/
	}
}