package files.statistic.web.application.model.form;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TextForm {
    @NotNull
    @Size(min = 1)
    private String message;
}
