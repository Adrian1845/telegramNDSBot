package com.telegram.bot.logic;

import com.telegram.bot.domain.HtmlSelectors;
import com.telegram.bot.domain.RomEntry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class HtmlParser {

    public List<RomEntry> parseRomTable(String html) {
        Document doc = Jsoup.parse(html);
        Element table = doc.selectFirst(HtmlSelectors.TABLE + HtmlSelectors.TABLE_ID);
        if (table == null) return Collections.emptyList();

        List<RomEntry> entries = new ArrayList<>();
        for (Element row : table.select(HtmlSelectors.ROWS)) {
            Element link = row.selectFirst(HtmlSelectors.LINK_CELL);
            if (link == null) continue;

            String title = link.attr(HtmlSelectors.TITLE_ATTR);
            String href = link.attr(HtmlSelectors.HREF_ATTR);
            String size = row.selectFirst(HtmlSelectors.SIZE_CELL) != null
                    ? row.selectFirst(HtmlSelectors.SIZE_CELL).text()
                    : "?";

            entries.add(new RomEntry(title, href, size));
        }
        return entries;
    }
}
