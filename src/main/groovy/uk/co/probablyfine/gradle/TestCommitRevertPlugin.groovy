package uk.co.probablyfine.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test

class TestCommitRevertExtension {
    String commitCommand = 'git commit --all --message %MESSAGE%'
    String revertCommand = 'git reset --hard HEAD'
    Boolean promptForMessage = false
}

class TestCommitRevertPlugin implements Plugin<Project> {

    static void runCommand(command) {
        println command.execute().text
    }

    static Closure commitRevert(Project project) {
        def extension = project.extensions.findByType(TestCommitRevertExtension)

        return { description, result ->
            // Only the final summary has a null parent
            // This is an individual test run, so ignore
            if (description.parent) return

            runCommand(result.failedTestCount > 0 ? extension.revertCommand : commit(extension))
        }
    }

    static def commit(TestCommitRevertExtension extension) {
        String message = System.console() && extension.promptForMessage ? System.console().readLine('Enter commit message: '): 'TCR'

        return extension.commitCommand.replace("%MESSAGE%", message)
    }

    @Override
    void apply(Project project) {

        project.extensions.create('testCommitRevert', TestCommitRevertExtension)

        project.tasks.withType(Test) {
            afterSuite commitRevert(project)
        }
    }
}
