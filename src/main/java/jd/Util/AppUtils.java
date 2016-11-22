package jd.Util;

import com.stripe.exception.*;
import com.stripe.model.Token;
import jd.persistence.model.Paymethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by eduardom on 9/5/16.
 */
public class AppUtils {

    //Establece la apikey de stripe
    private static final String StripeApiKey="sk_test_Xo9MTaPxVDTafZVdMpGMj8kr";

    private static final String secret = "ZDQ3ZmRmZTM1MjIxODk0MWUxNDRlMGQ4YmMzZTBlZjI=";

    private static final String tcoSellerId="901332927"; //Sandbox
    private static final String tcoPublishableKey="2E342104-E120-4B1F-A943-A6569479891B"; //Sandbox
    private static final String tcoPrivateKey="38353F1C-0A4B-47DE-8751-2E3B9EB54388"; //Sandbox

    private  Date fechaActual = new Date();

    public int servicerequest_guarantee=30; //percent

    public int servicerequest_dexpired =15;


    //Funciones Generales
    public String getCadenaNumAleatoria (int longitud){
        String cadenaAleatoria="";
        long milis = new GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while ( i < longitud){
            char c = (char)r.nextInt(255);
            if ((c >= '0' && c <='9')){
                cadenaAleatoria += c;
                i ++;
            }
        }
        return cadenaAleatoria;
    }

    public String getCadenaAlfaNumAleatoria (int longitud){
        String cadenaAleatoria = "";
        //long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random();
        int i = 0;
        while ( i < longitud){
            char c = (char)r.nextInt(255);
            if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
                cadenaAleatoria += c;
                i ++;
            }
        }
        return cadenaAleatoria;
    }

    public Date sumarMesesAFecha(Date f, int meses) {
        Calendar c = Calendar.getInstance();
        c.setTime(f);
        c.add(Calendar.MONTH, meses);
        return c.getTime();
    }

    public Date sumarDiasAFecha(Date f, int dias) {
        Calendar c = Calendar.getInstance();
        c.setTime(f);
        c.add(Calendar.DATE, dias);
        return c.getTime();
    }

    //generate stripe's token for begin a transaction
    public String getStripeToken(Paymethod card, String pay_ccsec){

        Calendar fechaexp = Calendar.getInstance();
        fechaexp.setTime(card.getPayvalid());

        Map<String, Object> tokenParams = new HashMap<String, Object>();
        Map<String, Object> cardParams = new HashMap<String, Object>();
        cardParams.put("number", card.getPayacctnum());
        cardParams.put("exp_month",fechaexp.get(Calendar.MONTH) + 1);
        cardParams.put("exp_year", fechaexp.get(Calendar.YEAR));
        cardParams.put("cvc", pay_ccsec);

        System.out.println(tokenParams);

        tokenParams.put("card", cardParams);

        try{
           return Token.create(tokenParams).getId();
        }catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (CardException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStripeApiKey(){
        return StripeApiKey;
    }

    public String getSecret() {
        return secret;
    }

    public static String Encriptar(String texto) {

        String secretKey = "ZDQ3ZmRmZT"; //llave para encriptar datos
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.getEncoder().encode(buf);

            base64EncryptedString = new String(base64Bytes);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return base64EncryptedString;
    }

    public static String Desencriptar(String textoEncriptado) throws Exception {

        String secretKey = "ZDQ3ZmRmZT"; //llave para encriptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.getDecoder().decode(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return base64EncryptedString;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public  String getTcoSellerId() {
        return tcoSellerId;
    }

    public  String getTcoPublishableKey() {
        return tcoPublishableKey;
    }

    public  String getTcoPrivateKey() {
        return tcoPrivateKey;
    }
}