package com.vgg;

/**

 */
public class ReverseLinkedList {


    public void addNode(int val){
        Node curr = new Node(val);
        if (head == null){
            head = curr;
            tail = curr;
        }
        else {
            tail.next = curr;
            tail = curr;
        }

    }

    public void reverse(){
        if (head != null) {
            reverseRecurse(head, null);
        }
    }

    private void reverseRecurse(Node curr, Node prev){
        if (curr.next != null) {
            reverseRecurse(curr.next, curr);
        }
        if (curr.next == null){
            head = curr;
        }
        else if (prev == null){
            tail = curr;
        }
        curr.next = prev;
    }

    public String getList(){
        StringBuilder list = new StringBuilder();
        Node curr = head;
        while (curr != null){
            list.append(curr.val + " ");
            curr = curr.next;
        }
        return list.toString().trim();
    }

    private Node head,tail;

    private class Node{
        Node(int val){
            this.val = val;
            this.next = null;
        };

        private Node next;
        private int val;
    }
}
