package it.bitz.cuke2customer.vcs

import it.bitz.cuke2customer.VersionControlAdapter
import org.eclipse.jgit.api.CloneCommand
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.tmatesoft.svn.core.SVNDepth
import org.tmatesoft.svn.core.SVNURL
import org.tmatesoft.svn.core.wc.SVNClientManager
import org.tmatesoft.svn.core.wc.SVNRevision
import org.tmatesoft.svn.core.wc.SVNUpdateClient
import org.tmatesoft.svn.core.wc.SVNWCUtil

class GitAdapterService implements VersionControlAdapter {

    String gitUser
    String gitPassword
    String gitUrl
    String path
    String featureDirectory

    void checkoutLatestRevision(File destinationDirectory) {
        Git.cloneRepository()
                .setURI(gitUrl)
                .setDirectory(destinationDirectory)
                .call();

        log.info " done... "
    }
}
