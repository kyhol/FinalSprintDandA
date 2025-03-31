package com.keyin.bstexplorer.repository;

import com.keyin.bstexplorer.model.BinaryTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BinaryTreeRepository extends JpaRepository<BinaryTree, Long> {

    List<BinaryTree> findAllByOrderByCreatedAtDesc();
}