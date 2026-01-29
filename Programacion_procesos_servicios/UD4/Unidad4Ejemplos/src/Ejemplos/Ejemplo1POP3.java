/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejemplos;

import java.io.BufferedReader;
import java.io.IOException;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.apache.commons.net.pop3.POP3SClient;

/**
 *
 * @author manuel.soto
 */
public class Ejemplo1POP3 {
    public static void main(String[] args) {
        String server = "localhost", username = "usu1", password = "usu1";
        int puerto = 110;
        
        POP3SClient pop3 = new POP3SClient();
        try{
            pop3.connect(server, puerto);
            System.out.println("Conexión realizada al servidor POP3" + server);
            if(!pop3.login(username, password))
                System.out.println("Error al hacer login");
            else{
                POP3MessageInfo[] mensajes = pop3.listMessages();
                if ( mensajes == null)
                    System.out.println("Imposible recuperar mensajes");
                else
                    System.out.println("Nº de mensajes: " + mensajes.length);
               // Recuperarmensajes(mensajes,pop3);
               // Recuperarcabeceras(mensajes,pop3);
                //RecuperarmensajesCompletos(mensajes,pop3);
                pop3.logout();
            }
            pop3.disconnect();
        } catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
    
    private static void Recuperarmensajes(POP3MessageInfo[] men, POP3SClient pop3) throws IOException {
        for (int i = 0; i < men.length; i++) {
            System.out.println("Mensaje: " + (i+1));
            POP3MessageInfo msginfo = men[i];
            System.out.println("Identificador: " + msginfo.identifier + ", Number"
            + msginfo.number + ", Tamaño: " + msginfo.size);
            
            System.out.println("Prueba de listUniqueIdentifier: ");
            POP3MessageInfo pmi = pop3.listUniqueIdentifier(i +1);
            System.out.println("\tIdentificador: " + pmi.identifier +
                    ", Numer: " + pmi.number + ", Tamaño: " + pmi.size);
        }
    }
    
    private static void Recuperarcabeceras (POP3MessageInfo[] men, POP3SClient pop3) throws IOException {
        for (int i = 0; i < men.length; i++) {
            System.out.println("Mensaje: " + (i+1) );
            POP3MessageInfo msginfo = men[i];
            System.out.println("Cabecera del mensaje: ");
            BufferedReader reader = (BufferedReader) pop3.retrieveMessageTop(msginfo.number, 0);
            String linea;
            while ((linea = reader.readLine())!=null){
                System.out.println(linea.toString());
            }
            reader.close();
        }
    }
    private static void RecuperarmensajesCompletos (POP3MessageInfo[] men, POP3SClient pop3) throws IOException {
        for (int i = 0; i < men.length; i++) {
            System.out.println("Mensaje: " + (i+1) );
            POP3MessageInfo msginfo = men[i];
            System.out.println("Mensaje completo: ");
            BufferedReader reader = (BufferedReader) pop3.retrieveMessage(msginfo.number);
            String linea;
            while ((linea = reader.readLine())!=null){
                System.out.println(linea.toString());
            }
            reader.close();
        }
    }
}
