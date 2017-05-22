/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.PrivateLesson;
import classes.Subject;
import classes.TeachTimeDataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
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
public class ResourcePrivateLesson {
    
    @Resource(name = "jdbc/teachtime")
    private DataSource ds;

     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response postPrivateLesson(@Context UriInfo c, PrivateLesson ripetizione) throws SQLException, NamingException, DataLayerException {
        //inserimento ripetizione nel sistema
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        for(Subject m : ripetizione.getMaterie()){
            m.setCategoria_key(ripetizione.getCategoria_key());
        }
        ripetizione.setDirty(false);
        datalayer.storeRipetizione(ripetizione);
        
        URI u = c.getAbsolutePathBuilder().path(ResourcePrivateLesson.class, "getPrivateLessonByKey")
                .build(ripetizione.getKey());
        
        return Response.created(u).build();
    }  
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrivateLesson(@QueryParam("city") String città, @QueryParam("category") String categoria
            , @QueryParam("subject") String materia, @QueryParam("tutor_key") String tutor_key) throws SQLException, NamingException, DataLayerException {
        
        //filtro per tutor_ID, per città, per città e categoria o per città categoria e materia.
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
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
     
     
     
    @GET
    @Path("{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrivateLessonByKey(@PathParam("id") int n) throws SQLException, NamingException, DataLayerException{
        //recupero ripetizione per id
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        PrivateLesson r = datalayer.getRipetizione(n);
        r.setTutor(datalayer.getUtente(r.getTutor_key()));
        List<Subject> materie = r.getMaterie();
        r.setMaterie(materie);
        r.setCategoria_key(materie.get(0).getCategoria_key());
        return Response.ok(r).build();
    }
    
    @DELETE
    @Path("{id: [0-9]+}")
    public Response deletePrivateLesson(@Context UriInfo c, @PathParam("id") int ripetizione_key) throws SQLException, NamingException, DataLayerException {
        //cancellazione della ripetizione id dal sistema
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        datalayer.deleteRipetizione(ripetizione_key);
        return Response.noContent().build();
    }
    
    
    @PUT
    @Path("{id: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUser(@PathParam("id") int ripetizione_key, PrivateLesson r) throws SQLException, NamingException, DataLayerException {
        //aggiornamento info relative alla ripetizione id
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        r.setKey(ripetizione_key);
        r.setDirty(true);
        datalayer.storeRipetizione(r);
        //di solito una PUT restituisce NO CONTENT
        return Response.noContent().build();
    }
    
    @Path("{repetition_id: [0-9]+}/bookings")
    public ResourceBooking toResourceBooking() throws SQLException, NamingException, DataLayerException {
        //passaggio alla risorsa bookings che gestisce le prenotazioni
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        return new ResourceBooking(datalayer);
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
