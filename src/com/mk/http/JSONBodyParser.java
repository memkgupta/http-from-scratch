package com.mk.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;

public class JSONBodyParser implements Middleware{
    @Override
    public Request process(Request request, Response response) {
        String contentType = request.getHeaders().get("Content-Type");
        if(contentType.equals("application/json")){
            ObjectMapper mapper = new ObjectMapper();
            String json = new String(request.getRawBody());
            try{
                LinkedHashMap obj = mapper.readValue(json,LinkedHashMap.class);
                JSONRequestBody jsonRequestBody = new JSONRequestBody();
                jsonRequestBody.setBody(obj);
                request.setRequestBody(jsonRequestBody);
            }
            catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }

        }

return request;
    }
}
