package com.keyin.bstexplorer.service;

import com.keyin.bstexplorer.model.BinaryTree;
import com.keyin.bstexplorer.model.TreeNode;
import com.keyin.bstexplorer.repository.BinaryTreeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BinaryTreeServiceTest {

    @Mock
    private BinaryTreeRepository repository;

    @InjectMocks
    private BinaryTreeService treeService;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuildBST() {
        List<Integer> numbers = Arrays.asList(10, 5, 15, 3, 7, 12, 20);
        TreeNode root = treeService.buildBST(numbers);

        assertNotNull(root);
        assertEquals(10, root.getValue());

        assertNotNull(root.getLeft());
        assertEquals(5, root.getLeft().getValue());

        assertNotNull(root.getRight());
        assertEquals(15, root.getRight().getValue());

        assertNotNull(root.getLeft().getLeft());
        assertEquals(3, root.getLeft().getLeft().getValue());

        assertNotNull(root.getLeft().getRight());
        assertEquals(7, root.getLeft().getRight().getValue());

        assertNotNull(root.getRight().getLeft());
        assertEquals(12, root.getRight().getLeft().getValue());

        assertNotNull(root.getRight().getRight());
        assertEquals(20, root.getRight().getRight().getValue());
    }

    @Test
    void testSaveTree() throws Exception {
        List<Integer> numbers = Arrays.asList(10, 5, 15);
        TreeNode root = treeService.buildBST(numbers);

        BinaryTree mockTree = new BinaryTree();
        mockTree.setId(1L);
        mockTree.setInputNumbers("10,5,15");
        mockTree.setTreeStructure(objectMapper.writeValueAsString(root));
        mockTree.setCreatedAt(LocalDateTime.now());
        mockTree.setBalanced(false);

        when(repository.save(any(BinaryTree.class))).thenReturn(mockTree);

        BinaryTree savedTree = treeService.saveTree(numbers, root, false);

        verify(repository, times(1)).save(any(BinaryTree.class));

        assertNotNull(savedTree);
        assertEquals("10,5,15", savedTree.getInputNumbers());
        assertFalse(savedTree.getBalanced());
    }

    @Test
    void testGetTreeById() {
        Long treeId = 1L;
        BinaryTree mockTree = new BinaryTree();
        mockTree.setId(treeId);

        when(repository.findById(treeId)).thenReturn(Optional.of(mockTree));

        Optional<BinaryTree> result = treeService.getTreeById(treeId);

        assertTrue(result.isPresent());
        assertEquals(treeId, result.get().getId());
        verify(repository, times(1)).findById(treeId);
    }

    @Test
    void testGetAllTrees() {
        BinaryTree tree1 = new BinaryTree();
        tree1.setId(1L);
        BinaryTree tree2 = new BinaryTree();
        tree2.setId(2L);

        List<BinaryTree> mockTrees = Arrays.asList(tree1, tree2);

        when(repository.findAllByOrderByCreatedAtDesc()).thenReturn(mockTrees);

        List<BinaryTree> result = treeService.getAllTrees();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(repository, times(1)).findAllByOrderByCreatedAtDesc();
    }
}