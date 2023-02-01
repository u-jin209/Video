package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Timestamp;

@Data
public class RentalDTO {

    private int rental_id;
    private Timestamp rental_date;
    private int inventory_id;
    private int customer_id;
    private Timestamp return_date;
    private int staff_id;
    private Timestamp last_update;

}
