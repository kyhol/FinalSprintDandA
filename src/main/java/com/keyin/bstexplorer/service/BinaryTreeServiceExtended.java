package com.keyin.bstexplorer.service;

import com.keyin.bstexplorer.model.TreeNode;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BinaryTreeServiceExtended {

    public TreeNode buildBalancedBST(List<Integer> numbers) {

        Collections.sort(numbers);

        return sortedArrayToBST(numbers, 0, numbers.size() - 1);
    }

    private TreeNode sortedArrayToBST(List<Integer> sortedNumbers, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;

        TreeNode node = new TreeNode(sortedNumbers.get(mid));

        node.setLeft(sortedArrayToBST(sortedNumbers, start, mid - 1));
        node.setRight(sortedArrayToBST(sortedNumbers, mid + 1, end));

        return node;
    }
}