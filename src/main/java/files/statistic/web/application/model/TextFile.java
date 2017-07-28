package files.statistic.web.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
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
    private String dateOfStatisticComputation;

    @OneToMany(
            //fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LineStatistic> linesStatistic = new ArrayList<>();

    public TextFile(String name) {
        /* Getting current date when the statistic is calculated */
        String ldt = new LocalDateTime().toString();
        int indexT = ldt.indexOf('T');
        String dateAdded = ldt.substring(0, indexT) + " " + ldt.substring(indexT + 1, ldt.lastIndexOf(':'));

        this.dateOfStatisticComputation = dateAdded;
        this.name = name;
    }
}