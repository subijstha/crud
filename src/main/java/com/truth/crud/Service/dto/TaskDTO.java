package com.truth.crud.Service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDTO {
    public String name;

    public String instruction;

    public int priority;
}
