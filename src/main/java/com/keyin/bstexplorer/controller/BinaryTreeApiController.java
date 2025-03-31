package com.keyin.bstexplorer.controller;

import com.keyin.bstexplorer.model.BinaryTree;
import com.keyin.bstexplorer.model.TreeNode;
import com.keyin.bstexplorer.service.BinaryTreeService;
import com.keyin.bstexplorer.service.BinaryTreeServiceExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BinaryTreeApiController {

    @Autowired
    private BinaryTreeService treeService;

    @Autowired
    private BinaryTreeServiceExtended extendedTreeService;

    @PostMapping("/trees")
    public ResponseEntity<?> createTree(@RequestBody Map<String, Object> request) {
        try {
            List<Integer> numbers = (List<Integer>) request.get("numbers");
            boolean balanced = request.containsKey("balanced") ?
                    (boolean) request.get("balanced") : false;

            TreeNode root;
            if (balanced) {
                root = extendedTreeService.buildBalancedBST(numbers);
            } else {
                root = treeService.buildBST(numbers);
            }

            BinaryTree savedTree = treeService.saveTree(numbers, root, balanced);

            Map<String, Object> response = new HashMap<>();
            response.put("id", savedTree.getId());
            response.put("inputNumbers", savedTree.getInputNumbers());
            response.put("treeStructure", savedTree.getTreeStructure());
            response.put("createdAt", savedTree.getCreatedAt());
            response.put("balanced", savedTree.getBalanced());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating tree: " + e.getMessage());
        }
    }

    @GetMapping("/trees/{id}")
    public ResponseEntity<?> getTreeById(@PathVariable Long id) {
        try {
            Optional<BinaryTree> tree = treeService.getTreeById(id);
            if (tree.isPresent()) {
                return ResponseEntity.ok(tree.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Tree with ID " + id + " not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving tree: " + e.getMessage());
        }
    }

    @GetMapping("/trees")
    public ResponseEntity<?> getAllTrees() {
        try {
            List<BinaryTree> trees = treeService.getAllTrees();
            return ResponseEntity.ok(trees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving trees: " + e.getMessage());
        }
    }
}