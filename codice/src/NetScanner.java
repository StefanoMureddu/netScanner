/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/** Programma che permette di pingare su determinate porte un ip.
 *
 * @author stefano.mureddu
 * @version 03.12.2020
 */
import java.net.*;
import java.net.Socket;
import java.util.regex.Pattern;

public class NetScanner {

    /**
     *Espressione regolare per verificare gli ip.
     */
    private static final String IPV4_REGEX
            = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";

    /**
     * Pattern dell'espressione regolare.
     */
    private static final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);
    
    /**
     * Array per l'ip iniziale.
     */
    public int[] ip = {0, 0, 0, 0};
    
    /**
     * Array per l'ip finale.
     */
    private int[] ipF = {0, 0, 0, 0};
    
    /**
     * Array per le porte.
     */
    public String[] porte;
    
    /**
     * Attributo per controllare che i dati immessi siano validi.
     */
    public boolean valido;

    /**
     * Costruttore del NetScanner.
     * @param addS una stringa contenente l'ip iniziale
     * @param addF una stringa contenente l'ip finale
     * @param porte una stringa contenente le porte
     */
    public NetScanner(String addS, String addF, String porte) {
        valido = true;
        String str = addS;
        //controllo che il primo id sia valido
        if (isValid(str)) {
            String[] strIp = str.split("\\.", 4);
            for (int i = 0; i < strIp.length; i++) {
                this.ip[i] = Integer.parseInt(strIp[i]);
            }
        } else {
            valido = false;
        }
        //controllo che il secondo id sia valido
        String strF = addF;
        if (isValid(strF)) {
            String[] strIpF = strF.split("\\.", 4);
            for (int i = 0; i < strIpF.length; i++) {
                this.ipF[i] = Integer.parseInt(strIpF[i]);
            }
        } else {
            valido = false;
        }
        //controllo che le porte siano valide
        String strP = porte;
        if (isValidPort(strP)) {
            String[] strPS = strP.split(",");
            this.porte = new String[strPS.length];
            for (int i = 0; i < strPS.length; i++) {
                this.porte[i] = strPS[i];
            }
        } else {
            valido = false;
        }
    }

    /**
     * Metodo per pingare un determinato ip su una determinata porta.
     * @param ip l'ip da pingare
     * @param port la porta da pingare
     * @return se ha ricevuto una risposta
     */
    public static boolean pingPort(String ip, int port) {
        try {
            //provo a pingare e se risponde ritorno vero
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 100);
            socket.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Metodo di controllo di validità dell'ip.
     * @param ip l'ip da verificare sottoforma di stringa
     * @return se l'ip è valido
     */
    public static boolean isValid(String ip) {
        if (ip == null) {
            return false;
        }

        if (!IPv4_PATTERN.matcher(ip).matches()) {
            return false;
        }

        String[] parts = ip.split("\\.");

        // verify that each of the four subgroups of IPv4 address is legal
        try {
            for (String segment : parts) {
                // x.0.x.x is accepted but x.01.x.x is not
                if (Integer.parseInt(segment) > 255
                        || (segment.length() > 1 && segment.startsWith("0"))) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * Metodo per controllare che le porte siano valide.
     * @param port le porte in forma di stringa
     * @return se è valido
     */
    public static boolean isValidPort(String port) {
        boolean valid = true;
        if (port.matches("^[0-9,-]+$")) {
            String[] porta = port.split(",");
            String[][] range = new String[porta.length][2];
            for (int i = 0; i < porta.length; i++) {
                if (porta[i].contains("-")) {
                    range[i] = porta[i].split("-");
                    int p = Integer.parseInt(range[i][0]);
                    int p1 = Integer.parseInt(range[i][1]);
                    if (!(p > 0 && p < 65536 && p1 > 0 && p1 < 65536)) {
                        valid = false;
                    }
                } else {
                    int p = Integer.parseInt(porta[i]);
                    if (!(p > 0 && p < 65536)) {
                        valid = false;
                    }
                }
            }
        } else {
            valid = false;
        }
        return valid;
    }

    /**
     * Metodo per calcolare quanti ip pingare in totale
     * @param scanner l'oggetto scanner con cui si pinga
     * @return il numero di cicli da fare per pingare tutti gli ip
     */
    public static int cicli(NetScanner scanner) {
        int primo = (scanner.ipF[0] - scanner.ip[0]);
        int secondo = (scanner.ipF[1] - scanner.ip[1]);
        int terzo = (scanner.ipF[2] - scanner.ip[2]);
        int quarto = (scanner.ipF[3] - scanner.ip[3]) + 1;
        return (quarto + 255 * terzo + 255 * 255 * secondo + 255 * 255 * 255 * primo) * 255;
    }
}
