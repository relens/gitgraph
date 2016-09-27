package org.dstadler.jgit.unfinished;

/*
 * Copyright 2013, 2014 Dominik Stadler
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.Repository;



/**
 * Note: This snippet is not done and likely does not show anything useful yet
 *
 * @author dominik.stadler at gmx.at
 */
public class ListRefLog {

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = CookbookHelper.openExistingRepository(CookbookHelper.REPO1)) {
            try (Git git = new Git(repository)) {
                List<Ref> refs = git.branchList().call();
                for (Ref ref : refs) {
                    System.out.println("Branch: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());

                    listReflog(repository, ref);
                }

                List<Ref> call = git.tagList().call();
                for (Ref ref : call) {
                    System.out.println("Tag: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());

                    listReflog(repository, ref);
                }
            }
        }
    }

    private static void listReflog(Repository repository, Ref ref) throws GitAPIException, InvalidRefNameException {
        /*
         * Ref head = repository.getRef(ref.getName());
         * RevWalk walk = new RevWalk(repository);
         * RevCommit commit = walk.parseCommit(head.getObjectId());
         */

        try (Git git = new Git(repository)) {
            Collection<ReflogEntry> call = git.reflog().setRef(ref.getName()).call();
            Iterator<ReflogEntry> it = call.iterator();
            while (it.hasNext()) {
                ReflogEntry reflog = it.next();
                System.out.println("Reflog: " + reflog);
            }
        }
    }
}
