package org.shopnow.structures;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.shopnow.enums.PageType;

@AllArgsConstructor
public class Page {
    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private PageType type;
}
