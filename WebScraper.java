import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebScraper {
    static class BBCScraping {
        private String title;
        private String headings;
        private String links;
        private String authors;
        private String publishedDate;
        private String headlines;

        public void scrape(String url) throws IOException {
            Document doc = Jsoup.connect(url).get();

            this.title = doc.title();
            StringBuilder headingBuilder = new StringBuilder();
            for (int i = 1; i <= 6; i++) {
                for (Element heading : doc.select("h" + i)) {
                    headingBuilder.append("H").append(i).append(": ").append(heading.text()).append("\n");
                }
            }
            this.headings = headingBuilder.toString();

            StringBuilder linkBuilder = new StringBuilder();
            for (Element link : doc.select("a[href]")) {
                linkBuilder.append(link.text()).append(" -> ").append(link.absUrl("href")).append("\n");
            }
            this.links = linkBuilder.toString();

            StringBuilder headlinesBuilder = new StringBuilder();
            StringBuilder authorsBuilder = new StringBuilder();
            StringBuilder datesBuilder = new StringBuilder();

            for (Element article : doc.select("div[data-testid*='card'], article, div.gs-c-promo")) {
                for (Element headline : article.select("h2, h3, .ssrcss-1if1g9v-MainHeading")) {
                    headlinesBuilder.append(headline.text()).append("\n");
                }

                for (Element author : article.select("p[data-testid='author'], .ssrcss-1pjc44v-AuthorText, .gs-c-promo-meta__author")) {
                    authorsBuilder.append(author.text()).append("\n");
                }

                for (Element date : article.select("time[datetime], [data-testid='timestamp']")) {
                    datesBuilder.append(date.attr("datetime")).append("\n");
                }
            }
            
            this.headlines = headlinesBuilder.toString();
            this.authors = authorsBuilder.toString();
            this.publishedDate = datesBuilder.toString();
        }

        public String getTitle() { return title; }
        public String getHeadings() { return headings; }
        public String getLinks() { return links; }
        public String getHeadlines() { return headlines; }
        public String getAuthors() { return authors; }
        public String getPublishedDate() { return publishedDate; }
    }
}
