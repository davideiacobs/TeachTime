/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.User;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.net.URI;
import java.sql.SQLException;
import javax.naming.NamingException;
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
public class ResourceUser extends TeachTimeDataLayerSupplier{
    
    public ResourceUser() throws SQLException, NamingException, DataLayerException{
        super();
    }
   
    
    @GET
    @Path("{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response getUser(@PathParam("id") int n) throws SQLException, NamingException, DataLayerException {
        //recupero oggetto utente (NO PWD)
        User utente = datalayer.getUtente(n); 
        datalayer.destroy();
        if(utente!=null){
            return Response.ok(utente).build();
        }else{
            return Response.serverError().build();
        }
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postUser(@Context UriInfo c, User utente) throws SQLException, NamingException, DataLayerException {
        
        //inserimento nuovo utente nel sistema
        datalayer.storeUtente(utente);
        //restituiamo la uri per recuperare le info relative all'utente appena creato
        URI u = c.getAbsolutePathBuilder()
                .path(ResourceUser.class, "getUser")
                .build(utente.getKey());
        datalayer.destroy();
        return Response.created(u).build();
    }  
    
    
    @PUT
    @Path("{id: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUser(@PathParam("SID") String session_id, @PathParam("id") int utente_key, User u) throws SQLException, NamingException, DataLayerException {
        
        //aggiornamento utente
        //controllare se session id è uguale a quello dell'utente
        if(datalayer.getTokenByUtente(utente_key).equals(session_id)){
            u.setDirty(true);
            u.setKey(utente_key);
            datalayer.storeUtente(u);
            datalayer.destroy();
            return Response.noContent().build();
        }
        datalayer.destroy();
        return Response.serverError().build();
    }
    
    
    @Path("{user_id: [0-9]+}/bookings")
    public ResourceBooking toResourceBooking() throws SQLException, NamingException, DataLayerException {
        //passaggio alla risorsa prenotazione
        return new ResourceBooking();
    }
    
    @Path("{tutor_id: [0-9]+}/feedbacks")
    public ResourceFeedback toResouceFeedback() throws SQLException, NamingException, DataLayerException {
        //passaggio alla risorsa feedback
        return new ResourceFeedback();
    }
}