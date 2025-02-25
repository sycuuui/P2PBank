package bank.p2pbank.domain.batch.entity;

import bank.p2pbank.domain.batch.enumerate.JobType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "batchJob")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BatchJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "bigint")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private JobType jobType;  // 배치 작업 유형 (이자 지급, 정산 등)

    @Column(columnDefinition = "TEXT")
    private String details;  // 배치 실행 결과

    @Builder
    public BatchJob(JobType jobType, String details) {
        this.jobType = jobType;
        this.details = details;
    }
}
