package com.telegram.bot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RomEntry {
    private String title;
    private String href;
    private String size;
}