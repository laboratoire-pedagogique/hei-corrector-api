package mg.hei.heicorrectorapi.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"result\"")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class TestResult implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "session_id")
    private Session session;
    private String student;

    private Integer score;
    @OneToMany(mappedBy = "result", cascade = ALL, fetch = EAGER)
    private List<Test> tests;
}
