package springboot.sample.models.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "CURRENCY")
public class Currency implements java.io.Serializable {

}
