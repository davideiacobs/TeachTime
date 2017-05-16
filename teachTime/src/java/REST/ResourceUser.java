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
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        User utente = datalayer.getUtente(n);
            
        return Response.ok(utente).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postUser(@Context UriInfo c, User utente) throws SQLException, NamingException, DataLayerException {
            
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        /* 
         * Attenzione: per poter essere deserializzato l'oggetto
         * deve essere dotato di un construttore di default 
         * (senza parametri), oltre ovviamente ad avere campo
         * mappabil su quelli del JSON del payload.
         */
        datalayer.storeUser(utente);
        URI u = c.getAbsolutePathBuilder()
                .path(ResourceUser.class, "getUser")
                .build(utente.getKey());

        return Response.created(u).build();
    }  
    
    
    @PUT
    @Path("{id: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUser(User u) throws SQLException, NamingException, DataLayerException {
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        u.setDirty(true);
        datalayer.storeUser(u);
        //di solito una PUT restituisce NO CONTENT
        return Response.noContent().build();
    }
}