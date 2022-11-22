package com.proyecto.cart.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

import com.proyecto.cart.security.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Document("venta")
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Getter @Setter
    private String id;
    @NotNull
    @Getter @Setter
    private Double total;
    @NotNull
    @Getter @Setter
    @Column(columnDefinition = "DATE")
    private Date date;
    @ManyToOne(optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @Getter @Setter
    private User client; /**Usuario que realiza la compra, por eso relación de muchos a uno */

    public Sale(double total, Date date, User client) {
        this.total = total;
        this.date = date;
        this.client = client;
    }
}
