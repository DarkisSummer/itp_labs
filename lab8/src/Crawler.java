import java.net.*;
import java.util.*;
import java.io.*;

public class Crawler {
    public static void main(String[] args) {
        if(args.length != 3) {
            System.out.println("incorrect args input");
            System.exit(1);
        }
        /**
         String url = args[0];
         int depth = Integer.parseInt(args[1])
         int threads = Integer.parseInt(args[2]);
         **/
        String url = "http://kremlin.ru/";
        int depth = 2;
        int threads = 3;
        URLPool pool = new URLPool(depth);
        pool.add(new URLDepthPair(0, url));
        for(int i=0; i < threads; i++) {
            CrawlerTask crawlerTask = new CrawlerTask(pool);
            Thread thread = new Thread(crawlerTask);
            thread.start();
        }
        while (threads != pool.getWaiters()) {
            try {
                Thread.sleep(500);
                show(pool.getUnique());
            }
            catch (InterruptedException e) {
                System.err.println("InterruptedException: " + e.getMessage());
            }
            catch (NullPointerException e) {
                System.err.println(e.getMessage());
            }
            System.exit(0);
        }
    }


    public static void show(LinkedList<URLDepthPair> list) {
        System.out.println("Depth - URL");
        for (URLDepthPair pair: list)
            System.out.println(pair.getDepth() + " -\t" + pair.getURL());
    }

    /*
    public static void getAllURLs(String url, int maxDepth) throws IOException {
        found.add(new URLDepthPair(0, url));
        while(!found.isEmpty()) {
            URLDepthPair current = found.remove(0);
            if(current.getDepth() < maxDepth) {
                Socket socket;
                socket = new Socket(current.getHost(), 80);
                socket.setSoTimeout(2000);
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);
                    get(current, out);
                    String line;
                    while(in.readLine() != null) {
                        line = in.readLine();
                        StringBuilder link = new StringBuilder();
                        if((line.indexOf(current.prefix) != -1) && (line.indexOf('"') != -1)) {
                            int index = line.indexOf(current.prefix);
                            while(line.charAt(index) != '"' && line.charAt(index) != ' ') {
                                if(line.charAt(index) == '<') {
                                    link.deleteCharAt(link.length() - 1);
                                    break;
                                }
                                else {
                                    link.append(line.charAt(index));
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
    */
}