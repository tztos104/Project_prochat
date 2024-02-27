package prochat.yj_activityservice.model.entity;


import com.example.yj_userservice.dto.Users;
import com.example.yj_userservice.dto.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "\"post\"")
@SQLDelete(sql = "UPDATE post SET remove_date = NOW() WHERE id=?")
@Where(clause = "remove_date is NULL")
@NoArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    private String members;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private List<CommentEntity> comments;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private List<LikeEntity> likes;

    private String stockCode;
    private Timestamp regDate;
    private Timestamp updateDate;
    private Timestamp removeDate;


    @PrePersist
    void regDate(){
        this.regDate= Timestamp.from(Instant.now());
    }
    @PreUpdate
    void updateDate(){
        this.updateDate= Timestamp.from(Instant.now());
    }


    public static PostEntity of(String title, String content, String memberEntity, String stockCode) {
        PostEntity entity = new PostEntity();
        entity.setTitle(title);
        entity.setContent(content);
        entity.setMembers(memberEntity);
        entity.setStockCode(stockCode);
        return entity;
    }
}