package org.dstadler.jgit.graph;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.revwalk.RevCommit;

public class NodeCommit {



    private int x;
    private int y;
    RevCommit rev;
    List<NodeCommit> parents;

    public NodeCommit() {
        super();
        this.x = 0;
        this.y = 0;
        this.rev = null;
        this.parents = new ArrayList<>();
    }



    /**
     * @param x
     * @param y
     * @param rev
     * @param parents
     */
    public NodeCommit(int x, int y, RevCommit rev) {
        super();
        this.x = x;
        this.y = y;
        this.rev = rev;
        this.parents = new ArrayList<>();
    }



    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }


    /**
     * @return the rev
     */
    public RevCommit getRev() {
        return rev;
    }


    /**
     * @param rev the rev to set
     */
    public void setRev(RevCommit rev) {
        this.rev = rev;
    }



    /**
     * @return the parents
     */
    public List<NodeCommit> getParents() {
        return parents;
    }



    /**
     * @param parents the parents to set
     */
    public void setParents(List<NodeCommit> parents) {
        this.parents = parents;
    }


    public void addParent(NodeCommit node) {
        this.getParents().add(node);
    }

    public boolean hasParent(NodeCommit node) {

        RevCommit[] revparents = this.getRev().getParents();
        String target = node.getRev().getName();
        for (RevCommit revparent : revparents) {
            System.out.println("target=" + target + ", revparent=" + revparent.getName());
            if (target.equals(revparent.getName())) {
                return true;
            }
        }
        return false;
    }
}
