/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.files;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 *
 * @author Commercial_2
 */
public interface UploadFileRS {
    
    
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(MultipartFormDataInput input,@Context HttpHeaders headers);
    
    /**
     * Upload file to temporal directory
     * @param input
     * @param headers
     * @return 
     */
     @POST
    @Path("temporalupload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFiletemporal(MultipartFormDataInput input,@Context HttpHeaders headers);
    
    @GET
    @Path("png/{filename}")
    @Produces("image/png")
    public Response downloadImageFile(@PathParam("filename") String filename);
    
    @GET
    @Path("img/{filename}/{name}")
    @Produces("image/png")
    public Response downloadImageFile2(@PathParam("filename") String filename,@PathParam("name") String name);
    
    @GET
    @Path("pdf/{filename}/{name}")
    @Produces("application/pdf")
    public Response downloadPdfFile(@PathParam("filename") String filename,@PathParam("name") String name);
    
    @GET
    @Path("text/{filename}/{name}")
    @Produces("text/plain")
    public Response downloadTextFile(@PathParam("filename") String filename,@PathParam("name") String name);
    
    @GET
    @Path("file/{filename}/{name}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("filename") String filename,@PathParam("name") String name);
    
    @GET
    @Path("exportbd")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response exportdatabase();
    
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{filename}")
    public void deleteFile(@PathParam("filename") String filename);
    
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("temporal/{filename}")
    public void deleteTempFile(@PathParam("filename") String filename);
}
