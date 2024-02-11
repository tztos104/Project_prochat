package prochat.yj_activityservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "file")
@Data
public class File {

	@Id
	@Column(name = "file_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "file_name", length = 150)
	private String fileName;
	
	@Column(name = "member_id", length = 150)
	private String memberId;
}
