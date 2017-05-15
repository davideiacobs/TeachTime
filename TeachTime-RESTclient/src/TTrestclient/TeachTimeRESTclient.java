/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TTrestclient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author iacobs
 */
public class TeachTimeRESTclient {

    private static final String baseURI = "http://localhost:8080/teachTime/MainApplication/rest";
    
    //una entry di esempio, già serializzata in JSON (come farebbe Google Gson, per esempio)
    private static final String dummy_json_entry = "{\"nome\":\"ameche1\",\"cognome\":\"cognome\",\"email\":\"top@top.com\",\"pwd\":\"564645\",\"dataDiNascita\":\"1995-04-27\",\"città\":\"sora\",\"telefono\":3333333333,\"titoloDiStudi\":\"laureato\",\"imgProfilo\":\"path\"}";
    //usiamo Apache Httpclient perchè molto più intuitivo della classi Java.net...
    CloseableHttpClient client = HttpClients.createDefault();

    private void execute_and_dump(HttpRequestBase request) {
        try {
            System.out.println("Metodo: " + request.getMethod());
            System.out.println("URL: " + request.getURI());
            if (request.getFirstHeader("Accept") != null) {
                System.out.println(request.getFirstHeader("Accept"));
            }
            if (request.getMethod().equals("POST")) {
                HttpEntity e = ((HttpPost) request).getEntity();
                System.out.print("Payload: ");
                e.writeTo(System.out);
                System.out.println();
                System.out.println("Tipo payload: " + e.getContentType());
            }
            //eseguiamo la richiesta
            CloseableHttpResponse response = client.execute(request);
            try {
                //preleviamo il contenuto della risposta
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    entity.writeTo(System.out);
                    System.out.println();
                }
                //controlliamo lo status
                //if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("Return status: " + response.getStatusLine().getReasonPhrase() + " (" + response.getStatusLine().getStatusCode() + ")");
                //}

            } finally {
                //chiudiamo la risposta
                response.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(TeachTimeRESTclient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TeachTimeRESTclient instance = new TeachTimeRESTclient();
        
        //1 -- Lista entries (JSON)
        System.out.println("1 -- Utente (JSON)");
        //creiamo la richiesta (GET)
        HttpGet get_request = new HttpGet(baseURI + "/users/1");
        get_request.setHeader("Accept", "application/json");
        instance.execute_and_dump(get_request);

        System.out.println();
        
        
        
        //6 -- Creazione  
        System.out.println("6 -- Creazione entry utente");
        HttpPost post_request = new HttpPost(baseURI + "/users");
        //per una richiesta POST, prepariamo anche il payload specificandone il tipo
        HttpEntity payload = new StringEntity(dummy_json_entry, ContentType.APPLICATION_JSON);
        //e lo inseriamo nella richiesta
        post_request.setEntity(payload);
        instance.execute_and_dump(post_request);

        System.out.println();
    }
    
}
