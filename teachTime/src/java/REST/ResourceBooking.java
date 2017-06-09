/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.Booking;
import classes.TeachTimeDataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author david
 */
public class ResourceBooking {
    private final TeachTimeDataLayer datalayer;
    
    ResourceBooking(TeachTimeDataLayer datalayer){
        this.datalayer = datalayer;
    }
    
    //QUI SERVE CONTROLLARE SE È LOGGATO!
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingByUser(@PathParam("user_id") int utente_key) throws SQLException, DataLayerException, NamingException{
        //recupero prenotazioni per la quale non sono stati rilasciati feedback per id studente
        List<Booking> prenotazioni = datalayer.getPrenotazioneByUtente(utente_key);
        
        //evitiamo di restituire i dati superflui riguardanti la ripetizione (es: lista materie)
        return Response.ok(prenotazioni).build();
        
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)    
    public Response postBooking(@Context UriInfo c, Booking prenotazione, @PathParam("SID") String token,
            @PathParam("repetition_id") int ripetizione_key) throws SQLException, DataLayerException, NamingException {
        //inserimento prenotazione alla ripetizione nel sistema
        
        //controllo se il token di sessione è lo stesso dell'utente che si sta prentoando
        if(datalayer.getTokenByUtente(prenotazione.getStudente_key()).equals(token)){
            prenotazione.setRipetizione_key(ripetizione_key);
            prenotazione.setDirty(false);
            datalayer.storePrenotazione(prenotazione);
            URI u = c.getAbsolutePath();
            return Response.created(u).build();    
        }
        return Response.serverError().build();
    }
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putBooking(Booking prenotazione, @PathParam("SID") String token,
            @PathParam("repetition_id") int ripetizione_key) throws SQLException, NamingException, DataLayerException{
        //aggiornamento prenotazione per id 
        prenotazione.setRipetizione_key(ripetizione_key);
        if(datalayer.getTokenByUtente(prenotazione.getStudente_key()).equals(token)){
            if(prenotazione.getVoto()!=-1){
                prenotazione.setStato(2);
            }
            prenotazione.setDirty(true);
            datalayer.storePrenotazione(prenotazione);

            return Response.noContent().build();
        }
        return Response.serverError().build();
    }
    
}
