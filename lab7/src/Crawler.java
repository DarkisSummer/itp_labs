import java.net.*;
import java.util.*;
import java.io.*;

public class Crawler {
    static LinkedList<URLDepthPair> found = new LinkedList<>();
    static LinkedList<URLDepthPair> unique = new LinkedList<>();
    private static BufferedReader in;
    private static PrintWriter out;
    static Socket socket;

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("incorrect args input");
            System.exit(1);
        }
        /*
         String url = args[0];
         int depth = Integer.parseInt(args[1]);
         */
        String url = "http://kremlin.ru/";
        int depth = 3;
        try {
            getAllURLs(url, depth);
        }
        catch (IOException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }



    public static void getAllURLs(String url, int maxDepth) throws IOException {
        found.add(new URLDepthPair(0, url));
        while(!found.isEmpty()) {
            URLDepthPair current = found.remove(0);
            if(current.getDepth() < maxDepth) {
                socket = new Socket(current.getHost(), 80);
                socket.setSoTimeout(2000);
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);
                    get(current, out);
                    String readLine;
                    while((readLine = in.readLine()) != null) {
                        StringBuilder link = new StringBuilder();
                        if(readLine.contains(current.prefix) && (readLine.indexOf('"') != -1)) {
                            int index = readLine.indexOf(current.prefix);
                            while(readLine.charAt(index) != '"' && readLine.charAt(index) != ' ') {
                                if(readLine.charAt(index) == '<') {
                                    link.deleteCharAt(link.length() - 1);
                                    break;
                                }
                                else {
                                    link.append(readLine.charAt(index));
                                    index++;
                                }
                            }
                            URLDepthPair pair = new URLDepthPair(current.getDepth() + 1, link.toString());
                            if(!current.url.equals(pair.url))
                                found.add(pair);
                        }
                    }
                    socket.close();
                }
                catch (SocketTimeoutException e) {
                    System.err.println("Socket timed out");
                    socket.close();
                }
            }
            unique.add(current);
        }
        show(unique);
    }


    public static void get(URLDepthPair url,PrintWriter out) throws MalformedURLException {
        out.println("GET " + url.getPath() + " HTTP/1.1");
        out.println("Host: "+ url.getHost());
        out.println("Connection: close");
        out.println();
        out.flush();
    }

    public static void show(LinkedList<URLDepthPair> list) {
        System.out.println("Depth - URL");
        for (URLDepthPair pair: unique)
            System.out.println(pair.getDepth() + " -\t" + pair.getURL());
    }
}