package org.dstadler.jgit.graph;

import java.util.List;

public class Branch {

    private List<NodeCommit> commits;
    private String name;
    private NodeCommit root;

    /**
     * @param commits
     * @param name
     * @param root
     */
    public Branch(List<NodeCommit> commits, String name, NodeCommit root) {
        super();
        this.commits = commits;
        this.name = name;
        this.root = root;
    }


    /**
     * @return the commits
     */
    public List<NodeCommit> getCommits() {
        return commits;
    }


    /**
     * @param commits the commits to set
     */
    public void setCommits(List<NodeCommit> commits) {
        this.commits = commits;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the root
     */
    public NodeCommit getRoot() {
        return root;
    }


    /**
     * @param root the root to set
     */
    public void setRoot(NodeCommit root) {
        this.root = root;
    }

}
