import grails.util.Holders
import it.bitz.cuke2customer.JiraService
import it.bitz.cuke2customer.vcs.GitAdapterService
import it.bitz.cuke2customer.vcs.SvnAdapterService

// Place your Spring DSL code here
beans = {

    def grailsApplication = Holders.grailsApplication

    if (grailsApplication.config.project.vcs.integration.toUpperCase() == 'ON') {
        switch (grailsApplication.config.project.vcs.type.toUpperCase()) {
            case 'SVN':
                versionControlAdapter(SvnAdapterService) {
                    svnUser = grailsApplication.config.project.vcs.svn.user
                    svnPassword = grailsApplication.config.project.vcs.svn.password
                    svnUrl = grailsApplication.config.project.vcs.svn.url
                }
                break;
            case 'GIT':
                versionControlAdapter(GitAdapterService) {
                    gitUser = grailsApplication.config.project.vcs.git.user
                    gitPassword = grailsApplication.config.project.vcs.git.password
                    gitUrl = grailsApplication.config.project.vcs.git.url
                    path = grailsApplication.config.project.vcs.featurePath
                    featureDirectory = grailsApplication.config.project.feature.directory
                }
                break;
            default: throw new IllegalArgumentException("no known vcs type configured, use one of svn or git")
        }
    }

    if(grailsApplication.config.project.jira.integration.toUpperCase() == 'ON') {
        jiraService(JiraService) {
            jiraUser = grailsApplication.config.project.jiraUser
            jiraPassword = grailsApplication.config.project.jiraPassword
            jiraUrl = grailsApplication.config.project.jiraUrl
        }
    }


}
