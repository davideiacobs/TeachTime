/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.Prenotation;
import classes.TeachTimeDataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.net.URI;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author david
 */
public class ResourceBooking {
    @Resource(name = "jdbc/teachtime")
    private DataSource ds;
    
    
   @Path("{studente_id: [0-9]+}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)    
    public Response postBooking(@Context UriInfo c, Prenotation prenotazione, @PathParam("id") int ripetizione_key,
        @PathParam("studente_id") int studente_key) throws SQLException, DataLayerException, NamingException {
        
        TeachTimeDataLayer datalayer = new TeachTimeDataLayer(ds);
        datalayer.init();
        
        prenotazione.setRipetizione_key(ripetizione_key);
        prenotazione.setStudente_key(studente_key);

        datalayer.storePrenotazione(prenotazione);
        
        
        URI u = c.getAbsolutePath();
        return Response.created(u).build();    
}
}
