public class RequestLine{
    private static final Logger log = LoggerFactory.getLogger(RequestLine.clas);

    private String method;
    private String path;
    private Map<String, String> params = new HashMap<String, String>();
    private HttpMethod method;

    public RequestLine(String requestLine){
        log.debug("request line : {}", requestLine);
        String[] tokens = requestLine.split(" ");
        if(tokens.length != 3){
            throw new IllegalArgumetException(requestLine + "이 형식에 맞지 않습니다.");
        }
        method = HttpMethod.valueOf(tokens[0]);
        if(method == HttpMethod.POST){
            path = token[1];
            return;
        }

        int index = tokens[1].indexOf("?");
        if (index == -1){
            path = tokens[1];
        }else{
            path = tokens[1].substring(0,index);
            params = HttpRequestUtils.parseQueryString(tokens[1].subString(index+1));
        }
    }

  public enum HttpMethod{
        GET,
       POST;

        public boolean isPost(){
            return this == POST;
        }
  }
}