import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
    public static void main(String[] args) throws Exception {
        final String url = "https://bbc.com";

        BBCScraping scraper = new BBCScraping();
        scraper.scrape(url);
        System.out.println(scraper);
    }

    static class BBCScraping {
        private String title;
        private String headings;
        private String links;
        private String authors;
        private String publishedDate;
        private String headlines;

        public void scrape(String url) throws IOException {
            Document doc = Jsoup.connect(url).get();

            // Existing code
            this.title = doc.title();

            // Headings extraction
            StringBuilder headingBuilder = new StringBuilder();
            for (int i = 1; i <= 6; i++) {
                Elements headingElements = doc.select("h" + i);
                for (Element heading : headingElements) {
                    headingBuilder.append("H").append(i).append(": ").append(heading.text()).append("\n");
                }
            }
            this.headings = headingBuilder.toString();

            // Links extraction
            StringBuilder linkBuilder = new StringBuilder();
            Elements linkElements = doc.select("a[href]");
            for (Element link : linkElements) {
                linkBuilder.append(link.text()).append(" -> ").append(link.absUrl("href")).append("\n");
            }
            this.links = linkBuilder.toString();

            // New extraction loops
            StringBuilder headlinesBuilder = new StringBuilder();
            StringBuilder authorsBuilder = new StringBuilder();
            StringBuilder datesBuilder = new StringBuilder();

            // Article container selector - may need adjustment based on current BBC layout
            Elements articles = doc.select("div[data-testid*='card'], article, div.gs-c-promo");
            
            for (Element article : articles) {
                // Headline extraction
                Elements headlineElements = article.select("h2, h3, .ssrcss-1if1g9v-MainHeading");
                for (Element headline : headlineElements) {
                    headlinesBuilder.append(headline.text()).append("\n");
                }

                // Author extraction
                Elements authorElements = article.select("p[data-testid='author'], .ssrcss-1pjc44v-AuthorText, .gs-c-promo-meta__author");
                for (Element author : authorElements) {
                    authorsBuilder.append(author.text()).append("\n");
                }

                // Date extraction
                Elements dateElements = article.select("time[datetime], [data-testid='timestamp']");
                for (Element date : dateElements) {
                    datesBuilder.append(date.attr("datetime")).append("\n");
                }
            }

            this.headlines = headlinesBuilder.toString();
            this.authors = authorsBuilder.toString();
            this.publishedDate = datesBuilder.toString();
        }

        // Add new getters/setters
        public String getAuthors() {
            return authors;
        }

        public void setAuthors(String authors) {
            this.authors = authors;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getHeadlines() {
            return headlines;
        }

        public void setHeadlines(String headlines) {
            this.headlines = headlines;
        }

        public String toString() {
            return String.format(
                "Title:\n%s\n\nHeadings:\n%s\nLinks:\n%s\nHeadlines:\n%s\nAuthors:\n%s\nPublished Dates:\n%s",
                title, headings, links, headlines, authors, publishedDate
            );
        }
    }
}
