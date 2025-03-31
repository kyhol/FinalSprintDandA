package com.keyin.bstexplorer.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "binary_trees")
public class BinaryTree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "input_numbers", nullable = false)
    private String inputNumbers;

    @Column(name = "tree_structure", columnDefinition = "TEXT", nullable = false)
    private String treeStructure;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "balanced")
    private Boolean balanced = false;

    public BinaryTree() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInputNumbers() {
        return inputNumbers;
    }

    public void setInputNumbers(String inputNumbers) {
        this.inputNumbers = inputNumbers;
    }

    public String getTreeStructure() {
        return treeStructure;
    }

    public void setTreeStructure(String treeStructure) {
        this.treeStructure = treeStructure;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getBalanced() {
        return balanced;
    }

    public void setBalanced(Boolean balanced) {
        this.balanced = balanced;
    }
}