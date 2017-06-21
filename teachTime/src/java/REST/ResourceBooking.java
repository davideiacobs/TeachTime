/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.Booking;
import classes.Subject;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
public class ResourceBooking extends TeachTimeDataLayerSupplier{
    
    public ResourceBooking() throws SQLException, NamingException, DataLayerException {
        super();
    }
    
   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingByUser(@PathParam("SID") String token, @PathParam("user_id") int utente_key) throws SQLException, DataLayerException, NamingException{
        //controllo se l'utente che richiede di vedere la lista delle ripetizioni per cui non sono 
        //stati rilasciati feedback è loggato 
        if(datalayer.getTokenByUtente(utente_key).equals(token)){
            //recupero prenotazioni per la quale non sono stati rilasciati feedback per id studente
            List<Booking> prenotazioni = datalayer.getPrenotazioneByUtente(utente_key);
        
            //evitiamo di restituire i dati superflui riguardanti la ripetizione (es: lista materie)
            datalayer.destroy();
            return Response.ok(prenotazioni).build();
        }
        datalayer.destroy();
        return Response.serverError().build();
    }
    
    
    @Path("{id: [0-9]+}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooking(@PathParam("privateLesson_id") int ripetizione_key, @PathParam("id") int key) throws DataLayerException, SQLException, NamingException{
        //recupero prentoazione per chiave prenotazione/chiave ripetizione
        Booking b = datalayer.getPrenotazione(key);
        datalayer.destroy();
        if(b!=null){
            return Response.ok(b).build();
        }else{
            return Response.serverError().build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)    
    public Response postBooking(@Context UriInfo c, Booking prenotazione, @PathParam("SID") String token,
            @PathParam("privateLesson_id") int ripetizione_key) throws SQLException, DataLayerException, NamingException {
        //inserimento prenotazione alla ripetizione nel sistema
        
        //recupero l'id dell'utente che si sta prenotando
        int user_key = datalayer.getUtenteByToken(token);
            if(user_key != 0){
                prenotazione.setRipetizione_key(ripetizione_key);
                prenotazione.setStudente_key(user_key);
                List<Subject> materie = datalayer.getMaterieByRipetizione(ripetizione_key);
                //controlliamo se la materia a cui ci si vuole prenotare è presente tra 
                //quelle disponibili nella ripetizione
                if(materie.stream().anyMatch(ti -> ti.getKey() == prenotazione.getMateria_key())){
                    int key = datalayer.storePrenotazione(prenotazione);
                    datalayer.destroy();
                    if(key == 0){
                        //l'utente ha già effettuato la stessa identica prenotazione
                        return Response.serverError().build();
                    }
                    URI u = URI.create(c.getBaseUri().toString()+"privateLessons/"+String.valueOf(ripetizione_key)+"/bookings/"+String.valueOf(key));
                    return Response.created(u).build();    
                }
                datalayer.destroy();
                return Response.serverError().build();
        }
        datalayer.destroy();
        return Response.serverError().build();
    }
    
    
    @Path("{booking_id: [0-9]+}/feedbacks")
    public ResourceFeedback toResourceFeedback() throws SQLException, NamingException, DataLayerException {
        //passaggio alla risorsa feedback che gestisce i feedback
        return new ResourceFeedback();
    }
    
    
}
