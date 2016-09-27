package org.dstadler.jgit.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dstadler.jgit.helper.CookbookHelper;
import org.dstadler.jgit.helper.Dumpers;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;



/**
 * Simple snippet which shows how to use RevWalk to iterate over objects
 */
public class WalkRevG {

    private static List<NodeCommit> nodes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            Ref head = repository.exactRef("refs/heads/master");

            // a RevWalk allows to walk over commits based on some filtering that is defined
            try (RevWalk walk = new RevWalk(repository)) {
                RevCommit commit = walk.parseCommit(head.getObjectId());
                System.out.println("Start-Commit: " + commit);

                System.out.println("Walking all commits starting at HEAD");
                walk.markStart(commit);
                for (RevCommit rev : walk) {
                    Dumpers.dumpRevCommit(rev);
                    NodeCommit node = new NodeCommit();
                    node.setRev(rev);
                    nodes.add(node);
                }
                linkParents(nodes);
                Dumpers.dumpLinks(nodes);

                walk.dispose();
            }
        }
    }

    private static void linkParents(@SuppressWarnings("hiding") List<NodeCommit> nodes) {
        for (NodeCommit node1 : nodes) {
            if (node1.getRev().getParents() != null && node1.getRev().getParents().length > 0) {
                for (NodeCommit node2 : nodes) {
                    if (node2 != node1) {

                        if (node1.hasParent(node2)) {
                            node1.addParent(node2);
                        }
                    }
                }
            }
        }
    }



}
