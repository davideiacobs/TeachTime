/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.TeachTimeDataLayer;
import classes.User;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.net.URI;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author iacobs
 */
 

@Path("users")
public class ResourceUser {
    
    @Resource(name = "jdbc/teachtime")
    private DataSource ds;
    
    //Accept: application/json
    @GET
    @Path("{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response getUser(@PathParam("id") int n) throws SQLException, NamingException, DataLayerException {
        //recupero utente per id
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        User utente = datalayer.getUtente(n);
            
        return Response.ok(utente).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postUser(@Context UriInfo c, User utente) throws SQLException, NamingException, DataLayerException {
        //inserimento utente nel sistema
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        datalayer.storeUser(utente);
        //restituiamo la uri per recuperare le info relative all'utente appena creato
        URI u = c.getAbsolutePathBuilder()
                .path(ResourceUser.class, "getUser")
                .build(utente.getKey());

        return Response.created(u).build();
    }  
    
    
    @PUT
    @Path("{id: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUser(@PathParam("id") int utente_key, User u) throws SQLException, NamingException, DataLayerException {
        //aggiornament info utente
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        u.setDirty(true);
        u.setKey(utente_key);
        datalayer.storeUser(u);
        return Response.noContent().build();
    }
    
    @Path("{user_id: [0-9]+}/bookings")
    public ResourceBooking toResourceBooking() throws SQLException, NamingException, DataLayerException {
        //passaggio alla risorsa bookings che gestisce le prenotazioni
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        return new ResourceBooking(datalayer);
    }
    
    @Path("{tutor_id: [0-9]+}/feedbacks")
    public ResourceFeedback toResouceFeedback() throws SQLException, NamingException, DataLayerException {
        //passaggio alla risorsa feedbacks che gestisce i feedback
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        return new ResourceFeedback(datalayer);
    }
    
    /*@Path("{user_id: [0-9]+}/bookings")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingByUser(@PathParam("user_id") int utente_key) throws SQLException, DataLayerException, NamingException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        List<Prenotation> prenotazioni = datalayer.getPrenotazioneByUtente(utente_key);
        
        return Response.ok(prenotazioni).build();
        
    }*/
    
    
    /*@Path("{tutor_id: [0-9]+}/feedbacks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeedbacksList(@PathParam("tutor_id") int tutor_key) throws SQLException, NamingException, DataLayerException, NamingException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        List<Prenotation> prenotazioni = datalayer.getFeedbacksByTutor(tutor_key);
        
        return Response.ok(prenotazioni).build();
    }
    
    @Path("{tutor_id: [0-9]+}/feedbacks/vote")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUserVote(@PathParam("tutor_id") int tutor_key) throws SQLException, NamingException, DataLayerException, NamingException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        String voto = datalayer.getVoto(tutor_key);
        return Response.ok(voto).build();
    }*/
}