/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import classes.Prenotation;
import classes.TeachTimeDataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.List;
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
public class ResourceFeedback {
    private final TeachTimeDataLayer datalayer;
    
    ResourceFeedback(TeachTimeDataLayer datalayer){
        this.datalayer = datalayer;
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeedbacksList(@PathParam("tutor_id") int tutor_key) throws DataLayerException{
        //recupero lista di feedback per tutor id     
        List<Prenotation> prenotazioni = this.datalayer.getFeedbacksByTutor(tutor_key);
        
        return Response.ok(prenotazioni).build();
    }
    
    
    @Path("vote")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUserVote(@PathParam("tutor_id") int tutor_key) throws DataLayerException{
        //recupero voto medio tutor per id 
        String voto = this.datalayer.getVoto(tutor_key);
        return Response.ok(voto).build();
    }
}
