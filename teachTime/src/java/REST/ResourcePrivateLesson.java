/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;


import classes.PrivateLesson;
import classes.Subject;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author iacobs
 */
@Path("privateLessons")
public class ResourcePrivateLesson extends TeachTimeDataLayerSupplier {
    
    public ResourcePrivateLesson() throws SQLException, NamingException, DataLayerException{
        
        super();
    }
    
    
     //testato
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response postPrivateLesson(@PathParam("SID") String session_id, @Context UriInfo c, PrivateLesson ripetizione) throws SQLException, NamingException, DataLayerException {
        //inserimento ripetizione nel sistema
        
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
   
    /*@GET 
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrivateLessonsByUser(@PathParam("SID") String session_id) throws DataLayerException{
         int user_key = datalayer.getSessionByToken(session_id).getUtente_key();
        List<PrivateLesson> l = datalayer.getRipetizioniByTutor(user_key);
        for(PrivateLesson r : l){ 
                List<Subject> materie = r.getMaterie();
                r.setMaterie(materie);
                r.setCategoria_key(materie.get(0).getCategoria_key());
            }
        return Response.ok(l).build();
    }*/
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrivateLessonsLogged(@PathParam("SID") String session_id, @QueryParam("city") String città, @QueryParam("category") String categoria
            , @QueryParam("subject") String materia, @QueryParam("tutor_key") String tutor_key) throws SQLException, NamingException, DataLayerException {
       
        List<PrivateLesson> result = null;
        int user_key;
        if(session_id!=null){
            //LOGGATO
            user_key = datalayer.getSessionByToken(session_id).getUtente_key();
            if((!"".equals(città) && città != null) || (!"".equals(tutor_key) && tutor_key != null)){
                if(materia != null && !"".equals(materia)){
                    //filtro per città, categoria e materia
                    result = datalayer.getRipetizioniByMateriaLogged(città, Integer.parseInt(materia),user_key);
                }else{
                    if(categoria != null && !"".equals(categoria)){
                        result = datalayer.getRipetizioniByCategoriaLogged(città, Integer.parseInt(categoria),user_key);   
                    }else{
                        //filtro per città
                        result = datalayer.getRipetizioniByCittàLogged(città, user_key); 
                    }
                }
            }else{
                result = datalayer.getRipetizioniByTutor(Integer.parseInt(tutor_key));
            }
        }else{
            //NON LOGGATO
        //filtro per tutor_ID, per città, per città e categoria o per città categoria e materia.
            if((!"".equals(città) && città != null) || (!"".equals(tutor_key) && tutor_key != null)){
                if(materia != null && !"".equals(materia)){
                    //filtro per città, categoria e materia
                    result = datalayer.getRipetizioniByMateria(città, Integer.parseInt(materia));
                }else{
                    if(categoria != null && !"".equals(categoria)){
                        result = datalayer.getRipetizioniByCategoria(città, Integer.parseInt(categoria));   
                    }else{
                        //filtro per città
                        result = datalayer.getRipetizioniByCittà(città); 
                    }
                }
            }else{
                
                result = datalayer.getRipetizioniByTutor(Integer.parseInt(tutor_key));
            }
           
        
        } 
        if(result!=null){
            for(PrivateLesson r : result){ 
                r.setTutor(datalayer.getUtente(r.getTutor_key()));
                List<Subject> materie = r.getMaterie();
                r.setMaterie(materie);
                r.setCategoria_key(materie.get(0).getCategoria_key());
            }
            return Response.ok(result).build();            
        }else {
            return Response.serverError().build();
        }
        
    }  
     
     
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrivateLessons(@QueryParam("city") String città, @QueryParam("category") String categoria
            , @QueryParam("subject") String materia, @QueryParam("tutor_key") String tutor_key) throws SQLException, NamingException, DataLayerException {
        
        //filtro per tutor_ID, per città, per città e categoria o per città categoria e materia.
        
        if ((città != null && !"".equals(città)) || (!"".equals(tutor_key) && tutor_key != null) ){            
            List<PrivateLesson> result;
            if(!"".equals(città) && città != null){
                if(materia != null && !"".equals(materia)){
                    //filtro per città, categoria e materia
                    result = datalayer.getRipetizioniByMateria(città, Integer.parseInt(materia));
                }else{
                    if(categoria != null && !"".equals(categoria)){
                        //filtro per città e categoria
                        result = datalayer.getRipetizioniByCategoria(città, Integer.parseInt(categoria));
                    }else{
                        //filtro per città
                        result = datalayer.getRipetizioniByCittà(città);
                    }
                }
            }else{
                result = datalayer.getRipetizioniByTutor(Integer.parseInt(tutor_key));
            }
            for(PrivateLesson r : result){ 
                r.setTutor(datalayer.getUtente(r.getTutor_key()));
                List<Subject> materie = r.getMaterie();
                r.setMaterie(materie);
                r.setCategoria_key(materie.get(0).getCategoria_key());
            }return Response.ok(result).build();            
        } else {
            return Response.serverError().build();
        }
    } 
    
    
    //testato
    @GET
    @Path("{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrivateLessonByKey(@PathParam("id") int n) throws SQLException, NamingException, DataLayerException{
        //recupero ripetizione per id
        
        PrivateLesson r = datalayer.getRipetizione(n);
        r.setTutor(datalayer.getUtente(r.getTutor_key()));
        List<Subject> materie = r.getMaterie();
        r.setMaterie(materie);
        r.setCategoria_key(materie.get(0).getCategoria_key());
        return Response.ok(r).build();
    }
    
    //testato
    @DELETE
    @Path("{id: [0-9]+}")
    public Response deletePrivateLesson(@Context UriInfo c, @PathParam("SID") String token, @PathParam("id") int ripetizione_key) throws SQLException, NamingException, DataLayerException {
        //cancellazione della ripetizione id dal sistema
        if(datalayer.getTokenByUtente(datalayer.getRipetizione(ripetizione_key).getTutor_key()).equals(token)){
            datalayer.deleteRipetizione(ripetizione_key);
            return Response.noContent().build();
        }
        return Response.serverError().build();
       
    }
    
    //testato
    @PUT
    @Path("{id: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putPrivateLesson(@PathParam("SID") String token, @PathParam("id") int ripetizione_key, PrivateLesson r) throws SQLException, NamingException, DataLayerException {
        /******NB******
        si DEVONO indicare le materie anche se non ne sono state aggiunte altre
        e si deve indicare la categoria_key 
        (alternativa: r.setCategoria_Key(datalayer.getRipetizione(ripetizione_key))
        ***************/

        //aggiornamento info relative alla ripetizione id
        //recuperiamo la chiave del tutor e verifichiamo se il token di sessione corrisponde
        PrivateLesson c = datalayer.getRipetizione(ripetizione_key);
        int tutor_key = c.getTutor_key();
        if(datalayer.getTokenByUtente(tutor_key).equals(token)){
            r.setDirty(true);
            r.setKey(ripetizione_key);
            r.setTutor_key(tutor_key);            
            datalayer.storeRipetizione(r);
            return Response.noContent().build();
        }
        return Response.serverError().build();
    }
    
    /*@Path("{privateLesson_id: [0-9]+}/bookings/{id: [0-9]+}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooking(@PathParam("privateLesson_id") int ripetizione_key, @PathParam("id") int key) throws DataLayerException, SQLException, NamingException{
        
        Booking b = datalayer.getPrenotazione(key);
        //b.setRipetizione(datalayer.getRipetizione(ripetizione_key));
        return Response.ok(b).build();
    }*/
    
    @Path("{privateLesson_id: [0-9]+}/bookings")
    public ResourceBooking toResourceBooking() throws SQLException, NamingException, DataLayerException {
        //passaggio alla risorsa bookings che gestisce le prenotazioni
        return new ResourceBooking();
    }
    
    /*@Path("{id: [0-9]+}/bookings")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)    
    public Response postBooking(@Context UriInfo c, Prenotation prenotazione, @PathParam("id") int ripetizione_key) throws SQLException, DataLayerException, NamingException {
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        prenotazione.setRipetizione_key(ripetizione_key);
        datalayer.storePrenotazione(prenotazione);
        
        
        URI u = c.getAbsolutePath();
        return Response.created(u).build();    
    }
    
    @Path("{id: [0-9]+}/bookings")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putBooking(Prenotation prenotazione, @PathParam("id") int ripetizione_key) throws SQLException, NamingException, DataLayerException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        prenotazione.setRipetizione_key(ripetizione_key);
        prenotazione.setDirty(true);
        datalayer.storePrenotazione(prenotazione);
        
        return Response.noContent().build();
    }
    */
}
