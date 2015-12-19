package it.bitz.cuke2customer.vcs

import it.bitz.cuke2customer.VersionControlAdapter
import org.tmatesoft.svn.core.SVNDepth
import org.tmatesoft.svn.core.SVNURL
import org.tmatesoft.svn.core.wc.SVNClientManager
import org.tmatesoft.svn.core.wc.SVNRevision
import org.tmatesoft.svn.core.wc.SVNUpdateClient
import org.tmatesoft.svn.core.wc.SVNWCUtil

class SvnAdapterService implements VersionControlAdapter {

    String svnUser
    String svnPassword
    String svnUrl

    void checkoutLatestRevision(File destinationDirectory) {
        SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true), svnUser,
                                                                      svnPassword)
        SVNUpdateClient updateClient = clientManager.getUpdateClient()
        SVNRevision revision = SVNRevision.HEAD
        boolean recursive = true
        log.info "checking out '$svnUrl' to '${destinationDirectory.getAbsolutePath()}'... "
        long revisionNumber = updateClient.doCheckout(SVNURL.parseURIEncoded(svnUrl), destinationDirectory, revision, revision,
                SVNDepth.fromRecurse(recursive), false)
        log.info " done, checked out revision $revisionNumber"
    }
}
