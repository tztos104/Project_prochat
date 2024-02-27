package prochat.yj_newsfeedservice.model.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;



@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users {
   private Long id;

   private String memberPw;
   private String email;
   private MemberRole role;

   private String name;
   private String phone;
   private String address;
   private String profile;
   private String image;

   private Timestamp regDate;
   private Timestamp updateDate;
   private Timestamp removeDate;





}