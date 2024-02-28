package prochat.yj_activityservice.model.entity;


import com.example.yj_userservice.model.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "\"like\"")
@SQLDelete(sql = "UPDATE like SET remove_date = NOW() WHERE id=?")
@Where(clause = "remove_date is NULL")
@NoArgsConstructor
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;


    private Users member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @Column(name = "reg_date")
    private Timestamp regDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column(name = "remove_date")
    private Timestamp removeDate;

    @PrePersist
    void regDate() {
        this.regDate = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updateDate() {
        this.updateDate = Timestamp.from(Instant.now());
    }

    public static LikeEntity ofPost(PostEntity post, Users member) {
        LikeEntity entity = new LikeEntity();
        entity.setPost(post);
        entity.setMember(member);
        return entity;
    }
    public static LikeEntity ofComment(CommentEntity comment, Users member) {
        LikeEntity entity = new LikeEntity();
        entity.setComment(comment);;
        entity.setMember(member);
        return entity;
    }
}
