package luxoft.web.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/* Lombok annotations
 * for generating getters, setters, toString and constructors */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "id")
/*------------------------------------------------------------*/
@Entity
@Table(name = "File")
public class TextFile implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dateStatComp")
    @JsonIgnore
    private Date dateOfStatisticComputation;

    @Transient
    private String formatDate;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LineStatistic> linesStatistic = new ArrayList<>();

    public TextFile(String name) {
        /* Getting current date with in time zone: "Africa/Cairo" */
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Africa/Cairo"));
        Calendar cal = df.getCalendar();
        cal.add(Calendar.HOUR, +3);
        cal.add(Calendar.YEAR, +80);

        this.name = name;
        this.dateOfStatisticComputation = cal.getTime();
    }

}