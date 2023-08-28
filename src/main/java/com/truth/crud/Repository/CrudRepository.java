package com.truth.crud.Repository;

import com.truth.crud.Model.Task;
import com.truth.crud.Service.dto.TaskDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface CrudRepository extends JpaRepository<Task,Integer> {
    List<Task> findByPriorityGreaterThanEqualOrderByPriorityDesc(int pNum);

    @Query("select t from Task t where t.name = :name ")
   public Task findByName(@RequestParam String name);

    Task findByNameAndInstructionAndPriority(String name, String instruction, int priority);
}
