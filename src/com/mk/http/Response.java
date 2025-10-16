package com.mk.http;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Response{
    private int status;
    private String version;
    private String contentType;
    private Map<String,String> headers;
    private final BufferedOutputStream outputStream;

    public Response(OutputStream outputStream,String version) {
        this.headers = new HashMap<>();
        this.version = version;
        this.outputStream = new BufferedOutputStream(outputStream);
    }
    public void throwNotFound() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(this.version).append(" ").append(404).append("Not Found\r\n");
        this.outputStream.write(sb.toString().getBytes());
        this.outputStream.flush();

    }
    private StringBuilder generateResponse()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.version).append(" ").append(status).append("\r\n");
        sb.append("Content-Type: ").append(contentType).append("\r\n");
        headers.forEach((k,v)->{
            if(!k.equals("Content-Type"))
            {
                sb.append(k).append(":").append("\t").append(v).append("\r\n");
            }

        });
       return sb;


    }
    public Response status(int status)
    {
        this.status = status;
        return this;
    }
    public void send(String data)
    {


        StringBuilder response = this.generateResponse();
        int contentLength = data.getBytes().length;
        response.append("Content-Length: ").append(contentLength).append("\r\n");
        response.append("\r\n");
        response.append(data);

        try {
            outputStream.write(response.toString().getBytes(StandardCharsets.UTF_8));

            outputStream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }


    }

    public void file(File file) throws IOException {
        headers.put("Content-Type", "application/octet-stream");
        headers.put("Content-Length", String.valueOf(file.length()));
        headers.put("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        StringBuilder response = this.generateResponse();
        response.append("\r\n");
        try {
            outputStream.write(response.toString().getBytes(StandardCharsets.UTF_8));
            BufferedInputStream fis = new BufferedInputStream( new FileInputStream(file));
            byte[] buffer = new byte[1024];  // 1 KB buffer
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
               outputStream.write(buffer,0,bytesRead);  // writes to console or output stream
            }
            outputStream.flush();

        } catch (FileNotFoundException e) {
            this.throwNotFound();
            return;
        }

    }

}
