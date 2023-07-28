package mg.hei.heicorrectorapi.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"test\"")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class Test implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "test_result_id")
    private TestResult result;
    private String kata;
    private Integer totalPoints;
    private Integer passed;
    private Integer failed;
}
