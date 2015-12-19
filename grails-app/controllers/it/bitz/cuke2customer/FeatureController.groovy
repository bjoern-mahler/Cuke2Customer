package it.bitz.cuke2customer

import it.bitz.cuke2customer.model.Comment
import it.bitz.cuke2customer.model.Feature
import it.bitz.cuke2customer.parser.GherkinParser
import org.apache.commons.io.FileUtils

class FeatureController {

    def grailsApplication

    VersionControlAdapter versionControlAdapter
    FeatureService featureService
    JiraService jiraService

    def index() {
        redirect(action: 'listFeatures')
    }

    def listFeatures() {
    }

    def show() {
        redirect(action: 'showFeature', params: params)
    }

    def showFeature(String featureHashCode) {
        String gherkin = featureService.getFeatureWithHashCode(featureHashCode)
        Feature feature = new GherkinParser(useJira()).parse(gherkin)
        List<Comment> jiraComments = fetchJiraComments(feature)
        return [feature: feature, jiraIntegration: useJira(), comments: jiraComments]
    }

    private fetchJiraComments(Feature feature) {
        if (useJira()) {
            return jiraService.fetchCommentsForFeature(feature).sort { it.date }.reverse()
        }
        return []
    }

    private boolean useJira() {
        return grailsApplication.config.project.jira.integration.toUpperCase() == 'ON'
    }


    def retrieveFeatures() {
        final String dir = grailsApplication.config.project.feature.directory
        if (useVcs()) {
            File repositoryDirectory = new File(dir)
            if (repositoryDirectory.exists()) {
                FileUtils.forceDelete(repositoryDirectory)
            }
            versionControlAdapter.checkoutLatestRevision(repositoryDirectory)
        }
        featureService.loadFeatures(dir)
        redirect(action: 'listFeatures')
    }

    /**
     * @return true , if the user wants to use a version control system like svn or git
     */
    private boolean useVcs() {
        return grailsApplication.config.project.vcs.integration.toUpperCase() == 'ON'
    }
}
