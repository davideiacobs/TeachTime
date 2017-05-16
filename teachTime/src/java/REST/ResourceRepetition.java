/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.Repetition;
import classes.TeachTimeDataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
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
@Path("repetitions")
public class ResourceRepetition {
    
    @Resource(name = "jdbc/teachtime")
    private DataSource ds;
     
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response postRepetition(@Context UriInfo c, Repetition ripetizione) throws SQLException, NamingException, DataLayerException {
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        datalayer.storeRepetition(ripetizione);
        
        URI u = c.getAbsolutePathBuilder().path(ResourceRepetition.class, "getRepetition")
                .build(ripetizione.getKey());
        
        return Response.created(u).build();
    }  
     
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRepetition(@QueryParam("tutor") String tutor_key) throws SQLException, NamingException, DataLayerException {
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        /*
         * L'annotazione @QueryParam permette di "iniettare"
         * su un parametro del metodo il valore effettivo del
         * parametro della query string col nome indicato. JAX-RS proverà
         * a convertire il parametro della query string nel tipo richiesto
         * dal metodo. Se il parametro non è spacificato, verrà impostato
         * su null.
         */
        if (tutor_key != null || tutor_key!="") {
            List<Repetition> result = datalayer.getRipetizioniByTutor(Integer.parseInt(tutor_key));
            return Response.ok(result).build();
        } else {
            return Response.serverError().build();
        }
    } 
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRepetition(@QueryParam("city") String città, @QueryParam("subject") String materia
            , @QueryParam("argument") String argomento) throws SQLException, NamingException, DataLayerException {
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        /*
         * L'annotazione @QueryParam permette di "iniettare"
         * su un parametro del metodo il valore effettivo del
         * parametro della query string col nome indicato. JAX-RS proverà
         * a convertire il parametro della query string nel tipo richiesto
         * dal metodo. Se il parametro non è spacificato, verrà impostato
         * su null.
         */
        if (città != null && città != "" && materia != null && materia != ""){
            List<Repetition> result = new ArrayList();
            if(argomento != null && argomento != ""){
                result = datalayer.getRipetizioniByFilter(città, Integer.parseInt(materia), Integer.parseInt(argomento));
            }else{
                result = datalayer.getRipetizioniByFilter(città, Integer.parseInt(materia));
            }
            

            return Response.ok(result).build();
        } else {
            return Response.serverError().build();
        }
    } 
     
     
     
    @GET
    @Path("{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRepetition(@PathParam("id") int n) throws SQLException, NamingException, DataLayerException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        Repetition r = datalayer.getRipetizione(n);
        r.setTutor(datalayer.getUtente(r.getTutor_key()));  //perchè non funge??
        return Response.ok(r).build();
    }
    
    @DELETE
    @Path("{id: [0-9]+}")
    public Response deleteRepetition(@Context UriInfo c) throws SQLException, NamingException, DataLayerException {
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        String k = c.getPath().split("/")[1];
        datalayer.deleteRipetizione(Integer.parseInt(k));
        return Response.noContent().build();
    }
    
    
    @PUT
    @Path("{id: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUser(Repetition r) throws SQLException, NamingException, DataLayerException {
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        r.setDirty(true);
        datalayer.storeRepetition(r);
        //di solito una PUT restituisce NO CONTENT
        return Response.noContent().build();
    }
    
    
}
