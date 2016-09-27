package org.dstadler.jgit.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dstadler.jgit.graph.NodeCommit;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

public class Dumpers {

    private static int refcount = 0;
    private static Map<String, String> stable = new HashMap<>();

    private static String getNextRef() {
        refcount++;
        if (refcount <= 26) {
            return String.valueOf((char) (refcount + 'A' - 1));
        }
        int ltr = refcount / 26;
        char c = (char) (ltr + 'A' - 1);
        int i = refcount - (ltr * 26) + 1;
        String code = String.format("%c%d", c, i);
        // System.out.println("refcount=" + refcount + ", code=" + code);
        return code;
    }

    private static String findSymbol(String sym) {
        String val = stable.get(sym);
        return val;
    }

    public static String getSub(String sym) {
        // System.out.println(sym);
        return sym;
        /*
         * String val = findSymbol(sym);
         * if (val != null) {
         * return val;
         * }
         * String sub = getNextRef();
         * stable.put(sym, sub);
         * return sub;
         */

    }


    public static void dumpRevCommit(RevCommit c) {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------\nCommit: {");
        sb.append("\n  name = " + getSub(c.getName()));
        sb.append(",\n  commitTime = " + c.getCommitTime());
        sb.append(",\n  firstByte = " + String.format("%x", c.getFirstByte()));
        sb.append(",\n  shortMessage = " + c.getShortMessage());
        sb.append(",\n  fullMessage = " + c.getFullMessage().substring(0, c.getFullMessage().length() - 1));
        sb.append(",\n  parentCount = " + c.getParentCount());

        if (c.getParentCount() > 0) {
            sb.append(",\n  parents = [");
            for (RevCommit rev : c.getParents()) {
                sb.append(rev.getName() + ",");
            }
            sb.setLength(sb.length() - 1);
            sb.append("]");
        }
        sb.append(",\n  id = " + getSub(c.getId().getName()));
        sb.append(",\n  authorIdent = " + c.getAuthorIdent());
        sb.append(",\n  committerIdent = " + c.getCommitterIdent());
        sb.append(",\n  abbreviated = " + c.abbreviate(6));
        sb.append(",\n  tree = " + getSub(c.getTree().getName()));
        sb.append("}");
        System.out.println(sb.toString());

    }

    public static void dumpRef(Ref r) {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------\nRef: {");
        sb.append("\n  name = " + getSub(r.getName()));
        sb.append(",\n  objectId = " + getSub(r.getObjectId().getName()));
        sb.append(",\n  leaf = " + getSub(r.getLeaf().getName()));
        sb.append(",\n  peeledObjectId = " + (r.getPeeledObjectId() != null ? getSub(r.getPeeledObjectId().getName()) : null));
        sb.append(",\n  isSymbolic = " + r.isSymbolic());
        sb.append(",\n  isPeeled = " + r.isPeeled());
        sb.append("}");
        System.out.println(sb.toString());

    }

    private static String getKey(String val) {
        for (Map.Entry<String, String> e : stable.entrySet()) {
            String key = e.getKey();
            Object value = e.getValue();
            if (val.equals(value)) {
                return key;
            }
        }
        return null;
    }

    public static void dumpSymTable() {
        List<String> vals = new ArrayList<>(stable.values());
        Collections.sort(vals);
        for (String val : vals) {
            System.out.println(val + " : " + getKey(val));
        }
    }

    public static void dumpLinks(List<NodeCommit> nodes) {
        for (NodeCommit node : nodes) {
            List<NodeCommit> parents = node.getParents();
            if (parents != null && parents.size() > 0) {
                for (NodeCommit parent : parents) {
                    System.out.println("link: " + node.getRev().abbreviate(6) +
                            " --> " + parent.getRev().abbreviate(6));

                }
            }
        }
    }


}
