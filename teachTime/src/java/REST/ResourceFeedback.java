/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.Booking;
import classes.TeachTimeDataLayer;
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
 * @author david
 */
public class ResourceFeedback extends TeachTimeDataLayerSupplier {
    
    public ResourceFeedback() throws SQLException, NamingException, DataLayerException{
        super();
    }
    
    //testato
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeedbacksList(@PathParam("tutor_id") int tutor_key) throws DataLayerException{
        //recupero lista di feedback per tutor id     
        List<Booking> prenotazioni = datalayer.getFeedbacksByTutor(tutor_key);
        datalayer.destroy();
        if(prenotazioni!=null){
            return Response.ok(prenotazioni).build();
        }
        return Response.serverError().build();
    }
    
    
    @Path("avg")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUserVote(@PathParam("tutor_id") int tutor_key) throws DataLayerException{
        //recupero voto medio tutor per id 
        String voto = datalayer.getVoto(tutor_key);
        datalayer.destroy();
        if(!voto.equals("")){
            return Response.ok(voto).build();
        }
        return Response.serverError().build();
    }
}
