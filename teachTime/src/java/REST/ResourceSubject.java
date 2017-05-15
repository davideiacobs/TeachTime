/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.Argument;
import classes.TeachTimeDataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
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
 * @author iacobs
 */
@Path("subjects")
public class ResourceSubject {
    
    @Resource(name = "jdbc/teachtime")
    private DataSource ds;
    
    
    @GET
    @Path("{id: [0-9]+}/arguments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArgList(@PathParam("id") int n) throws SQLException, NamingException, DataLayerException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        List<Argument> list = datalayer.getArgomentiByMateria(n);
        return Response.ok(list).build();
    }
    
    @POST
    @Path("{id: [0-9]+}/arguments")
    @Consumes(MediaType.APPLICATION_JSON)
     public Response postArgomento(@Context UriInfo c, @PathParam("id") int n, Argument argomento) throws SQLException, NamingException, DataLayerException {
            
        
        /*int materia_key = argomento.getMateria_key();
        if(materia_key != n){
            //vaffanculo 
        }*/
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        /* 
         * Attenzione: per poter essere deserializzato l'oggetto
         * deve essere dotato di un construttore di default 
         * (senza parametri), oltre ovviamente ad avere campo
         * mappabil su quelli del JSON del payload.
         */
        datalayer.storeArgument(argomento);
        
        URI u = c.getAbsolutePathBuilder()
                .path(ResourceSubject.class, "getArgomento")
                .build(argomento.getMateria_key(),argomento.getKey());

        return Response.created(u).build();
    }  

    
    
    @GET
    @Path("{id: [0-9]+}/arguments/{id_arg: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArgomento(@PathParam("id") int materia, @PathParam("id_arg") int argomento) throws SQLException, NamingException, DataLayerException{
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        Argument arg = datalayer.getArgomento(argomento);
        return Response.ok(arg).build();
    }
  
    
  }  
   
