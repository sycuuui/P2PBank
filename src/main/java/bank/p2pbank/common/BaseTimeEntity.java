package bank.p2pbank.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //JPA에 공통 엔티티 상속받아 사용, 실제 테이블이 생성되지 않고 하위 엔티티 테이블에서 적용
@EntityListeners(AuditingEntityListener.class) //JPA Auduting 기능 활성화하여, @CreatedDate와 @LastModifiedDate 자동으로 동작
public abstract class BaseTimeEntity {
    @CreatedDate //생성 시 자동으로 createAt 값을 기록
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime createdAt;

    @LastModifiedDate //수정 시 자동으로 modifiedAt 값을 갱신
    @Column(name = "modified_at", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime modifiedAt;

    //soft delete -> 실제 데이터를 삭제하지 않고 비활성화
    @Column(name = "deleted_at", columnDefinition = "timestamp")
    private LocalDateTime deletedAt;
}
