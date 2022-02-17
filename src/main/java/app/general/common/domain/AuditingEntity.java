package app.general.common.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.Instant;


@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditingEntity {

    @CreatedBy
    protected String createdBy;

    @NotNull
    @CreatedDate
    protected Instant createdAt;

    @LastModifiedBy
    protected String lastModifiedBy;

    @LastModifiedDate
    protected Instant lastModifiedAt;

    @NotNull
    @Column(nullable = false)
    protected Boolean isDeleted = false;
}
