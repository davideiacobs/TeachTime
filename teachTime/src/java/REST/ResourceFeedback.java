/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.Booking;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author david
 */
public class ResourceFeedback extends TeachTimeDataLayerSupplier {
    
    public ResourceFeedback() throws SQLException, NamingException, DataLayerException{
        super();
    }
    
    //testato
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeedbacksList(@PathParam("tutor_id") int tutor_key) throws DataLayerException{
        //recupero lista di feedback per tutor id     
        List<Booking> prenotazioni = datalayer.getFeedbacksByTutor(tutor_key);
        datalayer.destroy();
        if(prenotazioni!=null){
            return Response.ok(prenotazioni).build();
        }
        return Response.serverError().build();
    }
    
    
    @Path("avg")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUserVote(@PathParam("tutor_id") int tutor_key) throws DataLayerException{
        //recupero voto medio tutor per id 
        String voto = datalayer.getVoto(tutor_key);
        String voto2 = String.valueOf(voto.charAt(0));
        
        datalayer.destroy();
        if(!voto.equals("")){
            return Response.ok(voto2).build();
        }
        return Response.ok(0).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postFeedback(Booking prenotazione,@PathParam("booking_id") int key, @PathParam("SID") String token,
            @PathParam("privateLesson_id") int ripetizione_key) throws SQLException, NamingException, DataLayerException{
        //inserimento feedback nel sistema (aggiornamento prenotazione) 
        
        //BASTA INDICARE VOTO E RECENSIONE
        prenotazione.setKey(key);
        prenotazione.setRipetizione_key(ripetizione_key);
        int user_key = datalayer.getUtenteByToken(token);
        if(user_key!=0){
            if(prenotazione.getVoto()!=-1){
                prenotazione.setStato(2);
            }
            prenotazione.setDirty(true);
            datalayer.storePrenotazione(prenotazione);
            datalayer.destroy();
            return Response.noContent().build();
        }
        datalayer.destroy();
        return Response.serverError().build();
    }
    
    
    
    
    
    
}
