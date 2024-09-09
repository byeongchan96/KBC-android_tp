package bitc.fullstack405.server_intravel.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "money")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long travId;

    private String moneyTitle;

    private Long expenses;

    @JsonManagedReference
    @OneToMany(mappedBy = "money", cascade = CascadeType.REMOVE)
    private List<PayEntity> pays;
}
