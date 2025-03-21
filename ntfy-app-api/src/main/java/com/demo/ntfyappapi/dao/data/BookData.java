package com.demo.ntfyappapi.dao.data;

import java.time.OffsetDateTime;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class BookData implements Cloneable{
    private String title;
    private String description;
    private String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;

    @Override
    public BookData clone() {
        try {
            return (BookData) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
