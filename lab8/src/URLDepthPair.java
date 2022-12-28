import java.net.*;

public class URLDepthPair {
    private int depth;
    private String url;
    public URLDepthPair(int depth, String url) {
        this.depth = depth;
        this.url = url;
    }

    public int getDepth() {
        return depth;
    }

    public String getURL() {
        return url;
    }

    public String getHost() throws MalformedURLException {
        URL host = new URL(url);
        return host.getHost();
    }

    public String getPath() throws MalformedURLException {
        URL path = new URL(url);
        return path.getPath();
    }

    public String toString() {
        return "Depth + URL: \n" + Integer.toString(depth) + '\t' + url;
    }
}
