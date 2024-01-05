package happyaging.server.domain.question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@Entity
@Getter
@Table(name = "question")
public class Question {
    @Id
    @Column(name = "question_id")
    private String number;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String type;
}
