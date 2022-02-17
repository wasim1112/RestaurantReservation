package app.general.common.utils;

import lombok.Data;


@Data
public class ErrorMessage {
    private String ar;
    private String en;

    public ErrorMessage(String ar, String en) {
        this.ar = ar;
        this.en = en;
    }
}
