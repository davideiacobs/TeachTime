/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.PrivateLesson;
import classes.Session;
import classes.Subject;
import classes.TeachTimeDataLayer;
import classes.User;
import classes.Utility;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.net.URI;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author iacobs
 */
@Path("auth")
public class ResourceAuth {
    
    @Resource(name = "jdbc/teachtime")
    private DataSource ds;
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAuth(User u) throws SQLException, NamingException, DataLayerException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        User utente = datalayer.getUtenteByMail(u.getEmail());
        if(utente != null && utente.getPwd().equals(u.getPwd())){
            Session session = new Session(); 
            session.setUtente(utente);
            session.setToken(Utility.generateToken());
            datalayer.storeSessione(session);
            return Response.ok(session.getToken()).build();
        } else {
            return Response.serverError().build();
  
        }
    }
     
    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAuthDestroy(Session sessione) throws SQLException, NamingException, DataLayerException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        datalayer.deleteSessione(sessione.getToken());
        
        return Response.noContent().build();   
    }
    
    //RIMUOVERE QUELLA CHE NON SERVE IN RESOURCE USER
    @PUT
    @Path("{SID: [0-9]+}/users/{id: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUser(@PathParam("SID") String session_id, @PathParam("id") int utente_key, User u) throws SQLException, NamingException, DataLayerException {
        //aggiornament info utente
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        //controllare se session id è uguale a quello dell'utente
        //if(datalayer.getTokenByUtente(utente_key).equals(String.valueOf(session_id))){
        if(datalayer.getTokenByUtente(utente_key).equals(session_id)){
            u.setDirty(true);
            u.setKey(utente_key);
            datalayer.storeUtente(u);
            return Response.noContent().build();
        }
        return Response.serverError().build();
    }
    
    //RIMUOVERE QUELLA CHE NON SERVE IN RESOURCE PRIVATE LESSON
    @Path("{SID: [0-9]+}/privateLessons") 
    @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response postPrivateLesson(@PathParam("SID") String session_id, @Context UriInfo c, PrivateLesson ripetizione) throws SQLException, NamingException, DataLayerException {
        //inserimento ripetizione nel sistema
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        //recupero l'utente relativo alla sessione
        int user_key = datalayer.getSessionByToken(session_id).getUtente_key();
        
        for(Subject m : ripetizione.getMaterie()){
            m.setCategoria_key(ripetizione.getCategoria_key());
        }
        ripetizione.setTutor_key(user_key);
        ripetizione.setDirty(false);
        datalayer.storeRipetizione(ripetizione);
        
        URI u = URI.create(c.getBaseUri().toString()+"privateLessons/"+String.valueOf(ripetizione.getKey()));

        return Response.created(u).build();
    }  
    
}
