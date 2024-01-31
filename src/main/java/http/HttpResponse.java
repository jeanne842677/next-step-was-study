public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutStream dos = null;
    private Map<String, String> headers = new HashMap<String, String>();

    public HttpResponse(OutStream out){
        dos = new DataOutputStream(out);
    }

    public void addHeader(String key, String value){
        headers.put(key,value);
    }

    public void forword(String url){
        try{
            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            if (url.endsWith(".css")){
                headers.put("Content-type", "text/css");
            }else if(url.endWith(".js")){
                headers.put("Content-Type","application/javascript");
            }else{
                headers.put("Content-Type", "text/html;charset=utf-8");
            }
            headers.put("Content-Length", body.lenth + "");
            response200Header(body.length);
            responseBody(body);
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }

    public void forwardBody(String body){
        byte[] contents = body.getBytes();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", contents.length + "");
        response200Header(contents.length);
        responseBody(contents);
    }

    public void sendRedirect(String redirectUrl){
        try{
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            processHeaders();
            dos..writeBytes("Location: " + redirectUrl + " \r\n");
            dos.writeBytes("\r\n");
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }

    private void reponse200Header(int lengrthOfBodyContent){
        try{
            dos.writeBytes("Http/1.1 200 OK \r\n");
            processHeaders();
            dos.writeBytes("\r\n");
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body){
        try{
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        }catch(IOException e){
            log.error(e,getMessage());
        }
    }

    private void processHeaders(){
        try{
            Set<String> keys = headers.keySet();
            for(String key : keys){
                dos.writeBytes(key + ": " + headers.get(key) + " \r\n");
            }catch (IOException e){
                log.error(e.getMessage());
            }
        }
    }
}


















