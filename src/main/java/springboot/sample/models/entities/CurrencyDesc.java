package springboot.sample.models.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "CURRENCYDESC")
public class CurrencyDesc implements java.io.Serializable {


}