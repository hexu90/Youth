package Youth.core;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Value
public class YouthException extends Throwable {

    private int code;

    private String message;

    public YouthException(int code, String message) {
        this(code, message, null);
    }

    public YouthException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
    }
}
