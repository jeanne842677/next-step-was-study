import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.nio.file.Files;
import model.User;
public class RequestHandler extends Thread{
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket){
        this.connection = connectionSocket;
    }

    public void run(){
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());


        try(InputStream in = connection.getInputStream();
            OutpuStream out = connection.getOutputStream()){
            //요구사항 1.
            //http://localhost:8080/index.html로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
            //1-1 InputStream을 한 줄 단위로 읽기 위해 BufferedReader를 생성한다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            //BufferedReader.readLine() 메소드를 활용해 라인별로 HTTP 요청 정보를 읽는다.
            String line = br.readLine();
            log.debug("request line : {}", line);

            //1-2 HTTP 요청 정보의 첫 번째 라인에서 요청 URL(/index.html)을 추출한다.
            //String[] tokens = line.split(" "); 를 활용해 문자열을 분리할 수 있다.
            //구현은 별도의 유틸 클래스를 만들고 단위 테스트를 만들어 진행하면 편하다.
            String[] tokens = line.split(" ");
            boolean logined = false;
            int contentLength = 0;
            //1-2 Http 요청 정보 전체를 출력한다.
            //헤더 마지막은 while(!"".equals(line)) {}로 확인 가능하다.
            //line이 null값인 경우에 대한 예외 처리도 해야한다. 그렇지 않을 경우 무한 루프에 빠진다. (if (line == null) { return;})
            while (!line.equals("")){
                log.debug("header : {}", line);
                line = br.readLine();
                if (line.contains("Cookie")){
                    logined = isLogin(line);
            }

            String url = tokens[1];
            if(("/user/create".equals(url)){
                String body = IOUtils.reaData(br, contentLength);
                Map<String, String> params =
                            HttpRequestUtills.parseQueryString(queryString);
                User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
                log.debug("User : {}",user);
                //url = "/index.html";
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html");
            }else if ("/uer/login".equls(url)){
                if (!logined){
                    responseResource(out, "/user/login.html");
                    return;
                }
                Collection<User> users = DataBase.findAll();
                StringBuilder sb = new StringBuilder();
                sb.appen("<table border='1'>");
                for(User user : users){
                    sb.append("<tr>");
                    sb.append("<td>" + user.getUserId() + "</td>");
                    sb.append("<td>" + user.getName() + "</td>");
                    sb.append("<td>" + user.getEmail() + "</td>");
                    sb.append("</tr>");
                }
                sb.append("</table>");
                byte[] body = sb.toString().getBytes();
                DataOutputStream dos = new DataOutputStream(out);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }else if(url.endsWith(".css")){
                    DataOutputStream dos = new DataOutputStream(out);
                    byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
                    response200CssHeader(dos, body.length);
                    responseBody(dos, body);
            }else{
                responseResource(out, url);
            }catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        private void response200CssHeader(DataOutputStream dos, int lengthOfBodyContent){
                try{
                    dos.writeBytes("HTTP/1.1 200 OK \r\n");
                    dos.writeBytes("Content-Type: text/css\r\n");
                    dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
                    dos.writeBytes("\r\n");
                } catch (IOException e){
                    log.error(e.getMessage());
                }
            }

        private boolean isLogin(String line){
                String[] headerTokens = line.split(":");
                Map<String, String> cookies =
                        HttpRequestUtils.parseCookies(headerTokens[1].trim());
                String value = cookies.get("logined");
                if (value == null){
                    return false;
                }
                return Boolean.parseBoolean(value);
            }

        private void response302Header(DataOutputStream dos, String url){
            try{
                dos.writeBytes("Http/1.1 302 Redirect \r\n");
                dis.writeBytes("\r\n");
            }catch (IOException e){
                log.error(e.getMessage());
            }
        }

        private int getContentLength(String line){
            String[] headerTokens = line.split(":");
            return Integer.parseInt(headerTokens[1].trim());
        }
    }
}