import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/scrape")
public class ScrapeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = request.getParameter("url");
        String[] options = request.getParameterValues("option");

        // Run the scraper
        WebScraper.BBCScraping scraper = new WebScraper.BBCScraping();
        scraper.scrape(url);

        // Set up HTML response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Scraped Data</title></head><body>");
        out.println("<h2>Scraped Data from " + url + "</h2>");
        out.println("<table border='1'>");

        if (options != null) {
            for (String option : options) {
                switch (option) {
                    case "title":
                        out.println("<tr><th>Title</th><td>" + (scraper.title != null ? scraper.title : "") + "</td></tr>");
                        break;
                    case "headings":
                        out.println("<tr><th>Headings</th><td><pre>" + (scraper.headings != null ? scraper.headings : "") + "</pre></td></tr>");
                        break;
                    case "links":
                        out.println("<tr><th>Links</th><td><pre>" + (scraper.links != null ? scraper.links : "") + "</pre></td></tr>");
                        break;
                    case "headlines":
                        out.println("<tr><th>Headlines</th><td><pre>" + (scraper.headlines != null ? scraper.headlines : "") + "</pre></td></tr>");
                        break;
                    case "authors":
                        out.println("<tr><th>Authors</th><td><pre>" + (scraper.authors != null ? scraper.authors : "") + "</pre></td></tr>");
                        break;
                    case "publishedDate":
                        out.println("<tr><th>Published Dates</th><td><pre>" + (scraper.publishedDate != null ? scraper.publishedDate : "") + "</pre></td></tr>");
                        break;
                }
            }
        }

        out.println("</table>");
        out.println("</body></html>");
    }
}
