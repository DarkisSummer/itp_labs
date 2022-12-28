import java.io.*;
import java.net.*;

public class CrawlerTask implements Runnable{
    URLPool pool;
    public String prefix = "http://";
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    public CrawlerTask(URLPool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        while (true) {
            URLDepthPair pair = pool.get();
            try {
                socket = new Socket(pair.getHost(), 80);
                socket.setSoTimeout(1000);
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    get(pair, out);
                    String readLine;
                    StringBuilder link = new StringBuilder();
                    while ((readLine = in.readLine()) != null) {
                        if (readLine.contains(prefix) && readLine.indexOf('"') != -1) {
                            link.setLength(0);
                            int i = readLine.indexOf(prefix);
                            while (readLine.charAt(i) != '"' & readLine.charAt(i) != ' ') {
                                if (readLine.charAt(i) == '<') {
                                    link.deleteCharAt(link.length() - 1);
                                    break;
                                } else {
                                    link.append(readLine.charAt(i));
                                    i++;
                                }
                            }
                            URLDepthPair newPair = new URLDepthPair(pair.getDepth() + 1, link.toString());
                            pool.add(newPair);
                        }
                    }
                    socket.close();
                }
                catch (SocketTimeoutException e) {
                    System.err.println("Socked timed out");
                    socket.close();
                }
            }
            catch (IOException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
    }

    public static void get(URLDepthPair url,PrintWriter out) throws MalformedURLException {
        out.println("GET " + url.getPath() + " HTTP/1.1");
        out.println("Host: " + url.getHost());
        out.println("Connection: close");
        out.println();
        out.flush();
    }
}
