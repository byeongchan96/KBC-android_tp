package bitc.fullstack405.server_intravel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "pay")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "money_id", nullable = false)
    private MoneyEntity money;

    private String payTitle;

    private Long plusAmt;

    private Long minusAmt;
}
