/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

/**
 *
 * @author david
 */
public class UploadFoto extends HttpServlet {

    
     private void upload_foto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        
        Part foto_to_upload = request.getPart("foto-profilo");
        
        //File uploaded_foto = null;
        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();
        /*if(foto_to_upload != null && foto_to_upload.getSize() > 0){
            uploaded_foto = File.createTempFile("foto_profilo", ".jpg", new File(getServletContext().getInitParameter("fotoProfilo.directory")));
             String digest_foto = getDigest(foto_to_upload, uploaded_foto);
        }
        response.setContentType("text/plain"); 
        response.setCharacterEncoding("UTF-8"); 
        PrintWriter out = response.getWriter();
        try {
            out.println(uploaded_foto.getName());
        } finally {
            out.close();
        }*/
        String name = (String.valueOf((long) (Math.random()*1000000000)))+".jpg";
        out = new FileOutputStream(new File(getServletContext().getInitParameter("foto.directory")+File.separator+name));
        filecontent = foto_to_upload.getInputStream();
        int read = 0;
        final byte[] bytes = new byte[1024];

        while ((read = filecontent.read(bytes)) != -1) {
           out.write(bytes, 0, read);
        }
        
        out.close();
        filecontent.close();
        if (writer != null) {
            writer.close();
        }
        response.setContentType("text/plain"); 
        response.setCharacterEncoding("UTF-8"); 
        try (PrintWriter out2 = response.getWriter()) {
            out2.println(name);
        }
     
     }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        try {
            upload_foto(request, response);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            //action_error(request, response);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
