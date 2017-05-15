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
import java.util.GregorianCalendar;
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
 

@Path("user")
public class ResourceUser {
    @Resource(name = "jdbc/teachtime")
    private DataSource ds;
    
    //Accept: application/json
    @GET
    @Path("{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int n) throws SQLException, NamingException, DataLayerException {
        // Creiamo una classe dati giocattolo 
        /*User u = new User();
            u.setNome("dav");
            u.setCognome("iac");
            u.setEmail("a@a");
            u.setPwd("123");
            u.setCittà("sr");
            u.setImgProfilo("/ameche");
            u.setTelefono("33333");
            u.setTitoloDiStudi("top");
            GregorianCalendar gc = new GregorianCalendar();
            gc.setLenient(false);
            gc.set(GregorianCalendar.YEAR, 1995);
            gc.set(GregorianCalendar.MONTH, 03); //parte da 0
            gc.set(GregorianCalendar.DATE, 27);
            u.setDataDiNascita(gc);
        *///JAX-RS la serializza automaticamente in JSON se Jackson è tra le librerie!
        
          
            TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
            datalayer.init();
            
           User u = datalayer.getUtente(n);
            
       
        
        return Response.ok(u).build();
    }

     
    
}