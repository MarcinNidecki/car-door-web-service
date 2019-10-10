package com.mnidecki.cardoor.domain.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "discount_code")
public class DiscountCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    @Column(name = "discount_description")
    private String discountDescription;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Warsaw")
    @Column(name = "valid_from")
    private Timestamp validFrom;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Warsaw")
    @Column(name = "valid_to")
    private Timestamp validTo;

    @OneToMany(targetEntity = Booking.class,
            mappedBy = "discountCode",
            fetch = FetchType.EAGER)
    private List<Booking> bookingList = new ArrayList<>();

    public DiscountCode(Long id, Integer discountPercentage, String discountDescription, Timestamp validFrom, Timestamp validTo) {
        this.id = id;
        this.discountPercentage = discountPercentage;
        this.discountDescription = discountDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
}
