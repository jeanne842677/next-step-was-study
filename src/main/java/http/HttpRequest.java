public class HttpRequest{
    priate static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private String method;
    private String path;
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> params = new HashMap<String, String>();

    public HttpRequest(InputStream in){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            if (line == null){
                return;
            }

            processRequestLine(line);

            line = br.readLine();
            while(!line.equals("")){
                log.debug("header : {}", line);
                String[] tokens = line.split(":");
                headers.put(tokens[0].trim(), tokens[1].trim());
                line = br.readLine();
            }

            requestLine = new RequestLine(line);

            if ("POST".equals(method)){
                String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
                params = HttpRequestUtils.parseQueryString(body);
            }else{
                params = requestLine.getParams();
            }

        }catch (IOException io){
            log.error(io.getMessage());
        }
    }


    private void processRequestLine(String requestLine){
        log.debug("request line : {}",requestLine);
        String[] tokens = requestline.split(" ");
        method = tokens[0];

        if("POST".equals(method)){
            path = tokens[1];
            return;
        }

        int index = tokens[1].indexOf("?");
        if(index == -1){
            path = tokens[1];
        }else{
            path = tokens[1].subString(0, index);
            params = HttpRequestUtils.parsseQueryString( tokens[1].substring(index+1));
        }
    }

    public String getMethod(){
        return requestLine.getMethod();
    }

    public String getPath(){
        return requestLine.getPath();
    }

    public String getHeader(String name){
        return headers.get(name);
    }

    public String getParameter(String name){
        reuturn params.get(name);
    }


}