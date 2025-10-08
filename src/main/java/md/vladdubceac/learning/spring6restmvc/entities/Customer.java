package md.vladdubceac.learning.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "VARCHAR", updatable = false, nullable = false)
    private UUID id;
    private String name;
    @Version
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
