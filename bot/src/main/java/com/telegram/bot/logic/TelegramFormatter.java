package com.telegram.bot.logic;

import com.telegram.bot.domain.BotConstants;
import com.telegram.bot.domain.HtmlSelectors;
import com.telegram.bot.domain.RomEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class TelegramFormatter {

    private final Logger log = LoggerFactory.getLogger(TelegramFormatter.class);

    public String formatRomList(List<RomEntry> roms, String query, String baseUrl) {

        if (roms.isEmpty()) return String.format(BotConstants.romsNotFound, escapeHtml(query));

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(BotConstants.romsFound, query)).append(HtmlSelectors.NEWLINE);
        for (RomEntry entry : roms) {

            // Match the rom with the game we want
            if (!entry.getTitle().toLowerCase().contains(query.toLowerCase())) continue;

            // Format the message
            sb.append(HtmlSelectors.BULLET).append(HtmlSelectors.LINK_OPEN).append(baseUrl)
                    .append(urlEncode(entry.getHref())).append(HtmlSelectors.LINK_CLOSE)
                    .append(escapeHtml(entry.getTitle())).append(HtmlSelectors.LINK_END).append(HtmlSelectors.NEWLINE)
                    .append(HtmlSelectors.SIZE_PREFIX).append(entry.getSize())
                    .append(HtmlSelectors.NEWLINE).append(HtmlSelectors.NEWLINE);
        }

        log.info(sb.toString());
        return sb.toString();
    }

    private String escapeHtml(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private String urlEncode(String text) {
        return URLEncoder.encode(text, StandardCharsets.UTF_8).replace("+", "%20");
    }
}

