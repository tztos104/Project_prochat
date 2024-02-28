package prochat.yj_activityservice.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "comment")
@SQLDelete(sql = "UPDATE comment SET remove_date = NOW() WHERE id=?")
@Where(clause = "remove_date is NULL")
@NoArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    private String member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private List<LikeEntity> likes;

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

    public static CommentEntity of(String comment, PostEntity post, String member) {
        CommentEntity entity = new CommentEntity();
        entity.setComment(comment);
        entity.setPost(post);
        entity.setMember(member);
        return entity;
    }
}
