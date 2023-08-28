package com.truth.crud.Model;


import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int taskNumber;
    @Column(unique = true)
    public String name;

    public String instruction;

    @Column(unique = true)
    public int priority;
}
