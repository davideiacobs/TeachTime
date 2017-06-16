/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.Subject;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author iacobs
 */
@Path("categories")
public class ResourceSubject extends TeachTimeDataLayerSupplier{
    
    public ResourceSubject()throws SQLException, NamingException, DataLayerException{
        super();
    }
    
    @GET
    @Path("{id: [0-9]+}/subjects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArgList(@PathParam("id") int n) throws SQLException, NamingException, DataLayerException{
        
        //recuepero lista materie per id categoria
        List<Subject> list = datalayer.getMaterieByCategoria(n);
        return Response.ok(list).build();
    }
    
    /*@POST
    @Path("{id: [0-9]+}/subjects")
    @Consumes(MediaType.APPLICATION_JSON)
     public Response postArgomento(@Context UriInfo c, @PathParam("id") int n, Subject materia) throws SQLException, NamingException, DataLayerException {
            
        
        /*int materia_key = argomento.getMateria_key();
        if(materia_key != n){
            //vaffanculo 
        }*/
        
    /*    TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        datalayer.storeMateria(materia);
        
        URI u = c.getAbsolutePath();
        
        return Response.created(u).build();
    }  
    */
    
    
    /*@GET
    @Path("{id: [0-9]+}/subjects/{id_arg: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArgomento(@PathParam("id") int materia, @PathParam("id_arg") int argomento) throws SQLException, NamingException, DataLayerException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        Subject arg = datalayer.getArgomento(argomento);
        return Response.ok(arg).build();
    }
    */
    
  }  
   
