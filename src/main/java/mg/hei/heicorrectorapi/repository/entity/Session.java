package mg.hei.heicorrectorapi.repository.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"session\"")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class Session implements Serializable {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private String id;
  private String name;
  private ZonedDateTime date;
  private String associatedCourse;
  private String type;
  private String source;
  @OneToMany(mappedBy = "session", cascade = ALL, fetch = EAGER)
  private List<TestResult> results;
}
