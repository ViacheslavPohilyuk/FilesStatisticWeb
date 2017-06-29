package luxoft.web.application.model;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TextForm {
    @NotNull
    @Size(min = 1)
    private String message;
}
