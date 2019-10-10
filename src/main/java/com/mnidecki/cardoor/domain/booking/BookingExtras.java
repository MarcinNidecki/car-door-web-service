package com.mnidecki.cardoor.domain.booking;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "booking_extras")
public class BookingExtras implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "icon_path")
    private String iconPath;

    @OneToMany(targetEntity = BookingExtrasItem.class,
            mappedBy = "bookingExtras",
            fetch = FetchType.EAGER)
    private List<BookingExtrasItem> bookingExtrasItems = new ArrayList<>();

    @Override
    public String toString() {
        return "BookingExtras{" +
                "price=" + price +
                '}';
    }

}
