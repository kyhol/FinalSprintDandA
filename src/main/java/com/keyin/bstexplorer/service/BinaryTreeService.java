package com.keyin.bstexplorer.service;

import com.keyin.bstexplorer.model.BinaryTree;
import com.keyin.bstexplorer.model.TreeNode;
import com.keyin.bstexplorer.repository.BinaryTreeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BinaryTreeService {

    @Autowired
    private BinaryTreeRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();

    public TreeNode buildBST(List<Integer> numbers) {
        TreeNode root = null;
        for (int num : numbers) {
            root = insert(root, num);
        }
        return root;
    }

    private TreeNode insert(TreeNode root, int value) {
        if (root == null) {
            return new TreeNode(value);
        }

        if (value < root.getValue()) {
            root.setLeft(insert(root.getLeft(), value));
        } else if (value > root.getValue()) {
            root.setRight(insert(root.getRight(), value));
        }

        return root;
    }

    public BinaryTree saveTree(List<Integer> numbers, TreeNode root, boolean balanced) {
        try {
            String numbersStr = numbers.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            String treeJson = mapper.writeValueAsString(root);

            BinaryTree tree = new BinaryTree();
            tree.setInputNumbers(numbersStr);
            tree.setTreeStructure(treeJson);
            tree.setCreatedAt(LocalDateTime.now());
            tree.setBalanced(balanced);

            return repository.save(tree);
        } catch (Exception e) {
            throw new RuntimeException("Error saving tree", e);
        }
    }

    public Optional<BinaryTree> getTreeById(Long id) {
        return repository.findById(id);
    }

    public List<BinaryTree> getAllTrees() {
        return repository.findAllByOrderByCreatedAtDesc();
    }
}